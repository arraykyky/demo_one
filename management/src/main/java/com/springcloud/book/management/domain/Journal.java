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
 * @since 2019-10-21
 */
@TableName("fd_journal")
public class Journal extends Model<Journal> {

    private static final long serialVersionUID = 1L;

    /**
     * 期刊id
     */
    @TableId("journal_id")
    private String journalId;

    /**
     * nlm目录信息
     */
    @TableField("NLM_catalog_record")
    private String nlmCatalogRecord;

    /**
     * nlm期刊唯一编号
     */
    @TableField("NLM_ID")
    private String nlmId;

    /**
     * 创建日期
     */
    @TableField("date_created")
    private String dateCreated;

    /**
     * 修订日期
     */
    @TableField("date_revised")
    private String dateRevised;

    /**
     * 授权日期
     */
    @TableField("date_authorized")
    private String dateAuthorized;

    /**
     * 完成日期
     */
    @TableField("date_completed")
    private String dateCompleted;

    /**
     * 主要修订日期
     */
    @TableField("date_revised_major")
    private String dateRevisedMajor;

    /**
     * 期刊标题
     */
    @TableField("title_main")
    private String titleMain;

    /**
     * 期刊备用标题
     */
    @TableField("title_alternate")
    private String titleAlternate;

    /**
     * 国立医学图书馆标题缩写
     */
    @TableField("NLM_title_abbreviation")
    private String nlmTitleAbbreviation;

    /**
     * 国际标准化组织标题缩写
     */
    @TableField("ISO_abbreviation")
    private String isoAbbreviation;

    /**
     * 作者
     */
    @TableField("authors")
    private String authors;

    /**
     * 期刊类型
     */
    @TableField("resource_type")
    private String resourceType;

    /**
     * 发布状态
     */
    @TableField("issuance")
    private String issuance;

    /**
     * 内容类别：远程电子资源/文本
     */
    @TableField("resource_unit")
    private String resourceUnit;

    /**
     * 内容类型
     */
    @TableField("content_type")
    private String contentType;

    /**
     * 媒体类型
     */
    @TableField("media_type")
    private String mediaType;

    /**
     * 载体类型
     */
    @TableField("carrier_type")
    private String carrierType;

    /**
     * 出版类型集
     */
    @TableField("publication_types")
    private String publicationTypes;

    /**
     * 注释信息
     */
    @TableField("note")
    private String note;

    /**
     * 主题范畴
     */
    @TableField("mesh_headings")
    private String meshHeadings;

    /**
     * 国籍
     */
    @TableField("country")
    private String country;

    /**
     * 出版商
     */
    @TableField("publisher")
    private String publisher;

    /**
     * 起始发布年份
     */
    @TableField("publication_first_year")
    private String publicationFirstYear;

    /**
     * 截至发布年份
     */
    @TableField("publication_end_year")
    private String publicationEndYear;

    /**
     * 连载出版日期
     */
    @TableField("dates_serial_publication")
    private String datesSerialPublication;

    /**
     * 频率
     */
    @TableField("frequency")
    private String frequency;

    /**
     * 语言
     */
    @TableField("language")
    private String language;

    /**
     * 自身描述
     */
    @TableField("physical_description")
    private String physicalDescription;

    /**
     * 国会图书馆控制号
     */
    @TableField("LCCN")
    private String lccn;

    /**
     * 电子版的issn
     */
    @TableField("ISSN_E")
    private String issnE;

    /**
     * 印刷版的issn
     */
    @TableField("ISSN_P")
    private String issnP;

    /**
     * 连接版的issn
     */
    @TableField("ISSN_L")
    private String issnL;

    /**
     * 未知类型的issn
     */
    @TableField("ISSN_U")
    private String issnU;

    /**
     * 分类编号
     */
    @TableField("coden")
    private String coden;

    /**
     * 最新的影响因子
     */
    @TableField("recent_IF")
    private String recentIf;

    /**
     * 全部影响因子
     */
    @TableField("all_IFs")
    private String allIfs;

    /**
     * 电子版链接
     */
    @TableField("e_linck")
    private String eLinck;

    /**
     * 学科分类
     */
    @TableField("classification")
    private String classification;

    /**
     * 国外图书馆相关编号
     */
    @TableField("OCLC")
    private String oclc;

    /**
     * 国外图书馆在版著录资料编号
     */
    @TableField("DNLM")
    private String dnlm;

    /**
     * 相关期刊
     */
    @TableField("title_related")
    private String titleRelated;

    /**
     * 期刊官网地址
     */
    @TableField("journal_official_URL")
    private String journalOfficialUrl;

    /**
     * PubMed收录信息
     */
    @TableField("index_sources")
    private String indexSources;

    /**
     * 杂志被收录情况（OA,SCI,......）
     */
    @TableField("indexes")
    private String indexes;

    /**
     * 中文名称
     */
    @TableField("title_ch")
    private String titleCh;


    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public String getNlmCatalogRecord() {
        return nlmCatalogRecord;
    }

    public void setNlmCatalogRecord(String nlmCatalogRecord) {
        this.nlmCatalogRecord = nlmCatalogRecord;
    }

    public String getNlmId() {
        return nlmId;
    }

    public void setNlmId(String nlmId) {
        this.nlmId = nlmId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateRevised() {
        return dateRevised;
    }

    public void setDateRevised(String dateRevised) {
        this.dateRevised = dateRevised;
    }

    public String getDateAuthorized() {
        return dateAuthorized;
    }

    public void setDateAuthorized(String dateAuthorized) {
        this.dateAuthorized = dateAuthorized;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getDateRevisedMajor() {
        return dateRevisedMajor;
    }

    public void setDateRevisedMajor(String dateRevisedMajor) {
        this.dateRevisedMajor = dateRevisedMajor;
    }

    public String getTitleMain() {
        return titleMain;
    }

    public void setTitleMain(String titleMain) {
        this.titleMain = titleMain;
    }

    public String getTitleAlternate() {
        return titleAlternate;
    }

    public void setTitleAlternate(String titleAlternate) {
        this.titleAlternate = titleAlternate;
    }

    public String getNlmTitleAbbreviation() {
        return nlmTitleAbbreviation;
    }

    public void setNlmTitleAbbreviation(String nlmTitleAbbreviation) {
        this.nlmTitleAbbreviation = nlmTitleAbbreviation;
    }

    public String getIsoAbbreviation() {
        return isoAbbreviation;
    }

    public void setIsoAbbreviation(String isoAbbreviation) {
        this.isoAbbreviation = isoAbbreviation;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getIssuance() {
        return issuance;
    }

    public void setIssuance(String issuance) {
        this.issuance = issuance;
    }

    public String getResourceUnit() {
        return resourceUnit;
    }

    public void setResourceUnit(String resourceUnit) {
        this.resourceUnit = resourceUnit;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getPublicationTypes() {
        return publicationTypes;
    }

    public void setPublicationTypes(String publicationTypes) {
        this.publicationTypes = publicationTypes;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMeshHeadings() {
        return meshHeadings;
    }

    public void setMeshHeadings(String meshHeadings) {
        this.meshHeadings = meshHeadings;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicationFirstYear() {
        return publicationFirstYear;
    }

    public void setPublicationFirstYear(String publicationFirstYear) {
        this.publicationFirstYear = publicationFirstYear;
    }

    public String getPublicationEndYear() {
        return publicationEndYear;
    }

    public void setPublicationEndYear(String publicationEndYear) {
        this.publicationEndYear = publicationEndYear;
    }

    public String getDatesSerialPublication() {
        return datesSerialPublication;
    }

    public void setDatesSerialPublication(String datesSerialPublication) {
        this.datesSerialPublication = datesSerialPublication;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhysicalDescription() {
        return physicalDescription;
    }

    public void setPhysicalDescription(String physicalDescription) {
        this.physicalDescription = physicalDescription;
    }

    public String getLccn() {
        return lccn;
    }

    public void setLccn(String lccn) {
        this.lccn = lccn;
    }

    public String getIssnE() {
        return issnE;
    }

    public void setIssnE(String issnE) {
        this.issnE = issnE;
    }

    public String getIssnP() {
        return issnP;
    }

    public void setIssnP(String issnP) {
        this.issnP = issnP;
    }

    public String getIssnL() {
        return issnL;
    }

    public void setIssnL(String issnL) {
        this.issnL = issnL;
    }

    public String getIssnU() {
        return issnU;
    }

    public void setIssnU(String issnU) {
        this.issnU = issnU;
    }

    public String getCoden() {
        return coden;
    }

    public void setCoden(String coden) {
        this.coden = coden;
    }

    public String getRecentIf() {
        return recentIf;
    }

    public void setRecentIf(String recentIf) {
        this.recentIf = recentIf;
    }

    public String getAllIfs() {
        return allIfs;
    }

    public void setAllIfs(String allIfs) {
        this.allIfs = allIfs;
    }

    public String geteLinck() {
        return eLinck;
    }

    public void seteLinck(String eLinck) {
        this.eLinck = eLinck;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getOclc() {
        return oclc;
    }

    public void setOclc(String oclc) {
        this.oclc = oclc;
    }

    public String getDnlm() {
        return dnlm;
    }

    public void setDnlm(String dnlm) {
        this.dnlm = dnlm;
    }

    public String getTitleRelated() {
        return titleRelated;
    }

    public void setTitleRelated(String titleRelated) {
        this.titleRelated = titleRelated;
    }

    public String getJournalOfficialUrl() {
        return journalOfficialUrl;
    }

    public void setJournalOfficialUrl(String journalOfficialUrl) {
        this.journalOfficialUrl = journalOfficialUrl;
    }

    public String getIndexSources() {
        return indexSources;
    }

    public void setIndexSources(String indexSources) {
        this.indexSources = indexSources;
    }

    public String getIndexes() {
        return indexes;
    }

    public void setIndexes(String indexes) {
        this.indexes = indexes;
    }

    public String getTitleCh() {
        return titleCh;
    }

    public void setTitleCh(String titleCh) {
        this.titleCh = titleCh;
    }

    @Override
    protected Serializable pkVal() {
        return this.journalId;
    }

    @Override
    public String toString() {
        return "Journal{" +
        "journalId=" + journalId +
        ", nlmCatalogRecord=" + nlmCatalogRecord +
        ", nlmId=" + nlmId +
        ", dateCreated=" + dateCreated +
        ", dateRevised=" + dateRevised +
        ", dateAuthorized=" + dateAuthorized +
        ", dateCompleted=" + dateCompleted +
        ", dateRevisedMajor=" + dateRevisedMajor +
        ", titleMain=" + titleMain +
        ", titleAlternate=" + titleAlternate +
        ", nlmTitleAbbreviation=" + nlmTitleAbbreviation +
        ", isoAbbreviation=" + isoAbbreviation +
        ", authors=" + authors +
        ", resourceType=" + resourceType +
        ", issuance=" + issuance +
        ", resourceUnit=" + resourceUnit +
        ", contentType=" + contentType +
        ", mediaType=" + mediaType +
        ", carrierType=" + carrierType +
        ", publicationTypes=" + publicationTypes +
        ", note=" + note +
        ", meshHeadings=" + meshHeadings +
        ", country=" + country +
        ", publisher=" + publisher +
        ", publicationFirstYear=" + publicationFirstYear +
        ", publicationEndYear=" + publicationEndYear +
        ", datesSerialPublication=" + datesSerialPublication +
        ", frequency=" + frequency +
        ", language=" + language +
        ", physicalDescription=" + physicalDescription +
        ", lccn=" + lccn +
        ", issnE=" + issnE +
        ", issnP=" + issnP +
        ", issnL=" + issnL +
        ", issnU=" + issnU +
        ", coden=" + coden +
        ", recentIf=" + recentIf +
        ", allIfs=" + allIfs +
        ", eLinck=" + eLinck +
        ", classification=" + classification +
        ", oclc=" + oclc +
        ", dnlm=" + dnlm +
        ", titleRelated=" + titleRelated +
        ", journalOfficialUrl=" + journalOfficialUrl +
        ", indexSources=" + indexSources +
        ", indexes=" + indexes +
        ", titleCh=" + titleCh +
        "}";
    }
}
