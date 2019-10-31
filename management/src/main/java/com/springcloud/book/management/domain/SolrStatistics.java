package com.springcloud.book.management.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author grl
 * @since 2019-10-14
 */
@TableName("fd_solr_statistics")
public class SolrStatistics extends Model<SolrStatistics> {

    private static final long serialVersionUID = 1L;

    /**
     * 统计id
     */
    @TableId(value = "statistics_id", type = IdType.AUTO)
    private Integer statisticsId;

    /**
     * 统计类型(filter过滤词；XXXX统一的统计医院名称)【同类型数据为OR查询】
     */
    @TableField("statistics_type")
    private String statisticsType;

    /**
     * 统计的值
     */
    @TableField("statistics_value")
    private String statisticsValue;


    public Integer getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(Integer statisticsId) {
        this.statisticsId = statisticsId;
    }

    public String getStatisticsType() {
        return statisticsType;
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType;
    }

    public String getStatisticsValue() {
        return statisticsValue;
    }

    public void setStatisticsValue(String statisticsValue) {
        this.statisticsValue = statisticsValue;
    }

    @Override
    protected Serializable pkVal() {
        return this.statisticsId;
    }

    @Override
    public String toString() {
        return "SolrStatistics{" +
        "statisticsId=" + statisticsId +
        ", statisticsType=" + statisticsType +
        ", statisticsValue=" + statisticsValue +
        "}";
    }
}
