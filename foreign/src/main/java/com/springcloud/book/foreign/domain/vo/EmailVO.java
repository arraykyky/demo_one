package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailVO {
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

    public static void main(String[] args){
       byte[] buff = new byte[10];
       buff[0] = 0;
       buff[1] = 1;
       buff[2] = 2;
       buff[3] = 3;
       EmailVO emailVO = new EmailVO();
       emailVO.setPort("80");
       emailVO.setSystemEmail("867022565@qq.com");
       emailVO.setPassword("123456");
       emailVO.setRecipient("1006302873@qq.com");
       emailVO.setBytes(buff);
       emailVO.setAttachName("1236547.pdf");
       emailVO.setEmailTitle("邮件标题");
       emailVO.setEmailContent("邮件内容");
       //boolean value = emailVO.checkEmailVONecessaryFieldIsNotEmpty(emailVO, "addressesArray");
       //System.out.println(value);
       List<String> addressList = new ArrayList<>();
       addressList.add("867022565@qq.com");
       //Address[] addArray = new Address[addressList.size()];
       //emailVO.setAddressesArray(addressList);
        String[] array = {"bytes", "addressesArray"};
       boolean value1 = emailVO.checkEmailVONecessaryFieldIsNotEmpty(emailVO,array);
       System.out.println(value1);
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
