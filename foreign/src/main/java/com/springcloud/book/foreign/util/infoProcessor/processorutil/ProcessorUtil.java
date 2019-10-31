package com.springcloud.book.foreign.util.infoProcessor.processorutil;

import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.DocInfoVO;
import com.springcloud.book.foreign.util.infoProcessor.factory.ProcessorFactory;
import com.springcloud.book.foreign.util.infoProcessor.processor.Processor;

import java.util.List;

public class ProcessorUtil {
    //solr文献数据导出
    public static List<DocInfoVO> exportSolr(List<AbstractSolr> absttList, String factoryName){
        Processor ps = ProcessorFactory.getProcessor(factoryName);
        List<DocInfoVO> list = ps.proccessSolr(absttList);
        return list;
    }
}
