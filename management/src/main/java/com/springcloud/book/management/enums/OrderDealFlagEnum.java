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
public enum OrderDealFlagEnum {
	//dealFlag 未找到原文-2(订单重新恢复为申请状态)/拒绝-1/接受(处理中)1/馆际推送2/馆际撤回3
	D_FULLTEXTNOTFOUND(-2,"未找到原文"),
	D_REFUSE(-1,"拒绝"),
	D_ACCEPT(1,"接受"),
	D_CLOUDPUSH(2,"馆际推送"),
	D_CLOUDBACK(3,"馆际撤回"),
	;

	private Integer dealNum;
	private String dealCode;

	//订单描述
	private OrderDealFlagEnum(Integer dealNum, String dealCode) {
		this.dealNum=dealNum;
		this.dealCode=dealCode;
	}

	/**
	 * 通过请求Num获取描述Code
	 * @param dealNum
	 * @return
	 */
	public static String getCode(Integer dealNum){
		for (OrderDealFlagEnum oe : OrderDealFlagEnum.values()){
			if (oe.getDealNum().equals(dealNum)){
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