package com.springcloud.book.management.controller;

import com.springcloud.book.management.domain.vo.EmailVO;
import com.springcloud.book.management.remote.EmailSendRemote;
import com.springcloud.book.management.remote.ForeignRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EmailSendRemote emailSendRemote;

    @Autowired
    private ForeignRemote foreignRemote;

    @GetMapping("/time_out")
    public Object testTimeOut(String name){
        return foreignRemote.testTimeOut(name);
    }

    @GetMapping("/str")
    public Object testStr(String token){
        return emailSendRemote.helloWorld(token);
    }

    @GetMapping("/obj")
    public Object testObj(String token){
        EmailVO emailVO = new EmailVO();
        emailVO.setSystemEmail("867022565@qq.com");
        emailVO.setPort("80");
        emailVO.setPassword("123456");
        emailVO.setRecipient("grl100388@163.com");
        emailVO.setEmailTitle("测试");
        emailVO.setEmailContent("测试......");
        System.out.println(emailVO);
        return emailSendRemote.helloWorld(token,emailVO);
    }


}
