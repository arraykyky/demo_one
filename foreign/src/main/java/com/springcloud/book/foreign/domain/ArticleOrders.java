package com.springcloud.book.foreign.domain;

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
 * @since 2019-08-16
 */
@TableName("fd_article_orders")
public class ArticleOrders extends Model<ArticleOrders> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId("order_id")
    private String orderId;

    /**
     * 申请文献id,摘要表主键
     */
    @TableField("abstract_id")
    private String abstractId;

    /**
     * 文献标题
     */
    @TableField("article_title")
    private String articleTitle;

    /**
     * 文献来源--期刊名称
     */
    @TableField("article_source")
    private String articleSource;

    /**
     * 文献期刊号
     */
    @TableField("article_periodical_num")
    private String articlePeriodicalNum;

    /**
     * 订单状态(0010被拒绝;0000申请中，默认值;0001处理中;0011已完成;00000未找到全文)
     */
    @TableField("order_state")
    private String orderState;

    /**
     * 邮件状态(0001发送失败;0000未发送，默认值;1111发送成功)
     */
    @TableField("email_state")
    private String emailState;

    /**
     * 订单申请人id(对应Ella的用户id)
     */
    @TableField("order_apply_user_id")
    private String orderApplyUserId;

    /**
     * 订单申请人名称
     */
    @TableField("order_apply_user_name")
    private String orderApplyUserName;

    /**
     * 订单申请人所属单位
     */
    @TableField("order_apply_user_unit")
    private String orderApplyUserUnit;

    /**
     * 订单申请人联系电话
     */
    @TableField("order_apply_user_phone")
    private String orderApplyUserPhone;

    /**
     * 订单申请人邮箱
     */
    @TableField("order_apply_user_email")
    private String orderApplyUserEmail;

    /**
     * 订单申请时间
     */
    @TableField("order_apply_time")
    private String orderApplyTime;

    /**
     * 订单接受管理员
     */
    @TableField("order_accept_admin_id")
    private String orderAcceptAdminId;

    /**
     * 订单接受管理员名
     */
    @TableField("order_accept_admin_name")
    private String orderAcceptAdminName;

    /**
     * 订单最终操作时间
     */
    @TableField("order_final_action_time")
    private String orderFinalActionTime;

    /**
     * 订单处理方式(1100本地处理;1111馆际互借)
     */
    @TableField("order_deal_type")
    private String orderDealType;

    /**
     * 订单号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 订单被接收处理的次数（多少个人接收处理）
     */
    @TableField("order_accept_count")
    private Integer orderAcceptCount;

    /**
     * 订单处理方式(客服手动发送邮件1//系统定时发送的-1)
     */
    @TableField("order_handle_type")
    private Integer orderHandleType;

    /**
     * 订单接受时间
     */
    @TableField("order_accept_time")
    private String orderAcceptTime;

    /**
     * 订单响应时间(订单接受时间-订单申请时间)
     */
    @TableField("order_response_times")
    private Long orderResponseTimes;

    /**
     * 订单效率(总耗时)时间(订单完成时间-订单申请时间)
     */
    @TableField("order_efficiency_times")
    private Long orderEfficiencyTimes;

    /**
     * 邮件失败原因
     */
    @TableField("email_fail_desc")
    private String emailFailDesc;

    /**
     * 是否存在第三方链接(-1/1-否/是)
     */
    @TableField("article_exist_thrid_link")
    private Integer articleExistThridLink;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAbstractId() {
        return abstractId;
    }

    public void setAbstractId(String abstractId) {
        this.abstractId = abstractId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleSource() {
        return articleSource;
    }

    public void setArticleSource(String articleSource) {
        this.articleSource = articleSource;
    }

    public String getArticlePeriodicalNum() {
        return articlePeriodicalNum;
    }

    public void setArticlePeriodicalNum(String articlePeriodicalNum) {
        this.articlePeriodicalNum = articlePeriodicalNum;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getEmailState() {
        return emailState;
    }

    public void setEmailState(String emailState) {
        this.emailState = emailState;
    }

    public String getOrderApplyUserId() {
        return orderApplyUserId;
    }

    public void setOrderApplyUserId(String orderApplyUserId) {
        this.orderApplyUserId = orderApplyUserId;
    }

    public String getOrderApplyUserName() {
        return orderApplyUserName;
    }

    public void setOrderApplyUserName(String orderApplyUserName) {
        this.orderApplyUserName = orderApplyUserName;
    }

    public String getOrderApplyUserUnit() {
        return orderApplyUserUnit;
    }

    public void setOrderApplyUserUnit(String orderApplyUserUnit) {
        this.orderApplyUserUnit = orderApplyUserUnit;
    }

    public String getOrderApplyUserPhone() {
        return orderApplyUserPhone;
    }

    public void setOrderApplyUserPhone(String orderApplyUserPhone) {
        this.orderApplyUserPhone = orderApplyUserPhone;
    }

    public String getOrderApplyUserEmail() {
        return orderApplyUserEmail;
    }

    public void setOrderApplyUserEmail(String orderApplyUserEmail) {
        this.orderApplyUserEmail = orderApplyUserEmail;
    }

    public String getOrderApplyTime() {
        return orderApplyTime;
    }

    public void setOrderApplyTime(String orderApplyTime) {
        this.orderApplyTime = orderApplyTime;
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

    public String getOrderFinalActionTime() {
        return orderFinalActionTime;
    }

    public void setOrderFinalActionTime(String orderFinalActionTime) {
        this.orderFinalActionTime = orderFinalActionTime;
    }

    public String getOrderDealType() {
        return orderDealType;
    }

    public void setOrderDealType(String orderDealType) {
        this.orderDealType = orderDealType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderAcceptCount() {
        return orderAcceptCount;
    }

    public void setOrderAcceptCount(Integer orderAcceptCount) {
        this.orderAcceptCount = orderAcceptCount;
    }

    public Integer getOrderHandleType() {
        return orderHandleType;
    }

    public void setOrderHandleType(Integer orderHandleType) {
        this.orderHandleType = orderHandleType;
    }

    public String getOrderAcceptTime() {
        return orderAcceptTime;
    }

    public void setOrderAcceptTime(String orderAcceptTime) {
        this.orderAcceptTime = orderAcceptTime;
    }

    public Long getOrderResponseTimes() {
        return orderResponseTimes;
    }

    public void setOrderResponseTimes(Long orderResponseTimes) {
        this.orderResponseTimes = orderResponseTimes;
    }

    public Long getOrderEfficiencyTimes() {
        return orderEfficiencyTimes;
    }

    public void setOrderEfficiencyTimes(Long orderEfficiencyTimes) {
        this.orderEfficiencyTimes = orderEfficiencyTimes;
    }

    public String getEmailFailDesc() {
        return emailFailDesc;
    }

    public void setEmailFailDesc(String emailFailDesc) {
        this.emailFailDesc = emailFailDesc;
    }

    public Integer getArticleExistThridLink() {
        return articleExistThridLink;
    }

    public void setArticleExistThridLink(Integer articleExistThridLink) {
        this.articleExistThridLink = articleExistThridLink;
    }

    @Override
    protected Serializable pkVal() {
        return this.orderId;
    }

    @Override
    public String toString() {
        return "ArticleOrders{" +
        "orderId=" + orderId +
        ", abstractId=" + abstractId +
        ", articleTitle=" + articleTitle +
        ", articleSource=" + articleSource +
        ", articlePeriodicalNum=" + articlePeriodicalNum +
        ", orderState=" + orderState +
        ", emailState=" + emailState +
        ", orderApplyUserId=" + orderApplyUserId +
        ", orderApplyUserName=" + orderApplyUserName +
        ", orderApplyUserUnit=" + orderApplyUserUnit +
        ", orderApplyUserPhone=" + orderApplyUserPhone +
        ", orderApplyUserEmail=" + orderApplyUserEmail +
        ", orderApplyTime=" + orderApplyTime +
        ", orderAcceptAdminId=" + orderAcceptAdminId +
        ", orderAcceptAdminName=" + orderAcceptAdminName +
        ", orderFinalActionTime=" + orderFinalActionTime +
        ", orderDealType=" + orderDealType +
        ", orderNum=" + orderNum +
        ", orderAcceptCount=" + orderAcceptCount +
        ", orderHandleType=" + orderHandleType +
        ", orderAcceptTime=" + orderAcceptTime +
        ", orderResponseTimes=" + orderResponseTimes +
        ", orderEfficiencyTimes=" + orderEfficiencyTimes +
        ", emailFailDesc=" + emailFailDesc +
        ", articleExistThridLink=" + articleExistThridLink +
        "}";
    }
}
