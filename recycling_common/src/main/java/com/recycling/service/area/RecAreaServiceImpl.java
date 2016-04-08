package com.recycling.service.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecArea;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.dao.area.RecAreaDao;

/**
 * Description : TODO <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午4:00:49 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Service("recAreaService")
public class RecAreaServiceImpl implements RecAreaService{

	@Autowired
	private RecAreaDao recAreaDao;
	
	private MemCacheService mem = MemCacheServiceImpl.getInstance();
	
	@Override
	public RecArea getByIdInCache(Long areaId) {
//		RecArea recArea = (RecArea)mem.get(RecConstants.REC_AREA_INFO+areaId);
//		if(null == recArea){
			//recArea = recAreaDao.getByAreaId(areaId, 1, 0);
		RecArea	recArea = recAreaDao.getByAreaId(areaId, 1, 1);
//			if(null != recArea){
//				mem.set(RecConstants.REC_AREA_INFO+areaId, recArea, RecConstants.cache_time);
//			}
//		}
		return recArea;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RecArea> getAreaByParentIdInCache(Long parentId) {
		List<RecArea> areaList = (List<RecArea>)mem.get(RecConstants.REC_AREA_LIST+parentId);
		if(null == areaList || areaList.size() == 0){
			areaList = recAreaDao.getByParentId(parentId);
			if(null != areaList && areaList.size() > 0){
				mem.set(RecConstants.REC_AREA_LIST+parentId, areaList,RecConstants.cache_time);
			}
		}
		return areaList;
	}

	@Override
	public List<RecArea> getRecAreaList(String ids) {
		return recAreaDao.getRecAreaList(ids);
	}

	@Override
	public List<RecArea> getActiveByParentId(Long parentId) {
		// TODO Auto-generated method stub
		return recAreaDao.getActiveByParentId(parentId);
	}

	@Override
	public List<RecArea> getAllActiveByParentId(Long parentId) {
		// TODO Auto-generated method stub
		return recAreaDao.getAllActiveByParentId(parentId);
	}

	@Override
	public Long addRecArea(RecArea recAre) {
		// TODO Auto-generated method stub
		return recAreaDao.addRecArea(recAre);
	}

	@Override
	public boolean updateRecArea(RecArea recAre){
		return recAreaDao.updateArea(recAre);
	}

	@Override
	public boolean isIds(){
		return recAreaDao.isIds();
	}
	
	
	
}
