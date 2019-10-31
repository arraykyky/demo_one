package com.springcloud.book.foreign.remote;

import com.springcloud.book.foreign.remote.fuse.ManagementEmailSendRemoteFuse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "management-center",fallback = ManagementEmailSendRemoteFuse.class)
public interface ManagementEmailSendRemote {
    @PostMapping("/remote/email_send")
    Object sendEmail(@RequestParam("token") String token, @RequestParam("orderId") String orderId);
}
