package com.recycling.admin.auth.dao;

import com.recycling.admin.entity.AdminUser;
import com.recycling.admin.entity.AdminUserArea;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Title:AdminUserDao.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public interface AdminUserDao {
	
	/**
	 * 增加一条后台用户信息
	 * @param username
	 * @param password
	 * @param realName
	 * @param isValid
	 * @param addTime
	 */
	public Long addAdminUser(String username, String password, String realName,
                             Integer isValid, Timestamp addTime);
	
	/**
	 * 查询登录用户
	 */
	public AdminUser queryAdminUser(String userName, String password);
	
	/**
	 * 用户id查询用户 正常用户
	 * @param userId
	 * @return
	 */
	public AdminUser queryAdminUserById(Long userId);
	
	
	/**
	 * 查询后台用户列表
	 * @param ids
	 * @return
	 */
	public List<AdminUser> queryAdminUserList(String ids);

    /**
     * 更新用户信息
     * @param adminUser
     */
    public void updateAdminUser(AdminUser adminUser);

    /**
     * 用户ID查询用户
     * @param userId
     * @return
     */
    public AdminUser queryAdminUserByUserId(Long userId);

    /**
     * 删除用户
     * @param userId
     */
    public void deleteAdminUserById(Long userId);

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    public Long queryAdminUserByUserName(String userName);
    
    /**
     * 
     * 添加一条用户查看范围记录
     * @param userId
     * @param areaType
     * @param areaIds
     * @return
     */
	public Long addAdminUserArea(int userId, int areaType, String areaIds) ;
	/**
	 * 查询出用户的查询范围
	 * @param userId
	 * @return
	 */
    public AdminUserArea queryAdminUserArea(int userId ) ;
    
    /**
     * 修改用户查询范围
     * @param userArea
     */
    public void updateAdminUserArea(AdminUserArea userArea);
}
