package com.recycling.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recycling.action.common.BaseRecAction;
import com.recycling.common.bean.RecTrxorderQueryBean;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecAccount;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecCategory;
import com.recycling.common.entity.RecIntegralHistory;
import com.recycling.common.entity.RecMessage;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.entity.RecUser;
import com.recycling.common.entity.RecUserAdvise;
import com.recycling.common.enums.BeggarStatus;
import com.recycling.common.enums.PushStatus;
import com.recycling.common.enums.TrxStatus;
import com.recycling.common.enums.UserStatus;
import com.recycling.common.exception.SimpleException;
import com.recycling.common.exception.UserException;
import com.recycling.common.util.DateUtil;
import com.recycling.common.util.Pager;
import com.recycling.common.util.StringUtil;
import com.recycling.common.weixin.entity.res.TemplateMessageValue;
import com.recycling.common.weixin.utils.TemplateMessageUtil;
import com.recycling.service.account.RecAccountService;
import com.recycling.service.advise.RecUserAdviseService;
import com.recycling.service.area.RecAreaService;
import com.recycling.service.beggar.RecBeggarAddressService;
import com.recycling.service.beggar.RecBeggarService;
import com.recycling.service.category.RecCategoryService;
import com.recycling.service.history.RecIntegralHistoryService;
import com.recycling.service.message.RecMessageService;
import com.recycling.service.region.RecRegionService;
import com.recycling.service.trxorder.RecTrxorderDetailService;
import com.recycling.service.trxorder.RecTrxorderService;
import com.recycling.service.user.RecUserService;

/**
 * Description : 用户相关业务处理Action <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午4:46:17 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Controller
public class RecUserAction extends BaseRecAction{

	Logger logger = Logger.getLogger(RecUserAction.class);
	
	@Autowired
	private RecUserService recUserService;
	
	@Autowired
	private RecAreaService recAreaService;
	
	@Autowired
	private RecRegionService recRegionService;
	
	@Autowired
	private RecCategoryService recCategoryService;
	
	@Autowired
	private RecMessageService recMessageService;
	
	@Autowired
	private RecUserAdviseService recUserAdviseService;
	
	@Autowired
	private RecAccountService recAccountService;
	
	@Autowired
	private RecIntegralHistoryService recIntegralHistoryService;
	
	@Autowired
	private RecTrxorderService recTrxorderService;
	
	@Autowired
	private RecTrxorderDetailService recTrxorderDetailService;
	
	@Autowired
	private RecBeggarService recBeggarService;
	
	@Autowired
	private RecBeggarAddressService recBeggarAddressService;
	/**
	 * 回收废品首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/userIndex.do")
	public String userIndex(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{

			RecUser loginUser = recUserService.getLoginUser(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			RecAccount account = recAccountService.getByUserId(loginUser.getUserId());
			if(loginUser.getUserStatus() == UserStatus.ACTIVE){
				String userAddress = getUserAddress(loginUser);
				request.setAttribute("userAddress", userAddress);
			}
			String startDate = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyy-MM-dd 00:00:00");
			String endDate = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyy-MM-dd 23:59:59");
			RecTrxorder recTrxorder = recTrxorderService.getUserProcessOrder(loginUser.getUserId(), startDate, endDate);
			if(null != recTrxorder){
				RecBeggar beggar = recBeggarService.getByBeggarrId(recTrxorder.getCollectId());
				request.setAttribute("beggarInfo", beggar);
			}
			request.setAttribute("recTrxorder", recTrxorder);
			request.setAttribute("loginUser", loginUser);
			request.setAttribute("account", account);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "user/userIndex";
	}
	
	/**
	 *我的账户
	 */
	@RequestMapping("/user/accountInfo.do")
	public String accountInfo(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			RecUser loginUser = recUserService.getLoginUser(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			RecAccount account = recAccountService.getByUserId(loginUser.getUserId());
			request.setAttribute("loginUser", loginUser);
			request.setAttribute("account", account);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "user/accountInfo";
	}
	
	/**
	 *积分历史
	 */
	@RequestMapping("/user/showIntegralHistory.do")
	public void showIntegralHistory(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		try {
			out = response.getWriter();
			RecUser loginUser = recUserService.getLoginUser(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			String currentPage = request.getParameter("currentPage");
			int curPage = StringUtil.isBlank(currentPage) ? 1 : Integer.parseInt(currentPage);
			
			RecAccount recAccount = recAccountService.getByUserId(loginUser.getUserId());
			if(null == recAccount){
				throw new UserException(UserException.ACCOUNT_NOT_FOUND);
			}
			int totalCount = recIntegralHistoryService.getHistoryCount(recAccount.getAccountId());
			if(totalCount  > 0){
				 Pager pager = new Pager(curPage,totalCount,RecConstants.PAGE_SIZE_SMALL);
				List<RecIntegralHistory> historyList = recIntegralHistoryService.getHistoryByAccountId(recAccount.getAccountId(), pager);
				for (RecIntegralHistory recIntegralHistory : historyList) {
					recIntegralHistory.setCreateDateStr(DateUtil.formatDate(recIntegralHistory.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
				}
				rspMap.put("currentPage", pager.getCurrentPage());
				rspMap.put("totalPage", pager.getTotalPages());
				rspMap.put("historyList", historyList);
			}
			rspMap.put("totalCount", totalCount);
		}catch (SimpleException simple) {
			simple.printStackTrace();
			rspCode = simple.getErrCode();
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = SimpleException.SYS_ERROR;
		}finally{
			rspMap.put("rspCode", rspCode);
			rspMap.put("rspMsg", SimpleException.errMap.get(rspCode));
			String rspResult = JSONObject.toJSONString(rspMap);
			logger.info("+++++ rec_showIntegralHistory_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}
	
	/**
	 *回收记录
	 */
	@RequestMapping("/user/showTrxorderList.do")
	public void showTrxorderList(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		try {
			out = response.getWriter();
			RecUser loginUser = recUserService.getLoginUser(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			String currentPage = request.getParameter("currentPage");
			int curPage = StringUtil.isBlank(currentPage) ? 1 : Integer.parseInt(currentPage);
			RecTrxorderQueryBean query = new RecTrxorderQueryBean();
			query.setUserIds(loginUser.getUserId()+"");
			int totalCount = recTrxorderService.getCountByQueryParam(query);
			if(totalCount  > 0){
				 Pager pager = new Pager(curPage,totalCount,RecConstants.PAGE_SIZE_SMALL);
				List<RecTrxorder> trxorderList = recTrxorderService.getByUserId(loginUser.getUserId(), pager);
				for (RecTrxorder recTrxorder : trxorderList) {
					recTrxorder.setCreateDateStr(DateUtil.formatDate(recTrxorder.getCreateDate(), "yyyy-MM-dd"));
				}
				rspMap.put("currentPage", pager.getCurrentPage());
				rspMap.put("totalPage", pager.getTotalPages());
				rspMap.put("trxorderList", trxorderList);
			}
			rspMap.put("totalCount", totalCount);
		}catch (SimpleException simple) {
			simple.printStackTrace();
			rspCode = simple.getErrCode();
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = SimpleException.SYS_ERROR;
		}finally{
			rspMap.put("rspCode", rspCode);
			rspMap.put("rspMsg", SimpleException.errMap.get(rspCode));
			String rspResult = JSONObject.toJSONString(rspMap);
			logger.info("+++++ rec_showTrxorderList_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}
	
	/**
	 *回收详情 
	 */
	@RequestMapping("/user/getTrxorderDetail.do")
	public void getTrxorderDetail(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		try {
			out = response.getWriter();
			String trxorderId = request.getParameter("trxorderId");
			if(StringUtil.isBlank(trxorderId)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			RecTrxorder trxorder = recTrxorderService.getByTrxorderId(Long.parseLong(trxorderId));
			List<RecTrxorderDetail> detailList =  recTrxorderDetailService.getByTrxorderId(Long.parseLong(trxorderId));
			rspMap.put("trxorder", trxorder);
			rspMap.put("detailList", detailList);
			rspMap.put("createDate", DateUtil.formatDate(trxorder.getCreateDate(), "yyyy-MM-dd"));
		}catch (SimpleException simple) {
			simple.printStackTrace();
			rspCode = simple.getErrCode();
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = SimpleException.SYS_ERROR;
		}finally{
			rspMap.put("rspCode", rspCode);
			rspMap.put("rspMsg", SimpleException.errMap.get(rspCode));
			String rspResult = JSONObject.toJSONString(rspMap);
			logger.info("+++++ rec_trxorderDetail_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}
	
	/**
	 * 确认订单
	 */
	@RequestMapping("/user/confirmTrxorder.do")
	public String confirmTrxorder(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			String trxorderId = request.getParameter("trxorderId");
			String userOption = request.getParameter("userOption");
			if(StringUtil.isBlank(trxorderId) || StringUtil.isBlank(userOption)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			RecTrxorder trxorder = recTrxorderService.getByTrxorderId(Long.parseLong(trxorderId));
			if(trxorder.getTrxStatus() != TrxStatus.CONFIRM){
				logger.info("++++confirmTrxorder++trxorderStatus invalid: "+trxorder.getTrxStatus());
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
            RecBeggar recBeggar=recBeggarService.getByBeggarrId(trxorder.getCollectId());
            RecUser recUser=recUserService.getByUserId(trxorder.getUserId());
            String url=domainUtils.getDomain()+"/weixin/su.do?goType=init_login";
            Map<String,TemplateMessageValue> msgMap=new HashMap<String, TemplateMessageValue>();
            String templateStr="";
            msgMap.put("userName",new TemplateMessageValue(recUser.getRealName(),"#173177"));
            msgMap.put("beggarName",new TemplateMessageValue(recBeggar.getRealName(),"#173177"));
            if("SUCCESS".equals(userOption)){
                recTrxorderService.complateTrxorder(trxorder.getTrxorderId());
                templateStr=TemplateMessageUtil.ORDER_SUCCESS_TEMPLATE_ID;
			}else{
				//用户不通过 将状态更新为进行中 重新编辑
				trxorder.setTrxStatus(TrxStatus.PROCESSING);
				trxorder.setUpdateDate(new Date());
				List<RecTrxorderDetail> detailList = recTrxorderDetailService.getByTrxorderId(trxorder.getTrxorderId());
				recTrxorderService.updateTrxorder(trxorder, detailList);
                templateStr=TemplateMessageUtil.ORDER_RETRUNBACK_TEMPLATE_ID;
			}
            String json= TemplateMessageUtil.getTemplateMessageJsonStr(recBeggar.getOpenId(), templateStr, url, msgMap);
            TemplateMessageUtil.sendTemplateMessage(json);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "user/trxorderList";
	}
	
	/**
	 * 取消订单
	 */
	@RequestMapping("/user/cancelTrxorder.do")
	public String cancelTrxorder(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			String trxorderId = request.getParameter("trxorderId");
			if(StringUtil.isBlank(trxorderId)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			RecUser loginUser = recUserService.getLoginUser(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			RecTrxorder trxorder = recTrxorderService.getByTrxorderId(Long.parseLong(trxorderId));
			if(trxorder.getTrxStatus() != TrxStatus.INIT && trxorder.getTrxStatus() != TrxStatus.PROCESSING){
				logger.info("++++cancelTrxorder++trxorderStatus invalid: "+trxorder.getTrxStatus());
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			RecMessage recMessage  = recMessageService.getByTrxorderId(trxorder.getTrxorderId());
			recMessage.setPushStatus(PushStatus.CANCEL);
			recMessage.setUpdateDate(new Date());
			
			trxorder.setTrxStatus(TrxStatus.CANCEL);
			trxorder.setUpdateDate(new Date());
			List<RecTrxorderDetail> detailList = recTrxorderDetailService.getByTrxorderId(trxorder.getTrxorderId());
			recMessageService.updateRecMessage(recMessage, trxorder,detailList);
			if(trxorder.getCollectId().compareTo(0L) != 0){
				RecBeggar beggar = recBeggarService.getByBeggarrId(trxorder.getCollectId());
				if(null != beggar){
//					String content = SmsUtils.replaceTemplate(SmsUtils.templateCancel, loginUser.getRealName(), loginUser.getMobile());
//					SmsUtils.sendSms(beggar.getMobile(), content);
                    String url=domainUtils.getDomain()+"/weixin/su.do?goType=init_login";
                    Map<String,TemplateMessageValue> msgMap=new HashMap<String, TemplateMessageValue>();
                    msgMap.put("beggarName",new TemplateMessageValue(beggar.getRealName(),"#173177"));
                    msgMap.put("userName",new TemplateMessageValue(loginUser.getRealName(),"#173177"));
                    msgMap.put("userMobile",new TemplateMessageValue(loginUser.getMobile(),"#173177"));
                    String json=TemplateMessageUtil.getTemplateMessageJsonStr(beggar.getOpenId(), TemplateMessageUtil.ORDER_CANCEL_TEMPLATE_ID, url, msgMap);
                    logger.info(" 用户取消订单： "+ json);
                    TemplateMessageUtil.sendTemplateMessage(json);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "user/trxorderList";
	}
	
	/**
	 * 我要回收
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/iWantRecycle.do")
	public void iWantRecycle(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		RecUser recUser = null;
		try {
			out = response.getWriter();
			recUser = recUserService.getLoginUser(request);
			if(null == recUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			recUser = recUserService.getByUserId(recUser.getUserId());
			if(null == recUser){
				throw new UserException(UserException.USER_NOT_FOUND);
			}
			if(UserStatus.FROZEN == recUser.getUserStatus()){
				throw new UserException(UserException.USER_STATUS_INVALID);
			}
			String userAddress = getUserAddress(recUser);
			rspMap.put("userAddress", userAddress);
			rspMap.put("realName", recUser.getRealName());
			rspMap.put("mobile", recUser.getMobile());
		}catch (SimpleException simple) {
			simple.printStackTrace();
			rspCode = simple.getErrCode();
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = SimpleException.SYS_ERROR;
		}finally{
			rspMap.put("rspCode", rspCode);
			rspMap.put("rspMsg", SimpleException.errMap.get(rspCode));
			String rspResult = JSONObject.toJSONString(rspMap);
			logger.info("+++++ rec_iWantRecycle_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}
	
	/**
	 * 确认回收
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/confirmRecycle.do")
	public void confirmRecycle(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		RecUser recUser = null;
		try {
			out = response.getWriter();
			recUser = recUserService.getLoginUser(request);
			if(null == recUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			recUser = recUserService.getByUserId(recUser.getUserId());
			if(null == recUser){
				throw new UserException(UserException.USER_NOT_FOUND);
			}
			if(UserStatus.FROZEN == recUser.getUserStatus()){
				throw new UserException(UserException.USER_STATUS_INVALID);
			}
			String startDate = DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyy-MM-dd 00:00:00");
			String endDate = DateUtil.formatDate(DateUtil.getCurrentDate(),"yyyy-MM-dd 23:59:59");
			int count = recMessageService.queryUserUnComplateMsg(recUser.getUserId(), startDate, endDate);
			//用户当天未发送过请求
			if(count == 0){
				RecMessage recMessage = new RecMessage();
				recMessage.setUserId(recUser.getUserId());
				recMessage.setRegionId(recUser.getRegionId());
				recMessage.setPushStatus(PushStatus.INIT);
				
				RecTrxorder trxorder = new RecTrxorder();
				trxorder.setUserId(recUser.getUserId());
				recTrxorderService.addRecTrxorder(trxorder, recMessage, null);
//				recMessageService.addRecMessage(recMessage);
				//将消息推送给乞丐
				List<Long> regionIdList = new ArrayList<Long>();
				regionIdList.add(recUser.getRegionId());
				List<Long> beggarIdList = recBeggarAddressService.queryBeggarIdByRegionIds(regionIdList);
				List<RecBeggar> beggarList = recBeggarService.getBeggarInfoByIds(beggarIdList,BeggarStatus.ACTIVE);
				if(null != beggarList && beggarList.size() > 0 ){
					String url=domainUtils.getDomain()+"/weixin/su.do?goType=init_login";
                    for (RecBeggar collecter : beggarList) {
                        logger.info("============beggarName:"+collecter.getRealName()+"=========openId:"+collecter.getOpenId());
//						String content=SmsUtils.replaceTemplate(SmsUtils.template2pauper, recUser.getRealName(),recUser.getMobile());
//						SmsUtils.sendSms(collecter.getMobile(), content);
                        Map<String,TemplateMessageValue> msgMap=new HashMap<String, TemplateMessageValue>();
                        msgMap.put("beggerName",new TemplateMessageValue(collecter.getRealName(),"#173177"));
                        msgMap.put("userName",new TemplateMessageValue(recUser.getRealName(),"#173177"));
                        msgMap.put("userMobile",new TemplateMessageValue(recUser.getMobile(),"#173177"));
                        msgMap.put("userAddress",new TemplateMessageValue(getUserAddress(recUser),"#173177"));
                        String json=TemplateMessageUtil.getTemplateMessageJsonStr(collecter.getOpenId(), TemplateMessageUtil.ORDER_INIT_TEMPLATE_ID, url, msgMap);
					    TemplateMessageUtil.sendTemplateMessage(json);
                    }
				}
			}
		}catch (SimpleException simple) {
			simple.printStackTrace();
			rspCode = simple.getErrCode();
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = SimpleException.SYS_ERROR;
		}finally{
			rspMap.put("rspCode", rspCode);
			rspMap.put("rspMsg", SimpleException.errMap.get(rspCode));
			String rspResult = JSONObject.toJSONString(rspMap);
			logger.info("+++++ rec_confirmRecycle_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}
	
	/**
	 * 跳转到修改联系人信息页面
	 * @author XiaoXian Xu 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/gotoModifyUserInfo.do")
	public String gotoModifyUserInfo(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			RecUser loginUser = recUserService.getLoginUser(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			
			RecRegion userRegion = recRegionService.getByRegionId(loginUser.getRegionId()); //获得用户所在的小区 
			
			List<RecArea> provinceList 	= recAreaService.getActiveByParentId(0L); //获得省列表
			List<RecArea> cityList 		= new ArrayList<RecArea>()	; //获得市
			List<RecArea> districtList 	= new ArrayList<RecArea>()	; //获得区
			List<RecArea> streetList 	= new ArrayList<RecArea>()	; //获得街道
			List<RecRegion> regionList 	= new ArrayList<RecRegion>()	; //获得小区			
			
			Long provinceId = null;
			Long cityId 	= null;
			Long districtId = null;
			Long streetId 	= null;
						
			if(userRegion!=null ){
				RecArea recArea = recAreaService.getByIdInCache(userRegion.getAreaId());
				while(null != recArea){
					if(recArea.getAreaType() == 4){
						streetId 	= recArea.getAreaId();
						streetList	= recAreaService.getActiveByParentId(recArea.getParentId()); //获得街道
					}else if(recArea.getAreaType() == 3){
						districtId	= recArea.getAreaId();
						districtList= recAreaService.getActiveByParentId(recArea.getParentId()); //获得区
					}else if(recArea.getAreaType() == 2){
						cityId		= recArea.getAreaId();
						cityList	= recAreaService.getActiveByParentId(recArea.getParentId()); //获得市
					}else if(recArea.getAreaType() == 1){
						provinceId	= recArea.getAreaId();
					}				
					if(recArea.getParentId() != 0){
						recArea =  recAreaService.getByIdInCache(recArea.getParentId());
					}else{
						recArea = null;
					}
				}
				
			}
			
			
			
			if(streetId!=null ){
				regionList = recRegionService.getByStreetId(streetId);//获取小区列表
			}
			
			
			
		/*	
		 * //List<RecRegion> regionList = recRegionService.getByParentId(0L,loginUser.getAreaId());//获取小区列表
		  List<RecRegion> regionExtList = null;
			Long parentRegionId = 0L;
			Long regionId = 0L;
			if(UserStatus.ACTIVE == loginUser.getUserStatus() && loginUser.getRegionId() != 0L){
				RecRegion userRegion = recRegionService.getByRegionId(loginUser.getRegionId());
				regionExtList = recRegionService.getByParentId(userRegion.getParentId(),loginUser.getAreaId());
				parentRegionId = userRegion.getParentId();
				regionId = userRegion.getRegionId();
			}else{
				regionExtList =  recRegionService.getByParentId(regionList.get(0).getRegionId(),loginUser.getAreaId());
			}
			
			
			request.setAttribute("parentRegionId", parentRegionId);
			request.setAttribute("regionId", regionId);
			request.setAttribute("loginUser", loginUser);
			request.setAttribute("regionList", regionList);
			request.setAttribute("provinceList", provinceList);
			request.setAttribute("regionExtList", regionExtList);
			*/
			
			
			
			
			request.setAttribute("loginUser", loginUser);
			
			request.setAttribute("provinceList", provinceList);
			request.setAttribute("cityList", cityList);
			request.setAttribute("districtList", districtList);
			request.setAttribute("streetList", streetList);
			request.setAttribute("regionList", regionList);
			
			request.setAttribute("provinceId", provinceId);
			request.setAttribute("cityId", cityId);
			request.setAttribute("districtId", districtId);
			request.setAttribute("streetId", streetId);	
			
			return "user/modifyUserInfo";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/user/userIndex.do";
	}
	
	
	 @RequestMapping("/user/queryAreaJs.do")
	  public void queryRecyclingAdminAreaJs(HttpServletRequest request,HttpServletResponse response){
		 response.setContentType("application/json; charset=utf-8");
		 response.setHeader("Cache-Control", "no-cache");
		 String parentId=request.getParameter("parentId");
		 String selectType=request.getParameter("selectType");
	      List<RecArea> list =  new ArrayList<RecArea>();
	      if(!StringUtils.isBlank(parentId)){
		      if("all".equals( selectType)){
		    	  list = recAreaService.getAllActiveByParentId(Long.parseLong(parentId));
		      }else if("active".equals( selectType)){
		    	  list = recAreaService.getActiveByParentId(Long.parseLong(parentId));
		      } else if("noActive".equals( selectType)){
		    	//  list = recAreaService.getByParentId(Long.parseLong(parentId));
		      }
	      }
	    /*
	      Map<String, Object> resMap = new HashMap<String, Object>();
	      resMap.put("sEcho", sEcho);
	      resMap.put("aaData", recAreaList== null ? "":recAreaList );
	      resMap.put("iTotalRecords", pager.getTotalRows());
	      resMap.put("iTotalDisplayRecords", pager.getTotalRows());
	      resMap.put("iDisplayLength", iDisplayLength);
	      resMap.put("iDisplayStart", iDisplayStart);*/
	      
	      String json= JSON.toJSONString(list);
	     
	      PrintWriter out   = null;
			logger.info("+++++queryRegionMap_rspResult:"+json);
			try {
				out = response.getWriter();
				if(out!=null){
		              out.print(json);
		              out.close();
		          }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
	  }

	
	
	
	/**
	 * 保存联系人信息
	 * @author XiaoXian Xu 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user/modifyUserInfo.do")
	public void modifyUserInfo(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		try {
			out = response.getWriter();
			RecUser recUser = recUserService.getLoginUser(request);
			if(null == recUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			recUser = recUserService.getByUserId(recUser.getUserId());
			if(null == recUser){
				throw new UserException(UserException.USER_NOT_FOUND);
			}
			String areaId = request.getParameter("areaId");
			String regionId = request.getParameter("regionId");
			String mobile = request.getParameter("mobile");
			String address = request.getParameter("address");
			String realName = request.getParameter("realName");
			if(StringUtil.isBlank(areaId) || StringUtil.isBlank(regionId) || StringUtil.isBlank(mobile) ||
					!StringUtil.isMobileNo(mobile) || StringUtil.isBlank(address) || StringUtil.isBlank(realName)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			//手机号已存在
			RecUser existsUser = recUserService.getByMobile(mobile);
			if(null != existsUser && existsUser.getUserId().compareTo(recUser.getUserId()) != 0){
				throw new UserException(UserException.MOBILE_HAS_EXISTS);
			}
			recUser.setAreaId(Long.parseLong(areaId));
			recUser.setRegionId(Long.parseLong(regionId));
			recUser.setMobile(mobile);
			recUser.setAddress(address);
			recUser.setRealName(realName);
			recUser.setUserStatus(UserStatus.ACTIVE);
			recUserService.updateUser(recUser);
			recUserService.refreshUserInfoWithCookie(response, recUser);
		}catch (SimpleException simple) {
			simple.printStackTrace();
			rspCode = simple.getErrCode();
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = SimpleException.SYS_ERROR;
		}finally{
			rspMap.put("rspCode", rspCode);
			rspMap.put("rspMsg", SimpleException.errMap.get(rspCode));
			String rspResult = JSONObject.toJSONString(rspMap);
			logger.info("+++++ rec_modifyUserInfo_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}
	
	/**
	 * 价格明细
	 * @author XiaoXian Xu 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/showPriceDetail.do")
	public String showPriceDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info(getLogString(request));
		String errCode = "";
		try {
			
			Map<Long,List<RecCategory>> categoryMap = recCategoryService.getCategoryRelationMap();
//			List<RecCategory> categoryList = categoryMap.get(0L);
//			
//			//查询三级分类
//			if(null != categoryList && categoryList.size() > 0){
//				for (RecCategory recCategory : categoryList) {
//					List<RecCategory> childCategory = categoryMap.get(recCategory.getCategoryId());
//					recCategory.setChildCategoryList(childCategory);
//				}
//			}
//			request.setAttribute("categoryList", categoryList);
			request.setAttribute("categoryMap", categoryMap);
			return "user/priceDetail";
		} catch (SimpleException simple) {
			errCode = simple.getErrCode();
			simple.printStackTrace();
		} catch (Exception e) {
			errCode = SimpleException.SYS_ERROR;
			e.printStackTrace();
		}
		return "redirect:/user/userIndex.do";
			
	}
	
	/**
	 * 意见反馈
	 * @author XiaoXian Xu 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/saveUserAdvise.do")
	public String saveUserAdvise(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info(getLogString(request));
		String errCode = "";
		try {
			String adviseContent = request.getParameter("adviseContent");
			if(StringUtil.isBlank(adviseContent)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			RecUser recUser = recUserService.getLoginUser(request);
			if(null == recUser){
				return "/user/login";
			}
			RecUserAdvise userAdvise = new RecUserAdvise();
			userAdvise.setAdviseContent(adviseContent);
			userAdvise.setUserId(recUser.getUserId());
			recUserAdviseService.addUserAdvise(userAdvise);
		} catch (SimpleException simple) {
			errCode = simple.getErrCode();
			simple.printStackTrace();
		} catch (Exception e) {
			errCode = SimpleException.SYS_ERROR;
			e.printStackTrace();
		}
		String errMsg = SimpleException.errMap.get(errCode);
		request.setAttribute("errMsg", errMsg);
		return "redirect:/user/accountInfo.do";
			
	}
	
	/**
	 * 服务范围
	 * @author XiaoXian Xu 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user/showServiceArea.do")
	public String showServiceArea(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info(getLogString(request));
		String errCode = "";
		try {
			//TODO:暂时写死北京市
			List<RecRegion> regionList = recRegionService.getByParentId(0L,101L);
			for (RecRegion recRegion : regionList) {
				List<RecRegion> childRegionList = recRegionService.getByParentId(recRegion.getRegionId(),101L);
				recRegion.setChildRegionList(childRegionList);
			}
			request.setAttribute("regionList", regionList);
			
		} catch (SimpleException simple) {
			errCode = simple.getErrCode();
			simple.printStackTrace();
		} catch (Exception e) {
			errCode = SimpleException.SYS_ERROR;
			e.printStackTrace();
		}
		String errMsg = SimpleException.errMap.get(errCode);
		request.setAttribute("errMsg", errMsg);
		return "user/serviceArea";
			
	}
	/**
	 * 得到 用户的地址 
	 * @param recUser
	 * @return
	 */
	private String getUserAddress(RecUser recUser){
		if(null == recUser || recUser.getRegionId()==null){
			return null;
		}
		RecRegion recRegion = recRegionService.getByRegionId(recUser.getRegionId());
		
		String userAddress = "";
		if(null != recRegion){//添加小区名称
			userAddress = recRegion.getRegionCnName()+userAddress;
			RecArea recArea = recAreaService.getByIdInCache(recRegion.getAreaId());
			while(null != recArea){
				userAddress = recArea.getAreaCnName()+userAddress;
				if(recArea.getParentId() != 0){
					recArea =  recAreaService.getByIdInCache(recArea.getParentId());
				}else{
					recArea = null;
				}
			}
		}
		
		
		
	/*	String userAddress = "";
		while(null != recRegion){
			userAddress = recRegion.getRegionCnName()+userAddress;
			if(recRegion.getParentId() != 0){
				recRegion = recRegionService.getByRegionId(recRegion.getParentId());
			}else{
				recRegion = null;
			}
		}
		RecArea recArea = recAreaService.getByIdInCache(recUser.getAreaId());
		while(null != recArea){
			userAddress = userAddress;
			if(recArea.getParentId() != 0){
				recArea =  recAreaService.getByIdInCache(recArea.getParentId());
			}else{
				recArea = null;
			}
		}
		*/
		
		return userAddress + recUser.getAddress();
	}
	
	@RequestMapping("/user/queryRegionMap.do")
	public void queryRegionMap(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		
		
		logger.info("+++++ queryRegionMap.....Start +++++");
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> rspMap = new HashMap<String,Object>();
		try{
			out = response.getWriter();
			String regionIdStr = request.getParameter("regionId");
			Long regionId = StringUtil.isBlank(regionIdStr) ? 101L : Long.parseLong(regionIdStr);
			//Map<Long,List<RecRegion>> regionMap = recRegionService.getAllRegionMap();
			List<RecRegion> recList = recRegionService.getByStreetId(Long.parseLong(regionIdStr));
			
			Map<Long,List<RecRegion>> regionMap = new HashMap<Long, List<RecRegion>>();
			regionMap.put(Long.parseLong(regionIdStr),recList);
			rspMap.put("regionMap",regionMap.get(regionId));
		}catch(Exception e){
			logger.info("+++++ queryRegionMap_Exception:"+e.getMessage());
			e.printStackTrace();
		}finally{
			String rspResult = JSONObject.toJSONString(rspMap);
			logger.info("+++++queryRegionMap_rspResult:"+rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
		
	}
	
	@RequestMapping("/user/initUserAdvise.do")
	public String initUserAdvise(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info(getLogString(request));
		return "user/advise";
	}
	
	/***
	 *联系客服
	 */
	@RequestMapping("/user/initServiceRoute.do")
	public String initServiceRoute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info(getLogString(request));
		return "user/advise";
	}
	
	/***
	 *关于我们
	 */
	@RequestMapping("/user/initAboutus.do")
	public String initAboutus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.info(getLogString(request));
		return "user/advise";
	}
}
