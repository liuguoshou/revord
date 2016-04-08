package com.recycling.service.trxorder;

import java.util.List;

import com.recycling.common.bean.RecTrxorderQueryBean;
import com.recycling.common.entity.RecMessage;
import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.Pager;

/**
 * Description : 订单service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:11:48 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecTrxorderService {

	public Long addRecTrxorder(RecTrxorder trxorder,RecMessage message,List<RecTrxorderDetail> detailList);
	
	/**
	 * 仅更新订单状态 不含其它业务逻辑
	 */
	public boolean updateTrxorder(RecTrxorder trxorder,List<RecTrxorderDetail> detailList) throws StaleObjectStateException;
	
	/**
	 * 编辑订单（订单金额计算 明细增删等等）
	 */
	public boolean editTrxorder(RecTrxorder trxorder,List<RecTrxorderDetail> detailList) throws StaleObjectStateException;
	
	public RecTrxorder getByTrxorderId(Long trxorderId);
	
	public List<RecTrxorder> getByUserId(Long userId,Pager pager);
	
	public List<RecTrxorder> getByCollectId(Long collectId,Pager pager);
	
	public int getCountByQueryParam(RecTrxorderQueryBean queryParam);
	
	public List<RecTrxorder> getListByQueryParam(RecTrxorderQueryBean queryParam);
	
	/**
	 * 订单完成 更新订单状态，账户历史（账户出入张） 账户积分等
	 * @author XiaoXian Xu 
	 * @param trxorderId
	 */
	public void complateTrxorder(Long trxorderId);

    /**
     * 重置订单状态
     * @param orderIds
     * @return
     */
    public Integer resetTrxOrderStatus(String orderIds);
	
	public RecTrxorder getUserProcessOrder(Long userId, String startDate,String endDate);
	
}
