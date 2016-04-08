package com.recycling.common.enums;

/**
 * Description : 用户状态枚举 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:01:43 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public enum UserStatus {
	
	/**
	 * 正常
	 */
	ACTIVE("正常"),
	
	/**
	 *待业
	 */
	FROZEN("待业");
	
    private String name;

	public String getName() {
		return name;
	}
	private UserStatus(String name) { 
		this.name = name;
	}
	
}
