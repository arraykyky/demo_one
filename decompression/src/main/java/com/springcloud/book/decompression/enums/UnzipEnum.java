package com.springcloud.book.decompression.enums;

public enum UnzipEnum {
    ROOT("ROOT","D:\\unzip\\");

    private String key;
    private String value;

    private UnzipEnum(String key,String value){
        this.key = key;
        this.value = value;
    }

    public static String getValue(String key){
        for (UnzipEnum unzipEnum :UnzipEnum.values()){
            if (unzipEnum.getKey().equals(key)){
                return unzipEnum.getValue();
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
