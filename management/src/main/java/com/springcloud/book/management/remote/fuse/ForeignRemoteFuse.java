package com.springcloud.book.management.remote.fuse;

import com.springcloud.book.management.config.OverallConfig;
import com.springcloud.book.management.remote.ForeignRemote;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ForeignRemoteFuse implements ForeignRemote {
    @Override
    public Map<String, Object> getAbstractSolrDataByUniqueId(String abstractId) {
        Map<String,Object> map = new HashMap<>();
        map.put(OverallConfig.CODE,OverallConfig.ERROR);
        map.put(OverallConfig.MSG,"服务请求异常");
        return map;
    }

    @Override
    public Map<String, Object> getJournalSolrDataByNLMID(String journalWorld, String selectValue) {
        Map<String,Object> map = new HashMap<>();
        map.put(OverallConfig.CODE,OverallConfig.ERROR);
        map.put(OverallConfig.MSG,"服务请求异常");
        return map;
    }

    @Override
    public String testTimeOut(String name) {
        return "You send request service error!";
    }
}
