package com.recycling.admin.common.filter;

import com.recycling.admin.auth.service.AdminAuthService;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.common.util.WebUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @Title:菜单过滤器
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class AdminMenuFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		ApplicationContext ac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(request.getSession()
						.getServletContext());

		String requestPath = WebUtils.getRequestPathWithOutParam(request);
		
		AdminAuthService adminAuthService=(AdminAuthService) ac.getBean("adminAuthService");
		
		List<AdminMenus>  listSubMenu=adminAuthService.queryParentOfSubMenuBySubPath(requestPath);
		if(listSubMenu!=null&&listSubMenu.size()>0){
			Long parentId=listSubMenu.get(0).getParentId();
			request.setAttribute("currentParentId", parentId);
		}
		request.setAttribute("subMenuList", listSubMenu);
		request.setAttribute("requestPath", requestPath);
		//设置查询参数 到request里
		WebUtils.setAttritubeValues(request);
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		
	}

}
