package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.ArticleOrders;

public class OrderLogsVO extends ArticleOrders {
    private String token;
    private String startTime;
    private String endTime;
    private String userType;
    private String userBelongtoAdminId;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserBelongtoAdminId() {
        return userBelongtoAdminId;
    }

    public void setUserBelongtoAdminId(String userBelongtoAdminId) {
        this.userBelongtoAdminId = userBelongtoAdminId;
    }
}
