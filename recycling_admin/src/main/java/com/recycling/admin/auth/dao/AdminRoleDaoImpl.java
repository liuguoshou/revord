package com.recycling.admin.auth.dao;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.ds.AdminGenericDaoImpl;
import com.recycling.admin.entity.AdminRole;
import com.recycling.common.exception.SimpleException;
import com.recycling.common.util.CommonUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

/**
 * @Title:AdminRoleDaoImpl.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Repository(value = "adminRoleDao")
public class AdminRoleDaoImpl extends AdminGenericDaoImpl implements AdminRoleDao {


	@Override
	public Set<Long> getSubAdminMenuListByIds(String ids) {
		String sql = "select sub_menu_ids from fansenter_admin_role where admin_role_id in ("+ids+")";
		List<Map<String,Object>> listMap=this.getJdbcTemplate().queryForList(sql);
		if(listMap==null||listMap.size()==0)return null;
		Set<Long> set=new HashSet<Long>();
		for (Map<String, Object> map : listMap) {
			String json=(String) map.get("sub_menu_ids");
			if(StringUtils.isNotBlank(json)){
				List<Long> li=JSON.parseArray(json, Long.class);
				set.addAll(li);
			}
		}
		return set;
	}

	@Override
	public Set<Long> getParentAdminMenuListByIds(String ids) {
		String sql = "select super_menu_ids from fansenter_admin_role where admin_role_id in ("+ids+")";
		List<Map<String,Object>> listMap=this.getJdbcTemplate().queryForList(sql);
		if(listMap==null||listMap.size()==0)return null;
		Set<Long> set=new HashSet<Long>();
		for (Map<String, Object> map : listMap) {
			String json=(String) map.get("super_menu_ids");
			if(StringUtils.isNotBlank(json)){
				List<Long> li=JSON.parseArray(json, Long.class);
				set.addAll(li);
			}
		}
		return set;
	}

	@Override
	public String getRoleIdsByAdminUserId(Long userId) {
		String sql = "select role_id from fansenter_admin_user_role where user_id = ?";
		List<Map<String,Object>> listMap=this.getJdbcTemplate().queryForList(sql, userId);
		if(listMap==null||listMap.size()==0)return null;
		List<Long> listIds=new ArrayList<Long>();
		for (Map<String, Object> map : listMap) {
			Long roleId=(Long) map.get("role_id");
			listIds.add(roleId);
		}
		return CommonUtils.listToString(listIds);
	}

    /**
     * 查询角色列表
     * @param ids
     * @return
     */
    @Override
    public List<AdminRole> queryAdminRole(String ids) {
        String sql="select admin_role_id,role_name from fansenter_admin_role where admin_role_id in ("+ids+")";
        List<Map<String, Object>> listMap = this.getJdbcTemplate().queryForList(sql);
        if(listMap==null||listMap.size()==0)return null;
        List<AdminRole> adminRoleList=new ArrayList<AdminRole>();

        for (Map<String,Object> map:listMap){
            AdminRole adminRole=new AdminRole();
            Long role_id= (Long) map.get("admin_role_id");
            String role_name= (String) map.get("role_name");
            adminRole.setAdminRoleId(role_id);
            adminRole.setRoleName(role_name);
            adminRoleList.add(adminRole);
        }
        return adminRoleList;
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<AdminRole> queryAdminRole() {
        String sql="select admin_role_id,role_name from fansenter_admin_role where admin_role_id <> 1 ";
        List<Map<String, Object>> listMap = this.getJdbcTemplate().queryForList(sql);
        if(listMap==null||listMap.size()==0)return null;
        List<AdminRole> adminRoleList=new ArrayList<AdminRole>();

        for (Map<String,Object> map:listMap){
            AdminRole adminRole=new AdminRole();
            Long role_id= (Long) map.get("admin_role_id");
            String role_name= (String) map.get("role_name");
            adminRole.setAdminRoleId(role_id);
            adminRole.setRoleName(role_name);
            adminRoleList.add(adminRole);
        }
        return adminRoleList;
    }

    /**
     * 添加系统角色
     * @param roleName
     * @param super_ids
     * @param sub_ids
     */
    @Override
    public void addAdminRole(String roleName, String super_ids, String sub_ids) {
        String sql="INSERT INTO fansenter_admin_role(role_name,super_menu_ids,sub_menu_ids) VALUES(?,?,?) ";
        this.getJdbcTemplate().update(sql,roleName,super_ids,sub_ids);
    }

    /**
     * 更新角色
     * @param roleId
     * @param roleName
     * @param super_ids
     * @param sub_ids
     */
    @Override
    public void updateAdminRole(Long roleId, String roleName, String super_ids, String sub_ids) {
        String sql=" UPDATE fansenter_admin_role SET role_name=?,super_menu_ids=?,sub_menu_ids=? where admin_role_id=? ";
        this.getJdbcTemplate().update(sql,roleName,super_ids,sub_ids,roleId);
    }

    /**
     * 查询角色信息
     * @param roleId
     * @return
     */
    @Override
    public Map<String,Object> querySystemRoleByRoleId(Long roleId) {
        String sql="select admin_role_id,role_name,super_menu_ids,sub_menu_ids from fansenter_admin_role where admin_role_id = ?";
        List<Map<String,Object>> listMap=this.getJdbcTemplate().queryForList(sql,roleId);
        if(listMap==null||listMap.size()==0)return null;
        Map<String,Object> map=listMap.get(0);
        return map;
    }

    /**
     * 添加用户角色关系
     * @param userId
     * @param roleId
     */
    @Override
    public void addUserRole(Long userId, Long roleId) {
        Timestamp addTime=new Timestamp(System.currentTimeMillis());
        String sql="INSERT INTO fansenter_admin_user_role(user_id,role_id,add_time) VALUES(?,?,?) ";
        this.getJdbcTemplate().update(sql,userId,roleId,addTime);
    }

    /**
     * 更新用户角色
     * @param userId
     * @param roleId
     */
    @Override
    public void updateUserRole(Long userId, Long roleId) {
        String sql="update fansenter_admin_user_role set role_id = ? where user_id = ?";
        this.getJdbcTemplate().update(sql,roleId,userId);
    }

    /**
     * 添加用户角色
     * @param userId
     * @param roleId
     */
    @Override
    public void deleteUserRole(Long userId, Long roleId) {
        StringBuilder stringBuilder=new StringBuilder(" delete from fansenter_admin_user_role where 1=1 ");
        if(userId!=null){
            stringBuilder.append(" and user_id =  ");
            stringBuilder.append(userId);
        }else if(roleId!=null){
            stringBuilder.append(" and role_id =  ");
            stringBuilder.append(roleId);
        }else{
            throw new SimpleException();
        }
        this.getJdbcTemplate().update(stringBuilder.toString());
    }

    /**
     * 查询用户角色roleid
     * @param userId
     * @return
     */
    @Override
    public Long queryRoleIdByUserId(Long userId) {
        String sql="SELECT role_id FROM fansenter_admin_user_role where user_id = ?";
        List<Map<String,Object>> listMap=this.getJdbcTemplate().queryForList(sql, userId);
        if(listMap==null||listMap.size()==0)return null;
        Map<String,Object> map=listMap.get(0);
        Long role_id= (Long) map.get("role_id");
        return role_id;
    }

    /**
     * 查询用户菜单ids
     * @param userId
     * @return
     */
    @Override
    public String queryRoleSuperMenuIds(Long userId) {
        String sql=" select role.super_menu_ids from fansenter_admin_role role JOIN fansenter_admin_user_role ur ON role.admin_role_id= ur.role_id JOIN fansenter_admin_user u on u.user_id= ur.user_id where u.user_id=? ";
        List<Map<String,Object>> listMap=this.getJdbcTemplate().queryForList(sql,userId);
        if(listMap==null||listMap.size()==0)return null;
        Map<String,Object> map=listMap.get(0);
        String json= (String) map.get("super_menu_ids");
        List<Long> ids=JSON.parseArray(json, Long.class);
        return CommonUtils.listToString(ids);
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Override
    public void deleteRole(Long roleId) {
        String sql=" delete  from fansenter_admin_role  where admin_role_id = ?";
        this.getJdbcTemplate().update(sql,roleId);
    }


}
