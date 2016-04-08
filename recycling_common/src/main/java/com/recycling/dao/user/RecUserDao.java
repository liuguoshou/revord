package com.recycling.dao.user;

import java.util.List;

import com.recycling.common.entity.RecUser;
import com.recycling.common.enums.UserType;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 用户dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:19:45 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecUserDao {
	
	public Long addRecUser(RecUser recUser);
	
	public RecUser getByUserId(Long userId);
	
	public void updateUser(RecUser recUser) throws StaleObjectStateException;
	
	public RecUser getByMobile(String mobile);
	
	/**
	 * 根据用户所属区域和用户类型查询用户列表
	 * @author XiaoXian Xu 
	 *
	 * @param regionId
	 * @param userType
	 * @return
	 */
	public List<RecUser> getUserByRegionId(Long regionId,UserType userType);

    /**
     * 查询用户列表
     * @param ids
     * @return
     */
    public List<RecUser> getUserByIds(String ids);
    /**
     * 根据openId获取用户Id
     * @param openId
     * @return
     */
    public RecUser getRecUserByOpenId(String openId);
}
