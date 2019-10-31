package com.springcloud.book.decompression.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnzipEnvironmentConfig {

    @Value("${unzip.environment}")
    private String environment;

    @Value("${unzip.zip-file-path}")
    private String zipFilePath;

    @Value("${unzip.unzip-to-cata-log}")
    private String unzipToCataLog;
    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
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
    }
}
