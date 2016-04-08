package com.recycling.admin.pauper.dao;

import com.recycling.admin.pauper.querycase.RecyclingAdminPauperQueryCase;

import java.util.List;

/**
 * @Title:RecyclingAdminPauperDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminPauperDao {

    /**
     * 查询乞丐列表
     * @param queryCase
     * @return
     */
    public List<Long> queryRecyclingAdminPauper(RecyclingAdminPauperQueryCase queryCase);
}
