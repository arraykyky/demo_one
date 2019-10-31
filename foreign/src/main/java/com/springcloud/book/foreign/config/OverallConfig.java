package com.springcloud.book.foreign.config;

/**
 * 全局配置类
 */
public class OverallConfig {
    ///////////////////////////////返回参数定义/////////////////////////////////////////////////
    //page参数设置
    public static final String CURRENT = "current";
    public static final String PAGE_SIZE = "size";
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
    //菜单父节点id全局变量
    public static final String MENU_PID = "0";
    ////////////////////////////solr内核数据////////////////////////////////////////////////////
    public static final String ABSTRACT_CORE = "fd_abstract";
    public static final String ABSTRACT_STATISTICS = "fd_abstract_statistics";
    public static final String ABSTRACT_STATISTICS_TEST = "fd_abstract_test";
    public static final String JOURNAL_CORE = "fd_journal";
    public static final int SOLR_TOTAL_NUM = 30000000;

    /////////////////////////////ella访问路径////////////////////////////////////////////////////
    //跨域访问url
    public static final String ELLA_URL = "http://127.0.0.1:8094";
    public static final String CLOUD_LOGIN_URL = ELLA_URL + "/feign/cloud_login";
    public static final String LOGIN_URL = ELLA_URL + "/feign/fdlogin";
    public static final String MENU_URL = ELLA_URL + "/feign/menu";
    public static final String USER_ROLE_INFO_BY_ID = ELLA_URL + "/feign/userRoleInfoById";
    public static final String USER_UNIT_TREE = ELLA_URL + "/feign/unit/tree";
    public static final String UPDATA_USER = ELLA_URL + "/feign/user/updata";
    public static final String MOTIFY_PWD = ELLA_URL + "/feign/user/motifyPwd";
    public static final String ADD_USERS_BY_FILE = ELLA_URL + "/feign/user/addUsersByFile";
    public static final String UNIT_NAME_BY_LONG_ID = ELLA_URL + "/feign/unit_name";

    /////////////////////////////PubMed检索访问路径////////////////////////////////////////////////////
    public static final String PUBMED_URL= "https://www.ncbi.nlm.nih.gov/pubmed";

    // 在平台申请的APP_ID 详见 http://api.fanyi.baidu.com/api/trans/product/desktop?req=developer
    public static final String APP_ID = "20190118000257661";
    public static final String SECURITY_KEY = "I9iCC9GBGnQ1Vnb3k32S";

    //解读点启停配置，此配置同时存在emailsystem项目/foreign项目/management项目，一处修改，其他俩出也要做修改
    public static final boolean BORROW_ENABLE_POINT = true;

    //短信
    public static final String SMS_URL = "http://sms.10690221.com:9011/hy/";
    public static final String SMS_UID = "110254";
    public static final String SMS_PASSWORD = "Scibook2019";
}
