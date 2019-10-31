package com.springcloud.book.emailsystem.bean;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EmailVO {
    private String orderId;
    private String orderApplyUserId;
    private String port;
    private String systemEmail;
    private String password;
    private String recipient;
    private byte[] bytes;
    private String attachName;
    private String emailTitle;
    private String emailContent;
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
