package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description : 区域表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:45:27 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecRegion implements Serializable {

	private static final long serialVersionUID = -9204042720061401263L;

	private Long regionId;

	/**具体区域中文名称*/
	private String regionCnName="";

	/**具体区域英文名称*/
	private String regionEnName="";

	/**父类Id*/
	private Long parentId;

	/**是否激活*/
	private int isActive;

	/**是否上线*/
	private int isOnline;
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;
	
	private String areaType;

    private RecRegion parentRegion;

    /**
     * 省市ID
     */
    private Long areaId;

	private List<RecRegion> childRegionList = null;
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getRegionCnName() {
		return regionCnName;
	}
	public void setRegionCnName(String regionCnName) {
		this.regionCnName = regionCnName;
	}
	public String getRegionEnName() {
		return regionEnName;
	}
	public void setRegionEnName(String regionEnName) {
		this.regionEnName = regionEnName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
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
	public List<RecRegion> getChildRegionList() {
		return childRegionList;
	}
	public void setChildRegionList(List<RecRegion> childRegionList) {
		this.childRegionList = childRegionList;
	}

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public RecRegion getParentRegion() {
        return parentRegion;
    }

    public void setParentRegion(RecRegion parentRegion) {
        this.parentRegion = parentRegion;
    }
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
    
    
}
