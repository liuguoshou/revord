package com.recycling.dao.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecUser;
import com.recycling.common.enums.UserStatus;
import com.recycling.common.enums.UserType;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.EnumUtil;

/**
 * Description :  用户Dao实现<br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:20:34 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recUserDao")
public class RecUserDaoImpl extends GenericDaoImpl<Long,RecUser> implements RecUserDao{
	
	private static final String INSERT_SQL="insert into rec_user(real_name,mobile,email,password,area_id,region_id,address,open_id,user_type,user_status,description,create_date,update_date,version) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_user set real_name=?,mobile=?,email=?,password=?,area_id=?,region_id=?,address=?,open_id=?,user_type=?,user_status=?,description=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select user_id,real_name,mobile,email,password,area_id,region_id,address,open_id,user_type,user_status,description,create_date,update_date,version from rec_user ";

	@Override
	public Long addRecUser(RecUser recUser) {
		getJdbcTemplate().update(INSERT_SQL, 
				recUser.getRealName(),
				recUser.getMobile(),
				recUser.getEmail(),
				recUser.getPassword(),
				recUser.getAreaId(),
				recUser.getRegionId(),
				recUser.getAddress(),
				recUser.getOpenId(),
				recUser.getUserType().name(),
				recUser.getUserStatus().name(),
				recUser.getDescription(),
				recUser.getCreateDate(),
				recUser.getUpdateDate(),
				recUser.getVersion()
		);
		return getLastInsertId();
	}

	@Override
	public RecUser getByUserId(Long userId) {
		String sql =  QUERY_SQL +" where user_id = ?";
		List<RecUser> userList = getJdbcTemplate().query(sql, new RowMapperImpl(),userId);
		if(null != userList && userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateUser(RecUser recUser) throws StaleObjectStateException {
		String sql = UPDATE_SQL +" where user_id = ? and version = ? ";
		int result = getJdbcTemplate().update(sql, 
				recUser.getRealName(),
				recUser.getMobile(),
				recUser.getEmail(),
				recUser.getPassword(),
				recUser.getAreaId(),
				recUser.getRegionId(),
				recUser.getAddress(),
				recUser.getOpenId(),
				recUser.getUserType().name(),
				recUser.getUserStatus().name(),
				recUser.getDescription(),
				recUser.getCreateDate(),
				recUser.getUpdateDate(),
				recUser.getVersion()+1,
				recUser.getUserId(),
				recUser.getVersion()
		);
		if(result <= 0){
			throw new StaleObjectStateException(StaleObjectStateException.OPTIMISTIC_LOCK_ERROR);
		}
	}
	
	 protected class RowMapperImpl implements ParameterizedRowMapper<RecUser> {
        public RecUser mapRow(ResultSet rs, int num) throws SQLException {
        	RecUser recUser = new RecUser();
        	recUser.setUserId(rs.getLong("user_id"));
        	recUser.setRealName(rs.getString("real_name"));
        	recUser.setMobile(rs.getString("mobile"));
        	recUser.setEmail(rs.getString("email"));
        	recUser.setPassword(rs.getString("password"));
        	recUser.setAreaId(rs.getLong("area_id"));
        	recUser.setRegionId(rs.getLong("region_id"));
        	recUser.setAddress(rs.getString("address"));
        	recUser.setOpenId(rs.getString("open_id"));
        	recUser.setUserType(EnumUtil.transStringToEnum(UserType.class, rs.getString("user_type")));
        	recUser.setUserStatus(EnumUtil.transStringToEnum(UserStatus.class, rs.getString("user_status")));
        	recUser.setDescription(rs.getString("description"));
        	recUser.setCreateDate(rs.getTimestamp("create_date"));
        	recUser.setUpdateDate(rs.getTimestamp("update_date"));
        	recUser.setVersion(rs.getLong("version"));
            return recUser;
        }
    }

	@Override
	public RecUser getByMobile(String mobile) {
		String sql = QUERY_SQL+" where mobile=? ";
		List<RecUser> userList = getJdbcTemplate().query(sql, new RowMapperImpl(), mobile);
		if(null != userList && userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<RecUser> getUserByRegionId(Long regionId, UserType userType) {
		String sql = QUERY_SQL + " where region_id = ? and user_type = ? ";
		return getJdbcTemplate().query(sql, new RowMapperImpl(), regionId,userType.name());
	}

    /**
     * 查询用户列表
     * @param ids
     * @return
     */
    @Override
    public List<RecUser> getUserByIds(String ids) {
        String sql=QUERY_SQL+"where user_id in ("+ids+") order by update_date desc";
        List<RecUser> recUserList=this.getJdbcTemplate().query(sql,new RowMapperImpl());
        return recUserList;
    }

    /**
     * 根据openId获取用户信息
     * @param openId
     * @return
     */
    @Override
    public RecUser getRecUserByOpenId(String openId) {
        String sql=QUERY_SQL + " where open_id = ? ";
        List<RecUser> userList = getJdbcTemplate().query(sql, new RowMapperImpl(), openId);
        if(null != userList && userList.size() > 0){
            return userList.get(0);
        }else{
            return null;
        }
    }


}
