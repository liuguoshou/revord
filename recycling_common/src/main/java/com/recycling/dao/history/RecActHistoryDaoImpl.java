package com.recycling.dao.history;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecActHistory;
import com.recycling.common.enums.ActHistoryType;
import com.recycling.common.util.EnumUtil;

/**
 * Description : 账户历史Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午5:54:38 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recActHistoryDao")
public class RecActHistoryDaoImpl extends GenericDaoImpl<Long, RecActHistory> implements RecActHistoryDao{

	private static final String INSERT_SQL="insert into rec_act_history(trxorder_id,account_id,balance,trx_amount,act_history_type,description,create_date,update_date,version) values(?,?,?,?,?,?,?,?,?) ";
//	private static final String UPDATE_SQL="update rec_act_history set trxorder_id=?,account_id=?,balance=?,trx_amount=?,act_history_type=?,description=?,create_date=?,update_date=?,version=?";
//	private static final String QUERY_SQL="select act_history_id,trxorder_id,account_id,balance,trx_amount,act_history_type,description,create_date,update_date,version from rec_act_history ";
	
	@Override
	public Long addActHistory(RecActHistory actHistory) {
		getJdbcTemplate().update(INSERT_SQL, 
				actHistory.getTrxorderId(),
				actHistory.getAccountId(),
				actHistory.getBalance(),
				actHistory.getTrxAmount(),
				actHistory.getActHistoryType().name(),
				actHistory.getDescription(),
				actHistory.getCreateDate(),
				actHistory.getUpdateDate(),
				actHistory.getVersion()
		);
		return getLastInsertId();
	}
	
   protected class RowMapperImpl implements ParameterizedRowMapper<RecActHistory> {

        public RecActHistory mapRow(ResultSet rs, int num) throws SQLException {
        	RecActHistory recActHistory = new RecActHistory();
        	recActHistory.setActHistoryId(rs.getLong("act_history_id"));
        	recActHistory.setTrxorderId(rs.getLong("trxorder_id"));
        	recActHistory.setAccountId(rs.getLong("account_id"));
        	recActHistory.setBalance(rs.getDouble("balance"));
        	recActHistory.setTrxAmount(rs.getDouble("trx_amount"));
        	recActHistory.setActHistoryType(EnumUtil.transStringToEnum(ActHistoryType.class, rs.getString("act_history_type")));
        	recActHistory.setDescription(rs.getString("description"));
        	recActHistory.setCreateDate(rs.getTimestamp("create_date"));
        	recActHistory.setUpdateDate(rs.getTimestamp("update_date"));
        	recActHistory.setVersion(rs.getLong("version"));
            return recActHistory;
        }
    }
	   
}
