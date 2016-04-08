package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.recycling.common.enums.PushStatus;
import com.recycling.common.enums.TrxStatus;

/**
 * Description : 用户发单请求表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:45:09 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecMessage implements Serializable {

	private static final long serialVersionUID = -4355883424392679498L;
	
	private Long messageId;

	/**用户Id*/
	private Long userId;
	
	/**订单ID*/
	private Long trxorderId=0L;
	
	/**用户所属区域ID*/
	private Long regionId;

	/**乞丐ID*/
	private Long collectId=0L;

	/**推送状态*/
	private PushStatus pushStatus=PushStatus.INIT;
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;
	private String createDateStr = "";
	private TrxStatus trxStatus = null;
	private String trxAmountStr = null;
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Long getCollectId() {
		return collectId;
	}
	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}
	public PushStatus getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(PushStatus pushStatus) {
		this.pushStatus = pushStatus;
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
	public TrxStatus getTrxStatus() {
		return trxStatus;
	}
	public void setTrxStatus(TrxStatus trxStatus) {
		this.trxStatus = trxStatus;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public String getTrxAmountStr() {
		return trxAmountStr;
	}
	public void setTrxAmountStr(String trxAmountStr) {
		this.trxAmountStr = trxAmountStr;
	}
	
}
