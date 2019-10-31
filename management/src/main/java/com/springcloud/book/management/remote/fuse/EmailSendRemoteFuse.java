package com.springcloud.book.management.remote.fuse;

import com.springcloud.book.management.config.OverallConfig;
import com.springcloud.book.management.domain.vo.EmailVO;
import com.springcloud.book.management.remote.EmailSendRemote;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailSendRemoteFuse implements EmailSendRemote {
    @Override
    public String helloWorld(String name) {
        return "your message send str failed!";
    }

    @Override
    public String helloWorld(String token, EmailVO emailVO) {
        return "your message send obj failed!";
    }

    @Override
    public Map<String, String> sendEmailAttach(String token, EmailVO emailVO) {
        Map<String,String> map = new HashMap<>();
        map.put(OverallConfig.CODE,OverallConfig.ERROR);
        map.put(OverallConfig.MSG,"服务请求异常");
        return map;
    }

    @Override
    public Map<String,String> sendEmailText(String token, EmailVO emailVO) {
        Map<String,String> map = new HashMap<>();
        map.put(OverallConfig.CODE,OverallConfig.ERROR);
        map.put(OverallConfig.MSG,"服务请求异常");
        return map;
    }
}
