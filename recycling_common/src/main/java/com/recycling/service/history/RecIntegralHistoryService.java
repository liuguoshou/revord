package com.recycling.service.history;

import java.util.List;

import com.recycling.common.entity.RecIntegralHistory;
import com.recycling.common.util.Pager;

/**
 * Description : 积分历史记录service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:08:46 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecIntegralHistoryService {

	/**
	 * 插入积分历史记录
	 * @author XiaoXian Xu 
	 * @param recIntegralHistory
	 * @return
	 */
	public Long addIntegralHistory(RecIntegralHistory recIntegralHistory);
	
	public int getHistoryCount(Long accountId);
	
	/**
	 * 前期暂时先不分页
	 * @author XiaoXian Xu 
	 * @param userId
	 * @param pager
	 * @return
	 */
	public List<RecIntegralHistory> getHistoryByAccountId(Long accountId,Pager pager);
}
