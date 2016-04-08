package com.recycling.admin.auth.service;

import com.recycling.admin.auth.dao.AdminMenusDao;
import com.recycling.admin.auth.dao.AdminRoleDao;
import com.recycling.admin.constants.AdminConstant;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.common.util.CommonUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title:AdminAuthServiceImpl.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Service("adminAuthService")
public class AdminAuthServiceImpl implements AdminAuthService {

	@Autowired
	private AdminMenusDao adminMenuDao;

	@Autowired
	private AdminRoleDao adminRoleDao;

	@Override
	public List<AdminMenus> queryAllDisplayAdminMenus() {
		List<AdminMenus> listAdminMenus = adminMenuDao.queryAllSuperMenus();
		if (listAdminMenus == null || listAdminMenus.size() == 0)
			return null;
		String ids = getMenuIds(listAdminMenus);
		Map<Long, List<AdminMenus>> map = adminMenuDao
				.queryAllSubDisplayMenusBySuperIds(ids);

		convertMenus(map, listAdminMenus);
		return listAdminMenus;
	}

    /**
     * 查询用户显示菜单
     * @param userId
     * @return
     */
    @Override
    public List<AdminMenus> queryUserAdminMenus(Long userId) {
        String ids=adminRoleDao.queryRoleSuperMenuIds(userId);
        List<AdminMenus> adminMenusList=adminMenuDao.queryUserSuperMenus(ids);
        Map<Long,List<AdminMenus>> subMenusMap=adminMenuDao.queryAllSubDisplayMenusBySuperIds(ids);
        for (AdminMenus adminMenus:adminMenusList){
            List<AdminMenus> subMenuList=subMenusMap.get(adminMenus.getMenuId());
            adminMenus.setListSubMenu(subMenuList);
        }
        return adminMenusList;
    }

    private void convertMenus(Map<Long, List<AdminMenus>> map,
			List<AdminMenus> listAdminMenus) {
		if (map != null && map.size() > 0) {
			for (AdminMenus adminMenus : listAdminMenus) {
				Long parentId = adminMenus.getMenuId();
				List<AdminMenus> listSubMenu = map.get(parentId);
				adminMenus.setListSubMenu(listSubMenu);
			}
		}
	}

	private String getMenuIds(List<AdminMenus> listMenus) {
		List<Long> listIds = new ArrayList<Long>();
		for (AdminMenus adminMenus : listMenus) {
			listIds.add(adminMenus.getMenuId());
		}
		return CommonUtils.listToString(listIds);
	}

	@Override
	public List<AdminMenus> queryAllAdminMenus() {
		List<AdminMenus> listAdminMenus = adminMenuDao.queryAllSuperMenus();
		if (listAdminMenus == null || listAdminMenus.size() == 0)
			return null;
		String ids = getMenuIds(listAdminMenus);
		Map<Long, List<AdminMenus>> map = adminMenuDao
				.queryAllSubMenusBySuperIds(ids);

		convertMenus(map, listAdminMenus);
		return listAdminMenus;
	}

	@Override
	public AdminMenus isAuthUrl(Long userId, String url) {

		String roleIds = adminRoleDao.getRoleIdsByAdminUserId(userId);
		if (StringUtils.isBlank(roleIds))
			return null;

		Set<Long> menuIds = adminRoleDao.getSubAdminMenuListByIds(roleIds);
		if (menuIds == null || menuIds.size() == 0)
			return null;
		String ids = CommonUtils.setToString(menuIds);
		List<AdminMenus> listAllMenus = null;
		String jedisMenuKey = AdminConstant.USER_SUBMENU_KEY;
//		String json = JedisUtil.get(jedisMenuKey + ids);
//		if (StringUtils.isBlank(json)) {
			listAllMenus = adminMenuDao.queryAllSubMenusByIds(ids);
//			JedisUtil.set(jedisMenuKey + ids, JSON.toJSONString(listAllMenus),
//					60 * 60);
//		}else{
//			listAllMenus=JSON.parseArray(json, AdminMenus.class);
//		}
		if (listAllMenus == null || listAllMenus.size() == 0)
			return null;

		AdminMenus am = new AdminMenus();
		am.setMenuUrl(url);
		if (listAllMenus.indexOf(am) == -1)
			return null;
		return listAllMenus.get(listAllMenus.indexOf(am));
	}

	@Override
	public List<AdminMenus> queryAllSubMenus() {
		List<AdminMenus> listAdminMenus = null;
		// String json=JedisUtil.get(AdminJedisConstant.All_SUBMENU_KEY);
		// TODO:缓存
		listAdminMenus = adminMenuDao.queryAllSubMenus();
		return listAdminMenus;
	}

	@Override
	public List<AdminMenus> queryParentOfSubMenuBySubPath(String path) {
		List<AdminMenus> listSubMenu = queryAllSubMenus();

		List<AdminMenus> currentListSubMenu = null;

		if (listSubMenu != null && listSubMenu.size() > 0) {
			AdminMenus am = new AdminMenus();
			am.setMenuUrl(path);
			if (listSubMenu.indexOf(am) != -1) {
				AdminMenus amm = listSubMenu.get(listSubMenu.indexOf(am));
				Long parentId = amm.getParentId();
				Map<Long, List<AdminMenus>> map = adminMenuDao
						.queryAllSubMenusBySuperIds(parentId + "");
				
				//TODO：加缓存
				if (map != null && map.size() == 1) {
					currentListSubMenu = map.get(map.keySet().toArray()[0]);
				}
			}
		}
		return currentListSubMenu;
	}
}
