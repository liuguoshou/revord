package com.recycling.admin.category.dao;

import com.recycling.admin.category.querycase.RecyclingAdminCategroyQueryCase;

import java.util.List;

/**
 * @Title:RecyclingAdminCategroyDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminCategroyDao {

    /**
     * 查询分类列表
     * @param queryCase
     * @return
     */
    public List<Long> queryRecyclingCategroy(RecyclingAdminCategroyQueryCase queryCase);
}
