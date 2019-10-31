package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;

import java.util.Map;

public interface PubMedSearchService {
    void getPubMedPage(PageData pageData, Page page) throws Exception;
    Map<String,String> getInfo(PageData pageData) throws Exception;
}
