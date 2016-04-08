package com.recycling.admin.system.service;

import com.recycling.admin.entity.AdminMenus;
import com.recycling.admin.system.querycase.AdminSystemMenuQueryCase;

import java.util.List;

/**
 * @Title:LesAdminSystemMenuService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface AdminSystemMenuService {

    /**
     * 查询所有菜单
     * @return
     */
    public List<AdminMenus> queryAllSystemMenus();

    /**
     * 查询系统菜单列表
     * @param queryCase
     * @return
     */
    public List<AdminMenus> querySystemMenus(AdminSystemMenuQueryCase queryCase);

    /**
     * 查询所有父级菜单
     * @return
     */
    public List<AdminMenus> queryParentMenus();

    /**
     * 添加菜单
     * @param adminMenus
     */
    public void addSystemMenus(AdminMenus adminMenus);

    /**
     * 更新菜单
     * @param adminMenus
     */
    public void updateSystemMenus(AdminMenus adminMenus);

    /**
     * 删除菜单
     * @param menu_id
     */
    public void deleteSystemMenus(Long menu_id);

    /**
     * 根据id查询菜单
     * @param menu_id
     * @return
     */
    public AdminMenus queryAdminMenusById(Long menu_id);
}
