package com.recycling.admin.system.dao;

import com.recycling.admin.system.querycase.AdminSystemRoleQueryCase;

import java.util.List;

/**
 * @Title:LesAdminSystemRoleDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface AdminSystemRoleDao {

    /**
     * 查询角色用户
     * @param queryCase
     * @return
     */
    public List<Long> querySystemRole(AdminSystemRoleQueryCase queryCase);
}
