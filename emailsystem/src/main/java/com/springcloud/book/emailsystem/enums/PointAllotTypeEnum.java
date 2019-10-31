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
package com.springcloud.book.emailsystem.enums;

/**
 * 〈〉<br>
 * @author dell
 * @create 2018/7/2
 * @since 1.0.0
 */
public enum PointAllotTypeEnum {
	//消费类型（1点数分配；2金币充值；-1点数消费）
	P_ALLOT(1,"分配"),
	P_RECHARGE(2,"充值"),
	P_CONSUME(-1,"消费"),
	;

	private Integer dealNum;
	private String dealCode;

	//订单描述
	private PointAllotTypeEnum(Integer dealNum, String dealCode) {
		this.dealNum=dealNum;
		this.dealCode=dealCode;
	}

	/**
	 * 通过请求Num获取描述Code
	 * @param dealNum
	 * @return
	 */
	public static String getCode(Integer dealNum){
		for (PointAllotTypeEnum oe : PointAllotTypeEnum.values()){
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