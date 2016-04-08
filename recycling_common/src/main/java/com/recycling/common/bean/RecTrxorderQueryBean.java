package com.recycling.common.bean;


/**
 * Description : 订单查询参数封装 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-20 上午10:16:11 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecTrxorderQueryBean {

	private int startRow;
	private int pageSize;
	private String startDate;
	private String endDate;
	private Long regionId;
	private Long collectId;
	private String userIds;
	private String trxorderIds;

	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public Long getCollectId() {
		return collectId;
	}
	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getTrxorderIds() {
		return trxorderIds;
	}
	public void setTrxorderIds(String trxorderIds) {
		this.trxorderIds = trxorderIds;
	}
	
}
