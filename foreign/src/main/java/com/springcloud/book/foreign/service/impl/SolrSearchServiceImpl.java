package com.springcloud.book.foreign.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.config.solr.SolrClientSafe;
import com.springcloud.book.foreign.domain.SearchesHistory;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.solr_been.JournalSolr;
import com.springcloud.book.foreign.service.HistorySearchService;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.service.impl.solr_service_deals.SolrQueryDeal;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ordinarySearchServiceImpl")
public class SolrSearchServiceImpl implements SolrSearchService {

    private Logger logger = LoggerFactory.getLogger(SolrSearchServiceImpl.class);

    @Autowired
    private SolrClientSafe client;

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private HistorySearchService historySearchService;

    @Override
    public void searchSolr(Page page, String queryStr, String sortFieldStr, Integer sortType, boolean isHightLight, String... hightFields) throws Exception {
        SolrQuery sq = new SolrQuery(queryStr);
        //SolrQuery分页和排序设置
        SolrQueryDeal.sqPageSortDeal(true,sq,page,sortFieldStr,sortType);
        //SolrQuery默认返回字段
        SolrQueryDeal.sqDefaultShowFieldsDeal(sq,null);
        ////SolrQuery高亮设置
        SolrQueryDeal.sqHightLightDeal(isHightLight,sq,hightFields);
        //打印SolrQuery串
        logger.info("********************查询串: "+sq);
        //封装最终结果
        SolrQueryDeal.sqJournalResultDeal(client, sq, page);
    }

    @Override
    public JournalSolr searchSolrJournalSolrDataByNLMID(String journalWorld, String selectValue) throws Exception {
        SolrQuery sq = new SolrQuery(journalWorld +":\"" + selectValue + "\"");
        sq.setStart(0);
        sq.setRows(1);
        QueryResponse queryResponse = client.query(sq,OverallConfig.JOURNAL_CORE);
        List<JournalSolr> journalBeans = queryResponse.getBeans(JournalSolr.class);
        return journalBeans.get(0);
    }


    @Override
    public Map<String, Object> searchSolr(boolean isSaveSearchNote, String token, List<Map> historySearchList, Page page, String queryStr, String sortFieldStr, Integer sortType,
                                          boolean isHightLight, String... hightFields) throws Exception {
        SolrQuery sq = new SolrQuery(queryStr);
        //设置查询字段
        String[] queryFields = {"abstract_id","article_title","authors_str","authors_string","ISSN",
                "medline_TA","medline_date","volume","issue","medline_page","key_words_str",
                "affiliation_string","journal_indexes_str","original_URL","recent_IF","partition_jcr",
                "partition_zky_str"};
        sq.setFields(queryFields);
        //SolrQuery分页和排序设置
        SolrQueryDeal.sqPageSortDeal(true,sq,page,sortFieldStr,sortType);
        //SolrQuery默认返回字段
        SolrQueryDeal.sqDefaultShowFieldsDeal(sq,null);
        ////SolrQuery高亮设置
        SolrQueryDeal.sqHightLightDeal(isHightLight,sq,hightFields);
        //SolrQuery分面设置
        String[] facetField = {"medline_date","language","journal_indexes"};
        SolrQueryDeal.sqFacetDeal(sq,facetField);
        //打印SolrQuery串
        logger.info("********************Query String:"+sq);
        //获取结果
        long start = System.currentTimeMillis();
        Map<String, Object> resultMap = SolrQueryDeal.sqResultDeal(client, sq, page);
        long end = System.currentTimeMillis();
        logger.info("********************Query finished,use time" + (end - start) + "Millisecond");
        //获取检索表达式
        String exp = SolrQueryDeal.forSearchCreatExp(historySearchList);
        //返回exp检索表达式
        resultMap.put("exp",exp);
        if (isSaveSearchNote){
            //历史检索数据封装--记录检索数据
            SearchesHistory searchesHistory = new SearchesHistory();
            SolrQueryDeal.historyData(searchesHistory,exp,token,redisServer, historySearchService,page.getTotal(),sq.get("q"));
            historySearchService.addHistoryRecord(searchesHistory);
        }
        logger.info("********************Query record use time" + (System.currentTimeMillis() - end) + "Millisecond");
        return resultMap;
    }

    @Override
    public Map<String, Object> searchSolrBeanMapByUniqueId(String uniqueId, String uniqueValue, String searCore) throws Exception {
        Map<String,Object> map = new HashMap<>();
        SolrQuery solrQuery = new SolrQuery(uniqueId + ":\"" + uniqueValue+"\"");
        logger.info("********************Query String: "+solrQuery);
        QueryResponse result = client.query(solrQuery, searCore);
        if (searCore.equals(OverallConfig.JOURNAL_CORE)){
            List<JournalSolr> beans = result.getBeans(JournalSolr.class);
            if (beans != null && !beans.isEmpty()){
                JournalSolr bean = beans.get(0);
                HashMap beanMap = JSON.parseObject(bean.toString(), HashMap.class);
                map.putAll(beanMap);
            }
        }else if (searCore.equals(OverallConfig.ABSTRACT_CORE)){
            List<AbstractSolr> beans = result.getBeans(AbstractSolr.class);
            if (beans != null && !beans.isEmpty()){
                AbstractSolr bean = beans.get(0);
                HashMap beanMap = JSON.parseObject(bean.toString(), HashMap.class);
                map.putAll(beanMap);
            }
        }
        return map;
    }

    @Override
    public QueryResponse simpleSortQueryResponse(String sqStr, String sortField, int sortType)throws Exception {
        SolrQuery sq = new SolrQuery(sqStr);
        //SolrQuery分面设置
        String[] facetField = {"medline_date"};
        SolrQueryDeal.sqOnlyFacet(sq,facetField);
        SolrQueryDeal.sqPageSortDeal(true,sq,null,sortField,sortType);
        //打印SolrQuery串
        logger.info("********************Query String:"+sq);
        return SolrQueryDeal.queryResponseResultDeal(client, sq);
    }

    @Override
    public List<AbstractSolr> getAbstractListSimpleFunction(String queryStr) throws Exception {
        SolrQuery sq = new SolrQuery(queryStr);
        SolrQueryDeal.sqPageSortDeal(false,sq,null,null,null);
        //打印SolrQuery串
        logger.error("********************Query String: "+sq);
        return SolrQueryDeal.getAbstractList(client, sq);
    }

    @Override
    public List<AbstractSolr> getAbstractSolrWithQueryField(String sqStr, String[] queryField) throws Exception {
        SolrQuery sq = new SolrQuery(sqStr);
        sq.setFields(queryField);
        return SolrQueryDeal.getAbstractList(client,sq);
    }

    @Override
    public QueryResponse searchPivot(SolrQuery sq) throws Exception {
        return client.query(sq,OverallConfig.ABSTRACT_CORE);
    }
}
