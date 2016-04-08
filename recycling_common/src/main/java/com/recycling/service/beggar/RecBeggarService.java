package com.recycling.service.beggar;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.recycling.common.entity.RecBeggar;
import com.recycling.common.enums.BeggarStatus;
import com.recycling.common.exception.StaleObjectStateException;

/**
 * Description : 乞丐信息Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-25 下午2:34:13 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecBeggarService {

	
	public Long addBeggar(RecBeggar beggar);
	
	public RecBeggar getByBeggarrId(Long beggarId);
	
	public void updateBeggar(RecBeggar beggar) throws StaleObjectStateException;
	
	public RecBeggar getByMobile(String mobile);
	
    /**
     * 获取登录乞丐信息
     * @author XiaoXian Xu 
     * @param request
     * @return
     */
    public RecBeggar getLoginBeggar(HttpServletRequest request);
    
    /**
     * 刷新用户登录信息
     * @author XiaoXian Xu 
     * @param response
     * @param user
     */
    public void refreshBeggarInfoWithCookie(HttpServletResponse response,RecBeggar recBeggar);
    
    /**
     * 退出登录
     * @author XiaoXian Xu 
     * @param request
     */
    public void beggarLogout(HttpServletRequest request);
    
    public List<RecBeggar> getBeggarInfoByIds(List<Long> beggarIdList,BeggarStatus status);

    /**
     * 根据openId获取乞丐信息
     * @param openId
     * @return
     */
    public RecBeggar getBeggarInfoByOpenId(String openId);

}
