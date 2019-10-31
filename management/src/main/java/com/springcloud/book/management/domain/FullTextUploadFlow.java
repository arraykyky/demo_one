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
 * @since 2019-09-28
 */
@TableName("fd_full_text_upload_flow")
public class FullTextUploadFlow extends Model<FullTextUploadFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("upload_flow_id")
    private String uploadFlowId;

    /**
     * 摘要id，
     */
    @TableField("abstract_id")
    private String abstractId;

    /**
     * 管理员id(上传文件管理员的id)
     */
    @TableField("id")
    private String id;

    /**
     * 上传时间
     */
    @TableField("upload_time")
    private String uploadTime;

    /**
     * 上传路径
     */
    @TableField("upload_file_url")
    private String uploadFileUrl;

    /**
     * 下载/查看文件的路径
     */
    @TableField("file_path_url")
    private String filePathUrl;


    public String getUploadFlowId() {
        return uploadFlowId;
    }

    public void setUploadFlowId(String uploadFlowId) {
        this.uploadFlowId = uploadFlowId;
    }

    public String getAbstractId() {
        return abstractId;
    }

    public void setAbstractId(String abstractId) {
        this.abstractId = abstractId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadFileUrl() {
        return uploadFileUrl;
    }

    public void setUploadFileUrl(String uploadFileUrl) {
        this.uploadFileUrl = uploadFileUrl;
    }

    public String getFilePathUrl() {
        return filePathUrl;
    }

    public void setFilePathUrl(String filePathUrl) {
        this.filePathUrl = filePathUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.uploadFlowId;
    }

    @Override
    public String toString() {
        return "FullTextUploadFlow{" +
        "uploadFlowId=" + uploadFlowId +
        ", abstractId=" + abstractId +
        ", id=" + id +
        ", uploadTime=" + uploadTime +
        ", uploadFileUrl=" + uploadFileUrl +
        ", filePathUrl=" + filePathUrl +
        "}";
    }
}
