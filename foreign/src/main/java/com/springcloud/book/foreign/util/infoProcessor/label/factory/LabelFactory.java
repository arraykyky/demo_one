package com.springcloud.book.foreign.util.infoProcessor.label.factory;


import com.springcloud.book.foreign.domain.vo.LabelVO;

/**
 *
 * @author Ryan
 * Label工厂
 *
 */
public class LabelFactory {
	public static LabelVO getNoteExpressLabel(){
		LabelVO label=new LabelVO();
		label.setAbstracts("{AbstractSolr}:");
		label.setAuthor("{Author}:");
		label.setBibliographiesCount("");
		label.setDatabaseProvider("{Database Provider}:");
		label.setISSN("{ISBN/ISSN}:");
		label.setIssue("{Issue}:");
		label.setJournal("{Journal}:");
		label.setKeywords("{Keywords}:");
		label.setLanguage("");
		label.setLocalNumber("{Notes}:");
		label.setOrganization("{Author Address}:");
		label.setPageScope("{Pages}:");
		label.setPublisher("");
		label.setTitle("{Title}:");
		label.setType("{Reference Type}:");
		label.setVolume("");
		label.setYear("{Year}:");
		return label;
	}
	
	public static LabelVO getEndNoteLabel(){
		LabelVO label=new LabelVO();
		label.setAbstracts("%X");
		label.setAuthor("%A");
		label.setBibliographiesCount("");
		label.setDatabaseProvider("%W");
		label.setISSN("%@");
		label.setIssue("%N");
		label.setJournal("%J");
		label.setKeywords("%K");
		label.setLanguage("");
		label.setLocalNumber("%L");
		label.setOrganization("%+");
		label.setPageScope("%P");
		label.setPublisher("");
		label.setTitle("%T");
		label.setType("%0");
		label.setVolume("");
		label.setYear("%D");
		return label;
	}
	
	public static LabelVO getMyselfSetLabel(){
		LabelVO label=new LabelVO();
		label.setAbstracts("Summary-摘要:");
		label.setAuthor("Author-作者:");
		label.setBibliographiesCount("");
		label.setDatabaseProvider("Database Provider-数据库提供商:");
		label.setISSN("ISSN-号:");
		label.setIssue("Period-期:");
		label.setJournal("Source-文献来源:");
		label.setKeywords("Keyword-关键词:");
		label.setLanguage("Language-语言");
		label.setLocalNumber("note-国际编号:");
		label.setOrganization("Organ-单位:");
		label.setPageScope("PageCount-页码:");
		label.setPublisher("Publisher-出版商:");
		label.setTitle("Title-标题名:");
		label.setType("SrcDatabase-来源库:");
		label.setVolume("");
		label.setYear("Year-年 卷:");
		return label;
	}


}
