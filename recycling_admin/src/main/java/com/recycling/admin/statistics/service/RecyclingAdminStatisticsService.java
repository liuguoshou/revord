package com.recycling.admin.statistics.service;

import com.recycling.admin.statistics.querycase.RecyclingAdminStatisticsQueryCase;
import com.recycling.common.entity.RecTrxorderDetail;

import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminStatisticsService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminStatisticsService {

    /**
     * 查询订单列表
     * @param queryCase
     * @return
     */
    public Map<String,Object> queryRecyclingTrxorderDetail(RecyclingAdminStatisticsQueryCase queryCase);

    /**
     * 查询订单历史详情
     * @param region_id
     * @param categroy_id
     * @param start
     * @param length
     * @return
     */
    public Map<String,Object> queryRecyclingTrxOrderHistoryDetailList(Long region_id,Long categroy_id,int start,int length,String from_date,String to_date);

    /**
     * 回收详情
     * @param user_id
     * @param start
     * @param length
     * @return
     */
    public Map<String,Object> queryRecyclingPauperDetail(Long user_id,int start,int length);
}
