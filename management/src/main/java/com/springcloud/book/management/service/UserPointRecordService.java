package com.springcloud.book.management.service;

import com.springcloud.book.management.domain.UserPointRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
public interface UserPointRecordService extends IService<UserPointRecord> {
    //添加对账记录
    void addUserPointRecord(UserPointRecord userPointRecord);
}
