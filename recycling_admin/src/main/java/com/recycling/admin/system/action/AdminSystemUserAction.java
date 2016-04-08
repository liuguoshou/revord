package com.recycling.admin.system.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.area.service.RecyclingAdminAreaService;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.entity.AdminResult;
import com.recycling.admin.entity.AdminRole;
import com.recycling.admin.entity.AdminUser;
import com.recycling.admin.entity.AdminUserArea;
import com.recycling.admin.region.service.RecyclingAdminRegionService;
import com.recycling.admin.system.querycase.AdminSystemUserQueryCase;
import com.recycling.admin.system.service.AdminSystemRoleService;
import com.recycling.admin.system.service.AdminSystemUserService;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.util.MobilePurseSecurityUtils;
import com.recycling.common.util.Pager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:LesAdminSystemUserAction.java
 * @Description:系统用户管理action
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class AdminSystemUserAction extends BaseAdminAction {


    @Autowired
    private AdminSystemUserService adminSystemUserService;

    @Autowired
    private AdminSystemRoleService adminSystemRoleService;
    
    @Autowired
    private RecyclingAdminRegionService recyclingAdminRegionService;
    
    @Autowired
    private RecyclingAdminAreaService recyclingAdminAreaService;

    /**
     * 转向用户首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toSystemUser.do")
    public String toSystemUser(HttpServletRequest request,HttpServletResponse response){
        return "system/user/list_sys_user";
    }


    /**
     * 查询系统用户列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/querySystemUser.do")
    public String querySystemUser(HttpServletRequest request,HttpServletResponse response){
        AdminSystemUserQueryCase queryCase=new AdminSystemUserQueryCase();
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        Pager pager = new Pager();
        pager.setStartRow(Integer.parseInt(iDisplayStart));
        pager.setPageSize(Integer.parseInt(iDisplayLength));
        queryCase.setPager(pager);
        System.out.println("更新成功~~~~~~~~~~~~~~~~~~~~~~~！");
        List<AdminUser> adminUserList=null;
        try {
            adminUserList= adminSystemUserService.querySystemUser(queryCase);
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", adminUserList== null ? "":adminUserList );
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
     * 转向添加用户页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toAddSystemUser.do")
    public String toAddSystemUser(HttpServletRequest request,HttpServletResponse response){
        List<AdminRole> adminRoleList= adminSystemRoleService.querySystemRole();
        request.setAttribute("adminRoleList",adminRoleList);
        return "system/user/add_sys_user";
    }

    /**
     * 转向修改用户页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toUpdateSystemUser.do")
    public String toUpdateSystemUser(HttpServletRequest request,HttpServletResponse response){
        String user_id=request.getParameter("user_id");
        if(StringUtils.isBlank(user_id)){
            return "500";
        }
        AdminUser adminUser= adminSystemUserService.detailSystemUser(Long.valueOf(user_id));
        adminUser.setPassword(MobilePurseSecurityUtils.decryption(adminUser.getPassword(),RecConstants.USER_PASSWORD_KEY));
        List<AdminRole> adminRoleList= adminSystemRoleService.querySystemRole();
        Long roleId= adminSystemRoleService.queryRoleIdByUserId(Long.valueOf(user_id));
        request.setAttribute("adminRoleList",adminRoleList);
        request.setAttribute("adminUser",adminUser);
        request.setAttribute("roleId",roleId);
        return "system/user/detail_sys_user";
    }

    /**
     * 转向修改用户页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toUpdateSystemUserArea.do")
    public String toUpdateSystemUserArea(HttpServletRequest request,HttpServletResponse response){
        String user_id=request.getParameter("user_id");
        if(StringUtils.isBlank(user_id)){
            return "500";
        }
        //start 地域信息
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        List <RecArea>recAreaList = recyclingAdminAreaService.getActiveByParentId((new Long("0")));
        request.setAttribute("recAreaList",recAreaList);
        request.setAttribute("recRegionList",recRegionList);
       
        //end 地域信息
        
        AdminUser adminUser= adminSystemUserService.detailSystemUser(Long.valueOf(user_id));
        adminUser.setPassword(MobilePurseSecurityUtils.decryption(adminUser.getPassword(),RecConstants.USER_PASSWORD_KEY));
        List<AdminRole> adminRoleList= adminSystemRoleService.querySystemRole();
        Long roleId= adminSystemRoleService.queryRoleIdByUserId(Long.valueOf(user_id));
        request.setAttribute("adminRoleList",adminRoleList);
        request.setAttribute("adminUser",adminUser);
        request.setAttribute("roleId",roleId);
        return "system/user/add_sys_user_area";
    }
    
    
    
    	 /**
    	  * 保存用户权限
    	  * @param request
    	  * @param response
    	  * @return
    	  */
        @RequestMapping("/admin/saveSystemUserArea.do")
        public String saveSystemUserAre(HttpServletRequest request,HttpServletResponse response){
        	
        	String user_id=request.getParameter("user_id");										//用户Id
            String user_area_all=request.getParameter("user_area_all");								//是否为所有权限
            String rec_user_area_privenc_id=request.getParameter("rec_user_area_privenc_id");		//省Id
            String rec_user_area_city_id=request.getParameter("rec_user_area_city_id");				//市
            String rec_user_area_district_id=request.getParameter("rec_user_area_district_id");		//区
            String rec_user_area_street_id=request.getParameter("rec_user_area_street_id");			//街道
            String userAreaIds	 = request.getParameter("userAreaIds");				//小区Id
            
            
            
            String method=request.getParameter("method");
            
            if(StringUtils.isBlank(user_id)){
                return "500";
            }
            
            
            if(!"on".equals(user_area_all) && StringUtils.isBlank(rec_user_area_privenc_id)){ //未选中任务内容
                return "500";
            }
            
            
            AdminUserArea userArea = new AdminUserArea();
            userArea.setUserId(Integer.parseInt(user_id));
            userArea.setUserId(Integer.parseInt(user_id));
            
            if("on".equals(user_area_all)){			//拥有最高权限
            	userArea.setAreaType(0);
            	userArea.setAreaIds("0");
            }else if(StringUtils.isNotBlank(userAreaIds)){ //小区权限
            	userArea.setAreaType(5);
            	userArea.setAreaIds(userAreaIds);
            }else if(StringUtils.isNotBlank(rec_user_area_street_id)){ //街道权限
            	userArea.setAreaType(4);
            	userArea.setAreaIds(rec_user_area_street_id);
            }else if(StringUtils.isNotBlank(rec_user_area_district_id)){ //地区权限
            	userArea.setAreaType(3);
            	userArea.setAreaIds(rec_user_area_district_id);
            }else if(StringUtils.isNotBlank(rec_user_area_city_id)){ //市权限
            	userArea.setAreaType(2);
            	userArea.setAreaIds(rec_user_area_city_id);
            }else if(StringUtils.isNotBlank(rec_user_area_privenc_id)){ //省权限
            	userArea.setAreaType(1);
            	userArea.setAreaIds(rec_user_area_privenc_id);
            }
            	
            adminSystemUserService.addSystemUserArea(userArea);
            
            System.out.println(user_id);
            /*
            if(StringUtils.isBlank(user_name)||StringUtils.isBlank(user_realname)||StringUtils.isBlank(user_pwd)
                    ||StringUtils.isBlank(user_isvalid)||StringUtils.isBlank(method)){
                return "500";
            }
            AdminUser adminUser=new AdminUser();
            adminUser.setUserName(user_name);
            adminUser.setRealName(user_realname);
            adminUser.setPassword(MobilePurseSecurityUtils.secrect(user_pwd, RecConstants.USER_PASSWORD_KEY));
            adminUser.setIsValid(Integer.parseInt(user_isvalid));
            if(method.equals("save")){
                adminUser.setAddTime(new Timestamp(System.currentTimeMillis()));
                adminSystemUserService.addSystemUser(adminUser,role_id);
            }else if (method.equals("update")){
                String user_id=request.getParameter("user_id");
                if(StringUtils.isBlank(user_id)){
                    return "500";
                }
                adminUser.setAdminUserId(Long.valueOf(user_id));
                adminSystemUserService.updateSystemUser(adminUser,role_id);
            }*/
        	
        	
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
     * 保存用户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/saveSystemUser.do")
    public String saveSystemUser(HttpServletRequest request,HttpServletResponse response){
        String user_name=request.getParameter("user_name");
        String user_realname=request.getParameter("user_realname");
        String user_pwd=request.getParameter("user_pwd");
        String user_isvalid=request.getParameter("user_isvalid");
        String role_id=request.getParameter("role");
        String method=request.getParameter("method");

        if(StringUtils.isBlank(user_name)||StringUtils.isBlank(user_realname)||StringUtils.isBlank(user_pwd)
                ||StringUtils.isBlank(user_isvalid)||StringUtils.isBlank(method)){
            return "500";
        }
        AdminUser adminUser=new AdminUser();
        adminUser.setUserName(user_name);
        adminUser.setRealName(user_realname);
        adminUser.setPassword(MobilePurseSecurityUtils.secrect(user_pwd, RecConstants.USER_PASSWORD_KEY));
        adminUser.setIsValid(Integer.parseInt(user_isvalid));
        if(method.equals("save")){
            adminUser.setAddTime(new Timestamp(System.currentTimeMillis()));
            adminSystemUserService.addSystemUser(adminUser,role_id);
        }else if (method.equals("update")){
            String user_id=request.getParameter("user_id");
            if(StringUtils.isBlank(user_id)){
                return "500";
            }
            adminUser.setAdminUserId(Long.valueOf(user_id));
            adminSystemUserService.updateSystemUser(adminUser,role_id);
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
     * 删除系统用户
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/deleteSystemUser.do")
    public String deleteSystemUser(HttpServletRequest request,HttpServletResponse response){
        String user_id=request.getParameter("user_id");
        if(StringUtils.isBlank(user_id)){
            return "500";
        }
        adminSystemUserService.deleteSystemUser(Long.valueOf(user_id));
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
     * 根据用户名查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/querySystemUserByUserName.do")
    public String querySystemUserByUserName(HttpServletRequest request,HttpServletResponse response){
        String userName=request.getParameter("user_name");
        if(StringUtils.isBlank(userName)){
            return "500";
        }
        Boolean flag= adminSystemUserService.queryAdminUserByUserName(userName);
        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
