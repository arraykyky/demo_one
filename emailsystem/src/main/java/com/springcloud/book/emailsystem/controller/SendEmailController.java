package com.springcloud.book.emailsystem.controller;

import com.springcloud.book.emailsystem.bean.EmailVO;
import com.springcloud.book.emailsystem.config.OverallConfig;
import com.springcloud.book.emailsystem.service.ArticleOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/send")
public class SendEmailController {

    private Logger logger = LoggerFactory.getLogger(SendEmailController.class);

    @Autowired
    private ArticleOrdersService articleOrdersService;

    @PostMapping("/test_str")
    public Object test(String token){
        return token;
    }

    @PostMapping("/test_obj")
    public Object test(String token,@RequestBody EmailVO emailVO){
        System.out.println(emailVO);
        return token + ":" + emailVO;
    }

    /**
     * 邮件发送 —— 附件
     * @param token
     * @param emailVO
     * @return
     */
    @PostMapping("/mail_attach")
    public Object sendEmailAttach(String token, @RequestBody EmailVO emailVO){
        Map<String,String> map = new HashMap<>();
        try {
            if (emailVO.checkEmailVONecessaryFieldIsNotEmpty(emailVO,"addressesArray")){
                articleOrdersService.sendEmailAttach(emailVO);
                map.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                map.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            }else {
                throw new RuntimeException("参数不完整");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            map.put(OverallConfig.CODE,OverallConfig.ERROR);
            map.put(OverallConfig.MSG,e.getMessage());
        }
        return map;
    }

    /**
     * 邮件发送 —— 多人接收
     * @param token
     * @param emailVO
     * @return
     */
    @PostMapping("/mail_more_recipient")
    public Object sendEmailMoreRecipient(String token, @RequestBody EmailVO emailVO){
        Map<String,String> map = new HashMap<>();
        try {
            String[] array = {"bytes", "recipient","attachName"};
            if (emailVO.checkEmailVONecessaryFieldIsNotEmpty(emailVO,array)) {
                articleOrdersService.sendEmailMoreRecipient(emailVO);
                map.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                map.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            }else {
                throw new RuntimeException("参数不完整");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            map.put(OverallConfig.CODE,OverallConfig.ERROR);
            map.put(OverallConfig.MSG,e.getMessage());
        }
        return map;
    }

    /**
     * 邮件发送 —— 文本
     * @param token
     * @param emailVO
     * @return
     */
    @PostMapping("/mail_simple_text")
    public Object sendEmailText(String token,@RequestBody EmailVO emailVO){
        Map<String,String> map = new HashMap<>();
        try {
            String[] array = {"bytes", "addressesArray","attachName"};
            if (emailVO.checkEmailVONecessaryFieldIsNotEmpty(emailVO,array)) {
                articleOrdersService.sendEmailText(emailVO);
                map.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                map.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            }else {
                throw new RuntimeException("参数不完整");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            map.put(OverallConfig.CODE,OverallConfig.ERROR);
            map.put(OverallConfig.MSG,e.getMessage());
        }
        return map;
    }

}
