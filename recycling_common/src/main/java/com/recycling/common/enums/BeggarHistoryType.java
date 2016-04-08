package com.recycling.common.enums;

/**
 *
 * @Title:CommentType.java
 * @Description: 乞丐账户历史类型枚举
 * @author:renli.yu
 * @version:v1.0
 */
public enum BeggarHistoryType {
	
	/**
	 * 乞丐充值
	 */
	LOAD("充值");
	
    private String name;

	public String getName() {
		return name;
	}
	private BeggarHistoryType(String name) { 
		this.name = name;
	}
}
