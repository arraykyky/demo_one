package com.springcloud.book.foreign.controller;

import com.springcloud.book.foreign.remote.ManagementEmailSendRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailSendTest {

    @Autowired
    private ManagementEmailSendRemote emailSendRemote;

    @GetMapping("/send")
    public Object sendEmail(String token){
        return emailSendRemote.sendEmail(token, token);
    }
}
