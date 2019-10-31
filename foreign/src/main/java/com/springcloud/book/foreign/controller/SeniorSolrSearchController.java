package com.springcloud.book.foreign.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.vo.LimiteProVO;
import com.springcloud.book.foreign.domain.vo.SearchVoBean;
import com.springcloud.book.foreign.domain.vo.SeniorVo;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.util.DateUtil;
import com.springcloud.book.foreign.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/senior")
public class SeniorSolrSearchController extends BaseController {

    Logger logger = LoggerFactory.getLogger(SeniorSolrSearchController.class);

    @Autowired
    private SolrSearchService solrSearchService;

    @PostMapping("/list")
    public Object entityWrapperTest(Page page,String token, @RequestBody SeniorVo seniorVo){
        logger.info(seniorVo.toString());
        PageData pageData = new PageData();
        //获取基础检索串
        List<SearchVoBean> listSvb = new ArrayList<>();
        List<Map> listMap = new ArrayList<>();
        List<SearchVoBean> sbList = seniorVo.getSbList();
        if (sbList != null){
            for (SearchVoBean svb : sbList){
                if (svb.getSearchValue() != null && !"".equals(svb.getSearchValue())){
                    String rep = svb.getSearchValue().replaceAll(":", "");
                    rep = this.ordinaryChSearchHandle(svb.getSearchWorld(),rep);
                    svb.setSearchValue(rep);
                    listSvb.add(svb);
                }
            }
        }
        if (!listSvb.isEmpty()){
            listMap.addAll(JSON.parseObject(listSvb.toString(),new ArrayList<Map>().getClass()));
        }
        //封装日期检索串
        String start = seniorVo.getStartDate();
        String end = seniorVo.getEndDate();
        if (StringUtils.isNotBlank(start) && Tools.isNumer(start)){
            if (StringUtils.isBlank(end) || !Tools.isNumer(end)){
                Date endDate = DateUtil.getLastYear(DateUtil.getCurrentYear());
                end = DateUtil.formatCurrentDateTime(endDate.getTime());
            }
            Date startDate = DateUtil.getFirstYear(Integer.valueOf(start));
            Date endDate = DateUtil.getLastYear(Integer.valueOf(end));
            String solrStartDate = DateUtil.formatSolrDataStr(startDate);
            String solrEndDate = DateUtil.formatSolrDataStr(endDate);
            String timeValue = "[" +solrStartDate + " TO " +solrEndDate+ "]";
            Map<String, Object> timeMap = WebSolrDeals.searchFieldMap("", "13", timeValue);
            listMap.add(timeMap);
        }
        //封装筛选检索串
        List<String> selectList = seniorVo.getSelectList();
        if (selectList != null && !selectList.isEmpty()){
            for (String str : selectList){
                Map<String, Object> map = WebSolrDeals.seniorSelectSearchFieldMap(str);
                if (map != null){
                    listMap.add(map);
                }
            }
        }
        //限定对象
        List<LimiteProVO> limitePro = seniorVo.getLimitePro();
        if (limitePro != null){
            for (LimiteProVO limiteProVO : limitePro){
                List<String> searchValueList = limiteProVO.getSearchValueList();
                String searchValue = WebSolrDeals.createSubStr(searchValueList);
                limiteProVO.setSearchValue(searchValue);
                limiteProVO.setConn("0");
                listMap.add(JSON.parseObject(limiteProVO.toString(),HashMap.class));
            }
        }
        this.solrMoreFieldsSearch(true,page,token,listMap,solrSearchService,pageData,null);
        return pageData;
    }


}
