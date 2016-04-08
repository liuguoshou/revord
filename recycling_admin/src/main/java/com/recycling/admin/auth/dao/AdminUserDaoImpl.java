package com.recycling.admin.auth.dao;

import com.recycling.admin.ds.AdminGenericDaoImpl;
import com.recycling.admin.entity.AdminUser;
import com.recycling.admin.entity.AdminUserArea;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title:AdminUserDaoImpl.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Repository(value = "adminUserDao")
public class AdminUserDaoImpl extends AdminGenericDaoImpl implements
		AdminUserDao {

	@Override
	public Long addAdminUser(final String username, final String password, final String realName,
			final Integer isValid, final Timestamp addTime) {
		final String sql = "insert into fansenter_admin_user (user_name,user_pass,user_isvalid,user_realname,addtime) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setInt(3, isValid);
                ps.setString(4, realName);
                ps.setTimestamp(5,addTime);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
	}
	

	
	public Long addAdminUserArea(final int userId, final int areaType, final String areaIds) {
//		final String sql = "insert into fansenter_admin_user (user_name,user_pass,user_isvalid,user_realname,addtime) values(?,?,?,?,?)";
		final String sql = "insert into fansenter_admin_user_area (user_id,area_type,area_id) values(?,?,?)";
      	KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, areaType);
                ps.setString(3, areaIds);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
	}


	@Override
	public AdminUser queryAdminUser(String userName, String password) {
		String sql = "select user_id,user_name,user_pass,user_isvalid,user_realname,addtime	 from fansenter_admin_user where user_name=? and user_pass=? and user_isvalid='1'";
		AdminUser au = null;
		try {
			au = this.getJdbcTemplate().queryForObject(sql, userRowMapper,
					userName, password);
		} catch (Exception e) {
			if (!(e instanceof EmptyResultDataAccessException)) {
				e.printStackTrace();
			}
		}
		return au;
	}

	private RowMapper<AdminUser> userRowMapper = new RowMapper<AdminUser>() {

		@Override
		public AdminUser mapRow(ResultSet rs, int arg1) throws SQLException {
			AdminUser au = new AdminUser();
			Long userId = rs.getLong("user_id");
			String userName = rs.getString("user_name");
			String userPass = rs.getString("user_pass");
			Integer userIsvalid = rs.getInt("user_isvalid");
			String realName = rs.getString("user_realname");
			Timestamp addTime = rs.getTimestamp("addtime");
			au.setUserName(userName);
			au.setAdminUserId(userId);
			au.setPassword(userPass);
			au.setIsValid(userIsvalid);
			au.setRealName(realName);
			au.setAddTime(addTime);
			return au;
		}
	};
	
	
	private RowMapper<AdminUserArea> userAreaRowMapper = new RowMapper<AdminUserArea>() {

		@Override
		public AdminUserArea mapRow(ResultSet rs, int arg1) throws SQLException {
			AdminUserArea aua = new AdminUserArea();
			Integer userAreaId = rs.getInt("user_area_id");
			Integer userId = rs.getInt("user_id");
			Integer areaType = rs.getInt("area_type");
			String areaIds = rs.getString("area_id");
			
			aua.setUserAreaId(userAreaId);
			aua.setUserId(userId);
			aua.setAreaType(areaType);
			aua.setAreaIds(areaIds);
			return aua;
		}
	};
	
	
	

	@Override
	public AdminUser queryAdminUserById(Long userId) {
		String sql = "select user_id,user_name,user_pass,user_isvalid,user_realname,addtime	 from fansenter_admin_user where user_id=? and user_isvalid='1'";
		AdminUser au = null;
		try {
			au = this.getJdbcTemplate().queryForObject(sql, userRowMapper,
					userId);
		} catch (Exception e) {
			if (!(e instanceof EmptyResultDataAccessException)) {
				e.printStackTrace();
			}
		}
		return au;
	}

	@Override
	public List<AdminUser> queryAdminUserList(String ids) {
		String sql = "select user_id,user_name,user_pass,user_isvalid,user_realname,addtime	 from fansenter_admin_user where user_id in ("
				+ ids + ")";
		List<Map<String, Object>> listMap = this.getJdbcTemplate()
				.queryForList(sql);
		
		if(listMap==null||listMap.size()==0)return null;
		
		
		List<AdminUser> listUser=new ArrayList<AdminUser>();
		
		for (Map<String, Object> map : listMap) {
			Long userId = (Long) map.get("user_id");
			String userName = (String) map.get("user_name");
			String userPass = (String) map.get("user_pass");
			Integer userIsvalid = (Integer) map.get("user_isvalid");
			String realName = (String) map.get("user_realname");
			Timestamp addTime = (Timestamp) map.get("addtime");
			
			AdminUser au=new AdminUser();
			au.setUserName(userName);
			au.setAdminUserId(userId);
			au.setPassword(userPass);
			au.setIsValid(userIsvalid);
			au.setRealName(realName);
			au.setAddTime(addTime);
			listUser.add(au);
		}


		return listUser;
	}

    /**
     * 更新用户信息
     * @param adminUser
     */
    @Override
    public void updateAdminUser(AdminUser adminUser) {
        StringBuilder stringBuilder=new StringBuilder(" update fansenter_admin_user SET user_name=?,user_realname=?,user_pass=?,user_isvalid=?  ");
        if(adminUser.getAddTime()!=null){
            stringBuilder.append(" ,addtime = "+adminUser.getAddTime());
        }
        stringBuilder.append(" where user_id = ?");
        this.getJdbcTemplate().update(stringBuilder.toString(),
            adminUser.getUserName(),adminUser.getRealName(),adminUser.getPassword(),adminUser.getIsValid(),adminUser.getAdminUserId()
        );

    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Override
    public AdminUser queryAdminUserByUserId(Long userId) {
        String sql = "select user_id,user_name,user_pass,user_isvalid,user_realname,addtime	 from fansenter_admin_user where user_id=? ";
        AdminUser au = null;
        try {
            au = this.getJdbcTemplate().queryForObject(sql, userRowMapper,
                    userId);
        } catch (Exception e) {
            if (!(e instanceof EmptyResultDataAccessException)) {
                e.printStackTrace();
            }
        }
        return au;
    }

    /**
     * 删除系统用户
     * @param userId
     */
    @Override
    public void deleteAdminUserById(Long userId) {
        String sql="delete from fansenter_admin_user where user_id= ?";
        this.getJdbcTemplate().update(sql,userId);
    }

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    @Override
    public Long queryAdminUserByUserName(String userName) {
        String sql="select count(user_id) from fansenter_admin_user where user_name = ?";
        Long total=this.getJdbcTemplate().queryForLong(sql,userName);
        return total;
    }

    
    
    
    public AdminUserArea queryAdminUserArea(int userId ) {
		String sql = "SELECT user_area_id, user_id, area_type, area_id FROM fansenter_admin_user_area where user_id=?";
		AdminUserArea au = null;
		try {
			au = this.getJdbcTemplate().queryForObject(sql, userAreaRowMapper,userId);
		} catch (Exception e) {
			if (!(e instanceof EmptyResultDataAccessException)) {
				e.printStackTrace();
			}
		}
		return au;
	}
    
  

    public void updateAdminUserArea(AdminUserArea userArea) {
        StringBuilder stringBuilder=new StringBuilder(" update fansenter_admin_user_area SET area_type=? ,area_id =? where user_id=? ");
        
        this.getJdbcTemplate().update(stringBuilder.toString(), userArea.getAreaType(),userArea.getAreaIds(),userArea.getUserId() );

    }

}
