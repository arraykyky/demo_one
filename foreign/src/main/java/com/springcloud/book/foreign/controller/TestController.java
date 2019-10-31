package com.springcloud.book.foreign.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.util.DateUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.FieldStatsInfo;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SolrSearchService solrSearchService;

    @GetMapping("/pivot")
    public Object testTimeOut(String name) throws Exception {
        String journalId = "00110640R1471-69410029-3970";
        SolrQuery sq = new SolrQuery("journal_id:"+ "\"" + journalId + "\"");
        sq.setFacet(true);
        sq.setStart(0);
        sq.setRows(0);
        sq.add("facet.pivot","medline_date,volume,issue");
        QueryResponse queryResponse = solrSearchService.searchPivot(sq);
        System.out.println(sq.toString());
        List<Map<String,String>> resultList = new ArrayList<>();
        NamedList<List<PivotField>> namedList = queryResponse.getFacetPivot();
        if (namedList != null && namedList.size() > 0){
            List<PivotField> nextPivotFieldList = namedList.getVal(0);
            getYearVI(nextPivotFieldList,resultList);
        }
        resultList.forEach(v ->{
            System.out.println(v);
        });
        return namedList.toString();
    }

    private void getYearVI(List<PivotField> list,List<Map<String,String>> listMap){
        for (PivotField pf : list){
            String f0 = pf.getField();
            if (f0.equals("medline_date")){
                Date yearDate = (Date) pf.getValue();
                String yearStr = DateUtil.format(yearDate, "yyyy");
                Integer yearInt = Integer.parseInt(yearStr);
                String year = pf.getValue().toString();
                List<PivotField> volum = pf.getPivot();
                if (volum != null){
                    for (PivotField vpf : volum){
                        String volume = vpf.getValue().toString();
                        List<PivotField> issue = vpf.getPivot();
                        if (issue != null){
                            for (PivotField ipf : issue){
                                Map<String,String> imap = new HashMap<>();
                                imap.put("year",year);
                                imap.put("volume",volume);
                                imap.put("issue",ipf.getValue().toString());
                                listMap.add(imap);
                            }
                        }else {
                            Map<String,String> vmap = new HashMap<>();
                            vmap.put("year",year);
                            vmap.put("volume",volume);
                            vmap.put("issue",null);
                            listMap.add(vmap);
                        }
                    }
                }else {
                    Map<String,String> map = new HashMap<>();
                    map.put("year",year);
                    map.put("volume",null);
                    map.put("issue",null);
                    listMap.add(map);
                }
            }
        }
    }


}
