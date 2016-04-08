package com.recycling.service.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.dao.region.RecRegionDao;

@Service("recRegionService")
public class RecRegionServiceImpl implements RecRegionService{

	private MemCacheService mem = MemCacheServiceImpl.getInstance();

	@Autowired
	private RecRegionDao recRegionDao;
	
	@Override
	public Long addRecRegion(RecRegion recRegion) {
		return recRegionDao.addRecRegion(recRegion);
	}

	@Override
	public RecRegion getByRegionId(Long regionId) {
		RecRegion recRegion = (RecRegion)mem.get(RecConstants.REC_REGION_INFO+regionId);
		if(null == recRegion){
			//recRegion = recRegionDao.getByRegionId(regionId, 0, 1);
			recRegion = recRegionDao.getByRegionId(regionId, 1, 1);
			if(null != recRegion){
				mem.set(RecConstants.REC_REGION_INFO+regionId, recRegion,RecConstants.cache_time);
			}
		}
		return recRegion;
	}

	@Override
	public boolean updateRegion(RecRegion recRegion)	throws StaleObjectStateException {
		return recRegionDao.updateRegion(recRegion);
	}

	@Override
	public List<RecRegion> getByParentId(Long parentId,Long areaId) {
//		List<RecRegion> recRegionList = (List<RecRegion>)mem.get(RecConstants.REC_REGION_LIST+areaId+"_"+parentId);
//		if(null == recRegionList){
//			recRegionList = recRegionDao.getByParentId(parentId,areaId);
//			if(null != recRegionList){
//				mem.set(RecConstants.REC_REGION_LIST+areaId+"_"+parentId, recRegionList,RecConstants.cache_time);
//			}
//		}
//		return recRegionList;
		return recRegionDao.getByParentId(parentId,areaId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long,List<RecRegion>> getAllRegionMap() {
		Map<Long,List<RecRegion>> recRegionMap = (Map<Long,List<RecRegion>>)mem.get(RecConstants.REC_REGION_MAP);
		if(null == recRegionMap){
			recRegionMap = new HashMap<Long, List<RecRegion>>();
			List<RecRegion> recRegionList = recRegionDao.getAllRegionInfo();
			if(null != recRegionList){
				for (RecRegion recRegion : recRegionList) {
					if(recRegionMap.containsKey(recRegion.getParentId())){
						recRegionMap.get(recRegion.getParentId()).add(recRegion);
					}else{
						List<RecRegion> regionList = new ArrayList<RecRegion>();
						regionList.add(recRegion);
						recRegionMap.put(recRegion.getParentId(), regionList);
					}
				}
				mem.set(RecConstants.REC_REGION_MAP, recRegionMap,RecConstants.cache_time);
			}
		}
		return recRegionMap;
	}
	
	@Override
	public List<RecRegion> getByStreetId(Long parentId) {
		
		return recRegionDao.getByStreetId(parentId);
	}


}
