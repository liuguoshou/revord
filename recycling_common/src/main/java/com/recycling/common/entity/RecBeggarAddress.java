package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description : 乞丐地址信息表实体 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:32:39 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecBeggarAddress implements Serializable{

	private static final long serialVersionUID = 3098238616710638279L;

	private Long addressId;
	/**乞丐ID*/
	private Long beggarId;
	private Long areaId;
	private Long regionId;
	private String address;
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;

    /**
     * 省市区
     */
    private RecArea recArea;

    /**
     * 热区及小区
     */
    private RecRegion recRegion;

	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public Long getBeggarId() {
		return beggarId;
	}
	public void setBeggarId(Long beggarId) {
		this.beggarId = beggarId;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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


    public RecArea getRecArea() {
        return recArea;
    }

    public void setRecArea(RecArea recArea) {
        this.recArea = recArea;
    }

    public RecRegion getRecRegion() {
        return recRegion;
    }

    public void setRecRegion(RecRegion recRegion) {
        this.recRegion = recRegion;
    }
}
