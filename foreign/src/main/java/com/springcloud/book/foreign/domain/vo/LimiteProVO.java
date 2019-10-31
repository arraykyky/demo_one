package com.springcloud.book.foreign.domain.vo;

import java.util.List;

public class LimiteProVO extends SearchVoBean{
    private List<String> searchValueList;

    public List<String> getSearchValueList() {
        return searchValueList;
    }

    public void setSearchValueList(List<String> searchValueList) {
        this.searchValueList = searchValueList;
    }
}
