package com.springcloud.book.management.domain;

import com.alibaba.fastjson.JSON;
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
 * @since 2019-09-26
 */
@TableName("fd_no_text_record")
public class NoTextRecord extends Model<NoTextRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("no_text_id")
    private String noTextId;

    /**
     * 申请文献id，摘要表主键
     */
    @TableField("abstract_id")
    private String abstractId;

    /**
     * 记录时间
     */
    @TableField("record_time")
    private String recordTime;

    /**
     * 是否找到全文
     */
    @TableField("is_seek_out")
    private Integer isSeekOut;


    public String getNoTextId() {
        return noTextId;
    }

    public void setNoTextId(String noTextId) {
        this.noTextId = noTextId;
    }

    public String getAbstractId() {
        return abstractId;
    }

    public void setAbstractId(String abstractId) {
        this.abstractId = abstractId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public Integer getIsSeekOut() {
        return isSeekOut;
    }

    public void setIsSeekOut(Integer isSeekOut) {
        this.isSeekOut = isSeekOut;
    }

    @Override
    protected Serializable pkVal() {
        return this.noTextId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
