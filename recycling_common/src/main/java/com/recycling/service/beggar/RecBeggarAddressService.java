package com.recycling.service.beggar;

import java.util.List;

import com.recycling.common.entity.RecBeggarAddress;

/**
 * Description : 乞丐地址信息Service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-25 下午2:33:45 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecBeggarAddressService {

	public RecBeggarAddress getByAddressId(Long addressId);
	
	public List<RecBeggarAddress> getByBeggarId(Long beggarId);
	
	public Long addAddress(RecBeggarAddress address);
	
	public boolean updateAddress(RecBeggarAddress address);
	
	/**
	 *根据小区Id集合 查出负责这片区域的乞丐Id
	 */
	public List<Long> queryBeggarIdByRegionIds(List<Long> regionIdList);
}
