package com.springcloud.book.foreign.enums;

public enum AdminRoleEnum {
	//超级管理员CJZGLY;本地管理员LOCALGLY;馆际互借管理员CLOUD
	S_SUPER("CJZGLY","超级管理员"),
	S_LOCAL("LOCALGLY","本地管理员"),
	S_CLOUD("CLOUD","馆际互借管理员"),
	;

	private String dealNum;
	private String dealCode;

	private AdminRoleEnum(String  dealNum, String dealCode) {
		this.dealNum=dealNum;
		this.dealCode=dealCode;
	}

	/**
	 * 通过请求key获取描述key
	 * @param dealNum
	 * @return
	 */
	public static String getDealNumByDealsFlag(String dealNum){
		for (AdminRoleEnum oe : AdminRoleEnum.values()){
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