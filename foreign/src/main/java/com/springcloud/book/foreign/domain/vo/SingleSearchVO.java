package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

/**
 *  普通检索——单篇检索vo
 * [12 journal_title_main]journal_title_main
 * [13 medline_date]medline_date
 * [14 volume]volume
 * [15 issue]issue
 * [16 medline_page]medline_page
 */
public class SingleSearchVO extends SearchVoBean {
    private String titleMain;
    private String year;
    private String volume;
    private String issue;
    private String startPage;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getTitleMain() {
        return titleMain;
    }

    public void setTitleMain(String titleMain) {
        this.titleMain = titleMain;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }
}
