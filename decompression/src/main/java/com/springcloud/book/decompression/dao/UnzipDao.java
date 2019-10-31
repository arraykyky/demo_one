package com.springcloud.book.decompression.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.decompression.domain.Unzip;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-26
 */
public interface UnzipDao extends BaseMapper<Unzip> {

    Unzip selectByType(Unzip unzip);
}
