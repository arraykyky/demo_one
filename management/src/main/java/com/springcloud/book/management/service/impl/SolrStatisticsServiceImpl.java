package com.springcloud.book.management.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.HospitalNameAbbreviationInfo;
import com.springcloud.book.management.domain.SolrStatistics;
import com.springcloud.book.management.dao.SolrStatisticsDao;
import com.springcloud.book.management.domain.vo.SolrStatisticsVO;
import com.springcloud.book.management.service.SolrStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-14
 */
@Service
public class SolrStatisticsServiceImpl extends ServiceImpl<SolrStatisticsDao, SolrStatistics> implements SolrStatisticsService {

    @Override
    public void getHospitalStatisticsStandardPage(Page page, String hospitalName) {
        PageData pageData = new PageData();
        pageData.put("hospitalName",hospitalName);
        List<PageData> list = baseMapper.selectHospitalStatisticsStandardPage(page,pageData);
        page.setRecords(list);
    }

    @Override
    public void addOrUpdateStatisticsStandard(SolrStatisticsVO solrStatisticsVO) throws Exception{
        SolrStatistics solrStatistics = new SolrStatistics();
        solrStatistics.setStatisticsType(solrStatisticsVO.getStatisticsType());
        JSONObject json = new JSONObject();
        List<String> andValueList = new ArrayList<>();
        List<String> orValueList = new ArrayList<>();
        String andValue = solrStatisticsVO.getAndValue();
        String orValue = solrStatisticsVO.getOrValue();
        if (StringUtils.isBlank(andValue) && StringUtils.isBlank(orValue)){
            throw new RuntimeException("俩种归集标准方式不能同时为空");
        }
        if (StringUtils.isNotBlank(andValue)){
            String[] andValueArray = andValue.split(";");
            andValueList.addAll(Arrays.asList(andValueArray));
            json.put("AND",andValueList);
        }
        if (StringUtils.isNotBlank(orValue)){
            String[] orValueArray = orValue.split(";");
            orValueList.addAll(Arrays.asList(orValueArray));
            json.put("OR",orValueList);
        }
        if (!json.isEmpty()){
            solrStatistics.setStatisticsValue(json.toString());
        }
        //新增
        if (solrStatisticsVO.getStatisticsId() == null){
            //验证该单位是否已添加
            SolrStatistics daoSolrStatistics = this.getStatisticsStandardByType(solrStatisticsVO.getStatisticsType());
            if (daoSolrStatistics == null){
                baseMapper.insert(solrStatistics);
            }else {
             throw new RuntimeException("该单位归集标准已添加,如果变更,请修改您已添加的标准");
            }
        }
        //更新
        else {
            solrStatistics.setStatisticsId(solrStatisticsVO.getStatisticsId());
            baseMapper.updateById(solrStatistics);
        }
    }

    @Override
    public void deleteStatisticsStandard(SolrStatisticsVO solrStatisticsVO) {
        baseMapper.deleteById(solrStatisticsVO.getStatisticsId());
    }

    @Override
    public SolrStatistics getStatisticsStandardByType(String statisticsType) {
        SolrStatistics solrStatistics = new SolrStatistics();
        solrStatistics.setStatisticsType(statisticsType);
        return baseMapper.selectStatisticsStandardByType(solrStatistics);
    }
}
