package com.recycling.admin.system.dao;

import com.recycling.admin.ds.AdminGenericDaoImpl;
import com.recycling.admin.system.querycase.AdminSystemMenuQueryCase;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.PagerHelper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Title:LesAdminSystemMenuDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("lesAdminSystemMenuDao")
public class AdminSystemMenuDaoImpl extends AdminGenericDaoImpl implements AdminSystemMenuDao {
    @Override
    public List<Long> querySystemMenu(AdminSystemMenuQueryCase queryCase) {
        StringBuilder stringBuilder=new StringBuilder(" SELECT menu_id from fansenter_admin_menu where menu_parent = 0 AND menu_id <> 1 ");
        String sql = stringBuilder.toString();
        List listCount=this.getJdbcTemplate().queryForList(sql);
        if (listCount == null || listCount.size() == 0)
            return null;
        Pager pager = queryCase.getPager();
        pager.setTotalRows(listCount.size());
        PagerHelper.caulatePager(pager, pager.getCurrentPage(), pager.getTotalRows(), pager.getPageSize());
        pager.operatePager();
        stringBuilder.append(" order by menu_id asc ");
        String finalSql = convertFinalSql(stringBuilder,queryCase);
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(finalSql);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<Long> l = CommonUtils.getListIds(listMap, "menu_id");
        return l;
    }

    private String convertFinalSql(StringBuilder stringBuilder,AdminSystemMenuQueryCase queryCase){
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
