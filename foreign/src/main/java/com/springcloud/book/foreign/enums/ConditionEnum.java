package com.springcloud.book.foreign.enums;

import com.springcloud.book.foreign.config.PageData;

public enum ConditionEnum {
    EMPTY(-1,""),AND(0,"AND"),OR(1,"OR"),NOT(2,"NOT"),
    AND_GE(3,"ANDGE"),//>=
    AND_GT(10,"ANDGT"),//>
    ANDLE(4,"ANDLE"),//<=
    AND_LT(11,"ANDLT"),//<
    OR_GE(5,"ORGE"),OR_LE(6,"ORLE"),
    IS_NOT_NULL(7,"HAVE"),BETWEEN(8,"IN"),
    SUBBETWEEN(9,"SUBIN"),
    //最大值11
    ;

    private final int key;
    private final String value;

    private ConditionEnum(int key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getEnumValueByKey(int keyStr){
        for (ConditionEnum ce:ConditionEnum.values()){
            if(ce.getKey() == keyStr){
                return ce.getValue();
            }
        }
        return null;
    }

    public static int getEnumKeyByValue(String value){
        for (ConditionEnum ce:ConditionEnum.values()){
            if(ce.getValue().equals(value)){
                return ce.getKey();
            }
        }
        return -1;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static void main(String[] args){
        PageData pd = new PageData();
        PageData p1 = new PageData();
        p1.put("conditionWOrld","AND");
        p1.put("articleTitle","This is Car processorutil");
        PageData p2 = new PageData();
        p2.put("conditionWOrld","OR");
        p2.put("articleTitle","This is Cat processorutil");
        pd.put("p1",p1);
        pd.put("p2",p2);
        System.out.println(pd);
    }

}
