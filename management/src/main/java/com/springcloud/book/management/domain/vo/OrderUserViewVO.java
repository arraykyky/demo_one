package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.OrderUserView;

public class OrderUserViewVO extends OrderUserView {
    private String token;
    //申请时间开始值
    private String start;
    //申请时间结束值
    private String end;
    //接受订单管理员id(用于管理员分析中某个人的订单数据)
    private String orderAcceptAdminId;
    //管理员处理订单分析——时间段值(单位：毫秒)
    private String timeSlot;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String getOrderAcceptAdminId() {
        return orderAcceptAdminId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void setOrderAcceptAdminId(String orderAcceptAdminId) {
        this.orderAcceptAdminId = orderAcceptAdminId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}
