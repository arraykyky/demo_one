package com.springcloud.book.emailsystem.domain;

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
 * @since 2019-10-09
 */
@TableName("fd_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId("user_id")
    private String userId;

    /**
     * 用户名称(对应ella的loginName)
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户密码
     */
    @TableField("user_password")
    private String userPassword;

    /**
     * 微信用户的唯一标识
     */
    @TableField("user_open_id")
    private String userOpenId;

    /**
     * 用户真是姓名(对应ella的userName)
     */
    @TableField("user_real_name")
    private String userRealName;

    /**
     * 用户身份证号
     */
    @TableField("user_ID_card_num")
    private String userIdCardNum;

    /**
     * 用户联系方式
     */
    @TableField("user_phone")
    private String userPhone;

    /**
     * 用户邮箱
     */
    @TableField("user_email")
    private String userEmail;

    /**
     * 研究领域
     */
    @TableField("research_field")
    private String researchField;

    /**
     * 所属医院id
     */
    @TableField("hospital_id")
    private String hospitalId;

    /**
     * 所属科室
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 补充科室名称
     */
    @TableField("before_department_name")
    private String beforeDepartmentName;

    /**
     * 职称
     */
    @TableField("positional_titles")
    private String positionalTitles;

    /**
     * 过期时间
     */
    @TableField("out_time")
    private String outTime;

    /**
     * 备注信息
     */
    @TableField("notes")
    private String notes;

    /**
     * 可用点数
     */
    @TableField("enable_use_point_number")
    private Integer enableUsePointNumber;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private String userType;

    /**
     * 是否流量限制
     */
    @TableField("is_limit_flow")
    private Integer isLimitFlow;

    /**
     * 日流量数
     */
    @TableField("date_flow")
    private Integer dateFlow;

    /**
     * 是否限制总篇数
     */
    @TableField("is_limit_total_piece_num")
    private Integer isLimitTotalPieceNum;

    /**
     * 总篇数
     */
    @TableField("total_piece_num")
    private Integer totalPieceNum;

    /**
     * 用户状态
     */
    @TableField("user_state")
    private Integer userState;

    /**
     * 用户工号
     */
    @TableField("job_no")
    private String jobNo;

    /**
     * ella中用户的id
     */
    @TableField("ella_user_id")
    private String ellaUserId;

    /**
     * 用户所属管理员--(谁添加的用户)
     */
    @TableField("user_belongto_adminId")
    private String userBelongtoAdminid;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserOpenId() {
        return userOpenId;
    }

    public void setUserOpenId(String userOpenId) {
        this.userOpenId = userOpenId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserIdCardNum() {
        return userIdCardNum;
    }

    public void setUserIdCardNum(String userIdCardNum) {
        this.userIdCardNum = userIdCardNum;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getResearchField() {
        return researchField;
    }

    public void setResearchField(String researchField) {
        this.researchField = researchField;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getBeforeDepartmentName() {
        return beforeDepartmentName;
    }

    public void setBeforeDepartmentName(String beforeDepartmentName) {
        this.beforeDepartmentName = beforeDepartmentName;
    }

    public String getPositionalTitles() {
        return positionalTitles;
    }

    public void setPositionalTitles(String positionalTitles) {
        this.positionalTitles = positionalTitles;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getEnableUsePointNumber() {
        return enableUsePointNumber;
    }

    public void setEnableUsePointNumber(Integer enableUsePointNumber) {
        this.enableUsePointNumber = enableUsePointNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getIsLimitFlow() {
        return isLimitFlow;
    }

    public void setIsLimitFlow(Integer isLimitFlow) {
        this.isLimitFlow = isLimitFlow;
    }

    public Integer getDateFlow() {
        return dateFlow;
    }

    public void setDateFlow(Integer dateFlow) {
        this.dateFlow = dateFlow;
    }

    public Integer getIsLimitTotalPieceNum() {
        return isLimitTotalPieceNum;
    }

    public void setIsLimitTotalPieceNum(Integer isLimitTotalPieceNum) {
        this.isLimitTotalPieceNum = isLimitTotalPieceNum;
    }

    public Integer getTotalPieceNum() {
        return totalPieceNum;
    }

    public void setTotalPieceNum(Integer totalPieceNum) {
        this.totalPieceNum = totalPieceNum;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getEllaUserId() {
        return ellaUserId;
    }

    public void setEllaUserId(String ellaUserId) {
        this.ellaUserId = ellaUserId;
    }

    public String getUserBelongtoAdminid() {
        return userBelongtoAdminid;
    }

    public void setUserBelongtoAdminid(String userBelongtoAdminid) {
        this.userBelongtoAdminid = userBelongtoAdminid;
    }

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

    @Override
    public String toString() {
        return "User{" +
        "userId=" + userId +
        ", userName=" + userName +
        ", userPassword=" + userPassword +
        ", userOpenId=" + userOpenId +
        ", userRealName=" + userRealName +
        ", userIdCardNum=" + userIdCardNum +
        ", userPhone=" + userPhone +
        ", userEmail=" + userEmail +
        ", researchField=" + researchField +
        ", hospitalId=" + hospitalId +
        ", deptId=" + deptId +
        ", beforeDepartmentName=" + beforeDepartmentName +
        ", positionalTitles=" + positionalTitles +
        ", outTime=" + outTime +
        ", notes=" + notes +
        ", enableUsePointNumber=" + enableUsePointNumber +
        ", userType=" + userType +
        ", isLimitFlow=" + isLimitFlow +
        ", dateFlow=" + dateFlow +
        ", isLimitTotalPieceNum=" + isLimitTotalPieceNum +
        ", totalPieceNum=" + totalPieceNum +
        ", userState=" + userState +
        ", jobNo=" + jobNo +
        ", ellaUserId=" + ellaUserId +
        ", userBelongtoAdminid=" + userBelongtoAdminid +
        "}";
    }
}
