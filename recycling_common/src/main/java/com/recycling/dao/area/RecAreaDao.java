package com.recycling.dao.area;

import java.util.List;

import com.recycling.common.entity.RecArea;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.dao.area.RecAreaDaoImpl.RowMapperImpl;

/**
 * Description : 地市Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:00:54 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecAreaDao {
	
	public RecArea getByAreaId(Long areaId) ;
	
	public RecArea getByAreaId(Long areaId,int isOnLine,int isActive);
	
	public List<RecArea> getByParentId(Long parentId);
	
	public List<RecArea> getAllAreaInfo();
	
	public boolean updateArea(RecArea recArea)	throws StaleObjectStateException ;
	   /**
	    * 地区列表
	    * @param ids
	    * @return
	    */
	public List<RecArea> getRecAreaList(String ids) ;
	
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

	/**
	 * 添加地区
	 * @param recAre
	 * @return
	 */
	public Long addRecArea(RecArea recAre);


	/**
	 * 修改地区
	 * @return
	 */
	public boolean isIds();
}
