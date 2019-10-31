package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EmailVO {
    //订单id
    private String orderId;
    //订单申请人id
    private String orderApplyUserId;
    //发送邮件邮箱端口号
    private String port;
    //发送邮件邮箱
    private String systemEmail;
    //发送邮件邮箱密码
    private String password;
    //接收邮件的邮箱
    private String recipient;
    //发送邮件的附件字符数组
    private byte[] bytes;
    //发送邮件的附件名
    private String attachName;
    //发送邮件的邮件标题
    private String emailTitle;
    //发送邮件的邮件内容
    private String emailContent;
    ////接收邮件的邮箱集合
    private List<String> addressesArray;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean checkEmailVONecessaryFieldIsNotEmpty(EmailVO emailVO,String... ignoreField){
        if (emailVO == null){
            return false;
        }
        List<String> ignoreFieldList = Arrays.asList(ignoreField);
        try {
            for (Field field : emailVO.getClass().getDeclaredFields()){
                if (!ignoreFieldList.contains(field.getName())){
                    field.setAccessible(true);
                    Object value = field.get(emailVO);
                    if (value == null || "".equals(value.toString())){
                        return false;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderApplyUserId() {
        return orderApplyUserId;
    }

    public void setOrderApplyUserId(String orderApplyUserId) {
        this.orderApplyUserId = orderApplyUserId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public List<String> getAddressesArray() {
        return addressesArray;
    }

    public void setAddressesArray(List<String> addressesArray) {
        this.addressesArray = addressesArray;
    }
}
