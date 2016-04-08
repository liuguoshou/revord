package com.recycling.admin.weixinmenu.serivce;

import com.recycling.admin.entity.AdminWeixinMenu;
import com.recycling.admin.weixinmenu.dao.RecyclingAdminWeixinMenuDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title:CsbkWeixinMenuServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("csbkWeixinMenuService")
public class RecyclingAdminWeixinMenuServiceImpl implements RecyclingAdminWeixinMenuService
{

    private Logger log = Logger.getLogger(RecyclingAdminWeixinMenuServiceImpl.class);

    @Autowired
    private RecyclingAdminWeixinMenuDao recyclingAdminWeixinMenuDao;


    /**
     * 添加微信菜单
     * @param adminWeixinMenu
     */
    @Override
    public void addWeixinMenu(AdminWeixinMenu adminWeixinMenu) {
        recyclingAdminWeixinMenuDao.addWeixinMenu(adminWeixinMenu);
    }


    /**
     * 删除微信菜单
     * @param weixin_menu_id
     */
    @Override
    public void deleteWeixinMenu(int weixin_menu_id,int pid) {
        StringBuilder stringBuilder=new StringBuilder();
        if(pid==0){
            AdminWeixinMenu aminWeixinMenu=getAdminWeixinMenu(weixin_menu_id);
            int size=aminWeixinMenu.getSub_button().size();
            for (int i = 0; i < size ; i++) {
                stringBuilder.append(aminWeixinMenu.getSub_button().get(i).getId());
                stringBuilder.append(",");
            }
            stringBuilder.append(weixin_menu_id);
        }else{
            stringBuilder.append(weixin_menu_id);
        }

        recyclingAdminWeixinMenuDao.deleteWeixinMenu(stringBuilder.toString());
    }


    /**
     * 更新微信菜单
     * @param adminWeixinMenu
     */
    @Override
    public void updateWeixinMenu(AdminWeixinMenu adminWeixinMenu) {
        recyclingAdminWeixinMenuDao.updateWeixinMenu(adminWeixinMenu);
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
        return recyclingAdminWeixinMenuDao.getAdminWeixinMenuList(menu_type, pid, flag);
    }

    /**
     * 查询微信菜单
     * @param weixin_menu_id
     * @return
     */
    @Override
    public AdminWeixinMenu getAdminWeixinMenu(int weixin_menu_id) {
        return recyclingAdminWeixinMenuDao.getAdminWeixinMenu(weixin_menu_id);
    }

    /**
     * 验证微信菜单
     * @param menu_type
     * @param pid
     * @return
     */
    @Override
    public Long validateWeixinMenu(String menu_type, int pid) {
        return recyclingAdminWeixinMenuDao.validateWeixinMenu( menu_type,  pid);
    }
}
