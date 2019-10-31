package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.domain.ArticleOrders;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class OrderApplyVO extends ArticleOrders {
    private String abstractId;
    private String orderApplyUserEmail;
    private String userId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean checkOrderApplyVOFieldIsNotEmpty(OrderApplyVO orderApplyVO,String... ignoreField){
        if (orderApplyVO == null){
            return false;
        }
        List<String> ignoreFieldList = Arrays.asList(ignoreField);
        try {
            for (Field field : orderApplyVO.getClass().getDeclaredFields()){
                if (!ignoreFieldList.contains(field.getName())){
                    field.setAccessible(true);
                    Object value = field.get(orderApplyVO);
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

    @Override
    public String getAbstractId() {
        return abstractId;
    }

    @Override
    public void setAbstractId(String abstractId) {
        this.abstractId = abstractId;
    }

    @Override
    public String getOrderApplyUserEmail() {
        return orderApplyUserEmail;
    }

    @Override
    public void setOrderApplyUserEmail(String orderApplyUserEmail) {
        this.orderApplyUserEmail = orderApplyUserEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
