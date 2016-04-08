package com.recycling.dao.history;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecIntegralHistory;
import com.recycling.common.enums.IntegralHistoryType;
import com.recycling.common.util.EnumUtil;
import com.recycling.common.util.Pager;

/**
 * Description : 积分历史Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:12:56 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recIntegralHistoryDao")
public class RecIntegralHistoryDaoImpl extends GenericDaoImpl<Long, RecIntegralHistory> implements RecIntegralHistoryDao {
	
	private static final String INSERT_SQL="insert into rec_integral_history(trxorder_id,account_id,integral,trx_amount,integral_history_type,description,create_date,update_date,version) values(?,?,?,?,?,?,?,?,?) ";
//	private static final String UPDATE_SQL="update rec_integral_history set trxorder_id=?,account_id=?,integral=?,trx_amount=?,integral_history_type=?,description=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select integral_history_id,trxorder_id,account_id,integral,trx_amount,integral_history_type,description,create_date,update_date,version from rec_integral_history ";

	@Override
	public Long addIntegralHistory(RecIntegralHistory recIntegralHistory) {
		getJdbcTemplate().update(INSERT_SQL,
				recIntegralHistory.getTrxorderId(),
				recIntegralHistory.getAccountId(),
				recIntegralHistory.getIntegral(),
				recIntegralHistory.getTrxAmount(),
				recIntegralHistory.getIntegralHistoryType().name(),
				recIntegralHistory.getDescription(),
				recIntegralHistory.getCreateDate(),
				recIntegralHistory.getUpdateDate(),
				recIntegralHistory.getVersion()
		);
		return getLastInsertId();
	}
	
	protected class RowMapperImpl implements ParameterizedRowMapper<RecIntegralHistory> {

        public RecIntegralHistory mapRow(ResultSet rs, int num) throws SQLException {
        	RecIntegralHistory recIntegralHistory = new RecIntegralHistory();
        	recIntegralHistory.setIntegralHistoryId(rs.getLong("integral_history_id"));
        	recIntegralHistory.setTrxorderId(rs.getLong("trxorder_id"));
        	recIntegralHistory.setAccountId(rs.getLong("account_id"));
        	recIntegralHistory.setIntegral(rs.getLong("integral"));
        	recIntegralHistory.setTrxAmount(rs.getLong("trx_amount"));
        	recIntegralHistory.setIntegralHistoryType(EnumUtil.transStringToEnum(IntegralHistoryType.class, rs.getString("integral_history_type")));
        	recIntegralHistory.setDescription(rs.getString("description"));
        	recIntegralHistory.setCreateDate(rs.getTimestamp("create_date"));
        	recIntegralHistory.setUpdateDate(rs.getTimestamp("update_date"));
        	recIntegralHistory.setVersion(rs.getLong("version"));
            return recIntegralHistory;
        }
    }

	@Override
	public List<RecIntegralHistory> getHistoryByAccountId(Long accountId, Pager pager) {
		String sql = QUERY_SQL +" where account_id = ? order by create_date desc ";
		if(null != pager && pager.getPageSize() > 0){
			sql +=" limit "+pager.getStartRow()+","+pager.getPageSize();
		}
		return getJdbcTemplate().query(sql, new RowMapperImpl(),accountId);
	}

	@Override
	public int getHistoryCount(Long accountId) {
		String sql = "select count(*) from rec_integral_history where account_id = ?";
		return getJdbcTemplate().queryForObject(sql, Integer.class,accountId);
	}

}
