package com.recycling.dao.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecCategory;

/**
 * Description : 商品分类Dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:08:30 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recCategoryDao")
public class RecCategoryDaoImpl extends GenericDaoImpl<Long, RecCategory> implements RecCategoryDao {

	private static final String INSERT_SQL="insert into rec_category(category_name,parent_id,price,unit,is_online,create_date) values(?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_category set category_name=?,parent_id=?,price=?,unit=?,is_online=?,create_date=? ";
	private static final String QUERY_SQL="select category_id,category_name,parent_id,price,unit,is_online,create_date from rec_category ";

	@Override
	public Long addRecCategory(RecCategory recCategory) {
		getJdbcTemplate().update(INSERT_SQL, 
				recCategory.getCategoryName(),
				recCategory.getParentId(),
				recCategory.getPrice(),
				recCategory.getUnit(),
				recCategory.getIsOnline(),
				recCategory.getCreateDate()
		);
		return getLastInsertId();
	}

	@Override
	public boolean updateRecCategory(RecCategory recCategory) {
		String sql = UPDATE_SQL + " where category_id=?";
		int result = getJdbcTemplate().update(sql,
			recCategory.getCategoryName(),
			recCategory.getParentId(),
			recCategory.getPrice(),
			recCategory.getUnit(),
			recCategory.getIsOnline(),
			recCategory.getCreateDate(),
			recCategory.getCategoryId()
		);
		return result > 0;
	}

	@Override
	public RecCategory getByCategoryId(Long categoryId) {
		String sql = QUERY_SQL + " where category_id = ?";
		List<RecCategory> categoryList = getJdbcTemplate().query(sql, new RowMapperImpl(),categoryId);
		if(null != categoryList && categoryList.size() > 0){
			return categoryList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<RecCategory> getByParentId(Long parentId) {
		String sql = QUERY_SQL+ " where parent_id= ? and is_online=1";
		return getJdbcTemplate().query(sql, new RowMapperImpl(),parentId);
	}

	@Override
	public List<RecCategory> getAllCategoryInfo() {
		String sql = QUERY_SQL+ " where is_online=1";
		return getJdbcTemplate().query(sql, new RowMapperImpl());
	}

    /**
     * 查询所有父级分类
     * @return
     */
    @Override
    public List<RecCategory> getAllParentCategroy() {
        String sql=QUERY_SQL + " where parent_id = 0 and is_online = 1";
        return getJdbcTemplate().query(sql, new RowMapperImpl());
    }

    /**
     * 查询分类列表
     * @param ids
     * @return
     */
    @Override
    public List<RecCategory> queryCategroyByIds(String ids) {
        String sql=QUERY_SQL + " where category_id in ("+ids+") order by create_date desc";
        return getJdbcTemplate().query(sql, new RowMapperImpl());
    }


    /**
     * 获取分类名称
     * @param ids
     * @return
     */
    @Override
    public Map<Long, String> getCategroyNameByIds(String ids) {
        String sql="select category_id,category_name from rec_category where category_id in ("+ids+")";
        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(sql);
        if (listmap == null || listmap.size() == 0){
            return null;
        }
        Map<Long,String> resultMap=new HashMap<Long, String>();
        for(Map<String,Object> map:listmap){
            Long category_id = (Long) map.get("category_id");
            String category_name= (String) map.get("category_name");
            resultMap.put(category_id,category_name);
        }
        return resultMap;
    }

    protected class RowMapperImpl implements ParameterizedRowMapper<RecCategory> {

        public RecCategory mapRow(ResultSet rs, int num) throws SQLException {
        	RecCategory recCategory = new RecCategory();
        	recCategory.setCategoryId(rs.getLong("category_id"));
        	recCategory.setCategoryName(rs.getString("category_name"));
        	recCategory.setParentId(rs.getLong("parent_id"));
        	recCategory.setPrice(rs.getDouble("price"));
        	recCategory.setUnit(rs.getString("unit"));
        	recCategory.setIsOnline(rs.getInt("is_online"));
        	recCategory.setCreateDate(rs.getTimestamp("create_date"));
            return recCategory;
        }
    }

}
