package com.recycling.admin.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:AdminRole.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class AdminRole implements Serializable{

	private static final long serialVersionUID = -3974328625229850722L;
	
	
	private Long adminRoleId;
	
	private String roleName;
	
	private List<AdminMenus> listMenus;

	public Long getAdminRoleId() {
		return adminRoleId;
	}

	public void setAdminRoleId(Long adminRoleId) {
		this.adminRoleId = adminRoleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<AdminMenus> getListMenus() {
		return listMenus;
	}

	public void setListMenus(List<AdminMenus> listMenus) {
		this.listMenus = listMenus;
	}
	
	
	
}
