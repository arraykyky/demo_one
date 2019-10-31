package com.springcloud.book.emailsystem.service;

import com.springcloud.book.emailsystem.domain.UserPointRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
public interface UserPointRecordService extends IService<UserPointRecord> {

    void addUserPointRecord(UserPointRecord userPointRecord);
}
