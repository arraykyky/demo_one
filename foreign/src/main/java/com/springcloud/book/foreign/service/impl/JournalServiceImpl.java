package com.springcloud.book.foreign.service.impl;

import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.solr.SolrClientSafe;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.Journal;
import com.springcloud.book.foreign.dao.JournalDao;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;
import com.springcloud.book.foreign.service.JournalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.foreign.service.SolrSearchService;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-09
 */
@Service
public class JournalServiceImpl extends ServiceImpl<JournalDao, Journal> implements JournalService {

    @Autowired
    private SolrSearchService solrSearchService;

    @Override
    public List<Map> getJournalYearCounts(JournalNavigationMenuVO journalNavigationMenuVO) throws Exception {
        //每年统计结果集合
        List<Map> yearList = new ArrayList<>();
        //获取改期刊下不同的版本:年-卷-期
        String sqStr = WebSolrDeals.ordinarySolrFreeWorldSearch("100", journalNavigationMenuVO.getJournalId(), false);
        QueryResponse qrst = solrSearchService.simpleSortQueryResponse(sqStr, "medline_date", -1);
        //获取分面结果
        FacetField ff = qrst.getFacetField("medline_date");
        List<FacetField.Count> values = ff.getValues();
        for (FacetField.Count fc : values){
            long time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(fc.getName()).getTime();
            String javaYear = new SimpleDateFormat("yyyy").format(time + 28800000);
            PageData pageDataFac = new PageData();
            pageDataFac.put("countYear",javaYear);
            pageDataFac.put("countNum",fc.getCount());
            yearList.add(pageDataFac);
        }
        Collections.reverse(yearList);
        return yearList;
    }
}
