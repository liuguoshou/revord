package com.recycling.dao.beggar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecBeggarAddress;
import com.recycling.common.util.StringUtil;
@Repository("recBeggarAddressDao")
public class RecBeggarAddressDaoImpl extends GenericDaoImpl<Long,RecBeggarAddress> implements	RecBeggarAddressDao {

	private static final String INSERT_SQL="insert into rec_beggar_address(beggar_id,area_id,region_id,address,create_date,update_date,version) values(?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_beggar_address set beggar_id=?,area_id=?,region_id=?,address=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select address_id,beggar_id,area_id,region_id,address,create_date,update_date,version from rec_beggar_address ";

	@Override
	public RecBeggarAddress getByAddressId(Long addressId) {
		String sql = QUERY_SQL + " where address_id = ? ";
		List<RecBeggarAddress> addressList = getJdbcTemplate().query(sql, new RowMapperImpl(),addressId);
		if(null != addressList && addressList.size() > 0){
			return addressList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<RecBeggarAddress> getByBeggarId(Long beggarId) {
		String sql = QUERY_SQL + " where beggar_id = ? ";
		return getJdbcTemplate().query(sql, new RowMapperImpl(),beggarId);
	}

	@Override
	public Long addAddress(RecBeggarAddress address) {
		getJdbcTemplate().update(INSERT_SQL, 
				address.getBeggarId(),
				address.getAreaId(),
				address.getRegionId(),
				address.getAddress(),
				address.getCreateDate(),
				address.getUpdateDate(),
				address.getVersion()
		);
		return getLastInsertId();
	}

	@Override
	public boolean updateAddress(RecBeggarAddress address) {
		String sql = UPDATE_SQL + " where address_id = ? and version= ?";
		int result = getJdbcTemplate().update(sql, 
				address.getBeggarId(),
				address.getAreaId(),
				address.getRegionId(),
				address.getAddress(),
				address.getCreateDate(),
				address.getUpdateDate(),
				address.getVersion()+1,
				address.getBeggarId(),
				address.getVersion()
		);
		return result > 0;
	}

    /**
     * 删除乞丐相关地址
     * @param beggarId
     * @return
     */
    @Override
    public boolean deleteAddressByBeggarId(Long beggarId) {
        String sql="delete from rec_beggar_address where beggar_id = ?";
        int row=this.getJdbcTemplate().update(sql,beggarId);
        return true;
    }

    protected class RowMapperImpl implements ParameterizedRowMapper<RecBeggarAddress> {

        public RecBeggarAddress mapRow(ResultSet rs, int num) throws SQLException {
        	RecBeggarAddress address = new RecBeggarAddress();
        	address.setAddressId(rs.getLong("address_id"));
        	address.setBeggarId(rs.getLong("beggar_id"));
        	address.setAreaId(rs.getLong("area_id"));
        	address.setRegionId(rs.getLong("region_id"));
        	address.setAddress(rs.getString("address"));
        	address.setCreateDate(rs.getTimestamp("create_date"));
        	address.setUpdateDate(rs.getTimestamp("update_date"));
        	address.setVersion(rs.getLong("version"));
            return address;
        }
    }
	@Override
	public List<Long> queryBeggarIdByRegionIds(String regionIds) {
		if(StringUtil.isBlank(regionIds)){
			return Collections.emptyList();
		}
		String sql = "select distinct beggar_id from rec_beggar_address where region_id in("+regionIds+")";
		return getJdbcTemplate().queryForList(sql, Long.class);
	}
}
