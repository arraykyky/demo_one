package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class SolrSelectResultSortVO implements Serializable {
    //排序字段
    private Integer sortWorld = -1;
    //排序类型 以大于0升序，小于0降序
    private Integer sortType = -1;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
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
}
