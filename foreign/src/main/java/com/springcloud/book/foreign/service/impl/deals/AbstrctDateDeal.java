package com.springcloud.book.foreign.service.impl.deals;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.ArticleOrdersFlowDetails;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.OrderApplyVO;
import com.springcloud.book.foreign.enums.EmailStateEnum;
import com.springcloud.book.foreign.enums.OrderDealTypeEnum;
import com.springcloud.book.foreign.enums.OrderTypeEnum;
import com.springcloud.book.foreign.util.DateUtil;
import com.springcloud.book.foreign.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstrctDateDeal {

    private static Logger logger = LoggerFactory.getLogger(AbstrctDateDeal.class);

    //将摘要有序(按照year排序)的list数据按year个字段封装成map对象的版本信息数据
    public static void listData2WorldMap(List<PageData> list, PageData pageData){
        List<Map> allVi = new ArrayList();
        int i = 0;
        for (;i<list.size();i++){
            Map<String,Object> yearVi = new HashMap();
            List<PageData> viList = new ArrayList<>();
            PageData pdi = list.get(i);
            for (;i<list.size();i++){
                PageData pdj = list.get(i);
                if (pdi.get("year").toString().equals(pdj.get("year").toString())){
                    viList.add(pdj);
                }else {
                    break;
                }
            }
            yearVi.put(pdi.get("year").toString(),viList);
            allVi.add(yearVi);
        }
        pageData.put("versionInfo",allVi);
    }

    //封装订单对象
    public static void applyOrderDealWithSolr(OrderApplyVO orderApplyVO, AbstractSolr bean, String orderId, Long orderNum) throws Exception {
        //文献标题
        orderApplyVO.setArticleTitle(bean.getArticleTitle());
        //文献来源
        orderApplyVO.setArticleSource(bean.getJournalTitleMain());
        //文献作者
        if (bean.getHadFullText() == 1){
            String url = bean.getOriginalURL();
            if (url != null && !"".equals(url)){
                HashMap<String,String> jsonURL = JSON.parseObject(url, HashMap.class);
                if (jsonURL.containsKey("thridLink")){
                    orderApplyVO.setArticleExistThridLink(1);
                }else {
                    orderApplyVO.setArticleExistThridLink(-1);
                }
            }else {
                orderApplyVO.setArticleExistThridLink(-1);
            }
        }else {
            orderApplyVO.setArticleExistThridLink(-1);
        }
        //文献期刊号
        String num = "未知";
        String y = DateUtil.format(bean.getMedlineDate(),"yyyy");
        String v = bean.getVolume();
        String i = bean.getIssue();
        if (y!=null && !y.equals("")){
            num = y +" 年 ";
            if (v!=null && !v.equals("")){
                num += v + " 卷 ";
                if (i!=null && !i.equals("")){
                    num += i + " 期";
                }
            }else{
                num += "未知";
            }
        }
        orderApplyVO.setArticlePeriodicalNum(num);
        //文献id
        orderApplyVO.setAbstractId(bean.getAbstractId());
        //订单id
        orderApplyVO.setOrderId(orderId);
        //订单状态
        orderApplyVO.setOrderState(OrderTypeEnum.O_APPLYING.getDealNum());
        //邮件状态(0001发送失败;0000未发送，默认值;1111发送成功)
        orderApplyVO.setEmailState(EmailStateEnum.E_UNSENT.getDealNum());
        //订单处理方式
        orderApplyVO.setOrderDealType(OrderDealTypeEnum.O_LOCAL.getDealNum());
        //订单号
        orderApplyVO.setOrderNum(String.valueOf(orderNum));
        //订单申请时间
        orderApplyVO.setOrderApplyTime(DateUtil.formatCurrentDateTime(orderNum));
    }

    //封装订单流程对象
    public static void applyOrderFlowDeal(ArticleOrdersFlowDetails flowDetails, Long orderNum){
        //初始化流程id
        flowDetails.setFlowDetailId(Tools.getUUID());
        //初始化流程标号
        flowDetails.setFlowDetailDealSign(OrderTypeEnum.O_APPLYING.getDealNum());
        //初始化详情说明
        flowDetails.setFlowDetailDealNote(OrderTypeEnum.O_APPLYING.getDealCode());
        //初始化详情创建时间
        flowDetails.setFlowDetailCreateTime(DateUtil.formatCurrentDateTime(orderNum));
    }

    //邮件是否异步发送
//    public static void applyOrderEmailSend(OrderBiz orderBiz, FullTextUploadFlowService fullTextUploadFlowService, ArticleOrdersService articleOrdersService,
//                                           PageData map, String orderId, Long orderNum, User user){
//        //判断订单文献是否在全文库中存在abstractId
//        PageData fullTextPD = fullTextUploadFlowService.findFullTextByAbstractId(map);
//        if (fullTextPD != null){
//            //异步发送邮件，并更新订单的邮件发送状态orderApplyUserEmail
//            String orderApplyUserEmail = map.get("orderApplyUserEmail").toString();
//            String title = map.getString("articleTitle") != null ? map.getString("articleTitle"): "未知";
//            String authors = map.getString("authors") != null ? map.getString("authors") : "未知";
//            String jName = map.getString("articleSource") != null ? map.getString("articleSource") : "未知";
//            File textFile = new File(fullTextPD.get("uploadFileUrl").toString());
//            SendEmailDeal sendEmailDeal = new SendEmailDeal(orderBiz,orderId,orderApplyUserEmail,orderNum.toString(),textFile,user,title,authors,jName);
//            ThreadPoolExecutor tpe = new ThreadPoolExecutor(1,3,60,TimeUnit.SECONDS,
//                    new ArrayBlockingQueue<>(10),new ThreadPoolExecutor.CallerRunsPolicy());
//            tpe.execute(sendEmailDeal);
//            tpe.shutdown();
//        }else{
//            map.put("msg","没有原文信息，请先上传原文");
//        }
//    }
}
