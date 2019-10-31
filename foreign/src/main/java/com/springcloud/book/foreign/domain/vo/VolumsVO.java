package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class VolumsVO implements Serializable {
    //期刊id
    private String journalId;
    //年份
    private Integer year;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
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
}
