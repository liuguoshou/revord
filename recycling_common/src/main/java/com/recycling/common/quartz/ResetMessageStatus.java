package com.recycling.common.quartz;

import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.DateUtil;
import com.recycling.service.message.RecMessageService;
import com.recycling.service.trxorder.RecTrxorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title:ResetMessageStatus.java
 * @Description:重置message及订单表状态
 * @author:xu.he
 * @version:v1.0
 */
@Component
public class ResetMessageStatus {


    @Autowired
    private RecMessageService recMessageService;

    @Autowired
    private RecTrxorderService recTrxorderService;

    @Scheduled(cron="0 15 0 * * ?")
    public void resetRecMessagesStatus(){
        Date date= DateUtil.getBeforeDate(new Date());
        String fromDate=DateUtil.formatDate(date,"yyyy-MM-dd 00:00:00");
        String toDate=DateUtil.formatDate(date,"yyyy-MM-dd 23:59:59");
        Map<String,List<Long>> resultMap=recMessageService.queryInitMessageList(fromDate,toDate);
        List<Long> messageList=resultMap.get("messageList");
        List<Long> orderList=resultMap.get("orderList");
        String messageIds= CommonUtils.listToString(messageList);
        String orderIds=CommonUtils.listToString(orderList);
        recTrxorderService.resetTrxOrderStatus(orderIds);
        recMessageService.resetMessageStatus(messageIds);
    }
}
