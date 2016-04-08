package com.recycling.service.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.recycling.common.entity.RecUser;
import com.recycling.common.entity.RecWeixinUser;
import com.recycling.common.enums.UserType;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 用户Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:20:52 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecUserService {

    /**
     * 初始化用户信息及账户信息
     * @param recUser
     * @param recWeixinUser
     * @return
     */
	public Long addRecUser(RecUser recUser,RecWeixinUser recWeixinUser,HttpServletResponse response,HttpServletRequest request);

    /**
     * 根据openId获取用户
     * @param openId
     * @return
     */
    public RecUser getRecUserByOpenId(String openId);
	
	public RecUser getByUserId(Long userId);
	
	public void updateUser(RecUser recUser) throws StaleObjectStateException;
	
	public RecUser getByMobile(String mobile);
	
	public List<RecUser> getUserByRegionId(Long regionId, UserType userType);
	
    /**
     * 获取登录用户信息
     * @author XiaoXian Xu 
     * @param request
     * @return
     */
    public RecUser getLoginUser(HttpServletRequest request);
    
    /**
     * 刷新用户登录信息
     * @author XiaoXian Xu 
     * @param response
     * @param user
     */
    public void refreshUserInfoWithCookie(HttpServletResponse response,RecUser user);
    
    /**
     * 退出登录
     * @author XiaoXian Xu 
     * @param request
     */
    public void userLogout(HttpServletRequest request);
    
    public Map<Long,RecUser> getUserInfoByIds(List<Long> userIdList);

}
