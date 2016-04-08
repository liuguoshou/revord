package com.recycling.service.category;

import java.util.List;
import java.util.Map;

import com.recycling.common.entity.RecCategory;

/**
 * Description :  商品分类Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午4:19:56 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecCategoryService {

	public Long addRecCategory(RecCategory recCategory);
	
	public boolean updateRecCategory(RecCategory recCategory);
	
	public RecCategory getByCategoryId(Long categoryId);
	
	public List<RecCategory> getByParentId(Long parentId);
	
	public Map<Long,RecCategory> getAllCategoryInfo();
	
	/**
	 *查询分类关系Map key parentId value 子类集合 parentId 0为一级
	 */
	public Map<Long,List<RecCategory>> getCategoryRelationMap();
}
