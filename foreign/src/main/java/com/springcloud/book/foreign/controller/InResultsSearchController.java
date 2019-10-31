package com.springcloud.book.foreign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.AbstractFullTextLinks;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.*;
import com.springcloud.book.foreign.service.AbstractFullTextLinksService;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.util.SolrSearchExpression;
import com.springcloud.book.foreign.util.Tools;
import com.springcloud.book.foreign.util.infoProcessor.processorutil.ProcessorUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/result")
public class InResultsSearchController extends BaseController{

    Logger logger = LoggerFactory.getLogger(InResultsSearchController.class);

    @Autowired
    private SolrSearchService solrSearchService;

    @Autowired
    private AbstractFullTextLinksService fullTextLinksService;

    /**
     * 重新检索
     * @param page
     * @param ordinarySearchVO searchWorld 检索字段 searchValue 检索值 token 登录用户账号
     * @return
     */
    @GetMapping("/research")
    public Object research(Page page , OrdinarySearchVO ordinarySearchVO){
        PageData pageData = new PageData();
        this.searchYearForValue(ordinarySearchVO);
        this.solrSearch(false,page,ordinarySearchVO,solrSearchService,pageData);
        return pageData;
    }

    /**
     * 结果中检索
     * @param page
     * @param resultsSearchVO 结果中检索VO 包含必要参数：exp -- 历史检索表达式/sortWorld -- 排序字段号
     *                                                   /conn -- 逻辑连接词/searchWorld -- 检索字段
     *                                                   /searchValue -- 检索值/sortWorld 排序字段/sortType 排序类型
     * @return
     */
    @GetMapping("/insearch")
    public Object resultsSearch(Page page, ResultsSearchVO resultsSearchVO){
        PageData pageData = new PageData();
        List<Map> searchMapList = new ArrayList<>();
        //历史检索数据
        String exp = resultsSearchVO.getExp();
        if (StringUtils.isNotBlank(exp)){
            List<Map> oldSearchMapList = SolrSearchExpression.analysisExp(exp);
            searchMapList.addAll(oldSearchMapList);
        }
        //新的检索数据
        String searchValue = resultsSearchVO.getSearchValue();
        if (StringUtils.isNotBlank(searchValue)){
            //年份检索
            if (Integer.parseInt(resultsSearchVO.getSearchWorld()) == 13){
                String[] yearArray = searchValue.split("-");
                Integer start = null;
                Integer end = null;
                if (yearArray.length == 2){
                    if (Tools.isNumer(yearArray[0])){
                        start = Integer.valueOf(yearArray[0]);
                    }
                    if (Tools.isNumer(yearArray[1])){
                        end = Integer.valueOf(yearArray[1]);
                    }
                }else {
                    if (Tools.isNumer(yearArray[0]))
                        start = end = Integer.valueOf(yearArray[0]);
                }
                String searchYearValue = WebSolrDeals.solrDateSearchStr(start.toString(), end.toString());
                resultsSearchVO.setSearchValue(searchYearValue);
            }
            Map<String, Object> newSearchMap = WebSolrDeals.searchFieldMap(resultsSearchVO.getConn(), resultsSearchVO.getSearchWorld(), resultsSearchVO.getSearchValue());
            searchMapList.add(newSearchMap);
        }
        //历史数据和检索数据去重
        List<Map> resultMapList = new ArrayList<>();
        for (Map map : searchMapList){
            boolean b = resultMapList.stream().anyMatch(m -> m.get("searchWorld").equals(map.get("searchWorld"))
                    && m.get("searchValue").equals(map.get("searchValue")));
            if (!b){
                resultMapList.add(map);
            }
        }
        //排序设置 --
        SolrSelectResultSortVO solrSelectResultSortVO = null;
        if (resultsSearchVO.getSortWorld() != null && resultsSearchVO.getSortType() != null) {
            solrSelectResultSortVO = new SolrSelectResultSortVO();
            solrSelectResultSortVO.setSortWorld(resultsSearchVO.getSortWorld());
            solrSelectResultSortVO.setSortType(resultsSearchVO.getSortType());
        }
        this.solrMoreFieldsSearch(false,page,resultsSearchVO.getToken(),resultMapList,solrSearchService,pageData,solrSelectResultSortVO);
        return pageData;
    }

    /**
     * 导出摘要
     * @param abstractIds 选择摘要id
     * @param ed    导出数据VO 包含必要数据 exportType -- 导出类型：0/1 全部字段导出
     *                                                     2自定义导出字段，选择标记值为:1/true
     *                                                     (文献标题 title / 文献作者 author / 作者单位 unit
     *                                                     /期刊标题 journalTitle / 出版年份 year / 卷 volum / 期 issue / 页 page
     *                                                     /关键词 keyWorlds / 摘要 abstr / ISSN号 issn / 国际编号 note / 数据库提供商 dper)
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void exportAbstract(String abstractIds, ExprotAbsractDataVO ed, HttpServletResponse response) throws Exception {
        List<String> abstractIdList = new ArrayList<>(Arrays.asList(abstractIds.split(",")));
        String subStr = WebSolrDeals.createSubStr(abstractIdList);
        String sqStr = WebSolrDeals.ordinarySolrFreeWorldSearch("25",subStr,false);
        List<AbstractSolr> absttList = solrSearchService.getAbstractListSimpleFunction(sqStr);
        //将摘要信息转打印文件对象
        // exportType:0/1/2 "EndNote"/"NoteExpress"/"MyselfSet"
        String factoryName = "EndNote";
        if ("2".equals(ed.getExportType())){
            factoryName = "MyselfSet";
        }else if ("1".equals(ed.getExportType())){
            factoryName = "NoteExpress";
        }
        List<DocInfoVO> abstractList = ProcessorUtil.exportSolr(absttList,factoryName);
        WebSolrDeals.export(response,abstractList,ed);
    }

    /**
     *
     * @param page
     * @param ifSearchVO 影响因子查询VO 包含必要参数 minIfs -- 最小影响因子，默认值为0.0
     *                                                 maxIfs -- 最大影响因子，默认值为0.0
     * @return
     */
    @GetMapping("/ifsearch")
    public Object ifsSearch(Page page, IfSearchVO ifSearchVO){
        PageData pageData = new PageData();
        List<Map> searchMapList = new ArrayList<>();
        //历史检索数据,exp=null为重新检索/exp!=null为结果中检索
        String exp = ifSearchVO.getExp();
        if (StringUtils.isNotBlank(exp)){
            List<Map> oldSearchMapList = SolrSearchExpression.analysisExp(exp);
            searchMapList.addAll(oldSearchMapList);
        }
        Double min = ifSearchVO.getMinIfs();
        Double max = ifSearchVO.getMaxIfs();
        Map<String, Object> ifMap = WebSolrDeals.searchFieldMap("0", "12", "[" + min + " TO " + max + "]");
        searchMapList.add(ifMap);
        this.solrMoreFieldsSearch(false,page,ifSearchVO.getToken(),searchMapList,solrSearchService,pageData,null);
        return pageData;
    }

    /**
     * OriginalURL动态获取
     * @param fullTextLinksVO 摘要全文链接VO 包含必要参数 urlType -- 链接类型
     *                                                       europePubMedCentral:1 = pubmed
     *                                                       thridLink:2 = doi
     *                                                       elsevierScience:3 = pii
     *                                                       pubMedCentral:4 = pmc;
     *                                                       urlContent -- 链接内容
     * @return
     */
    @GetMapping("/links")
    public Object getOriginalURL(AbstractFullTextLinksVO fullTextLinksVO){
        PageData pageData = new PageData();
        try {
            AbstractFullTextLinks fullTextLinks = fullTextLinksService.getFullTextLinksByUrlType(fullTextLinksVO);
            String links = fullTextLinks.getUrlLink();
            String[] linksArray = links.split(";");
            List<String> linksList = new ArrayList<>();
            for (String link : linksArray){
                linksList.add(link + fullTextLinksVO.getUrlContent());
            }
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,linksList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }




}
