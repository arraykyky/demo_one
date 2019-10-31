package com.springcloud.book.foreign.domain.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 *
 * @author Ryan
 * 文献基本信息
 *
 */
public class DocInfoVO {
	 private String Type;//文献类型
	 private String BibliographiesCount;//参考书目数量
	 private String Title;//标题
	 private String Language;//语言
	 private List<String> Authors;//作者
	 private String Organization;//作者单位
	 private String Journal;//文献来源/刊名
	 private String Year;//出版年份
	 private String Publisher;//出版单位
	 private String Volume;//卷号
	 private String Issue;//期号
	 private String PageScope;//页码范围
	 private String Keywords;//关键词
	 private String Abstracts;//摘要
	 private String ISSN;//国际刊号
	 private String LocalNumber;//国内刊号
	 private String DatabaseProvider;//数据库提供商

	 public String getType() {
		 return Type;
	 }
	 public void setType(String type) {
		 Type = type;
	 }
	 public String getBibliographiesCount() {
		 return BibliographiesCount;
	 }
	 public void setBibliographiesCount(String bibliographiesCount) {
		 BibliographiesCount = bibliographiesCount;
	 }
	 public String getTitle() {
		 return Title;
	 }
	 public void setTitle(String title) {
		 Title = title;
	 }
	 public String getLanguage() {
		 return Language;
	 }
	 public void setLanguage(String language) {
		 Language = language;
	 }
	 public List<String> getAuthors() {
		 return Authors;
	 }
	 public void setAuthors(List<String> author) {
		 Authors = author;
	 }
	 public String getOrganization() {
		 return Organization;
	 }
	 public void setOrganization(String organization) {
		 Organization = organization;
	 }
	 public String getJournal() {
		 return Journal;
	 }
	 public void setJournal(String journal) {
		 Journal = journal;
	 }
	 public String getYear() {
		 return Year;
	 }
	 public void setYear(String year) {
		 Year = year;
	 }
	 public String getPublisher() {
		 return Publisher;
	 }
	 public void setPublisher(String publisher) {
		 Publisher = publisher;
	 }
	 public String getVolume() {
		 return Volume;
	 }
	 public void setVolume(String volume) {
		 Volume = volume;
	 }
	 public String getIssue() {
		 return Issue;
	 }
	 public void setIssue(String issue) {
		 Issue = issue;
	 }
	 public String getPageScope() {
		 return PageScope;
	 }
	 public void setPageScope(String pageScope) {
		 PageScope = pageScope;
	 }
	 public String getKeywords() {
		 return Keywords;
	 }
	 public void setKeywords(String keywords) {
		 Keywords = keywords;
	 }
	 public String getAbstracts() {
		 return Abstracts;
	 }
	 public void setAbstracts(String abstracts) {
		 Abstracts = abstracts;
	 }
	 public String getISSN() {
		 return ISSN;
	 }
	 public void setISSN(String iSSN) {
		 ISSN = iSSN;
	 }
	 public String getLocalNumber() {
		 return LocalNumber;
	 }
	 public void setLocalNumber(String localNumber) {
		 LocalNumber = localNumber;
	 }
	 public String getDatabaseProvider() {
		 return DatabaseProvider;
	 }
	 public void setDatabaseProvider(String databaseProvider) {
		 DatabaseProvider = databaseProvider;
	 }
	 @Override
	 public String toString() {
		 return JSON.toJSONString(this);
	 }
}
