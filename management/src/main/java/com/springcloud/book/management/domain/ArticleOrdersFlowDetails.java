package com.springcloud.book.management.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author grl
 * @since 2019-09-19
 */
@TableName("fd_article_orders_flow_details")
public class ArticleOrdersFlowDetails extends Model<ArticleOrdersFlowDetails> {

    private static final long serialVersionUID = 1L;

    /**
     * 流程详情id
     */
    @TableId("flow_detail_id")
    private String flowDetailId;

    /**
     * 对应订单表的主键id
     */
    @TableField("order_id")
    private String orderId;

    /**
     * 订单接受管理员id
     */
    @TableField("order_accept_admin_id")
    private String orderAcceptAdminId;

    /**
     * 订单接受管理员名
     */
    @TableField("order_accept_admin_name")
    private String orderAcceptAdminName;

    /**
     * 流程详情标号(0010;0000;0001;0011;1111;1100)
     */
    @TableField("flow_detail_deal_sign")
    private String flowDetailDealSign;

    /**
     * 流程详情说明(订单被拒;申请中;处理中;订单完成;订单馆际推送;馆际撤回;)
     */
    @TableField("flow_detail_deal_note")
    private String flowDetailDealNote;

    /**
     * 流程详情创建时间
     */
    @TableField("flow_detail_create_time")
    private String flowDetailCreateTime;


    public String getFlowDetailId() {
        return flowDetailId;
    }

    public void setFlowDetailId(String flowDetailId) {
        this.flowDetailId = flowDetailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAcceptAdminId() {
        return orderAcceptAdminId;
    }

    public void setOrderAcceptAdminId(String orderAcceptAdminId) {
        this.orderAcceptAdminId = orderAcceptAdminId;
    }

    public String getOrderAcceptAdminName() {
        return orderAcceptAdminName;
    }

    public void setOrderAcceptAdminName(String orderAcceptAdminName) {
        this.orderAcceptAdminName = orderAcceptAdminName;
    }

    public String getFlowDetailDealSign() {
        return flowDetailDealSign;
    }

    public void setFlowDetailDealSign(String flowDetailDealSign) {
        this.flowDetailDealSign = flowDetailDealSign;
    }

    public String getFlowDetailDealNote() {
        return flowDetailDealNote;
    }

    public void setFlowDetailDealNote(String flowDetailDealNote) {
        this.flowDetailDealNote = flowDetailDealNote;
    }

    public String getFlowDetailCreateTime() {
        return flowDetailCreateTime;
    }

    public void setFlowDetailCreateTime(String flowDetailCreateTime) {
        this.flowDetailCreateTime = flowDetailCreateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.flowDetailId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
