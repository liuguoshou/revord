package com.recycling.dao.beggar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.enums.BeggarStatus;
import com.recycling.common.util.EnumUtil;
@Repository("recBeggarDao")
public class RecBeggarDaoImpl extends GenericDaoImpl<Long,RecBeggar> implements RecBeggarDao {
	
	private static final String INSERT_SQL="insert into rec_beggar(real_name,mobile,open_id,password,beggar_status,create_date,update_date,version) values(?,?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_beggar set real_name=?,mobile=?,open_id=?,password=?,beggar_status=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select beggar_id,real_name,mobile,open_id,password,beggar_status,create_date,update_date,version from rec_beggar ";

	@Override
	public RecBeggar getById(Long recBeggarId) {
		String sql =  QUERY_SQL +" where beggar_id = ?";
		List<RecBeggar> userList = getJdbcTemplate().query(sql, new RowMapperImpl(),recBeggarId);
		if(null != userList && userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public RecBeggar getbyMobile(String mobile) {
		String sql =  QUERY_SQL +" where mobile = ?";
		List<RecBeggar> userList = getJdbcTemplate().query(sql, new RowMapperImpl(),mobile);
		if(null != userList && userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean updateBeggarInfo(RecBeggar recBeggar) {
		String sql = UPDATE_SQL+" where beggar_id = ? and version = ?";
		int result = getJdbcTemplate().update(sql, 
				recBeggar.getRealName(),
				recBeggar.getMobile(),
				recBeggar.getOpenId(),
				recBeggar.getPassword(),
				recBeggar.getBeggarStatus().name(),
				recBeggar.getCreateDate(),
				recBeggar.getUpdateDate(),
				recBeggar.getVersion()+1,
				recBeggar.getBeggarId(),
				recBeggar.getVersion()
		);
		return result > 0;
	}

	@Override
	public Long addBeggar(RecBeggar recBeggar) {
		getJdbcTemplate().update(INSERT_SQL, 
				recBeggar.getRealName(),
				recBeggar.getMobile(),
				recBeggar.getOpenId(),
				recBeggar.getPassword(),
				recBeggar.getBeggarStatus().name(),
				recBeggar.getCreateDate(),
				recBeggar.getUpdateDate(),
				recBeggar.getVersion()
		);
		return getLastInsertId();
	}

    /**
     * 查询乞丐列表
     * @param region_parent_id
     * @param region_id
     * @param start
     * @param length
     * @return
     */
    @Override
    public Map<String,Object> queryBeggerList(String region_parent_id,String region_id,int start,int length) {

        StringBuilder stringBuilder = new StringBuilder(" SELECT begger.beggar_id,begger.real_name,begger.beggar_status,region.parent_id,GROUP_CONCAT(region.region_cn_name) as reg_name  FROM rec_beggar begger ");
        stringBuilder.append(" JOIN rec_beggar_address address ON begger.beggar_id = address.beggar_id ");
        //stringBuilder.append(" JOIN rec_area area ON address.area_id = area.area_id ");
        stringBuilder.append(" JOIN rec_region region ON address.region_id = region.region_id ");
        if(StringUtils.isNotBlank(region_parent_id)){
//            stringBuilder.append(" and region.parent_id = ");
//            stringBuilder.append(Long.valueOf(region_parent_id));
            
            stringBuilder.append(" and region.parent_id in "+region_parent_id+" ");
        }

        if(StringUtils.isNotBlank(region_id)){
            stringBuilder.append(" and region.region_id = ");
            stringBuilder.append(Long.valueOf(region_id));
        }
        stringBuilder.append(" GROUP BY begger.beggar_id ");

        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(stringBuilder.toString());
        if(listmap == null || listmap.size() == 0){
            return null;
        }
        stringBuilder.append(" order by begger.update_date desc,begger.beggar_id DESC ");
        stringBuilder.append(" limit ").append(start).append(",").append(length);
        List<Map<String,Object>> resultmap=this.getJdbcTemplate().queryForList(stringBuilder.toString());
        if(resultmap == null || resultmap.size() == 0){
            return null;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("totalCount",listmap.size());
        map.put("result",resultmap);
        return map;
    }
    
    
    
    
    /**
     * 查询乞丐列表
     * @param region_parent_id
     * @param region_id
     * @param start
     * @param length
     * @return
     */
    @Override
    public Map<String,Object> queryBeggerList(String region_parent_id,int  areaType,String userAreaIds ,String region_id,int start,int length) {

        StringBuilder stringBuilder = new StringBuilder(" SELECT begger.beggar_id,begger.real_name,begger.beggar_status,region.parent_id,GROUP_CONCAT(region.region_cn_name) as reg_name  FROM rec_beggar begger ");
        stringBuilder.append(" JOIN rec_beggar_address address ON begger.beggar_id = address.beggar_id ");
        //stringBuilder.append(" JOIN rec_area area ON address.area_id = area.area_id ");
        stringBuilder.append(" JOIN rec_region region ON address.region_id = region.region_id ");
        if(StringUtils.isNotBlank(region_parent_id)){
//            stringBuilder.append(" and region.parent_id = ");
//            stringBuilder.append(Long.valueOf(region_parent_id));
            
            stringBuilder.append(" and region.parent_id in "+region_parent_id+" ");
        }
        
        if(areaType==5){ //小区Id
        	
        	stringBuilder.append(" and region.region_id in( ");
        	stringBuilder.append(userAreaIds );
        	stringBuilder.append(")");
        }else{	//地区Id
        	 stringBuilder.append(" and region.parent_id in "+userAreaIds+" ");
        }

        if(StringUtils.isNotBlank(region_id)){
            stringBuilder.append(" and region.region_id = ");
            stringBuilder.append(Long.valueOf(region_id));
        }
        stringBuilder.append(" GROUP BY begger.beggar_id ");

        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(stringBuilder.toString());
        if(listmap == null || listmap.size() == 0){
            return null;
        }
        stringBuilder.append(" order by begger.update_date desc,begger.beggar_id DESC ");
        stringBuilder.append(" limit ").append(start).append(",").append(length);
        List<Map<String,Object>> resultmap=this.getJdbcTemplate().queryForList(stringBuilder.toString());
        if(resultmap == null || resultmap.size() == 0){
            return null;
        }
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("totalCount",listmap.size());
        map.put("result",resultmap);
        return map;
    }

    protected class RowMapperImpl implements ParameterizedRowMapper<RecBeggar> {
       public RecBeggar mapRow(ResultSet rs, int num) throws SQLException {
    	    RecBeggar recUser = new RecBeggar();
    	    recUser.setBeggarId(rs.getLong("beggar_id"));
	       	recUser.setRealName(rs.getString("real_name"));
	       	recUser.setMobile(rs.getString("mobile"));
	       	recUser.setPassword(rs.getString("password"));
	       	recUser.setOpenId(rs.getString("open_id"));
	       	recUser.setBeggarStatus(EnumUtil.transStringToEnum(BeggarStatus.class, rs.getString("beggar_status")));
	       	recUser.setCreateDate(rs.getTimestamp("create_date"));
	       	recUser.setUpdateDate(rs.getTimestamp("update_date"));
	       	recUser.setVersion(rs.getLong("version"));
            return recUser;
       }
   }

	@Override
	public List<RecBeggar> getByBeggarIds(String ids,BeggarStatus status) {
		String sql=QUERY_SQL+"where beggar_id in ("+ids+")";
		if(null != status){
			sql +=" and beggar_status = '"+status.name()+"'";
		}
		sql +=" order by create_date desc";
		return getJdbcTemplate().query(sql, new RowMapperImpl());
	}

    /**
     * 更新乞丐状态
     * @param beggar_id
     * @param frozen
     * @return
     */
    @Override
    public Boolean updateStatusUser(Long beggar_id, String frozen) {
        String sql="update rec_beggar set beggar_status = ? where beggar_id = ?";
        int result=this.getJdbcTemplate().update(sql,frozen,beggar_id);
        Boolean flag=true;
        if(result > 0 ){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }

    /**
     * 根据openId获取乞丐信息
     * @param openId
     * @return
     */
    @Override
    public RecBeggar getBeggarInfoByOpenId(String openId) {
        String sql=QUERY_SQL + "where open_id = ?" ;
        List<RecBeggar> userList = getJdbcTemplate().query(sql, new RowMapperImpl(),openId);
		if(null != userList && userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
    }

}
