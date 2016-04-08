package com.recycling.action.user;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.recycling.action.common.BaseRecAction;
import com.recycling.common.bean.RecTrxorderQueryBean;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecBeggarAddress;
import com.recycling.common.entity.RecCategory;
import com.recycling.common.entity.RecMessage;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.entity.RecTrxorder;
import com.recycling.common.entity.RecTrxorderDetail;
import com.recycling.common.entity.RecUser;
import com.recycling.common.enums.BeggarStatus;
import com.recycling.common.enums.PushStatus;
import com.recycling.common.enums.TrxStatus;
import com.recycling.common.exception.SimpleException;
import com.recycling.common.exception.UserException;
import com.recycling.common.util.Amount;
import com.recycling.common.util.DateUtil;
import com.recycling.common.util.MobilePurseSecurityUtils;
import com.recycling.common.util.Pager;
import com.recycling.common.util.StringUtil;
import com.recycling.common.weixin.entity.res.TemplateMessageValue;
import com.recycling.common.weixin.utils.TemplateMessageUtil;
import com.recycling.service.area.RecAreaService;
import com.recycling.service.beggar.RecBeggarAddressService;
import com.recycling.service.beggar.RecBeggarService;
import com.recycling.service.category.RecCategoryService;
import com.recycling.service.message.RecMessageService;
import com.recycling.service.region.RecRegionService;
import com.recycling.service.trxorder.RecTrxorderDetailService;
import com.recycling.service.trxorder.RecTrxorderService;
import com.recycling.service.user.RecUserService;

/**
 * Description : 废品回收人员Action <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-19 下午4:42:17 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Controller
public class RecBeggarAction extends BaseRecAction{
	
	Logger logger = Logger.getLogger(RecBeggarAction.class);
	
	@Autowired
	private RecBeggarService recBeggarService;
	
	@Autowired
	private RecUserService recUserService;
	
	@Autowired
	private RecTrxorderService recTrxorderService;
	
	@Autowired
	private RecTrxorderDetailService recTrxorderDetailService;
	
	@Autowired
	private RecMessageService recMessageService;
	
	@Autowired
	private RecAreaService recAreaService;
	
	@Autowired
	private RecRegionService recRegionService;
	
	@Autowired
	private RecCategoryService recCategoryService;
	
	@Autowired
	private RecBeggarAddressService recBeggarAddressService;
	
	/**
	 * 初始化回收人员登录页面
	 */
	@RequestMapping("/collect/initLogin.do")
	public String initLogin(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
        String openId = request.getParameter("openId");
        RecBeggar beggar =null;
        try{
			if(StringUtil.isBlank(openId)){
				logger.info("++++init user login openId is null+++++");
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
            beggar=recBeggarService.getBeggarInfoByOpenId(openId);
            if(null != beggar){
            	recBeggarService.refreshBeggarInfoWithCookie(response, beggar);
            	return "redirect:/collect/initRequestMessage.do";
            }
		}catch(Exception e){
			e.printStackTrace();
		}
        request.setAttribute("openId",openId);
        return "collect/login";
	}
	
	/**
	 * 回收人员登录
	 */
	@RequestMapping("/collect/login.do")
	public String collectLogin(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		String rspCode = "";
		String openId = "";
		try{
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			openId = request.getParameter("openId");
			if(StringUtil.isBlank(loginName) || StringUtil.isBlank(password) || StringUtil.isBlank(openId)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}

            RecBeggar beggar = recBeggarService.getByMobile(loginName);
            if(null == beggar){
                logger.info("+++ beggar not found +++");
                throw new UserException(UserException.USER_NOT_FOUND);
            }
            String encPwd = MobilePurseSecurityUtils.secrect(password,
                    RecConstants.USER_PASSWORD_KEY);
            if(!encPwd.equals(beggar.getPassword())){
                logger.info("++++++beggar password invalid  +++++");
                throw new UserException(UserException.PASSWORD_INVALID);
            }
			if(beggar.getBeggarStatus() != BeggarStatus.ACTIVE){
				throw new UserException(UserException.USER_STATUS_INVALID);
			}
            if(StringUtils.isBlank(beggar.getOpenId())){
                //将乞丐的openId更新到数据库中
                beggar.setOpenId(openId);
                recBeggarService.updateBeggar(beggar);
            }
			recBeggarService.refreshBeggarInfoWithCookie(response, beggar);
			return "redirect:/collect/initRequestMessage.do";
		}catch(SimpleException simple){
			rspCode = simple.getErrCode();
			simple.printStackTrace();
		}catch(Exception e){
			rspCode = SimpleException.SYS_ERROR;
			e.printStackTrace();
		}
		request.setAttribute("openId", openId);
		request.setAttribute("errMsg", SimpleException.errMap.get(rspCode));
		return "collect/login";
	}
	
	/**
	 *初始化回收消息中心
	 */
	@RequestMapping("/collect/initRequestMessage.do")
	public String initRequestMessage(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			RecBeggar loginUser = recBeggarService.getLoginBeggar(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			return "collect/requestMessage";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:" + domainUtils.getDomainUrl(null, "/weixin/su.do?goType=init_login");
	}
	
	/**
	 *回收消息中心
	 */
	@RequestMapping("/collect/requestMessage.do")
	public void requestMessage(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		try {
			out = response.getWriter();
			RecBeggar loginUser = recBeggarService.getLoginBeggar(request);
			if(null == loginUser){
				throw new UserException(UserException.USER_NOT_LOGIN);
			}
			Set<Long> regionIdSet = new HashSet<Long>();
			List<RecBeggarAddress> addressList = recBeggarAddressService.getByBeggarId(loginUser.getBeggarId());
			if(null != addressList && addressList.size() > 0){
				for (RecBeggarAddress address : addressList) {
					regionIdSet.add(address.getRegionId());
				}
			}
			
			Date today = new Date();
			String todayStart = DateUtil.formatDate(today, "yyyy-MM-dd 00:00:00");
			String todayEnd = DateUtil.formatDate(today,"yyyy-MM-dd 23:59:59");
			String currentPage = request.getParameter("currentPage");
			int curPage = StringUtil.isBlank(currentPage) ? 1 : Integer.parseInt(currentPage);
			int totalCount = recMessageService.getMesssageCount(regionIdSet, loginUser.getBeggarId(), todayStart, todayEnd);
			if(totalCount  > 0){
				 Pager pager = new Pager(curPage,totalCount,RecConstants.PAGE_SIZE_SMALL);
				//查询当天附近用户发起的未被接单的 和已经被自己接单的消息列表
				List<RecMessage> messageList = recMessageService.getMesssageList(
									regionIdSet, loginUser.getBeggarId(), todayStart, todayEnd,pager);
				 rspMap.put("currentPage", pager.getCurrentPage());
				 rspMap.put("totalPage", pager.getTotalPages());
				 rspMap.put("messageList", messageList);
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
			logger.info("+++++ rec_iWantRecycle_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}
	
	/**
	 *回收申请详情
	 */
	@RequestMapping("/collect/messageDetail.do")
	public void messageDetail(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		try {
			out = response.getWriter();
			String messageId = request.getParameter("messageId");
			if(StringUtil.isBlank(messageId)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			 RecMessage messageInfo = recMessageService.getByMessageId(Long.parseLong(messageId));
			 RecUser userInfo  = recUserService.getByUserId(messageInfo.getUserId());
			 String userAddress  = getAddressByRegionId(userInfo.getRegionId(), userInfo.getAreaId());
			 userAddress = userAddress + userInfo.getAddress();
			 rspMap.put("realName", userInfo.getRealName());
			 rspMap.put("mobile", userInfo.getMobile());
			 rspMap.put("userAddress", userAddress);
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
	 *确认接单
	 */
	@RequestMapping("/collect/comfirmReceptOrder.do")
	public String comfirmReceptOrder(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			String messageId = request.getParameter("messageId");
			if(StringUtil.isBlank(messageId)){
				throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			}
			 RecMessage messageInfo = recMessageService.getByMessageId(Long.parseLong(messageId));
			 RecBeggar loginUser = recBeggarService.getLoginBeggar(request);
			 if(null== loginUser){
				 throw new UserException(UserException.USER_NOT_LOGIN);
			 }
			 if(messageInfo.getPushStatus() != PushStatus.INIT){
				 logger.info("+++++message have been  robbed++++++");
				 throw new SimpleException(SimpleException.ILLEGAL_REQUEST);
			 }
			 messageInfo.setCollectId(loginUser.getBeggarId());
			 messageInfo.setPushStatus(PushStatus.SUCCESS);
			 messageInfo.setUpdateDate(new Date());
			 
			 RecTrxorder trxorder = recTrxorderService.getByTrxorderId(messageInfo.getTrxorderId());
			 trxorder.setCollectId(loginUser.getBeggarId());
			 trxorder.setTrxStatus(TrxStatus.PROCESSING);
			 trxorder.setUpdateDate(new Date());
			 recMessageService.updateRecMessage(messageInfo,trxorder,null);

			 RecUser recUser = recUserService.getByUserId(messageInfo.getUserId());
			 //给用户发短信
			 if(null != recUser){
//				 String content = SmsUtils.replaceTemplate(SmsUtils.template2user, loginUser.getRealName(), loginUser.getMobile());
//				 SmsUtils.sendSms(recUser.getMobile(), content);
                 String url=domainUtils.getDomain()+"/weixin/su.do?goType=user_index";
                 Map<String,TemplateMessageValue> msgMap=new HashMap<String, TemplateMessageValue>();
                 msgMap.put("userName",new TemplateMessageValue(recUser.getRealName(),"#173177"));
                 msgMap.put("beggarName",new TemplateMessageValue(loginUser.getRealName(),"#173177"));
                 msgMap.put("beggarMobile",new TemplateMessageValue(loginUser.getMobile(),"#173177"));
                 String json= TemplateMessageUtil.getTemplateMessageJsonStr(recUser.getOpenId(), TemplateMessageUtil.ORDER_PROCESSING_TEMPLATE_ID, url, msgMap);
                 TemplateMessageUtil.sendTemplateMessage(json);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/collect/initRequestMessage.do";
	}
	
	/**
	 *初始化订单统计页面
	 */
	@RequestMapping("/collect/initTrxorderList.do")
	public String initTrxorderList(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			RecBeggar loginUser = recBeggarService.getLoginBeggar(request);
			 if(null== loginUser){
				 logger.info("+++++user not login ++++");
				 return "collect/login";
			 }
			 List<RecBeggarAddress> addressList = recBeggarAddressService.getByBeggarId(loginUser.getBeggarId());
			 if(null != addressList && addressList.size() > 0){
				 RecRegion collectRegion = recRegionService.getByRegionId(addressList.get(0).getRegionId());
				 List<RecRegion> regionList = recRegionService.getByParentId(collectRegion.getParentId(), collectRegion.getAreaId());
				 request.setAttribute("regionList", regionList);
			 }
			return "collect/trxorderList";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/collect/initRequestMessage.do";
	}
	
	/**
	 *订单统计
	 */
	@RequestMapping("/collect/trxorderList.do")
	public void trxorderList(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		logger.info(getLogString(request));
		Map<String, Object> rspMap = new HashMap<String, Object>();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String rspCode = SimpleException.SUCCESS;
		PrintWriter out   = null;
		try {
			out = response.getWriter();
			 String startDate =  request.getParameter("startDate");
			 String endDate = request.getParameter("endDate");
			 String regionId = request.getParameter("regionId");
			 String currentPage = request.getParameter("currentPage");
			 int curPage = StringUtil.isBlank(currentPage) ? 1:Integer.parseInt(currentPage);
			 RecBeggar loginUser = recBeggarService.getLoginBeggar(request);
			 if(null== loginUser){
				 throw new UserException(UserException.USER_NOT_LOGIN);
			 }
			 RecTrxorderQueryBean queryParam = new RecTrxorderQueryBean();
			 if(!StringUtil.isBlank(regionId)){
				 queryParam.setRegionId(Long.parseLong(regionId));
			 }
			 queryParam.setCollectId(loginUser.getBeggarId());
			 if(!StringUtil.isBlank(startDate)){
				 startDate = startDate+" 00:00:00";
				 queryParam.setStartDate(startDate);
			 }
			 if(!StringUtil.isBlank(endDate)){
				 endDate = endDate+" 23:59:59";
				 queryParam.setEndDate(endDate);
			 }
			 int totalCount = recTrxorderService.getCountByQueryParam(queryParam);
			 if(totalCount > 0){
				 Pager pager = new Pager(curPage,totalCount,RecConstants.PAGE_SIZE_SMALL);
				 queryParam.setStartRow(pager.getStartRow());
				 queryParam.setPageSize(pager.getPageSize());
				 List<RecTrxorder> trxorderList = recTrxorderService.getListByQueryParam(queryParam);
				 if(null != trxorderList && trxorderList.size() > 0){
					 List<Long> userIdList = new ArrayList<Long>();
					 for (RecTrxorder recTrxorder : trxorderList) {
						 if(!userIdList.contains(recTrxorder.getUserId())){
							 userIdList.add(recTrxorder.getUserId());
						 }
					}
					 Map<Long,RecUser> userMap = recUserService.getUserInfoByIds(userIdList);
					 for (RecTrxorder recTrxorder : trxorderList) {
						 recTrxorder.setCreateDateStr(DateUtil.formatDate(recTrxorder.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
						if(userMap.containsKey(recTrxorder.getUserId())){
							RecUser user = userMap.get(recTrxorder.getUserId());
							recTrxorder.setUserAddress(getAddressByRegionId(user.getRegionId(), user.getAreaId()));
							recTrxorder.setRecUser(user);
						}
					}
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
			logger.info("+++++ rec_trxorderList_rspResult=" + rspResult);
            if(out!=null){
                out.print(rspResult);
                out.close();
            }
		}
	}

	/**
	 *编辑订单明细
	 */
	@RequestMapping("/collect/editTrxorder.do")
	public String editTrxorder(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			 String trxorderId = request.getParameter("trxorderId");
			 RecTrxorder trxorder = recTrxorderService.getByTrxorderId(Long.parseLong(trxorderId));
			 Map<Long,List<RecCategory>> categoryMap = recCategoryService.getCategoryRelationMap();
			 if(null != trxorder){
				 List<RecTrxorderDetail> detailList = recTrxorderDetailService.getByTrxorderId(trxorder.getTrxorderId());
				 if(null != detailList && detailList.size() > 0){
					 Map<Long,RecTrxorderDetail> detailMap = new HashMap<Long,RecTrxorderDetail>();
					 for (RecTrxorderDetail recTrxorderDetail : detailList) {
						detailMap.put(recTrxorderDetail.getCategoryId(), recTrxorderDetail);
					}
					 for (Map.Entry<Long,List<RecCategory>> entryCategory : categoryMap.entrySet()) {
						List<RecCategory> categoryList = entryCategory.getValue();
						if(null != categoryList && categoryList.size() > 0){
							for (RecCategory recCategory : categoryList) {
								if(detailMap.containsKey(recCategory.getCategoryId())){
									recCategory.setBuyCount(detailMap.get(recCategory.getCategoryId()).getCount());
								}
							}
						}
					}
				 }
				 request.setAttribute("trxorder", trxorder);
				 request.setAttribute("detailList", detailList);
			 }
			 request.setAttribute("categoryMap", categoryMap);
			return "collect/editTrxorder";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/collect/initRequestMessage.do";
	}
	
	/**
	 *保存编辑订单
	 */
	@RequestMapping("/collect/saveTrxorderDetail.do")
	public String saveTrxorderDetail(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			 String trxorderId = request.getParameter("trxorderId");
			 String[] categoryIdArr = request.getParameterValues("categoryId");
//			 String[] detailIdArr = request.getParameterValues("detailIdArr");
			 String[] saleCountArr = request.getParameterValues("buyCount");
			 RecTrxorder trxorder = recTrxorderService.getByTrxorderId(Long.parseLong(trxorderId));
			 Map<Long,RecCategory> categoryMap = recCategoryService.getAllCategoryInfo();
			 if(null != trxorder){
				 trxorder.setTrxStatus(TrxStatus.CONFIRM);
				 trxorder.setUpdateDate(new Date());
				 RecUser saleUser = recUserService.getByUserId(trxorder.getUserId());
				 List<RecTrxorderDetail> detailList = new ArrayList<RecTrxorderDetail>();
				 for (int i=0;i<categoryIdArr.length;i++) {
					 Long categoryId = Long.parseLong(categoryIdArr[i]);
					 if(!categoryMap.containsKey(categoryId)){
						 throw new SimpleException(SimpleException.CATEGORY_NOT_EXISTS);
					 }
					 if(!Amount.bigger(Double.parseDouble(saleCountArr[i]),0)){
						 continue;
					 }
					 RecTrxorderDetail detail = new RecTrxorderDetail();
				//TODO:新增的分类 和修改的分类的区分（暂时简单处理：删除订单明细重新添加）
					 
					 detail.setCategoryId(categoryId);
					 detail.setCount(Double.parseDouble(saleCountArr[i]));
					 detail.setTrxorderId(Long.parseLong(trxorderId));
					 detail.setRegionId(saleUser.getRegionId());
					 detail.setUnit(categoryMap.get(categoryId).getUnit());
					 detail.setPrice(categoryMap.get(categoryId).getPrice());
					 detail.setTrxStatus(TrxStatus.CONFIRM);
					 detailList.add(detail);
				}
				 recTrxorderService.editTrxorder(trxorder, detailList);

                 String url=domainUtils.getDomain()+"/weixin/su.do?goType=user_index";
                 Map<String,TemplateMessageValue> msgMap=new HashMap<String, TemplateMessageValue>();
                 msgMap.put("userName",new TemplateMessageValue(saleUser.getRealName(),"#173177"));
//                 msgMap.put("beggarName",new TemplateMessageValue(loginUser.getRealName(),"#173177"));
                 String json= TemplateMessageUtil.getTemplateMessageJsonStr(saleUser.getOpenId(), TemplateMessageUtil.ORDER_CONFIRM_TEMPLATE_ID, url, msgMap);
                 TemplateMessageUtil.sendTemplateMessage(json);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/collect/initRequestMessage.do";
	}
	
	/**
	 *查看订单详情
	 */
	@RequestMapping("/collect/viewTrxorderDetail.do")
	public String viewTrxorderDetail(HttpServletRequest request,HttpServletResponse response){
		logger.info(getLogString(request));
		try{
			 String trxorderId = request.getParameter("trxorderId");
			 RecTrxorder trxorder = recTrxorderService.getByTrxorderId(Long.parseLong(trxorderId));
			 if(null != trxorder){
				 List<RecTrxorderDetail> detailList = recTrxorderDetailService.getByTrxorderId(trxorder.getTrxorderId());
				 request.setAttribute("trxorder", trxorder);
				 request.setAttribute("detailList", detailList);
			 }
			return "collect/trxorderDetail";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/collect/initTrxorderList.do";
	}
	
	private String getAddressByRegionId(Long regionId,Long areaId){
		RecRegion recRegion = recRegionService.getByRegionId(regionId);
		String userAddress = "";
		while(null != recRegion){
			userAddress = recRegion.getRegionCnName()+userAddress;
			if(recRegion.getParentId() != 0){
				recRegion = recRegionService.getByRegionId(recRegion.getParentId());
			}else{
				recRegion = null;
			}
		}
		RecArea recArea = recAreaService.getByIdInCache(areaId);
		while(null != recArea){
			userAddress = userAddress;
			if(recArea.getParentId() != 0){
				recArea =  recAreaService.getByIdInCache(recArea.getParentId());
			}else{
				recArea = null;
			}
		}
		return userAddress;
	}
}
