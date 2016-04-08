package com.recycling.dao.user;

import com.recycling.common.entity.RecWeixinUser;

/**
 * @Title:RecUserWeixinDao.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecWeixinUserDao {

    /**
     * 添加用户微信信息
     * @param recWeixinUser
     * @return
     */
    public Long addRecUserWeixin(RecWeixinUser recWeixinUser);
}
