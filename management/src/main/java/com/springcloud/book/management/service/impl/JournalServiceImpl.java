package com.springcloud.book.management.service.impl;

import com.springcloud.book.management.config.Constants;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.config.redis.RedisServer;
import com.springcloud.book.management.domain.Journal;
import com.springcloud.book.management.dao.JournalDao;
import com.springcloud.book.management.service.JournalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-21
 */
@Service
public class JournalServiceImpl extends ServiceImpl<JournalDao, Journal> implements JournalService {

    @Autowired
    private RedisServer redisServer;

    @Override
    public List<PageData> getJournalMenuList() {
        List<PageData> list = (List<PageData>) redisServer.get(Constants.getJournalMenuCachekey());
        if (list == null || list.isEmpty()){
            list = baseMapper.getJournalMenuList();
            redisServer.set(Constants.getJournalMenuCachekey(),list,12 * 60 * 60);
        }
        return list;
    }
}
