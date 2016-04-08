package com.recycling.admin.user.dao;

import com.recycling.admin.user.querycase.RecyclingAdminUserQueryCase;

import java.util.List;

/**
 * @Title:RecyclingAdminUserDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminUserDao {

    /**
     * 查询用户列表ID
     * @param queryCase
     * @return
     */
    public List<Long> queryRecyclingUser(RecyclingAdminUserQueryCase queryCase);
}
