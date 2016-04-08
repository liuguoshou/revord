package com.recycling.admin.auth.service;

import com.recycling.admin.entity.AdminMenus;

import java.util.List;

/**
 * @Title:AdminAuthService.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public interface AdminAuthService {
	
	/**
	 * 取出所有菜单显示出来
	 * @return
	 */
	public List<AdminMenus> queryAllDisplayAdminMenus();

    /**
     * 取出用户所有显示的菜单
     * @param userId
     * @return
     */
    public List<AdminMenus> queryUserAdminMenus(Long userId);
	
	
	/**
	 * 取出所有菜单
	 * @return
	 */
	public List<AdminMenus> queryAllAdminMenus();
	
	/**
	 * 是否有权限访问某个URL
	 */
	public AdminMenus isAuthUrl(Long userId, String url);
	
	/**
	 * 取出所有子菜单
	 * @return
	 */
	public List<AdminMenus> queryAllSubMenus();
	
	/**
	 * 根据路径查找当前父菜单下面的子菜单
	 */
	public List<AdminMenus> queryParentOfSubMenuBySubPath(String path);
	
}
