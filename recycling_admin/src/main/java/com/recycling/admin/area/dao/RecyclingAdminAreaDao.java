package com.recycling.admin.area.dao;

import java.util.List;

import com.recycling.admin.area.querycase.RecyclingAdminAreaQueryCase;

/**
 * @Title:RecyclingAdminAreaDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminAreaDao {

    /**
     * 查询小区列表
     * @param queryCase
     * @return
     */
    public List<Long> queryRecyclingArea(RecyclingAdminAreaQueryCase queryCase);
}
