//package com.springcloud.book.foreign.service.impl.solr_service_deals;
//
//import com.medbook.foreigndata.core.biz.service.ClassificationService;
//import com.medbook.foreigndata.core.util.DateUtil;
//import com.medbook.foreigndata.dal.domain.DocumentUnion;
//import com.medbook.foreigndata.dal.domain.solrbeans.StatisticsAbstractSolr;
//import com.medbook.foreigndata.dal.tools.PageData;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DocumentUnionDeal {
//    public static List<DocumentUnion> addSolrData2Mysql(List<StatisticsAbstractSolr> records, ClassificationService classificationService){
//        List<DocumentUnion> duList = new ArrayList<>();
//        for (StatisticsAbstractSolr sas : records){
//            DocumentUnion du = new DocumentUnion();
//            PageData pageData = new PageData();
//            pageData.put("journalId",sas.getJournalId());
//            List<String> sbdn = new ArrayList<>();
//            List<String> sbdv = new ArrayList<>();
//            List<PageData> pdList = classificationService.getJournalIfsAndClassificationInfo(pageData);
//            // ifs,subjectName,menuName
//            for (PageData pd : pdList){
//                sbdv.add(pd.getString("subjectName"));
//                sbdn.add(pd.getString("menuName"));
//            }
//            du.setTitle(sas.getArticleTitle());
//            du.setAuthor(sas.getAuthorsStr());
//            du.setHospitalNum(StringUtils.join(sas.getTatisticsAbstractsBlongToHospitalName(),";"));
//            du.setHospitalName(sas.getAffiliationStr());
//            du.setPublishTime(DateUtil.format(sas.getMedlineDate(),"yyyy"));
//            du.setIncluded(StringUtils.join(sas.getJournalIndexes(),";"));
//            du.setSubjectName(StringUtils.join(sbdn,";"));
//            du.setSubjectValue(StringUtils.join(sbdv,";"));
//            du.setJournalName(sas.getJournalTitleMain());
//            if (pdList != null && !pdList.isEmpty()){
//                PageData pd = pdList.get(0);
//                if (pd.getString("ifs") !=null && !"".equals(pd.getString("ifs"))){
//                    du.setAllIfs(pd.getString("ifs"));
//                }
//            }
//            duList.add(du);
//        }
//        return duList;
//    }
//}
