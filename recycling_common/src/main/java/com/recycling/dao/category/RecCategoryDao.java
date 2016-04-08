package com.recycling.dao.category;

import java.util.List;
import java.util.Map;

import com.recycling.common.entity.RecCategory;

/**
 * Description : 商品分类Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:05:45 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecCategoryDao {

	public Long addRecCategory(RecCategory recCategory);
	
	public boolean updateRecCategory(RecCategory recCategory);
	
	public RecCategory getByCategoryId(Long categoryId);
	
	public List<RecCategory> getByParentId(Long parentId);
	
	public List<RecCategory> getAllCategoryInfo();

    /**
     * 查询所有父级分类
     * @return
     */
    public List<RecCategory> getAllParentCategroy();

    /**
     * 查询分类列表
     * @param ids
     * @return
     */
    public List<RecCategory> queryCategroyByIds(String ids);

    /**
     * 获取分类名称
     * @param ids
     * @return
     */
    public Map<Long,String> getCategroyNameByIds(String ids);
}
