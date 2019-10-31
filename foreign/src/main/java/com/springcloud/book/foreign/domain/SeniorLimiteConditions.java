package com.springcloud.book.foreign.domain;

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
 * @since 2019-09-11
 */
@TableName("fd_senior_limite_conditions")
public class SeniorLimiteConditions extends Model<SeniorLimiteConditions> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "limite_id", type = IdType.AUTO)
    private Long limiteId;

    /**
     * 限定条件检索字段号(对应solr中的字段序号)
     */
    @TableField("limite_field_num")
    private String limiteFieldNum;

    /**
     * 限定条件英文检索值
     */
    @TableField("limite_en_world")
    private String limiteEnWorld;

    /**
     * 限定条件中文描述词
     */
    @TableField("limite_ch_world")
    private String limiteChWorld;

    /**
     * 限定条件分组号(0000:人类/动物；0001:性别；0002:文献类型；0003:语种；0004:年龄)
     */
    @TableField("limite_group_num")
    private String limiteGroupNum;

    /**
     * 限定条件分组名(人类/动物；性别；文献类型；语种；年龄)
     */
    @TableField("limite_group_name")
    private String limiteGroupName;


    public Long getLimiteId() {
        return limiteId;
    }

    public void setLimiteId(Long limiteId) {
        this.limiteId = limiteId;
    }

    public String getLimiteFieldNum() {
        return limiteFieldNum;
    }

    public void setLimiteFieldNum(String limiteFieldNum) {
        this.limiteFieldNum = limiteFieldNum;
    }

    public String getLimiteEnWorld() {
        return limiteEnWorld;
    }

    public void setLimiteEnWorld(String limiteEnWorld) {
        this.limiteEnWorld = limiteEnWorld;
    }

    public String getLimiteChWorld() {
        return limiteChWorld;
    }

    public void setLimiteChWorld(String limiteChWorld) {
        this.limiteChWorld = limiteChWorld;
    }

    public String getLimiteGroupNum() {
        return limiteGroupNum;
    }

    public void setLimiteGroupNum(String limiteGroupNum) {
        this.limiteGroupNum = limiteGroupNum;
    }

    public String getLimiteGroupName() {
        return limiteGroupName;
    }

    public void setLimiteGroupName(String limiteGroupName) {
        this.limiteGroupName = limiteGroupName;
    }

    @Override
    protected Serializable pkVal() {
        return this.limiteId;
    }

    @Override
    public String toString() {
        return "SeniorLimiteConditions{" +
        "limiteId=" + limiteId +
        ", limiteFieldNum=" + limiteFieldNum +
        ", limiteEnWorld=" + limiteEnWorld +
        ", limiteChWorld=" + limiteChWorld +
        ", limiteGroupNum=" + limiteGroupNum +
        ", limiteGroupName=" + limiteGroupName +
        "}";
    }
}
