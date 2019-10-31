package com.springcloud.book.foreign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.solr.SolrClientSafe;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.OrdinarySearchVO;
import com.springcloud.book.foreign.domain.vo.SingleSearchVO;
import com.springcloud.book.foreign.service.SolrSearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  普通检索
 * </p>
 *
 * @author grl
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/ordinary")
public class OrdinarySearchController extends BaseController{

    @Autowired
    private SolrSearchService solrSearchService;

    @Autowired
    private SolrClientSafe clientSafe;

    @GetMapping("/test")
    public Object test(){
        SolrQuery solrQuery = new SolrQuery("*:*");
        try {
            QueryResponse result = clientSafe.query(solrQuery, OverallConfig.ABSTRACT_CORE);
            List<AbstractSolr> beans = result.getBeans(AbstractSolr.class);
            return beans;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 普通检索----自由词检索
     * @param page current 当前页 size 每页显示条数，默认10
     * @param searchVO searchWorld 检索字段 searchValue 检索值 checkOut 勾选true,未勾选false token 登录用户账号
     * @return
     */
    @GetMapping("/free")
    public Object getFreeWorldSearchPage(Page page, OrdinarySearchVO searchVO){
        PageData pageData = new PageData();
        String searchValue = searchVO.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)){
            searchValue = searchValue.replaceAll(":"," ");
            searchVO.setSearchValue(searchValue);
        }
        this.solrSearch(true,page,searchVO, solrSearchService,pageData);
        return pageData;
    }

    /**
     * 普通检索----PMID检索
     * @param page current 当前页 size 每页显示条数，默认10
     * @param searchVO searchWorld 检索字段 searchValue 检索值 checkOut 勾选true,未勾选false token 登录用户账号
     * @return
     * [11 PMID]pmid
     */
    @GetMapping("/pmid")
    public Object pmidSearch(Page page, OrdinarySearchVO searchVO){
        PageData pageData = pageData = new PageData();;
        searchVO.setSearchWorld("11");
        this.solrSearch(true,page,searchVO, solrSearchService,pageData);
        return pageData;
    }


    /**
     * 普通检索----单篇检索
     * page current 当前页 size 每页显示条数，默认10
     * @param singleSearchVO titleMain 期刊名称/year 年/volume 卷/issue 期/startPage 起始页
     * @return
     */
    @GetMapping("/single")
    public Object singleSearch(Page page,SingleSearchVO singleSearchVO){
        PageData pageData = new PageData();
        List<Map> searchFieldList = new ArrayList<>();
        WebSolrDeals.singleSearch(singleSearchVO,searchFieldList);
        this.solrMoreFieldsSearch(true,page,singleSearchVO.getToken(),searchFieldList, solrSearchService,pageData,null);
        return pageData;
    }

}
