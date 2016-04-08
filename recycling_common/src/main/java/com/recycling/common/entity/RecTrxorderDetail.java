package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.recycling.common.enums.TrxStatus;

/**
 * Description : 订单明细表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:45:36 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecTrxorderDetail implements Serializable {

	private static final long serialVersionUID = 2550108537370717640L;

	private Long trxorderDetailId;

	/**订单id*/
	private Long trxorderId;

	/**具体小区Id*/
	private Long regionId;

	/**具体分类ID*/
	private Long categoryId;

	/**数量*/
	private double count;
	
	/**价格*/
	private double price;
	
	/**单位*/
	private String unit;

	/**订单状态*/
	private TrxStatus trxStatus = TrxStatus.PROCESSING;
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;
	/**分类名称*/
	private String categoryName = "";
	
	public Long getTrxorderDetailId() {
		return trxorderDetailId;
	}
	public void setTrxorderDetailId(Long trxorderDetailId) {
		this.trxorderDetailId = trxorderDetailId;
	}
	public Long getTrxorderId() {
		return trxorderId;
	}
	public void setTrxorderId(Long trxorderId) {
		this.trxorderId = trxorderId;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public TrxStatus getTrxStatus() {
		return trxStatus;
	}
	public void setTrxStatus(TrxStatus trxStatus) {
		this.trxStatus = trxStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
