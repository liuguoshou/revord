package com.recycling.admin.user.dao;

import com.recycling.admin.user.querycase.RecyclingAdminUserQueryCase;
import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecUser;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.PagerHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminUserDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("recyclingAdminUserDao")
public class RecyclingAdminUserDaoImpl extends GenericDaoImpl implements RecyclingAdminUserDao {

    /**
     * 查询用户列表ID
     * @param queryCase
     * @return
     */
    @Override
    public List<Long> queryRecyclingUser(RecyclingAdminUserQueryCase queryCase) {
    	 //StringBuilder stringBuilder=new StringBuilder(" SELECT u.user_id from rec_user u JOIN rec_area area ON u.area_id = area.area_id JOIN rec_region region ON u.region_id = region.region_id where 1=1 ");
    	 StringBuilder stringBuilder=new StringBuilder(" SELECT u.user_id from rec_user u  JOIN rec_region region ON u.region_id = region.region_id where 1=1 ");
         String sql = convertCountSql(queryCase,stringBuilder);
        List listCount=this.getJdbcTemplate().queryForList(sql);
        if (listCount == null || listCount.size() == 0)
            return null;
        Pager pager = queryCase.getPager();
        pager.setTotalRows(listCount.size());
        stringBuilder.append(" order by u.update_date desc ");
        String finalSql = convertFinalSql(queryCase,stringBuilder);
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(finalSql);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<Long> l = CommonUtils.getListIds(listMap, "user_id");
        return l;
    }

    public String convertCountSql(RecyclingAdminUserQueryCase queryCase,StringBuilder stringBuilder){

        stringBuilder.append(" and u.user_type = '");
        stringBuilder.append("USER");
        stringBuilder.append("' ");

        stringBuilder.append(" and u.user_status = '");
        stringBuilder.append("ACTIVE");
        stringBuilder.append("' ");

      /*  if(StringUtils.isNotBlank(queryCase.getArea_parent_id())){
            stringBuilder.append(" and area.parent_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getArea_parent_id()));
        }
       */
        
        if(StringUtils.isNotBlank(queryCase.getArea_parent_id())){
            stringBuilder.append(" and region.parent_id in ");
            stringBuilder.append( queryCase.getArea_parent_id());
        }
        if(StringUtils.isNotBlank(queryCase.getArea_id())){
            stringBuilder.append(" and area.area_id = ");
            stringBuilder.append(queryCase.getArea_id());
        }

        if(StringUtils.isNotBlank(queryCase.getRegion_parent_id())){
            stringBuilder.append(" and region.parent_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getRegion_parent_id()));
        }

        if(StringUtils.isNotBlank(queryCase.getRegion_id())){
            stringBuilder.append(" and region.region_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getRegion_id()));
        }

        if(StringUtils.isNotBlank(queryCase.getPhone_number())){
            stringBuilder.append(" and u.mobile like '%");
            stringBuilder.append(queryCase.getPhone_number());
            stringBuilder.append("%'");
        }

        if(StringUtils.isNotBlank(queryCase.getReal_name())){
            stringBuilder.append(" and u.real_name like '%");
            stringBuilder.append(queryCase.getReal_name());
            stringBuilder.append("%'");
        }

        return stringBuilder.toString();
    }

    private String convertFinalSql(RecyclingAdminUserQueryCase queryCase,StringBuilder stringBuilder){
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
