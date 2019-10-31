package com.springcloud.book.foreign.util.infoProcessor.processor;

import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.DocInfoVO;

import java.util.List;

/**
 *
 * @author Ryan
 * 处理器接口，定义了一个处理外部导入的文献信息的方法
 *
 */
public interface Processor {
	List<DocInfoVO> proccessSolr(List<AbstractSolr> abstractList);

}
