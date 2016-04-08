package com.recycling.dao.trxorder;

import java.util.List;
import java.util.Map;

import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 订单明细Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:18:08 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecTrxorderDetailDao {
	
	public void addTrxorderDetailList(List<RecTrxorderDetail> detailList);
	
	public List<RecTrxorderDetail> getByTrxorderId(Long trxorderId);
	
	public void updateTrxorderDetailList(List<RecTrxorderDetail> detailList);
	
	public Long addTrxorderDetail(RecTrxorderDetail trxorderDetail);
	
	public void updateTrxorderDetail(RecTrxorderDetail trxorderDetail) throws StaleObjectStateException;
	
	public void deleteByDetailId(Long detailId);
	
	public void deleteByTrxorderId(Long trxorderId);

    /**
     * 回收统计列表
     * @param region_parent_id
     * @param region_id
     * @param categroy_parent_id
     * @param categroy_id
     * @param fromDate
     * @param toDate
     * @param start
     * @param length
     * @return
     */
    public Map<String,Object> queryStatistics(String region_parent_id,String region_id,String categroy_parent_id,String categroy_id,String fromDate,String toDate,int start,int length,String categroy_sub_ids,String collect_id);

    /**
     * 查询统计详情
     * @param region_id
     * @param categroy
     * @param start
     * @param length
     * @return
     */
    public Map<String,Object> queryDetailHistory(Long region_id,Long categroy,int start,int length,String from_date,String to_date);

    /**
     * 回收统计
     * @param user_id
     * @param start
     * @param length
     * @return
     */
    public Map<String,Object> queryDetailHistoryByUserId(Long user_id, int start, int length);
}
