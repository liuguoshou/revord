package com.recycling.dao.area;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 区县Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:02:05 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recAreaDao")
public class RecAreaDaoImpl extends GenericDaoImpl<Long, RecArea> implements RecAreaDao {
	
	private static final String INSERT_SQL="insert into rec_area(area_cn_name,area_en_name,parent_id,is_active,is_online,area_type) values(?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_area set area_cn_name=?,area_en_name=?,parent_id=?,is_active=?,is_online=? ";
	private static final String QUERY_SQL="select area_id,area_cn_name,area_en_name,parent_id,is_active,is_online,area_type,ids from rec_area ";
	
	@Override
	public Long addRecArea(RecArea recAre) {
		getJdbcTemplate().update(INSERT_SQL, 
				recAre.getAreaCnName(),
				recAre.getAreaEnName(),
				recAre.getParentId(),
				recAre.getIsActive(),
				recAre.getIsOnline(),
				recAre.getAreaType()
		);
		return getLastInsertId();
	}

	@Override
	public boolean isIds() {
		String sql = QUERY_SQL +" where ids = ? ";
		return false;
	}


	public RecArea getByAreaId(Long areaId) {
		String sql = QUERY_SQL +" where area_id = ? ";
		List<RecArea> areaList =  getJdbcTemplate().query(
					sql, new RowMapperImpl(),areaId);
		if(null != areaList && areaList.size() >0 ){
			return areaList.get(0);
		}else{
			return null;
		}
	}
	@Override
	public RecArea getByAreaId(Long areaId,int isOnline,int isActive) {
		String sql = QUERY_SQL +" where area_id = ? and is_online= ? and  is_active=? ";
		List<RecArea> areaList =  getJdbcTemplate().query(
					sql, new RowMapperImpl(),areaId,isOnline,isActive);
		if(null != areaList && areaList.size() >0 ){
			return areaList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public List<RecArea> getByParentId(Long parentId) { //查询所有未开通的
		String sql = QUERY_SQL +" where parent_id = ? and  is_active=0 and is_online=1";
		return  getJdbcTemplate().query(sql, new RowMapperImpl(),parentId);
	}
	
	@Override
	public List<RecArea> getActiveByParentId(Long parentId) { //查询所有未开通的
		String sql = QUERY_SQL +" where parent_id = ? and  is_active=1 and is_online=1";
		return  getJdbcTemplate().query(sql, new RowMapperImpl(),parentId);
	}
	
	@Override
	public List<RecArea> getAllActiveByParentId(Long parentId) { //查询所的
		String sql = QUERY_SQL +" where parent_id = ? and is_online=1";
		return  getJdbcTemplate().query(sql, new RowMapperImpl(),parentId);
	}
	
	
	@Override
	public List<RecArea> getAllAreaInfo() {
		String sql = QUERY_SQL+" where  is_active=0 and is_online=1 ";
		return  getJdbcTemplate().query(sql, new RowMapperImpl());
	}

   protected class RowMapperImpl implements ParameterizedRowMapper<RecArea> {

        public RecArea mapRow(ResultSet rs, int num) throws SQLException {
        	RecArea recArea = new RecArea();
        	recArea.setAreaId(rs.getLong("area_id"));
        	recArea.setAreaCnName(rs.getString("area_cn_name"));
        	recArea.setAreaEnName(rs.getString("area_en_name"));
        	recArea.setParentId(rs.getLong("parent_id"));
        	recArea.setIsActive(rs.getInt("is_active"));
        	recArea.setIsOnline(rs.getInt("is_online"));
        	recArea.setAreaType(rs.getInt("area_type"));
            return recArea;
        }
    }
   
   public boolean updateArea(RecArea recArea)	throws StaleObjectStateException {
		String sql = UPDATE_SQL +" where area_id = ? ";
		int result = getJdbcTemplate().update(sql, 
				recArea.getAreaCnName(),
				recArea.getAreaEnName(),
				recArea.getParentId(),
				recArea.getIsActive(),
				recArea.getIsOnline(),
				recArea.getAreaId()
		);
		if(result <= 0){
			throw new StaleObjectStateException(StaleObjectStateException.OPTIMISTIC_LOCK_ERROR);
		}
		return true;
	}
   
   /**
    * 地区列表
    * @param ids
    * @return
    */
   @Override
   public List<RecArea> getRecAreaList(String ids) {
       String sql=QUERY_SQL+" where region_id in ("+ids+") order by area_id desc";
       List<RecArea> areaList = getJdbcTemplate().query(sql, new RowMapperImpl());
       if(null != areaList && areaList.size() > 0){
           return areaList;
       }else{
           return null;
       }
   }
   
}
