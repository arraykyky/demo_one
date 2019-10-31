package com.springcloud.book.foreign.controller;

import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.service.SolrSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abstract")
public class AbstractController extends BaseController{

    @Autowired
    private SolrSearchService solrSearchService;

    @GetMapping("/info")
    public Object getAbstractInfo(String abstractId){
        PageData pageData = new PageData();
        this.solrAbstractByUniqueId(pageData,solrSearchService,abstractId);
        return pageData;
    }
}
