package com.springcloud.book.foreign.controller;


import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.CollectionJournal;
import com.springcloud.book.foreign.domain.vo.JournalStoreVO;
import com.springcloud.book.foreign.service.CollectionJournalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author grl
 * @since 2019-08-19
 */
@RestController
@RequestMapping("/collection")
public class CollectionJournalController {

    private Logger logger = LoggerFactory.getLogger(CollectionJournalController.class);

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private CollectionJournalService collectionJournalService;

    /**
     * 期刊收藏 ---- 验证当前期刊是否已被当前用户收藏
     * @param journalStoreVO 期刊库VO 包含必要参数 collectionJournalId --  期刊id
     * @return
     */
    @GetMapping("/check_journal_collected")
    public Object checkJournalIsCollected(JournalStoreVO journalStoreVO){
        PageData pageData = new PageData();
        try {
            String userId = (String) redisServer.get(Constants.getUserModuleCacheKeyForeignUserId(journalStoreVO.getToken()));
            journalStoreVO.setCollectionUserId(userId);
            String collectedId = collectionJournalService.checkJournalIsCollected(journalStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,collectedId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 期刊收藏 ---- 收藏
     * @param journalStoreVO    期刊库VO 包含必要参数 token
     * @param collectionJournal 期刊库   包含必要参数 collectionJournalId --  期刊id
     *                                                collectionJournalName  -- 期刊名称
     * @return
     */
    @PostMapping("/add_journal_collected")
    public Object journalCollection(JournalStoreVO journalStoreVO, CollectionJournal collectionJournal){
        PageData pageData = new PageData();
        try{
            String userId = (String) redisServer.get(Constants.getUserModuleCacheKeyForeignUserId(journalStoreVO.getToken()));
            collectionJournal.setCollectionUserId(userId);
            collectionJournalService.addJournalCollection(collectionJournal);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }



}

