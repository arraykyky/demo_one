package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class SeniorVo {
    //出版开始日期
    private String startDate;
    //出版结束日期
    private String endDate;
    //检索数据
    private List<SearchVoBean> sbList;
    //筛选限制
    private List<String> selectList;
    //限定对象
    private List<LimiteProVO> limitePro;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<SearchVoBean> getSbList() {
        return sbList;
    }

    public void setSbList(List<SearchVoBean> sbList) {
        this.sbList = sbList;
    }

    public List<String> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }

    public List<LimiteProVO> getLimitePro() {
        return limitePro;
    }

    public void setLimitePro(List<LimiteProVO> limitePro) {
        this.limitePro = limitePro;
    }
}
