package com.recycling.admin.system.dao;

import com.recycling.admin.system.querycase.AdminSystemUserQueryCase;

import java.util.List;

/**
 * @Title:LesAdminSystemUserDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface AdminSystemUserDao {
    /**
     * 查询系统用户
     * @param queryCase
     * @return
     */
    public List<Long> querySystemUser(AdminSystemUserQueryCase queryCase);
}
