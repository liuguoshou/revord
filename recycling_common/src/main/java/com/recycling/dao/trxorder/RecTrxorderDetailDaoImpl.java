package com.recycling.dao.trxorder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.enums.TrxStatus;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.EnumUtil;

/**
 * Description : 订单明细Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:18:54 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recTrxorderDetailDao")
public class RecTrxorderDetailDaoImpl extends GenericDaoImpl<Long, RecTrxorderDetail> implements	RecTrxorderDetailDao {
	
	private static final String INSERT_SQL="insert into rec_trxorder_detail(trxorder_id,region_id,category_id,count,price,unit,trx_status,create_date,update_date,version) values(?,?,?,?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_trxorder_detail set trxorder_id=?,region_id=?,category_id=?,count=?,price=?,unit=?,trx_status=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select trxorder_detail_id,trxorder_id,region_id,category_id,count,price,unit,trx_status,create_date,update_date,version from rec_trxorder_detail ";

	@Override
	public void addTrxorderDetailList(final List<RecTrxorderDetail> detailList) {
		getJdbcTemplate().batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement preparedStatement,int i) throws SQLException {
				preparedStatement.setObject(1, detailList.get(i).getTrxorderId());
				preparedStatement.setObject(2, detailList.get(i).getRegionId());
				preparedStatement.setObject(3, detailList.get(i).getCategoryId());
				preparedStatement.setObject(4, detailList.get(i).getCount());
				preparedStatement.setObject(5, detailList.get(i).getPrice());
				preparedStatement.setObject(6, detailList.get(i).getUnit());
				preparedStatement.setObject(7, detailList.get(i).getTrxStatus().name());
				preparedStatement.setObject(8, detailList.get(i).getCreateDate());
				preparedStatement.setObject(9, detailList.get(i).getUpdateDate());
				preparedStatement.setObject(10, detailList.get(i).getVersion());
			}
			
			@Override
			public int getBatchSize() {
				return detailList.size();
			}
		});
		
	}

	@Override
	public List<RecTrxorderDetail> getByTrxorderId(Long trxorderId) {
		String sql = QUERY_SQL + " where trxorder_id = ?";
		return getJdbcTemplate().query(sql, new RowMapperImpl(),trxorderId);
	}

	@Override
	public void updateTrxorderDetailList(final List<RecTrxorderDetail> detailList) {
		String sql = UPDATE_SQL +" where trxorder_detail_id=?  and version = ? ";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement preparedStatement,int i) throws SQLException {
				preparedStatement.setObject(1, detailList.get(i).getTrxorderId());
				preparedStatement.setObject(2, detailList.get(i).getRegionId());
				preparedStatement.setObject(3, detailList.get(i).getCategoryId());
				preparedStatement.setObject(4, detailList.get(i).getCount());
				preparedStatement.setObject(5, detailList.get(i).getPrice());
				preparedStatement.setObject(6, detailList.get(i).getUnit());
				preparedStatement.setObject(7, detailList.get(i).getTrxStatus().name());
				preparedStatement.setObject(8, detailList.get(i).getCreateDate());
				preparedStatement.setObject(9, detailList.get(i).getUpdateDate());
				preparedStatement.setObject(10, detailList.get(i).getVersion()+1);
				preparedStatement.setObject(11, detailList.get(i).getTrxorderDetailId());
				preparedStatement.setObject(12, detailList.get(i).getVersion());
			}
			
			@Override
			public int getBatchSize() {
				return detailList.size();
			}
		});
	}
	
	 protected class RowMapperImpl implements ParameterizedRowMapper<RecTrxorderDetail> {
        public RecTrxorderDetail mapRow(ResultSet rs, int num) throws SQLException {
        	RecTrxorderDetail recTrxorderDetail = new RecTrxorderDetail();
        	recTrxorderDetail.setTrxorderDetailId(rs.getLong("trxorder_detail_id"));
        	recTrxorderDetail.setTrxorderId(rs.getLong("trxorder_id"));
        	recTrxorderDetail.setRegionId(rs.getLong("region_id"));
        	recTrxorderDetail.setCategoryId(rs.getLong("category_id"));
        	recTrxorderDetail.setCount(rs.getDouble("count"));
        	recTrxorderDetail.setPrice(rs.getDouble("price"));
        	recTrxorderDetail.setUnit(rs.getString("unit"));
        	recTrxorderDetail.setTrxStatus(EnumUtil.transStringToEnum(TrxStatus.class, rs.getString("trx_status")));
        	recTrxorderDetail.setCreateDate(rs.getTimestamp("create_date"));
        	recTrxorderDetail.setUpdateDate(rs.getTimestamp("update_date"));
        	recTrxorderDetail.setVersion(rs.getLong("version"));
            return recTrxorderDetail;
        }
    }

	@Override
	public Long addTrxorderDetail(RecTrxorderDetail trxorderDetail) {
		getJdbcTemplate().update(INSERT_SQL, 
				trxorderDetail.getTrxorderId(),
				trxorderDetail.getRegionId(),
				trxorderDetail.getCategoryId(),
				trxorderDetail.getCount(),
				trxorderDetail.getPrice(),
				trxorderDetail.getUnit(),
				trxorderDetail.getTrxStatus().name(),
				trxorderDetail.getCreateDate(),
				trxorderDetail.getUpdateDate(),
				trxorderDetail.getVersion()
		);
		return getLastInsertId();
	}

	@Override
	public void updateTrxorderDetail(RecTrxorderDetail trxorderDetail)	throws StaleObjectStateException {
		String sql = UPDATE_SQL +" where trxorder_detail_id=?  and version = ? ";
		int result = getJdbcTemplate().update(sql, 
				trxorderDetail.getTrxorderId(),
				trxorderDetail.getRegionId(),
				trxorderDetail.getCategoryId(),
				trxorderDetail.getCount(),
				trxorderDetail.getPrice(),
				trxorderDetail.getUnit(),
				trxorderDetail.getTrxStatus().name(),
				trxorderDetail.getCreateDate(),
				trxorderDetail.getUpdateDate(),
				trxorderDetail.getVersion()+1,
				trxorderDetail.getTrxorderDetailId(),
				trxorderDetail.getVersion()
		);
		if(result <=0){
			throw new StaleObjectStateException(StaleObjectStateException.OPTIMISTIC_LOCK_ERROR);
		}
	}

	@Override
	public void deleteByDetailId(Long detailId) {
		String sql = "delete from rec_trxorder_detail where trxorder_detail_id = ?";
		getJdbcTemplate().update(sql,detailId);
	}
	
	@Override
	public void deleteByTrxorderId(Long trxorderId) {
		String sql = "delete from rec_trxorder_detail where trxorder_id = ?";
		getJdbcTemplate().update(sql,trxorderId);
	}

    /**
     * 回收统计列表
     * @param region_parent_id
     * @param region_id
     * @param categroy_parent_id
     * @param categroy_id
     * @param fromDate
     * @param toDate
     * @param start
     * @param length
     * @return
     */
    @Override
    public Map<String, Object> queryStatistics(String region_parent_id, String region_id, String categroy_parent_id, String categroy_id, String fromDate, String toDate, int start, int length,String categroy_sub_ids,String collect_id) {
        StringBuilder stringBuilder=new StringBuilder(" SELECT detail.region_id,detail.category_id,region.parent_id as rpid,categroy.parent_id as cpid,region.region_cn_name,categroy.category_name,sum(detail.count) as total,detail.unit,detail.price FROM rec_trxorder_detail detail JOIN rec_region region ON detail.region_id = region.region_id JOIN rec_category categroy ON detail.category_id = categroy.category_id JOIN rec_trxorder o ON detail.trxorder_id = o.trxorder_id JOIN rec_beggar beggar ON o.collect_id = beggar.beggar_id where 1=1 and detail.trx_status = 'SUCCESS'  ");

        if(StringUtils.isNotBlank(region_id)){
            stringBuilder.append(" AND (region.region_id = ");
            stringBuilder.append(Long.valueOf(region_id));
            stringBuilder.append(" or region.parent_id =");
            stringBuilder.append(Long.valueOf(region_id));
            stringBuilder.append(") ");
        }else if(StringUtils.isNotBlank(region_parent_id)){
            stringBuilder.append(" AND (region.region_id = ");
            stringBuilder.append(Long.valueOf(region_parent_id));
            stringBuilder.append(" or region.parent_id =");
            stringBuilder.append(Long.valueOf(region_parent_id));
            stringBuilder.append(") ");
        }

        if(StringUtils.isNotBlank(categroy_id)){
            stringBuilder.append(" AND (categroy.parent_id = ");
            stringBuilder.append(Long.valueOf(categroy_id));
            stringBuilder.append(" or categroy.category_id =");
            stringBuilder.append(Long.valueOf(categroy_id));
            if(StringUtils.isNotBlank(categroy_sub_ids)) {
                stringBuilder.append(" or categroy.category_id in (");
                stringBuilder.append(categroy_sub_ids);
                stringBuilder.append(" ) ");
            }
            stringBuilder.append(") ");
        }else if(StringUtils.isNotBlank(categroy_parent_id)){
            stringBuilder.append(" AND (categroy.parent_id = ");
            stringBuilder.append(Long.valueOf(categroy_parent_id));
            stringBuilder.append(" or categroy.category_id =");
            stringBuilder.append(Long.valueOf(categroy_parent_id));
            if(StringUtils.isNotBlank(categroy_sub_ids)){
                stringBuilder.append(" or categroy.category_id in (");
                stringBuilder.append(categroy_sub_ids);
                stringBuilder.append(" ) ");
            }
            stringBuilder.append(") ");
        }

        if(StringUtils.isNotBlank(fromDate) && StringUtils.isNotBlank(toDate)){
            stringBuilder.append(" and ");
            stringBuilder.append("  detail.update_date >= '");
            stringBuilder.append(fromDate);
            stringBuilder.append("' and detail.update_date <= '");
            stringBuilder.append(toDate);
            stringBuilder.append("' ");
        }

        if(StringUtils.isNotBlank(collect_id)){
            stringBuilder.append(" and o.collect_id =  ");
            stringBuilder.append(collect_id);
        }
        stringBuilder.append(" GROUP BY categroy.category_id,region.region_id order by region.region_id asc");
        String countSql=stringBuilder.toString();
        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(countSql);
        if(listmap == null || listmap.size() == 0){
            return null;
        }
        stringBuilder.append(" limit ");
        stringBuilder.append(start);
        stringBuilder.append(",");
        stringBuilder.append(length);
        String finalSql=stringBuilder.toString();
        List<Map<String,Object>> resultmap=this.getJdbcTemplate().queryForList(finalSql);
        if(resultmap == null || resultmap.size() == 0){
            return null;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("totalCount",listmap.size());
        map.put("result",resultmap);
        return map;
    }

    /**
     * 查询订单统计详情
     * @param region_id
     * @param categroy_id
     * @param start
     * @param length
     * @return
     */
    @Override
    public Map<String, Object> queryDetailHistory(Long region_id, Long categroy_id,int start,int length,String from_date,String to_date) {
        StringBuilder stringBuilder=new StringBuilder(" SELECT detail.trxorder_detail_id,beggar.real_name,region.region_cn_name,categroy.category_name,detail.count,detail.price,detail.unit,detail.update_date  ");
        stringBuilder.append(" FROM rec_trxorder_detail detail JOIN rec_region region ON detail.region_id = region.region_id ");
        stringBuilder.append(" JOIN rec_category categroy ON detail.category_id = categroy.category_id JOIN rec_trxorder o ON detail.trxorder_id = o.trxorder_id ");
        stringBuilder.append(" JOIN rec_beggar beggar ON beggar.beggar_id = o.collect_id where detail.trx_status = 'SUCCESS'  ");
        stringBuilder.append(" AND detail.region_id = ? AND detail.category_id = ?  ");

        if(StringUtils.isNotBlank(from_date)&&StringUtils.isNotBlank(to_date)){
            if (StringUtils.isNotBlank(from_date)
                    && StringUtils.isNotBlank(to_date)) {
                stringBuilder.append(" and ");
                stringBuilder.append(" detail.update_date >= '");
                stringBuilder.append(from_date);
                stringBuilder.append("' and detail.update_date <= '");
                stringBuilder.append(to_date);
                stringBuilder.append("' ");
            }
        }

        stringBuilder.append(" ORDER BY detail.update_date DESC,detail.trxorder_detail_id DESC ");
        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(stringBuilder.toString(),region_id,categroy_id);
        if(listmap == null || listmap.size() == 0){
            return null;
        }
        stringBuilder.append(" limit ").append(start).append(",").append(length);
        List<Map<String,Object>> resultmap=this.getJdbcTemplate().queryForList(stringBuilder.toString(),region_id,categroy_id);
        if(resultmap == null || resultmap.size() == 0){
            return null;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("totalCount",listmap.size());
        map.put("result",resultmap);
        return map;
    }

    /**
     * 回收详情
     * @param user_id
     * @param start
     * @param length
     * @return
     */
    @Override
    public Map<String, Object> queryDetailHistoryByUserId(Long user_id, int start, int length) {
        StringBuilder stringBuilder=new StringBuilder(" SELECT detail.trxorder_detail_id,beggar.real_name,region.region_cn_name,categroy.category_name,detail.count,detail.price,detail.unit,detail.update_date  ");
        stringBuilder.append(" FROM rec_trxorder_detail detail JOIN rec_region region ON detail.region_id = region.region_id ");
        stringBuilder.append(" JOIN rec_category categroy ON detail.category_id = categroy.category_id JOIN rec_trxorder o ON detail.trxorder_id = o.trxorder_id ");
        stringBuilder.append(" JOIN rec_beggar beggar ON beggar.beggar_id = o.collect_id where detail.trx_status = 'SUCCESS'  ");
        stringBuilder.append(" AND o.collect_id = ? ORDER BY detail.update_date DESC,detail.trxorder_detail_id DESC ");

        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(stringBuilder.toString(),user_id);
        if(listmap == null || listmap.size() == 0){
            return null;
        }
        stringBuilder.append(" limit ").append(start).append(",").append(length);
        List<Map<String,Object>> resultmap=this.getJdbcTemplate().queryForList(stringBuilder.toString(),user_id);
        if(resultmap == null || resultmap.size() == 0){
            return null;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("totalCount",listmap.size());
        map.put("result",resultmap);
        return map;
    }

}
