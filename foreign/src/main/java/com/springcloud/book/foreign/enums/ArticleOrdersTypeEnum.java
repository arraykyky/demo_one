package com.springcloud.book.foreign.enums;

public enum ArticleOrdersTypeEnum {

    //订单状态(0010被拒绝;0000申请中，默认值;0001处理中;0011已完成;00000未找到全文)
    APPLY(0,"0000"),
    HANDLE(1,"0001"),
    REFUSE(2,"0010"),
    COMPLETE(3,"0011"),
    NOTEXT(-1,"00000"),
    ALL(-2,null);

    private final Integer key;
    private final String value;

    private ArticleOrdersTypeEnum(Integer key,String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    //通过key获取订单状态
    public static String getTypeValueByKey(Integer key){
        for (ArticleOrdersTypeEnum e : ArticleOrdersTypeEnum.values()){
            if (e.getKey() == key){
                return e.getValue();
            }
        }
        return null;
    }

}
