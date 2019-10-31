package com.springcloud.book.foreign.util;

import com.springcloud.book.foreign.enums.ConditionEnum;
import com.springcloud.book.foreign.enums.SolrSearchFieldEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SolrSearchExpression extends SearchExpression{

    /**
     * 创建检索表达式
     * @param conditions
     * @param texts
     * @param values
     * @return
     */
    public static String creatExp(Integer[] conditions,Integer[] texts, String[] values){
        StringBuffer sb = new StringBuffer();
        String con = "";
        //遍历values，根据位置value位置获取conditions对应位置的连接词及对应位置的检索字段
        for (int i=0;i<values.length;i++){
            //给前面的检索表达式添加"("和")"
            if (sb.toString()!=null && !sb.toString().equals("")){
                sb.insert(0,"(");
                sb.append(") ");
            }
            //检索表达式拼接
            if (conditions[i] != -1 && i > 0){
                con = ConditionEnum.getEnumValueByKey(conditions[i]);
            }
            String text = SolrSearchFieldEnum.getEnumValueByKey(texts[i]);
            String value = values[i];
            if (value==null || value.equals("")){
                value = "\"\"";
            }
            String t = con + " " + value + "[" + text + "]";
            sb.append(t.trim());
        }
        return sb.toString();
    }

    /**
     * 单个exp检索表达式解析
     * @param exp
     * @return
     */
    public static List<Map> analysisExp(String exp){
        Stack<String> stack = new Stack<>();
        subStr(exp,stack);
        return stackToSearch(stack);
    }

    //多个exp检索表达式解析
    public static List<Map> analysisMoreExps(List<String> targs,List<String> cons){
        //如果expList个数大于1，下一个表达式的开头连接词，用conList中的连接词替换
        Stack<String> stack = new Stack();
        expsConsStack(targs,cons,stack);
        return stackToSearch(stack);
    }



    public static void main(String[] args){
        //Stack<String> stack = new Stack<>();
        String exp0 = "entropy[TEXT]";
        String exp1 = "((hydrated entropy[TITLE]) AND Density[TEXT]) AND [\"2006-01-01T00:00:00Z\" TO \"2018-01-01T00:00:00Z\"][YEAR]";
        List<String> exps = new ArrayList<>();
        List<String> cons = new ArrayList<>();
        exps.add(exp0);
        exps.add(exp1);
        cons.add("2");
        List<Map> listMap = analysisMoreExps(exps, cons);
        System.out.println(listMap);
    }



}
