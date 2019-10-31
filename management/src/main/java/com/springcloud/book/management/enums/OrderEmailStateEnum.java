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
public enum OrderEmailStateEnum {
	//邮件状态(0001发送失败;0000未发送，默认值;1111发送成功)
	E_NOTSEND("0000","未发送"),
	E_SUCCESS("1111","发送成功"),
	E_ERROR("0001","发送失败"),
	;

	private String dealNum;
	private String dealCode;

	//订单描述
	private OrderEmailStateEnum(String  dealNum, String dealCode) {
		this.dealCode=dealCode;
		this.dealNum=dealNum;
	}

	/**
	 * 通过请求key获取描述key
	 * @param dealNum
	 * @return
	 */
	public static String getDealNumByDealsFlag(String dealNum){
		for (OrderEmailStateEnum oe : OrderEmailStateEnum.values()){
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