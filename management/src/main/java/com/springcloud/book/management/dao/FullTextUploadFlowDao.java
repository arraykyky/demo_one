package com.springcloud.book.management.dao;

import com.springcloud.book.management.domain.FullTextUploadFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-28
 */
public interface FullTextUploadFlowDao extends BaseMapper<FullTextUploadFlow> {

    void insertFullText(FullTextUploadFlow fullTextUploadFlow);

    FullTextUploadFlow selectFullTextUploadFlowByAbstractId(FullTextUploadFlow fullTextUploadFlow);
}
