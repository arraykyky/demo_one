package com.springcloud.book.emailsystem.service.impl;

import com.springcloud.book.emailsystem.domain.UserPointRecord;
import com.springcloud.book.emailsystem.dao.UserPointRecordDao;
import com.springcloud.book.emailsystem.service.UserPointRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
@Service
public class UserPointRecordServiceImpl extends ServiceImpl<UserPointRecordDao, UserPointRecord> implements UserPointRecordService {

    @Override
    public void addUserPointRecord(UserPointRecord userPointRecord) {
        baseMapper.insertPointRecord(userPointRecord);
    }
}
