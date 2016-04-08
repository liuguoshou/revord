package com.recycling.admin.common.filter;

import com.recycling.admin.auth.service.AdminAuthService;
import com.recycling.admin.auth.service.AdminUserService;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.admin.entity.AdminUser;
import com.recycling.common.util.WebUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Title:用户登录以及权限过滤器
 * @author:ye.tian
 * @version:v1.0
 */
public class AdminAuthFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		// 加载菜单
		// 加载菜单
		ApplicationContext ac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession()
						.getServletContext());
		//参数设置
		WebUtils.setAttritubeValues(request);
		String requestPath = WebUtils.getRequestPathWithOutParam(request);

		AdminUserService adminUserService = (AdminUserService) ac
				.getBean("adminUserService");
		AdminUser adminUser = adminUserService.isUserLogin(request);
		AdminAuthService adminAuthService = (AdminAuthService) ac
				.getBean("adminAuthService");
		if (adminUser != null) {
			request.setAttribute("loginUser", adminUser);
			AdminMenus am = adminAuthService.isAuthUrl(
					adminUser.getAdminUserId(), requestPath);
			// 没有权限
			if (am == null) {
				response.sendRedirect(request.getContextPath()+"/jsp/noauth.jsp");
				return;
			} else {
				filterChain.doFilter(arg0, arg1);
			}
		} else {
			// 用户为空，看看用户是不是需要登录
			List<AdminMenus> allSubAdminMenus = adminAuthService
					.queryAllSubMenus();
			if (allSubAdminMenus != null && allSubAdminMenus.size() > 0) {
				AdminMenus am = new AdminMenus();
				am.setMenuUrl(requestPath);
				if (allSubAdminMenus.indexOf(am) != -1) {
					AdminMenus amm = allSubAdminMenus.get(allSubAdminMenus
							.indexOf(am));
					// 如果不显示菜单的url 用户是有权限的 否则都是没有权限
					if (amm.getMenuDisplay() != 0) {
						//跳转到登录页面
						response.sendRedirect(request.getContextPath()+"/index.jsp");
						return ;
					}
				}
			}
			filterChain.doFilter(arg0, arg1);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
