package com.springcloud.book.management.service.impl;

import com.springcloud.book.management.domain.NoTextRecord;
import com.springcloud.book.management.dao.NoTextRecordDao;
import com.springcloud.book.management.service.NoTextRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-26
 */
@Service
public class NoTextRecordServiceImpl extends ServiceImpl<NoTextRecordDao, NoTextRecord> implements NoTextRecordService {

    @Override
    public NoTextRecord getNotextRecordByAbstractId(String abstractId) {
        NoTextRecord noTextRecord = new NoTextRecord();
        noTextRecord.setAbstractId(abstractId);
        return baseMapper.selectNotextRecordByAbstractId(noTextRecord);
    }

    @Override
    public void addNoTextRecord(NoTextRecord daoNoTextRecord) {
        baseMapper.insertNoTextRecord(daoNoTextRecord);
    }

    @Override
    public void updateNoTextRecordById(NoTextRecord noTextRecord) {
        baseMapper.updateById(noTextRecord);
    }
}
