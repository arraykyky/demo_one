package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

/**
 * 普通检索VO
 * @param //searchWorld 检索字段
 * @param //searchValue 检索值
 * @param //checkOut 勾选true,未勾选false
 * @param //token 登录用户账号
 */
public class OrdinarySearchVO extends SearchVoBean{
    //勾选检索，勾选true--精确检索/未勾选false--模糊检索
    private boolean checkOut = false;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public boolean getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(boolean checkOut) {
        this.checkOut = checkOut;
    }
}
