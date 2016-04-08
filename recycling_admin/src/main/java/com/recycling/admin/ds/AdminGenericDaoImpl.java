package com.recycling.admin.ds;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.Serializable;

public class AdminGenericDaoImpl<T, ID extends Serializable> extends
		SimpleJdbcDaoSupport implements AdminGenericDao<T, ID> {


	@Resource(name = "admin.parentDataSource")
	public void setDataSource1(DataSource dataSource) {
		setDataSource(dataSource);
	}
	
	public Long getLastInsertId(){
		String sql ="select last_insert_id()";
		return getJdbcTemplate().queryForLong(sql);
	}
 
}