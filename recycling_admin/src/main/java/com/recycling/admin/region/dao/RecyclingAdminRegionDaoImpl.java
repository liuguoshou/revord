package com.recycling.admin.region.dao;

import com.recycling.admin.auth.dao.AdminUserDao;
import com.recycling.admin.entity.AdminUserArea;
import com.recycling.admin.region.querycase.RecyclingAdminRegionQueryCase;
import com.recycling.admin.user.querycase.RecyclingAdminUserQueryCase;
import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.PagerHelper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminRegionDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("recyclingAdminRegionDao")
public class RecyclingAdminRegionDaoImpl extends GenericDaoImpl implements RecyclingAdminRegionDao {
	  @Autowired
	  private AdminUserDao adminUserDao;
    /**
     * 查询小区列表
     * @param queryCase
     * @return
     */
    @Override
    public List<Long> queryRecyclingRegion(RecyclingAdminRegionQueryCase queryCase) {
    	
    	
    	
    	
        StringBuilder stringBuilder=new StringBuilder("select region_id from rec_region where 1=1");
        String sql = convertCountSql(queryCase,stringBuilder);
        List listCount=this.getJdbcTemplate().queryForList(sql);
        if (listCount == null || listCount.size() == 0)
            return null;
        Pager pager = queryCase.getPager();
        pager.setTotalRows(listCount.size());
        stringBuilder.append(" order by update_date desc,parent_id asc ");
        String finalSql = convertFinalSql(queryCase,stringBuilder);
        List<Map<String, Object>> listMap=this.getJdbcTemplate().queryForList(finalSql);
        if (listMap == null || listMap.size() == 0)
            return null;
        List<Long> l = CommonUtils.getListIds(listMap, "region_id");
        return l;
    }
    public String convertCountSql(RecyclingAdminRegionQueryCase queryCase,StringBuilder stringBuilder){
	
    	
    	//根据权限查询小区列表
    	
    	long tt = queryCase.getUserId();
    	int it= (int) tt;
    	//用户权限管理
    	AdminUserArea uaserArea = adminUserDao.queryAdminUserArea(it);
    	String userAreaIds="";
    	if(uaserArea.getAreaType()==5){//小区Id
    		StringBuffer sbf = new StringBuffer();
    		String [] ids = uaserArea.getAreaIds().split(",");
    		sbf.append("");
    		for (String id : ids) {
    			sbf.append(id);
    			sbf.append(",");
			}
    		userAreaIds = sbf.substring(0, sbf.length()-1);
    		stringBuilder.append(" and region_id in( ");
        	stringBuilder.append(userAreaIds );
        	stringBuilder.append(")");
    	}else{							//地区Id
    		userAreaIds= getParentIds(Long.parseLong(uaserArea.getAreaIds()));
    		stringBuilder.append(" and parent_id in "+userAreaIds+" ");
    	}    
    	
        if(StringUtils.isNotBlank(queryCase.getRegion_parent_id())){
            /*stringBuilder.append(" and parent_id = ");
            stringBuilder.append(Long.valueOf(queryCase.getRegion_parent_id()));
            */
        	stringBuilder.append(" and parent_id in ");
        	String inSql =getParentIds(Long.valueOf(queryCase.getRegion_parent_id()));
        	stringBuilder.append(inSql);
            
        }


        return stringBuilder.toString();
    }
    
    /**
     *  根据父Id,得到此节点下的所有街道Id
     * @param parentId
     * @return
     */
    @Override
    public  String getParentIds(Long parentId){
    	StringBuffer sbf= new StringBuffer("(");
    	 List <Map<String, Object>>list = getParentList(parentId);
    	 if(list==null || list.size()<1){
    		 sbf.append(parentId);
    	 }else{
    		 for (Map<String, Object> map : list) {
    			 
    			 if("4".equals(map.get("area_type").toString())){
    				 sbf.append(map.get("area_id"));
    				 sbf.append(",");
    			 }
			}
    		sbf.append("-1");
    	 }
    	sbf.append(")");
    	return sbf.toString();
    }
    
    //获取parentId节点下所有街道的Id
    private List<Map<String, Object>> getParentList(Long parentId){
    	
    	String sql= "select area_id,parent_id,area_type from rec_area where parent_id="+parentId.toString()+" and is_active=1 and is_online=1";
    	
    	List<Map<String, Object>> list=this.getJdbcTemplate().queryForList(sql);
    	List<Map<String, Object>> listTemp = new ArrayList<Map<String,Object>>();
    	if(list!=null && list.size()>0){
    		for (Map<String, Object> map : list) {
    			if( !"4".equals(map.get("area_type") )){
	       			 List <Map<String, Object>>list1 = getParentList( Long.parseLong(map.get("area_id").toString()));
	       			 if(list1!=null && list.size()>0){
	       				listTemp.addAll(list1);
	       			 }
    			}
			}
    	}
    	list.addAll(listTemp);
    	return list;
    }
    

    private String convertFinalSql(RecyclingAdminRegionQueryCase queryCase,StringBuilder stringBuilder){
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
