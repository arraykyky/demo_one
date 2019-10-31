package com.springcloud.book.management.enums;

/**
 * 〈〉<br>
 * @author dell
 * @create 2018/7/2
 * @since 1.0.0
 */
public enum OrderDealNumWithStateNumEnum {
	//未找到原文-2(订单重新恢复为申请状态)/拒绝-1/接受(处理中)1/馆际推送2/馆际撤回3/已完成4
	//未找到全文
	O_NOTEXT(-2,OrderTypeEnum.O_NOTEXT.getDealNum()),
	//被拒绝
	D_REFUSE(-1,OrderTypeEnum.O_REFUSE.getDealNum()),
	//处理中
	D_TAKE(1,OrderTypeEnum.O_DEALING.getDealNum()),
	//馆际推送
	D_COULD(2,OrderTypeEnum.F_COULD.getDealNum()),
	//馆际撤回
	D_RECALL(3,OrderTypeEnum.F_RECALL.getDealNum()),
	//已完成
	D_FINISH(4,OrderTypeEnum.O_FINISH.getDealNum()),
	;
	private Integer dealNum;
	private String dealCode;

	//订单描述
	private OrderDealNumWithStateNumEnum(Integer  dealNum, String dealCode) {
		this.dealNum=dealNum;
		this.dealCode=dealCode;
	}

	/**
	 * 通过请求key获取描述key
	 * @param dealNum
	 * @return
	 */
	public static String getDealNumByDealsFlag(Integer dealNum){
		for (OrderDealNumWithStateNumEnum oe : OrderDealNumWithStateNumEnum.values()){
			if (oe.getDealNum() == dealNum){
				return oe.getDealCode();
			}
		}
		return null;
	}

	public Integer getDealNum() {
		return dealNum;
	}
	public String getDealCode() { return dealCode; }
}