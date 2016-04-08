package com.recycling.admin.auth.dao;

import com.recycling.admin.entity.AdminRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title:AdminMenuDao.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public interface AdminRoleDao {
	
	
	
	/**
	 * 按照多角色id 查询出对应的子级别菜单Ids
	 * @param ids
	 * @return
	 */
	public Set<Long> getSubAdminMenuListByIds(String ids);
	
	/**
	 * 按照多角色id 查询出对应的父级别菜单ids
	 * @param ids
	 * @return
	 */
	public Set<Long> getParentAdminMenuListByIds(String ids);
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public String getRoleIdsByAdminUserId(Long userId);

    /**
     * 查询角色列表
     * @param ids
     * @return
     */
    public List<AdminRole> queryAdminRole(String ids);


    /**
     * 查询所有角色列表
     * @return
     */
    public List<AdminRole> queryAdminRole();

    /**
     * 添加角色
     */
    public void addAdminRole(String roleName,String super_ids,String sub_ids);

    /**
     * 更新角色
     */
    public void updateAdminRole(Long roleId,String roleName,String super_ids,String sub_ids);

    /**
     * 查询角色信息
     * @param roleId
     * @return
     */
    public Map<String,Object> querySystemRoleByRoleId(Long roleId);

    /**
     * 添加用户角色关联信息
     * @param userId
     * @param roleId
     */
    public void addUserRole(Long userId,Long roleId);

    /**
     * 更新用户角色信息
     * @param userId
     * @param roleId
     */
    public void updateUserRole(Long userId,Long roleId);

    /**
     * 删除用户角色信息
     * @param userId
     * @param roleId
     */
    public void deleteUserRole(Long userId,Long roleId);

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    public Long queryRoleIdByUserId(Long userId);

    /**
     * 查询用户菜单id
     * @return
     */
    public String queryRoleSuperMenuIds(Long userId);

    /**
     * 删除角色
     * @param roleId
     */
    public void deleteRole(Long roleId);
}
