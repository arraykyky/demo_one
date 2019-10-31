package com.springcloud.book.foreign.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.CollectionJournal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.JournalStoreVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-19
 */
public interface CollectionJournalDao extends BaseMapper<CollectionJournal> {

    List<CollectionJournal> selectCurrentUserJournalStorePage(Page page, @Param("pd") JournalStoreVO journalStoreVO);

    CollectionJournal selectJournalIsCollected(JournalStoreVO journalStoreVO);

    void insertJournalCollection(CollectionJournal collectionJournal);
}
