package com.springcloud.book.decompression.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.decompression.config.FinalFileConfig;
import com.springcloud.book.decompression.dao.UnzipDao;
import com.springcloud.book.decompression.domain.Unzip;
import com.springcloud.book.decompression.service.UnzipService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-26
 */
@Service
public class UnzipServiceImpl extends ServiceImpl<UnzipDao, Unzip> implements UnzipService {

    @Override
    public Unzip selectByType(Unzip unzip) {
        return baseMapper.selectByType(unzip);
    }

    @Override
    public void addUnzipRecords(Unzip unzip) {
        this.saveOrUpdate(unzip);
    }
}
