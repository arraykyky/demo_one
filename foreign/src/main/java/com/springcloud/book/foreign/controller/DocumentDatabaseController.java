package com.springcloud.book.foreign.controller;


import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.domain.vo.DocumentStoreVO;
import com.springcloud.book.foreign.service.DocumentDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/document")
public class DocumentDatabaseController {

    private Logger logger = LoggerFactory.getLogger(DocumentDatabaseController.class);

    @Autowired
    private DocumentDatabaseService documentDatabaseService;


    /**
     * 文献收藏
     * @param documentStoreVO 文献库VO 包含必要参数: documentDatabaseId -- 我的文献库主键id
     *                                                  documentId -- 收藏文献的摘要id
     *                                                  documentTitle -- 收藏文献的摘要标题
     * @return
     */
    @PostMapping("/add_abstract_collected")
    public Object abstractCollection(DocumentStoreVO documentStoreVO){
        PageData pageData = new PageData();
        try {
            documentDatabaseService.addDocumentCollection(documentStoreVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,"收藏成功");
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

