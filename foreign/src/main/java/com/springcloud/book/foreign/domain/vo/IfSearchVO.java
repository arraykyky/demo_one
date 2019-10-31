package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

public class IfSearchVO extends ResultsSearchVO {
    private Double minIfs = 0.0;
    private Double maxIfs = 0.0;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Double getMinIfs() {
        return minIfs;
    }

    public void setMinIfs(Double minIfs) {
        this.minIfs = minIfs;
    }

    public Double getMaxIfs() {
        return maxIfs;
    }

    public void setMaxIfs(Double maxIfs) {
        this.maxIfs = maxIfs;
    }
}
