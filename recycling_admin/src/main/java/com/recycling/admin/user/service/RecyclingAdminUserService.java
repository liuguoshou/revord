package com.recycling.admin.user.service;

import com.recycling.admin.user.querycase.RecyclingAdminUserQueryCase;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.entity.RecUser;

import java.util.List;

/**
 * @Title:RecyclingAdminUserService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminUserService {

    /**
     * 查询用户列表
     * @param queryCase
     * @return
     */
    public List<RecUser> queryRecyclingUser(RecyclingAdminUserQueryCase queryCase);

    /**
     * 查询用户详情
     * @param user_id
     * @return
     */
    public RecUser getRecyclingUserById(Long user_id);
}
