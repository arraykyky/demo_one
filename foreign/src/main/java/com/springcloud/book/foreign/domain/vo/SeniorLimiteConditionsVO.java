package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.domain.SeniorLimiteConditions;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class SeniorLimiteConditionsVO extends SeniorLimiteConditions {
    private String token;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static void main(String[] args){
       SeniorLimiteConditionsVO conditionsVO = new SeniorLimiteConditionsVO();
       conditionsVO.setLimiteChWorld("中文");
       conditionsVO.setLimiteEnWorld("chi");
       conditionsVO.setLimiteFieldNum("17");
       conditionsVO.setLimiteGroupName("语种");
       conditionsVO.setLimiteGroupNum("0003");
        boolean value = checkSeniorLimiteConditionsNecessaryFieldIsNotEmpty(conditionsVO, "limiteId");
        System.out.println(value);
    }

    public static boolean checkSeniorLimiteConditionsNecessaryFieldIsNotEmpty(SeniorLimiteConditionsVO conditions,String ignoreField){
        if (conditions == null){
            return false;
        }
        try {
            for (Field field : conditions.getClass().getSuperclass().getDeclaredFields()){
                if (!field.getName().equals(ignoreField)){
                    field.setAccessible(true);
                    Object value = field.get(conditions);
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
