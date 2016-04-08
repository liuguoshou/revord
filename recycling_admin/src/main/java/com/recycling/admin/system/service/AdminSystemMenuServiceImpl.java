package com.recycling.admin.system.service;

import com.recycling.admin.auth.dao.AdminMenusDao;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.admin.system.dao.AdminSystemMenuDao;
import com.recycling.admin.system.querycase.AdminSystemMenuQueryCase;
import com.recycling.common.util.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title:LesAdminSystemMenuServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("lesAdminSystemMenuService")
public class AdminSystemMenuServiceImpl implements AdminSystemMenuService {

    @Autowired
    private AdminMenusDao adminMenusDao;

    @Autowired
    private AdminSystemMenuDao adminSystemMenuDao;

    /**
     * 查询系统所有菜单
     * @return
     */
    @Override
    public List<AdminMenus> queryAllSystemMenus() {
        List<AdminMenus> parentList=adminMenusDao.queryAllSuperMenus();
        List<Long> idList=new ArrayList<Long>();
        for (AdminMenus adminMenus:parentList){
            idList.add(adminMenus.getMenuId());
        }
        String ids=CommonUtils.listToString(idList);
        Map<Long,List<AdminMenus>> map = adminMenusDao.queryAllSubMenusBySuperIds(ids);
        for (AdminMenus adminMenus:parentList){
            List<AdminMenus> subMenuList=map.get(adminMenus.getMenuId());
            adminMenus.setListSubMenu(subMenuList);
        }
        return parentList;
    }

    /**
     * 查询系统菜单列表
     * @param queryCase
     * @return
     */
    @Override
    public List<AdminMenus> querySystemMenus(AdminSystemMenuQueryCase queryCase) {
        List<Long> idList= adminSystemMenuDao.querySystemMenu(queryCase);
        if (idList == null || idList.size() == 0)
            return null;
        String ids= CommonUtils.listToString(idList);
        Map<Long,List<AdminMenus>> map=adminMenusDao.queryAllSubMenusBySuperIds(ids);
        List<AdminMenus>  parentMenusList=adminMenusDao.queryUserSuperMenus(ids);
        for (AdminMenus adminMenus:parentMenusList){
            List<AdminMenus> sublist=map.get(adminMenus.getMenuId());
            adminMenus.setListSubMenu(sublist);
        }
        return parentMenusList;
    }

    /**
     * 查询所有父级菜单
     * @return
     */
    @Override
    public List<AdminMenus> queryParentMenus() {
        return adminMenusDao.queryAllSuperMenus();
    }

    /**
     * 添加系统菜单
     * @param adminMenus
     */
    @Override
    public void addSystemMenus(AdminMenus adminMenus) {
        adminMenusDao.addMenu(adminMenus);
    }

    /**
     * 更新系统菜单
     * @param adminMenus
     */
    @Override
    public void updateSystemMenus(AdminMenus adminMenus) {
        adminMenusDao.updateMenu(adminMenus);
    }

    /**
     * 删除系统菜单
     * @param menu_id
     */
    @Override
    public void deleteSystemMenus(Long menu_id) {
        adminMenusDao.deleteMenu(menu_id);
    }

    /**
     * 根据ID查询菜单
     * @param menu_id
     * @return
     */
    @Override
    public AdminMenus queryAdminMenusById(Long menu_id) {
        return adminMenusDao.queryMenu(menu_id);
    }
}
