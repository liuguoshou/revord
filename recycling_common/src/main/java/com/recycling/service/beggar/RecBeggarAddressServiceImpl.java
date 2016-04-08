package com.recycling.service.beggar;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.entity.RecBeggarAddress;
import com.recycling.common.util.StringUtil;
import com.recycling.dao.beggar.RecBeggarAddressDao;

@Service("recBeggarAddressService")
public class RecBeggarAddressServiceImpl implements RecBeggarAddressService{

	@Autowired
	private RecBeggarAddressDao recBeggarAddressDao;
	
	@Override
	public RecBeggarAddress getByAddressId(Long addressId) {
		return recBeggarAddressDao.getByAddressId(addressId);
	}

	@Override
	public List<RecBeggarAddress> getByBeggarId(Long beggarId) {
		return recBeggarAddressDao.getByBeggarId(beggarId);
	}

	@Override
	public Long addAddress(RecBeggarAddress address) {
		return recBeggarAddressDao.addAddress(address);
	}

	@Override
	public boolean updateAddress(RecBeggarAddress address) {
		return recBeggarAddressDao.updateAddress(address);
	}

	@Override
	public List<Long> queryBeggarIdByRegionIds(List<Long> regionIdList) {
		if(null == regionIdList || regionIdList.isEmpty()){
			return Collections.emptyList();
		}
		String regionIds = StringUtil.arrayToString(regionIdList.toArray(), ",");
		return recBeggarAddressDao.queryBeggarIdByRegionIds(regionIds);
	}

}
