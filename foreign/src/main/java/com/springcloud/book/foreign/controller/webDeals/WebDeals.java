//package com.springcloud.book.foreign.controller.webDeals;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedOutputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class WebDeals {
//
//    //将字符串转字符串集合
//    public static List<String> str2StrList(String regex,String str){
//        return new ArrayList(Arrays.asList(str.trim().split(regex)));
//    }
//
//    //将数值型字符串转数值集合
//    public static List<Integer> strNum2IntList(String regex,String str){
//        List<Integer> conList = new ArrayList<>();
//        String[] conArrayStr = str.trim().split(regex);
//        WebDeals.strArray2intList(conArrayStr,conList);
//        return conList;
//    }
//    //将数字型字符串数组转成数值型集合
//    public static void strArray2intList(String[] conArrayStr , List<Integer> conList){
//        for (int i=0;i<conArrayStr.length;i++){
//            String str = conArrayStr[i];
//            if (Tools.isNumer(str)){
//                conList.add(Integer.parseInt(str));
//            }else {
//                new RuntimeException("参数类型错误");
//            }
//        }
//    }
//
//    //文献摘要信息导出
//    public static void export(HttpServletResponse response, List<DocInfo> abstractList, ExprotData ed){
//        //设置response参数及filename的默认名
//        response.setContentType("text/plain");
//        //EndNote:"0"/NoteExpress:"1"/自定义Myself:"2"(txt/net/txt)格式
//        if (!"1".equals(ed.getExportType())){
//            response.addHeader("Content-Disposition","attachment;filename=HZBOOK-"+ System.currentTimeMillis() +".txt");
//        }else {
//            response.addHeader("Content-Disposition","attachment;filename=HZBOOK-"+ System.currentTimeMillis() +".net");
//        }
//        //容器输出流
//        ServletOutputStream outSTr = null;
//        //缓冲字节输出流
//        BufferedOutputStream buff = null;
//        try {
//            //数据组装
//            StringBuffer stringBuffer = new StringBuffer();
//            for (DocInfo di : abstractList) {
//                WebDeals.packageData(stringBuffer,di,ed);
//                //最后一条数据时不再打印空行
//                int i = 0;
//                if (abstractList.size() >1 &&  i < abstractList.size()-1){
//                    stringBuffer.append("\r\n");
//                }
//                i++;
//            }
//            //将数据打印到txt文档上
//            outSTr = response.getOutputStream();//获取容器输出流
//            buff = new BufferedOutputStream(outSTr);//封装缓冲字节流
//            byte[] bytes = stringBuffer.toString().getBytes("UTF-8");
//            //设置相应文本内容大小
//            response.setContentLength(bytes.length);
//            buff.write(bytes);
//            buff.flush();
//            buff.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (outSTr != null){
//                    outSTr.close();
//                }
//                if (buff != null){
//                    buff.close();
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//    //导出数据封装
//    public static void packageData(StringBuffer stringBuffer, DocInfo di, ExprotData ed){
//        //exportType 0 1 时全部导出;2时部分导出
//        String enter = "\r\n";
//        //导出全部字段
//        if (!"2".equals(ed.getExportType())){
//            ed = new ExprotData(true);
//        }
//        //类型
//        if (!StringUtil.isEmpty(di.getType())){
//            stringBuffer.append(di.getType()).append(enter);
//        }
//        //标题
//        if (!StringUtil.isEmpty(di.getTitle()) && ed.isTitle()){
//            stringBuffer.append(di.getTitle()).append(enter);
//        }
//        //作者
//        List<String> author = di.getAuthors();
//        if (author!= null && !author.isEmpty() && ed.isAuthor()){
//            for (String str : author){
//                stringBuffer.append(str).append(enter);
//            }
//        }
//        //作者单位
//        if (!StringUtil.isEmpty(di.getOrganization()) && ed.isUnit()) {
//            stringBuffer.append(di.getOrganization()).append(enter);
//        }
//        //杂志
//        if (!StringUtil.isEmpty(di.getJournal()) && ed.isJournalTitle()){
//            stringBuffer.append(di.getJournal()).append(enter);
//        }
//        //年卷期
//        if (!StringUtil.isEmpty(di.getYear()) && ed.isYear()){
//            stringBuffer.append(di.getYear());
//            if (!StringUtil.isEmpty(di.getVolume()) && ed.isVolum()){
//                stringBuffer.append(di.getVolume()).append(enter);
//            }
//        }
//        if (!StringUtil.isEmpty(di.getIssue()) && ed.isIssue()){
//            stringBuffer.append(di.getIssue()).append(enter);
//        }
//        //页面
//        if (!StringUtil.isEmpty(di.getPageScope()) && ed.isPage()){
//            stringBuffer.append(di.getPageScope()).append(enter);
//        }
//        //关键词
//        if (!StringUtil.isEmpty(di.getKeywords()) && ed.isKeyWorlds()){
//            stringBuffer.append(di.getKeywords()).append(enter);
//        }
//        //摘要
//        if (!StringUtil.isEmpty(di.getAbstracts()) && ed.isAbstr()){
//            stringBuffer.append(di.getAbstracts()).append(enter);
//        }
//        //ISSN号
//        if (!StringUtil.isEmpty(di.getISSN()) && ed.isIssn()){
//            stringBuffer.append(di.getISSN()).append(enter);
//        }
//        //国际编号
//        if (!StringUtil.isEmpty(di.getLocalNumber()) && ed.isNote()){
//            stringBuffer.append(di.getLocalNumber()).append(enter);
//        }
//        //数据库提供商
//        if (!StringUtil.isEmpty(di.getDatabaseProvider()) && ed.isDper()){
//            stringBuffer.append(di.getDatabaseProvider()).append(enter);
//        }
//    }
//}
