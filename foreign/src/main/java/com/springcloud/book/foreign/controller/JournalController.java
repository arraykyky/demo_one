package com.springcloud.book.foreign.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;
import com.springcloud.book.foreign.enums.SolrSearchFieldEnum;
import com.springcloud.book.foreign.service.JournalService;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  期刊导航
 * </p>
 *
 * @author grl
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/journal")
public class JournalController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(JournalController.class);

    @Autowired
    private SolrSearchService solrSearchService;

    @Autowired
    private JournalService journalService;

    /**
     *  期刊导航 期刊检索
     * @param page
     * @param journalNavigationMenuVO 期刊检索VO 包含必要字段searchWorld —— 检索字段
     *                                                           //101--ISSN ; 102--主题范畴 ; 103--学科分类 ; 104--语言 ; 105--期刊分类(收录机构) ;
     *                                                           107--期刊首字母 ; 108109 --期刊英文108/期刊中文109
     *                                                           searchValue —— 检索值
     *                                                           sortWorld   —— 排序字段，默认106以影响因子
     *                                                           sortType    —— 排序类型，默认1以大于0升序，小于0降序
     * @return
     */
    @GetMapping("/list")
    public Object getJournalPageByClassificationMenuValue(Page page, JournalNavigationMenuVO journalNavigationMenuVO){
        PageData pageData = new PageData();
        List<Map> searchFieldList = new ArrayList<>();
        //101--ISSN ; 102--主题范畴 ; 103--学科分类 ; 104--语言 ; 105--期刊分类(收录机构) ; 107--期刊首字母 ; 108109 --期刊英文108/期刊中文109
        String searchWorld = journalNavigationMenuVO.getSearchWorld();
        if (searchWorld != null && !"".equals(searchWorld)){
            if ("107".equals(searchWorld)){
                Map<String, Object> map0 = WebSolrDeals.searchFieldMap("", searchWorld, journalNavigationMenuVO.getSearchValue() + "*");
                Map<String, Object> map1 = WebSolrDeals.searchFieldMap("1", searchWorld, journalNavigationMenuVO.getSearchValue().toLowerCase() + "*");
                searchFieldList.add(map0);
                searchFieldList.add(map1);
            }else if ("101".equals(searchWorld) || "108109".equals(searchWorld)) {
                String value = Tools.getTextTrim(journalNavigationMenuVO.getSearchValue().replaceAll(":", " ").replaceAll(" ", " "));
                if ("108109".equals(searchWorld)) {
                    if (Tools.isChinese(value)) {
                        searchWorld = "109";
                    } else {
                        searchWorld = "108";
                    }
                }
                Map<String, Object> mapOthers = WebSolrDeals.searchFieldMap("", searchWorld, "\"" + value + "\"");
                searchFieldList.add(mapOthers);
                if ("108".equals(searchWorld)) {
                    Map<String, Object> mapOthersSimp = WebSolrDeals.searchFieldMap("1", "110", "\"" + value + "\"");
                    searchFieldList.add(mapOthersSimp);
                }
            }else {
                if ("103".equals(searchWorld)){
                    journalNavigationMenuVO.setSearchValue("\""+ journalNavigationMenuVO.getSearchValue() +"\"");
                }
                Map<String, Object> mapOthers = WebSolrDeals.searchFieldMap("", searchWorld, journalNavigationMenuVO.getSearchValue());
                searchFieldList.add(mapOthers);
            }
        }
        String sortField = null;
        try {
            if (searchFieldList.isEmpty()){
                sortField = SolrSearchFieldEnum.getTableFieldByKey(Integer.valueOf(journalNavigationMenuVO.getSortWorld()));;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        Integer sortType = journalNavigationMenuVO.getSortType();
        this.solrMoreFieldsSearchJournal(page,searchFieldList,solrSearchService,sortField,sortType,pageData);
        return pageData;
    }

    /**
     *  期刊导航 期刊详情
     * @param journalNavigationMenuVO 包含必要字段journalId —— 期刊id
     * @return
     */
    @GetMapping("/info")
    public Object getJournalInfoByJournalId(JournalNavigationMenuVO journalNavigationMenuVO){
        PageData pageData = new PageData();
        this.solrJournalByJournalId(pageData,solrSearchService,journalNavigationMenuVO);
        return pageData;
    }

    /**
     * 期刊导航 期刊详情的入库期卷的年份数据
     * @param journalNavigationMenuVO 包含必要字段journalId —— 期刊id
     * @return
     * @throws Exception
     */
    @GetMapping("/year_counts")
    public Object getJournalYearCounts(JournalNavigationMenuVO journalNavigationMenuVO){
        PageData pageData = new PageData();
        try {
            List<Map> yearList = journalService.getJournalYearCounts(journalNavigationMenuVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,yearList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     *
     * @param page
     * @param journalNavigationMenuVO 包含必要字段 journalId —— 期刊id
     *                                                 year      —— 年
     *                                                 volume    —— 卷
     *                                                 issue     —— 期
     * @param token 用户登录标记
     * @return
     */
    @GetMapping("/abstracts")
    public Object getCurrentJournalVolumsIssueAbstracts(Page page,JournalNavigationMenuVO journalNavigationMenuVO,String token){
        PageData pageData = new PageData();
        List<Map> searchFieldList = new ArrayList<>();
        if (journalNavigationMenuVO.getJournalId() != null && !"".equals(journalNavigationMenuVO.getJournalId())){
            Map<String,Object> jIdMap = WebSolrDeals.searchFieldMap("","26",journalNavigationMenuVO.getJournalId());
            searchFieldList.add(jIdMap);
        }
        if (journalNavigationMenuVO.getYear() != null && !"".equals(journalNavigationMenuVO.getYear())){
            Integer year = journalNavigationMenuVO.getYear();
            if (year != null){
                String dateStr = WebSolrDeals.solrDateSearchStr(String.valueOf(year),String.valueOf(year));
                Map<String,Object> jIdMap = WebSolrDeals.searchFieldMap("","13",dateStr);
                searchFieldList.add(jIdMap);
            }
        }
        if (journalNavigationMenuVO.getVolume() != null && !"".equals(journalNavigationMenuVO.getVolume())){
            Map<String,Object> jVdMap = WebSolrDeals.searchFieldMap("","14",journalNavigationMenuVO.getVolume());
            searchFieldList.add(jVdMap);
            //卷数不为空时，再拼接期信息
            if (journalNavigationMenuVO.getIssue() != null && !"".equals(journalNavigationMenuVO.getIssue())){
                Map<String,Object> jIdMap = WebSolrDeals.searchFieldMap("","15",journalNavigationMenuVO.getIssue());
                searchFieldList.add(jIdMap);
            }
        }else{
            Map<String,Object> jVdMap = WebSolrDeals.searchFieldMap("","14","*");
            searchFieldList.add(jVdMap);
            Map<String,Object> jIdMap = WebSolrDeals.searchFieldMap("","15","*");
            searchFieldList.add(jIdMap);
        }

        this.solrMoreFieldsSearch(false,page,token,searchFieldList,solrSearchService,pageData,null);
        return pageData;
    }

}

