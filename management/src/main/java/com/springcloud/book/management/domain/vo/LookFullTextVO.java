package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;

public class LookFullTextVO {
    private String fileName;
    private String fileUrl;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
