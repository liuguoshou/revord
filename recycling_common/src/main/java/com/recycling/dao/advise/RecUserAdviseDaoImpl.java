package com.recycling.dao.advise;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecUserAdvise;

/**
 * Description : 用户投诉建议Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:58:23 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recUserAdviseDao")
public class RecUserAdviseDaoImpl extends GenericDaoImpl<Long, RecUserAdvise> implements RecUserAdviseDao{

	private static final String INSERT_SQL="insert into rec_user_advise(user_id,advise_content,create_date) values(?,?,?) ";
//	private static final String UPDATE_SQL="update rec_user_advise set user_id=?,advise_content=?,create_date=? ";
//	private static final String QUERY_SQL="select advise_id,user_id,advise_content,create_date from rec_user_advise ";
	
	@Override
	public Long addUserAdvise(RecUserAdvise userAdvise) {
		getJdbcTemplate().update(INSERT_SQL,
			userAdvise.getUserId(),
			userAdvise.getAdviseContent(),
			userAdvise.getCreateDate()
		);
		return getLastInsertId();
	}
	
   protected class RowMapperImpl implements ParameterizedRowMapper<RecUserAdvise> {

        public RecUserAdvise mapRow(ResultSet rs, int num) throws SQLException {
        	RecUserAdvise userAdvise = new RecUserAdvise();
        	userAdvise.setAdviseId(rs.getLong("advise_id"));
        	userAdvise.setUserId(rs.getLong("user_id"));
        	userAdvise.setAdviseContent(rs.getString("advise_content"));
        	userAdvise.setCreateDate(rs.getTimestamp("create_date"));
            return userAdvise;
        }
    }


}
