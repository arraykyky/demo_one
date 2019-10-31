package com.springcloud.book.management.remote;

import com.springcloud.book.management.domain.vo.EmailVO;
import com.springcloud.book.management.remote.fuse.EmailSendRemoteFuse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "email-center",fallback = EmailSendRemoteFuse.class)
public interface EmailSendRemote {
    @PostMapping("/send/test_str")
    String helloWorld(@RequestParam("token") String token);

    @PostMapping("/send/test_obj")
    String helloWorld(@RequestParam("token") String token, @RequestBody EmailVO emailVO);

    @PostMapping("/send/mail_attach")
    Map<String,String> sendEmailAttach(@RequestParam("token") String token, @RequestBody EmailVO emailVO);

    @PostMapping("/send/mail_simple_text")
    Map<String,String> sendEmailText(@RequestParam("token") String token, @RequestBody EmailVO emailVO);
}
