package com.springcloud.book.emailsystem.dao;

import com.springcloud.book.emailsystem.domain.UserPointRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
public interface UserPointRecordDao extends BaseMapper<UserPointRecord> {

    void insertPointRecord(UserPointRecord userPointRecord);
}
