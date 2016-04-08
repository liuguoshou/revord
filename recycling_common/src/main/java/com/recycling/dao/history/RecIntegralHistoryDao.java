package com.recycling.dao.history;

import java.util.List;

import com.recycling.common.entity.RecIntegralHistory;
import com.recycling.common.util.Pager;

/**
 * Description : 积分历史Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:09:38 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecIntegralHistoryDao {

	/**
	 * 插入积分历史记录
	 * @author XiaoXian Xu 
	 * @param recIntegralHistory
	 * @return
	 */
	public Long addIntegralHistory(RecIntegralHistory recIntegralHistory);
	
	/**
	 * 查询积分历史列表
	 * @author XiaoXian Xu 
	 * @param accountId
	 * @param pager
	 * @return
	 */
	public List<RecIntegralHistory> getHistoryByAccountId(Long accountId,Pager pager);
	
	public int getHistoryCount(Long accountId);
}
