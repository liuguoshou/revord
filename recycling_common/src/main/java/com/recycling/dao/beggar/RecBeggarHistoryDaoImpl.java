package com.recycling.dao.beggar;

import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecBeggarHistory;

@Repository("recBeggarHistoryDao")
public class RecBeggarHistoryDaoImpl extends GenericDaoImpl<Long, RecBeggarHistory> implements RecBeggarHistoryDao{

	private static final String INSERT_SQL="insert into rec_beggar_history(trxorder_id,account_id,balance,trx_amount,beggar_history_type,description,create_date) values(?,?,?,?,?,?,?) ";

	@Override
	public Long addBeggarHistory(RecBeggarHistory beggarHistory) {
		getJdbcTemplate().update(INSERT_SQL, 
				beggarHistory.getTrxorderId(),
				beggarHistory.getAccountId(),
				beggarHistory.getBalance(),
				beggarHistory.getTrxAmount(),
				beggarHistory.getBeggarHistoryType().name(),
				beggarHistory.getDescription(),
				beggarHistory.getCreateDate()
		);
		return getLastInsertId();
	}

}
