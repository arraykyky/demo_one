package com.springcloud.book.foreign.config.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Component
public class SolrClientSafe {

    @Autowired
    private SolrClient client;
    @Autowired
    private SolrConnectionConfig solrConnectionConfig;

    /**
     * 批量添加数据-安全验证
     * @param beans 数据bean集合
     * @param collection 连接内核
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public UpdateResponse addBeans(Collection<?> beans, String collection) throws SolrServerException, IOException {
        DocumentObjectBinder binder = this.client.getBinder();
        ArrayList<SolrInputDocument> docs = new ArrayList(beans.size());
        Iterator var6 = beans.iterator();
        while(var6.hasNext()) {
            Object bean = var6.next();
            docs.add(binder.toSolrInputDocument(bean));
        }
        UpdateRequest req = new UpdateRequest();
        req.setBasicAuthCredentials(this.solrConnectionConfig.getSolrUser(),this.solrConnectionConfig.getSolrPassword());
        req.add(docs);
        req.setCommitWithin(-1);
        return req.process(this.client, collection);
    }

    /**
     * 查询
     * @param params 查询串
     * @param collection 连接内核
     *
     * @throws Exception
     */
    public QueryResponse query(SolrParams params, String collection) throws Exception{
        QueryRequest req = new QueryRequest(params);
        req.setMethod(SolrRequest.METHOD.POST);
        req.setBasicAuthCredentials(this.solrConnectionConfig.getSolrUser(),this.solrConnectionConfig.getSolrPassword());
        return req.process(this.client,collection);
    }

    /**
     * 提交数据-安全验证
     * @param collection 连接内核
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public UpdateResponse commit(String collection) throws SolrServerException, IOException {
        UpdateRequest req = new UpdateRequest();
        req.setBasicAuthCredentials(this.solrConnectionConfig.getSolrUser(),this.solrConnectionConfig.getSolrPassword());
        return req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true).process(this.client, collection);
    }

    /**
     *  回滚数据-安全验证
     * @param collection 连接内核
     * @return
     */
    public UpdateResponse rollback(String collection) throws IOException, SolrServerException {
        UpdateRequest req = new UpdateRequest();
        req.setBasicAuthCredentials(this.solrConnectionConfig.getSolrUser(),this.solrConnectionConfig.getSolrPassword());
        return req.rollback().process(this.client, collection);
    }

    /**
     *  索引优化-安全验证
     * @param collection 连接内核
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public UpdateResponse optimize(String collection) throws SolrServerException, IOException {
        UpdateRequest req = new UpdateRequest();
        req.setBasicAuthCredentials(this.solrConnectionConfig.getSolrUser(),this.solrConnectionConfig.getSolrPassword());
        return req.setAction(AbstractUpdateRequest.ACTION.OPTIMIZE, true, true, 1).process(this.client, collection);
    }
}
