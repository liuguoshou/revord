package com.recycling.service.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.entity.RecIntegralHistory;
import com.recycling.common.util.Pager;
import com.recycling.dao.history.RecIntegralHistoryDao;

/**
 * Description : TODO <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:09:34 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Service("recIntegralHistoryService")
public class RecIntegralHistoryServiceImpl implements RecIntegralHistoryService{

	@Autowired
	private RecIntegralHistoryDao recIntegralHistoryDao;
	
	@Override
	public Long addIntegralHistory(RecIntegralHistory recIntegralHistory) {
		return recIntegralHistoryDao.addIntegralHistory(recIntegralHistory);
	}
	
	
	@Override
	public List<RecIntegralHistory> getHistoryByAccountId(Long accountId, Pager pager) {
		return recIntegralHistoryDao.getHistoryByAccountId(accountId, pager);
	}


	@Override
	public int getHistoryCount(Long accountId) {
		return recIntegralHistoryDao.getHistoryCount(accountId);
	}

}
