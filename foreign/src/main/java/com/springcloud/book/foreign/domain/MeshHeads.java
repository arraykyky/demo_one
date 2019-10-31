package com.springcloud.book.foreign.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author grl
 * @since 2019-08-08
 */
@TableName("fd_mesh_heads")
public class MeshHeads extends Model<MeshHeads> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识
     */
    @TableId("id")
    private String id;

    /**
     * 父节点id
     */
    @TableField("menu_parent_id")
    private String menuParentId;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private String menuId;

    /**
     * xml文件中的mesh主键id，在此不唯一
     */
    @TableField("descriptor_UI")
    private String descriptorUi;

    /**
     * 菜单名
     */
    @TableField("descriptor_name")
    private String descriptorName;

    /**
     * 菜单中文名称
     */
    @TableField("descriptor_name_CH")
    private String descriptorNameCh;

    /**
     * 副主题词
     */
    @TableField("sub_mesh_heads")
    private String subMeshHeads;

    /**
     * 英文注释
     */
    @TableField("scope_note")
    private String scopeNote;

    /**
     * 中文注释
     */
    @TableField("scope_note_CH")
    private String scopeNoteCh;

    /**
     * 入口词
     */
    @TableField("terms")
    private String terms;


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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getDescriptorUi() {
        return descriptorUi;
    }

    public void setDescriptorUi(String descriptorUi) {
        this.descriptorUi = descriptorUi;
    }

    public String getDescriptorName() {
        return descriptorName;
    }

    public void setDescriptorName(String descriptorName) {
        this.descriptorName = descriptorName;
    }

    public String getDescriptorNameCh() {
        return descriptorNameCh;
    }

    public void setDescriptorNameCh(String descriptorNameCh) {
        this.descriptorNameCh = descriptorNameCh;
    }

    public String getSubMeshHeads() {
        return subMeshHeads;
    }

    public void setSubMeshHeads(String subMeshHeads) {
        this.subMeshHeads = subMeshHeads;
    }

    public String getScopeNote() {
        return scopeNote;
    }

    public void setScopeNote(String scopeNote) {
        this.scopeNote = scopeNote;
    }

    public String getScopeNoteCh() {
        return scopeNoteCh;
    }

    public void setScopeNoteCh(String scopeNoteCh) {
        this.scopeNoteCh = scopeNoteCh;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
