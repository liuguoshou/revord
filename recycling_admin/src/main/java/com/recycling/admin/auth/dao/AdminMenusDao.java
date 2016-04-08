package com.recycling.admin.auth.dao;

import com.recycling.admin.entity.AdminMenus;

import java.util.List;
import java.util.Map;

/**
 * @Title:AdminMenusDao.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public interface AdminMenusDao {
	
	/**
	 * 获得所有父级别菜单
	 * @return
	 */
	public List<AdminMenus> queryAllSuperMenus();

    /**
     * 获取用户菜单列表
     * @param ids
     * @return
     */
    public List<AdminMenus> queryUserSuperMenus(String ids);
	
	
	/**
	 * 根据所有父级别查询 可显示的菜单
	 */
	public Map<Long,List<AdminMenus>> queryAllSubDisplayMenusBySuperIds(String superMenuIds);
	
	/**
	 * 根据所有父级别查询 可显示的菜单
	 */
	public Map<Long,List<AdminMenus>> queryAllSubMenusBySuperIds(String superMenuIds);
	
	/**
	 * 取出所有子级别菜单
	 * @return
	 */
	public List<AdminMenus> queryAllSubMenus();
	
	/**
	 * 取出所有子级别菜单
	 * @return
	 */
	public List<AdminMenus> queryAllSubMenusByIds(String ids);

    /**
     * 添加菜单
     * @param adminMenus
     */
    public void addMenu(AdminMenus adminMenus);

    /**
     * 更新菜单
     * @param adminMenus
     */
    public void updateMenu(AdminMenus adminMenus);

    /**
     * 删除菜单
     * @param menu_id
     */
    public void deleteMenu(Long menu_id);

    /**
     * 查询菜单
     * @param menu_id
     * @return
     */
    public AdminMenus queryMenu(Long menu_id);

}
