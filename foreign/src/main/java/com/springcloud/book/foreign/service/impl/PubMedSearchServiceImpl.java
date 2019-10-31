package com.springcloud.book.foreign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.service.PubMedSearchService;
import com.springcloud.book.foreign.util.pubmed.AbstractMXLParser;
import com.springcloud.book.foreign.util.pubmed.DocSumParser;
import com.springcloud.book.foreign.util.pubmed.PubmedSearch;
import com.springcloud.book.foreign.util.pubmed.Result;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PubMedSearchServiceImpl implements PubMedSearchService {

    //pubmed-search
    @Override
    public void getPubMedPage(PageData pageData, Page page) throws Exception {
        PubmedSearch search = new PubmedSearch();
        long index = (page.getCurrent() - 1) * page.getSize();
        Result result = search.query(pageData.get("term").toString(),index,page.getSize());
        //xml数据解析，数据集合
        String content = result.content;
        if (content != null){
            page.setTotal(result.count);
            DocSumParser ds = new DocSumParser();
            List<?> list  = ds.parse(content);
            //封装分页数据
            page.setRecords(list);
        }
    }

    //PubMed检索 详情
    @Override
    public Map<String,String> getInfo(PageData pageData) throws Exception {
        String id = pageData.getString("id");
        PubmedSearch search = new PubmedSearch();
        String info = search.fetch(id);
        AbstractMXLParser axp = new AbstractMXLParser();
        return axp.parse(info);
    }
}
