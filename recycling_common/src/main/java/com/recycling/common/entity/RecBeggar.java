package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.recycling.common.enums.BeggarStatus;

/**
 * Description : 乞丐表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:45:41 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecBeggar implements Serializable {

	private static final long serialVersionUID = -394467069278837845L;

	private Long beggarId;

	/**真是姓名*/
	private String realName="";

	/**手机号*/
	private String mobile="";

	/**密码*/
	private String password="";

	/**开放平台ID*/
	private String openId="";
	
	/**乞丐状态 正常或者冻结 */
	private BeggarStatus beggarStatus=BeggarStatus.FROZEN;


    /**
     * 社区对象
     */
    private RecRegion region;

    /**
     * 所有小区id
     */
    private String regionids;

    /**
     * 乞丐负责区域信息
     */
    private List<RecBeggarAddress> addressList;

	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;
	public Long getBeggarId() {
		return beggarId;
	}
	public void setBeggarId(Long beggarId) {
		this.beggarId = beggarId;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public BeggarStatus getBeggarStatus() {
		return beggarStatus;
	}
	public void setBeggarStatus(BeggarStatus beggarStatus) {
		this.beggarStatus = beggarStatus;
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

    public List<RecBeggarAddress> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<RecBeggarAddress> addressList) {
        this.addressList = addressList;
    }

    public RecRegion getRegion() {
        return region;
    }

    public void setRegion(RecRegion region) {
        this.region = region;
    }

    public String getRegionids() {
        return regionids;
    }

    public void setRegionids(String regionids) {
        this.regionids = regionids;
    }
}
