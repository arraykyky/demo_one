package com.springcloud.book.foreign.service.impl.deals;

import com.springcloud.book.foreign.config.PageData;

import java.util.*;

public class SearchResultDeal {

    /**
     * 1.数据年份与1970比较，分为:(1)1970以前的数据;(2)1970以后的数据俩个分类
     * 2.如果是分段(2)1970年以后的数据时，计算数据年份属于的年份分段，计算公式:
     *   分段开始年份 = 数据时间 - 数据时间 % 10
     *   分段结束年份 = 分段开始年份 + 9
     * 3.分段结束年份与当前年份比较，判断存放类型
     *   分段结束年份 >= 当前年份  数据单独存放
     *   分段结束年份 < 当前年份  数据分段存放
     * @param pageData    参数传输对象
     * @param pd           数据存储对象
     * @param year         当前年份
     */
    public static void yearSta(PageData pageData, Map pd, int year){
        //数据年份
        int dYear = pageData.getInt("year");
        //该年的统计数据
        int dNum = pageData.getInt("num");
        if (dYear < 1970){
            String key = "1970年以前";
            data(pd,key,dNum);
        }else {
            //分段开始时间
            int startYear = dYear - dYear % 10;
            //分段结束时间
            int endYear = startYear + 9;
            //分段结束年份 > 当前年份  数据单独存放
            if (endYear >= year){
                pd.put(dYear,dNum);
            }
            //分段结束年份 < 当前年份  数据分段存放
            else{
                String key = startYear + "-" + endYear;
                data(pd,key,dNum);
            }
        }

    }

    //数据统计累计
    public static void data(Map pd, String key, int dNum){
        int oldNum = 0;
        if (pd.containsKey(key)){
            oldNum = Integer.valueOf(pd.get(key).toString());
        }
        int newNum = dNum + oldNum;
        pd.put(key,newNum);
    }

    public static List map2List(Map<String,String> map){
        List list = new ArrayList();
        Iterator<Map.Entry<String,String>> iter =map.entrySet().iterator();
        Map.Entry<String,String> entry;
        while (iter.hasNext()){
            entry = iter.next();
            Object key = entry.getKey();
            Object value=entry.getValue();
            list.add(key+"("+value+")");
        }
        return list;
    }

    //语言判断
    private static boolean language(String language){
        boolean flag = false;
        String[] lan = {"eng","chi","fre","ger","dan","ita","und","jpn","rus","pol"};
        for (int i = 0 ; i < lan.length ; i++){
            if (language.equals(lan[i])){
                flag = true;
            }
        }
        return flag;
    }

}
