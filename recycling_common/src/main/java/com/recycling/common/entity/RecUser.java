package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.recycling.common.enums.UserStatus;
import com.recycling.common.enums.UserType;

import javax.swing.plaf.synth.Region;

/**
 * Description : 用户表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:45:41 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecUser implements Serializable {

	private static final long serialVersionUID = -8197423073125808277L;

	private Long userId;

	/**真是姓名*/
	private String realName="";

	/**手机号*/
	private String mobile="";

	/**密码*/
	private String password="";

	/**邮箱*/
	private String email="";

	/**区县Id*/
	private Long areaId;

	/**所属小区Id*/
	private Long regionId;

	/**详细地址（具体到门牌号）*/
	private String address="";

	/**开放平台ID*/
	private String openId="";
	
	/**用户类型 消费者 或者乞丐*/
	private UserType userType;

	/**用户状态 正常或者冻结 */
	private UserStatus userStatus=UserStatus.FROZEN;

	/**附加描述信息*/
	private String description="";
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;

    private RecRegion region;

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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

    public RecRegion getRegion() {
        return region;
    }

    public void setRegion(RecRegion region) {
        this.region = region;
    }
}
