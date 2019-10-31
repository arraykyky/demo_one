package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.ArticleOrders;

public class ArticleOrdersVO extends ArticleOrders {
    private String token;
    //超级管理员CJZGLY;本地管理员LOCALGLY;馆际互借管理员CLOUD
    private String userRoleCode;
    private Integer dealFlag;



    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode;
    }

    public Integer getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(Integer dealFlag) {
        this.dealFlag = dealFlag;
    }
}
