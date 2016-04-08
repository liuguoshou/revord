package com.recycling.service.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.recycling.common.entity.RecWeixinUser;
import com.recycling.dao.user.RecWeixinUserDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecAccount;
import com.recycling.common.entity.RecUser;
import com.recycling.common.enums.AccountStatus;
import com.recycling.common.enums.UserType;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.common.util.MobilePurseSecurityUtils;
import com.recycling.common.util.StringUtil;
import com.recycling.common.util.WebUtils;
import com.recycling.dao.account.RecAccountDao;
import com.recycling.dao.user.RecUserDao;

@Service("recUserService")
public class RecUserServiceImpl implements RecUserService{

	private MemCacheService mem = MemCacheServiceImpl.getInstance();

	@Autowired
	private RecUserDao recUserDao;
	
	@Autowired
	private RecAccountDao recAccountDao;

    @Autowired
    private RecWeixinUserDao recWeixinUserDao;

    /**
     * 初始化用户信息及账户信息
     * @param recUser
     * @param recWeixinUser
     * @return
     */
	@Override
	public Long addRecUser(RecUser recUser,RecWeixinUser recWeixinUser,HttpServletResponse response,HttpServletRequest request) {
        Long userId = null;
        String openId=recWeixinUser.getOpenId();
        RecUser user= null;
        if(StringUtils.isBlank(openId)){
            return null;
        }else {
            user=getLoginUser(request);
            if(user == null){
                user=recUserDao.getRecUserByOpenId(openId);
            }
        }
		if(user == null){
            recUser.setUserType(UserType.USER);
            recUser.setAreaId(101L);
            recUser.setRegionId(0L);
            userId = recUserDao.addRecUser(recUser);
            recUser.setUserId(userId);
            refreshUserInfoWithCookie(response,recUser);
            RecAccount recAccount = new RecAccount();
            recAccount.setUserId(userId);
            recAccount.setAccountStatus(AccountStatus.FROZEN);
            recAccountDao.addAccount(recAccount);
            if(recWeixinUser != null){
                recWeixinUser.setUserId(userId);
                recWeixinUserDao.addRecUserWeixin(recWeixinUser);
            }
        }else{
            userId=user.getUserId();
            refreshUserInfoWithCookie(response,user);
        }
		return userId;
	}

    /**
     * 根据openId获取用户
     * @param openId
     * @return
     */
    @Override
    public RecUser getRecUserByOpenId(String openId) {
        return recUserDao.getRecUserByOpenId(openId);
    }

    @Override
	public RecUser getByUserId(Long userId) {
		return recUserDao.getByUserId(userId);
	}

	@Override
	public void updateUser(RecUser recUser) throws StaleObjectStateException {
		recUserDao.updateUser(recUser);
	}

	@Override
	public RecUser getLoginUser(HttpServletRequest request) {
		String value = WebUtils.getCookieValue(RecConstants.USER_LOGIN_KEY, request);
		if (StringUtils.isBlank(value)){
			return null;
		}

		String userIds = MobilePurseSecurityUtils.decryption(value,
				RecConstants.USER_PASSWORD_KEY);

		if (StringUtils.isBlank(userIds)){
			return null;
		}

		RecUser user = (RecUser) mem.get(RecConstants.USER_DETAIL + userIds);
		return user;
	}

	@Override
	public void refreshUserInfoWithCookie(HttpServletResponse response,RecUser user) {
		if(null== user || null == user.getUserId()){
			return;
		}
		String cookieSecret = MobilePurseSecurityUtils.secrect(user.getUserId() + "",
				RecConstants.USER_PASSWORD_KEY);
		Cookie cookie = WebUtils.cookie(RecConstants.USER_LOGIN_KEY,
					cookieSecret, RecConstants.cache_time);
		response.addCookie(cookie);
		
		user = getByUserId(user.getUserId());
		mem.set(RecConstants.USER_DETAIL + user.getUserId(), user,RecConstants.cache_time);
	
	}

	@Override
	public void userLogout(HttpServletRequest request) {
		String value = WebUtils.getCookieValue(
				RecConstants.USER_LOGIN_KEY, request);
		if (StringUtils.isNotBlank(value)) {
			String userIds = MobilePurseSecurityUtils.decryption(value,
					RecConstants.USER_PASSWORD_KEY);
			mem.remove(RecConstants.USER_DETAIL + userIds);
		}

		WebUtils.removeableCookie(RecConstants.USER_LOGIN_KEY);
	}

	@Override
	public RecUser getByMobile(String mobile) {
		return recUserDao.getByMobile(mobile);
	}

	@Override
	public List<RecUser> getUserByRegionId(Long regionId, UserType userType) {
		return recUserDao.getUserByRegionId(regionId, userType);
	}

	@Override
	public Map<Long, RecUser> getUserInfoByIds(List<Long> userIdList) {
		if(null == userIdList || userIdList.isEmpty()){
			return Collections.emptyMap();
		}
		String userIds = StringUtil.arrayToString(userIdList.toArray(), ",");
		List<RecUser> userList = recUserDao.getUserByIds(userIds);
		if(null != userList && userList.size() > 0){
			Map<Long,RecUser> userMap = new HashMap<Long,RecUser>();
			for (RecUser recUser : userList) {
				userMap.put(recUser.getUserId(), recUser);
			}
			return userMap;
		}
		return Collections.emptyMap();
	}

}
