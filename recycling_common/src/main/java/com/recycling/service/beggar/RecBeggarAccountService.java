package com.recycling.service.beggar;

import com.recycling.common.entity.RecBeggarAccount;
import com.recycling.common.enums.BeggarHistoryType;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 乞丐账户信息Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-25 下午2:33:58 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecBeggarAccountService {
	
	public RecBeggarAccount getById(Long accountId);
	
	public RecBeggarAccount getByBeggarId(Long beggarId);
	
	public Long addAccount(RecBeggarAccount recAccount);
	
	public boolean updateAccount(RecBeggarAccount recAccount) throws StaleObjectStateException;

    /**
     * 乞丐账户入款
     * @author XiaoXian Xu 
     * @param recAccount 账户信息
     * @param trxAmount订单金额
     * @param trxorderId订单Id
     * @param actHistoryType 账务历史类型
     * @param desc附加描述信息
     * @throws StaleObjectStateException
     */
    public void credit(RecBeggarAccount recAccount, Double trxAmount, Long trxorderId, BeggarHistoryType beggarHistoryType,String desc) throws StaleObjectStateException;

    /**
     * 乞丐账户出款
     * @author XiaoXian Xu 
     * @param account 账户信息
     * @param trxAmount订单金额
     * @param trxorderId订单Id
     * @param actHistoryType 账务历史类型
     * @param desc附加描述信息
     * @throws StaleObjectStateException
     */
    public void debit(RecBeggarAccount account, Double trxAmount, Long trxorderId, BeggarHistoryType beggarHistoryType,String desc) throws StaleObjectStateException;

}
