package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 核对数据导出文件VO
 */
public class UnzipCheckExportVO implements Serializable {
    private String journalName;
    private String abParentDir;
    private String abVolume;
    private String abIssue;
    private Integer abNum;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getAbParentDir() {
        return abParentDir;
    }

    public void setAbParentDir(String abParentDir) {
        this.abParentDir = abParentDir;
    }

    public String getAbVolume() {
        return abVolume;
    }

    public void setAbVolume(String abVolume) {
        this.abVolume = abVolume;
    }

    public String getAbIssue() {
        return abIssue;
    }

    public void setAbIssue(String abIssue) {
        this.abIssue = abIssue;
    }

    public Integer getAbNum() {
        return abNum;
    }

    public void setAbNum(Integer abNum) {
        this.abNum = abNum;
    }
}
