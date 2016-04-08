package com.recycling.service.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.bean.RecTrxorderQueryBean;
import com.recycling.common.entity.RecMessage;
import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.enums.TrxStatus;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.Amount;
import com.recycling.common.util.DateUtil;
import com.recycling.common.util.Pager;
import com.recycling.common.util.StringUtil;
import com.recycling.dao.message.RecMessageDao;
import com.recycling.service.trxorder.RecTrxorderService;

@Service("recMessageService")
public class RecMessageServiceImpl implements RecMessageService{

	@Autowired
	private RecMessageDao recMessageDao;
	
	@Autowired
	private RecTrxorderService recTrxorderService;
	
	@Override
	public Long addRecMessage(RecMessage recMessage) {
		return  recMessageDao.addRecMessage(recMessage);
	}

	@Override
	public boolean updateRecMessage(RecMessage recMessage,RecTrxorder trxorder,List<RecTrxorderDetail> detailList)throws StaleObjectStateException {
		recTrxorderService.updateTrxorder(trxorder, detailList);
		return recMessageDao.updateRecMessage(recMessage);
	}

	@Override
	public RecMessage getByMessageId(Long messageId) {
		return recMessageDao.getByMessageId(messageId);
	}

	@Override
	public List<RecMessage> getMesssageList(Set<Long> regionIdSet,Long collectId,String startDate,String endDate,Pager pager){
		List<RecMessage> messageList = recMessageDao.getMesssageList(regionIdSet, collectId, startDate, endDate,pager);

		//将订单状态填充到message中
		if(null != messageList && messageList.size() > 0){
			RecTrxorderQueryBean query = new RecTrxorderQueryBean();
			Set<Long> trxorderIdSet = new HashSet<Long>();
			for (RecMessage message : messageList) {
				message.setCreateDateStr(DateUtil.formatDate(message.getCreateDate(), "yyyy-MM-dd"));
				if(null != message.getTrxorderId() && message.getTrxorderId().compareTo(0L) != 0){
					trxorderIdSet.add(message.getTrxorderId());
				}
			}
			if(!trxorderIdSet.isEmpty()){
				query.setTrxorderIds(StringUtil.arrayToString(trxorderIdSet.toArray(), ","));
				List<RecTrxorder> trxorderList = recTrxorderService.getListByQueryParam(query);
				if(null != trxorderList && trxorderList.size() > 0){
					Map<Long,RecTrxorder> trxStatusMap = new HashMap<Long,RecTrxorder>();
					for (RecTrxorder trxorder : trxorderList) {
						trxStatusMap.put(trxorder.getTrxorderId(), trxorder);
					}
					for (RecMessage message : messageList) {
						if(trxStatusMap.containsKey(message.getTrxorderId())){
							RecTrxorder order = trxStatusMap.get(message.getTrxorderId());
							message.setTrxStatus(order.getTrxStatus());
							if(Amount.bigger(order.getTrxAmount(),0) && (order.getTrxStatus() == TrxStatus.PROCESSING ||
									order.getTrxStatus() == TrxStatus.CONFIRM || order.getTrxStatus() == TrxStatus.SUCCESS)){
								message.setTrxAmountStr(order.getTrxAmount()+"");
							}
						}
					}
				}
			}
		}
		 return messageList;
	}

	@Override
	public int queryUserUnComplateMsg(Long userId,String startDate,String endDate) {
		return recMessageDao.queryUserUnComplateMsg(userId,startDate,endDate);
	}

    /**
     * 查询前一天乞丐未接单的订单及消息
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public Map<String, List<Long>> queryInitMessageList(String fromDate, String toDate) {
        return recMessageDao.queryInitMessageList( fromDate,  toDate);
    }

    /**
     * 重置消息状态
     * @param messageIds
     * @return
     */
    @Override
    public Integer resetMessageStatus(String messageIds) {
        return recMessageDao.resetMessageStatus(messageIds);
    }

    @Override
	public int getMesssageCount(Set<Long> regionIdSet, Long collectId,String startDate, String endDate) {
		return recMessageDao.getMesssageCount(regionIdSet, collectId, startDate, endDate);
	}

	@Override
	public RecMessage getByTrxorderId(Long trxorderId) {
		return recMessageDao.getByTrxorderId(trxorderId);
	}

}
