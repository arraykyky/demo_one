package com.springcloud.book.management.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2019-10-18
 */
@TableName("fd_record_abstract_root_path")
public class RecordAbstractRootPath extends Model<RecordAbstractRootPath> {

    private static final long serialVersionUID = 1L;

    /**
     * 文献记录-主键id
     */
    @TableId(value = "ab_id", type = IdType.AUTO)
    private Long abId;

    /**
     * 对应pubmed目录存放的父目录名，如这里的2017
     */
    @TableField("ab_cata_log")
    private String abCataLog;

    /**
     * 文献记录-文献的父目录
     */
    @TableField("ab_parent_dir")
    private String abParentDir;

    /**
     * 文献名，做唯一标识
     */
    @TableField("ab_name")
    private String abName;

    /**
     * 摘要对应的卷
     */
    @TableField("ab_volume")
    private String abVolume;

    /**
     * 摘要对应的期
     */
    @TableField("ab_issue")
    private String abIssue;

    /**
     * 文献记录-文献全路径
     */
    @TableField("ab_full_path")
    private String abFullPath;

    /**
     * 文献记录-是否已存储(-1未存储；1已存储)
     */
    @TableField("ab_isSave")
    private Integer abIssave;


    public Long getAbId() {
        return abId;
    }

    public void setAbId(Long abId) {
        this.abId = abId;
    }

    public String getAbCataLog() {
        return abCataLog;
    }

    public void setAbCataLog(String abCataLog) {
        this.abCataLog = abCataLog;
    }

    public String getAbParentDir() {
        return abParentDir;
    }

    public void setAbParentDir(String abParentDir) {
        this.abParentDir = abParentDir;
    }

    public String getAbName() {
        return abName;
    }

    public void setAbName(String abName) {
        this.abName = abName;
    }

    public String getAbVolume() {
        return abVolume;
    }

    public void setAbVolume(String abVolume) {
        this.abVolume = abVolume;
    }

    public String getAbIssue() {
        return abIssue;
    }

    public void setAbIssue(String abIssue) {
        this.abIssue = abIssue;
    }

    public String getAbFullPath() {
        return abFullPath;
    }

    public void setAbFullPath(String abFullPath) {
        this.abFullPath = abFullPath;
    }

    public Integer getAbIssave() {
        return abIssave;
    }

    public void setAbIssave(Integer abIssave) {
        this.abIssave = abIssave;
    }

    @Override
    protected Serializable pkVal() {
        return this.abId;
    }

    @Override
    public String toString() {
        return "RecordAbstractRootPath{" +
        "abId=" + abId +
        ", abCataLog=" + abCataLog +
        ", abParentDir=" + abParentDir +
        ", abName=" + abName +
        ", abVolume=" + abVolume +
        ", abIssue=" + abIssue +
        ", abFullPath=" + abFullPath +
        ", abIssave=" + abIssave +
        "}";
    }
}
