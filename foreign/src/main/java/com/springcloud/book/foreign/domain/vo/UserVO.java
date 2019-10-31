package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.domain.User;

public class UserVO extends User {
    private String token;

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
}
