package com.springcloud.book.foreign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.CollectionJournal;
import com.springcloud.book.foreign.dao.CollectionJournalDao;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.domain.vo.JournalStoreVO;
import com.springcloud.book.foreign.service.CollectionJournalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.foreign.service.UserService;
import com.springcloud.book.foreign.util.Tools;
import org.apache.commons.lang.StringUtils;
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
 * @since 2019-08-19
 */
@Service
public class CollectionJournalServiceImpl extends ServiceImpl<CollectionJournalDao, CollectionJournal> implements CollectionJournalService {

    @Autowired
    private UserService userService;

    @Override
    public void getCurrentUserJournalStorePage(Page page, JournalStoreVO journalStoreVO) {
        List<CollectionJournal> list = baseMapper.selectCurrentUserJournalStorePage(page, journalStoreVO);
        page.setRecords(list);
    }

    @Override
    @Transactional
    public void deleteCurrentUserCollectionBySelectedIds(JournalStoreVO journalStoreVO) {
        baseMapper.deleteBatchIds(journalStoreVO.getCollectionIdList());
    }

    @Override
    public String checkJournalIsCollected(JournalStoreVO journalStoreVO) {
        CollectionJournal collectionJournal = baseMapper.selectJournalIsCollected(journalStoreVO);
        return collectionJournal != null ? collectionJournal.getCollectionId() : null;
    }

    @Override
    public void addJournalCollection(CollectionJournal collectionJournal) {
        User user = userService.getById(collectionJournal.getCollectionUserId());
        String userName = user.getUserName();
        String userRealName = user.getUserRealName();
        collectionJournal.setCollectionId(Tools.getUUID());
        if (StringUtils.isNotBlank(userRealName)){
            collectionJournal.setCollectionUserName(userRealName);
        }else {
            collectionJournal.setCollectionUserName(userName);
        }
        collectionJournal.setCollectionTime(String.valueOf(System.currentTimeMillis()));
        baseMapper.insertJournalCollection(collectionJournal);
    }
}
