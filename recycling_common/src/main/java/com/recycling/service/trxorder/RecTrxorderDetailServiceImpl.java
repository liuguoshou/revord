package com.recycling.service.trxorder;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.entity.RecCategory;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.dao.trxorder.RecTrxorderDetailDao;
import com.recycling.service.category.RecCategoryService;

/**
 * Description : TODO <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:16:00 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Service("recTrxorderDetailService")
public class RecTrxorderDetailServiceImpl implements RecTrxorderDetailService{

	@Autowired
	private RecTrxorderDetailDao recTrxorderDetailDao;
	
	@Autowired
	private RecCategoryService recCategoryService;
	
	@Override
	public List<RecTrxorderDetail> getByTrxorderId(Long trxorderId) {
		List<RecTrxorderDetail> detailList =  recTrxorderDetailDao.getByTrxorderId(trxorderId);
		if(null != detailList && detailList.size() > 0){
			Map<Long,RecCategory> categoryMap = recCategoryService.getAllCategoryInfo();
			for (RecTrxorderDetail recTrxorderDetail : detailList) {
				RecCategory categoryInfo = categoryMap.get(recTrxorderDetail.getCategoryId());
				if(null != categoryInfo){
					recTrxorderDetail.setCategoryName(categoryInfo.getCategoryName());
				}
			}
		}
		return detailList;
	}

}
