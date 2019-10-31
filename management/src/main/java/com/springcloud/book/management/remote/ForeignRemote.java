package com.springcloud.book.management.remote;

import com.springcloud.book.management.remote.fuse.ForeignRemoteFuse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "foreign-center",fallback = ForeignRemoteFuse.class)
public interface ForeignRemote {
    @GetMapping("/remote/abstract_info")
    Map<String, Object> getAbstractSolrDataByUniqueId(@RequestParam("abstractId") String abstractId);

    @GetMapping("/remote/journal_info")
    Map<String, Object> getJournalSolrDataByNLMID(@RequestParam("journalWorld") String journalWorld, @RequestParam("selectValue") String selectValue);

    @GetMapping("/test/one")
    String testTimeOut(@RequestParam("name") String name);
}
