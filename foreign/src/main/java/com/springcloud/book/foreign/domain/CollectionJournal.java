package com.springcloud.book.foreign.domain;

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
@TableName("fd_collection_journal")
public class CollectionJournal extends Model<CollectionJournal> {

    private static final long serialVersionUID = 1L;

    /**
     * 收藏id
     */
    @TableId("collection_id")
    private String collectionId;

    /**
     * 收藏期刊id
     */
    @TableField("collection_journal_id")
    private String collectionJournalId;

    /**
     * 收藏期刊名称
     */
    @TableField("collection_journal_name")
    private String collectionJournalName;

    /**
     * 收藏人id(对应Ella的用户id)
     */
    @TableField("collection_user_id")
    private String collectionUserId;

    /**
     * 收藏人姓名
     */
    @TableField("collection_user_name")
    private String collectionUserName;

    /**
     * 收藏时间
     */
    @TableField("collection_time")
    private String collectionTime;


    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionJournalId() {
        return collectionJournalId;
    }

    public void setCollectionJournalId(String collectionJournalId) {
        this.collectionJournalId = collectionJournalId;
    }

    public String getCollectionJournalName() {
        return collectionJournalName;
    }

    public void setCollectionJournalName(String collectionJournalName) {
        this.collectionJournalName = collectionJournalName;
    }

    public String getCollectionUserId() {
        return collectionUserId;
    }

    public void setCollectionUserId(String collectionUserId) {
        this.collectionUserId = collectionUserId;
    }

    public String getCollectionUserName() {
        return collectionUserName;
    }

    public void setCollectionUserName(String collectionUserName) {
        this.collectionUserName = collectionUserName;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.collectionId;
    }

    @Override
    public String toString() {
        return "CollectionJournal{" +
        "collectionId=" + collectionId +
        ", collectionJournalId=" + collectionJournalId +
        ", collectionJournalName=" + collectionJournalName +
        ", collectionUserId=" + collectionUserId +
        ", collectionUserName=" + collectionUserName +
        ", collectionTime=" + collectionTime +
        "}";
    }
}
