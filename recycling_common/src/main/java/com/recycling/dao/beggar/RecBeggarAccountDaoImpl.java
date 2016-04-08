package com.recycling.dao.beggar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecBeggarAccount;
import com.recycling.common.enums.AccountStatus;
import com.recycling.common.util.EnumUtil;

@Repository("recBeggarAccountDao")
public class RecBeggarAccountDaoImpl extends GenericDaoImpl<Long,RecBeggarAccount> implements 	RecBeggarAccountDao {

	private static final String INSERT_SQL="insert into rec_beggar_account(beggar_id,balance,account_status,create_date,update_date,version) values(?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_beggar_account set beggar_id=?,balance=?,account_status=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select account_id,beggar_id,balance,account_status,create_date,update_date,version from rec_beggar_account ";

	@Override
	public RecBeggarAccount getById(Long accountId) {
		String sql = QUERY_SQL +" where account_id=? ";
		List<RecBeggarAccount> accountList = getJdbcTemplate().query(sql, new RowMapperImpl(),accountId);
		if(null != accountList && accountList.size() > 0){
			return accountList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public RecBeggarAccount getByBeggarId(Long beggarId){
		String sql = QUERY_SQL +" where beggar_id=? ";
		List<RecBeggarAccount> accountList = getJdbcTemplate().query(sql, new RowMapperImpl(),beggarId);
		if(null != accountList && accountList.size() > 0){
			return accountList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public Long addAccount(RecBeggarAccount recAccount) {
		getJdbcTemplate().update(INSERT_SQL,
				recAccount.getBeggarId(),
				recAccount.getBalance(),
				recAccount.getAccountStatus().name(),
				recAccount.getCreateDate(),
				recAccount.getUpdateDate(),
				recAccount.getVersion()
		);
		return getLastInsertId();
	}

	@Override
	public boolean updateAccount(RecBeggarAccount recAccount) {
		String sql = UPDATE_SQL + " where account_id=?  and version=? ";
		int result = getJdbcTemplate().update(sql,
				recAccount.getBeggarId(),
				recAccount.getBalance(),
				recAccount.getAccountStatus().name(),
				recAccount.getCreateDate(),
				recAccount.getUpdateDate(),
				recAccount.getVersion()+1,
				recAccount.getAccountId(),
				recAccount.getVersion()
			);
		return result > 0;
	}

	   protected class RowMapperImpl implements ParameterizedRowMapper<RecBeggarAccount> {

	        public RecBeggarAccount mapRow(ResultSet rs, int num) throws SQLException {
	        	RecBeggarAccount account = new RecBeggarAccount();
	        	account.setAccountId(rs.getLong("account_id"));
	        	account.setBeggarId(rs.getLong("beggar_id"));
	        	account.setBalance(rs.getDouble("balance"));
	        	account.setAccountStatus(EnumUtil.transStringToEnum(AccountStatus.class, rs.getString("account_status")));
	        	account.setCreateDate(rs.getTimestamp("create_date"));
	        	account.setUpdateDate(rs.getTimestamp("update_date"));
	        	account.setVersion(rs.getLong("version"));
	            return account;
	        }
	    }
}
