package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.recycling.common.enums.SettleStatus;
import com.recycling.common.enums.TrxStatus;

/**
 * Description : 订单表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:45:31 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecTrxorder implements Serializable {

	private static final long serialVersionUID = 6507082867190909906L;

	private Long trxorderId;
	
	/**
	 *订单唯一序列号
	 */
	private String requestId;

	/**用户ID*/
	private Long userId;

	/**乞丐ID*/
	private Long collectId=0L;

	/**订单总金额*/
	private double trxAmount=0.0;

	/**订单状态*/
	private TrxStatus trxStatus = TrxStatus.INIT;
	/**订单提现状态*/
	private SettleStatus settleStatus = SettleStatus.INIT;
	private Date createDate = new Date();
	private Date updateDate = new Date();

	/**乐观锁版本号*/
	private Long version=0L;
	
	private String userAddress = "";
	
	private String createDateStr = "";
	
	private RecUser recUser = null;
	
	public Long getTrxorderId() {
		return trxorderId;
	}
	public void setTrxorderId(Long trxorderId) {
		this.trxorderId = trxorderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCollectId() {
		return collectId;
	}
	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}
	public double getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(double trxAmount) {
		this.trxAmount = trxAmount;
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
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public SettleStatus getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(SettleStatus settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	public RecUser getRecUser() {
		return recUser;
	}
	public void setRecUser(RecUser recUser) {
		this.recUser = recUser;
	}
	
}
