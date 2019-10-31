package com.springcloud.book.management.controller;

import com.springcloud.book.management.config.OverallConfig;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.service.ArticleOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remote")
public class RemoteController {

    Logger logger = LoggerFactory.getLogger(RemoteController.class);

    @Autowired
    private ArticleOrdersService articleOrdersService;

    @PostMapping("/email_send")
    public Object getFullText(String token,String orderId){
        PageData pageData = new PageData();
        try {
            articleOrdersService.remoteEmailSend(token,orderId);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,true);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return orderId;
    }
}
