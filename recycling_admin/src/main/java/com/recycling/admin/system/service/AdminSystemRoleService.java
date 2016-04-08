package com.recycling.admin.system.service;

import com.recycling.admin.entity.AdminRole;
import com.recycling.admin.system.querycase.AdminSystemRoleQueryCase;

import java.util.List;
import java.util.Map;

/**
 * @Title:LesAdminSystemRoleService
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface AdminSystemRoleService {

    /**
     * 查询系统角色
     * @param queryCase
     * @return
     */
    public List<AdminRole> querySystemRole(AdminSystemRoleQueryCase queryCase);

    /**
     * 添加系统角色
     * @param roleName
     * @param super_ids
     * @param sub_ids
     */
    public void addSystemRole(String roleName, String super_ids, String sub_ids);

    /**
     * 修改系统角色
     * @param roleId
     * @param roleName
     * @param super_ids
     * @param sub_ids
     */
    public void updateSystemRole(Long roleId, String roleName, String super_ids, String sub_ids);

    /**
     * 查询角色信息
     * @param roleId
     * @return
     */
    public Map<String,Object> querySystemRoleByRoleId(Long roleId);

    /**
     * 查询所有角色
     * @return
     */
    public List<AdminRole> querySystemRole();

    /**
     * 查询用户的角色ID
     * @param userId
     * @return
     */
    public Long queryRoleIdByUserId(Long userId);

    /**
     * 删除角色
     * @param roleId
     */
    public void deleteRole(Long roleId);

}
