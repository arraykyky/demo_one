package com.springcloud.book.foreign.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author grl
 * @since 2019-08-05
 */
@TableName("fd_searches_history")
public class SearchesHistory extends Model<SearchesHistory> {

    private static final long serialVersionUID = 1L;

    /**
     * 检索id
     */
    @TableId("searches_id")
    private String searchesId;

    /**
     * 检索编号
     */
    @TableField("searches_num")
    private String searchesNum;

    /**
     * 检索表达式
     */
    @TableField("searches_statement")
    private String searchesStatement;

    /**
     * 命中数量
     */
    @TableField("searches_counts")
    private Integer searchesCounts;

    /**
     * 检索人id
     */
    @TableField("searches_userId")
    private String searchesUserid;

    /**
     * 删除:1;没删除:0
     */
    @TableField("searches_delete")
    private Integer searchesDelete;

    /**
     * SolrQuery检索字符串
     */
    @TableField("searches_sql")
    private String searchesSql;

    /**
     * 检索记录的时间
     */
    @TableField("searches_time")
    private LocalDateTime searchesTime;


    public String getSearchesId() {
        return searchesId;
    }

    public void setSearchesId(String searchesId) {
        this.searchesId = searchesId;
    }

    public String getSearchesNum() {
        return searchesNum;
    }

    public void setSearchesNum(String searchesNum) {
        this.searchesNum = searchesNum;
    }

    public String getSearchesStatement() {
        return searchesStatement;
    }

    public void setSearchesStatement(String searchesStatement) {
        this.searchesStatement = searchesStatement;
    }

    public Integer getSearchesCounts() {
        return searchesCounts;
    }

    public void setSearchesCounts(Integer searchesCounts) {
        this.searchesCounts = searchesCounts;
    }

    public String getSearchesUserid() {
        return searchesUserid;
    }

    public void setSearchesUserid(String searchesUserid) {
        this.searchesUserid = searchesUserid;
    }

    public Integer getSearchesDelete() {
        return searchesDelete;
    }

    public void setSearchesDelete(Integer searchesDelete) {
        this.searchesDelete = searchesDelete;
    }

    public String getSearchesSql() {
        return searchesSql;
    }

    public void setSearchesSql(String searchesSql) {
        this.searchesSql = searchesSql;
    }

    @Override
    protected Serializable pkVal() {
        return this.searchesId;
    }

    public LocalDateTime getSearchesTime() {
        return searchesTime;
    }

    public void setSearchesTime(LocalDateTime searchesTime) {
        this.searchesTime = searchesTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
