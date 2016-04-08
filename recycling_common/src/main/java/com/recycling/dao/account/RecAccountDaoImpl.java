package com.recycling.dao.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecAccount;
import com.recycling.common.enums.AccountStatus;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.EnumUtil;

/**
 * Description : 账户信息Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:46:13 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recAccountDao")
public class RecAccountDaoImpl extends GenericDaoImpl<Long, RecAccount> implements RecAccountDao{

	private static final String INSERT_SQL="insert into rec_account(user_id,total_income,balance,integral,account_status,create_date,update_date,version) values(?,?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_account set user_id=?,total_income=?,balance=?,integral=?,account_status=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select account_id,user_id,total_income,balance,integral,account_status,create_date,update_date,version from rec_account ";
	
	@Override
	public RecAccount getById(Long accountId) {
		String sql = QUERY_SQL +" where account_id=? ";
		List<RecAccount> accountList = getJdbcTemplate().query(sql, new RowMapperImpl(),accountId);
		if(null != accountList && accountList.size() > 0){
			return accountList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public RecAccount getByUserId(Long userId) {
		String sql = QUERY_SQL +" where user_id=? ";
		List<RecAccount> accountList = getJdbcTemplate().query(sql, new RowMapperImpl(),userId);
		if(null != accountList && accountList.size() > 0){
			return accountList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Long addAccount(RecAccount recAccount) {
		getJdbcTemplate().update(INSERT_SQL,
				recAccount.getUserId(),
				recAccount.getTotalIncome(),
				recAccount.getBalance(),
				recAccount.getIntegral(),
				recAccount.getAccountStatus().name(),
				recAccount.getCreateDate(),
				recAccount.getUpdateDate(),
				recAccount.getVersion()
			);
		return getLastInsertId();
	}

	@Override
	public boolean updateAccount(RecAccount recAccount) throws StaleObjectStateException{
		String sql = UPDATE_SQL + " where account_id=?  and version=? ";
		int result = getJdbcTemplate().update(sql,
				recAccount.getUserId(),
				recAccount.getTotalIncome(),
				recAccount.getBalance(),
				recAccount.getIntegral(),
				recAccount.getAccountStatus().name(),
				recAccount.getCreateDate(),
				recAccount.getUpdateDate(),
				recAccount.getVersion()+1,
				recAccount.getAccountId(),
				recAccount.getVersion()
			);
		if(result <= 0){
			throw new StaleObjectStateException(StaleObjectStateException.OPTIMISTIC_LOCK_ERROR);
		}
		return true;
	}
	
   protected class RowMapperImpl implements ParameterizedRowMapper<RecAccount> {

        public RecAccount mapRow(ResultSet rs, int num) throws SQLException {
        	RecAccount account = new RecAccount();
        	account.setAccountId(rs.getLong("account_id"));
        	account.setUserId(rs.getLong("user_id"));
        	account.setTotalIncome(rs.getDouble("total_income"));
        	account.setBalance(rs.getDouble("balance"));
        	account.setIntegral(rs.getLong("integral"));
        	account.setAccountStatus(EnumUtil.transStringToEnum(AccountStatus.class, rs.getString("account_status")));
        	account.setCreateDate(rs.getTimestamp("create_date"));
        	account.setUpdateDate(rs.getTimestamp("update_date"));
        	account.setVersion(rs.getLong("version"));
            return account;
        }
    }

}
