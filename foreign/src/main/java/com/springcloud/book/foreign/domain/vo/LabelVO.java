package com.springcloud.book.foreign.domain.vo;

/**
 * 各大文献管理系统文献信息标签，定义这个类是为了屏蔽差异，统一处理各种格式的文献信息
 * @author Ryan
 *
 */
public class LabelVO extends DocInfoVO {
	private String Author;//作者

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}
}
