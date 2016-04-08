package com.recycling.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecUser;
import com.recycling.common.util.DomainUtils;
import com.recycling.common.util.WebUtils;
import com.recycling.service.beggar.RecBeggarService;
import com.recycling.service.user.RecUserService;

/**
 * Description : 废品回收登录拦截器 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-30 下午12:44:46 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecUserLoginInteceptor implements HandlerInterceptor {

	private List<String> excludedUrls;// 免登录路径

	private RecUserService recUserService;
	
	private RecBeggarService recBeggarService;
	
	private DomainUtils domainUtils;
	
	public void setDomainUtils(DomainUtils domainUtils) {
		this.domainUtils = domainUtils;
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	public void setRecUserService(RecUserService recUserService) {
		this.recUserService = recUserService;
	}

	public void setRecBeggarService(RecBeggarService recBeggarService) {
		this.recBeggarService = recBeggarService;
	}

	private static final String USER_LOGIN_URL = "/weixin/su.do?goType=user_index";
	
	private static final String BEGGAR_LOGIN_URL = "/weixin/su.do?goType=init_login";
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		// 设置请求参数
		String source = WebUtils.getRequestPath(request);
		request.setAttribute("source", source);
		WebUtils.setAttritubeValues(request);
		// 如果当前访问的是公用路径 直接放过
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (requestUri.indexOf(contextPath) != -1) {
			requestUri = requestUri.substring(requestUri.indexOf(contextPath)
					+ contextPath.length());
		}
		if (excludedUrls.contains(requestUri)) {
			return true;
		}
		
	System.out.println(requestUri);
		if(requestUri.startsWith("/user")){
			RecUser user = recUserService.getLoginUser(request);
			if(null == user){
				response.sendRedirect(domainUtils.getDomainUrl(null, USER_LOGIN_URL));
			}
		}else if(requestUri.startsWith("/collect")){
			RecBeggar beggar = recBeggarService.getLoginBeggar(request);
			if(null == beggar){
				response.sendRedirect(domainUtils.getDomainUrl(null, BEGGAR_LOGIN_URL));
			}
		}
		return true;
	}

}
