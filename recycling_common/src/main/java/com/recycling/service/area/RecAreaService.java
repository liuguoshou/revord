package com.recycling.service.area;

import java.util.List;

import com.recycling.common.entity.RecArea;

/**
 * Description : 区县Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:33:17 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecAreaService {

	public RecArea getByIdInCache(Long areaId);
	
	public List<RecArea> getAreaByParentIdInCache(Long parentId);
	
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
	 * @param recAre
	 * @return
	 */
	public boolean updateRecArea(RecArea recAre);

	/**
	 * 是否有无IDS数据
	 * @return
	 */
	public boolean isIds();
}
