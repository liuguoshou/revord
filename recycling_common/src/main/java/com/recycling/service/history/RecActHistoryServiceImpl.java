package com.recycling.service.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.entity.RecActHistory;
import com.recycling.dao.history.RecActHistoryDao;

@Service("recActHistoryService")
public class RecActHistoryServiceImpl implements RecActHistoryService{

	@Autowired
	private RecActHistoryDao recActHistoryDao;
	
	@Override
	public Long addActHistory(RecActHistory actHistory) {
		return recActHistoryDao.addActHistory(actHistory);
	}

}
