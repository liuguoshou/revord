package com.recycling.admin.auth.service;

import com.recycling.admin.auth.dao.AdminUserDao;
import com.recycling.admin.constants.AdminConstant;
import com.recycling.admin.entity.AdminUser;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.common.util.MobilePurseSecurityUtils;
import com.recycling.common.util.WebUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title:AdminUserServiceImpl.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	private AdminUserDao adminUserDao;

	private MemCacheService mem = MemCacheServiceImpl.getInstance();

	@Override
	public AdminUser userLogin(String userName, String password,
			HttpServletResponse response) {
		AdminUser adminUser = adminUserDao.queryAdminUser(userName,
				MobilePurseSecurityUtils.secrect(password,
						RecConstants.USER_PASSWORD_KEY));
		if (adminUser != null) {

			mem.set(AdminConstant.USER_ADMIN_LOGIN_KEY
					+ adminUser.getAdminUserId(), adminUser);
			// cookie为 加密后用户id 存储客户端
			String cookieSecret = MobilePurseSecurityUtils.secrect(
					adminUser.getAdminUserId() + "",
					RecConstants.USER_PASSWORD_KEY);
			Cookie cookie = WebUtils.cookie(
					AdminConstant.USER_ADMIN_LOGIN_COOKIE_KEY, cookieSecret,
					24 * 30 * 60 * 60, null);
			response.addCookie(cookie);
		}
		return adminUser;
	}

	@Override
	public AdminUser isUserLogin(HttpServletRequest request) {
		String cookies = WebUtils.getCookieValue(
				AdminConstant.USER_ADMIN_LOGIN_COOKIE_KEY, request);
		if (StringUtils.isBlank(cookies))
			return null;
		String userIds = MobilePurseSecurityUtils.decryption(cookies,
				RecConstants.USER_PASSWORD_KEY);
		if (StringUtils.isBlank(userIds))
			return null;

		AdminUser au = (AdminUser) mem.get(AdminConstant.USER_ADMIN_LOGIN_KEY
				+ userIds);

		return au;
	}

	@Override
	public void userLogout(HttpServletRequest request) {
		String cookies = WebUtils.getCookieValue(
				AdminConstant.USER_ADMIN_LOGIN_COOKIE_KEY, request);
		if (StringUtils.isNotBlank(cookies)) {
			String userIds = MobilePurseSecurityUtils.decryption(cookies,
					RecConstants.USER_PASSWORD_KEY);
			if (StringUtils.isNotBlank(userIds)) {
				mem.remove(AdminConstant.USER_ADMIN_LOGIN_KEY + userIds);
			}
			WebUtils.removeableCookie(AdminConstant.USER_ADMIN_LOGIN_COOKIE_KEY);
		}

	}
}
