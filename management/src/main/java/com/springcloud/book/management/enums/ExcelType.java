package com.springcloud.book.management.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by grl
 */
public enum ExcelType {
    abstracts,logOrders,count,orderview,unitAbstractEmportVo,unzipCheck;
	
	//导出
    public Map<String, String> enName() {
        Map map = new LinkedHashMap();
        switch (this) {
            case abstracts:
                map.put("authorsStr", "作者");
                map.put("articleTitle", "题名");
                map.put("elocationIdDOI", "DOI");
                map.put("journalTitleMain", "期刊名称");
                map.put("issn", "ISSN");
                map.put("medlineDate", "年");
                map.put("volume", "卷");
                map.put("issue", "期");
                map.put("medlinePage", "页码");
                map.put("affiliationStr", "机构（作者单位）");
                map.put("pmid","pmid号");
                map.put("issn","issn号");
                map.put("medlineTA","medlineTA号");
                map.put("abstractText","摘要信息");
                map.put("language","语言");
                map.put("country","国家");
                map.put("nlmUniqueID","国立医学图书馆唯一编号");
                map.put("isoAbbreviation","国际标准化组织标题缩写");
                map.put("meshHeads","主题词");
                map.put("qualifier","限定词");
                map.put("keyWords","关键词");
                map.put("journalIndexes","期刊收录");
                return map;
            case logOrders:
                map.put("num", "序号");
                map.put("userName", "用户名");
                map.put("userEmail", "邮箱");
                map.put("userUnit", "所属机构");
                map.put("abstractTitle", "标题");
                map.put("journalTitle", "期刊");
                map.put("applyTime", "请求时间");
                map.put("orderType", "状态");
                return map;
            case count:
                map.put("journalId", "期刊id");
                map.put("nlmId", "NLMID");
                map.put("count", "数量");
                map.put("journalName","期刊名称");
                return map;
            case orderview:
                map.put("orderNum", "订单号");
                map.put("orderAcceptAdminName", "订单处理人");
                map.put("orderState", "订单状态");
                map.put("orderApplyTime","订单申请时间");
                map.put("orderAcceptTime","订单接受时间");
                map.put("orderFinalActionTime","订单完成时间");
                map.put("orderHandleTypeDesc","订单模式");
                map.put("orderResponseTimes","订单响应时间");
                map.put("orderEfficiencyTimes","订单处理效率");
                return map;
            case unitAbstractEmportVo:
                map.put("articleTitle", "标题");
                map.put("authorsStr", "作者");
                map.put("affiliationStr", "作者单位");
                map.put("journalTitleMain", "来源出版物");
                map.put("volume","卷");
                map.put("issue","期");
                map.put("elocationIdDOI","DOI");
                map.put("articleTypes","文件类型");
                map.put("ifs","影响因子");
                map.put("medlinePage","页");
                map.put("medlineDate","出版年");
                return map;
            case unzipCheck:
                map.put("journalName","期刊名称");
                map.put("abParentDir","NLM_ID");
                map.put("abVolume","期刊卷号");
                map.put("abIssue","期刊期号");
                map.put("abNum","文献数量");
                return map;
            default:
                throw new RuntimeException();
        }
    }
}
