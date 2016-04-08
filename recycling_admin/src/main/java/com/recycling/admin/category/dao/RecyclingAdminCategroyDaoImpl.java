package com.recycling.admin.category.dao;

import com.recycling.admin.category.querycase.RecyclingAdminCategroyQueryCase;
import com.recycling.admin.region.querycase.RecyclingAdminRegionQueryCase;
import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.PagerHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminCategroyDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("recyclingAdminCategroyDao")
public class RecyclingAdminCategroyDaoImpl extends GenericDaoImpl implements RecyclingAdminCategroyDao {

    /**
     * 查询分类列表
     * @param queryCase
     * @return
     */
    @Override
    public List<Long> queryRecyclingCategroy(RecyclingAdminCategroyQueryCase queryCase) {
        StringBuilder stringBuilder=new StringBuilder(" select category_id from rec_category where 1=1 ");
        String sql = convertCountSql(queryCase,stringBuilder);
        List listCount=this.getJdbcTemplate().queryForList(sql);
        if (listCount == null || listCount.size() == 0)
            return null;
        Pager pager = queryCase.getPager();
        pager.setTotalRows(listCount.size());
        stringBuilder.append(" order by create_date desc ");
        String finalSql = convertFinalSql(queryCase,stringBuilder);
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(finalSql);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<Long> l = CommonUtils.getListIds(listMap, "category_id");
        return l;
    }

    public String convertCountSql(RecyclingAdminCategroyQueryCase queryCase,StringBuilder stringBuilder){

        stringBuilder.append(" and parent_id <> 0 ");

        if(StringUtils.isNotBlank(queryCase.getParent_category_id())){
            stringBuilder.append(" and parent_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getParent_category_id()));
        }

        if(StringUtils.isNotBlank(queryCase.getSub_categroy_id())){
            stringBuilder.append(" or category_id in (");
            stringBuilder.append(queryCase.getSub_categroy_id());
            stringBuilder.append(")");

            stringBuilder.append(" or parent_id in(");
            stringBuilder.append(queryCase.getSub_categroy_id());
            stringBuilder.append(")");
        }

        return stringBuilder.toString();
    }

    private String convertFinalSql(RecyclingAdminCategroyQueryCase queryCase,StringBuilder stringBuilder){
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
