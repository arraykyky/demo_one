package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class AbstractFullTextLinksVO implements Serializable {
    //1 = pubmed 2 = doi 3 = pii 4 = pmc;
    private Integer urlType;
    private String urlContent;
    private String token;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
    }

    public String getUrlContent() {
        return urlContent;
    }

    public void setUrlContent(String urlContent) {
        this.urlContent = urlContent;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
