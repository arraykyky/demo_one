package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.CollectionJournal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.JournalStoreVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-19
 */
public interface CollectionJournalService extends IService<CollectionJournal> {
    //获取当前用户的期刊库列表
    void getCurrentUserJournalStorePage(Page page, JournalStoreVO journalStoreVO);
    //通过选择的收藏id删除收藏期刊数据
    void deleteCurrentUserCollectionBySelectedIds(JournalStoreVO journalStoreVO);
    //判断当前用户是否收藏该期刊
    String checkJournalIsCollected(JournalStoreVO journalStoreVO);
    //添加收藏期刊信息
    void addJournalCollection(CollectionJournal journalStoreVO);
}
