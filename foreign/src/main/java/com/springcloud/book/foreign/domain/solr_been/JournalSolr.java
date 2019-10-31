package com.springcloud.book.foreign.domain.solr_been;

import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
import java.util.List;

/**
 * 期刊
 * @author grl
 *
 */
public class JournalSolr {
	//主键，期刊id
	@Field("journal_id")
	private String journalId;
	//杂志唯一编号
	@Field("nlm_id")
	private String nlmId;
	//创建日期
	@Field("date_created")
	private Date dateCreated;
	//修订日期
	@Field("date_revised")
	private Date dateRevised;
	//授权日期
	@Field("date_authorized")
	private Date dateAuthorized;
	//完成日期
	@Field("date_completed")
	private Date dateCompleted;
	//主要修订日期
	@Field("date_revised_major")
	private Date dateRevisedMajor;
	//主标题
	@Field("title_main")
	private String titleMain;
	//备用标题
	@Field("title_alternate")
	private List<String> titleAlternate;
	//国立医学图书馆标题缩写
	@Field("nlm_title_abbreviation")
	private String nlmTitleAbbreviation;
	//国际标准化组织标题缩写
	@Field("iso_abbreviation")
	private String isoAbbreviation;
	//作者
	@Field("journal_authors")
	private List<String> journalAuthors;
	//期刊类型
	@Field("resource_type")
	private String resourceType;
	//发布状态
	@Field("issuance")
	private String issuance;
	//内容类别  远程电子资源/文本
	@Field("resource_unit")
	private List<String> resourceUnit;
	//内容类型
	@Field("content_type")
	private String contentType;
	//媒体类型
	@Field("media_type")
	private String mediaType;
	//载体类型
	@Field("carrier_type")
	private String carrierType;
	//出版类型集
	@Field("publication_types")
	private List<String> publicationTypes;
	//注释信息
	@Field("note")
	private List<String> note;
	//主题范畴
	@Field("mesh_headings")
	private List<String> meshHeadings;
	//国籍
	@Field("country")
	private String country;
	//起始发布年份
	@Field("publication_first_year")
	private Date publicationFirstYear;
	//截止发布年份
	@Field("publication_end_year")
	private Date publicationEndYear;
	//连载出版日期
	@Field("dates_serial_publication")
	private String datesSerialPublication;
	//频率
	@Field("frequency")
	private List<String> frequency;
	//语言
	@Field("language")
	private List<String> language;
	//自身描述
	@Field("physical_description")
	private List<String> physicalDescription;
	//国会图书馆控制号
	@Field("lccn")
	private List<String> lccn;
	//国际标准期刊编号LSSN_P/LSSN_L/LSSN_U/LSSN_E  [ddwe222,222233rrr,224234,...]
	@Field("issn")
	private List<String> issn;
	//分类编号
	@Field("coden")
	private String coden;
	//最新的影响因子数
	@Field("recent_If")
	private double recentIF;
	//全部影响因子数
	@Field("all_Ifs")
	private String allIFs;//全部影响因子
	//电子版链接
	@Field("e_linck")
	private List<String> eLinck;
	//国外图书馆相关编号
	@Field("oclc")
	private String oclc;
	//国家医学图书馆在版著录资料编号
	@Field("dnlm")
	private String dnlm;
	//相关杂志
	@Field("title_related")
	private List<String> titleRelated;
	//杂志收录机构
	@Field("index_sources")
	private List<String> indexSources;
	//杂志被收录信息
	@Field("indexes")
	private List<String> indexes;
	//中文名称
	@Field("title_ch")
	private String titleCH;
	//出版商
	@Field("publisher")
	private List<String> publisher;
	//学科分类 检索聚合使用
	@Field("subject_classification")
	private List<String> subjectClassificationList;

	public String getJournalId() {
		return journalId;
	}

	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}

	public String getNlmId() {
		return nlmId;
	}

	public void setNlmId(String nlmId) {
		this.nlmId = nlmId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateRevised() {
		return dateRevised;
	}

	public void setDateRevised(Date dateRevised) {
		this.dateRevised = dateRevised;
	}

	public Date getDateAuthorized() {
		return dateAuthorized;
	}

	public void setDateAuthorized(Date dateAuthorized) {
		this.dateAuthorized = dateAuthorized;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public Date getDateRevisedMajor() {
		return dateRevisedMajor;
	}

	public void setDateRevisedMajor(Date dateRevisedMajor) {
		this.dateRevisedMajor = dateRevisedMajor;
	}

	public String getTitleMain() {
		return titleMain;
	}

	public void setTitleMain(String titleMain) {
		this.titleMain = titleMain;
	}

	public List<String> getTitleAlternate() {
		return titleAlternate;
	}

	public void setTitleAlternate(List<String> titleAlternate) {
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

	public List<String> getJournalAuthors() {
		return journalAuthors;
	}

	public void setJournalAuthors(List<String> journalAuthors) {
		this.journalAuthors = journalAuthors;
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

	public List<String> getResourceUnit() {
		return resourceUnit;
	}

	public void setResourceUnit(List<String> resourceUnit) {
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

	public List<String> getPublicationTypes() {
		return publicationTypes;
	}

	public void setPublicationTypes(List<String> publicationTypes) {
		this.publicationTypes = publicationTypes;
	}

	public List<String> getNote() {
		return note;
	}

	public void setNote(List<String> note) {
		this.note = note;
	}

	public List<String> getMeshHeadings() {
		return meshHeadings;
	}

	public void setMeshHeadings(List<String> meshHeadings) {
		this.meshHeadings = meshHeadings;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getPublisher() {
		return publisher;
	}

	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationFirstYear() {
		return publicationFirstYear;
	}

	public void setPublicationFirstYear(Date publicationFirstYear) {
		this.publicationFirstYear = publicationFirstYear;
	}

	public Date getPublicationEndYear() {
		return publicationEndYear;
	}

	public void setPublicationEndYear(Date publicationEndYear) {
		this.publicationEndYear = publicationEndYear;
	}

	public String getDatesSerialPublication() {
		return datesSerialPublication;
	}

	public void setDatesSerialPublication(String datesSerialPublication) {
		this.datesSerialPublication = datesSerialPublication;
	}

	public List<String> getFrequency() {
		return frequency;
	}

	public void setFrequency(List<String> frequency) {
		this.frequency = frequency;
	}

	public List<String> getLanguage() {
		return language;
	}

	public void setLanguage(List<String> language) {
		this.language = language;
	}

	public List<String> getPhysicalDescription() {
		return physicalDescription;
	}

	public void setPhysicalDescription(List<String> physicalDescription) {
		this.physicalDescription = physicalDescription;
	}

	public List<String> getLccn() {
		return lccn;
	}

	public void setLccn(List<String> lccn) {
		this.lccn = lccn;
	}

	public List<String> getIssn() {
		return issn;
	}

	public void setIssn(List<String> issn) {
		this.issn = issn;
	}

	public String getCoden() {
		return coden;
	}

	public void setCoden(String coden) {
		this.coden = coden;
	}

	public double getRecentIF() {
		return recentIF;
	}

	public void setRecentIF(double recentIF) {
		this.recentIF = recentIF;
	}

	public String getAllIFs() {
		return allIFs;
	}

	public void setAllIFs(String allIFs) {
		this.allIFs = allIFs;
	}

	public List<String> geteLinck() {
		return eLinck;
	}

	public void seteLinck(List<String> eLinck) {
		this.eLinck = eLinck;
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

	public List<String> getTitleRelated() {
		return titleRelated;
	}

	public void setTitleRelated(List<String> titleRelated) {
		this.titleRelated = titleRelated;
	}

	public List<String> getIndexSources() {
		return indexSources;
	}

	public void setIndexSources(List<String> indexSources) {
		this.indexSources = indexSources;
	}

	public List<String> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<String> indexes) {
		this.indexes = indexes;
	}

	public String getTitleCH() {
		return titleCH;
	}

	public void setTitleCH(String titleCH) {
		this.titleCH = titleCH;
	}

	public List<String> getSubjectClassificationList() {
		return subjectClassificationList;
	}

	public void setSubjectClassificationList(List<String> subjectClassificationList) {
		this.subjectClassificationList = subjectClassificationList;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
