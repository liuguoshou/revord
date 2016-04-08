package com.recycling.admin.system.service;

import com.recycling.admin.auth.dao.AdminRoleDao;
import com.recycling.admin.entity.AdminRole;
import com.recycling.admin.system.dao.AdminSystemRoleDao;
import com.recycling.admin.system.querycase.AdminSystemRoleQueryCase;
import com.recycling.common.util.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Title:LesAdminSystemRoleService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("lesAdminSystemRoleService")
public class AdminSystemRoleServiceImpl implements AdminSystemRoleService {


    @Autowired
    private AdminSystemRoleDao adminSystemRoleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;
    /**
     * 查询用户列表
     * @param queryCase
     * @return
     */
    @Override
    public List<AdminRole> querySystemRole(AdminSystemRoleQueryCase queryCase) {
        List<Long> idList= adminSystemRoleDao.querySystemRole(queryCase);
        if (idList == null || idList.size() == 0)
            return null;
        String ids= CommonUtils.listToString(idList);
        List<AdminRole> adminRoleList=adminRoleDao.queryAdminRole(ids);
        return adminRoleList;
    }

    /**
     * 添加系统角色
     * @param roleName
     * @param super_ids
     * @param sub_ids
     */
    @Override
    public void addSystemRole(String roleName, String super_ids, String sub_ids) {
        adminRoleDao.addAdminRole(roleName, super_ids, sub_ids);
    }

    /**
     * 更新系统角色
     * @param roleId
     * @param roleName
     * @param super_ids
     * @param sub_ids
     */
    @Override
    public void updateSystemRole(Long roleId, String roleName, String super_ids, String sub_ids) {
        adminRoleDao.updateAdminRole(roleId, roleName, super_ids, sub_ids);
    }

    /**
     * 查询角色信息
     * @param roleId
     * @return
     */
    @Override
    public Map<String,Object> querySystemRoleByRoleId(Long roleId) {
        return adminRoleDao.querySystemRoleByRoleId(roleId);
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<AdminRole> querySystemRole() {
        return adminRoleDao.queryAdminRole();
    }

    /**
     * 查询用户的角色ID
     * @param userId
     * @return
     */
    @Override
    public Long queryRoleIdByUserId(Long userId) {
        return adminRoleDao.queryRoleIdByUserId(userId);
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    public void deleteRole(Long roleId) {
        adminRoleDao.deleteUserRole(null,roleId);
        adminRoleDao.deleteRole(roleId);
    }
}
