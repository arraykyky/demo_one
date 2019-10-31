package com.springcloud.book.management.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2019-10-13
 */
@TableName("fd_hospital_name_abbreviation_info")
public class HospitalNameAbbreviationInfo extends Model<HospitalNameAbbreviationInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 医院信息id
     */
    @TableId(value = "hi_id", type = IdType.AUTO)
    private Integer hiId;

    /**
     * 医院名称
     */
    @TableField("hi_name")
    private String hiName;

    /**
     * 医院社会统一编码
     */
    @TableField("hi_abbreviation")
    private String hiAbbreviation;

    /**
     * 所属地区-默认为1浙江地区
     */
    @TableField("hi_area")
    private Integer hiArea;


    public Integer getHiId() {
        return hiId;
    }

    public void setHiId(Integer hiId) {
        this.hiId = hiId;
    }

    public String getHiName() {
        return hiName;
    }

    public void setHiName(String hiName) {
        this.hiName = hiName;
    }

    public String getHiAbbreviation() {
        return hiAbbreviation;
    }

    public void setHiAbbreviation(String hiAbbreviation) {
        this.hiAbbreviation = hiAbbreviation;
    }

    public Integer getHiArea() {
        return hiArea;
    }

    public void setHiArea(Integer hiArea) {
        this.hiArea = hiArea;
    }

    @Override
    protected Serializable pkVal() {
        return this.hiId;
    }

    @Override
    public String toString() {
        return "HospitalNameAbbreviationInfo{" +
        "hiId=" + hiId +
        ", hiName=" + hiName +
        ", hiAbbreviation=" + hiAbbreviation +
        ", hiArea=" + hiArea +
        "}";
    }
}
