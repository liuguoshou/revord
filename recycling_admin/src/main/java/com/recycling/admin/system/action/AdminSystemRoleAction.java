package com.recycling.admin.system.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.admin.entity.AdminResult;
import com.recycling.admin.entity.AdminRole;
import com.recycling.admin.system.querycase.AdminSystemRoleQueryCase;
import com.recycling.admin.system.service.AdminSystemMenuService;
import com.recycling.admin.system.service.AdminSystemRoleService;
import com.recycling.common.util.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:LesAdminSystemRoleAction.java
 * @Description:系统角色管理action
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class AdminSystemRoleAction extends BaseAdminAction {

    @Autowired
    private AdminSystemRoleService adminSystemRoleService;

    @Autowired
    private AdminSystemMenuService adminSystemMenuService;

    /**
     * 跳转到角色首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toSystemRole.do")
    public String toSystemRole(HttpServletRequest request,HttpServletResponse response){
        return "system/role/list_sys_role";
    }


    /**
     * 查询系统角色列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/querySystemRole.do")
    public String querySystemRole(HttpServletRequest request,HttpServletResponse response){
        AdminSystemRoleQueryCase queryCase=new AdminSystemRoleQueryCase();
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");

        Pager pager = new Pager();
        pager.setStartRow(Integer.parseInt(iDisplayStart));
        pager.setPageSize(Integer.parseInt(iDisplayLength));
        queryCase.setPager(pager);
        List<AdminRole> adminRoleList=null;
        try {
            adminRoleList= adminSystemRoleService.querySystemRole(queryCase);
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", adminRoleList== null ? "":adminRoleList);
        resMap.put("iTotalRecords", pager.getTotalRows());
        resMap.put("iTotalDisplayRecords", pager.getTotalRows());
        resMap.put("iDisplayLength", iDisplayLength);
        resMap.put("iDisplayStart", iDisplayStart);
        String json=JSON.toJSONString(resMap);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.getWriter().print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转向添加角色页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toAddSystemRole.do")
    public String toAddSystemRole(HttpServletRequest request,HttpServletResponse response){
        List<AdminMenus> adminMenusList= adminSystemMenuService.queryAllSystemMenus();
        request.setAttribute("adminMenusList",adminMenusList);
        return "system/role/add_sys_role";
    }

    /**
     * 转向添加角色页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toUpdateSystemRole.do")
    public String toUpdateSystemRole(HttpServletRequest request,HttpServletResponse response){
        String roleId=request.getParameter("roleId");
        if(StringUtils.isBlank(roleId)){
            return "500";
        }
        Map<String,Object> map= adminSystemRoleService.querySystemRoleByRoleId(Long.valueOf(roleId));
        String parent_menu= (String) map.get("super_menu_ids");
        String sub_menu = (String) map.get("sub_menu_ids");
        List<Long> parentList=JSON.parseArray(parent_menu,Long.class);
        List<Long> subList=JSON.parseArray(sub_menu,Long.class);
        map.put("super_menu_ids",parentList);
        map.put("sub_menu_ids",subList);
        List<AdminMenus> adminMenusList= adminSystemMenuService.queryAllSystemMenus();
        request.setAttribute("adminMenusList",adminMenusList);
        request.setAttribute("map",map);
        return "system/role/detail_sys_role";
    }

    /**
     * 保存角色信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/saveSystemRole.do")
    public String saveSystemRole(HttpServletRequest request,HttpServletResponse response){
        String role_name=request.getParameter("role_name");
        String parent_menu=request.getParameter("parent_menu");
        String sub_menu=request.getParameter("sub_menu");
        String method=request.getParameter("method");
        if(StringUtils.isBlank(role_name)){
            return "500";
        }
        if(StringUtils.isNotBlank(parent_menu)){
            parent_menu="[1,"+parent_menu+"]";
        }
        if(StringUtils.isNotBlank(sub_menu)){
            sub_menu="[2,3,4,5,"+sub_menu+"]";
        }

        if(method.equals("save")){
            adminSystemRoleService.addSystemRole(role_name,parent_menu,sub_menu);
        }else if(method.equals("update")){
            String roleId=request.getParameter("role_id");
            if(StringUtils.isBlank(roleId)){
                return "500";
            }
            adminSystemRoleService.updateSystemRole(Long.valueOf(roleId),role_name,parent_menu,sub_menu);
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
     * 查询角色菜单列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryRoleMenusList.do")
    public String queryRoleMenusList(HttpServletRequest request,HttpServletResponse response){

        return null;
    }

    /**
     * 删除角色信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/deleteSystemRole.do")
    public String deleteSystemRole(HttpServletRequest request,HttpServletResponse response){
        String roleId=request.getParameter("roleId");
        if(StringUtils.isBlank(roleId)){
            return "500";
        }
        adminSystemRoleService.deleteRole(Long.valueOf(roleId));
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
