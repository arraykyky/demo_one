package com.springcloud.book.foreign.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.HttpClientService;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.ArticleOrders;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.domain.vo.*;
import com.springcloud.book.foreign.service.ArticleOrdersService;
import com.springcloud.book.foreign.service.CollectionJournalService;
import com.springcloud.book.foreign.service.DocumentDatabaseService;
import com.springcloud.book.foreign.service.UserService;
import com.springcloud.book.foreign.util.ExcelUtil;
import com.springcloud.book.foreign.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/bookstore")
public class MyBookStoreController extends BaseController {

    Logger logger = LoggerFactory.getLogger(MyBookStoreController.class);

    @Autowired
    private ArticleOrdersService ordersService;

    @Autowired
    private CollectionJournalService collectionJournalService;

    @Autowired
    private DocumentDatabaseService documentDatabaseService;

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private ArticleOrdersService articleOrdersService;

    @Autowired
    private UserService userService;

    /**
     * 获取用户角色，用于我的图书馆左侧菜单的显/隐
     * @param userRoleVO 用户角色VO 包含必要参数  token           用户标识
     *                                              userType;       用户类型id
     *                                              hospitalId;     用户医院id
     *                                              roleName;       角色名
     *                                              roleCode;       角色编码
     * @return
     */
    @GetMapping("/role")
    public Object getUserRoleInfo(UserRoleVO userRoleVO){
        PageData pageData = new PageData();
        try{
            userRoleVO.getRoleInfo(redisServer,userRoleVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,userRoleVO);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的馆际互借请求
     * @param page
     * @param myRequestOrdersVO 互借请求VO 包含必要参数 orderType -- 订单状态
     *                                                                  申请中:0;处理中:1;被拒绝:2;
     *                                                                  已完成:3;未找到全文-1;全部:-2
     * @return
     */
    @GetMapping("/my_order_request")
    public Object GetMyOrderRequest(Page page, BookStoreMyRequestOrdersVO myRequestOrdersVO){
        PageData pageData = new PageData();
        try {
            ordersService.getCurrentUserRequestOrders(page,myRequestOrdersVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的馆际互借请求 -- 查看详情
     * @param requestOrdersVO 互借请求VO 包含必要参数 orderId --  订单id
     * @return
     */
    @GetMapping("/order_info")
    public Object GetMyCurrentOrdersRequest(BookStoreMyRequestOrdersVO requestOrdersVO){
        PageData pageData = new PageData();
        try {
            ArticleOrders articleOrderInfo = ordersService.GetMyCurrentOrdersRequestInfo(requestOrdersVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,articleOrderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的期刊库
     * @param journalStoreVO 期刊库VO 包含必要参数 collectionUserId --  收藏人id
     * @param page
     * @return
     */
    @GetMapping("/journal_store")
    public Object getMyJournalStore(Page page, JournalStoreVO journalStoreVO){
        PageData pageData = new PageData();
        try {
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyForeignUserId(journalStoreVO.getToken()));
            journalStoreVO.setCollectionUserId(ellaUserId);
            collectionJournalService.getCurrentUserJournalStorePage(page,journalStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的期刊库 -- 删除所选文献
     * @param journalStoreVO 期刊库VO 包含必要参数 collectionIds --  以分号";"链接的收藏期刊的收藏id字符串
     *                                                //collectionIdList  -- 执行业务时的参数由collectionIds设置而来
     */
    @PostMapping("/delete_collections")
    public Object deleteMyCollection(JournalStoreVO journalStoreVO){
        PageData pageData = new PageData();
        try {
            collectionJournalService.deleteCurrentUserCollectionBySelectedIds(journalStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的文献库
     * @param documentStoreVO 我的文献库VO
     * @param page
     * @return
     */
    @RequestMapping("/document_store")
    public Object getMyDocumentStore(Page page, DocumentStoreVO documentStoreVO){
        PageData pageData = new PageData();
        try {
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(documentStoreVO.getToken()));
            documentStoreVO.setDocumentDatabaseUserId(ellaUserId);
            documentDatabaseService.getCurrentUserMyDocumentStorePage(page,documentStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的文献库 -- 新建文献库
     * @param documentStoreVO 我的文献库VO 包含必要参数 documentDatabaseShowName -- 文献库显示名
     * @return
     */
    @PostMapping("/creat_document")
    public Object createMyDocumentDatabase(DocumentStoreVO documentStoreVO){
        PageData pageData = new PageData();
        try {
            long time = new Date().getTime();
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(documentStoreVO.getToken()));
            documentStoreVO.setDocumentDatabaseUserId(ellaUserId);
            documentStoreVO.setDocumentDatabaseRealName("fd_"+ellaUserId+time);
            documentStoreVO.setDocumentDatabaseId(Tools.getUUID());
            documentDatabaseService.createCurrentUserSetUpDocumentDatabase(documentStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的文献库 -- 改名
     * @param documentStoreVO 我的文献库VO 包含必要参数 documentDatabaseShowName -- 文献库显示名
     *                                                     documentDatabaseId       -- 文献库id
     * @return
     */
    @PostMapping("/rename_document")
    public Object myDocumentDatabaseRename(DocumentStoreVO documentStoreVO){
        PageData pageData = new PageData();
        try {
            documentDatabaseService.renameMyDocumentDatabaseById(documentStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的文献库 -- 删除
     * @param documentStoreVO 我的文献库VO 包含必要参数 documentDatabaseId -- 文献库id
     * @return
     */
    @PostMapping("/delete_document")
    public Object deleteMyDocumentDatabase(DocumentStoreVO documentStoreVO){
        PageData pageData = new PageData();
        try {
            documentDatabaseService.deleteCurrentDocumentDatabaseById(documentStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的文献库 -- 查看当前文献库收藏列表
     * @param documentStoreVO 我的文献库VO 包含必要参数 documentDatabaseId -- 文献库id
     * @param page
     * @return
     */
    @GetMapping("/document_page")
    public Object getCurrentDocumentPage(Page page,DocumentStoreVO documentStoreVO){
        PageData pageData = new PageData();
        try {
            documentDatabaseService.getCurrentDocumentPage(page,documentStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 我的文献库 -- 删除所选收藏文献记录
     * @param documentStoreVO 我的文献库VO 包含必要参数 documentDatabaseId -- 文献库id
     *                                                     documentDatabaseIdsStr -- 以分号";"链接的收藏文献的文献库收藏id字符串
     * @return
     */
    @PostMapping("/delete_document_collected")
    public Object deleteDocumentCollects(DocumentStoreVO documentStoreVO){
        PageData pageData = new PageData();
        try {
            documentDatabaseService.deleteDocumentCollectedBySelectedIds(documentStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 统计报告 -- 本院申请列表
     * @param userVO 用户VO 包含必要参数 token -- 用户登录标识
     * @param page
     * @return
     */
    @GetMapping("/hospital_orders")
    public Object getHospitalOrders(Page page, UserVO userVO){
        PageData pageData = new PageData();
        try {
            String hospitalId = (String) redisServer.get(Constants.getUserModuleCacheKeyHospitalId(userVO.getToken()));
            userVO.setHospitalId(hospitalId);
            articleOrdersService.getHospitalOrderPage(page,userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 我的图书馆 ---- 统计报告 -- 统计申请数据
     * @param userVO 用户VO 包含必要参数 token -- 用户登录标识
     * @return
     */
    @GetMapping("/hospital_orders_report")
    public Object getUseReport(Page page, UserVO userVO){
        PageData pageData = new PageData();
        try{
            String hospitalId = (String) redisServer.get(Constants.getUserModuleCacheKeyHospitalId(userVO.getToken()));
            userVO.setHospitalId(hospitalId);
            ordersService.getHospitalOrderReportPage(page,userVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,page);
        }catch (Exception e){
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 下载用户导入模板
     * @param response
     */
    @GetMapping("/down_user_import_file")
    public void downLoadUserImportModel(HttpServletResponse response){
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=user_import_model.xlsx");
        byte[] bytes = new byte[1024];
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("files/userImportModel.xlsx");
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

        }
    }

    /**
     * 通过用户模板导入批量用户
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/import_users")
    public Object importUsersByModel(MultipartFile file,UserVO userVO) throws Exception {
        PageData pageData = new PageData();
        try {
            int addNum = 0;
            int errorNum = 0;
            List errorlist = new ArrayList();
            Map<String,String> titleValueMap = new HashMap<>();
            titleValueMap.put("手机号","telephone");
            titleValueMap.put("姓名","realName");
            titleValueMap.put("密码","password");
            //读取文件转成集合
            List<Map<String, String>> list = ExcelUtil.readXlsToListMap(file,titleValueMap);
            //获取院内管理员信息
            String ellaUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(userVO.getToken()));
            userVO.setEllaUserId(ellaUserId);
            User user = userService.getUserByEllaUserId(userVO);
            for (Map<String,String> map : list){
                map.put("loginName",map.get("telephone"));
                map.put("userTypeId","3");
                map.put("roleId","9");
                map.put("hospitalId",user.getHospitalId());
                map.put("orgId",user.getDeptId());
                map.put("showEndTime",user.getOutTime());
                //调用ella用户添加接口
                String isAdded = httpClientService.doPost(OverallConfig.ADD_USERS_BY_FILE, map);
                Map<String,String> msgMap = JSON.parseObject(isAdded, HashMap.class);
                String code = msgMap.get("code");
                String msg = msgMap.get("msg");
                if (code.equals("8001")){
                    addNum++;
                }else if(code.equals("4001")){
                    errorNum++;
                    errorlist.add(map.get("telephone") + ":" + msg + "@行号:" + (list.indexOf(map) + 2));
                }
            }
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put("MSG","共有数据" + list.size()+"行，成功添加"+addNum+"行;失败"+errorNum+"行");
            pageData.put(OverallConfig.DATA,true);
            if (errorNum > 0){
                pageData.put("ERRORDATA",errorlist);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }


}
