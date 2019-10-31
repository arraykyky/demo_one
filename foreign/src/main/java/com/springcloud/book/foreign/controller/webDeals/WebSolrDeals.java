package com.springcloud.book.foreign.controller.webDeals;

import com.springcloud.book.foreign.domain.vo.DocInfoVO;
import com.springcloud.book.foreign.domain.vo.ExprotAbsractDataVO;
import com.springcloud.book.foreign.domain.vo.SingleSearchVO;
import com.springcloud.book.foreign.enums.ConditionEnum;
import com.springcloud.book.foreign.enums.SolrSearchFieldEnum;
import com.springcloud.book.foreign.util.DateUtil;
import com.springcloud.book.foreign.util.Tools;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.*;

/**
 * solr检索，controller数据封装
 */
public class WebSolrDeals {

    //单字段--检索串生成
    public static String ordinarySolrFreeWorldSearch(String searchWorld,String searchValue,boolean isCheckOut){
        String qStr = "*:*";
        StringBuffer stringBuffer = new StringBuffer();
        if (Tools.isNumer(searchWorld)){
            try {
                String field = SolrSearchFieldEnum.getTableFieldByKey(Integer.valueOf(searchWorld));
                stringBuffer.append(field + ":");
                if (searchValue !=null && !"".equals(searchValue)){
                    if (isCheckOut){
                        stringBuffer.append("\""+searchValue+"\"");
                    }else {
                        stringBuffer.append(searchValue);
                    }
                }else {
                    stringBuffer.append("*");
                }
                qStr = stringBuffer.toString();
            }catch (Exception e){
                throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
            }
        }
//        else {
//            throw new RuntimeException("searchWorld is not number OR searchValue is null/\"\"");
//        }
        return qStr;
    }

    //单字段--检索串生成--自由词检索--跳词检索
    public static String ordinarySolrFreeWorldSearchForJump(String qStr, String searchWorld,String searchValue,List<Map> historySearchList){
        StringBuffer stringBuffer = new StringBuffer();
        if (searchWorld != null && !"".equals(searchWorld) && Tools.isNumer(searchWorld) && searchValue !=null && !"".equals(searchValue)){
            stringBuffer.append(qStr + " OR ");
            try {
                String field = SolrSearchFieldEnum.getTableFieldByKey(Integer.valueOf(searchWorld));
                String[] svArray = Tools.getTextTrim(searchValue).split(" ");
                if (svArray.length > 0){
                    stringBuffer.append("(");
                    for (int i = 0;i< svArray.length ;i++){
                        Map<String ,String> map = new HashMap();
                        if (i==0){
                            map.put("conn","1"); //OR
                        }else {
                            map.put("conn","0"); //OR
                        }
                        map.put("searchWorld",searchWorld);
                        map.put("searchValue",svArray[i]);
                        if (i < svArray.length - 1){
                            stringBuffer.append(field + ":" + svArray[i] + " AND ");
                        }else {
                            stringBuffer.append(field + ":" + svArray[i] + ")");
                        }
                        historySearchList.add(map);
                    }
                }
                qStr = stringBuffer.toString();
            }catch (Exception e){
                throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
            }
        }
//        else {
//            throw new RuntimeException("searchWorld is not number OR searchValue is null/\"\"");
//        }
        return qStr;
    }

    //多字段--检索串生成
    public static String ordinarySolrFreeWorldSearch(List<Map> searchFieldList){
        String qStr = "*:*";
        StringBuffer stringBuffer = new StringBuffer();
        int index = 0;
        for (Map map : searchFieldList){
            String searchWorld = map.get("searchWorld").toString();
            String searchValue = map.get("searchValue").toString();
            if (searchWorld != null && !"".equals(searchWorld) && Tools.isNumer(searchWorld)){
                try {
                    if (index > 0){
                        Object conn = map.get("conn");
                        String connStr = "AND";
                        if (conn != null && !"".equals(conn)){
                            connStr = ConditionEnum.getEnumValueByKey(Integer.valueOf(conn.toString()));
                        }
                        stringBuffer.insert(0,"(");
                        stringBuffer.append(")");
                        stringBuffer.append(" "+connStr+" ");
                    }
                    int searchWorldNum = Integer.valueOf(searchWorld);
                    if ((searchValue.equals("")||searchValue.equals("*")) && (searchWorldNum == 14 || searchWorldNum == 15)){
                        String field = SolrSearchFieldEnum.getTableFieldByKey(searchWorldNum);
                        stringBuffer.append("-" +field + ":");
                        stringBuffer.append("*");
                    }else {
                        String field = SolrSearchFieldEnum.getTableFieldByKey(searchWorldNum);
                        stringBuffer.append(field + ":");
                        if ((searchWorldNum == 14 || searchWorldNum == 15)){
                            stringBuffer.append("\"" + searchValue.toString() + "\"");
                        }else {
                            stringBuffer.append(searchValue.toString());
                        }
                    }
                    qStr = stringBuffer.toString();
                }catch (Exception e){
                    throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
                }
            }else {
                throw new RuntimeException("searchWorld is not number!");
            }
            index ++;
        }
        return qStr;
    }


    //单字段--高亮字段设置
    public static String[] ordinarySolrFreeWorldSearchHightFields(String searchWorld){
        List<String> listStr = new ArrayList<>();
        if (searchWorld != null && Tools.isNumer(searchWorld)){
            int searchWorldNum = Integer.valueOf(searchWorld);
            if (searchWorldNum == 0){
                try {
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(1));
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(2));
                }catch (Exception e){
                    throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
                }
            }else if (searchWorldNum == 5){
                try {
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(6));
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(7));
                }catch (Exception e){
                    throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
                }
            }else if (searchWorldNum == 10){
                try {
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(28));
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(29));
                }catch (Exception e){
                    throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
                }
            }else if (searchWorldNum == 108109){
                try {
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(108));
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(109));
                }catch (Exception e){
                    throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
                }
            }else if (searchWorldNum == 107){
                try {
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(108));
                }catch (Exception e){
                    throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
                }
            }else if (searchWorldNum != 13 && searchWorldNum != 23 && searchWorldNum != 24 && searchWorldNum != 26
                    && searchWorldNum != 30 && searchWorldNum != 31
                    && searchWorldNum != 100 && searchWorldNum != 101 && searchWorldNum != 102 && searchWorldNum != 103
                    && searchWorldNum != 104 && searchWorldNum != 105 && searchWorldNum != 106){
                try {
                    listStr.add(SolrSearchFieldEnum.getTableFieldByKey(searchWorldNum));
                }catch (Exception e){
                    throw new RuntimeException("“searchWorld” is not existent.It means that the “searchWorld” value is wrong!");
                }
            }
        }
//        else {
//            throw new RuntimeException("“searchWorld” is not number!");
//        }
        return listStr.toArray(new String[listStr.size()]);
    }

    //多字段--高亮字段设置
    public static String[] ordinarySolrFreeWorldSearchHightFields(List<Map> searchFieldList){
        List<String> listStr = new ArrayList<>();
        for (Map map:searchFieldList){
            Object searchWorld = map.get("searchWorld");
            if (searchWorld != null && !"".equals(searchWorld)){
                String[] array = ordinarySolrFreeWorldSearchHightFields(searchWorld.toString());
                if (array!=null && array.length > 0){
                    listStr.addAll(new ArrayList(Arrays.asList(array)));
                }
            }
        }
        return listStr.toArray(new String[listStr.size()]);
    }

    //检索字段封装
    public static Map<String,Object> searchFieldMap(String conn,String searchWorld,Object searchValue){
        Map<String,Object> map = new HashMap<>();
        map.put("conn",conn);
        map.put("searchWorld",searchWorld);
        map.put("searchValue",searchValue);
        return map;
    }

    //高级检索--筛选检索字段条件封装
    public static Map<String,Object> seniorSelectSearchFieldMap(String str) {
        //有免费全文
        if (str.equals("0")){
            return searchFieldMap("","21",1);
        }
        //有全文
        if(str.equals("1")) {
            return searchFieldMap("","22",1);
        }
        //有摘要
        if (str.equals("2")){
            return searchFieldMap("","2","[\"\" TO *]");
        }
        //综述摘要
        if (str.equals("3")){
            return searchFieldMap("","20","Review");
        }
        // 被F1000收录
        if (str.equals("4")){
            return searchFieldMap("","23","F1000");
        }
        return null;
    }

    //普通检索----单篇检索
    public static void singleSearch(SingleSearchVO singleSearchVO, List<Map> searchVoBeanList){
        String titleMain = singleSearchVO.getTitleMain();
        String year = singleSearchVO.getYear();
        String volume = singleSearchVO.getVolume();
        String issue = singleSearchVO.getIssue();
        String startPage = singleSearchVO.getStartPage();
        if (titleMain != null && !"".equals(titleMain)){
            Map<String, Object> map = WebSolrDeals.searchFieldMap("", "8", titleMain.replaceAll(":",""));
            searchVoBeanList.add(map);
        }
        if (year != null && !"".equals(year)){
            String startYear = DateUtil.formatSolrDataStr(DateUtil.getFirstYear(Integer.valueOf(year)));
            String endYear = DateUtil.formatSolrDataStr(DateUtil.getLastYear(Integer.valueOf(year)));
            String time = "[\"" + startYear + "\" TO \"" + endYear + "\"]";
            Map<String, Object> map = WebSolrDeals.searchFieldMap("", "13", time);
            searchVoBeanList.add(map);
        }
        if (volume != null && !"".equals(volume)){
            Map<String, Object> map = WebSolrDeals.searchFieldMap("", "14", volume);
            searchVoBeanList.add(map);
        }
        if (issue != null && !"".equals(issue)){
            Map<String, Object> map = WebSolrDeals.searchFieldMap("", "15", issue);
            searchVoBeanList.add(map);
        }
        if (startPage != null && !"".equals(startPage)){
            Map<String, Object> map = WebSolrDeals.searchFieldMap("", "16", startPage+"*");
            searchVoBeanList.add(map);
        }
    }


    // solr同字段下多值检索，多值串组装
    public static String createSubStr(List<String> cfList){
        StringBuffer stringBuffer = new StringBuffer();
        if (cfList != null && !cfList.isEmpty()){
            int i = 0;
            stringBuffer.append("(");
            for (String cf : cfList){
                if (i == 0){
                    stringBuffer.append("\"" + cf + "\"");
                }else {
                    stringBuffer.append(",\"" + cf + "\"");
                }
                i++;
            }
            stringBuffer.append(")");
        }
        return stringBuffer.toString() == null || "".equals(stringBuffer.toString()) ? null : stringBuffer.toString()  ;
    }

    //时间段检索
    public static String solrDateSearchStr(String start,String end){
        String timeValue = null;
        if (start != null && !"".equals(start) && Tools.isNumer(start)){
            if (end == null || "".equals(end) || !Tools.isNumer(end)){
                Date endDate = new Date();
                end = DateUtil.formatCurrentDateTime(endDate.getTime());
            }
            Date startDate = DateUtil.getFirstYear(Integer.valueOf(start));
            Date endDate = DateUtil.getLastYear(Integer.valueOf(end));
            String solrStartDate = DateUtil.formatSolrDataStr(startDate);
            String solrEndDate = DateUtil.formatSolrDataStr(endDate);
            //检索的是1970年以前的数据
            if ("1970".equals(start) && start.equals(end)){
                timeValue = "[* TO " +solrEndDate+ "]";
            }else {
                timeValue = "[" + solrStartDate + " TO " + solrEndDate + "]";
            }
        }
        return timeValue;
    }

    //文献摘要信息导出
    public static void export(HttpServletResponse response, List<DocInfoVO> abstractList, ExprotAbsractDataVO ed){
        //设置response参数及filename的默认名
        response.setContentType("text/plain");
        //EndNote:"0"/NoteExpress:"1"/自定义Myself:"2"(txt/net/txt)格式
        if (!"1".equals(ed.getExportType())){
            response.addHeader("Content-Disposition","attachment;filename=HZBOOK-"+ System.currentTimeMillis() +".txt");
        }else {
            response.addHeader("Content-Disposition","attachment;filename=HZBOOK-"+ System.currentTimeMillis() +".net");
        }
        //容器输出流
        ServletOutputStream outSTr = null;
        //缓冲字节输出流
        BufferedOutputStream buff = null;
        try {
            //数据组装
            StringBuffer stringBuffer = new StringBuffer();
            for (DocInfoVO di : abstractList) {
                WebSolrDeals.packageData(stringBuffer,di,ed);
                //最后一条数据时不再打印空行
                int i = 0;
                if (abstractList.size() >1 &&  i < abstractList.size()-1){
                    stringBuffer.append("\r\n");
                }
                i++;
            }
            //将数据打印到txt文档上
            outSTr = response.getOutputStream();//获取容器输出流
            buff = new BufferedOutputStream(outSTr);//封装缓冲字节流
            byte[] bytes = stringBuffer.toString().getBytes("UTF-8");
            //设置相应文本内容大小
            response.setContentLength(bytes.length);
            buff.write(bytes);
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outSTr != null){
                    outSTr.close();
                }
                if (buff != null){
                    buff.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //导出数据封装
    public static void packageData(StringBuffer stringBuffer, DocInfoVO di, ExprotAbsractDataVO ed){
        //exportType 0 1 时全部导出;2时部分导出
        String enter = "\r\n";
        //导出全部字段
        if (!"2".equals(ed.getExportType())){
            ed = new ExprotAbsractDataVO(true);
        }
        //类型
        if (StringUtils.isNotBlank(di.getType())){
            stringBuffer.append(di.getType()).append(enter);
        }
        //标题
        if (StringUtils.isNotBlank(di.getTitle()) && ed.isTitle()){
            stringBuffer.append(di.getTitle()).append(enter);
        }
        //作者
        List<String> author = di.getAuthors();
        if (author!= null && !author.isEmpty() && ed.isAuthor()){
            for (String str : author){
                stringBuffer.append(str).append(enter);
            }
        }
        //作者单位
        if (StringUtils.isNotBlank(di.getOrganization()) && ed.isUnit()) {
            stringBuffer.append(di.getOrganization()).append(enter);
        }
        //杂志
        if (StringUtils.isNotBlank(di.getJournal()) && ed.isJournalTitle()){
            stringBuffer.append(di.getJournal()).append(enter);
        }
        //年卷期
        if (StringUtils.isNotBlank(di.getYear()) && ed.isYear()){
            stringBuffer.append(di.getYear());
            if (StringUtils.isNotBlank(di.getVolume()) && ed.isVolum()){
                stringBuffer.append(di.getVolume()).append(enter);
            }
        }
        if (StringUtils.isNotBlank(di.getIssue()) && ed.isIssue()){
            stringBuffer.append(di.getIssue()).append(enter);
        }
        //页码
        if (StringUtils.isNotBlank(di.getPageScope()) && ed.isPage()){
            stringBuffer.append(di.getPageScope()).append(enter);
        }
        //关键词
        if (StringUtils.isNotBlank(di.getKeywords()) && ed.isKeyWorlds()){
            stringBuffer.append(di.getKeywords()).append(enter);
        }
        //摘要
        if (StringUtils.isNotBlank(di.getAbstracts()) && ed.isAbstr()){
            stringBuffer.append(di.getAbstracts()).append(enter);
        }
        //ISSN号
        if (StringUtils.isNotBlank(di.getISSN()) && ed.isIssn()){
            stringBuffer.append(di.getISSN()).append(enter);
        }
        //国际编号
        if (StringUtils.isNotBlank(di.getLocalNumber()) && ed.isNote()){
            stringBuffer.append(di.getLocalNumber()).append(enter);
        }
        //数据库提供商
        if (StringUtils.isNotBlank(di.getDatabaseProvider()) && ed.isDper()){
            stringBuffer.append(di.getDatabaseProvider()).append(enter);
        }
    }

}
