package com.springcloud.book.foreign.service.impl.solr_service_deals;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.config.solr.SolrClientSafe;
import com.springcloud.book.foreign.domain.SearchesHistory;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.solr_been.JournalSolr;
import com.springcloud.book.foreign.enums.LanguageEnum;
import com.springcloud.book.foreign.service.HistorySearchService;
import com.springcloud.book.foreign.service.impl.deals.SearchResultDeal;
import com.springcloud.book.foreign.util.DateUtil;
import com.springcloud.book.foreign.util.SolrSearchExpression;
import com.springcloud.book.foreign.util.Tools;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class SolrQueryDeal {
    //SolrQuery分页和排序设置
    public static void sqPageSortDeal(boolean isPage, SolrQuery sq, Page page, String sortFieldStr, Integer sortType){
        //查询超时时间
        sq.setTimeAllowed(3*60*1000);
        //分页设置
        if (isPage){
            if (page == null){
                sq.setStart(0);
                sq.setRows(0);
            }else {
                sq.setStart((int)((page.getCurrent() - 1) * page.getSize()));
                sq.setRows((int)page.getSize());
            }
        }else {
            sq.setStart(0);
            sq.setRows(OverallConfig.SOLR_TOTAL_NUM);
        }
        //设置排序 sortFieldStr == null // ""时按照相关度评分排序
        if (sortFieldStr != null && !"".equals(sortFieldStr)){
            if (sortType != null && !"".equals(sortType)){
                //升序
                if (sortType > 0){
                    sq.setSort(sortFieldStr,SolrQuery.ORDER.asc);
                }
                //降序
                else if (sortType <0){
                    sq.setSort(sortFieldStr,SolrQuery.ORDER.desc);
                }
            }
        }else{
            sq.setSort("score",SolrQuery.ORDER.desc);
        }
    }

    //SolrQuery分页和排序设置
    public static void sqMoreFielsSortDeal(SolrQuery sq,Integer sortType,String... sortFieldStr){
        //查询超时时间
        sq.setTimeAllowed(1*60*1000);
        //分页设置
        sq.setStart(0);
        sq.setRows(OverallConfig.SOLR_TOTAL_NUM);
        //设置排序 sortFieldStr == null // ""时按照相关度评分排序
        if (sortFieldStr != null && !"".equals(sortFieldStr)){
            if (sortType != null && !"".equals(sortType)){
                //升序-从小到大
                List<SolrQuery.SortClause> list = new ArrayList<>();
                if (sortType > 0){
                    for (String sortFile:sortFieldStr){
                        //sq.setSort(sortFile,SolrQuery.ORDER.asc);
                        list.add(new SolrQuery.SortClause(sortFile, SolrQuery.ORDER.asc));
                    }
                }
                //降序
                else if (sortType <0){
                    for (String sortFile:sortFieldStr){
                        //sq.setSort(sortFile,SolrQuery.ORDER.desc);
                        list.add(new SolrQuery.SortClause(sortFile, SolrQuery.ORDER.desc));
                    }
                }
                if (!list.isEmpty()){
                    sq.setSorts(list);
                }
            }
        }
    }


    //SolrQuery默认返回字段
    public static void sqDefaultShowFieldsDeal(SolrQuery sq,String... strs){
        if (strs != null && strs.length > 0){
            sq.setFields(strs);
        }
    }

    //SolrQuery高亮设置
    public static void sqHightLightDeal(boolean isHightLight,SolrQuery sq ,String... hightFields){
        if (isHightLight && hightFields.length > 0){
            sq.setHighlight(true);
            for (String field : hightFields){
                sq.addHighlightField(field);
            }
            sq.setHighlightFragsize(100000);
            sq.setHighlightSimplePre("<span style='color:red'>");
            sq.setHighlightSimplePost("</span>");
        }
    }

    //SolrQuery分面设置
    public static void sqFacetDeal(SolrQuery sq,String...facetFields){
        //分面查询
        sq.setFacet(true);
        sq.setFacetSort("index");
        sq.addFacetField(facetFields);
        sq.setFacetLimit(1000);
        //分面查询 journal_indexes:OA
        //sq.addFacetQuery("{!key=\"有免费全文\"}had_free_full_text:1");
        sq.addFacetQuery("{!key=\"有免费全文\"}journal_indexes:OA");
        sq.addFacetQuery("{!key=\"有全文\"}had_full_text:1");
        sq.addFacetQuery("{!key=\"有摘要\"}abstract_text:RESULTS");
    }

    //分面设置
    public static void sqOnlyFacet(SolrQuery sq,String...facetFields){
        //分面查询
        sq.setFacet(true);
        sq.setFacetSort("index");
        sq.addFacetField(facetFields);
        sq.setFacetLimit(OverallConfig.SOLR_TOTAL_NUM);
    }

    //获取“期刊”结果
    public static void sqJournalResultDeal(SolrClientSafe client, SolrQuery sq, Page page)throws Exception{
        //数据集合
        List<JournalSolr> beansList = new ArrayList<>();
        QueryResponse qrst = client.query(sq,OverallConfig.JOURNAL_CORE);
        //从结果中获取beans
        List<JournalSolr> beans = qrst.getBeans(JournalSolr.class);
        //获取solr DocumentList
        SolrDocumentList documents = qrst.getResults();
        //设置分页对象总页数
        page.setTotal((int)documents.getNumFound());
        // 获取高亮显示信息
        Map<String, Map<String, List<String>>> highlighting = qrst.getHighlighting();
        //获取设置高亮字段
        String[] highlightfields = sq.getHighlightFields();
        //获取bean的注解词与对应字段
        Field[] fields = JournalSolr.class.getDeclaredFields();
        Map<String,String> annotation_field = new HashMap<>();
        for (Field f : fields){
            org.apache.solr.client.solrj.beans.Field field = f.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
            annotation_field.put(field.value(),f.getName());
        }
        if (highlighting != null && !"".equals(highlighting) && highlightfields != null){
            for (SolrDocument doc : documents) {
                Map<String, List<String>> pro = highlighting.get(doc.get("journal_id"));
                for (JournalSolr js : beans){
                    if (doc.get("journal_id").equals(js.getJournalId())){
                        for (String str : highlightfields){
                            List<String> hightDocs = pro.get(str);
                            if (hightDocs != null){
                                Field f = JournalSolr.class.getDeclaredField(annotation_field.get(str));
                                f.setAccessible(true);
                                org.apache.solr.client.solrj.beans.Field abField = f.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
                                if (abField != null && abField.value().equals(str)){
                                    f.setAccessible(true);
                                    f.set(js,hightDocs.get(0));
                                }
                            }
                        }
                        beansList.add(js);
                        break;
                    }
                }
            }
        }else {
            beansList.addAll(beans);
        }
        page.setRecords(beansList);
    }

    //获取“文献”结果--特性设置
    public static Map<String,Object> sqResultDeal(SolrClientSafe client, SolrQuery sq, Page page)throws Exception{
        //数据bean集合
        List<AbstractSolr> asList = new ArrayList<>();
        //语言统计结果集合
        List<Map> languageList = new ArrayList<>();
        //每年统计结果集合
        List<PageData> yearList = new ArrayList<>();
        //分组统计年份数据
        Map pd = new LinkedHashMap();
        //收录机构统计结果集合
        List<String> indexesList = new ArrayList<>();
        //QueryResponse qrst = SafeSolrClientUtil.query(OverallConfig.SOLR_USER, OverallConfig.SOLR_PASSWORD, client, sq, OverallConfig.ABSTRACT_CORE);
        QueryResponse qrst =client.query(sq,OverallConfig.ABSTRACT_CORE);
        //从结果中获取beans
        List<AbstractSolr> beans = qrst.getBeans(AbstractSolr.class);
        //获取solr DocumentList
        SolrDocumentList documents = qrst.getResults();
        //设置分页对象总页数
        page.setTotal((int)documents.getNumFound());
        // 获取高亮显示信息
        Map<String, Map<String, List<String>>> highlighting = qrst.getHighlighting();
        //获取分面数据
        facetHandle(qrst,yearList,languageList,indexesList);
        //获取分面查询结果
        Map<String, Integer> facetQuery = qrst.getFacetQuery();
        //获取设置高亮字段
        String[] highlightfields = sq.getHighlightFields();
        //获取bean的注解词与对应字段
        Field[] fields = AbstractSolr.class.getDeclaredFields();
        Map<String,String> annotation_field = new HashMap<>();
        for (Field f : fields){
            org.apache.solr.client.solrj.beans.Field field = f.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
            if (field != null){
                annotation_field.put(field.value(),f.getName());
            }
        }
        if (highlighting != null && !"".equals(highlighting) && highlightfields != null){
            for (SolrDocument doc : documents) {
                Map<String, List<String>> pro = highlighting.get(doc.get("abstract_id"));
                for (AbstractSolr as : beans){
                    if (doc.get("abstract_id").equals(as.getAbstractId())){
                        for (String str : highlightfields){
                            List<String> hightDocs = pro.get(str);
                            if (hightDocs != null){
                                Field f = AbstractSolr.class.getDeclaredField(annotation_field.get(str));
                                f.setAccessible(true);
                                org.apache.solr.client.solrj.beans.Field abField = f.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
                                if (abField != null && abField.value().equals(str)){
                                    f.setAccessible(true);
                                    f.set(as,hightDocs.get(0));
                                }
                            }
                        }
                        asList.add(as);
                        break;
                    }
                }
            }
        }else {
            asList.addAll(beans);
        }
        page.setRecords(asList);
        Map<String ,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("language",languageList);
        map.put("indexes",indexesList);
        map.put("fileFormat",facetQuery);
        map.put("years",yearHandle(yearList));
        return map;
    }

    //获取分面数据
    private static void facetHandle(QueryResponse qrst,List<PageData> yearList,List<Map> languageList,List<String> indexesList) throws Exception {
        //获取分面结果
        List<FacetField> facetFields = qrst.getFacetFields();
        for (FacetField ff : facetFields){
            List<FacetField.Count> values = ff.getValues();
            if (ff.getName().equals("medline_date")){
                for (FacetField.Count fc : values){
                    String javaYear = new SimpleDateFormat("yyyy").format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(fc.getName()).getTime() + 28800000);
                    PageData pageData = new PageData();
                    pageData.put("year",javaYear);
                    pageData.put("num",fc.getCount());
                    yearList.add(pageData);
                }
            }else if (ff.getName().equals("language")){
                for (FacetField.Count fc : values){
                    String lanCh = LanguageEnum.getLangCH(fc.getName());
                    long count = fc.getCount();
                    if (lanCh != null && count > 0){
                        Map lanMap = new HashMap();
                        lanMap.put("EN",fc.getName());
                        lanMap.put("CH",lanCh);
                        lanMap.put("NUM",count);
                        languageList.add(lanMap);
                    }
                }
            }else if (ff.getName().equals("journal_indexes")){
                for (FacetField.Count fc : values){
                    if (fc.getName().equals("Core Journal") || fc.getName().equals("Medline")
                            || fc.getName().equals("OA") || fc.getName().equals("SCIE")
                            || fc.getName().equals("F1000")){
                        indexesList.add(fc.toString());
                    }
                }
            }
        }
    }

    //对每年的统计数据分组展示
    private static List yearHandle(List<PageData> yearList){
        //对每年的统计数据分组展示
        int year = DateUtil.getCurrentYear();
        //分组统计年份数据
        Map pd = new LinkedHashMap();
        for (PageData p : yearList){
            SearchResultDeal.yearSta(p,pd,year);
        }
        List list = SearchResultDeal.map2List(pd);
        Collections.reverse(list);
        return list;
    }

    //solr“文献”检索表达式生成通用方法
    public static String forSearchCreatExp(List<Map> historySearchList){
        String exp = null;
        List<Integer> conditions = new ArrayList<>();
        List<Integer> texts = new ArrayList<>();
        List<String> values = new ArrayList<>();
        int index = 0;
        for (Map map : historySearchList){
            if (!"".equals(map.get("conn").toString())){
                int con = Integer.valueOf(map.get("conn").toString());
                conditions.add(con);
            }else if (index == 0){
                conditions.add(-1);
            }else {
                conditions.add(0);
            }
            int sw = Integer.valueOf(map.get("searchWorld").toString());
            texts.add(sw);
            String sv = map.get("searchValue").toString();
            if (sv.equals("*") || sv.equals("")){
                values.add("EMPTY");
            }else {
                values.add(sv);
            }
            index ++;
        }
        if (!conditions.isEmpty() &&  !texts.isEmpty() && !values.isEmpty()){
            exp = SolrSearchExpression.creatExp(conditions.toArray(new Integer[conditions.size()]),texts.toArray(new Integer[texts.size()]),values.toArray(new String[values.size()]));
        }
        return exp;
    }

    public static void historyData(SearchesHistory searchesHistory, String exp, String token, RedisServer redisServer, HistorySearchService historySearchService, Long total, String q) {
        String loginUserId = redisServer.get(Constants.getUserModuleCacheKeyId(token)).toString();
        searchesHistory.setSearchesUserid(loginUserId);
        int count = historySearchService.getCurrentUserHistoryCount(searchesHistory);
        searchesHistory.setSearchesId(Tools.getUUID());
        searchesHistory.setSearchesNum(String.valueOf(count + 1));
        searchesHistory.setSearchesStatement(exp);
        searchesHistory.setSearchesCounts(total.intValue());
        searchesHistory.setSearchesSql(q);
        searchesHistory.setSearchesTime(DateUtil.convertDateToLocalDateTime(new Date()));
    }

    //获取“文献”结果--QueryResponse对象
    public static QueryResponse queryResponseResultDeal(SolrClientSafe client, SolrQuery sq) throws Exception{
        QueryResponse qrst = client.query(sq, OverallConfig.ABSTRACT_CORE);
        return qrst;
    }

    //获取“文献”结果--AbstractSolr对象
    public static List<AbstractSolr> getAbstractList(SolrClientSafe client, SolrQuery sq) throws Exception {
        QueryResponse qrst = SolrQueryDeal.queryResponseResultDeal(client,sq);
        return qrst.getBeans(AbstractSolr.class);
    }
}
