package com.springcloud.book.decompression.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 自定义字符串格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date,String pattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * Date转LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime convertDateToLDT(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
	
	public static void main(String[] args) {
		String str = format(new Date(), "yyyy-MM-dd HH:ss:mm");
		System.out.println(str);
	}

}
