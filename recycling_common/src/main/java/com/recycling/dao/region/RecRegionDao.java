package com.recycling.dao.region;

import java.util.List;
import java.util.Map;

import com.recycling.common.entity.RecRegion;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 区域Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:15:22 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecRegionDao {

	public Long addRecRegion(RecRegion recRegion);
	
	public RecRegion getByRegionId(Long regionId,int isActive,int isOnline);
	
	public boolean updateRegion(RecRegion recRegion)throws StaleObjectStateException;
	
	public List<RecRegion> getByParentId(Long parentId,Long areaId);
	
	public List<RecRegion> getAllRegionInfo();

    /**
     * 查询小区列表
     * @param ids
     * @return
     */
    public  List<RecRegion> getRecRegionList(String ids);

    /**
     * 查询所有热区
     * @return
     */
    public List<RecRegion> getAllParentRegion();

    /**
     * 根据ids查询所以热区名称
     * @param ids
     * @return
     */
    public Map<Long,String> getParentRegionCnName(String ids);
    
    public List<RecRegion> getAllProvence() ;

	public List<RecRegion> getAllByParentId(Long parentId);
	/**
	 * 只根据Id查询
	 * @param regionId
	 * @return
	 */
	public RecRegion getByRegionId(Long regionId);

	public List<RecRegion> getByStreetId(Long parentId);
}
