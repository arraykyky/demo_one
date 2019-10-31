package com.springcloud.book.foreign.util;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.domain.vo.SearchVoBean;
import com.springcloud.book.foreign.enums.ConditionEnum;
import com.springcloud.book.foreign.enums.SolrSearchFieldEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchExpression {

    //递归截取字符串并存储入栈
    public static void subStr(String target,Stack<String> stack){
        int index = target.lastIndexOf(")");
        String childrenExperience = null;
        String newTarget = null;
        if (index > 0){
            childrenExperience = target.substring(index + 1).trim();
            newTarget = target.substring(1,index);
        }else {
            childrenExperience = target;
            newTarget = null;
        }
        if (childrenExperience!=null){
            stack.push(childrenExperience);
        }
        if (newTarget!=null){
            subStr(newTarget,stack);
        }
    }

    //递归截取字符串并存储入栈 -- 拼接AND/OR/NOT
    public static void subStr(String target,Stack<String> stack,String con){
        int index = target.lastIndexOf(")");
        String childrenExperience = null;
        String newTarget = null;
        if (index > 0){
            childrenExperience = target.substring(index + 1).trim();
            //AND直接拼接；OR/NOT时替换exp中的连接词
            if (!"AND".equals(con)){
                childrenExperience =  childrenExperience.replaceFirst(find(childrenExperience),con);
            }
            newTarget = target.substring(1,index);
        }else {
            childrenExperience = target;
            childrenExperience = con + " " + childrenExperience;
            newTarget = null;
        }
        if (childrenExperience!=null){
            stack.push(childrenExperience);
        }
        if (newTarget!=null){
            subStr(newTarget,stack,con);
        }
    }

    public static String find(String exp){
        String regex = null;
        Matcher matcher = Pattern.compile("[A-Z]").matcher(exp);
        if(matcher.find()) {
            regex = exp.substring(matcher.start(), exp.indexOf(" ")).trim();
        }else {
            throw new RuntimeException("检索表达式(exp)替换连接词寻找异常");
        }
        return regex;
    }

    public static void expsConsStack(List<String> targs, List<String> cons, Stack<String> stack){
        if (targs !=null && !targs.isEmpty()){
            for (int i=targs.size()-1;i>=0;i--){
                String target = targs.get(i);
                if (i>0){
                    String con = ConditionEnum.getEnumValueByKey(Integer.valueOf(cons.get(i-1)));
                    subStr(target,stack,con);
                }else {
                    subStr(target,stack);
                }
            }
        }else {
            throw new RuntimeException("多个检索表达式重组异常");
        }
    }


    public static List<Map> stackToSearch(Stack<String> stack){
        List<SearchVoBean> listMap = new ArrayList<>();
        while(!stack.isEmpty()){
            String exp = stack.pop();
            //连接词
            int index = exp.indexOf(" ");
            //连接词对应key，若存在则返回对应的key，否则返回-1
            int connKey = -1;
            int subConStart = 0;
            if (index > 0){
                String con = exp.substring(0,index).trim();
                connKey = ConditionEnum.getEnumKeyByValue(con);
                if (connKey >= 0){
                    subConStart = con.length() + 1;
                }
            }
            //检索字段
            String field = exp.substring(exp.lastIndexOf("[")+1,exp.lastIndexOf("]")).trim();
            //检索字段对应key，若存在则返回对应的key，否则返回-1
            int fieldKey = SolrSearchFieldEnum.getEnumKeyByValue(field);
            //检索值
            String value = exp.substring(subConStart,exp.lastIndexOf("[")).trim();
            SearchVoBean svb = null;
            if (fieldKey >=0 && value != null){
                svb = new SearchVoBean();
                svb.setConn(String.valueOf(connKey));
                svb.setSearchWorld(String.valueOf(fieldKey));
                if ("EMPTY".equals(value)){
                    svb.setSearchValue("");
                }else {
                    svb.setSearchValue(value);
                }
            }
            if (svb != null){
                listMap.add(svb);
            }
        }
        return JSON.parseObject(listMap.toString(),new ArrayList<Map>().getClass());
    }
}
