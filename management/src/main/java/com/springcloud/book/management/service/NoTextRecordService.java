package com.springcloud.book.management.service;

import com.springcloud.book.management.domain.NoTextRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-26
 */
public interface NoTextRecordService extends IService<NoTextRecord> {
    //获取无全文记录
    NoTextRecord getNotextRecordByAbstractId(String abstractId);
    //添加无全文记录
    void addNoTextRecord(NoTextRecord daoNoTextRecord);
    //更新
    void updateNoTextRecordById(NoTextRecord noTextRecord);
}
