package com.springcloud.book.management.service.impl;

import com.springcloud.book.management.domain.UserPointRecord;
import com.springcloud.book.management.dao.UserPointRecordDao;
import com.springcloud.book.management.service.UserPointRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
@Service
public class UserPointRecordServiceImpl extends ServiceImpl<UserPointRecordDao, UserPointRecord> implements UserPointRecordService {

    @Override
    public void addUserPointRecord(UserPointRecord userPointRecord) {
        baseMapper.insertPointRecord(userPointRecord);
    }
}
