package com.springcloud.book.foreign.dao;

import com.springcloud.book.foreign.domain.AbstractFullTextLinks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-16
 */
public interface AbstractFullTextLinksDao extends BaseMapper<AbstractFullTextLinks> {

    AbstractFullTextLinks selectOne(AbstractFullTextLinks abstractFullTextLinks);
}
