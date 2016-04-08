package com.recycling.admin.system.service;

import com.recycling.admin.auth.dao.AdminRoleDao;
import com.recycling.admin.auth.dao.AdminUserDao;
import com.recycling.admin.entity.AdminUser;
import com.recycling.admin.entity.AdminUserArea;
import com.recycling.admin.system.dao.AdminSystemUserDao;
import com.recycling.admin.system.querycase.AdminSystemUserQueryCase;
import com.recycling.common.util.CommonUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title:les_parent
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("lesAdminSystemUserService")
public class AdminSystemUserServiceImpl implements AdminSystemUserService {

    @Autowired
    private AdminSystemUserDao adminSystemUserDao;

    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    /**
     * 查询用户列表
     * @param queryCase
     * @return
     */
    @Override
    public List<AdminUser> querySystemUser(AdminSystemUserQueryCase queryCase) {
        List<Long> idList= adminSystemUserDao.querySystemUser(queryCase);
        if (idList == null || idList.size() == 0)
            return null;
        String ids= CommonUtils.listToString(idList);
        List<AdminUser>  adminUserList=adminUserDao.queryAdminUserList(ids);
        return adminUserList;
    }

    /**
     * 保存用户信息
     * @param adminUser
     * @param roleId
     */
    @Override
    public void addSystemUser(AdminUser adminUser,String roleId) {
        Long userId=adminUserDao.addAdminUser(adminUser.getUserName(), adminUser.getPassword(), adminUser.getRealName(), adminUser.getIsValid(), adminUser.getAddTime());
        if(StringUtils.isNotBlank(roleId)){
            adminRoleDao.addUserRole(userId,Long.valueOf(roleId));
        }
    }

    /**
     * 更细用户信息
     * @param adminUser
     */
    @Override
    public void updateSystemUser(AdminUser adminUser,String roleId) {
        adminUserDao.updateAdminUser(adminUser);
        if(StringUtils.isNotBlank(roleId)){
            adminRoleDao.updateUserRole(adminUser.getAdminUserId(),Long.valueOf(roleId));
        }
    }

    /**
     * 获取用户信息
     * @param user_id
     * @return
     */
    @Override
    public AdminUser detailSystemUser(Long user_id) {
        return adminUserDao.queryAdminUserByUserId(user_id);
    }

    /**
     * 删除系统用户
     * @param user_id
     */
    @Override
    public void deleteSystemUser(Long user_id) {
        adminUserDao.deleteAdminUserById(user_id);
        adminRoleDao.deleteUserRole(user_id,null);
    }

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    @Override
    public Boolean queryAdminUserByUserName(String userName) {
        long total=adminUserDao.queryAdminUserByUserName(userName);
        boolean flag=true;
        if(total>0){
            flag=false;
        }
        return flag;
    }
    
    /**
     * 
     * @param adminUserArea
     */
    public void addSystemUserArea(AdminUserArea adminUserArea) {
    	AdminUserArea aua=adminUserDao.queryAdminUserArea(adminUserArea.getUserId());
    	if(aua == null){
    		adminUserDao.addAdminUserArea(adminUserArea.getUserId(), adminUserArea.getAreaType(), adminUserArea.getAreaIds());
    	}else{
    		adminUserDao.updateAdminUserArea(adminUserArea);
    	}
       
    }

    
	
    
}
