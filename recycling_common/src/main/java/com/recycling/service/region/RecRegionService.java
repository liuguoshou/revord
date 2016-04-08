package com.recycling.service.region;

import java.util.List;
import java.util.Map;

import com.recycling.common.entity.RecRegion;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 区域Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午4:22:34 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecRegionService {

	public Long addRecRegion(RecRegion recRegion);
	
	public RecRegion getByRegionId(Long regionId);
	
	public boolean updateRegion(RecRegion recRegion)throws StaleObjectStateException;
	
	public List<RecRegion> getByParentId(Long parentId,Long areaId);
	
	public List<RecRegion> getByStreetId(Long parentId) ;
	
	public Map<Long,List<RecRegion>> getAllRegionMap();
}
