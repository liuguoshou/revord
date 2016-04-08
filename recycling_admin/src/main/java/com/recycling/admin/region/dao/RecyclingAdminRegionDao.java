package com.recycling.admin.region.dao;

import com.recycling.admin.region.querycase.RecyclingAdminRegionQueryCase;

import java.util.List;

/**
 * @Title:RecyclingAdminRegionDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminRegionDao {

    /**
     * 查询小区列表
     * @param queryCase
     * @return
     */
    public List<Long> queryRecyclingRegion(RecyclingAdminRegionQueryCase queryCase);
    
    /**
     *  根据父Id,得到此节点下的所有街道Id
     * @param parentId
     * @return
     */
	public String getParentIds(Long parentId);
}
