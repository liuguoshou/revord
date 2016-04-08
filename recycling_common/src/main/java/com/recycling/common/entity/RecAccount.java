package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.security.auth.login.AccountException;

import com.recycling.common.enums.AccountStatus;
import com.recycling.common.util.Amount;

/**
 * Description : 账户信息表实体 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:32:39 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecAccount implements Serializable{

	private static final long serialVersionUID = 1435439899091737178L;
	
	private Long accountId;

	/**当前账户对应的用户Id*/
	private Long userId;

	/**累计收入金额*/
	private double totalIncome=0.0;

	/**当前账户余额*/
	private double balance=0.0;

	/**当前账户总积分*/
	private Long integral=0L;

	/**账户状态*/
	private AccountStatus accountStatus=AccountStatus.ACTIVE;
	private Date createDate = new Date();
	private Date updateDate = new Date();
	private Long version=0L;
	
    /**
     * 入款
     * @param amount
     * @param isFreeAmount
     * @throws AccountException
     */
    public void credit(double amount){
        setBalance(Amount.add(this.balance, amount));
    }
    
    /**
     * 积分入帐
     * 
     * @param amount
     * @param isFreeAmount
     * @throws AccountException
     */
    public void creditIntegral(Long trxIntegral){
        setIntegral(this.integral+trxIntegral);
    }

    /**
     * 出款
     * @param amount
     * @throws AccountException
     */
    public void debit(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
//        if (!AccountStatus.ACTIVE.equals(getAccountStatus())) {
//
//            throw new AccountException(AccountException.ACCOUNT_STATUS_INVALID);
//        }
        setBalance(Amount.sub(this.balance, amount));
    }
    
    /**
     * 积分出账
     * @param amount
     * @throws AccountException
     */
    public void debitIntegral(Long trxIntegral) {

        if (trxIntegral < 0) {
            throw new IllegalArgumentException("trxIntegral must be > 0");
        }
//        if (!AccountStatus.ACTIVE.equals(getAccountStatus())) {
//
//            throw new AccountException(AccountException.ACCOUNT_STATUS_INVALID);
//        }
        setIntegral(this.integral-trxIntegral);
    }
    
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public double getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Long getIntegral() {
		return integral;
	}
	public void setIntegral(Long integral) {
		this.integral = integral;
	}
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
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
