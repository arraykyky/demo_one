package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 管理员处理订单分析——全部数据分析结果
 */
public class AdminOrderAnalysisTotalDataVO implements Serializable {
    private Integer totalOrders;
    private Integer acceptCount;
    private Integer finishedCount;
    private Integer refuseCount;
    private Integer sendSuccesCount;
    private Integer sendFailCount;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Integer getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(Integer acceptCount) {
        this.acceptCount = acceptCount;
    }

    public Integer getFinishedCount() {
        return finishedCount;
    }

    public void setFinishedCount(Integer finishedCount) {
        this.finishedCount = finishedCount;
    }

    public Integer getRefuseCount() {
        return refuseCount;
    }

    public void setRefuseCount(Integer refuseCount) {
        this.refuseCount = refuseCount;
    }

    public Integer getSendSuccesCount() {
        return sendSuccesCount;
    }

    public void setSendSuccesCount(Integer sendSuccesCount) {
        this.sendSuccesCount = sendSuccesCount;
    }

    public Integer getSendFailCount() {
        return sendFailCount;
    }

    public void setSendFailCount(Integer sendFailCount) {
        this.sendFailCount = sendFailCount;
    }
}
