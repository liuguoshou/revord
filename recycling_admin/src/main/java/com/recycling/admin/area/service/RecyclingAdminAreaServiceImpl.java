package com.recycling.admin.area.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.admin.area.dao.RecyclingAdminAreaDao;
import com.recycling.admin.area.querycase.RecyclingAdminAreaQueryCase;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.util.CommonUtils;
import com.recycling.dao.area.RecAreaDao;

/**
 * @Title:RecyclingAdminRegionServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("recyclingAdminAreaService")
public class RecyclingAdminAreaServiceImpl implements RecyclingAdminAreaService {
	
	@Autowired
	private RecAreaDao recAreaDao;
	
	@Autowired
	private RecyclingAdminAreaDao recyclingAdminAreaDao;
	
	/**
	 * 得到所有子项
	 * @param parentId
	 * @return
	 */
	@Override
	public List<RecArea> getByParentId (Long parentId){
		return recAreaDao.getByParentId(parentId);
	}
	
	
    public Boolean updateRecyclingRegion(RecArea recArea) {
        Boolean flag=recAreaDao.updateArea(recArea);
     //   mem.remove(RecConstants.REC_REGION_MAP);
        return flag;
    }
	
	/**
	 * 得到已开通的
	 * @param parentId
	 * @return
	 */
	public List<RecArea> getActiveByParentId(Long parentId) {
		return recAreaDao.getActiveByParentId(parentId);
	}
	
	/**
	 * 得到所有的
	 * @param parentId
	 * @return
	 */
	public List<RecArea> getAllActiveByParentId(Long parentId) {
		return recAreaDao.getAllActiveByParentId(parentId);
	}
    
    
  /*  @Autowired
    private RecRegionDao recRegionDao;

    @Autowired
    private RecyclingAdminRegionDao recyclingAdminRegionDao;
*/
 //   private MemCacheService mem= MemCacheServiceImpl.getInstance();

    /**
     * 查询所有热区列表
     * @return
     */
//    @Override
//    public List<RecRegion> queryAllRecyclingRegion() {
//        return recRegionDao.getAllParentRegion();
//    }

    /**
     * 根据pid查询小区列表
     * @param parentId
     * @return
     */
//    @Override
//    public List<RecRegion> getByParentId(Long parentId) {
//        return recRegionDao.getByParentId(parentId,101L);
//    }

    /**
     * 查询小区列表
     * @param queryCase
     * @return
     */
    @Override
    public List<RecArea> queryRecyclingArea(RecyclingAdminAreaQueryCase queryCase) {
        List<Long> idList=recyclingAdminAreaDao.queryRecyclingArea(queryCase);
        if(idList == null || idList.size() == 0){
            return null;
        }
        String ids= CommonUtils.listToString(idList);
        List<RecArea> recRegionList=recAreaDao.getRecAreaList(ids);
        return recRegionList;
    }


	@Override
	public RecArea getByAreaId(Long areaId) {		
		return recAreaDao.getByAreaId(areaId);
	}

	 public boolean updateArea(RecArea recArea){
		 return recAreaDao.updateArea(recArea);
	 }
	
	public Long addRecArea(RecArea recAre) {		
		 return recAreaDao.addRecArea(recAre);
	}
	 
    /**
     * 添加小区
     * @param recRegion
     * @return
     */
//    @Override
//    public Boolean addRecyclingRegion(RecRegion recRegion) {
//        Long region_id=recRegionDao.addRecRegion(recRegion);
//        Boolean flag=true;
//        if(region_id==null){
//            flag=false;
//        }
//        mem.remove(RecConstants.REC_REGION_MAP);
//        return flag;
//    }

    /**
     * 更新小区
     * @param recRegion
     * @return
     */
//    @Override
//    public Boolean updateRecyclingRegion(RecRegion recRegion) {
//        Boolean flag=recRegionDao.updateRegion(recRegion);
//        mem.remove(RecConstants.REC_REGION_MAP);
//        return flag;
//    }


    /**
     * 查询小区信息
     * @param region_id
     * @return
     */
//    @Override
//    public RecRegion getRecyclingRegionById(Long region_id) {
//        RecRegion recRegion=recRegionDao.getByRegionId(region_id,0,1);
//        if(recRegion.getParentId() != 0){
//            RecRegion parentRegion=recRegionDao.getByRegionId(recRegion.getParentId(),0,1);
//            recRegion.setParentRegion(parentRegion);
//        }
//        return recRegion;
//    }
//    
//    public List <RecRegion>getAllProvence(){
//    	return recRegionDao.getAllProvence();
//    }
    
}
