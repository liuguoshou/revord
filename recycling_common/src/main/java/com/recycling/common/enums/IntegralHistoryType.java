package com.recycling.common.enums;


/**
 * Description : 积分历史类型枚举 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:59:53 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public enum IntegralHistoryType {
	
	/**
	 *回收废品
	 */
	RECYCLE_GARBAGE("回收废品");
	
    private String name;

	public String getName() {
		return name;
	}
	private IntegralHistoryType(String name) { 
		this.name = name;
	}
}
