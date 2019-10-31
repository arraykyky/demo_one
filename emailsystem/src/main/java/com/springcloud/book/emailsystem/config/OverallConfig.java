package com.springcloud.book.emailsystem.config;

public class OverallConfig {

    //返回结果编码参数
    public static final String CODE = "responseCode";
    //返回结果数据参数
    public static final String DATA = "responseData";
    //返回结消息信息参数
    public static final String MSG = "responseMsg";
    //返回结果历史检索参数
    public static final String LASTTERM = "lastTerm";
    //返回正常结果码
    public static final String SUCCESS = "200";
    //返回异常结果码
    public static final String ERROR = "500";
    //返回正常结果消息数据
    public static final String SUCCESS_MSG = "请求成功";

    //解读点启停配置，此配置同时存在emailsystem项目/foreign项目/management项目，一处修改，其他俩出也要做修改
    public static final boolean ENABLE_BORROW_POINT = true;

}
