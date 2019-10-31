package com.springcloud.book.foreign.domain;

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
 * @since 2019-08-16
 */
@TableName("fd_abstract_full_text_links")
public class AbstractFullTextLinks extends Model<AbstractFullTextLinks> {

    private static final long serialVersionUID = 1L;

    /**
     * 摘要全文链接id
     */
    @TableId(value = "url_id", type = IdType.AUTO)
    private Long urlId;

    /**
     * 摘要全文链接类型
     */
    @TableField("url_type")
    private String urlType;

    /**
     * 摘要全文链接地址
     */
    @TableField("url_link")
    private String urlLink;


    public Long getUrlId() {
        return urlId;
    }

    public void setUrlId(Long urlId) {
        this.urlId = urlId;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    @Override
    protected Serializable pkVal() {
        return this.urlId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
