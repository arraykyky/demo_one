package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JournalStoreVO implements Serializable {
    private String token;
    private String collectionJournalId;
    private String collectionUserId;
    private List<String> collectionIdList;
    //以分号";"链接的收藏期刊的收藏id字符串
    private String collectionIds;

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

    public String getCollectionJournalId() {
        return collectionJournalId;
    }

    public void setCollectionJournalId(String collectionJournalId) {
        this.collectionJournalId = collectionJournalId;
    }

    public String getCollectionUserId() {
        return collectionUserId;
    }

    public void setCollectionUserId(String collectionUserId) {
        this.collectionUserId = collectionUserId;
    }

    public List<String> getCollectionIdList() {
        return collectionIdList;
    }

    public void setCollectionIdList(List<String> collectionIdList) {
        this.collectionIdList = collectionIdList;
    }

    public void setCollectionIdList() {
        if (StringUtils.isNotBlank(this.collectionIds)){
            String[] array = this.collectionIds.split(";");
            this.setCollectionIdList(new ArrayList<>(Arrays.asList(array)));
        }
    }

    public String getCollectionIds() {
        return collectionIds;
    }

    public void setCollectionIds(String collectionIds) {
        this.collectionIds = collectionIds;
        this.setCollectionIdList();
    }
}
