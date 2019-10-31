package com.springcloud.book.foreign.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author grl
 * @since 2019-08-12
 */
@TableName("fd_abstract_volums_count_for_journal")
public class AbstractVolumsCountForJournal extends Model<AbstractVolumsCountForJournal> {

    private static final long serialVersionUID = 1L;

    public AbstractVolumsCountForJournal(){}

    public AbstractVolumsCountForJournal(String journalId,int year,String volume,String issue){
        this.journalId = journalId;
        this.year = year;
        this.volume = volume;
        this.issue = issue;
    }

    /**
     * 统计自增id
     */
    @TableId(value = "volume_count_id", type = IdType.AUTO)
    private Integer volumeCountId;

    /**
     * 期刊id
     */
    @TableField("journal_id")
    private String journalId;

    /**
     * 年份
     */
    @TableField("year")
    private Integer year;

    /**
     * 卷数
     */
    @TableField("volume")
    private String volume;

    /**
     * 期数
     */
    @TableField("issue")
    private String issue;


    public Integer getVolumeCountId() {
        return volumeCountId;
    }

    public void setVolumeCountId(Integer volumeCountId) {
        this.volumeCountId = volumeCountId;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    @Override
    protected Serializable pkVal() {
        return this.volumeCountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractVolumsCountForJournal)) return false;
        AbstractVolumsCountForJournal that = (AbstractVolumsCountForJournal) o;
        return Objects.equals(journalId, that.journalId) &&
                Objects.equals(year, that.year) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(issue, that.issue);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
