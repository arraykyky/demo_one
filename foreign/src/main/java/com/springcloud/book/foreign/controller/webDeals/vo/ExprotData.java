package com.springcloud.book.foreign.controller.webDeals.vo;

public class ExprotData {
    //导出类型 0 1 全部;2自定义
    private String exportType;
    //是否全部导出
    private boolean yesOrNotExportAll;
    //文献标题
    private boolean title;
    //文献作者
    private boolean author;
    //作者单文
    private boolean unit;
    //期刊标题
    private boolean journalTitle;
    //出版年份
    private boolean year;
    //卷
    private boolean volum;
    //期
    private boolean issue;
    //页
    private boolean page;
    //关键词
    private boolean keyWorlds;
    //摘要
    private boolean abstr;
    //ISSN号
    private boolean issn;
    //国际编号
    private boolean note;
    //数据库提供商
    private boolean dper;

    public ExprotData(){}

    public ExprotData(boolean yesOrNotExportAll){
        if (yesOrNotExportAll){
            this.yesOrNotExportAll = yesOrNotExportAll;
            this.title = true;
            this.author = true;
            this.unit = true;
            this.journalTitle = true;
            this.year = true;
            this.volum = true;
            this.issue = true;
            this.page = true;
            this.keyWorlds = true;
            this.abstr = true;
            this.issn = true;
            this.note = true;
            this.dper = true;
        }

    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public boolean isYesOrNotExportAll() {
        return yesOrNotExportAll;
    }

    public void setYesOrNotExportAll(boolean yesOrNotExportAll) {
        this.yesOrNotExportAll = yesOrNotExportAll;
    }

    public boolean isTitle() {
        return title;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public boolean isUnit() {
        return unit;
    }

    public void setUnit(boolean unit) {
        this.unit = unit;
    }

    public boolean isJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(boolean journalTitle) {
        this.journalTitle = journalTitle;
    }

    public boolean isYear() {
        return year;
    }

    public void setYear(boolean year) {
        this.year = year;
    }

    public boolean isVolum() {
        return volum;
    }

    public void setVolum(boolean volum) {
        this.volum = volum;
    }

    public boolean isIssue() {
        return issue;
    }

    public void setIssue(boolean issue) {
        this.issue = issue;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public boolean isKeyWorlds() {
        return keyWorlds;
    }

    public void setKeyWorlds(boolean keyWorlds) {
        this.keyWorlds = keyWorlds;
    }

    public boolean isAbstr() {
        return abstr;
    }

    public void setAbstr(boolean abstr) {
        this.abstr = abstr;
    }

    public boolean isIssn() {
        return issn;
    }

    public void setIssn(boolean issn) {
        this.issn = issn;
    }

    public boolean isNote() {
        return note;
    }

    public void setNote(boolean note) {
        this.note = note;
    }

    public boolean isDper() {
        return dper;
    }

    public void setDper(boolean dper) {
        this.dper = dper;
    }
}
