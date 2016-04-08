package com.recycling.admin.weixinmenu.dao;


import com.recycling.admin.entity.AdminWeixinMenu;

import java.util.List;

/**
 * @Title:RecyclingAdminWeixinMenuDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminWeixinMenuDao {
    /**
     * 添加微信菜单
     * @param adminWeixinMenu
     */
    public void addWeixinMenu(AdminWeixinMenu adminWeixinMenu);

    /**
     * 删除微信菜单
     * @param ids
     */
    public void deleteWeixinMenu(String ids);

    /**
     * 更新微信菜单
     * @param adminWeixinMenu
     */
    public void updateWeixinMenu(AdminWeixinMenu adminWeixinMenu);

    /**
     * 查询微信菜单列表
     * @param menu_type
     * @param pid
     * @param flag
     * @return
     */
    public List<AdminWeixinMenu> getAdminWeixinMenuList(String menu_type, int pid, boolean flag);

    /**
     * 查询微信菜单
     * @param weixin_menu_id
     * @return
     */
    public AdminWeixinMenu getAdminWeixinMenu(int weixin_menu_id);

    /**
     * 验证微信菜单
     * @param menu_type
     * @param pid
     * @return
     */
    public  Long validateWeixinMenu(String menu_type, int pid);
}
