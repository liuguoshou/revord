package com.recycling.admin.common.action;

import com.recycling.admin.auth.service.AdminAuthService;
import com.recycling.admin.auth.service.AdminUserService;
import com.recycling.admin.constants.AdminConstant;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.admin.entity.AdminUser;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Title:后台用户Action
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Controller
public class AdminUserAction {
	
	@RequestMapping("/redirect/goUrl.do")
	public String redirectUrl(HttpServletRequest request){
		
		String url=request.getParameter("url");
		
		return url;
	}

	@Autowired
	private AdminUserService adminUserService;

	@RequestMapping("/adminuser/userLogin.do")
	public String userLogin(HttpServletRequest request,HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String passwd = request.getParameter("passwd");
		if(StringUtils.isBlank(userName)||StringUtils.isBlank(passwd)){
			request.setAttribute("errMsg", "用户名或者密码不能为空!");
			return "../index";
		}
		AdminUser au = adminUserService.userLogin(userName, passwd, response);
		if (au == null) {
			request.setAttribute("errMsg", "用户名或者密码不正确!");
			return "../index";
		}

        ServletContext sc = request.getSession().getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        AdminAuthService adminAuthService = (AdminAuthService) ac.getBean("adminAuthService");
        List<AdminMenus> listMenus = adminAuthService.queryUserAdminMenus(au.getAdminUserId());
        for (AdminMenus adminMenus:listMenus){
            adminMenus.setMenuIcon(AdminConstant.iconMap.get(adminMenus.getMenuId()));
        }
        sc.setAttribute("loginUser", au);
        if (listMenus != null && listMenus.size() > 0) {
            sc.setAttribute(AdminConstant.CONTEXT_MENUS, listMenus);
        }
		return "main";
	}
	
	@RequestMapping("/adminuser/userLogout.do")
	public String userLogOut(HttpServletRequest request,HttpServletResponse response){
		
		adminUserService.userLogout(request);
		return "../index";
	}
}
