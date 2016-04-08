package com.recycling.common.enums;

/**
 * Description : 消息推送状态 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:01:43 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public enum PushStatus {
	
	/**
	 * 初始化
	 */
	INIT("初始化"),
	
	/**
	 * 成功
	 */
	SUCCESS("成功"),
	
	/**
	 *取消推送
	 */
	CANCEL("已取消");
	
    private String name;

	public String getName() {
		return name;
	}
	private PushStatus(String name) { 
		this.name = name;
	}
}
