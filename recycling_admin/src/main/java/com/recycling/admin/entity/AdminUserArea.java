package com.recycling.admin.entity;

import java.io.Serializable;

/**
 * 
 * @author xqgxie
 *
 */
public class AdminUserArea implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 602652026420593042L;
	private int userAreaId;
	private int userId;
	private int areaType;
	private String areaIds;
	
	
	
	public AdminUserArea() {
		super();
	}
	public AdminUserArea(int userAreaId, int userId, int areaType,
			String areaIds) {
		super();
		this.userAreaId = userAreaId;
		this.userId = userId;
		this.areaType = areaType;
		this.areaIds = areaIds;
	}
	public int getUserAreaId() {
		return userAreaId;
	}
	public void setUserAreaId(int userAreaId) {
		this.userAreaId = userAreaId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAreaType() {
		return areaType;
	}
	public void setAreaType(int areaType) {
		this.areaType = areaType;
	}
	public String getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
	
	
}
