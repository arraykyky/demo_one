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
package com.springcloud.book.foreign.enums;

/**
 * 〈〉<br>
 * @author dell
 * @create 2018/7/2
 * @since 1.0.0
 */
public enum OrderDealTypeEnum {
	//订单处理方式
	O_LOCAL("1100","本地处理"),
	O_COULD("1111","馆记互借"),
	;

	private String dealNum;
	private String dealCode;

	//订单描述
	private OrderDealTypeEnum(String  dealNum, String dealCode) {
		this.dealCode=dealCode;
		this.dealNum=dealNum;
	}

	/**
	 * 通过请求key获取描述key
	 * @param dealNum
	 * @return
	 */
	public static String getDealNumByDealsFlag(String dealNum){
		for (OrderDealTypeEnum oe : OrderDealTypeEnum.values()){
			if (oe.getDealNum().equals(dealNum)){
				return oe.getDealCode();
			}
		}
		return null;
	}

	public String getDealNum() { return dealNum; }

	public String getDealCode() { return dealCode; }

	public void setDealNum(String dealNum) { this.dealNum = dealNum; }

	public void setDealCode(String dealCode) { this.dealCode = dealCode; }
}