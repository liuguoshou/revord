package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.recycling.common.enums.ActHistoryType;

/**
 * Description : 账户历史表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:39:20 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecActHistory implements Serializable{

	private static final long serialVersionUID = 3132994137028236392L;

	private Long actHistoryId;

	/**订单Id*/
	private Long trxorderId;

	/**账户Id*/
	private Long accountId;

	/**账户余额*/
	private double balance=0.0;

	/**交易金额*/
	private double trxAmount=0.0;

	/**账户历史类型*/
	private ActHistoryType actHistoryType;

	/**附加描述信息*/
	private String description="";
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;
	public Long getActHistoryId() {
		return actHistoryId;
	}
	public void setActHistoryId(Long actHistoryId) {
		this.actHistoryId = actHistoryId;
	}
	public Long getTrxorderId() {
		return trxorderId;
	}
	public void setTrxorderId(Long trxorderId) {
		this.trxorderId = trxorderId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(double trxAmount) {
		this.trxAmount = trxAmount;
	}
	public ActHistoryType getActHistoryType() {
		return actHistoryType;
	}
	public void setActHistoryType(ActHistoryType actHistoryType) {
		this.actHistoryType = actHistoryType;
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
	
}
