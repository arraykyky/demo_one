package com.springcloud.book.foreign.dao;

import com.springcloud.book.foreign.domain.FullTextUploadFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-19
 */
public interface FullTextUploadFlowDao extends BaseMapper<FullTextUploadFlow> {

    FullTextUploadFlow selectByAbstractId(FullTextUploadFlow fullTextUploadFlow);
}
