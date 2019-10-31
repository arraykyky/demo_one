package com.springcloud.book.decompression.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.decompression.enums.UnzipEnum;

import java.io.Serializable;

public class CustomUnzipVO implements Serializable {
    private String zipFilePath;
    private String unzipToCataLog = "2019";
    private String unzipToPath;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getZipFilePath() {
        return zipFilePath;
    }

    public void setZipFilePath(String zipFilePath) {
        this.zipFilePath = zipFilePath;
    }

    public String getUnzipToCataLog() {
        return unzipToCataLog;
    }

    public void setUnzipToCataLog(String unzipToCataLog) {
        this.unzipToCataLog = unzipToCataLog;
        this.unzipToPath = UnzipEnum.getValue("ROOT") + this.unzipToCataLog;
    }

    public String getUnzipToPath() {
        return unzipToPath;
    }

}
