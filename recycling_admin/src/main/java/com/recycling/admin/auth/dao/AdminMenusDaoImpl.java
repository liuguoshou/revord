package com.recycling.admin.auth.dao;

import com.recycling.admin.ds.AdminGenericDaoImpl;
import com.recycling.admin.entity.AdminMenus;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:AdminMenusDaoImpl.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Repository(value = "adminMenusDao")
public class AdminMenusDaoImpl extends AdminGenericDaoImpl implements AdminMenusDao {

	@Override
	public List<AdminMenus> queryAllSuperMenus() {
		String sql = "select menu_id,menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay from fansenter_admin_menu where menu_parent=0 and menu_isvalid='1' and menu_isdisplay='1'";
		List<Map<String, Object>> listMap = this.getJdbcTemplate()
				.queryForList(sql);
		return convertAdminMenus(listMap);
	}

    /**
     * 获取用户菜单列表
     * @param ids
     * @return
     */
    @Override
    public List<AdminMenus> queryUserSuperMenus(String ids) {
        String sql = "select menu_id,menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay from fansenter_admin_menu where menu_parent=0 and menu_isvalid='1' and menu_isdisplay='1' and menu_id in ("+ids+") ORDER BY sort ASC ";
        List<Map<String, Object>> listMap = this.getJdbcTemplate().queryForList(sql);
        return convertAdminMenus(listMap);
    }

    private List<AdminMenus> convertAdminMenus(List<Map<String, Object>> listMap) {
		List<AdminMenus> listAdminMenus = new ArrayList<AdminMenus>();
		for (Map<String, Object> map : listMap) {
			AdminMenus am = new AdminMenus();
			Long menuId = (Long) map.get("menu_id");
			String menuName = (String) map.get("menu_name");
			String menuUrl = (String) map.get("menu_url");
			Long parentId = (Long) map.get("menu_parent");
			Integer menuValid = (Integer) map.get("menu_isvalid");
			Integer menu_isdisplay = (Integer) map.get("menu_isdisplay");

			am.setMenuId(menuId);
			am.setMenuName(menuName);
			am.setMenuUrl(menuUrl);
			am.setParentId(parentId);
			am.setIsValid(menuValid);
			am.setMenuDisplay(menu_isdisplay);
			listAdminMenus.add(am);
		}
		return listAdminMenus;
	}

	/**
	 * 
	 */
	private Map<Long, List<AdminMenus>> convertAdminMenusMap(List<Map<String, Object>> listMap) {
		List<AdminMenus> listAdminMenus = convertAdminMenus(listMap);
		Map<Long, List<AdminMenus>> mapMenus = new HashMap<Long, List<AdminMenus>>();
		if (listAdminMenus != null && listAdminMenus.size() > 0) {
			for (AdminMenus adminMenus : listAdminMenus) {
				Long parentId = adminMenus.getParentId();
				List<AdminMenus> listMenus = mapMenus.get(parentId);
				if (listMenus == null) {
					listMenus = new ArrayList<AdminMenus>();

				}
				listMenus.add(adminMenus);
				mapMenus.put(parentId, listMenus);
			}
		}
		return mapMenus;
	}

	@Override
	public Map<Long, List<AdminMenus>> queryAllSubDisplayMenusBySuperIds(String superMenuIds) {
		String sql = "select menu_parent,menu_id,menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay from fansenter_admin_menu where menu_parent in ("+superMenuIds+") and menu_isvalid='1' and menu_isdisplay='1'";
		List<Map<String, Object>> listMap = this.getJdbcTemplate()
				.queryForList(sql);

		return convertAdminMenusMap(listMap);
	}

	@Override
	public Map<Long, List<AdminMenus>> queryAllSubMenusBySuperIds(String superMenuIds) {
		String sql = "select menu_parent,menu_id,menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay from fansenter_admin_menu where menu_parent in ("+superMenuIds+") and menu_isvalid='1'";
		List<Map<String, Object>> listMap = this.getJdbcTemplate()
				.queryForList(sql);

		return convertAdminMenusMap(listMap);
	}

	@Override
	public List<AdminMenus> queryAllSubMenusByIds(String ids) {
		String sql = "select menu_id,menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay from fansenter_admin_menu where menu_parent!=0 and menu_isvalid='1' and menu_id in ("+ids+")";
		List<Map<String, Object>> listMap = this.getJdbcTemplate()
				.queryForList(sql);
		return convertAdminMenus(listMap);
	}

    @Override
    public List<AdminMenus> queryAllSubMenus() {
        String sql = "select menu_id,menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay from fansenter_admin_menu where menu_parent!=0 and menu_isvalid='1'";
        List<Map<String, Object>> listMap = this.getJdbcTemplate()
                .queryForList(sql);
        return convertAdminMenus(listMap);
    }

    /**
     * 添加菜单
     * @param adminMenus
     */
    @Override
    public void addMenu(AdminMenus adminMenus) {
        String sql=" INSERT INTO fansenter_admin_menu(menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay) VALUES(?,?,?,?,?) ";
        this.getJdbcTemplate().update(sql,
            adminMenus.getMenuName(),adminMenus.getMenuUrl(),adminMenus.getParentId(),adminMenus.getIsValid(),adminMenus.getMenuDisplay()
        );
    }

    /**
     * 更新菜单
     * @param adminMenus
     */
    @Override
    public void updateMenu(AdminMenus adminMenus) {
        String sql=" update fansenter_admin_menu set menu_name=?,menu_url=?,menu_parent=?,menu_isvalid=?,menu_isdisplay=? where menu_id=? ";
        this.getJdbcTemplate().update(sql,adminMenus.getMenuName(),adminMenus.getMenuUrl(),adminMenus.getParentId(),adminMenus.getIsValid(),adminMenus.getMenuDisplay(),adminMenus.getMenuId());
    }

    /**
     * 删除菜单
     * @param menu_id
     */
    @Override
    public void deleteMenu(Long menu_id) {
        String sql=" delete from fansenter_admin_menu where menu_id=? ";
        this.getJdbcTemplate().update(sql,menu_id);
    }

    /**
     * 查询菜单
     * @param menu_id
     * @return
     */
    @Override
    public AdminMenus queryMenu(Long menu_id) {
        String sql=" select menu_id,menu_name,menu_url,menu_parent,menu_isvalid,menu_isdisplay from fansenter_admin_menu where menu_id=? ";
        List<Map<String,Object>> listMap=this.getJdbcTemplate().queryForList(sql, menu_id);
        if(listMap==null||listMap.size()==0)return null;
        Map<String,Object> map=listMap.get(0);

        AdminMenus am = new AdminMenus();
        Long menuId = (Long) map.get("menu_id");
        String menuName = (String) map.get("menu_name");
        String menuUrl = (String) map.get("menu_url");
        Long parentId = (Long) map.get("menu_parent");
        Integer menuValid = (Integer) map.get("menu_isvalid");
        Integer menu_isdisplay = (Integer) map.get("menu_isdisplay");
        am.setMenuId(menuId);
        am.setMenuName(menuName);
        am.setMenuUrl(menuUrl);
        am.setParentId(parentId);
        am.setIsValid(menuValid);
        am.setMenuDisplay(menu_isdisplay);
        return am;
    }
}
