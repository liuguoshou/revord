package com.recycling.dao.trxorder;

import java.util.List;

import com.recycling.common.bean.RecTrxorderQueryBean;
import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.Pager;


/**
 * Description : 订单Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:16:42 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecTrxorderDao {

	public Long addRecTrxorder(RecTrxorder trxorder);
	
	public boolean updateTrxorder(RecTrxorder trxorder) throws StaleObjectStateException;
	
	public RecTrxorder getByTrxorderId(Long trxorderId);
	
	public List<RecTrxorder> getByUserId(Long userId,Pager pager);
	
	public List<RecTrxorder> getByCollectId(Long collectId,Pager pager);
	
	public int getCountByQueryParam(RecTrxorderQueryBean queryParam);
	
	public List<RecTrxorder> getListByQueryParam(RecTrxorderQueryBean queryParam);
	
	/**
	 *查询用户未完成订单
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public RecTrxorder getUserProcessOrder(Long userId,String startDate,String endDate);

    /**
     * 重置订单状态
     * @param orderIds
     * @return
     */
    public Integer resetTrxOrderStatus(String orderIds);
}
