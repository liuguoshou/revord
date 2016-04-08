package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.recycling.common.enums.IntegralHistoryType;

/**
 * Description : 积分历史表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:41:55 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecIntegralHistory implements Serializable {

	private static final long serialVersionUID = 3106238415409469523L;
	
	private Long integralHistoryId;

	/**订单Id*/
	private Long trxorderId;

	/**账户ID*/
	private Long accountId;

	/**当前总积分*/
	private Long integral=0L;

	/**交易积分*/
	private Long trxAmount=0L;

	/**积分历史类型*/
	private IntegralHistoryType integralHistoryType=IntegralHistoryType.RECYCLE_GARBAGE;

	/**附加描述信息*/
	private String description="";
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;
	private String createDateStr = "";
	public Long getIntegralHistoryId() {
		return integralHistoryId;
	}
	public void setIntegralHistoryId(Long integralHistoryId) {
		this.integralHistoryId = integralHistoryId;
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
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	public Long getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(Long trxAmount) {
		this.trxAmount = trxAmount;
	}
	public IntegralHistoryType getIntegralHistoryType() {
		return integralHistoryType;
	}
	public void setIntegralHistoryType(IntegralHistoryType integralHistoryType) {
		this.integralHistoryType = integralHistoryType;
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
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
	
}
