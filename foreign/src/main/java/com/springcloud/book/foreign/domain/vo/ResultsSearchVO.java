package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

/**
 * 结果中检索vo
 */
public class ResultsSearchVO extends SearchVoBean{

    //历史检索表达式
    private String exp;
    //排序字段
    private Integer sortWorld;
    //排序类型 以大于0升序，小于0降序
    private Integer sortType;

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public Integer getSortWorld() {
        return sortWorld;
    }

    public void setSortWorld(Integer sortWorld) {
        this.sortWorld = sortWorld;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
