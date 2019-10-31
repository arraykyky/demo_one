package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.domain.DocumentDatabase;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocumentStoreVO extends DocumentDatabase {
    private String token;
    private String documentDatabaseIdsStr;
    private List<String> documentDatabaseIdList;
    private String documentId;
    private String documentTitle;

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

    public String getDocumentDatabaseIdsStr() {
        return documentDatabaseIdsStr;
    }

    public void setDocumentDatabaseIdsStr(String documentDatabaseIdsStr) {
        this.documentDatabaseIdsStr = documentDatabaseIdsStr;
        this.setDocumentDatabaseIdList();
    }

    public List<String> getDocumentDatabaseIdList() {
        return documentDatabaseIdList;
    }

    public void setDocumentDatabaseIdList(List<String> documentDatabaseIdList) {
        this.documentDatabaseIdList = documentDatabaseIdList;
    }

    public void setDocumentDatabaseIdList() {
        if (StringUtils.isNotBlank(this.documentDatabaseIdsStr)){
            this.documentDatabaseIdList = new ArrayList<>(Arrays.asList(this.documentDatabaseIdsStr.split(";")));
        }else {
            this.documentDatabaseIdList = new ArrayList<>();
        }
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }
}
