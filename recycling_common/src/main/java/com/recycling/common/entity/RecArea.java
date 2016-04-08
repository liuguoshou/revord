package com.recycling.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : 地市表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:40:06 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecArea implements Serializable {

	private static final long serialVersionUID = 9151094911830151290L;

	private Long areaId;

	/**区县中文名称*/
	private String areaCnName="";

	/**曲线英文名称*/
	private String areaEnName="";

	/**父级区县ID*/
	private Long parentId;

	/**当前区县是否激活*/
	private int isActive;

	/**当前区县是否上线*/
	private int isOnline;
	
	private int areaType;

	private String ids;
	
//	private List<RecArea> childAreaList = new ArrayList<RecArea>();
	
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAreaCnName() {
		return areaCnName;
	}
	public void setAreaCnName(String areaCnName) {
		this.areaCnName = areaCnName;
	}
	public String getAreaEnName() {
		return areaEnName;
	}
	public void setAreaEnName(String areaEnName) {
		this.areaEnName = areaEnName;
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
	public int getAreaType() {
		return areaType;
	}
	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
