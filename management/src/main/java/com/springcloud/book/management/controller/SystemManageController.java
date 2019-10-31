package com.springcloud.book.management.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.Constants;
import com.springcloud.book.management.config.HttpClientService;
import com.springcloud.book.management.config.OverallConfig;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.config.redis.RedisServer;
import com.springcloud.book.management.domain.*;
import com.springcloud.book.management.domain.vo.*;
import com.springcloud.book.management.enums.*;
import com.springcloud.book.management.remote.ForeignRemote;
import com.springcloud.book.management.service.*;
import com.springcloud.book.management.util.DateUtil;
import com.springcloud.book.management.util.ExcelUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/system")
public class SystemManageController {

    Logger logger = LoggerFactory.getLogger(SystemManageController.class);

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleOrdersService articleOrdersService;

    @Autowired
    private OrderUserViewService orderUserViewService;

    @Autowired
    private ForeignRemote foreignRemote;

    @Autowired
    private FullTextUploadFlowService fullTextUploadFlowService;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private JournalNavigationMenuService journalNavigationMenuService;

    @Autowired
    private SystemEmailService systemEmailService;

    @Autowired
    private HospitalNameAbbreviationInfoService hospitalNameAbbreviationInfoService;

    @Autowired
    private SolrStatisticsService solrStatisticsService;

    @Autowired
    private RecordAbstractRootPathService recordAbstractRootPathService;

    @Autowired
    private JournalService journalService;

    /**
     * 获取管理员菜单列表
     * @return
     */
    @GetMapping("/menu")
    public Object indexMenu(String token){
        PageData pageData = new PageData();
        try {
            String userType = (String) redisServer.get(Constants.getUserModuleCacheKeyUserType(token));
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(token));
            pageData.put("id",ellaUserId);
            pageData.put("order","4");
            String value = httpClientService.doPost(OverallConfig.MENU_URL, pageData);
            HashMap map = JSON.parseObject(value, HashMap.class);
            List<Map> lisMap = (List<Map>) map.get("menus");
            Map dataMap = new HashMap();
            dataMap.put("menus",lisMap);
            dataMap.put("userType",userType);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,dataMap);
        } catch (Exception e) {
            e.printStackTrace();
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
        }
        return pageData;
    }

    /**
     * 获取当前管理员管理的用户
     * @param page
     * @param userVO 用户VO 包含必要信息 userType -- 用户类型: 1超级管理员；2院内管理员；3普通用户
     *                                    hospitalId -- 医院id
     *                                    userName -- 用户名，用户检索时的检索参数
     * @return
     */
    @GetMapping("/user_page")
    public Object getManageUser(Page page, UserVO userVO){
        PageData pageData = new PageData();
        try {
            String userTypeId = (String)redisServer.get(Constants.getUserModuleCacheKeyUserType(userVO.getToken()));
            String hospittalId = (String) redisServer.get(Constants.getUserModuleCacheKeyHospitalId(userVO.getToken()));
            userVO.setUserType(userTypeId);
            userVO.setHospitalId(hospittalId);
            userService.findUserPage(page,userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 通过过用户id获取用户详情
     * @param userVO 用户VO 包含必要信息 userId -- 用户id
     * @return
     */
    @GetMapping("user_info")
    public Object showUserInfo(UserVO userVO){
        PageData pageData = new PageData();
        try {
            User user = userService.getUserById(userVO.getUserId());
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,user);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 用户“借读点”分配
     * @param userVO 用户VO 包含必要信息 userId -- 用户id
     *                                     allotPoint -- 分配点数
     * @return
     */
    @PostMapping("/user_point_allot")
    public Object allotCenter(UserVO userVO){
        PageData pageData = new PageData();
        try {
            //当前管理员分配解读点给用户
            //1.判断管理员剩余可用点是否够分配
            boolean able = userService.isAbleAllot(userVO);
            if (able){
                userVO.setAllotType(PointAllotTypeEnum.P_ALLOT.getDealNum());
                userService.updateUserEnablePointNumberAllot(userVO);
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                pageData.put(OverallConfig.DATA,true);
            }else {
                throw new RuntimeException("管理员剩余可用点数不足");
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 用户“解读点”充值
     * @param userVO 用户VO 包含必要信息 userId -- 用户id
     *                                    allotPoint -- 分配点数
     * @return
     */
    @PostMapping("/user_point_voucher")
    public Object voucherCenter(UserVO userVO){
        PageData pageData = new PageData();
        try {
            userService.updateUserEnablePointNumberVoucher(userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * * 修改获取订单管理列表(本地/馆际具角色获取已接受和申请中的订单列表)
     * @param page
     * @param articleOrdersVO 订单VO 包含必要参数
     *                                               ***************************
     *                                               userRoleCode -- 管理员角色编码(超级管理员CJZGLY;本地管理员LOCALGLY;馆际互借管理员CLOUD)
     *                                               orderState -- 订单状态(0010被拒绝;0000申请中，默认值;0001处理中;0011已完成;00000未找到全文)
     *                                               emailState -- 邮件状态(0001发送失败;0000未发送，默认值;1111发送成功)
     *                                               orderHandleType -- 订单处理方式(客服手动发送邮件1//系统定时发送的-1)
     *                                               **************检索参数*************
     *                                               orderApplyUserName -- 订单申请人名称
     *                                               orderApplyUserPhone -- 订单申请人电话
     *                                               orderApplyUserEmail -- 订单申请人邮箱
     *                                               orderApplyTime -- 订单申请时间
     *                                               articleTitle -- 订单申请文献标题
     * @return
     */
    @GetMapping("/order_page")
    public Object getOrdersPage(Page page, ArticleOrdersVO articleOrdersVO){
        PageData pageData = new PageData();
        try {
            articleOrdersService.getOrdersPage(page,articleOrdersVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 订单详情
     * @param articleOrdersVO 订单VO 包含必要参数
     *                                                  orderId     处理订单的主键id
     * @return
     */
    @GetMapping("/order_info")
    public Object getOrderInfo(ArticleOrdersVO articleOrdersVO) throws Exception {
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(articleOrdersVO.getToken()) && StringUtils.isNotBlank(articleOrdersVO.getOrderId())){
                String roleCode = (String) redisServer.get(Constants.getUserModuleCacheUserRoleCode(articleOrdersVO.getToken()));
                String loginUserEllaId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(articleOrdersVO.getToken()));
                ArticleOrders articleOrder = articleOrdersService.getOrderById(articleOrdersVO.getOrderId());
                Integer handType = articleOrder.getOrderHandleType();
                String orderState = articleOrder.getOrderState();
                String orderUserEllaId = articleOrder.getOrderAcceptAdminId();
                //判断订单不是申请状态和未找到全文状态时，判断当前订单是否是当前登录管理员接收的订单
                if (!RoleCodeKeyEnum.R_SUPER.getCode().equals(roleCode) && handType != OrderHandleTypeEnum.O_SYSTEM.getDealNum() &&
                        !OrderTypeEnum.O_APPLYING.getDealNum().equals(orderState) && !OrderTypeEnum.O_NOTEXT.getDealNum().equals(orderState)){
                    if (!loginUserEllaId.equals(orderUserEllaId)){
                        throw new RuntimeException("订单已被其他管理员接收,继续处理其他订单，请点击“是”返回订单列表");
                    }
                }
                Map<String, Object> abstractMap = new HashMap<>();
                Map<String, Object> map = foreignRemote.getAbstractSolrDataByUniqueId(articleOrder.getAbstractId());
                if (map.get(OverallConfig.CODE).toString().equals(OverallConfig.SUCCESS)){
                    abstractMap.putAll(JSON.parseObject(JSON.toJSONString(map.get(OverallConfig.DATA)),HashMap.class));
                }
                OrdersWithAbstractSolrInfoVO ordersWithAbstractSolrInfoVO = new OrdersWithAbstractSolrInfoVO();
                ordersWithAbstractSolrInfoVO.getOrdersWithAbstractSolrInfoVO(ordersWithAbstractSolrInfoVO,articleOrder,abstractMap);
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                pageData.put(OverallConfig.DATA,ordersWithAbstractSolrInfoVO);
            }else {
                throw new RuntimeException("必要参数不能为空!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 订单系列操作
     * @param articleOrdersVO 订单VO 包含必要参数
     *                                                  orderId     处理订单的主键id
     *                                                  dealFlag    处理标记:接受(处理中)1/拒绝-1/馆际推送2/馆际撤回0/未找到原文-2(订单重新恢复为申请状态)
     * @return
     */
    @PostMapping("/order_deal")
    public Object dealsOrder(ArticleOrdersVO articleOrdersVO) throws Exception {
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(articleOrdersVO.getToken()) && StringUtils.isNotBlank(articleOrdersVO.getOrderId()) && articleOrdersVO.getDealFlag() != null){
                //防止管理员处理订单时，迟迟没有接收订单，而该订单被其他管理员接收后，重复接收现象
                if (OrderDealFlagEnum.D_ACCEPT.getDealNum().equals(articleOrdersVO.getDealFlag())){
                    ArticleOrders articleOrders = articleOrdersService.getOrderById(articleOrdersVO.getOrderId());
                    String orderState = articleOrders.getOrderState();
                    if (!OrderTypeEnum.O_APPLYING.getDealNum().equals(orderState) && !OrderTypeEnum.O_NOTEXT.getDealNum().equals(orderState)){
                        throw new RuntimeException("订单已被其他管理员接收,继续处理其他订单，请点击“是”返回订单列表");
                    }
                }
                articleOrdersService.dealOrderById(articleOrdersVO);
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                pageData.put(OverallConfig.DATA,true);
            }else {
                throw new RuntimeException("必要参数不能为空!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }


    /**
     * 上传文献全文
     * 如果文献已存在，会覆盖原来的全文文件
     * @param fullTextVO 全文上传VO 包含必要字段 file -- 文件
     *                                             abstractId -- 文献id
     * @return
     */
    @PostMapping("/full_text")
    public Object uploadFullText(FullTextVO fullTextVO) {
        PageData pageData = new PageData();
        try {
            if (fullTextVO.checkFullTextVONecessaryFieldIsNotEmpty(fullTextVO)){
                fullTextUploadFlowService.uploadFullText(fullTextVO);
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                pageData.put(OverallConfig.DATA,true);
            }else {
                throw new RuntimeException("必要参数不能为空");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 查看全文
     * @param abstractId 文献id
     * @return
     */
    @GetMapping(value = "/full_look")
    public Object lookFullText(String token,String abstractId){
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(abstractId)){
               FullTextUploadFlow fullTextUploadFlow =  fullTextUploadFlowService.getFullTextUploadFlowByAbstractId(abstractId);
               if (fullTextUploadFlow!=null){
                   String uploadUrl = fullTextUploadFlow.getUploadFileUrl();
                   String textFullUrl = fullTextUploadFlow.getFilePathUrl();
                   String fileName = uploadUrl.substring(uploadUrl.lastIndexOf(File.separator) + 1);

                   LookFullTextVO lookFullTextVO = new LookFullTextVO();
                   lookFullTextVO.setFileName(fileName);
                   if (StringUtils.isNotBlank(textFullUrl)){
                       lookFullTextVO.setFileUrl(textFullUrl);
                   }else {
                       lookFullTextVO.setFileUrl(OverallConfig.TEXT_FULL + File.separator + fileName);
                   }
                   pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                   pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                   pageData.put(OverallConfig.DATA,lookFullTextVO);
               }else{
                   pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                   pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                   pageData.put(OverallConfig.DATA,null);
               }
            }else {
                throw new RuntimeException("必要参数不能为空");
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 发送邮件
     * @param emailSendVO 邮件发送VO  包含必要参数 orderId  处理订单的主键id
     *                                               orderApplyUserEmail 修改接收邮件的邮箱
     * @return
     */
    @PostMapping("/email_send")
    public Object sendEmail(EmailSendVO emailSendVO){
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(emailSendVO.getToken()) && StringUtils.isNotBlank(emailSendVO.getOrderId())){
                articleOrdersService.updateOrderEmailStateForSend(emailSendVO);
            }else {
             throw new RuntimeException("必要参数不能为空");
            }
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 订单日志
     * @param page
     * @param orderLogsVO 订单日志VO  包含必要参数 articleTitle 标题；orderApplyUserEmail 邮箱；orderApplyUserUnit 机构
     *                                                startTime 开始时间;endTime 截至时间;userType 管理员类型(1超管/2院内管理)
     * @return
     */
    @RequestMapping("/order_logs")
    public Object getOrderLogForAdminById(Page page,OrderLogsVO orderLogsVO){
        PageData pageData = new PageData();
        try {
            if (orderLogsVO.getStartTime() != null && !"".equals(orderLogsVO.getStartTime())){
                String startTime = DateUtil.formatDateTime(DateUtil.getStartOfDay(DateUtil.parse(orderLogsVO.getStartTime(),"yyyy-MM-dd")));
                orderLogsVO.setStartTime(startTime);
                if (orderLogsVO.getEndTime() !=null && !"".equals(orderLogsVO.getEndTime())){
                    String endTime = DateUtil.formatDateTime(DateUtil.getStartOfDay(DateUtil.parse(orderLogsVO.getEndTime(),"yyyy-MM-dd")));
                    orderLogsVO.setEndTime(endTime);
                }else {
                    orderLogsVO.setEndTime(DateUtil.formatDateTime(new Date()));
                }
            }
            String userType = (String) redisServer.get(Constants.getUserModuleCacheKeyUserType(orderLogsVO.getToken()));
            orderLogsVO.setUserType(userType);
            articleOrdersService.getOrderLogesPage(page,orderLogsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 订单日志导出
     * @param orderLogsVO 订单日志VO  包含必要参数 articleTitle 标题；orderApplyUserEmail 邮箱；orderApplyUserUnit 机构
     *                                                startTime 开始时间;endTime 截至时间;userType 管理员类型(1超管/2院内管理)
     * @return
     * @throws Exception
     */
    @GetMapping("/order_logs_export")
    public Object exportLogOrders(HttpServletResponse response, OrderLogsVO orderLogsVO)throws Exception{
        PageData pageData = new PageData();
        if (orderLogsVO.getStartTime() != null && !"".equals(orderLogsVO.getStartTime())){
            String startTime = DateUtil.formatDateTime(DateUtil.getStartOfDay(DateUtil.parse(orderLogsVO.getStartTime(),"yyyy-MM-dd")));
            orderLogsVO.setStartTime(startTime);
            if (orderLogsVO.getEndTime() !=null && !"".equals(orderLogsVO.getEndTime())){
                String endTime = DateUtil.formatDateTime(DateUtil.getStartOfDay(DateUtil.parse(orderLogsVO.getEndTime(),"yyyy-MM-dd")));
                orderLogsVO.setEndTime(endTime);
            }else {
                orderLogsVO.setEndTime(DateUtil.formatDateTime(new Date()));
            }
        }
        String userType = (String) redisServer.get(Constants.getUserModuleCacheKeyUserType(orderLogsVO.getToken()));
        orderLogsVO.setUserType(userType);
        List<ArticleOrders> totalList = articleOrdersService.exportOrdersLogsList(orderLogsVO);
        if (!totalList.isEmpty()){
            ExcelType excelType = ExcelType.valueOf("logOrders");
            Map<String, String> enName = excelType.enName();
            Set<Map.Entry<String, String>> entries = enName.entrySet();
            XSSFWorkbook xb = new XSSFWorkbook();
            //创建Excel的工作sheet，对应到一个excel文档的tab
            XSSFSheet sheet = xb.createSheet();
            //创建第一行，标题行
            XSSFRow titleRow = sheet.createRow(0);
            //excel标题列控制符
            int i = 0;
            //遍历列数据，生成标题行信息
            for (Map.Entry<String,String> entry : entries){
                XSSFCell titleCell = titleRow.createCell(i);
                titleCell.setCellValue(entry.getValue());
                i++;
            }
            //数据行的行控制符
            int j = 1;
            for (ArticleOrders abstractSolr : totalList){
                //获取期刊名称
                String journalName = "暂无数据";
                Map<String, Object> map = foreignRemote.getAbstractSolrDataByUniqueId(abstractSolr.getAbstractId());
                if (map.get(OverallConfig.CODE).toString().equals(OverallConfig.SUCCESS)){
                    Map abstractMap = JSON.parseObject(JSON.toJSONString(map.get(OverallConfig.DATA)),HashMap.class);
                    if (abstractMap.containsKey("journalTitleMain")){
                        journalName = abstractMap.get("journalTitleMain").toString();
                    }
                }
                LogOrdersExportVO loe = new LogOrdersExportVO();
                loe.orderToThis(abstractSolr,j,journalName);
                //循环创建新行
                XSSFRow dataRow = sheet.createRow(j);
                //数据行的列控制符
                int k = 0;
                for (Map.Entry<String,String> entry : entries){
                    try {
                        //创建数据行第j行的第k列
                        XSSFCell dataCell = dataRow.createCell(k);
                        Class<?> type = PropertyUtils.getPropertyType(loe, entry.getKey());
                        if (Integer.class.equals(type)){
                            dataCell.setCellValue(Integer.valueOf(String.valueOf(PropertyUtils.getProperty(loe,entry.getKey()))));
                        }else if (Date.class.equals(type)){
                            Date date = (Date) PropertyUtils.getProperty(loe, entry.getKey());
                            String year = DateUtil.format(date, "yyyy");
                            dataCell.setCellValue(year);
                        }else {
                            String str = String.valueOf(PropertyUtils.getProperty(loe,entry.getKey()));
                            if (str.length() > SpreadsheetVersion.EXCEL2007.getMaxTextLength()) {
                                str = str.substring(0,SpreadsheetVersion.EXCEL2007.getMaxTextLength() - 10) + "......";
                            }
                            dataCell.setCellValue(str);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        k++;
                    }
                }
                j++;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            xb.write(baos);
            byte[] bytes = baos.toByteArray();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=abstracts.xlsx");
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            return null;
        }else {
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,"没有可导出数据");
        }
        return pageData;
    }


//    /**
//     * 订单数据导出
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/export_order_analysis")
//    public Object exportOrderAnalysisData(HttpServletResponse response)throws Exception{
//        Map<String,Object> pageData = new HashMap<>();
//        List<OrderUserView> list = orderUserViewService.findAllOrderAnalysisPageData(pageData);
//        if (!list.isEmpty()){
//            ExcelUtil excelUtil = new ExcelUtil();
//            excelUtil.export("orderview",list,response);
//        }else {
//            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
//            pageData.put(OverallConfig.MSG,"没有可导出数据");
//        }
//       return null;
//    }


    /**
     * 订单分析--管理员数据
     * @return
     */
    @GetMapping("/order_admin")
    public Object getOrderAnalysisAdminMenu(){
        PageData pageData = new PageData();
        try{
            List<User> list = userService.getAdminUserList();
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 订单分析--列表数据
     * @param orderUserViewVO 订单分析VO 包含(选择参数) start申请时间开始值 end申请时间结束值
     *                                                   orderAcceptAdminId接受订单管理员id(用于管理员分析中某个人的订单数据)
     * @param page
     * @return
     */
    @GetMapping("/order_analysis")
    public Object getOrderAnalysisPage(Page page,OrderUserViewVO orderUserViewVO){
        PageData pageData = new PageData();
        try {
            orderUserViewService.getOrderAnalysisPage(page,orderUserViewVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 管理员处理订单分析
     * @param page
     * @param orderUserViewVO 管理员处理订单分析VO 包含(选择参数) start 申请时间开始值 end申请时间结束值
     *                                                               timeSlot时间段值(单位：毫秒)
     * @return
     */
    @GetMapping("/admin_analysis")
    public Object getAdminOrderAnalysisPage(Page page,OrderUserViewVO orderUserViewVO){
        PageData pageData = new PageData();
        try {
            orderUserViewService.getAdminOrderAnalysisPage(page,orderUserViewVO);
            AdminOrderAnalysisTotalDataVO adminOrderAnalysisTotalDataVO = orderUserViewService.getAdminOrderAnalysisTotalData(orderUserViewVO);
            Map map = new HashMap();
            map.put("page",page);
            map.put("orderMSG",adminOrderAnalysisTotalDataVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,map);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 单位订单分析
     * @param page
     * @param orderUserViewVO 管理员处理订单分析VO 包含(选择参数) start 申请时间开始值 end申请时间结束值
     *                                                               orderApplyUserUnit 单位名称
     * @return
     */
    @GetMapping("/unit_analysis")
    public Object getUnitOrderAnalysisDataPage(Page page,OrderUserViewVO orderUserViewVO){
        PageData pageData = new PageData();
        try {
            orderUserViewService.getUnitOrderAnalysisDataPage(page,orderUserViewVO);
            pageData.put(OverallConfig.DATA,page);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 获取菜单菜单子目录列表
     * @param menuParentId    父标签id
     * @return
     */
    @GetMapping("/journal_navigation_submenu")
    public Object getJournalsNavigationSubmenuByParentId(String menuParentId){
        PageData pageData = new PageData();
        try {
            List<JournalNavigationMenu> list = journalNavigationMenuService.getJournalsNavigationSubmenuByParentId(menuParentId);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,list);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 添加期刊导航菜单
     * @param journalNavigationMenu 包含必要参数 menuId   菜单id,用于更新
     *                                              menuName 菜单名
     *                                              menuParentId 父节点Id
     *                                              menuDesc 描述信息
     *                                              menuValue 菜单值
     * @return
     */
    @PostMapping("/journal_navigation_menu_add")
    public Object addJournalNavigationMenu(JournalNavigationMenu journalNavigationMenu){
        PageData pageData = new PageData();
        try {
            journalNavigationMenuService.addJournalNavigationMenu(journalNavigationMenu);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,true);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 删除期刊导航菜单
     * @param menuId    菜单id
     * @return
     */
    @PostMapping("/journal_navigation_menu_delete")
    public Object deleteJournalNavigationMenuByMenuId(String menuId){
        PageData pageData = new PageData();
        try {
            journalNavigationMenuService.deleteJournalNavigationMenu(menuId);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,true);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 获取全部系统邮箱信息
     * @param page
     * @return
     */
    @GetMapping("/system_email")
    public Object getSystemEmailPage(Page page){
        PageData pageData = new PageData();
        try{
            systemEmailService.getSystemEmailPage(page);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e);
        }
        return pageData;
    }

    /**
     * 系统邮箱添加
     * @param systemEmail 包含必要参数 email 邮箱账号
     *                                   password 邮箱密码
     *                                   port 端口号
     *                                   level 启用级别
     *                                   enabled 启用状态
     * @return
     */
    @PostMapping("/system_email_add")
    public Object addSystemEmail(SystemEmail systemEmail){
        PageData pageData = new PageData();
        try {
            systemEmailService.addSystemEmail(systemEmail);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,true);
            pageData.put(OverallConfig.MSG,"添加成功");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e);
        }
        return pageData;
    }

    //删除系统邮箱
    @PostMapping("/system_email_delete")
    public Object deleteSystemEmail(Integer id){
        PageData pageData = new PageData();
        try {
            systemEmailService.deleteSystemEmail(id);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,true);
            pageData.put(OverallConfig.MSG,"删除成功");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e);
        }
        return pageData;
    }

    /**
     * 下载医院(单位)导入模板
     * @param response
     */
    @GetMapping("/down_hospital_name_abbreviation_file")
    public void downLoadUserImportModel(HttpServletResponse response){
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=hospital_name_abbreviation.xlsx");
        byte[] bytes = new byte[1024];
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("files/hospitalNameAbbreviation.xlsx");
        try {
            ServletOutputStream outPutStream = response.getOutputStream();
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0){
                outPutStream.write(bytes,0,len);
            }
            outPutStream.flush();
            inputStream.close();
            outPutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 医院(单位)信息导入
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/hospital_name_abbreviation_import")
    public Object importHospitalNameAbbreviation(MultipartFile file) throws Exception {
        PageData pageData = new PageData();
        try {
            int addNum = 0;
            int errorNum = 0;
            List errorlist = new ArrayList();
            Map<String,String> titleValueMap = new HashMap<>();
            titleValueMap.put("单位社会统一编码号","hiAbbreviation");
            titleValueMap.put("单位名称","hiName");
            //读取文件转成集合
            List<Map<String, String>> list = ExcelUtil.readXlsToListMap(file,titleValueMap);
            for (Map<String,String> map : list){
                try {
                    HospitalNameAbbreviationInfo hospitalNameAbbreviationInfo = new HospitalNameAbbreviationInfo();
                    hospitalNameAbbreviationInfo.setHiName(map.get("hiName"));
                    hospitalNameAbbreviationInfo.setHiAbbreviation(map.get("hiAbbreviation"));
                    hospitalNameAbbreviationInfo.setHiArea(1);
                    //添加数据
                    hospitalNameAbbreviationInfoService.addHospitalNameAbbreviation(hospitalNameAbbreviationInfo);
                    addNum++;
                } catch (Exception e) {
                    errorNum++;
                    errorlist.add(map.get("hiName") + ":" + e.getMessage() + "@行号:" + (list.indexOf(map) + 2));
                }
            }
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put("MSG","共有数据" + list.size()+"行，成功添加"+addNum+"行;失败"+errorNum+"行");
            pageData.put(OverallConfig.DATA,true);
            if (errorNum > 0){
                pageData.put("ERRORDATA",errorlist);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 归集标准分页列表
     * @param page
     * @param hospitalName 查询参数 医院名称
     * @return
     */
    @GetMapping("statistics_standard")
    public Object getHospitalStatisticsStandardPage(Page page,String hospitalName){
        PageData pageData = new PageData();
        try {
            solrStatisticsService.getHospitalStatisticsStandardPage(page,hospitalName);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,page);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }
    /**
     * 获取医院的清洗类型(即医院的社会统一编码)下拉菜单
     * @return
     */
    @GetMapping("/statistics_type")
    public Object getHospitalStatisticsType(){
        PageData pageData = new PageData();
        try {
            List<HospitalNameAbbreviationInfo> list = hospitalNameAbbreviationInfoService.getHospitalStatisticsType();
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     *  添加/更新归集标准
     * @param solrStatisticsVO 归集标准VO 包含必要参数 statisticsId 归集标准id，更新标准时使用
     *                                                    statisticsType  过滤类型
     *                                                    andValue  精确过滤值,多个过滤值以“;”分割
     *                                                    orValue   模糊过滤值,多个过滤值以“;”分割
     * @return
     */
    @PostMapping("/standard_add_or_update")
    public Object addOrUpdateStatisticsStandard(SolrStatisticsVO solrStatisticsVO){
        PageData pageData = new PageData();
        try {
            solrStatisticsService.addOrUpdateStatisticsStandard(solrStatisticsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     *  删除归集标准
     * @param solrStatisticsVO 归集标准VO 包含必要参数 statisticsId 归集标准id
     * @return
     */
    @PostMapping("/standard_delete")
    public Object deleteStatisticsStandard(SolrStatisticsVO solrStatisticsVO){
        PageData pageData = new PageData();
        try {
            solrStatisticsService.deleteStatisticsStandard(solrStatisticsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 解压数据核对
     * @param unzipDataCheckVO 核对数据VO 包含查询参数 abParentDir 期刊nlmId
     * @return
     */
    @GetMapping("/unzip_check")
    public Object getUnzipDataCheckPage(Page page,UnzipDataCheckVO unzipDataCheckVO){
        PageData pageData = new PageData();
        try {
            recordAbstractRootPathService.getJournalVolumeIssueCountPage(page,unzipDataCheckVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 解压数据核对
     * @param unzipDataCheckVO 核对数据VO 包含查询参数 abParentDir 期刊nlmId
     * @return
     */
    @GetMapping("/unzip_check_export")
    public Object getUnzipDataCheckPage(UnzipDataCheckVO unzipDataCheckVO,HttpServletResponse response){
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(unzipDataCheckVO.getAbParentDir())){
                List<UnzipCheckExportVO> list = recordAbstractRootPathService.getJournalVolumeIssueCountList(unzipDataCheckVO);
                for (UnzipCheckExportVO vo : list){
                    try {
                        Map<String, Object> map = foreignRemote.getJournalSolrDataByNLMID("nlm_id", vo.getAbParentDir());
                        if (map.get(OverallConfig.CODE).equals(OverallConfig.SUCCESS)){
                            Object journal = map.get(OverallConfig.DATA);
                            if (journal != null){
                                HashMap journalMap = JSON.parseObject(JSON.toJSONString(journal), HashMap.class);
                                if (journalMap.containsKey("titleMain")){
                                    vo.setJournalName(journalMap.get("titleMain").toString());
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                    }
                }
                //文件导出
                ExcelUtil excelUtil = new ExcelUtil();
                excelUtil.export("unzipCheck",list,response,unzipDataCheckVO.getAbParentDir()+"_Check");
                return null;
            }else {
                throw new RuntimeException("数据过多，请输入导出期刊对应的NLM_ID号");
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 解压数据卷期详细核对
     * @param unzipDataCheckVO 核对数据VO 包含必要参数 abParentDir 期刊nlmId
     *                                                    abVolume    卷号
     *                                                    abIssue     期号
     * @return
     */
    @GetMapping("/journal_volume_issue_check")
    public Object getUnzipDataJournalVolumeIssueCheckPage(Page page,UnzipDataCheckVO unzipDataCheckVO){
        PageData pageData = new PageData();
        try {
            recordAbstractRootPathService.getUnzipDataJournalVolumeIssuePage(page,unzipDataCheckVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 获取期刊名对应NLM_ID菜单集合
     * @return
     */
    @GetMapping("/journal_menu")
    public Object getJournalMenuList(){
        PageData pageData = new PageData();
        try{
            List<PageData> list = journalService.getJournalMenuList();
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,list);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

}
