package com.recycling.admin.auth.service;

import com.recycling.admin.entity.AdminUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title:用户服务
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public interface AdminUserService {
	
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public AdminUser userLogin(String userName, String password, HttpServletResponse response);
	
	/**
	 * 判断当前用户是否登录
	 * @param response
	 * @return
	 */
	public AdminUser   isUserLogin(HttpServletRequest request);
	
	/**
	 * 用户退出登录
	 * 清除cookie jedisCache
	 * @param request
	 */
	public void  userLogout(HttpServletRequest request);


}
