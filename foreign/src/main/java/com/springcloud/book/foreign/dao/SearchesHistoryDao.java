package com.springcloud.book.foreign.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.SearchesHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.HistorySearchVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-05
 */
public interface SearchesHistoryDao extends BaseMapper<SearchesHistory> {

    int selectCount(SearchesHistory searchesHistory);

    void insertOne(SearchesHistory searchesHistory);

    List<SearchesHistory> selectCurrentUserSearchHistory(Page page,@Param("vo") HistorySearchVO historySearchVO);

    void updateHistorySearchToDeletedBySearchesId(HistorySearchVO historySearchVO);

    void updateHistorySearchToDeletedBySearchesUserId(HistorySearchVO historySearchVO);
}
