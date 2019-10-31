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
public enum OrderTypeEnum {
	//订单及订单流程状态
	O_REFUSE("0010","未找到全文,订单结束"),
	O_APPLYING("0000","申请中"),
	O_DEALING("0001","处理中"),
	O_NOTEXT("00000","未找到全文"),
	O_FINISH("0011","已完成"),
	F_COULD("1111","馆际推送"),
	F_RECALL("1100","馆际撤回"),
	;

	private String dealNum;
	private String dealCode;

	//订单描述
	private OrderTypeEnum(String  dealNum, String dealCode) {
		this.dealCode=dealCode;
		this.dealNum=dealNum;
	}

	/**
	 * 通过请求key获取描述key
	 * @param dealNum
	 * @return
	 */
	public static String getDealNumByDealsFlag(String dealNum){
		for (OrderTypeEnum oe : OrderTypeEnum.values()){
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