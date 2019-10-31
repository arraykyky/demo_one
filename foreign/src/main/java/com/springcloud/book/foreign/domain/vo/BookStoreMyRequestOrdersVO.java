package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class BookStoreMyRequestOrdersVO implements Serializable {
    private String token;
    //订单id
    private String orderId;
    //状态为-1对应订单类型字符串为：null时，获取全部类型的数据
    private Integer orderType = -1;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
