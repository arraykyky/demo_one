package com.springcloud.book.decompression.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.decompression.domain.Unzip;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-26
 */
public interface UnzipService extends IService<Unzip> {
    Unzip selectByType(Unzip unzip);
    void addUnzipRecords(Unzip unzip);
}
