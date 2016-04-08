package com.recycling.common.enums;

/**
 *
 * @Title:CommentType.java
 * @Description: 账户状态枚举类
 * @author:renli.yu
 * @version:v1.0
 */
public enum AccountStatus {
	/**
	 * 激活
	 */
	ACTIVE("激活"),
	
	/**
	 * 冻结
	 */
	FROZEN("冻结");
	
    private String name;

	public String getName() {
		return name;
	}
	private AccountStatus(String name) { 
		this.name = name;
	}
}
