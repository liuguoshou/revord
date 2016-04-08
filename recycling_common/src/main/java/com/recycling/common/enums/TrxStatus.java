package com.recycling.common.enums;

/**
 * Description : 交易订单状态 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:01:43 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public enum TrxStatus {

	/**
	 * 初始化
	 */
	INIT("初始化"),
	/**
	 *进行中 
	 */
	PROCESSING("进行中"),
	
	/**
	 * 待确认
	 */
	CONFIRM("确认中"),
	
	/**
	 * 交易成功
	 */
	SUCCESS("交易成功"),
	
	/**
	 * 已取消
	 */
	CANCEL("已取消");
	
    private String name;

	public String getName() {
		return name;
	}
	private TrxStatus(String name) { 
		this.name = name;
	}
}
