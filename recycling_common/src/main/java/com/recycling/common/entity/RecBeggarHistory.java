package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.recycling.common.enums.BeggarHistoryType;

/**
 * Description : 乞丐账户历史表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:39:20 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecBeggarHistory implements Serializable{

	private static final long serialVersionUID = 4358736541767079276L;

	private Long beggarHistoryId;

	/**订单Id*/
	private Long trxorderId;

	/**账户Id*/
	private Long accountId;

	/**账户余额*/
	private double balance=0.0;

	/**交易金额*/
	private double trxAmount=0.0;

	/**账户历史类型*/
	private BeggarHistoryType beggarHistoryType;

	/**附加描述信息*/
	private String description="";
	private Date createDate = new Date();
	public Long getBeggarHistoryId() {
		return beggarHistoryId;
	}
	public void setBeggarHistoryId(Long beggarHistoryId) {
		this.beggarHistoryId = beggarHistoryId;
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
	public BeggarHistoryType getBeggarHistoryType() {
		return beggarHistoryType;
	}
	public void setBeggarHistoryType(BeggarHistoryType beggarHistoryType) {
		this.beggarHistoryType = beggarHistoryType;
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
	
}
