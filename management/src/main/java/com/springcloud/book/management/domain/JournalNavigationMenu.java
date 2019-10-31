package com.springcloud.book.management.domain;

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
 * @since 2019-10-12
 */
@TableName("fd_journal_navigation_menu")
public class JournalNavigationMenu extends Model<JournalNavigationMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，唯一标识
     */
    @TableId("menu_id")
    private String menuId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 父节点
     */
    @TableField("menu_parent_id")
    private String menuParentId;

    /**
     * 描述信息
     */
    @TableField("menu_desc")
    private String menuDesc;

    /**
     * 菜单值
     */
    @TableField("menu_value")
    private String menuValue;

    /**
     * 菜单排序号
     */
    @TableField("menu_order")
    private Integer menuOrder;


    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuValue() {
        return menuValue;
    }

    public void setMenuValue(String menuValue) {
        this.menuValue = menuValue;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

    @Override
    public String toString() {
        return "JournalNavigationMenu{" +
        "menuId=" + menuId +
        ", menuName=" + menuName +
        ", menuParentId=" + menuParentId +
        ", menuDesc=" + menuDesc +
        ", menuValue=" + menuValue +
        ", menuOrder=" + menuOrder +
        "}";
    }
}
