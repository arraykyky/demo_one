package com.springcloud.book.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.Constants;
import com.springcloud.book.management.config.OverallConfig;
import com.springcloud.book.management.config.redis.RedisServer;
import com.springcloud.book.management.domain.*;
import com.springcloud.book.management.dao.ArticleOrdersDao;
import com.springcloud.book.management.domain.vo.ArticleOrdersVO;
import com.springcloud.book.management.domain.vo.EmailSendVO;
import com.springcloud.book.management.domain.vo.EmailVO;
import com.springcloud.book.management.domain.vo.OrderLogsVO;
import com.springcloud.book.management.enums.OrderDealFlagEnum;
import com.springcloud.book.management.enums.OrderDealNumWithStateNumEnum;
import com.springcloud.book.management.enums.OrderEmailStateEnum;
import com.springcloud.book.management.enums.OrderTypeEnum;
import com.springcloud.book.management.remote.EmailSendRemote;
import com.springcloud.book.management.remote.ForeignRemote;
import com.springcloud.book.management.run.EmailSendRunnable;
import com.springcloud.book.management.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.management.util.DateUtil;
import com.springcloud.book.management.util.Tools;
import com.springcloud.book.management.util.email.SystemEmailUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-25
 */
@Service
public class ArticleOrdersServiceImpl extends ServiceImpl<ArticleOrdersDao, ArticleOrders> implements ArticleOrdersService {

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private UserService userService;

    @Autowired
    private NoTextRecordService noTextRecordService;

    @Autowired
    private SystemEmailService systemEmailService;

    @Autowired
    private EmailSendRemote emailSendRemote;

    @Autowired
    private ArticleOrdersFlowDetailsService articleOrdersFlowDetailsService;

    @Autowired
    private FullTextUploadFlowService fullTextUploadFlowService;

    @Autowired
    private ForeignRemote foreignRemote;

    @Override
    public void getOrdersPage(Page page, ArticleOrdersVO articleOrdersVO) {
        String roleCode = (String) redisServer.get(Constants.getUserModuleCacheUserRoleCode(articleOrdersVO.getToken()));
        String userEllaId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(articleOrdersVO.getToken()));
        articleOrdersVO.setUserRoleCode(roleCode);
        articleOrdersVO.setOrderAcceptAdminId(userEllaId);
        List<ArticleOrders> list = baseMapper.selectOrderPage(page,articleOrdersVO);
        page.setRecords(list);
    }

    @Override
    public ArticleOrders getOrderById(String orderId) {
        return baseMapper.selectById(orderId);
    }

    @Override
    @Transactional
    public void dealOrderById(ArticleOrdersVO articleOrdersVO) throws Exception{
        //redis获取登录用户的id
        String userId = (String) redisServer.get(Constants.getUserModuleCacheKeyForeignUserId(articleOrdersVO.getToken()));
        //获取登录用户信息
        User user = userService.getUserById(userId);
        //处理标记——未找到原文-2(订单重新恢复为申请状态)/拒绝-1/接受(处理中)1/馆际推送2/馆际撤回3
        Integer flag = articleOrdersVO.getDealFlag();
        //获取订单信息
        ArticleOrders articleOrders = this.getOrderById(articleOrdersVO.getOrderId());
        //获取订单申请时间
        String orderApplyTime = articleOrders.getOrderApplyTime();
        //获取当前时间
        Long time = System.currentTimeMillis();
        //声明数据更新对象
        ArticleOrders daoArticleOrders = new ArticleOrders();
        daoArticleOrders.setOrderId(articleOrders.getOrderId());
        daoArticleOrders.setOrderAcceptAdminId(user.getEllaUserId());
        daoArticleOrders.setOrderAcceptAdminName(user.getUserName());
        //接收订单
        if (flag == OrderDealFlagEnum.D_ACCEPT.getDealNum()){
            daoArticleOrders.setOrderAcceptCount(articleOrders.getOrderAcceptCount() + 1);
            daoArticleOrders.setOrderState(OrderDealNumWithStateNumEnum.getDealNumByDealsFlag(flag));
            daoArticleOrders.setOrderAcceptTime(DateUtil.formatCurrentDateTime(time));
            if (StringUtils.isNotBlank(orderApplyTime)){
                daoArticleOrders.setOrderResponseTimes(time - DateUtil.parseDateTime(orderApplyTime).getTime());
            }
        }
        //拒绝(未找到全文中，订单被接受次数打到3次以上后，手动拒绝订单)
        if (flag == OrderDealFlagEnum.D_REFUSE.getDealNum()){
            daoArticleOrders.setOrderState(OrderDealNumWithStateNumEnum.getDealNumByDealsFlag(flag));
            //判断全文记录是否存在
            NoTextRecord noTextRecord = noTextRecordService.getNotextRecordByAbstractId(articleOrders.getAbstractId());
            if (noTextRecord == null){
                NoTextRecord daoNoTextRecord = new NoTextRecord();
                daoNoTextRecord.setNoTextId(Tools.getUUID());
                daoNoTextRecord.setRecordTime(String.valueOf(System.currentTimeMillis()));
                daoNoTextRecord.setAbstractId(articleOrders.getAbstractId());
                daoNoTextRecord.setIsSeekOut(0);
                noTextRecordService.addNoTextRecord(daoNoTextRecord);
            }
            //发送文本邮件
            String emailState = null;
            String emailSendError = null;
            try {
                List<SystemEmail> list = systemEmailService.findAllSystemEmailDataList();
                EmailVO emailVO = SystemEmailUtil.getEmailVO(list, articleOrders.getOrderId(),articleOrders.getOrderApplyUserId(),articleOrders.getOrderApplyUserEmail(),OverallConfig.SIMPLE_EMAIL_TITLE,
                        OverallConfig.SIMPLE_EMAIL_CONTENT.replaceAll("orderNum",articleOrders.getOrderNum()),null,null,null);
                Map<String, String> sendValue = emailSendRemote.sendEmailText(articleOrdersVO.getToken(), emailVO);
                if (sendValue.get(OverallConfig.CODE).equals(OverallConfig.SUCCESS)){
                    emailState = OrderEmailStateEnum.E_SUCCESS.getDealNum();
                }else if (sendValue.get(OverallConfig.CODE).equals(OverallConfig.ERROR)){
                    emailState = OrderEmailStateEnum.E_ERROR.getDealNum();
                    emailSendError = sendValue.get(OverallConfig.MSG);
                }
            } catch (Exception e) {
                emailState = OrderEmailStateEnum.E_ERROR.getDealNum();
                emailSendError = e.getMessage();
            }
            daoArticleOrders.setEmailState(emailState);
            if (StringUtils.isNotBlank(emailSendError)){
                daoArticleOrders.setEmailFailDesc(emailSendError);
            }
            if (StringUtils.isNotBlank(orderApplyTime)){
                daoArticleOrders.setOrderEfficiencyTimes(time - DateUtil.parseDateTime(orderApplyTime).getTime());
            }
        }
        //未找到全文
        if (flag == OrderDealFlagEnum.D_FULLTEXTNOTFOUND.getDealNum()){
            daoArticleOrders.setOrderState(OrderDealNumWithStateNumEnum.getDealNumByDealsFlag(flag));
        }
        //馆际推送
        if (flag == OrderDealFlagEnum.D_CLOUDPUSH.getDealNum()){
            daoArticleOrders.setOrderState(OrderTypeEnum.O_APPLYING.getDealNum());
            daoArticleOrders.setOrderDealType(OrderDealNumWithStateNumEnum.getDealNumByDealsFlag(flag));
        }
        //馆际撤回
        if (flag == OrderDealFlagEnum.D_CLOUDBACK.getDealNum()){
            daoArticleOrders.setOrderState(OrderTypeEnum.O_DEALING.getDealNum());
            daoArticleOrders.setOrderDealType(OrderDealNumWithStateNumEnum.getDealNumByDealsFlag(flag));
        }
        daoArticleOrders.setOrderFinalActionTime(DateUtil.formatCurrentDateTime(time));
        this.updateOrderById(daoArticleOrders);
        //订单流程记录
        ArticleOrdersFlowDetails flowDetails = new ArticleOrdersFlowDetails();
        flowDetails.setFlowDetailId(Tools.getUUID());
        flowDetails.setOrderId(articleOrdersVO.getOrderId());
        flowDetails.setOrderAcceptAdminId(user.getEllaUserId());
        flowDetails.setOrderAcceptAdminName(user.getUserName());
        flowDetails.setFlowDetailDealSign(OrderDealNumWithStateNumEnum.getDealNumByDealsFlag(flag));
        flowDetails.setFlowDetailDealNote(OrderTypeEnum.getCodeByNum(flowDetails.getFlowDetailDealSign()));
        flowDetails.setFlowDetailCreateTime(DateUtil.formatCurrentDateTime(time));
        articleOrdersFlowDetailsService.addOrderFlowDetailsApply(flowDetails);
    }

    @Override
    @Transactional
    public void updateOrderById(ArticleOrders daoArticleOrders) {
        baseMapper.updateById(daoArticleOrders);
    }

    @Override
    public void updateOrderEmailStateForSend(EmailSendVO emailSendVO)throws Exception {
        //获取订单信息
        ArticleOrders articleOrders = this.getOrderById(emailSendVO.getOrderId());
        if (articleOrders != null){
            //获取全文记录信息
            FullTextUploadFlow fullTextUploadFlow = fullTextUploadFlowService.getFullTextUploadFlowByAbstractId(articleOrders.getAbstractId());
            //判断是否有全文上传记录
            if (fullTextUploadFlow!=null){
                //获取全文文件
                File file = new File(fullTextUploadFlow.getUploadFileUrl());
                //判断全文是否存在，全文存在，执行邮件发送业务
                if (file.exists()){
                    //获取当前发送邮件用户的ellaId
                    String userEllaId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(emailSendVO.getToken()));
                    emailSendVO.setOrderAcceptAdminId(userEllaId);
                    //判断是否是更换了邮件接收的邮箱账号
                    //未换了邮箱
                    if (StringUtils.isBlank(emailSendVO.getOrderApplyUserEmail())){
                        emailSendVO.setOrderApplyUserEmail(articleOrders.getOrderApplyUserEmail());
                    }
                    if (StringUtils.isNotBlank(emailSendVO.getOrderApplyUserEmail())){
                        String abstractAuthors = "未知";
                        Map<String, Object> map = foreignRemote.getAbstractSolrDataByUniqueId(articleOrders.getAbstractId());
                        if (map.get(OverallConfig.CODE).toString().equals(OverallConfig.SUCCESS)){
                            Map abstractMap = JSON.parseObject(JSON.toJSONString(map.get(OverallConfig.DATA)),HashMap.class);
                            if (abstractMap.containsKey("authorsString")){
                                abstractAuthors = abstractMap.get("authorsString").toString();
                            }
                        }
                        String orderId = articleOrders.getOrderId();
                        String orderNum = articleOrders.getOrderNum();
                        String ellaUserId = articleOrders.getOrderApplyUserId();
                        String userEmail = emailSendVO.getOrderApplyUserEmail();
                        String abstractTitle = articleOrders.getArticleTitle();
                        String journalName = articleOrders.getArticleSource();
                        String emailTile = OverallConfig.ATTACH_EMAIL_TITLE.replaceAll("#ORDERNUM#",orderNum);
                        String emailContent = OverallConfig.ATTACH_EMAIL_CONTENT.replaceAll("#USEREMAIL#",userEmail)
                                .replaceAll("#ABSTRACTNAME#",abstractTitle).replaceAll("#AUTHOR#",abstractAuthors)
                                .replaceAll("#JOURNALNAME#",journalName);
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[fileInputStream.available()];
                        fileInputStream.read(bytes);
                        String uploadUrl = fullTextUploadFlow.getUploadFileUrl();
                        String attachName = uploadUrl.substring(uploadUrl.lastIndexOf(File.separator) + 1);
                        //执行邮件发送业务
                        List<SystemEmail> list = systemEmailService.findAllSystemEmailDataList();
                        EmailVO emailVO = SystemEmailUtil.getEmailVO(list,orderId,ellaUserId,userEmail, emailTile, emailContent, bytes, attachName, null);
                        //邮件发送任务
                        EmailSendRunnable emailSendRunnable = new EmailSendRunnable(this,articleOrdersFlowDetailsService,emailSendRemote,articleOrders,emailVO,emailSendVO.getToken());
                        ExecutorService emailSendThreadPool = Executors.newFixedThreadPool(1);
                        emailSendThreadPool.execute(emailSendRunnable);
                        emailSendThreadPool.shutdown();
                    }else {
                        throw new RuntimeException("必要参数不能为空");
                    }
                }else {
                    throw new RuntimeException("附件全文不存在，请重新上传全文后再发送邮件");
                }
            }else {
                throw new RuntimeException("附件全文记录不存在，请先上传全文后再发送邮件");
            }
        }else {
            throw new RuntimeException("订单数据不存在，请刷新后重试...");
        }
    }

    @Override
    public void getOrderLogesPage(Page page, OrderLogsVO orderLogsVO) {
        List<ArticleOrders> list = baseMapper.selectOrderLogesPage(page,orderLogsVO);
        page.setRecords(list);
    }

    @Override
    public List<ArticleOrders> exportOrdersLogsList(OrderLogsVO orderLogsVO) {
        return baseMapper.selectOrderLogesPage(orderLogsVO);
    }

    @Override
    public void remoteEmailSend(String token, String orderId) throws Exception{
        //获取订单信息
        ArticleOrders articleOrders = this.getOrderById(orderId);
        if (articleOrders != null){
            //获取全文记录信息
            FullTextUploadFlow fullTextUploadFlow = fullTextUploadFlowService.getFullTextUploadFlowByAbstractId(articleOrders.getAbstractId());
            if (fullTextUploadFlow!=null){
                //获取全文文件
                File file = new File(fullTextUploadFlow.getUploadFileUrl());
                //判断全文是否存在，全文存在，执行邮件发送业务
                if (file.exists()){
                    if (StringUtils.isNotBlank(articleOrders.getOrderApplyUserEmail())){
                        String abstractAuthors = "未知";
                        Map<String, Object> map = foreignRemote.getAbstractSolrDataByUniqueId(articleOrders.getAbstractId());
                        if (map.get(OverallConfig.CODE).toString().equals(OverallConfig.SUCCESS)){
                            Map abstractMap = JSON.parseObject(JSON.toJSONString(map.get(OverallConfig.DATA)),HashMap.class);
                            if (abstractMap.containsKey("authorsString")){
                                abstractAuthors = abstractMap.get("authorsString").toString();
                            }
                        }
                        String orderNum = articleOrders.getOrderNum();
                        String ellaUserId = articleOrders.getOrderApplyUserId();
                        String userEmail = articleOrders.getOrderApplyUserEmail();
                        String abstractTitle = articleOrders.getArticleTitle();
                        String journalName = articleOrders.getArticleSource();
                        String emailTile = OverallConfig.ATTACH_EMAIL_TITLE.replaceAll("#ORDERNUM#",orderNum);
                        String emailContent = OverallConfig.ATTACH_EMAIL_CONTENT.replaceAll("#USEREMAIL#",userEmail)
                                .replaceAll("#ABSTRACTNAME#",abstractTitle).replaceAll("#AUTHOR#",abstractAuthors)
                                .replaceAll("#JOURNALNAME#",journalName);
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[fileInputStream.available()];
                        fileInputStream.read(bytes);
                        String uploadUrl = fullTextUploadFlow.getUploadFileUrl();
                        String attachName = uploadUrl.substring(uploadUrl.lastIndexOf(File.separator) + 1);
                        //执行邮件发送业务
                        List<SystemEmail> list = systemEmailService.findAllSystemEmailDataList();
                        EmailVO emailVO = SystemEmailUtil.getEmailVO(list,orderId,ellaUserId,userEmail, emailTile, emailContent, bytes, attachName, null);
                        //邮件发送任务
                        EmailSendRunnable emailSendRunnable = new EmailSendRunnable(this,articleOrdersFlowDetailsService,emailSendRemote,articleOrders,emailVO,token);
                        ExecutorService emailSendThreadPool = Executors.newFixedThreadPool(1);
                        emailSendThreadPool.execute(emailSendRunnable);
                        emailSendThreadPool.shutdown();
                    }else {
                        throw new RuntimeException("必要参数不能为空");
                    }
                }else {
                    throw new RuntimeException("附件全文不存在，请重新上传全文后再发送邮件");
                }
            }
        }
    }
}
