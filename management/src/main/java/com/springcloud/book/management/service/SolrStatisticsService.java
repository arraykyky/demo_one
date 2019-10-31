package com.springcloud.book.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.HospitalNameAbbreviationInfo;
import com.springcloud.book.management.domain.SolrStatistics;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.management.domain.vo.SolrStatisticsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-14
 */
public interface SolrStatisticsService extends IService<SolrStatistics> {

    void getHospitalStatisticsStandardPage(Page page, String hospitalName);

    void addOrUpdateStatisticsStandard(SolrStatisticsVO solrStatisticsVO) throws Exception;

    void deleteStatisticsStandard(SolrStatisticsVO solrStatisticsVO);

    SolrStatistics getStatisticsStandardByType(String statisticsType);
}
