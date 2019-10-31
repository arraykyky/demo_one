package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

/**
 * 基础检索VO
 */
public class SearchVoBean {
        private String conn;
        //检索字段 默认检索所有字段
        private String searchWorld = "99";
        //检索值
        private String searchValue;
        //登录用户账号
        private String token;

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }

        public String getConn() {
            return conn;
        }

        public void setConn(String conn) {
            this.conn = conn;
        }

        public String getSearchWorld() {
            return searchWorld;
        }

        public void setSearchWorld(String searchWorld) {
            this.searchWorld = searchWorld;
        }

        public String getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(String searchValue) {
            this.searchValue = searchValue;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
}