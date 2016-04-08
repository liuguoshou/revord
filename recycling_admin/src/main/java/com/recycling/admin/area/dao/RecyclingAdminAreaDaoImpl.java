package com.recycling.admin.area.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.recycling.admin.area.querycase.RecyclingAdminAreaQueryCase;
import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.Pager;

/**
 * @Title:RecyclingAdminRegionDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("recyclingAdminAreaDao")
public class RecyclingAdminAreaDaoImpl extends GenericDaoImpl implements RecyclingAdminAreaDao {

    /**
     * 查询小区列表
     * @param queryCase
     * @return
     */
    @Override
    public List<Long> queryRecyclingArea(RecyclingAdminAreaQueryCase queryCase) {
        StringBuilder stringBuilder=new StringBuilder("select area_id from rec_area where 1=1");
        String sql = convertCountSql(queryCase,stringBuilder);
        List listCount=this.getJdbcTemplate().queryForList(sql);
        if (listCount == null || listCount.size() == 0)
            return null;
        Pager pager = queryCase.getPager();
        pager.setTotalRows(listCount.size());
        stringBuilder.append(" parent_id asc ");
        String finalSql = convertFinalSql(queryCase,stringBuilder);
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(finalSql);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<Long> l = CommonUtils.getListIds(listMap, "region_id");
        return l;
    }
    public String convertCountSql(RecyclingAdminAreaQueryCase queryCase,StringBuilder stringBuilder){

        if(StringUtils.isNotBlank(queryCase.getArea_parent_id())){
            stringBuilder.append(" and parent_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getArea_parent_id()));
        }


        return stringBuilder.toString();
    }

    private String convertFinalSql(RecyclingAdminAreaQueryCase queryCase,StringBuilder stringBuilder){
        Pager pager = queryCase.getPager();
        if (pager != null) {
            stringBuilder.append(" limit ");
            stringBuilder.append(pager.getStartRow());
            stringBuilder.append(",");
            stringBuilder.append(pager.getPageSize());
        }
        return stringBuilder.toString();
    }
}
