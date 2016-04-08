package com.recycling.dao.region;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 区域Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:16:16 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recRegionDao")
public class RecRegionDaoImpl extends GenericDaoImpl<Long, RecRegion> implements 	RecRegionDao {
	
	private static final String INSERT_SQL="insert into rec_region(region_cn_name,region_en_name,area_id,parent_id,is_active,is_online,create_date,update_date,version) values(?,?,?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_region set region_cn_name=?,region_en_name=?,area_id=?,parent_id=?,is_active=?,is_online=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select region_id,region_cn_name,region_en_name,area_id,parent_id,is_active,is_online,create_date,update_date,version from rec_region ";

	@Override
	public Long addRecRegion(RecRegion recRegion) {
		getJdbcTemplate().update(INSERT_SQL, 
				recRegion.getRegionCnName(),
				recRegion.getRegionEnName(),
				recRegion.getAreaId(),
				recRegion.getParentId(),
				recRegion.getIsActive(),
				recRegion.getIsOnline(),
				recRegion.getCreateDate(),
				recRegion.getUpdateDate(),
				recRegion.getVersion()
		);
		return getLastInsertId();
	}

	@Override
	public RecRegion getByRegionId(Long regionId,int isActive,int isOnline) {
		String sql = QUERY_SQL +" where region_id = ? and is_active=? and is_online=? ";
		List<RecRegion> regionList = getJdbcTemplate().query(sql, new RowMapperImpl(),regionId,isActive,isOnline);
		if(null != regionList && regionList.size() > 0){
			return regionList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public RecRegion getByRegionId(Long regionId) {
		String sql = QUERY_SQL +" where region_id = ? ";
		List<RecRegion> regionList = getJdbcTemplate().query(sql, new RowMapperImpl(),regionId);
		if(null != regionList && regionList.size() > 0){
			return regionList.get(0);
		}else{
			return null;
		}
	}

	

	@Override
	public boolean updateRegion(RecRegion recRegion)	throws StaleObjectStateException {
		String sql = UPDATE_SQL +" where region_id = ? and version = ?";
		int result = getJdbcTemplate().update(sql, 
				recRegion.getRegionCnName(),
				recRegion.getRegionEnName(),
				recRegion.getAreaId(),
				recRegion.getParentId(),
				recRegion.getIsActive(),
				recRegion.getIsOnline(),
				recRegion.getCreateDate(),
				recRegion.getUpdateDate(),
				recRegion.getVersion()+1L,
				recRegion.getRegionId(),
				recRegion.getVersion()
		);
		if(result <= 0){
			throw new StaleObjectStateException(StaleObjectStateException.OPTIMISTIC_LOCK_ERROR);
		}
		return true;
	}

	@Override
	public List<RecRegion> getByParentId(Long parentId,Long areaId) {
		String sql = QUERY_SQL+" where parent_id = ? and area_id=?  and is_active=0 and is_online=1 ";
		return getJdbcTemplate().query(sql, new RowMapperImpl(),parentId,areaId);
	}

	@Override
	public List<RecRegion> getByStreetId(Long parentId) {
		String sql = QUERY_SQL+" where parent_id = ?  and is_active=1 and is_online=1 ";
		return getJdbcTemplate().query(sql, new RowMapperImpl(),parentId);
	}

	
	@Override
	public List<RecRegion> getAllByParentId(Long parentId) {
		String sql = QUERY_SQL+" where parent_id = ? and is_online=1 ";
		return getJdbcTemplate().query(sql, new RowMapperImpl(),parentId);
	}
	@Override
	public List<RecRegion> getAllRegionInfo() {
		String sql = QUERY_SQL+" where  is_active=0 and is_online=1 ";
		return getJdbcTemplate().query(sql, new RowMapperImpl());
	}

    /**
     * 查询小区列表
     * @param ids
     * @return
     */
    @Override
    public List<RecRegion> getRecRegionList(String ids) {
        String sql=QUERY_SQL+" where region_id in ("+ids+") order by update_date desc";
        List<RecRegion> regionList = getJdbcTemplate().query(sql, new RowMapperImpl());
        if(null != regionList && regionList.size() > 0){
            return regionList;
        }else{
            return null;
        }
    }

    /**
     * 查询所有商圈
     * @return
     */
    @Override
    public List<RecRegion> getAllParentRegion() {
        String sql = QUERY_SQL+" where  is_active=0 and is_online=1 and parent_id = 0";
        return getJdbcTemplate().query(sql, new RowMapperImpl());
    }

    /**
     * 根据ids查询所以热区名称
     * @param ids
     * @return
     */
    @Override
    public Map<Long, String> getParentRegionCnName(String ids) {
        String sql=" select region_id,region_cn_name from rec_region where region_id IN ("+ids+")";
        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(sql);
        if(listmap == null || listmap.size() == 0){
            return null;
        }
        Map<Long, String> regionMap = new HashMap<Long, String>();
        for (Map<String,Object> map:listmap){
            Long region_id = (Long) map.get("region_id");
            String region_cn_name = (String) map.get("region_cn_name");
            regionMap.put(region_id,region_cn_name);
        }
        return regionMap;
    }

    protected class RowMapperImpl implements ParameterizedRowMapper<RecRegion> {
        public RecRegion mapRow(ResultSet rs, int num) throws SQLException {
        	RecRegion recRegion = new RecRegion();
        	recRegion.setRegionId(rs.getLong("region_id"));
        	recRegion.setRegionCnName(rs.getString("region_cn_name"));
        	recRegion.setRegionEnName(rs.getString("region_en_name"));
        	recRegion.setAreaId(rs.getLong("area_id"));
        	recRegion.setParentId(rs.getLong("parent_id"));
        	recRegion.setIsActive(rs.getInt("is_active"));
        	recRegion.setIsOnline(rs.getInt("is_online"));
        	recRegion.setCreateDate(rs.getTimestamp("create_date"));
        	recRegion.setUpdateDate(rs.getTimestamp("update_date"));
        	recRegion.setVersion(rs.getLong("version"));
            return recRegion;
        }
    }
    
    
    
    /**
     * 查询所有省
     * @return
     */
 
    public List<RecRegion> getAllProvence() {
        String sql = QUERY_SQL+" where  is_active=0 and is_online=1 and parent_id = 0 ";
        return getJdbcTemplate().query(sql, new RowMapperImpl());
    }
}
