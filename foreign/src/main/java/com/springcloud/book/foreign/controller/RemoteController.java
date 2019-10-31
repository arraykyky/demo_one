package com.springcloud.book.foreign.controller;

import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.solr_been.JournalSolr;
import com.springcloud.book.foreign.service.SolrSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/remote")
public class RemoteController {

    @Autowired
    private SolrSearchService solrSearchService;

    @GetMapping("/abstract_info")
    public Object getAbstractSolrDataByUniqueId(String abstractId){
        PageData pageData = new PageData();
        try {
            Map<String, Object> abstractMap = solrSearchService.searchSolrBeanMapByUniqueId("abstract_id", abstractId, OverallConfig.ABSTRACT_CORE);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,abstractMap);
        } catch (Exception e) {
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }


    @GetMapping("/journal_info")
    public Object getJournalSolrDataByNLMID(String journalWorld, String selectValue){
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(journalWorld) && StringUtils.isNotBlank(selectValue)){
                JournalSolr journalSolr = solrSearchService.searchSolrJournalSolrDataByNLMID(journalWorld, selectValue);
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
                pageData.put(OverallConfig.DATA,journalSolr);
            }else {
                throw new RuntimeException("检索期刊数据的字段和值不能为空!");
            }
        } catch (Exception e) {
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

}
