package com.springcloud.book.foreign.remote.fuse;

import com.springcloud.book.foreign.remote.ManagementEmailSendRemote;
import org.springframework.stereotype.Component;

@Component
public class ManagementEmailSendRemoteFuse implements ManagementEmailSendRemote {
    @Override
    public Object sendEmail(String token, String name) {
        return "your message send failed!";
    }
}
