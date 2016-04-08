package com.recycling.dao.beggar;

import java.util.List;

import com.recycling.common.entity.RecBeggarAddress;

/**
 * Description : 乞丐地址信息Dao <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-25 下午1:42:33 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecBeggarAddressDao {
	
	public RecBeggarAddress getByAddressId(Long addressId);
	
	public List<RecBeggarAddress> getByBeggarId(Long beggarId);
	
	public Long addAddress(RecBeggarAddress address);
	
	public boolean updateAddress(RecBeggarAddress address);
	
	public List<Long> queryBeggarIdByRegionIds(String regionIds);

    /**
     * 删除乞丐相关地址
     * @param beggarId
     * @return
     */
    public boolean deleteAddressByBeggarId(Long beggarId);
}
