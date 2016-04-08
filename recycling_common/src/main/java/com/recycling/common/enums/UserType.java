package com.recycling.common.enums;

/**
 * Description : 用户类型枚举 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:01:43 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public enum UserType {
	
	/**
	 *用户
	 */
	USER("用户"),
	
	/**
	 *乞丐 
	 */
	PAUPER("乞丐");
	
    private String name;

	public String getName() {
		return name;
	}
	private UserType(String name) { 
		this.name = name;
	}
}
