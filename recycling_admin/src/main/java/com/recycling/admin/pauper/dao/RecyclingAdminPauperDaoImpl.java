package com.recycling.admin.pauper.dao;

import com.recycling.admin.pauper.querycase.RecyclingAdminPauperQueryCase;
import com.recycling.admin.user.querycase.RecyclingAdminUserQueryCase;
import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.PagerHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminPauperDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("recyclingAdminPauperDao")
public class RecyclingAdminPauperDaoImpl extends GenericDaoImpl implements RecyclingAdminPauperDao {

    /**
     * 查询乞丐列表
     * @param queryCase
     * @return
     */
    @Override
    public List<Long> queryRecyclingAdminPauper(RecyclingAdminPauperQueryCase queryCase) {
        StringBuilder stringBuilder=new StringBuilder(" SELECT DISTINCT begger.beggar_id FROM rec_beggar begger JOIN rec_beggar_address address ON begger.beggar_id = address.beggar_id JOIN rec_area area ON address.area_id = area.area_id JOIN rec_region region ON address.region_id = region.region_id WHERE 1 = 1 ");
        String sql = convertCountSql(queryCase,stringBuilder);
        List listCount=this.getJdbcTemplate().queryForList(sql);
        if (listCount == null || listCount.size() == 0)
            return null;
        Pager pager = queryCase.getPager();
        pager.setTotalRows(listCount.size());
        stringBuilder.append(" order by begger.update_date desc,begger.beggar_id desc ");
        String finalSql = convertFinalSql(queryCase,stringBuilder);
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(finalSql);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<Long> l = CommonUtils.getListIds(listMap, "beggar_id");
        return l;
    }

    public String convertCountSql(RecyclingAdminPauperQueryCase queryCase,StringBuilder stringBuilder){

        if(StringUtils.isNotBlank(queryCase.getRegion_parent_id())){
            stringBuilder.append(" and region.parent_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getRegion_parent_id()));
        }

        if(StringUtils.isNotBlank(queryCase.getRegion_id())){
            stringBuilder.append(" and region.region_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getRegion_id()));
        }

        return stringBuilder.toString();
    }

    private String convertFinalSql(RecyclingAdminPauperQueryCase queryCase,StringBuilder stringBuilder){
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
