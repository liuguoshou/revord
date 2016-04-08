package com.recycling.service.account;

import com.recycling.common.entity.RecAccount;
import com.recycling.common.enums.ActHistoryType;
import com.recycling.common.enums.IntegralHistoryType;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 用户账户Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午12:35:31 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecAccountService {
	
	public RecAccount getById(Long accountId);
	
	public RecAccount getByUserId(Long userId);
	
	public Long addAccount(RecAccount recAccount);
	
	public boolean updateAccount(RecAccount recAccount) throws StaleObjectStateException;

    /**
     * 账户入款
     * @author XiaoXian Xu 
     * @param recAccount 账户信息
     * @param trxAmount订单金额
     * @param trxorderId订单Id
     * @param actHistoryType 账务历史类型
     * @param desc附加描述信息
     * @throws StaleObjectStateException
     */
    public void credit(RecAccount recAccount, Double trxAmount, Long trxorderId, ActHistoryType actHistoryType,String desc) throws StaleObjectStateException;

    /**
     * 账户出款
     * @author XiaoXian Xu 
     * @param account 账户信息
     * @param trxAmount订单金额
     * @param trxorderId订单Id
     * @param actHistoryType 账务历史类型
     * @param desc附加描述信息
     * @throws StaleObjectStateException
     */
    public void debit(RecAccount account, Double trxAmount, Long trxorderId, ActHistoryType actHistoryType,String desc) throws StaleObjectStateException;

    /**
     * 账户积分入款
     * @author XiaoXian Xu 
     * @param recAccount 账户信息
     * @param trxAmount订单金额
     * @param trxorderId订单Id
     * @param actHistoryType 账务历史类型
     * @param desc附加描述信息
     * @throws StaleObjectStateException
     */
    public void creditIntegral(RecAccount recAccount, Long trxIntegral, Long trxorderId, IntegralHistoryType historyType,String desc) throws StaleObjectStateException;

    /**
     * 账户积分出款
     * @author XiaoXian Xu 
     * @param account 账户信息
     * @param trxAmount订单金额
     * @param trxorderId订单Id
     * @param actHistoryType 积分历史类型
     * @param desc附加描述信息
     * @throws StaleObjectStateException
     */
    public void debitIntegral(RecAccount account, Long trxIntegral, Long trxorderId, IntegralHistoryType historyType,String desc) throws StaleObjectStateException;

}
