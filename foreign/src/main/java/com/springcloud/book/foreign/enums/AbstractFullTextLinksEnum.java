package com.springcloud.book.foreign.enums;

public enum AbstractFullTextLinksEnum {
    pubmed,doi,pii,pmc;

    public static String getValue(Integer key){
        String value = null;
        switch (key){
            case 1:
                value = AbstractFullTextLinksEnum.pubmed.toString();
                break;
            case 2:
                value = AbstractFullTextLinksEnum.doi.toString();
                break;
            case 3:
                value = AbstractFullTextLinksEnum.pii.toString();
                break;
            case 4:
                value = AbstractFullTextLinksEnum.pmc.toString();
                break;
        }
        return value;
    }

    public static void main(String[] args){
        String value = AbstractFullTextLinksEnum.getValue(5);
        System.out.println(value);
    }

}
