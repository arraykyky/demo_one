package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

public class MeshHeadsVO implements Serializable {
    //菜单id
    private String id;
    //菜单父节点id
    private String menuParentId = "0";
    //起始年份
    private String start;
    //截至年份
    private String end;
    //副主题词集合
    private List<String> qualifier;
    //主题词(菜单英文名称)
    private String descriptorName;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<String> getQualifier() {
        return qualifier;
    }

    public void setQualifier(List<String> qualifier) {
        this.qualifier = qualifier;
    }

    public String getDescriptorName() {
        return descriptorName;
    }

    public void setDescriptorName(String descriptorName) {
        this.descriptorName = descriptorName;
    }
}
