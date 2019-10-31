/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: OrderTypeEnum
 * Author:   dell
 * Date:     2018/7/2 18:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.springcloud.book.management.enums;

/**
 * 〈〉<br>
 * @author dell
 * @create 2018/7/2
 * @since 1.0.0
 */
public enum OrderHandleTypeEnum {
	//订单处理方式(客服手动发送邮件1//系统定时发送的-1)
	O_LOCAL(1,"客服处理"),
	O_SYSTEM(-1,"系统处理"),
	;

	private Integer dealNum;
	private String dealCode;

	//订单描述
	private OrderHandleTypeEnum(Integer dealNum, String dealCode) {
		this.dealNum=dealNum;
		this.dealCode=dealCode;
	}

	/**
	 * 通过请求Num获取描述Code
	 * @param dealNum
	 * @return
	 */
	public static String getCode(Integer dealNum){
		for (OrderHandleTypeEnum oe : OrderHandleTypeEnum.values()){
			if (oe.getDealNum() == dealNum){
				return oe.getDealCode();
			}
		}
		return null;
	}

	public Integer getDealNum() {
		return dealNum;
	}

	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}

	public String getDealCode() {
		return dealCode;
	}

	public void setDealCode(String dealCode) {
		this.dealCode = dealCode;
	}
}