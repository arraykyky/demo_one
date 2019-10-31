package com.springcloud.book.foreign.dao;

import com.springcloud.book.foreign.domain.ArticleOrdersFlowDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
