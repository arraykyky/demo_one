package com.springcloud.book.foreign;

import com.springcloud.book.foreign.config.Constants;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.redis.RedisServer;
import com.springcloud.book.foreign.config.solr.SolrClientSafe;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.service.HistorySearchService;
import com.springcloud.book.foreign.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.crazycake.shiro.RedisCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.l;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ForeignApplicationTests {

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private HistorySearchService historySearchService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SolrClientSafe clientSafe;



    @Test
    public void redisTest() {

    }

    public static void main(String[] args){
        String str = DateUtil.formatCurrentDateTime(1567958340000l);
        System.out.println(str);
    }

}

