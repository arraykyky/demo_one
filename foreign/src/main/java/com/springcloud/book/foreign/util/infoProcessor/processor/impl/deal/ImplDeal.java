package com.springcloud.book.foreign.util.infoProcessor.processor.impl.deal;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.DocInfoVO;
import com.springcloud.book.foreign.domain.vo.LabelVO;
import com.springcloud.book.foreign.util.DateUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImplDeal {
    //数据封装
    public static void packageData(AbstractSolr abstr, List<DocInfoVO> docInfoList, LabelVO label, String conSign, String type){
        DocInfoVO docInfo = new DocInfoVO();
        //设置文件类型
        docInfo.setType(label.getType() + conSign + type);
        //设置作者
        List<String> aus = abstr.getAuthorsStr();
        if (!aus.isEmpty()){
            List<String> auList = new ArrayList<>();
            for (String auStr : aus){
                String str = label.getAuthor() + conSign + auStr;
                auList.add(str);
            }
            docInfo.setAuthors(auList);
        }
        //设置单位
        if (StringUtils.isNotBlank(abstr.getAffiliationString())){
            docInfo.setOrganization(label.getOrganization() + conSign + abstr.getAffiliationStr());
        }
        //设置标题
        if (StringUtils.isNotBlank(abstr.getArticleTitle())){
            docInfo.setTitle(label.getTitle() + conSign + abstr.getArticleTitle());
        }
        //设置文献出处(杂志名)
        if (StringUtils.isNotBlank(abstr.getJournalTitleMain())){
            docInfo.setJournal(label.getJournal() + conSign + abstr.getJournalTitleMain());
        }
        //设置年卷期
        if (StringUtils.isNotBlank(DateUtil.format(abstr.getMedlineDate(),"yyyy"))){
            docInfo.setYear(label.getYear() + conSign + abstr.getMedlineDate());
        }
        if (StringUtils.isNotBlank(abstr.getVolume())){
            docInfo.setVolume(label.getVolume() + conSign + abstr.getVolume());
        }
        if (StringUtils.isNotBlank(abstr.getIssue())){
            docInfo.setIssue(label.getIssue() + conSign + abstr.getIssue());
        }
        //设置关键词
        if (StringUtils.isNotBlank(abstr.getKeyWordsStr())){
            docInfo.setKeywords(label.getKeywords() + conSign + abstr.getKeyWords());
        }
        //设置摘要
        if (StringUtils.isNotBlank(abstr.getAbstractText())){
            String text = abstr.getAbstractText();
            Map textMap = JSON.parseObject(text, HashMap.class);
            StringBuffer sb = new StringBuffer();
            Object ba = textMap.get("BACKGROUND");
            if (ba != null){
                sb.append(ba.toString());
            }
            Object ob = textMap.get("OBJECTIVE");
            if (ob != null){
                sb.append(ob.toString());
            }
            Object me = textMap.get("METHODS");
            if (me !=null){
                sb.append(me.toString());
            }
            Object re = textMap.get("RESULTS");
            if (re != null){
                sb.append(re.toString());
            }
            Object co = textMap.get("CONCLUSIONS");
            if (co != null){
                sb.append(co.toString());
            }
            Object un = textMap.get("UNASSIGNED");
            if (un != null){
                sb.append(un.toString());
            }
            Object t = textMap.get("TEXT");
            if (t != null){
                sb.append(t.toString());
            }
            docInfo.setAbstracts(label.getAbstracts() + conSign + sb.toString());
        }
        //设置页码
        if (StringUtils.isNotBlank(abstr.getMedlinePage())){
            docInfo.setPageScope(label.getPageScope() + conSign + abstr.getMedlinePage());
        }
        //设置issn
        if (StringUtils.isNotBlank(abstr.getIssn())){
            docInfo.setISSN(label.getISSN() + conSign + abstr.getIssn());
        }
        //设置国际编号
        if(StringUtils.isNotBlank(abstr.getElocationIdDOI())){
            docInfo.setLocalNumber(label.getLocalNumber() + conSign + abstr.getElocationIdDOI());
        }
        //设置数据库提供商
        docInfo.setDatabaseProvider(label.getDatabaseProvider() + conSign + "HZBOOK");
        docInfoList.add(docInfo);
    }
}
