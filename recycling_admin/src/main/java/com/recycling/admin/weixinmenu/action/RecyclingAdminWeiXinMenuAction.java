package com.recycling.admin.weixinmenu.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.constants.AdminConstant;
import com.recycling.admin.entity.AdminResult;
import com.recycling.admin.entity.AdminWeixinMenu;
import com.recycling.admin.weixinmenu.MenuUtils;
import com.recycling.common.constants.RecConstants;
import com.recycling.service.weixin.CommonUtils;
import com.recycling.service.weixin.WeixinToken;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:AdminCsbkWeiXinMenuAction.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class RecyclingAdminWeiXinMenuAction extends BaseAdminAction {
    private Logger log = Logger.getLogger(RecyclingAdminWeiXinMenuAction.class);

    /**
     * 查询菜单列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryMenuList.do")
    public String queryMenuList(HttpServletRequest request,HttpServletResponse response){
        String tag=request.getParameter("weixin_type");
        if (StringUtils.isBlank(tag)){
            tag= AdminConstant.WEIXIN_SUBSCRIPT_TEST;
        }
        List<AdminWeixinMenu> menuList=new ArrayList<AdminWeixinMenu>();
        try {
            menuList=recyclingAdminWeixinMenuService.getAdminWeixinMenuList(tag, 0, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("menuList",menuList);
        request.setAttribute("weixin_type",tag);
        return "weixinmenu/list_weixinmenus";
    }


    /**
     * 删除菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/deleteWeixinMenu.do")
    public String deleteWeixinMenu(HttpServletRequest request,HttpServletResponse response){
        String id=request.getParameter("id");
        String pid=request.getParameter("pid");
        if(StringUtils.isBlank(id)||StringUtils.isBlank(pid)){
            return "500";
        }
        try {
            recyclingAdminWeixinMenuService.deleteWeixinMenu(Integer.valueOf(id),Integer.valueOf(pid));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "500";
        }
        AdminResult result = new AdminResult();
        result.setType(AdminResult.SUCCESS);
        result.setMsg("操作成功");
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转到添加菜单页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/addWeixinMenu.do")
    public  String addWeixinMenu(HttpServletRequest request,HttpServletResponse response){
        String tag=request.getParameter("weixin_type");
        if(StringUtils.isBlank(tag)){
            return "500";
        }
        List<AdminWeixinMenu> menuList=new ArrayList<AdminWeixinMenu>();
        try {
            //查询一级菜单
            menuList=recyclingAdminWeixinMenuService.getAdminWeixinMenuList(tag, 0, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("weixin_type",tag);
        request.setAttribute("parent_ment",menuList);
        return "weixinmenu/add_weixinmenu";
    }

    /**
     * 保存和更新菜单操作
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/saveWeixinMenu.do")
    public String saveWeixinMenu(HttpServletRequest request,HttpServletResponse response){

        String name=request.getParameter("menu_name");
        String type=request.getParameter("menu_type");
        String pid=request.getParameter("menu_pid");
        String key=request.getParameter("menu_key");
        String url=request.getParameter("menu_url");
        String weixin_type=request.getParameter("weixin_type");
        String method =request.getParameter("method");
        String sort =request.getParameter("sort");
        AdminWeixinMenu AdminWeixinMenu=new AdminWeixinMenu();
        AdminWeixinMenu.setName(name);
        AdminWeixinMenu.setMenu_type(weixin_type);
        AdminWeixinMenu.setType(type);
        AdminWeixinMenu.setPid(Integer.valueOf(pid));
        AdminWeixinMenu.setKey(key);
        AdminWeixinMenu.setUrl(url);
        AdminWeixinMenu.setUpdate_time(new Timestamp(System.currentTimeMillis()));
        AdminWeixinMenu.setSort(Integer.valueOf(sort));

        try {
            if (method.equals("update")){
                String wx_menu_id=request.getParameter("weixin_id");
                AdminWeixinMenu.setId(Integer.valueOf(wx_menu_id));
                recyclingAdminWeixinMenuService.updateWeixinMenu(AdminWeixinMenu);
            }else{
                recyclingAdminWeixinMenuService.addWeixinMenu(AdminWeixinMenu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }
        AdminResult result = new AdminResult();
        result.setType(AdminResult.SUCCESS);
        result.setMsg("操作成功");
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 跳转到更新菜单页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toUpdateWeixinMenu.do")
    public String toUpdateWeixinMenu(HttpServletRequest request,HttpServletResponse response){

        String weixin_type=request.getParameter("weixin_type");
        String id=request.getParameter("id");

        if(StringUtils.isBlank(weixin_type)||StringUtils.isBlank(id)){
            return  "500";
        }

        AdminWeixinMenu adminWeixinMenu=null;
        try {
            adminWeixinMenu=recyclingAdminWeixinMenuService.getAdminWeixinMenu(Integer.valueOf(id));
            List<AdminWeixinMenu> menuList=new ArrayList<AdminWeixinMenu>();
            if(adminWeixinMenu!=null){
                //查询一级菜单
                menuList=recyclingAdminWeixinMenuService.getAdminWeixinMenuList(weixin_type, 0, false);
            }
            request.setAttribute("weixin_menu",adminWeixinMenu);
            request.setAttribute("parent_menu",menuList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "weixinmenu/detail_weixinmenu";
    }

    /**
     * 生成微信菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/createWeixinMenu.do")
    public String createWeixinMenu(HttpServletRequest request,HttpServletResponse response){
        String weixin_type=request.getParameter("weixin_type");
        if (StringUtils.isBlank(weixin_type)){
            return "500";
        }
        List<AdminWeixinMenu> menuList=new ArrayList<AdminWeixinMenu>();
        //测试账号
        String appID= RecConstants.getWeixinAppkey(weixin_type);
        String appsecret=RecConstants.getWeixinSecret(weixin_type);
        WeixinToken token= CommonUtils.getTokenByAppid(appID, appsecret);
        int create_menu_result=-1;
        try {
            menuList=recyclingAdminWeixinMenuService.getAdminWeixinMenuList(weixin_type, 0, true);
            if(menuList!=null){
                String json=getWeixinBtnJson(menuList);
                log.info("----------/admin/createWeixinMenu.do----------start");
                create_menu_result= MenuUtils.createMenu(json, token.getToken());
                log.info("----------/admin/createWeixinMenu.do----------end:create_menu_result："+create_menu_result);
            }
            AdminResult result=new AdminResult();
            if(create_menu_result==0){
                result.setMsg("微信菜单创建成功！");
                result.setType(AdminResult.SUCCESS);
            }else{
                result.setMsg("微信菜单创建失败！");
                result.setType(AdminResult.ERROR);
            }
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析菜单集合并生成相应的JSON数据
     * @param menuList
     * @return
     */
    private String getWeixinBtnJson(List<AdminWeixinMenu> menuList){
        List<Map<String,Object>> button=new ArrayList<Map<String,Object>>();
        Map<String,Object> btnMap=new HashMap<String,Object>();
        for (AdminWeixinMenu weixinMenu:menuList){
            Map<String,Object> button_Map=new HashMap<String, Object>();
            if(weixinMenu.getSub_button()!=null){
                List<Map<String,Object>> btn_map=new ArrayList<Map<String,Object>>();
                for (AdminWeixinMenu wx_Menu:weixinMenu.getSub_button()){
                    btn_map.add(getMenuMap(wx_Menu));
                }
                button_Map.put("name",weixinMenu.getName());
                button_Map.put("sub_button",btn_map);
            }else{
                button_Map=getMenuMap(weixinMenu);
            }
            button.add(button_Map);
        }
        btnMap.put("button",button);
        String json=JSON.toJSONString(btnMap);
        return json;
    }

    /**
     * 将菜单对象转化成微信菜单Map
     * @param weixinMenu
     * @return
     */
    private Map<String,Object> getMenuMap(AdminWeixinMenu weixinMenu){
        Map<String,Object> button_Map=new HashMap<String, Object>();
        button_Map.put("name",weixinMenu.getName());
        if(weixinMenu.getType().equals("click")){
            button_Map.put("type",weixinMenu.getType());
            button_Map.put("key", weixinMenu.getKey());
        }else{
            button_Map.put("type", weixinMenu.getType());
            button_Map.put("url", weixinMenu.getUrl());
        }

        return button_Map;
    }

    /**
     * 验证微信菜单是否可以添加
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/validateWeixinMenu.do")
    public String validateWeixinMenu(HttpServletRequest request,HttpServletResponse response){
        String weixin_type=request.getParameter("weixin_type");
        String pid=request.getParameter("menu_pid");
        if(StringUtils.isBlank(weixin_type)||StringUtils.isBlank(pid)){
            return "500";
        }
        Long size= 0L;
        int menu_pid=Integer.valueOf(pid);
        boolean flag=true;
        try {
            size = recyclingAdminWeixinMenuService.validateWeixinMenu(weixin_type,menu_pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(menu_pid==0){
            if (size>=3){
                flag=false;
            }
        }else {
            if(size>=5){
                flag=false;
            }
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
