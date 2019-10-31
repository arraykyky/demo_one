package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 期刊导航--学科分类菜单vo
 */
public class JournalNavigationMenuVO implements Serializable {
    //父节点
    private String menuParentId = "0";
    //检索字段  --  字符串数字
    private String searchWorld;
    //检索值
    private String searchValue;
    //排序字段  --  字符串数字
    private String sortWorld = "106";
    //排序方式，大于0升序，小于0降序
    private Integer sortType = 1;
    //期刊id
    private String journalId;
    //年份
    private Integer year;
    //卷数
    private String volume;
    // 期数
    private String issue;


    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getSearchWorld() {
        return searchWorld;
    }

    public void setSearchWorld(String searchWorld) {
        this.searchWorld = searchWorld;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSortWorld() {
        return sortWorld;
    }

    public void setSortWorld(String sortWorld) {
        this.sortWorld = sortWorld;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
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
    public String toString() {
        return JSON.toJSONString(this);
    }
}
