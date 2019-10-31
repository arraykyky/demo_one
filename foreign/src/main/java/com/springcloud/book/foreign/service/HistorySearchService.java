package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.SearchesHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.HistorySearchVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-05
 */
public interface HistorySearchService extends IService<SearchesHistory> {

    //通过检索人的id获取该用户的检索记录的条数
    int getCurrentUserHistoryCount(SearchesHistory searchesHistory);
    //添加检索记录
    void addHistoryRecord(SearchesHistory searchesHistory);
    //获取当前用户的检索记录
    void getCurrentUserHistorySearchPage(Page page, HistorySearchVO historySearchVO);
    //通过searchesId删除历史检索记录(逻辑删除)
    void deleteHistorySearchBySearchesId(HistorySearchVO historySearchVO);
    //清空当前用户的检索记录
    void cleanAllHistorySearch(HistorySearchVO historySearchVO);
}
