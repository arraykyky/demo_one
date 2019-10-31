package com.springcloud.book.foreign.config.solr;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConnectionConfig {
    @Value("${spring.data.solr.host}")
    private String solrHost;
    @Value("${spring.data.solr.solruser}")
    private String solrUser;
    @Value("${spring.data.solr.password}")
    private String solrPassword;

    @Bean
    public SolrConnectionConfig solrDate(){
        return this;
    }

//    public String getSolrHost() {
//        return solrHost;
//    }

    public String getSolrUser() {
        return solrUser;
    }

    public String getSolrPassword() {
        return solrPassword;
    }

}
