package com.recycling.admin.area.service;

import java.util.List;

import com.recycling.admin.area.querycase.RecyclingAdminAreaQueryCase;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecRegion;

/**
 * @Title:RecyclingAdminRegionService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminAreaService {

	
	public List<RecArea> getByParentId (Long parentId);
	
    public Boolean updateRecyclingRegion(RecArea recArea) ;
    
    
    /**
     * 查询地区列表
     * @param queryCase
     * @return
     */
    public List<RecArea> queryRecyclingArea(RecyclingAdminAreaQueryCase queryCase);
    
	public RecArea getByAreaId(Long areaId) ;
	
	 public boolean updateArea(RecArea recArea);
    
	/**
	 * 得到已开通的
	 * @param parentId
	 * @return
	 */
	public List<RecArea> getActiveByParentId(Long parentId) ;
	
	/**
	 * 得到所有的
	 * @param parentId
	 * @return
	 */
	public List<RecArea> getAllActiveByParentId(Long parentId) ;
	
	public Long addRecArea(RecArea recAre);
}
