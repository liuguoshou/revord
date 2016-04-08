package com.recycling.service.advise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.entity.RecUserAdvise;
import com.recycling.dao.advise.RecUserAdviseDao;

/**
 * Description : TODO <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:01:23 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Service("recUserAdviseService")
public class RecUserAdviseServiceImpl implements RecUserAdviseService{

	@Autowired
	private RecUserAdviseDao recUserAdviseDao;
	
	@Override
	public Long addUserAdvise(RecUserAdvise userAdvise) {
		return recUserAdviseDao.addUserAdvise(userAdvise);
	}

}
