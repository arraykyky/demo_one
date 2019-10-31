package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.ArticleOrders;
import com.springcloud.book.management.enums.OrderEmailStateEnum;
import com.springcloud.book.management.enums.OrderHandleTypeEnum;
import com.springcloud.book.management.enums.OrderTypeEnum;

import java.util.Map;

public class OrdersWithAbstractSolrInfoVO extends ArticleOrders {
   private String pmid;
   private String issn;
   private String sourceLinkUrl;
   private String orderHandleTypeCode;

   public void getOrdersWithAbstractSolrInfoVO(OrdersWithAbstractSolrInfoVO ordersWithAbstractSolrInfoVO,ArticleOrders articleOrders,Map<String,Object> map){
       if (map != null && !map.isEmpty()){
           //pmid
           if (map.containsKey("pmid")){
               ordersWithAbstractSolrInfoVO.setPmid(map.get("pmid").toString());
           }
           //issn
           if (map.containsKey("issn")){
               ordersWithAbstractSolrInfoVO.setIssn(map.get("issn").toString());
           }
           //url
           if (map.containsKey("originalURL")){
               String originalURL = JSON.toJSONString(map.get("originalURL").toString());
               ordersWithAbstractSolrInfoVO.setSourceLinkUrl(originalURL);
           }
       }
       ordersWithAbstractSolrInfoVO.setOrderId(articleOrders.getOrderId());
       ordersWithAbstractSolrInfoVO.setAbstractId(articleOrders.getAbstractId());
       ordersWithAbstractSolrInfoVO.setOrderNum(articleOrders.getOrderNum());
       ordersWithAbstractSolrInfoVO.setArticleTitle(articleOrders.getArticleTitle());
       ordersWithAbstractSolrInfoVO.setArticlePeriodicalNum(articleOrders.getArticlePeriodicalNum());
       ordersWithAbstractSolrInfoVO.setArticleSource(articleOrders.getArticleSource());
       ordersWithAbstractSolrInfoVO.setOrderApplyUserUnit(articleOrders.getOrderApplyUserUnit());
       ordersWithAbstractSolrInfoVO.setOrderApplyUserName(articleOrders.getOrderApplyUserName());
       ordersWithAbstractSolrInfoVO.setOrderApplyUserPhone(articleOrders.getOrderApplyUserPhone());
       ordersWithAbstractSolrInfoVO.setOrderApplyUserEmail(articleOrders.getOrderApplyUserEmail());
       ordersWithAbstractSolrInfoVO.setOrderApplyTime(articleOrders.getOrderApplyTime());
       ordersWithAbstractSolrInfoVO.setOrderDealType(articleOrders.getOrderDealType());
       ordersWithAbstractSolrInfoVO.setEmailState(OrderEmailStateEnum.getDealNumByDealsFlag(articleOrders.getEmailState()));
       ordersWithAbstractSolrInfoVO.setOrderHandleTypeCode(OrderHandleTypeEnum.getCode(articleOrders.getOrderHandleType()));
       ordersWithAbstractSolrInfoVO.setOrderState(OrderTypeEnum.getCodeByNum(articleOrders.getOrderState()));
   }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getSourceLinkUrl() {
        return sourceLinkUrl;
    }

    public void setSourceLinkUrl(String sourceLinkUrl) {
        this.sourceLinkUrl = sourceLinkUrl;
    }

    public String getOrderHandleTypeCode() {
        return orderHandleTypeCode;
    }

    public void setOrderHandleTypeCode(String orderHandleTypeCode) {
        this.orderHandleTypeCode = orderHandleTypeCode;
    }
}
