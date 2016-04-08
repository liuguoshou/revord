package com.recycling.dao.beggar;

import com.recycling.common.entity.RecBeggarAccount;

/**
 * Description : 乞丐账户Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-25 下午1:42:23 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecBeggarAccountDao {
	
	public RecBeggarAccount getById(Long accountId);
	
	public RecBeggarAccount getByBeggarId(Long beggarId);
	
	public Long addAccount(RecBeggarAccount recAccount);
	
	public boolean updateAccount(RecBeggarAccount recAccount);

}
