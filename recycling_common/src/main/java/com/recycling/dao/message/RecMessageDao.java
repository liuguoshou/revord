package com.recycling.dao.message;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.recycling.common.entity.RecMessage;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.Pager;

/**
 * Description : 用户发单请求Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:13:56 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecMessageDao {
	
	public Long addRecMessage(RecMessage recMessage);
	
	public boolean updateRecMessage(RecMessage recMessage) throws StaleObjectStateException;
	
	public RecMessage getByMessageId(Long messageId);
	
	public RecMessage getByTrxorderId(Long trxorderId);
	
	public int getMesssageCount(Set<Long> regionIdSet,Long collectId,String startDate,String endDate);
	
	public List<RecMessage> getMesssageList(Set<Long> regionIdSet,Long collectId,String startDate,String endDate,Pager pager);
	
	/**
	 *查询用户未完成的回收请求数
	 */
	public int queryUserUnComplateMsg(Long userId,String startDate,String endDate);
    /**
     * 查询前一天乞丐未接单的订单及消息
     * @param fromDate
     * @param toDate
     * @return
     */
    public Map<String,List<Long>> queryInitMessageList(String fromDate, String toDate);

    /**
     * 重置消息状态
     * @param messageIds
     * @return
     */
    public Integer resetMessageStatus(String messageIds);
}
