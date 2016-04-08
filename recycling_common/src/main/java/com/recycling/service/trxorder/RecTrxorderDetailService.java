package com.recycling.service.trxorder;

import java.util.List;

import com.recycling.common.entity.RecTrxorderDetail;

/**
 * Description : 订单明细service <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午3:17:19 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public interface RecTrxorderDetailService {
	
	public List<RecTrxorderDetail> getByTrxorderId(Long trxorderId);

}
