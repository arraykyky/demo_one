package com.springcloud.book.foreign.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.HttpClientService;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.ArticleOrders;
import com.springcloud.book.foreign.dao.ArticleOrdersDao;
import com.springcloud.book.foreign.domain.ArticleOrdersFlowDetails;
import com.springcloud.book.foreign.domain.FullTextUploadFlow;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.BookStoreMyRequestOrdersVO;
import com.springcloud.book.foreign.domain.vo.OrderApplyVO;
import com.springcloud.book.foreign.domain.vo.UserVO;
import com.springcloud.book.foreign.enums.ArticleOrdersTypeEnum;
import com.springcloud.book.foreign.enums.OrderTypeEnum;
import com.springcloud.book.foreign.remote.ManagementEmailSendRemote;
import com.springcloud.book.foreign.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.foreign.service.impl.deals.AbstrctDateDeal;
import com.springcloud.book.foreign.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-16
 */
@Service
public class ArticleOrdersServiceImpl extends ServiceImpl<ArticleOrdersDao, ArticleOrders> implements ArticleOrdersService {

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpClientService clientService;

    @Autowired
    private SolrSearchService solrSearchService;

    @Autowired
    private FullTextUploadFlowService fullTextUploadFlowService;

    @Autowired
    private ArticleOrdersFlowDetailsService articleOrdersFlowDetailsService;

    @Autowired
    private ManagementEmailSendRemote managementEmailSendRemote;

    @Override
    public void getCurrentUserRequestOrders(Page page, BookStoreMyRequestOrdersVO myRequestOrdersVO) throws Exception{
        //从redis中获取用户的id
        String loginUserId = redisServer.get(Constants.getUserModuleCacheKeyId(myRequestOrdersVO.getToken())).toString();
        String ordertype = ArticleOrdersTypeEnum.getTypeValueByKey(myRequestOrdersVO.getOrderType());
        ArticleOrders articleOrders = new ArticleOrders();
        articleOrders.setOrderState(ordertype);
        articleOrders.setOrderApplyUserId(loginUserId);
        List<ArticleOrders> list = baseMapper.selectMyOrderList(page,articleOrders);
        page.setRecords(list);
    }

    @Override
    public ArticleOrders GetMyCurrentOrdersRequestInfo(BookStoreMyRequestOrdersVO requestOrdersVO) throws Exception{
        return baseMapper.selectById(requestOrdersVO);
    }

    @Override
    public void getHospitalOrderPage(Page page, UserVO userVO) throws Exception{
        List<PageData> list = baseMapper.selectHospitalOrderList(page,userVO);
        page.setRecords(list);
    }

    @Override
    public void getHospitalOrderReportPage(Page page, UserVO userVO) throws Exception{
        List<PageData> list = baseMapper.selectHospitalOrderReportList(page,userVO);
        page.setRecords(list);
    }

    @Override
    @Transactional
    public void addOrderApply(String token, OrderApplyVO orderApplyVO) throws Exception{
        if (orderApplyVO.checkOrderApplyVOFieldIsNotEmpty(orderApplyVO)){
            //获取用户信息
            User user = userService.getById(orderApplyVO.getUserId());
            Map<String,String> map = new HashMap<>();
            map.put("id",user.getHospitalId());
            orderApplyVO.setOrderApplyUserId(user.getEllaUserId());
            orderApplyVO.setOrderApplyUserName(StringUtils.isNotBlank(user.getUserName()) ? user.getUserName() : "");
            String unitName = null;
            try {
                unitName = clientService.doGet(OverallConfig.UNIT_NAME_BY_LONG_ID, map);
            }catch (Exception e){
                unitName = (String) redisServer.get(Constants.getUserModuleCacheKeyHospitalName(token));
            }
            orderApplyVO.setOrderApplyUserUnit(StringUtils.isNotBlank(unitName) ? unitName : "");
            orderApplyVO.setOrderApplyUserPhone(StringUtils.isNotBlank(user.getUserPhone()) ? user.getUserPhone() : "");
            orderApplyVO.setOrderDealType(OrderTypeEnum.F_RECALL.getDealNum());
            //文档信息
            //添加订单信息
            Long orderNum = System.currentTimeMillis();
            String orderId = Tools.getUUID();
            Map<String, Object> abstractMap = solrSearchService.searchSolrBeanMapByUniqueId("abstract_id", orderApplyVO.getAbstractId(), OverallConfig.ABSTRACT_CORE);
            AbstractSolr bean = JSON.parseObject(JSON.toJSONString(abstractMap), AbstractSolr.class);
            AbstrctDateDeal.applyOrderDealWithSolr(orderApplyVO,bean,orderId,orderNum);
            //邮件是否发送业务(原文存在，则直接发送邮件，并标记order_handle_type = -1)
            FullTextUploadFlow fullTextInfo = fullTextUploadFlowService.findFullTextInfoByAbstractId(orderApplyVO.getAbstractId());
            if (fullTextInfo != null){
                orderApplyVO.setOrderHandleType(-1);
            }else {
                orderApplyVO.setOrderHandleType(1);
            }
            this.insertOrderApply(orderApplyVO);
            //添加流程信息
            ArticleOrdersFlowDetails flowDetails = new ArticleOrdersFlowDetails();
            flowDetails.setOrderId(orderApplyVO.getOrderId());
            AbstrctDateDeal.applyOrderFlowDeal(flowDetails,orderNum);
            articleOrdersFlowDetailsService.addOrderFlowDetailsApply(flowDetails);
            if (fullTextInfo != null){
                //开启线程发送邮件
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        managementEmailSendRemote.sendEmail(token,orderId);
                    }
                };
                thread.start();
            }
        }else {
            new RuntimeException("参数不完整");
        }
    }

    @Override
    public void insertOrderApply(OrderApplyVO orderApplyVO) {
        baseMapper.insertData(orderApplyVO);
    }

    @Override
    public ArticleOrders findOrderInfoByAbstractId(String abstractId, String orderApplyUserEmail) {
        ArticleOrders articleOrders = new ArticleOrders();
        articleOrders.setAbstractId(abstractId);
        articleOrders.setOrderApplyUserEmail(orderApplyUserEmail);
        return baseMapper.selectOne(articleOrders);
    }
}
