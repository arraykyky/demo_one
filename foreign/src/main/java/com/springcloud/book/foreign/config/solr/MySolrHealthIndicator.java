package com.springcloud.book.foreign.config.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.common.params.CoreAdminParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.solr.SolrHealthIndicator;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Solr健康性验证
 */
@Configuration
public class MySolrHealthIndicator extends SolrHealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(MySolrHealthIndicator.class);
    private SolrClient solrClient;

    @Autowired
    private SolrConnectionConfig solrConnectionConfig;

    public MySolrHealthIndicator(SolrClient solrClient) {
        super(solrClient);
        this.solrClient = solrClient;
        logger.info("SolrClient加载成功......");
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        CoreAdminRequest request = new CoreAdminRequest();
        request.setBasicAuthCredentials(solrConnectionConfig.getSolrUser(),solrConnectionConfig.getSolrPassword());
        request.setAction(CoreAdminParams.CoreAdminAction.STATUS);
        CoreAdminResponse response = request.process(this.solrClient);
        int statusCode = response.getStatus();
        Status status = (statusCode == 0 ? Status.UP : Status.DOWN);
        builder.status(status).withDetail("solrStatus",
                (statusCode == 0 ? "OK" : statusCode));
        logger.info("Solr Health check success");
    }
}
