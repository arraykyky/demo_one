package com.springcloud.book.foreign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.exception.CustomException;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;
import com.springcloud.book.foreign.domain.vo.OrdinarySearchVO;
import com.springcloud.book.foreign.domain.vo.SolrSelectResultSortVO;
import com.springcloud.book.foreign.enums.SolrSearchFieldEnum;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.util.DateUtil;
import com.springcloud.book.foreign.util.Tools;
import com.springcloud.book.foreign.util.translate.TransApi;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BaseController {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	//solr单字段“文献”检索通用方法
	public void solrSearch(boolean isSaveSearchNote, Page page, OrdinarySearchVO searchVO ,
						   SolrSearchService solrSearchService, PageData pageData) {
		try {
			List<Map> historySearchList = new ArrayList<>();
			String searchWorld = searchVO.getSearchWorld();
			String searchValue = searchVO.getSearchValue();
			boolean isCheckOut = searchVO.getCheckOut();
			//记录此次检索条件
			Map<String ,String> map = new HashMap();
			map.put("conn","");
			map.put("searchWorld",searchWorld);
			if (searchValue != null && !"".equals(searchValue)){
				if (isCheckOut){
					map.put("searchValue","\"" + searchValue + "\"");
				}else {
					map.put("searchValue",searchValue);
				}
			}else {
				map.put("searchValue","*");
			}
			historySearchList.add(map);
			//获取检索串
			String qStr = WebSolrDeals.ordinarySolrFreeWorldSearch(searchWorld,searchValue,isCheckOut);
			//自由词检索--跳词检索
			//qStr = WebSolrDeals.ordinarySolrFreeWorldSearchForJump(qStr,searchWorld,searchValue,historySearchList);
			//获取需设置高亮字段
			String[] str = WebSolrDeals.ordinarySolrFreeWorldSearchHightFields(searchWorld);
			Map<String, Object> result = solrSearchService.searchSolr(isSaveSearchNote,searchVO.getToken(),historySearchList,page, qStr, null,-1, true, str);
			//封装返回结果数据
			result.put(OverallConfig.LASTTERM,historySearchList);
			pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
			pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
			pageData.put(OverallConfig.DATA,result);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
			pageData.put(OverallConfig.MSG,e.getMessage());
		}
	}


	//solr多字段“文献”检索通用方法
	public void solrMoreFieldsSearch(boolean isSaveSearchNote, Page page, String token, List<Map> searchFieldList, SolrSearchService solrSearchService, PageData pageData, SolrSelectResultSortVO solrSelectResultSortVO){
		List<Map> historySearchList = new ArrayList<>();
		try {
			//记录此次检索条件
			historySearchList.addAll(searchFieldList);
			//获取检索串
			String qStr = WebSolrDeals.ordinarySolrFreeWorldSearch(searchFieldList);
			//获取需设置高亮字段
			String[] str = WebSolrDeals.ordinarySolrFreeWorldSearchHightFields(searchFieldList);
			//默认排序值
			String sortFieldStr = null;
			Integer sortType = -1;
			if (solrSelectResultSortVO != null){
				sortFieldStr = SolrSearchFieldEnum.getTableFieldByKey(solrSelectResultSortVO.getSortWorld());
				sortType = solrSelectResultSortVO.getSortType();
			}
			Map<String, Object> result = solrSearchService.searchSolr(isSaveSearchNote,token,historySearchList,page, qStr, sortFieldStr, sortType, true, str);
			//封装返回结果数据
			result.put(OverallConfig.LASTTERM,historySearchList);
			pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
			pageData.put(OverallConfig.MSG,"");
			pageData.put(OverallConfig.DATA,result);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
			pageData.put(OverallConfig.MSG,e.getMessage());
		}
	}

	//solr多字段“期刊”检索通用方法
	public void solrMoreFieldsSearchJournal(Page page, List<Map> searchFieldList, SolrSearchService solrSearchService,String sortFieldStr,Integer sortType, PageData pageData){
		List<Map> historySearchList = new ArrayList<>();
		try {
			//获取检索串
			String qStr = WebSolrDeals.ordinarySolrFreeWorldSearch(searchFieldList);
			//获取需设置高亮字段
			String[] str = WebSolrDeals.ordinarySolrFreeWorldSearchHightFields(searchFieldList);
			solrSearchService.searchSolr(page, qStr, sortFieldStr, sortType, true, str);
			//封装返回结果数据
			pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
			pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
			pageData.put(OverallConfig.DATA,page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
			pageData.put(OverallConfig.MSG,e.getMessage());
		}
	}

	//通过摘要id获取摘要数据
	public void solrAbstractByUniqueId(PageData pageData, SolrSearchService solrSearchService, String uniqueValue) {
		try {
			//唯一主键
			String uniqueId = SolrSearchFieldEnum.getTableFieldByKey(25);
			//查询内核
			String searCore = OverallConfig.ABSTRACT_CORE;
			//solr通过主键获取唯一数据
			if (StringUtils.isNotBlank(uniqueValue)){
				Map<String,Object> beanMap = solrSearchService.searchSolrBeanMapByUniqueId(uniqueId,uniqueValue,searCore);
				pageData.put(OverallConfig.DATA,beanMap);
			}else {
				throw new CustomException("检索摘要的abstractId参数不能为空!");
			}
			//封装返回结果数据
			pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
			pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
			pageData.put(OverallConfig.MSG,e.getMessage());
		}

	}

	//通过期刊id获取期刊数据
    public void solrJournalByJournalId(PageData pageData, SolrSearchService solrSearchService, JournalNavigationMenuVO journalNavigationMenuVO) {
		try {
			//唯一主键
			String uniqueId = SolrSearchFieldEnum.getTableFieldByKey(100);
			//唯一主键值
			String uniqueValue = journalNavigationMenuVO.getJournalId();
			//查询内核
			String searCore = OverallConfig.JOURNAL_CORE;
			//solr通过主键获取唯一数据
			if (StringUtils.isNotBlank(uniqueValue)){
				Map<String,Object> beanMap = solrSearchService.searchSolrBeanMapByUniqueId(uniqueId,uniqueValue,searCore);
				pageData.put(OverallConfig.DATA,beanMap);
			}else {
				throw new CustomException("检索摘要的journalId参数不能为空!");
			}
			//封装返回结果数据
			pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
			pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
			pageData.put(OverallConfig.MSG,e.getMessage());
		}

	}

	//年份检索时，对检索值的重新封装
	protected void searchYearForValue(OrdinarySearchVO ordinarySearchVO) {
		if (Integer.parseInt(ordinarySearchVO.getSearchWorld()) == 13){
			String year = ordinarySearchVO.getSearchValue();
			if (year != null && !"".equals(year) && Tools.isNumer(year)){
				String startYear = DateUtil.formatSolrDataStr(DateUtil.getFirstYear(Integer.valueOf(year)));
				String endYear = DateUtil.formatSolrDataStr(DateUtil.getLastYear(Integer.valueOf(year)));
				String time = "[\"" + startYear + "\" TO \"" + endYear + "\"]";
				ordinarySearchVO.setSearchValue(time);
			}
		}
	}

	/**
	 * 对中文检索的处理
	 * @param searchWorld
	 * @param searchValue
	 */
	public String ordinaryChSearchHandle(String searchWorld, String searchValue){
		Set<String> mhDesStr = new HashSet<>();
		//非主题词的中文检索时，通过翻译完成
		if (Integer.valueOf(searchWorld) !=10 && Tools.isChinese(searchValue)){
			searchValue = TransApi.getStrCh2EN(OverallConfig.APP_ID,OverallConfig.SECURITY_KEY,searchValue);
		}
		if (Integer.valueOf(searchWorld) == 13){
			if (Tools.isNumer(searchValue)){
				String startTime = DateUtil.formatSolrDataStr(DateUtil.getFirstYear(Integer.valueOf(searchValue) - 1));
				String endTime = DateUtil.formatSolrDataStr(DateUtil.getLastYear(Integer.valueOf(searchValue) - 1));
				searchValue = "[" + startTime + " TO " + endTime + "]";
			}
		}
		return searchValue;
	}
}
