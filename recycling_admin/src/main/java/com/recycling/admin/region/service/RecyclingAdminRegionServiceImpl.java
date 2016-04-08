package com.recycling.admin.region.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.admin.region.dao.RecyclingAdminRegionDao;
import com.recycling.admin.region.querycase.RecyclingAdminRegionQueryCase;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.common.util.CommonUtils;
import com.recycling.dao.region.RecRegionDao;

/**
 * @Title:RecyclingAdminRegionServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("recyclingAdminRegionService")
public class RecyclingAdminRegionServiceImpl implements RecyclingAdminRegionService {

    @Autowired
    private RecRegionDao recRegionDao;

    @Autowired
    private RecyclingAdminRegionDao recyclingAdminRegionDao;

    private MemCacheService mem= MemCacheServiceImpl.getInstance();

    /**
     * 查询所有热区列表
     * @return
     */
    @Override
    public List<RecRegion> queryAllRecyclingRegion() {
        return recRegionDao.getAllParentRegion();
    }

    /**
     * 根据pid查询小区列表
     * @param parentId
     * @return
     */
    @Override
    public List<RecRegion> getByParentId(Long parentId) {
        return recRegionDao.getByParentId(parentId,101L);
    }

	@Override
	public List<RecRegion> getByStreetId(Long parentId) {
		return recRegionDao.getByStreetId(parentId);
	}
    
    
    
    /**
     * 查询小区列表
     * @param queryCase
     * @return
     */
    @Override
    public List<RecRegion> queryRecyclingRegion(RecyclingAdminRegionQueryCase queryCase) {
        List<Long> idList=recyclingAdminRegionDao.queryRecyclingRegion(queryCase);
        if(idList == null || idList.size() == 0){
            return null;
        }
        String ids= CommonUtils.listToString(idList);
        List<RecRegion> recRegionList=recRegionDao.getRecRegionList(ids);
        return recRegionList;
    }

    /**
     * 添加小区
     * @param recRegion
     * @return
     */
    @Override
    public Boolean addRecyclingRegion(RecRegion recRegion) {
        Long region_id=recRegionDao.addRecRegion(recRegion);
        Boolean flag=true;
        if(region_id==null){
            flag=false;
        }
        mem.remove(RecConstants.REC_REGION_MAP);
        return flag;
    }

    /**
     * 更新小区
     * @param recRegion
     * @return
     */
    @Override
    public Boolean updateRecyclingRegion(RecRegion recRegion) {
        Boolean flag=recRegionDao.updateRegion(recRegion);
        mem.remove(RecConstants.REC_REGION_MAP);
        return flag;
    }


    /**
     * 查询小区信息
     * @param region_id
     * @return
     */
    @Override
    public RecRegion getRecyclingRegionById(Long region_id) {
        RecRegion recRegion=recRegionDao.getByRegionId(region_id,1,1);
        if(recRegion.getParentId() != 0){
            RecRegion parentRegion=recRegionDao.getByRegionId(recRegion.getParentId(),1,1);
            recRegion.setParentRegion(parentRegion);
        }
        return recRegion;
    }
    @Override
    public RecRegion getRecyclingRegionByIdAllState(Long region_id) {
        RecRegion recRegion=recRegionDao.getByRegionId(region_id);
        if(recRegion.getParentId() != 0){
            RecRegion parentRegion=recRegionDao.getByRegionId(recRegion.getParentId(),0,1);
            recRegion.setParentRegion(parentRegion);
        }
        return recRegion;
    }
    public List <RecRegion>getAllProvence(){
    	return recRegionDao.getAllProvence();
    }
    
    
    @Override
    public List<RecRegion> getAllByParentId(Long parentId) {      
        List<RecRegion> recRegionList=recRegionDao.getAllByParentId(parentId);
        return recRegionList;
    }

}
