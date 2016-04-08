package com.recycling.admin.system.dao;

import com.recycling.admin.system.querycase.AdminSystemMenuQueryCase;

import java.util.List;

/**
 * @Title:LesAdminSystemMenuDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface AdminSystemMenuDao {

    /**
     * 查询系统菜单列表
     * @param queryCase
     * @return
     */
    public List<Long> querySystemMenu(AdminSystemMenuQueryCase queryCase);
}
