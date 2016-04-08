package com.recycling.admin.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Title:AdminUser.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class AdminUser implements Serializable{

	private static final long serialVersionUID = 3468950476773281351L;
	
	private Long adminUserId;
	
	private String userName;//用户名 登录使用
	
	private String password;//密码
	
	private String realName;//真实姓名
	
	private Integer isValid;//是否有效
	
	private Timestamp addTime;//增加时间
	

	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adminUserId == null) ? 0 : adminUserId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminUser other = (AdminUser) obj;
		if (adminUserId == null) {
			if (other.adminUserId != null)
				return false;
		} else if (!adminUserId.equals(other.adminUserId))
			return false;
		return true;
	}

	public Long getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	
	
	
	
	
	
}
