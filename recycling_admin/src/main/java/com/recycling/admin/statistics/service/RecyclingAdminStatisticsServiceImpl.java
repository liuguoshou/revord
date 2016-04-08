package com.recycling.admin.statistics.service;

import com.recycling.admin.statistics.querycase.RecyclingAdminStatisticsQueryCase;
import com.recycling.common.entity.RecCategory;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.StringUtil;
import com.recycling.dao.category.RecCategoryDao;
import com.recycling.dao.region.RecRegionDao;
import com.recycling.dao.trxorder.RecTrxorderDetailDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Title:RecyclingAdminStatisticsServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("recyclingAdminStatisticsService")
public class RecyclingAdminStatisticsServiceImpl implements RecyclingAdminStatisticsService {

    @Autowired
    private RecTrxorderDetailDao recTrxorderDetailDao;

    @Autowired
    private RecRegionDao recRegionDao;

    @Autowired
    private RecCategoryDao recCategoryDao;

    /**
     * 查询订单详情列表
     * @param queryCase
     * @return
     */
    @Override
    public Map<String,Object> queryRecyclingTrxorderDetail(RecyclingAdminStatisticsQueryCase queryCase) {
        List<Long> idList=new ArrayList<Long>();
        String ids="";
        if(StringUtils.isNotBlank(queryCase.getCategroy_parent_id())){
            idList=getCategroySubIds(Long.valueOf(queryCase.getCategroy_parent_id()), idList);
            if(idList!=null && idList.size() > 0){
                ids= CommonUtils.listToString(idList);
            }
        }
        queryCase.setCategrou_sub_ids(ids);


        Map<String,Object> resMap=recTrxorderDetailDao.queryStatistics(queryCase.getRegion_parent_id(), queryCase.getRegion_id()
                , queryCase.getCategroy_parent_id(), queryCase.getCategroy_id(), queryCase.getFromDate(), queryCase.getToDate(),
                queryCase.getStart(), queryCase.getLength(),queryCase.getCategrou_sub_ids(),queryCase.getCollect_id());

        if(resMap!=null){
            List<Map<String,Object>> resultList= (List<Map<String, Object>>) resMap.get("result");
            Set<Long> rpidList=new HashSet<Long>();
            Set<Long> cpidList=new HashSet<Long>();
            //待优化
//            RecRegion parentRegion=recRegionDao.getByRegionId(rpid,0,1);
//            RecCategory parentCategroy=recCategoryDao.getByCategoryId()
            Double totalMoney=0.0;
            for (Map<String,Object> map:resultList){
                Long rpid= (Long) map.get("rpid");
                Long cpid= (Long) map.get("cpid");
                BigDecimal price= (BigDecimal) map.get("price");
                BigDecimal total= (BigDecimal) map.get("total");
                Double money=price.multiply(total).doubleValue();
                rpidList.add(rpid);
                cpidList.add(cpid);
                map.put("money",money);
                totalMoney+=money;
            }

            String rpidIds=CommonUtils.listToString(new ArrayList<Long>(rpidList));
            String cpidIds=CommonUtils.listToString(new ArrayList<Long>(cpidList));

            Map<Long,String>  rpidMap=recRegionDao.getParentRegionCnName(rpidIds);
            Map<Long,String>  cpidMap=recCategoryDao.getCategroyNameByIds(cpidIds);

            for (Map<String,Object> map:resultList){
                Long rpid= (Long) map.get("rpid");
                Long cpid= (Long) map.get("cpid");
                String regionName= rpidMap.get(rpid);
                String categroyName= cpidMap.get(cpid);
                map.put("regionName",regionName);
                map.put("categroyName",categroyName);
            }
            resMap.put("totalMoney",totalMoney);
        }else{
            resMap=new HashMap<String, Object>();
            resMap.put("result",null);
            resMap.put("totalCount",0);
            resMap.put("totalMoney",0.00);
        }
        return resMap;
    }

    /**
     * 查询订单历史详情
     * @param region_id
     * @param categroy_id
     * @param start
     * @param length
     * @return
     */
    @Override
    public Map<String, Object> queryRecyclingTrxOrderHistoryDetailList(Long region_id, Long categroy_id,int start,int length,String from_date,String to_date) {
        Map<String, Object> map=null;
        map=recTrxorderDetailDao.queryDetailHistory(region_id, categroy_id, start, length,from_date,to_date);
        if(map == null || map.size() == 0){
            map=new HashMap<String, Object>();
            map.put("totalCount",0);
            map.put("result",null);
        }
        return map;
    }

    /**
     * 回收详情
     * @param user_id
     * @param start
     * @param length
     * @return
     */
    @Override
    public Map<String, Object> queryRecyclingPauperDetail(Long user_id, int start, int length) {

        Map<String, Object> objectMap = null;
        objectMap=recTrxorderDetailDao.queryDetailHistoryByUserId(user_id, start, length);
        if(objectMap==null || objectMap.size() == 0){
            objectMap = new HashMap<String, Object>();
            objectMap.put("result",null);
            objectMap.put("totalCount",0);
        }
        return objectMap;
    }

    private RecCategory getTopCategroy(Long categroy_id){
        RecCategory category=recCategoryDao.getByCategoryId(categroy_id);
        if(category.getParentId()!=0){
            category=getTopCategroy(category.getParentId());
        }
        return category;
    }

    private List<Long> getCategroySubIds(Long parent_categroy_id,List<Long> idList){
        List<RecCategory>  recCategoryList=recCategoryDao.getByParentId(parent_categroy_id);
        if(recCategoryList != null && recCategoryList.size() > 0){
            for (RecCategory recCategory:recCategoryList){
                idList.add(recCategory.getCategoryId());
                getCategroySubIds(recCategory.getCategoryId(),idList);
            }
        }
        return idList;
    }
}
