package com.recycling.admin.system.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.constants.AdminConstant;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.admin.entity.AdminResult;
import com.recycling.admin.system.querycase.AdminSystemMenuQueryCase;
import com.recycling.admin.system.service.AdminSystemMenuService;
import com.recycling.common.util.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Title:LesAdminSystemMenuAction.java
 * @Description:系统菜单管理action
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class AdminSystemMenuAction extends BaseAdminAction {

    @Autowired
    private AdminSystemMenuService adminSystemMenuService;

    /**
     * 转向系统菜单首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toSystemMenu.do")
    public String toSystemMenu(HttpServletRequest request,HttpServletResponse response){
        return "redirect:/admin/querySystemMenu.do";
    }


    /**
     * 查询系统菜单列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/querySystemMenu.do")
    public String querySystemMenu(HttpServletRequest request,HttpServletResponse response){
        AdminSystemMenuQueryCase queryCase=new AdminSystemMenuQueryCase();
        String cpage = request.getParameter("cpage");
        if (StringUtils.isBlank(cpage)) {
            cpage = "1";
        }
        Pager pager = new Pager();
        pager.setCurrentPage(Integer.parseInt(cpage));
        pager.setPageSize(AdminConstant.PAGE_SIZE);
        queryCase.setPager(pager);

        List<AdminMenus> adminMenusList=null;
        try {
            adminMenusList= adminSystemMenuService.querySystemMenus(queryCase);
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }
        request.setAttribute("sysMenuList",adminMenusList);
        return "system/menu/list_sys_menu";
    }

    /**
     * 转向添加系统菜单页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toAddSystemMenu.do")
    public String toAddSystemMenu(HttpServletRequest request,HttpServletResponse response){
        List<AdminMenus> adminMenusList= adminSystemMenuService.queryParentMenus();
        request.setAttribute("adminMenusList",adminMenusList);
        return "system/menu/add_sys_menu";
    }

    /**
     * 转向更新系统菜单页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toUpdateSystemMenu.do")
    public String toUpdateSystemMenu(HttpServletRequest request,HttpServletResponse response){
        String menu_id=request.getParameter("menu_id");
        if(StringUtils.isBlank(menu_id)){
            return "500";
        }
        AdminMenus adminMenus= adminSystemMenuService.queryAdminMenusById(Long.valueOf(menu_id));
        List<AdminMenus> adminMenusList= adminSystemMenuService.queryParentMenus();
        request.setAttribute("adminMenusList",adminMenusList);
        request.setAttribute("adminMenus",adminMenus);
        return "system/menu/detail_sys_menu";
    }

    /**
     * 保存系统菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/saveSystemMenu.do")
    public String saveSystemMenu(HttpServletRequest request,HttpServletResponse response){

        String menu_name=request.getParameter("menu_name");
        String menu_url=request.getParameter("menu_url");
        String parentMenu=request.getParameter("parentMenu");
        String menu_isvalid=request.getParameter("menu_isvalid");
        String menu_isdisplay=request.getParameter("menu_isdisplay");
        String method=request.getParameter("method");

        if(StringUtils.isBlank(menu_name)||StringUtils.isBlank(menu_url)||StringUtils.isBlank(parentMenu)||StringUtils.isBlank(menu_isvalid)
                ||StringUtils.isBlank(menu_isdisplay)||StringUtils.isBlank(method)){
            return "500";
        }
        AdminMenus adminMenus=new AdminMenus();
        adminMenus.setMenuName(menu_name);
        adminMenus.setMenuUrl(menu_url);
        adminMenus.setParentId(Long.valueOf(parentMenu));
        adminMenus.setIsValid(Integer.valueOf(menu_isvalid));
        adminMenus.setMenuDisplay(Integer.valueOf(menu_isdisplay));
        if(method.equals("save")){
            adminSystemMenuService.addSystemMenus(adminMenus);
        }else if (method.equals("update")){
            String menu_id=request.getParameter("menu_id");
            if(StringUtils.isBlank(menu_id)){
                return "500";
            }
            adminMenus.setMenuId(Long.valueOf(menu_id));
            adminSystemMenuService.updateSystemMenus(adminMenus);
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
     * 删除系统菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/deleteSystemMenu.do")
    public String deleteSystemMenu(HttpServletRequest request,HttpServletResponse response){
        String menu_id=request.getParameter("menu_id");
        if (StringUtils.isBlank(menu_id)){
            return "500";
        }
        adminSystemMenuService.deleteSystemMenus(Long.valueOf(menu_id));
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

}
