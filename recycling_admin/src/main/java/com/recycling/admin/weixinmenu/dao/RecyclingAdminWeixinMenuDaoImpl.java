package com.recycling.admin.weixinmenu.dao;

import com.recycling.admin.ds.AdminGenericDaoImpl;
import com.recycling.admin.entity.AdminWeixinMenu;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminWeixinMenuDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Repository("recyclingAdminWeixinMenuDao")
public class RecyclingAdminWeixinMenuDaoImpl extends AdminGenericDaoImpl implements RecyclingAdminWeixinMenuDao {

    /**
     * 添加微信菜单
     * @param adminWeixinMenu
     */
    @Override
    public void addWeixinMenu(AdminWeixinMenu adminWeixinMenu) {
        String sql="insert fansenter_weixin_menu(wxmenu_type,wxmenu_name,wxmenu_key,wxmenu_url,pid,menu_type,update_time,sort) VALUES(?,?,?,?,?,?,?,?);";
        this.getJdbcTemplate().update(
                sql,adminWeixinMenu.getType(),adminWeixinMenu.getName(),adminWeixinMenu.getKey(),
                adminWeixinMenu.getUrl(),adminWeixinMenu.getPid(),adminWeixinMenu.getMenu_type(),adminWeixinMenu.getUpdate_time(),adminWeixinMenu.getSort());
    }

    /**
     * 删除微信菜单
     * @param ids
     */
    @Override
    public void deleteWeixinMenu(String ids) {
        String sql="delete from fansenter_weixin_menu where fansenter_weixin_menu.id in ("+ids+")";
        this.getJdbcTemplate().update(sql);
    }


    /**
     * 更新微信菜单
     * @param adminWeixinMenu
     */
    @Override
    public void updateWeixinMenu(AdminWeixinMenu adminWeixinMenu) {
        String sql="update fansenter_weixin_menu set wxmenu_key=?, wxmenu_name=?, wxmenu_type=?, wxmenu_url=?, pid=?,update_time=?,sort=? where id=?";
        this.getJdbcTemplate().update(sql,
                adminWeixinMenu.getKey(),adminWeixinMenu.getName(),adminWeixinMenu.getType(),
                adminWeixinMenu.getUrl(),adminWeixinMenu.getPid(),adminWeixinMenu.getUpdate_time(),adminWeixinMenu.getSort(),adminWeixinMenu.getId()
        );
    }

    /**
     * 查询微信菜单列表
     * @param menu_type
     * @param pid
     * @param flag
     * @return
     */
    @Override
    public List<AdminWeixinMenu> getAdminWeixinMenuList(String menu_type,int pid,boolean flag) {
        String sql="select id,wxmenu_name,wxmenu_type,wxmenu_key,wxmenu_url,pid,menu_type,update_time,sort from fansenter_weixin_menu menu where menu.menu_type=? AND menu.pid=? ORDER BY menu.sort ASC ";
        List<Map<String, Object>> listMap = this.getJdbcTemplate().queryForList(sql, menu_type, pid);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<AdminWeixinMenu> listMenu=new ArrayList<AdminWeixinMenu>();
        for (Map<String, Object> map:listMap){
            Integer id= (Integer) map.get("id");
            String wxmenu_name= (String) map.get("wxmenu_name");
            String wxmenu_type= (String) map.get("wxmenu_type");
            String wxmenu_key= (String) map.get("wxmenu_key");
            String wxmenu_url= (String) map.get("wxmenu_url");
            Integer wxmenu_pid= (Integer) map.get("pid");
            String wx_menu_type= (String) map.get("menu_type");
            Timestamp update_time= (Timestamp) map.get("update_time");
            Integer sort= (Integer) map.get("sort");
            AdminWeixinMenu menu=new AdminWeixinMenu();
            menu.setId(id);
            menu.setType(wxmenu_type);
            menu.setName(wxmenu_name);
            menu.setKey(wxmenu_key);
            menu.setUrl(wxmenu_url);
            menu.setPid(wxmenu_pid);
            menu.setMenu_type(wx_menu_type);
            menu.setSort(sort);
            menu.setUpdate_time(update_time);
            //如果是一级菜单则查询子菜单
            if (wxmenu_pid==0&&flag==true){
                List<AdminWeixinMenu> sub_listMenu=new ArrayList<AdminWeixinMenu>();
                sub_listMenu=getAdminWeixinMenuList(wx_menu_type, id, flag);
                menu.setSub_button(sub_listMenu);
            }
            listMenu.add(menu);
        }

        return listMenu;
    }

    /**
     * 查询微信菜单
     * @param weixin_menu_id
     * @return
     */
    @Override
    public AdminWeixinMenu getAdminWeixinMenu(int weixin_menu_id) {
        String sql="select id,wxmenu_name,wxmenu_type,wxmenu_key,wxmenu_url,pid,menu_type,update_time,sort from fansenter_weixin_menu menu where menu.id=?";
        List<Map<String, Object>> listMap = this.getJdbcTemplate().queryForList(sql,weixin_menu_id);
        if (listMap == null || listMap.size() == 0)
            return null;
        Map<String, Object> map=listMap.get(0);
        Integer id= (Integer) map.get("id");
        String wxmenu_name= (String) map.get("wxmenu_name");
        String wxmenu_type= (String) map.get("wxmenu_type");
        String wxmenu_key= (String) map.get("wxmenu_key");
        String wxmenu_url= (String) map.get("wxmenu_url");
        Integer wxmenu_pid= (Integer) map.get("pid");
        String menu_type= (String) map.get("menu_type");
        Timestamp update_time= (Timestamp) map.get("udpate_time");
        Integer sort= (Integer) map.get("sort");
        AdminWeixinMenu menu=new AdminWeixinMenu();
        menu.setId(id);
        menu.setName(wxmenu_name);
        menu.setType(wxmenu_type);
        menu.setKey(wxmenu_key);
        menu.setUrl(wxmenu_url);
        menu.setPid(wxmenu_pid);
        menu.setMenu_type(menu_type);
        menu.setUpdate_time(update_time);
        menu.setSort(sort);
        //如果是一级菜单则查询子菜单
        if (wxmenu_pid==0){
            List<AdminWeixinMenu> sub_listMenu=new ArrayList<AdminWeixinMenu>();
            sub_listMenu=getAdminWeixinMenuList(menu_type, id, false);
            menu.setSub_button(sub_listMenu);
        }

        return menu;
    }

    /**
     * 验证微信菜单
     * @param menu_type
     * @param pid
     * @return
     */
    @Override
    public Long validateWeixinMenu(String menu_type, int pid) {
        String sql="SELECT count(menu.id) as size from fansenter_weixin_menu menu where menu.menu_type=? AND menu.pid=?";
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(sql,menu_type,pid);
        if (listMap == null || listMap.size() == 0)
            return 0L;
        Map<String, Object> map=listMap.get(0);
        Long size= (Long) map.get("size");
        return size;
    }
}
