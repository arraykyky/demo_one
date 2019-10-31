package com.springcloud.book.management.domain.vo;

import com.springcloud.book.management.domain.ArticleOrders;

/**
 * 订单日志导出对象
 */
public class LogOrdersExportVO {
    private Integer num;
    private String userName;
    private String userEmail;
    private String userUnit;
    private String abstractTitle;
    private String journalTitle;
    private String applyTime;
    private String orderType;

    public LogOrdersExportVO orderToThis(ArticleOrders ao, int num, String journalTitle){
        this.num = num;
        this.userName = ao.getOrderApplyUserName();
        this.userEmail = ao.getOrderApplyUserEmail();
        this.userUnit = ao.getOrderApplyUserUnit();
        this.abstractTitle = ao.getArticleTitle();
        this.journalTitle = journalTitle;
        this.applyTime = ao.getOrderApplyTime();
        this.orderType = ao.getOrderState();
        return this;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserUnit() {
        return userUnit;
    }

    public void setUserUnit(String userUnit) {
        this.userUnit = userUnit;
    }

    public String getAbstractTitle() {
        return abstractTitle;
    }

    public void setAbstractTitle(String abstractTitle) {
        this.abstractTitle = abstractTitle;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
