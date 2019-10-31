package com.springcloud.book.management.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.management.domain.ArticleOrders;

public class EmailSendVO extends ArticleOrders {
    private String token;
    private String orderId;
    private String orderAcceptAdminId;
    private String orderApplyUserEmail;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

//    public boolean checkEmailSendVONecessaryFieldIsNotEmpty(EmailSendVO emailSendVO,String... ignoreField){
//        if (emailSendVO == null){
//            return false;
//        }
//        List<String> ignoreFieldList = new ArrayList<>();
//        if (ignoreField!=null && ignoreField.length > 0){
//            ignoreFieldList.addAll(Arrays.asList(ignoreField));
//        }
//        try {
//            for (Field field : emailSendVO.getClass().getDeclaredFields()){
//                //跳过忽略验证字段
//                if (!ignoreFieldList.contains(field.getName())){
//                    field.setAccessible(true);
//                    Object value = field.get(emailSendVO);
//                    if (value == null || "".equals(value.toString())){
//                        return false;
//                    }
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getOrderId() {
        return orderId;
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String getOrderAcceptAdminId() {
        return orderAcceptAdminId;
    }

    @Override
    public void setOrderAcceptAdminId(String orderAcceptAdminId) {
        this.orderAcceptAdminId = orderAcceptAdminId;
    }

    @Override
    public String getOrderApplyUserEmail() {
        return orderApplyUserEmail;
    }

    @Override
    public void setOrderApplyUserEmail(String orderApplyUserEmail) {
        this.orderApplyUserEmail = orderApplyUserEmail;
    }
}
