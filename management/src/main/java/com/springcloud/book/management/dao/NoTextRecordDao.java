package com.springcloud.book.management.dao;

import com.springcloud.book.management.domain.NoTextRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-26
 */
public interface NoTextRecordDao extends BaseMapper<NoTextRecord> {

    NoTextRecord selectNotextRecordByAbstractId(NoTextRecord noTextRecord);

    void insertNoTextRecord(NoTextRecord daoNoTextRecord);
}
