package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 历史检索VO
 */
public class HistorySearchVO extends SearchVoBean {
    //检索记录id
    private String searchesId;
    //检索人id
    private String searchesUserid;
    //检索表达式
    private String ext = "*[ALL]";
    //策略检索exp集合
    private List<String> searcheExps;
    //策略检索连接词集合
    private List<String> cons;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getSearchesId() {
        return searchesId;
    }

    public void setSearchesId(String searchesId) {
        this.searchesId = searchesId;
    }

    public String getSearchesUserid() {
        return searchesUserid;
    }

    public void setSearchesUserid(String searchesUserid) {
        this.searchesUserid = searchesUserid;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public List<String> getSearcheExps() {
        return searcheExps;
    }

    public void setSearcheExps(List<String> searcheExps) {
        this.searcheExps = searcheExps;
    }

    public List<String> getCons() {
        return cons;
    }

    public void setCons(List<String> cons) {
        this.cons = cons;
    }
}
