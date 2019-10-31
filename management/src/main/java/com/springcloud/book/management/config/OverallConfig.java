package com.springcloud.book.management.config;

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
    ///////////////////////////////文件上传/////////////////////////////////////////////////
    public static final String FILE_PATH = "D:\\upload";
    public static final String TEXT_FULL = "text_full";
    ///////////////////////////////邮件/////////////////////////////////////////////////
    //新订单提醒邮件标题
    public static final String ORDER_REMIND_TITLE = "提示信息";
    //新订单提醒邮件内容
    public static final String ORDER_REMIND_CONTENT = "您有一笔新的订单，请及时处理;如若是系统订单，可忽略此次提示...";
    //无全文邮件标题
    public static final String SIMPLE_EMAIL_TITLE = "未找到全文";
    //无全文邮件正文
    public static final String SIMPLE_EMAIL_CONTENT = "您申请的全文订单,订单号为:orderNum的订单未能找到全文。";
    //全文附件邮件标题
    public static final String ATTACH_EMAIL_TITLE = "馆际互借协作中心文献反馈(订单号:#ORDERNUM#)";
    //全文附件邮件正文
    public static final String ATTACH_EMAIL_CONTENT = "<style>#bigbox{\twidth:652px;\theight:360px;\tbackground-image:url(http://115.28.172.69:8085/QTSK/images/mail_bg.png);" +
            "\tcolor:#333;\t}#bigbox .mail_title{\tfont-size:16px;\tcolor:#333;\tpadding-left:32px;\tpadding-top:42px;\t}#bigbox .mail_remind{\tfont-size:14px;\tcolor:#333;" +
            "\tlist-style-type:none;  padding:0;\tmargin-left:18px;\t}#bigbox .mail_remind li{\tmargin-top:8px;\t}#bigbox .mail_contain{\tfont-size:14px;\tcolor:#333;" +
            "\tlist-style-type:none;\tmargin-left:18px;\t}#bigbox .mail_contain li{\tmargin-top:4px;\t}</style>\n\t\t<div id=\"bigbox\">\t<div class=\"mail_title\">" +
            "亲爱的 <span style=\"font-size:16px; color:#ff3c00; font-weight:bold\">#USEREMAIL#</span>用户：</div><ul class=\"mail_remind\">\t<li>您所请求的文献已处理完成,请下载附" +
            "件文件以便查阅,谢谢!</li></ul><ul class=\"mail_contain\"><li>以下是文献信息：</li>\t<li>文献名称：#ABSTRACTNAME#</li>\t<li>作者：#AUTHOR#</li><li>刊名：#JOURNALNAME#</li>" +
            "<li style=\"list-style:none;color:#1A1A1A;font-weight:bold;\">关于馆际互借请求的版权声明根据《中华人民共和国著作权法》,在法律规定的特定条件下，图书馆对已经出版的图书、期刊上的文" +
            "章或其他资料可以做少量复制。复制品只能用于个人学习、研究的目的。如果在此范围之外提出互借请求，或者后来使用该复制品用于商业目的，则属于侵权行为。" +
            "</li><li style=\"list-style:none;text-align:right;color:#1A1A1A;font-weight:bold;\">BK原文传递服务</li></ul></div></div>\n\t\t</div>\n";
    //解读点启停配置，此配置同时存在emailsystem项目/foreign项目/management项目，一处修改，其他俩出也要做修改
    public static final boolean BORROW_ENABLE_POINT = true;
    /////////////////////////////ella访问路径////////////////////////////////////////////////////
    //跨域访问url
    public static final String ELLA_URL = "http://127.0.0.1:8094";
    public static final String MENU_URL = ELLA_URL + "/feign/menu";
    //solr
}
