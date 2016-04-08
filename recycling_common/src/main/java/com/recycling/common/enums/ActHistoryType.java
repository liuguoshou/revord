package com.recycling.common.enums;

/**
 *
 * @Title:CommentType.java
 * @Description: 账户历史类型枚举
 * @author:renli.yu
 * @version:v1.0
 */
public enum ActHistoryType {
	
	/**
	 *用户 销售收入
	 */
	SALES("销售收入"),
	
	/**
	 * 提现
	 */
	SETTLE("提现");
	
    private String name;

	public String getName() {
		return name;
	}
	private ActHistoryType(String name) { 
		this.name = name;
	}
}
