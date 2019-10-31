package com.springcloud.book.foreign.util.infoProcessor.processor.impl;

import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.DocInfoVO;
import com.springcloud.book.foreign.domain.vo.LabelVO;
import com.springcloud.book.foreign.util.infoProcessor.label.factory.LabelFactory;
import com.springcloud.book.foreign.util.infoProcessor.processor.Processor;
import com.springcloud.book.foreign.util.infoProcessor.processor.impl.deal.SolrImplDeal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ryan
 * 该类实现了Processor接口,专门处理EndNote格式的文献信息
 *
 */
public class MyselfSetProcessor implements Processor {

	@Override
	public List<DocInfoVO> proccessSolr(List<AbstractSolr> abstractList) {
		List<DocInfoVO> docInfoList = new ArrayList<>();
		LabelVO label = LabelFactory.getMyselfSetLabel();
		String type = "Journal Article";
		String conSign = " ";
		for (AbstractSolr abstr : abstractList){
			SolrImplDeal.packageData(abstr,docInfoList,label,conSign,type);
		}
		return docInfoList;
	}
}
