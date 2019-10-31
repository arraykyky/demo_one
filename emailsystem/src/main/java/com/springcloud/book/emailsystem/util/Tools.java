package com.springcloud.book.emailsystem.util;

import java.util.UUID;

public class Tools {

	/**
	 * 主键生成方法
	 *
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		return str.replaceAll("-", "");
	}

	/**
	 * 上传文件重命名
	 * @param pref		真实名字
	 * @param oldName	原名字
	 * @return
	 */
	public static String uploadFileRename(String pref,String oldName){
		String suff = oldName.substring(oldName.lastIndexOf("."));
		String newName = pref + suff;
		return newName;
	}

}
