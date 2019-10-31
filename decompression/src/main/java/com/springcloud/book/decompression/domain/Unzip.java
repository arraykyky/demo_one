package com.springcloud.book.decompression.domain;

import com.alibaba.fastjson.JSON;
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
 * @since 2019-08-26
 */
@TableName("fd_unzip")
public class Unzip extends Model<Unzip> {

    private static final long serialVersionUID = 1L;

    /**
     * 解压id
     */
    @TableId(value = "unzip_id", type = IdType.AUTO)
    private Long unzipId;

    /**
     * 解压文件位置(毫秒)
     */
    @TableField("unzip_time_position")
    private Long unzipTimePosition = 0l;

    /**
     * 解压文件位置时间描述对应unzip_time_position时间
     */
    @TableField("unzip_date_time_position")
    private String unzipDateTimePosition;

    /**
     * 解压文件目录记录(对应期刊xml文件名相同的文件夹名)
     */
    @TableField("unzip_catalog_set")
    private String unzipCatalogSet;

    /**
     * unzipCatalogSet是否做记录 大于0记录，小于0不记录
     */
    @TableField("unzip_catalog_set_is_save")
    private int unzipCatalogSetIsSave = -1;

    /**
     * 解压记录环境(test测试/pro生产)
     */
    @TableField("unzip_environment")
    private String unzipEnvironment;


    public Long getUnzipId() {
        return unzipId;
    }

    public void setUnzipId(Long unzipId) {
        this.unzipId = unzipId;
    }

    public Long getUnzipTimePosition() {
        return unzipTimePosition;
    }

    public void setUnzipTimePosition(Long unzipTimePosition) {
        this.unzipTimePosition = unzipTimePosition;
    }

    public String getUnzipDateTimePosition() {
        return unzipDateTimePosition;
    }

    public void setUnzipDateTimePosition(String unzipDateTimePosition) {
        this.unzipDateTimePosition = unzipDateTimePosition;
    }

    public String getUnzipCatalogSet() {
        return unzipCatalogSet;
    }

    public void setUnzipCatalogSet(String unzipCatalogSet) {
        this.unzipCatalogSet = unzipCatalogSet;
    }

    public int getUnzipCatalogSetIsSave() {
        return unzipCatalogSetIsSave;
    }

    public void setUnzipCatalogSetIsSave(int unzipCatalogSetIsSave) {
        this.unzipCatalogSetIsSave = unzipCatalogSetIsSave;
    }

    public String getUnzipEnvironment() {
        return unzipEnvironment;
    }

    public void setUnzipEnvironment(String unzipEnvironment) {
        this.unzipEnvironment = unzipEnvironment;
    }

    @Override
    protected Serializable pkVal() {
        return this.unzipId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
