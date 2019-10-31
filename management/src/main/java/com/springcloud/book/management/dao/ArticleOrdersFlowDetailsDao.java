package com.springcloud.book.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.management.domain.ArticleOrdersFlowDetails;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-19
 */
public interface ArticleOrdersFlowDetailsDao extends BaseMapper<ArticleOrdersFlowDetails> {

    void insertData(ArticleOrdersFlowDetails flowDetails);
}
