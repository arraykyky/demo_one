package com.springcloud.book.management.dao;

import com.springcloud.book.management.domain.UserPointRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
public interface UserPointRecordDao extends BaseMapper<UserPointRecord> {

    void insertPointRecord(UserPointRecord userPointRecord);
}
