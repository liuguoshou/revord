package com.recycling.dao.beggar;

import java.util.List;
import java.util.Map;

import com.recycling.common.entity.RecBeggar;
import com.recycling.common.enums.BeggarStatus;

/**
 * Description : 乞丐信息Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-25 下午1:16:55 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecBeggarDao {
	
	public RecBeggar getById(Long recBeggarId);
	
	public RecBeggar getbyMobile(String mobile);
	
	public boolean updateBeggarInfo(RecBeggar recBeggar);
	
	public Long addBeggar(RecBeggar recBeggar);

    /**
     * 查询乞丐列表
     * @param region_parent_id
     * @param region_id
     * @param start
     * @param length
     * @return
     */
    public Map<String,Object> queryBeggerList(String region_parent_id,String region_id,int start,int length);
	
	public List<RecBeggar> getByBeggarIds(String ids,BeggarStatus status);

    /**
     * 更新乞丐状态
     * @param beggar_id
     * @param frozen
     * @return
     */
    public Boolean updateStatusUser(Long beggar_id, String frozen);

    /**
     * 根据openId获取用户信息
     * @param openId
     * @return
     */
    public RecBeggar getBeggarInfoByOpenId(String openId);
    
    /**
     * 根据权限查询用户
     * @param region_parent_id
     * @param userAreaIds
     * @param region_id
     * @param start
     * @param length
     * @return
     */
    public Map<String,Object> queryBeggerList(String region_parent_id,int  areaType,String userAreaIds ,String region_id,int start,int length) ;

}
