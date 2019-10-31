package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.solr_been.JournalSolr;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.List;
import java.util.Map;

public interface SolrSearchService {
    /**
     * 期刊
     * @param page 分页对象
     * @param queryStr 检索传
     * @param sortFieldStr 排序字段
     * @param sortType 排序类型大于0升序小于0降序
     * @param isHightLight 是否开启高亮
     * @param hightFields 高亮字段
     */
    void searchSolr(Page page, String queryStr, String sortFieldStr, Integer sortType, boolean isHightLight, String... hightFields) throws Exception;

    /**
     * 通过nlm_id获取期刊
     * @param journalWorld 检索字段
     * @param selectValue   检索值
     * @return
     */
    JournalSolr searchSolrJournalSolrDataByNLMID(String journalWorld, String selectValue) throws Exception;

    /**
     * 摘要
     * @param isSaveSearchNote
     * @param token
     * @param historySearchList
     * @param page
     * @param queryStr
     * @param sortFieldStr
     * @param sortType
     * @param isHightLight
     * @param hightFields
     * @return
     * @throws Exception
     */
    Map<String, Object> searchSolr(boolean isSaveSearchNote, String token, List<Map> historySearchList, Page page, String queryStr,
                                   String sortFieldStr, Integer sortType, boolean isHightLight, String... hightFields) throws Exception;

    //solr通过主键获取唯一数据
    Map<String,Object> searchSolrBeanMapByUniqueId(String uniqueId, String uniqueValue, String searCore) throws Exception;
    //简单分面查询
    QueryResponse simpleSortQueryResponse(String sqStr, String sortField, int sortType) throws Exception;
    //摘要数据
    List<AbstractSolr> getAbstractListSimpleFunction(String queryStr) throws Exception;
    //指定字段查询
    List<AbstractSolr> getAbstractSolrWithQueryField(String sqStr, String[] queryField) throws Exception;

    QueryResponse searchPivot(SolrQuery sq) throws Exception;

}
