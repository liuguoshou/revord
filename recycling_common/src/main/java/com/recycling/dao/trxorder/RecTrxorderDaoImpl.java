package com.recycling.dao.trxorder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.bean.RecTrxorderQueryBean;
import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.enums.SettleStatus;
import com.recycling.common.enums.TrxStatus;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.EnumUtil;
import com.recycling.common.util.Pager;
import com.recycling.common.util.StringUtil;

/**
 * Description : 订单Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:17:33 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recTrxorderDao")
public class RecTrxorderDaoImpl extends GenericDaoImpl<Long, RecTrxorder> implements RecTrxorderDao {
	
	private static final String INSERT_SQL="insert into rec_trxorder(request_id,user_id,collect_id,trx_amount,trx_status,settle_status,create_date,update_date,version) values(?,?,?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_trxorder set request_id=?,user_id=?,collect_id=?,trx_amount=?,trx_status=?,settle_status=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select request_id,trxorder_id,user_id,collect_id,trx_amount,trx_status,settle_status,create_date,update_date,version from rec_trxorder ";

	@Override
	public Long addRecTrxorder(RecTrxorder trxorder) {
		getJdbcTemplate().update(INSERT_SQL,
				trxorder.getRequestId(),
				trxorder.getUserId(),
				trxorder.getCollectId(),
				trxorder.getTrxAmount(),
				trxorder.getTrxStatus().name(),
				trxorder.getSettleStatus().name(),
				trxorder.getCreateDate(),
				trxorder.getUpdateDate(),
				trxorder.getVersion()
		);
		return getLastInsertId();
	}

	@Override
	public boolean updateTrxorder(RecTrxorder trxorder)throws StaleObjectStateException {
		String sql = UPDATE_SQL +" where trxorder_id=? and version=? ";
		getJdbcTemplate().update(sql,
				trxorder.getRequestId(),
				trxorder.getUserId(),
				trxorder.getCollectId(),
				trxorder.getTrxAmount(),
				trxorder.getTrxStatus().name(),
				trxorder.getSettleStatus().name(),
				trxorder.getCreateDate(),
				trxorder.getUpdateDate(),
				trxorder.getVersion()+1,
				trxorder.getTrxorderId(),
				trxorder.getVersion()
		);
		return true;
	}

	@Override
	public RecTrxorder getByTrxorderId(Long trxorderId) {
		String sql = QUERY_SQL +" where trxorder_id = ? ";
		List<RecTrxorder> trxorderList = getJdbcTemplate().query(sql, new RowMapperImpl(),trxorderId);
		if(null != trxorderList && trxorderList.size() > 0){
			return trxorderList.get(0);
		}else{
			return null;
		}
	}
	
	 protected class RowMapperImpl implements ParameterizedRowMapper<RecTrxorder> {
        public RecTrxorder mapRow(ResultSet rs, int num) throws SQLException {
        	RecTrxorder recTrxorder = new RecTrxorder();
        	recTrxorder.setRequestId(rs.getString("request_id"));
        	recTrxorder.setTrxorderId(rs.getLong("trxorder_id"));
        	recTrxorder.setUserId(rs.getLong("user_id"));
        	recTrxorder.setCollectId(rs.getLong("collect_id"));
        	recTrxorder.setTrxAmount(rs.getDouble("trx_amount"));
        	recTrxorder.setTrxStatus(EnumUtil.transStringToEnum(TrxStatus.class, rs.getString("trx_status")));
        	recTrxorder.setSettleStatus(EnumUtil.transStringToEnum(SettleStatus.class, rs.getString("settle_status")));
        	recTrxorder.setCreateDate(rs.getTimestamp("create_date"));
        	recTrxorder.setUpdateDate(rs.getTimestamp("update_date"));
        	recTrxorder.setVersion(rs.getLong("version"));
            return recTrxorder;
        }
    }

	@Override
	public List<RecTrxorder> getByUserId(Long userId,Pager pager) {
		String sql = QUERY_SQL + " where user_id = ? order by create_date desc";
		if(null != pager && pager.getPageSize() > 0){
			sql += " limit "+pager.getStartRow()+","+pager.getPageSize();
		}
		return getJdbcTemplate().query(sql, new RowMapperImpl(),userId);
	}

	@Override
	public List<RecTrxorder> getByCollectId(Long collectId,Pager pager) {
		String sql = QUERY_SQL + " where collect_id = ? order by create_date desc";
		if(null != pager && pager.getPageSize() > 0){
			sql += " limit "+pager.getStartRow()+","+pager.getPageSize();
		}
		return getJdbcTemplate().query(sql, new RowMapperImpl(),collectId);
	}

	@Override
	public List<RecTrxorder> getListByQueryParam(RecTrxorderQueryBean queryParam) {
		String sql = QUERY_SQL + getConditionSql(queryParam);
		return getJdbcTemplate().query(sql, new RowMapperImpl());
	}

    /**
     * 重置订单状态
     * @param orderIds
     * @return
     */
    @Override
    public Integer resetTrxOrderStatus(String orderIds) {
        String sql=" update rec_trxorder set trx_status = 'CANCEL' where trxorder_id in ("+orderIds+")";
        Integer total=this.getJdbcTemplate().update(sql);
        return total;
    }

    protected String getConditionSql(RecTrxorderQueryBean queryParam){
		if(null == queryParam){
			return null;
		}
		StringBuilder sql = new StringBuilder(" where 1=1 ");
		if(null != queryParam.getCollectId()){
			sql.append(" and collect_id = ").append(queryParam.getCollectId());
		}
		if(!StringUtil.isBlank(queryParam.getStartDate())){
			sql.append(" and create_date >= '").append(queryParam.getStartDate()).append("' ");
		}
		if(!StringUtil.isBlank(queryParam.getEndDate())){
			sql.append(" and create_date < '").append(queryParam.getEndDate()).append("' ");
		}
		if(!StringUtil.isBlank(queryParam.getUserIds())){
			sql.append(" and user_id in(").append(queryParam.getUserIds()).append(") ");
		}
		if(!StringUtil.isBlank(queryParam.getTrxorderIds())){
			sql.append(" and trxorder_id in(").append(queryParam.getTrxorderIds()).append(") ");
		}
		sql.append(" order by create_date desc ");
		if(queryParam.getPageSize() > 0){
			sql.append(" limit ").append(queryParam.getStartRow()).append(",").append(queryParam.getPageSize());
		}
		return sql.toString();
	}

	@Override
	public int getCountByQueryParam(RecTrxorderQueryBean queryParam) {
		String sql = "select count(*) from rec_trxorder " + getConditionSql(queryParam);
		return getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	@Override
	public RecTrxorder getUserProcessOrder(Long userId, String startDate,String endDate) {
		String sql = QUERY_SQL +" where create_date >= ? and create_date < ? and user_id = ? ";
		sql +=" and trx_status in('INIT','PROCESSING','CONFIRM') order by create_date limit 1 ";
		List<RecTrxorder> trxorderList = getJdbcTemplate().query(sql, new RowMapperImpl(),startDate,endDate,userId);
		if(null != trxorderList && trxorderList.size() > 0){
			return trxorderList.get(0);
		}else{
			return null;
		}
	}

}
