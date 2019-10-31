package com.springcloud.book.management.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.HospitalNameAbbreviationInfo;
import com.springcloud.book.management.domain.SolrStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-10-14
 */
public interface SolrStatisticsDao extends BaseMapper<SolrStatistics> {

    List<PageData> selectHospitalStatisticsStandardPage(Page page,@Param("mpd") PageData pageData);

    SolrStatistics selectStatisticsStandardByType(SolrStatistics solrStatistics);
}
