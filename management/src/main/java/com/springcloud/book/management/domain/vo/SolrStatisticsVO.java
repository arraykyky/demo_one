package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.SolrStatistics;

public class SolrStatisticsVO extends SolrStatistics {
    private String token;
    private String andValue;
    private String orValue;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAndValue() {
        return andValue;
    }

    public void setAndValue(String andValue) {
        this.andValue = andValue;
    }

    public String getOrValue() {
        return orValue;
    }

    public void setOrValue(String orValue) {
        this.orValue = orValue;
    }
}
