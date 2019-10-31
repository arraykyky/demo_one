package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.User;

public class UserVO extends User {
    private String token;
    //分配/充值可用点数
    private int allotPoint;
    //消费类型（1点数分配；2金币充值；-1点数消费）
    private Integer allotType;

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

    public int getAllotPoint() {
        return allotPoint;
    }

    public void setAllotPoint(int allotPoint) {
        this.allotPoint = allotPoint;
    }

    public Integer getAllotType() {
        return allotType;
    }

    public void setAllotType(Integer allotType) {
        this.allotType = allotType;
    }
}
