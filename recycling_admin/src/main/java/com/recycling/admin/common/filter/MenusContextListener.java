package com.recycling.admin.common.filter;

import com.recycling.admin.auth.service.AdminAuthService;
import com.recycling.admin.constants.AdminConstant;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * @Title:启动加载所有菜单
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class MenusContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		MemCacheService mem = MemCacheServiceImpl.getInstance();

		ServletContext sc = arg0.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sc);
		AdminAuthService adminAuthService = (AdminAuthService) ac
				.getBean("adminAuthService");
		List<AdminMenus> listMenus = adminAuthService
				.queryAllDisplayAdminMenus();
		if (listMenus != null && listMenus.size() > 0) {
			sc.setAttribute(AdminConstant.CONTEXT_MENUS, listMenus);
		}

	}

}
