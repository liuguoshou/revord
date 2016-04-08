package com.recycling.service.trxorder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.bean.RecTrxorderQueryBean;
import com.recycling.common.entity.RecAccount;
import com.recycling.common.entity.RecBeggarAccount;
import com.recycling.common.entity.RecMessage;
import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.entity.RecUser;
import com.recycling.common.enums.ActHistoryType;
import com.recycling.common.enums.BeggarHistoryType;
import com.recycling.common.enums.IntegralHistoryType;
import com.recycling.common.enums.SettleStatus;
import com.recycling.common.enums.TrxStatus;
import com.recycling.common.enums.UserType;
import com.recycling.common.exception.SimpleException;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.Amount;
import com.recycling.common.util.MobilePurseSecurityUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.StringUtil;
import com.recycling.dao.message.RecMessageDao;
import com.recycling.dao.trxorder.RecTrxorderDao;
import com.recycling.dao.trxorder.RecTrxorderDetailDao;
import com.recycling.service.account.RecAccountService;
import com.recycling.service.beggar.RecBeggarAccountService;
import com.recycling.service.user.RecUserService;

/**
 * Description : TODO <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:12:51 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Service("recTrxorderService")
public class RecTrxorderServiceImpl implements RecTrxorderService{

	@Autowired
	private RecTrxorderDao recTrxorderDao;
	
	@Autowired
	private RecTrxorderDetailDao recTrxorderDetailDao;
	
	@Autowired
	private RecAccountService recAccountService;
	
	@Autowired
	private RecUserService recUserService;
	
	@Autowired
	private RecBeggarAccountService recBeggarAccountService;
	
	@Autowired
	private RecMessageDao recMessageDao;
	
	@Override
	public Long addRecTrxorder(RecTrxorder trxorder,RecMessage message,List<RecTrxorderDetail> detailList) {
		if(StringUtil.isBlank(trxorder.getRequestId())){
			String requestId = MobilePurseSecurityUtils.generateOrderNumber("Req");
			trxorder.setRequestId(requestId);
		}
		Long trxorderId = recTrxorderDao.addRecTrxorder(trxorder);
		message.setTrxorderId(trxorderId);
		recMessageDao.addRecMessage(message);
		if(null != detailList && detailList.size() > 0){
			for (RecTrxorderDetail recTrxorderDetail : detailList) {
				recTrxorderDetail.setTrxorderId(trxorderId);
			}
			recTrxorderDetailDao.addTrxorderDetailList(detailList);
		}
		return trxorderId;
	}

	@Override
	public boolean updateTrxorder(RecTrxorder trxorder,List<RecTrxorderDetail> detailList)throws StaleObjectStateException {
		if(null != detailList && detailList.size() > 0){
			for (RecTrxorderDetail recTrxorderDetail : detailList) {
				recTrxorderDetail.setTrxStatus(trxorder.getTrxStatus());
				recTrxorderDetail.setUpdateDate(trxorder.getUpdateDate());
			}
			recTrxorderDetailDao.updateTrxorderDetailList(detailList);
		}
		return recTrxorderDao.updateTrxorder(trxorder);
	}

	@Override
	public RecTrxorder getByTrxorderId(Long trxorderId) {
		return recTrxorderDao.getByTrxorderId(trxorderId);
	}

	@Override
	public void complateTrxorder(Long trxorderId)throws StaleObjectStateException,SimpleException {
		Date nowTime = new Date();
		// 更新订单状态，返回成功页面
		RecTrxorder trxorder = recTrxorderDao.getByTrxorderId(trxorderId);
		
		if(null != trxorder && TrxStatus.SUCCESS != trxorder.getTrxStatus()){
			trxorder.setUpdateDate(nowTime);
			trxorder.setTrxStatus(TrxStatus.SUCCESS);
			trxorder.setSettleStatus(SettleStatus.UNSETTLE);
			recTrxorderDao.updateTrxorder(trxorder);
			List<RecTrxorderDetail> detailList = recTrxorderDetailDao.getByTrxorderId(trxorderId);
			if(null != detailList && detailList.size() > 0){
				for (RecTrxorderDetail recTrxorderDetail : detailList) {
					recTrxorderDetail.setUpdateDate(nowTime);
					recTrxorderDetail.setTrxStatus(TrxStatus.SUCCESS);
				}
				recTrxorderDetailDao.updateTrxorderDetailList(detailList);
			}
			//用户账户出入帐
			RecAccount userAccount = recAccountService.getByUserId(trxorder.getUserId());
			recAccountService.credit(userAccount, trxorder.getTrxAmount(), trxorderId, ActHistoryType.SALES, "");
			//再次查询 防止乐观锁
			userAccount = recAccountService.getByUserId(trxorder.getUserId());
			recAccountService.debit(userAccount, trxorder.getTrxAmount(), trxorderId, ActHistoryType.SETTLE, "");
			
			//乞丐账户入账（乞丐线下把钱给了用户 在这里做充值）
			RecBeggarAccount pauperAccount = recBeggarAccountService.getByBeggarId(trxorder.getCollectId());
			recBeggarAccountService.credit(pauperAccount, trxorder.getTrxAmount(), trxorderId, BeggarHistoryType.LOAD, "");
		
			//用户积分计算 一毛钱一分
			RecAccount integralAccount = recAccountService.getByUserId(trxorder.getUserId());
			Double dIntegral = Amount.mul(trxorder.getTrxAmount(), 10);
			Long trxIntegral = new BigDecimal(dIntegral).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			recAccountService.creditIntegral(integralAccount, trxIntegral, trxorderId, IntegralHistoryType.RECYCLE_GARBAGE, "回收废品赠送积分");
			
		}
		
	}

    /**
     * 重置订单状态
     * @param orderIds
     * @return
     */
    @Override
    public Integer resetTrxOrderStatus(String orderIds) {
        return recTrxorderDao.resetTrxOrderStatus(orderIds);
    }

    @Override
	public List<RecTrxorder> getByUserId(Long userId, Pager pager) {
		return recTrxorderDao.getByUserId(userId, pager);
	}

	@Override
	public List<RecTrxorder> getByCollectId(Long collectId, Pager pager) {
		return recTrxorderDao.getByCollectId(collectId, pager);
	}

	@Override
	public List<RecTrxorder> getListByQueryParam(RecTrxorderQueryBean queryParam) {
		//订单表中无地域ID 转换成userId来查
		if(null != queryParam.getRegionId()){
			List<RecUser> userList = recUserService.getUserByRegionId(queryParam.getRegionId(), UserType.USER);
			if(null == userList || userList.size() == 0){
				return Collections.emptyList();
			}
			Set<Long> userIdSet = new HashSet<Long>();
			for (RecUser recUser : userList) {
				userIdSet.add(recUser.getUserId());
			}
			String userIds = StringUtil.arrayToString(userIdSet.toArray(), ",");
			queryParam.setUserIds(userIds);
		}
		return recTrxorderDao.getListByQueryParam(queryParam);
	}

	@Override
	public boolean editTrxorder(RecTrxorder trxorder,List<RecTrxorderDetail> detailList)throws StaleObjectStateException {
		if(null == trxorder || null == detailList || detailList.size() ==0){
			return false;
		}
		//简单处理 先删除明细 重新增加 此种编辑订单明细的方法暂时不用
//		List<RecTrxorderDetail> dbDetailList = recTrxorderDetailDao.getByTrxorderId(trxorder.getTrxorderId());
//		//数据库中的订单明细列表
//		Set<Long> dbdetailIdSet = new HashSet<Long>();
//		for (RecTrxorderDetail dbDetailInfo : dbDetailList) {
//			dbdetailIdSet.add(dbDetailInfo.getTrxorderDetailId());
//		}
//		double trxTotalAmount = 0.0d;
//		//垃圾手机人员编辑后的订单明细
//		for (RecTrxorderDetail editDetailInfo : detailList) {
//			double singleTrxAmount = Amount.mul(editDetailInfo.getPrice(), editDetailInfo.getCount());
//			trxTotalAmount = Amount.add(trxTotalAmount, singleTrxAmount);
//			if(editDetailInfo.getTrxorderDetailId() == null){
//				recTrxorderDetailDao.addTrxorderDetail(editDetailInfo);
//			}else{
//				//更新订单明细
//				recTrxorderDetailDao.updateTrxorderDetail(editDetailInfo);
//				dbdetailIdSet.remove(editDetailInfo.getTrxorderDetailId()); 
//			}
//		}
//		//将订单明细中删除的数据从 数据库清除
//		if(!dbdetailIdSet.isEmpty()){
//			for (Long detailId : dbdetailIdSet) {
//				recTrxorderDetailDao.deleteByDetailId(detailId);
//			}
//		}
		double trxTotalAmount = 0.0d;
		for (RecTrxorderDetail editDetailInfo : detailList) {
			double singleTrxAmount = Amount.mul(editDetailInfo.getPrice(), editDetailInfo.getCount());
			trxTotalAmount = Amount.add(trxTotalAmount, singleTrxAmount);
		}
		recTrxorderDetailDao.deleteByTrxorderId(trxorder.getTrxorderId());
		
		trxorder.setTrxAmount(trxTotalAmount);
		recTrxorderDao.updateTrxorder(trxorder);
		recTrxorderDetailDao.addTrxorderDetailList(detailList);
		return true;
	}

	@Override
	public int getCountByQueryParam(RecTrxorderQueryBean queryParam) {
		//订单表中无地域ID 转换成userId来查
		if(null != queryParam.getRegionId()){
			List<RecUser> userList = recUserService.getUserByRegionId(queryParam.getRegionId(), UserType.USER);
			if(null == userList || userList.size() == 0){
				return 0;
			}
			Set<Long> userIdSet = new HashSet<Long>();
			for (RecUser recUser : userList) {
				userIdSet.add(recUser.getUserId());
			}
			String userIds = StringUtil.arrayToString(userIdSet.toArray(), ",");
			queryParam.setUserIds(userIds);
		}
		return recTrxorderDao.getCountByQueryParam(queryParam);
	}

	@Override
	public RecTrxorder getUserProcessOrder(Long userId, String startDate,String endDate) {
		return recTrxorderDao.getUserProcessOrder(userId, startDate, endDate);
	}
	
}
