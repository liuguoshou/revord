package com.recycling.admin.system.dao;

import com.recycling.admin.ds.AdminGenericDaoImpl;
import com.recycling.admin.system.querycase.AdminSystemUserQueryCase;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.PagerHelper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Title:LesAdminSystemUserDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("lesAdminSystemUserDao")
public class AdminSystemUserDaoImpl extends AdminGenericDaoImpl implements AdminSystemUserDao {

    /**
     * 查询用户列表
     * @param queryCase
     * @return
     */
    @Override
    public List<Long> querySystemUser(AdminSystemUserQueryCase queryCase) {
        StringBuilder stringBuilder=new StringBuilder(" select user_id,user_name,user_pass,user_isvalid,user_realname,addtime from fansenter_admin_user where 1=1 and user_id <> 1 ");
        String sql = stringBuilder.toString();
        List listCount=this.getJdbcTemplate().queryForList(sql);
        if (listCount == null || listCount.size() == 0)
            return null;
        Pager pager = queryCase.getPager();
        pager.setTotalRows(listCount.size());
        PagerHelper.caulatePager(pager, pager.getCurrentPage(), pager.getTotalRows(), pager.getPageSize());
        pager.operatePager();
        stringBuilder.append(" order by addtime desc,user_id DESC ");
        String finalSql = convertFinalSql(stringBuilder,queryCase);
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(finalSql);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<Long> l = CommonUtils.getListIds(listMap, "user_id");
        return l;
    }

    private String convertFinalSql(StringBuilder stringBuilder,AdminSystemUserQueryCase queryCase){
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
