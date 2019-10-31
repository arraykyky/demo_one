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
 * @since 2019-08-19
 */
@TableName("fd_document_database")
public class DocumentDatabase extends Model<DocumentDatabase> {

    private static final long serialVersionUID = 1L;

    /**
     * 文献库id
     */
    @TableId("document_database_id")
    private String documentDatabaseId;

    /**
     * 文献库真实名
     */
    @TableField("document_database_real_name")
    private String documentDatabaseRealName;

    /**
     * 文献库显示名
     */
    @TableField("document_database_show_name")
    private String documentDatabaseShowName;

    /**
     * 文献库用户id(对应Ella的用户id)
     */
    @TableField("document_database_user_id")
    private String documentDatabaseUserId;


    public String getDocumentDatabaseId() {
        return documentDatabaseId;
    }

    public void setDocumentDatabaseId(String documentDatabaseId) {
        this.documentDatabaseId = documentDatabaseId;
    }

    public String getDocumentDatabaseRealName() {
        return documentDatabaseRealName;
    }

    public void setDocumentDatabaseRealName(String documentDatabaseRealName) {
        this.documentDatabaseRealName = documentDatabaseRealName;
    }

    public String getDocumentDatabaseShowName() {
        return documentDatabaseShowName;
    }

    public void setDocumentDatabaseShowName(String documentDatabaseShowName) {
        this.documentDatabaseShowName = documentDatabaseShowName;
    }

    public String getDocumentDatabaseUserId() {
        return documentDatabaseUserId;
    }

    public void setDocumentDatabaseUserId(String documentDatabaseUserId) {
        this.documentDatabaseUserId = documentDatabaseUserId;
    }

    @Override
    protected Serializable pkVal() {
        return this.documentDatabaseId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
