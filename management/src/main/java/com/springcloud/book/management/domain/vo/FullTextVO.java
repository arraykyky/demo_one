package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.FullTextUploadFlow;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;

public class FullTextVO extends FullTextUploadFlow {
    private String token;
    private MultipartFile file;
    private String abstractId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean checkFullTextVONecessaryFieldIsNotEmpty(FullTextVO fullTextVO){
        if (fullTextVO == null){
            return false;
        }
        try {
            for (Field field : fullTextVO.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object value = field.get(fullTextVO);
                if (value == null || "".equals(value.toString())){
                    return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String getAbstractId() {
        return abstractId;
    }

    @Override
    public void setAbstractId(String abstractId) {
        this.abstractId = abstractId;
    }
}
