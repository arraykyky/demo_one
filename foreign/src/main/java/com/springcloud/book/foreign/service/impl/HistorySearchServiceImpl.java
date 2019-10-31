package com.springcloud.book.foreign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.SearchesHistory;
import com.springcloud.book.foreign.dao.SearchesHistoryDao;
import com.springcloud.book.foreign.domain.vo.HistorySearchVO;
import com.springcloud.book.foreign.service.HistorySearchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-05
 */
@Service
public class HistorySearchServiceImpl extends ServiceImpl<SearchesHistoryDao, SearchesHistory> implements HistorySearchService {

    @Autowired
    private RedisServer redisServer;

    @Override
    public int getCurrentUserHistoryCount(SearchesHistory searchesHistory) {
        return baseMapper.selectCount(searchesHistory);
    }

    @Override
    public void addHistoryRecord(SearchesHistory searchesHistory) {
        baseMapper.insertOne(searchesHistory);
    }

    @Override
    public void getCurrentUserHistorySearchPage(Page page, HistorySearchVO historySearchVO) {
        String searchUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(historySearchVO.getToken()));
        historySearchVO.setSearchesUserid(searchUserId);
        List<SearchesHistory> list = baseMapper.selectCurrentUserSearchHistory(page, historySearchVO);
        page.setRecords(list);
    }

    @Override
    @Transactional
    public void deleteHistorySearchBySearchesId(HistorySearchVO historySearchVO) {
        baseMapper.updateHistorySearchToDeletedBySearchesId(historySearchVO);
    }

    @Override
    @Transactional
    public void cleanAllHistorySearch(HistorySearchVO historySearchVO) {
        String searchUserId = (String) redisServer.get(Constants.getUserModuleCacheKeyId(historySearchVO.getToken()));
        historySearchVO.setSearchesUserid(searchUserId);
        baseMapper.updateHistorySearchToDeletedBySearchesUserId(historySearchVO);
    }
}
