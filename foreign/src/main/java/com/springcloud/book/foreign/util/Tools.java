package com.springcloud.book.foreign.util;

import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Tools {

	/**
	 * 判断是否是中文
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		str.toCharArray();
		for (char c : str.toCharArray()){
			if (String.valueOf(c).matches("[\\u4e00-\\u9fa5]")){
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取n位数字字符串
	 * @param length
	 * @return
	 */
	public static String getRondomNumStr(int length){
		StringBuffer stringBuffer = new StringBuffer();
		Stream.generate(Math::random).limit(length).forEach(num ->{
			stringBuffer.append(new Double(num * 9 +1).intValue());
		});
		return stringBuffer.toString();
	}

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
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumer(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 过滤字符串中的汉字
	 * @param str
	 * @return
	 */
	public static String filterChin(String str){
		return str.replaceAll("[\u4e00-\u9fa5]+", "");
	}

	public static String getTextTrim(String text) {
		StringBuilder textContent = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(text);
		while (tokenizer.hasMoreTokens()) {
			String str = tokenizer.nextToken();
			textContent.append(str);
			if (tokenizer.hasMoreTokens()) {
				textContent.append(" "); // separator
			}
		}
		return textContent.toString();
	}

	public static void main(String[] args){
		String numStr = getRondomNumStr(6);
		System.out.println(numStr);
	}
}
