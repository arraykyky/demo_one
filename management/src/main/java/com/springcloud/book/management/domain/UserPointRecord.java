package com.springcloud.book.management.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
@TableName("fd_user_point_record")
public class UserPointRecord extends Model<UserPointRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("record_id")
    private String recordId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 记录时间
     */
    @TableField("record_time")
    private String recordTime;

    /**
     * 消费类型（1点数分配；2金币充值；-1点数消费）
     */
    @TableField("consume_type")
    private Integer consumeType;

    /**
     * 金币充值金额(记录金币充值时的金币额数，点数消费和点数分配时为0)
     */
    @TableField("add_money")
    private Integer addMoney;

    /**
     * 历史可用点数
     */
    @TableField("history_point_count")
    private Integer historyPointCount;

    /**
     * 消费点数(点数消费为负数；充值和分配为正数)
     */
    @TableField("consume_point_count")
    private Integer consumePointCount;

    /**
     * 可用点数(当前余额点数)
     */
    @TableField("enable_use_point_number")
    private Integer enableUsePointNumber;


    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Integer consumeType) {
        this.consumeType = consumeType;
    }

    public Integer getAddMoney() {
        return addMoney;
    }

    public void setAddMoney(Integer addMoney) {
        this.addMoney = addMoney;
    }

    public Integer getHistoryPointCount() {
        return historyPointCount;
    }

    public void setHistoryPointCount(Integer historyPointCount) {
        this.historyPointCount = historyPointCount;
    }

    public Integer getConsumePointCount() {
        return consumePointCount;
    }

    public void setConsumePointCount(Integer consumePointCount) {
        this.consumePointCount = consumePointCount;
    }

    public Integer getEnableUsePointNumber() {
        return enableUsePointNumber;
    }

    public void setEnableUsePointNumber(Integer enableUsePointNumber) {
        this.enableUsePointNumber = enableUsePointNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.recordId;
    }

    @Override
    public String toString() {
        return "UserPointRecord{" +
        "recordId=" + recordId +
        ", userId=" + userId +
        ", recordTime=" + recordTime +
        ", consumeType=" + consumeType +
        ", addMoney=" + addMoney +
        ", historyPointCount=" + historyPointCount +
        ", consumePointCount=" + consumePointCount +
        ", enableUsePointNumber=" + enableUsePointNumber +
        "}";
    }
}
