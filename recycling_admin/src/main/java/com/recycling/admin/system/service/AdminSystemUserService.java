package com.recycling.admin.system.service;

import com.recycling.admin.entity.AdminUser;
import com.recycling.admin.entity.AdminUserArea;
import com.recycling.admin.system.querycase.AdminSystemUserQueryCase;

import java.util.List;

/**
 * @Title:LesAdminSystemUserService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface AdminSystemUserService {

    /**
     * 查询系统用户
     * @param queryCase
     * @return
     */
    public List<AdminUser> querySystemUser(AdminSystemUserQueryCase queryCase);

    /**
     * 保存用户信息
     * @param adminUser
     * @param roleId
     */
    public void addSystemUser(AdminUser adminUser,String roleId);

    /**
     * 修改用户信息
     * @param adminUser
     * @param roleId
     */
    public void updateSystemUser(AdminUser adminUser,String roleId);

    /**
     * 用户详细信息
     * @param user_id
     * @return
     */
    public AdminUser detailSystemUser(Long user_id);

    /**
     * 删除系统用户
     * @param user_id
     */
    public void deleteSystemUser(Long user_id);

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    public Boolean queryAdminUserByUserName(String userName);
    
    /**
     * 添加用户查看权限
     * @param adminUserArea
     */
    public void addSystemUserArea(AdminUserArea adminUserArea) ;
}
