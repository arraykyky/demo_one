package com.springcloud.book.foreign.domain.solr_been;

import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;
import java.util.List;

/**
 * 摘要
 * @author dell
 *
 */
public class AbstractSolr {
	//表唯一标识字段(PMID +Nlm+DOI)
	@Field("abstract_id")
	private String abstractId;
	//对应杂志唯一标识字段
	@Field("journal_id")
	private String journalId;
	//杂志期刊名 -- 高亮偏移量
	@Field("journal_title_main")
	private String journalTitleMain;
	//pubmed唯一标识码
	@Field("PMID")
	private String pmid;
	//国际标准期刊编号
	@Field("ISSN")
	private String issn;
	//卷
	@Field("volume")
	private String volume;
	//期
	@Field("issue")
	private String issue;
	//日期
	@Field("medline_date")
	private Date medlineDate;
	//来源描述 -- 高亮偏移量
	@Field("medline_TA")
	private String medlineTA;
	//摘要标题 -- 高亮偏移量
	@Field("article_title")
	private String articleTitle;
	//页码
	@Field("medline_page")
	private String medlinePage;
	//数字对象唯一标识符(作用等同于PMID)
	@Field("elocation_id_DOI")
	private String elocationIdDOI;
	//摘要内容 -- 高亮偏移量
	@Field("abstract_text")
	private String abstractText;
	//作者 Authors 的toString json字符串 只存储
	@Field("abstract_authors")
	private List<String> abstractAuthors;
	//语言
	@Field("language")
	private String language;
	//文献出版类型--文献类别
	@Field("publication_types")
	private List<String> publicationTypes;
	//文献出版类型--文献类别
	@Field("publication_types_str")
	private String publicationTypesStr;
	//国籍
	@Field("country")
	private String country;
	//国立医学图书馆唯一id
	@Field("NlmUnique_ID")
	private String nlmUniqueID;
	//国际标准化组织标题缩写 -- 高亮偏移量
	@Field("ISO_abbreviation")
	private String isoAbbreviation;
	//主题词对应mesh中的ui，即mesh中的id 检索 不涉及高亮
	@Field("mesh_heads_UI")
	private List<String> meshHeadsUI;
	//英语主题词 聚合分组使用
	@Field("mesh_heads")
	private List<String> meshHeads;
	//英语主题词 高亮检索使用
	@Field("mesh_heads_str")
	private String meshHeadsStr;
	//限定词 Qualifier 对象的toString 只存储
	@Field("qualifier")
	private List<String> qualifier;
	//聚合分组统计字段
	@Field("qualifier_str")
	private List<String> qualifierStr;
	//检索字段
	@Field("qualifier_string")
	private String qualifierString;
	//关键词 聚合分组使用
	@Field("key_words")
	private List<String> keyWords;
	//关键词 高亮检索使用
	@Field("key_words_str")
	private String keyWordsStr;
	//有免费全文(此值需要管理员设置)
	@Field("had_free_full_text")
	private int hadFreeFullText;
	//有全文(根据originalURL是否存在，标记该字段1存在-1不存在)
	@Field("had_full_text")
	private int hadFullText;
	//原文路径
	@Field("original_URL")
	private String originalURL;
	//作者字符串 聚合应用
	@Field("authors_str")
	private List<String> authorsStr;
	//作者字符串 高亮检索使用
	@Field("authors_string")
	private String authorsString;
	//单位字符串 聚合应用
	@Field("affiliation_str")
	private List<String> affiliationStr;
	//单位字符串 高亮检索使用
	@Field("affiliation_string")
	private String affiliationString;
	//期刊搜录单位 聚合应用
	@Field("journal_indexes")
	private List<String> journalIndexes;
	//期刊搜录单位 高亮检索使用
	@Field("journal_indexes_str")
	private String journalIndexesStr;

	/******************** 第二版新增字段 *********************/

	//影响因子——对应期刊出版时的影响因子
	@Field("recent_IF")
	private Double recentIf;
	//英语主题词_中文 聚合
	@Field("mesh_heads_ch")
	private List<String> meshHeadsCH;
	//英语主题词_中文 高亮检索
	@Field("mesh_heads_ch_str")
	private String meshHeadsCHStr;
	//对应期刊的jcr分区 PartitionJcr 的toString 不分词，存储
	@Field("partition_jcr")
	private List<String>  partitionJcr;
	@Field("partition_jcr_str")
	private String  partitionJcrStr;
	//期刊分区学科
	@Field("journal_partition_classification")
	private List<String> journalPartitionClassification;
	//jcr中的排名
	@Field("jcr_ranking")
	private List<Integer> jcrRanking;
	//jcr中的分区
	@Field("jcr_partition")
	private List<String> jcrPartition;
	//对应期刊的中科院分区 PartitionZKY的zkyPartitionList不分词，存储
	@Field("partition_zky")
	private List<String>  partitionZKY;
	@Field("partition_zky_str")
	private String  partitionZKYStr;
	//中科院分区学科
	@Field("zky_partition_classification")
	private List<String> zkyPartitionClassification;
	//中科院分区
	@Field("zky_partition")
	private List<Integer> zkyPartition;
	//学科分类
	@Field("subject_classification")
	private List<String> subjectClassificationList;


	//toString方法
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}



	public String getAbstractId() {
		return abstractId;
	}

	public void setAbstractId(String abstractId) {
		this.abstractId = abstractId;
	}

	public String getJournalId() {
		return journalId;
	}

	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}

	public String getJournalTitleMain() {
		return journalTitleMain;
	}

	public void setJournalTitleMain(String journalTitleMain) {
		this.journalTitleMain = journalTitleMain;
	}

	public String getPmid() {
		return pmid;
	}

	public void setPmid(String pmid) {
		this.pmid = pmid;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Date getMedlineDate() {
		return medlineDate;
	}

	public void setMedlineDate(Date medlineDate) {
		this.medlineDate = medlineDate;
	}

	public String getMedlineTA() {
		return medlineTA;
	}

	public void setMedlineTA(String medlineTA) {
		this.medlineTA = medlineTA;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getMedlinePage() {
		return medlinePage;
	}

	public void setMedlinePage(String medlinePage) {
		this.medlinePage = medlinePage;
	}

	public String getElocationIdDOI() {
		return elocationIdDOI;
	}

	public void setElocationIdDOI(String elocationIdDOI) {
		this.elocationIdDOI = elocationIdDOI;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public List<String> getAbstractAuthors() {
		return abstractAuthors;
	}

	public void setAbstractAuthors(List<String> abstractAuthors) {
		this.abstractAuthors = abstractAuthors;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getPublicationTypes() {
		return publicationTypes;
	}

	public void setPublicationTypes(List<String> publicationTypes) {
		this.publicationTypes = publicationTypes;
	}

	public String getPublicationTypesStr() {
		return publicationTypesStr;
	}

	public void setPublicationTypesStr(String publicationTypesStr) {
		this.publicationTypesStr = publicationTypesStr;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNlmUniqueID() {
		return nlmUniqueID;
	}

	public void setNlmUniqueID(String nlmUniqueID) {
		this.nlmUniqueID = nlmUniqueID;
	}

	public String getIsoAbbreviation() {
		return isoAbbreviation;
	}

	public void setIsoAbbreviation(String isoAbbreviation) {
		this.isoAbbreviation = isoAbbreviation;
	}

	public List<String> getMeshHeadsUI() {
		return meshHeadsUI;
	}

	public void setMeshHeadsUI(List<String> meshHeadsUI) {
		this.meshHeadsUI = meshHeadsUI;
	}

	public List<String> getMeshHeads() {
		return meshHeads;
	}

	public void setMeshHeads(List<String> meshHeads) {
		this.meshHeads = meshHeads;
	}

	public List<String > getQualifier() {
		return qualifier;
	}

	public void setQualifier(List<String> qualifier) {
		this.qualifier = qualifier;
	}

	public List<String> getQualifierStr() {
		return qualifierStr;
	}

	public void setQualifierStr(List<String> qualifierStr) {
		this.qualifierStr = qualifierStr;
	}

	public String getQualifierString() {
		return qualifierString;
	}

	public void setQualifierString(String qualifierString) {
		this.qualifierString = qualifierString;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

	public int getHadFreeFullText() {
		return hadFreeFullText;
	}

	public void setHadFreeFullText(int hadFreeFullText) {
		this.hadFreeFullText = hadFreeFullText;
	}

	public int getHadFullText() {
		return hadFullText;
	}

	public void setHadFullText(int hadFullText) {
		this.hadFullText = hadFullText;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public List<String> getAuthorsStr() {
		return authorsStr;
	}

	public void setAuthorsStr(List<String> authorsStr) {
		this.authorsStr = authorsStr;
	}

	public List<String> getAffiliationStr() {
		return affiliationStr;
	}

	public void setAffiliationStr(List<String> affiliationStr) {
		this.affiliationStr = affiliationStr;
	}

	public List<String> getJournalIndexes() {
		return journalIndexes;
	}

	public void setJournalIndexes(List<String> journalIndexes) {
		this.journalIndexes = journalIndexes;
	}

	public Double getRecentIf() {
		return recentIf;
	}

	public void setRecentIf(Double recentIf) {
		this.recentIf = recentIf;
	}

	public List<String> getMeshHeadsCH() {
		return meshHeadsCH;
	}

	public void setMeshHeadsCH(List<String> meshHeadsCH) {
		this.meshHeadsCH = meshHeadsCH;
	}

	public List<String> getPartitionJcr() {
		return partitionJcr;
	}

	public void setPartitionJcr(List<String> partitionJcr) {
		this.partitionJcr = partitionJcr;
	}

	public List<String> getJournalPartitionClassification() {
		return journalPartitionClassification;
	}

	public void setJournalPartitionClassification(List<String> journalPartitionClassification) {
		this.journalPartitionClassification = journalPartitionClassification;
	}

	public List<Integer> getJcrRanking() {
		return jcrRanking;
	}

	public void setJcrRanking(List<Integer> jcrRanking) {
		this.jcrRanking = jcrRanking;
	}

	public List<String> getJcrPartition() {
		return jcrPartition;
	}

	public void setJcrPartition(List<String> jcrPartition) {
		this.jcrPartition = jcrPartition;
	}

	public List<String> getPartitionZKY() {
		return partitionZKY;
	}

	public void setPartitionZKY(List<String> partitionZKY) {
		this.partitionZKY = partitionZKY;
	}

	public List<String> getZkyPartitionClassification() {
		return zkyPartitionClassification;
	}

	public void setZkyPartitionClassification(List<String> zkyPartitionClassification) {
		this.zkyPartitionClassification = zkyPartitionClassification;
	}

	public List<Integer> getZkyPartition() {
		return zkyPartition;
	}

	public void setZkyPartition(List<Integer> zkyPartition) {
		this.zkyPartition = zkyPartition;
	}

	public List<String> getSubjectClassificationList() {
		return subjectClassificationList;
	}

	public void setSubjectClassificationList(List<String> subjectClassificationList) {
		this.subjectClassificationList = subjectClassificationList;
	}

	public String getMeshHeadsStr() {
		return meshHeadsStr;
	}

	public void setMeshHeadsStr(String meshHeadsStr) {
		this.meshHeadsStr = meshHeadsStr;
	}

	public String getKeyWordsStr() {
		return keyWordsStr;
	}

	public void setKeyWordsStr(String keyWordsStr) {
		this.keyWordsStr = keyWordsStr;
	}

	public String getAuthorsString() {
		return authorsString;
	}

	public void setAuthorsString(String authorsString) {
		this.authorsString = authorsString;
	}

	public String getAffiliationString() {
		return affiliationString;
	}

	public void setAffiliationString(String affiliationString) {
		this.affiliationString = affiliationString;
	}

	public String getJournalIndexesStr() {
		return journalIndexesStr;
	}

	public void setJournalIndexesStr(String journalIndexesStr) {
		this.journalIndexesStr = journalIndexesStr;
	}

	public String getMeshHeadsCHStr() {
		return meshHeadsCHStr;
	}

	public void setMeshHeadsCHStr(String meshHeadsCHStr) {
		this.meshHeadsCHStr = meshHeadsCHStr;
	}

	public String getPartitionJcrStr() {
		return partitionJcrStr;
	}

	public void setPartitionJcrStr(String partitionJcrStr) {
		this.partitionJcrStr = partitionJcrStr;
	}

	public String getPartitionZKYStr() {
		return partitionZKYStr;
	}

	public void setPartitionZKYStr(String partitionZKYStr) {
		this.partitionZKYStr = partitionZKYStr;
	}
}
