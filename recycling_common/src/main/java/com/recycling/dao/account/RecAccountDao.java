package com.recycling.dao.account;

import com.recycling.common.entity.RecAccount;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 账户信息Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:43:14 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecAccountDao {
	
	public RecAccount getById(Long accountId);
	
	public RecAccount getByUserId(Long userId);
	
	public Long addAccount(RecAccount recAccount);
	
	public boolean updateAccount(RecAccount recAccount) throws StaleObjectStateException;
}
