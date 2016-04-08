package com.recycling.service.beggar;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecBeggarAccount;
import com.recycling.common.enums.AccountStatus;
import com.recycling.common.enums.BeggarStatus;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.common.util.MobilePurseSecurityUtils;
import com.recycling.common.util.StringUtil;
import com.recycling.common.util.WebUtils;
import com.recycling.dao.beggar.RecBeggarAccountDao;
import com.recycling.dao.beggar.RecBeggarDao;

@Service("recBeggarService")
public class RecBeggarServiceImpl implements RecBeggarService{

	private MemCacheService mem = MemCacheServiceImpl.getInstance();

	@Autowired
	private RecBeggarDao recBeggarDao;
	
	@Autowired
	private RecBeggarAccountDao recBeggarAccountDao;
	
	@Override
	public Long addBeggar(RecBeggar beggar) {
		Long beggarId = recBeggarDao.addBeggar(beggar);
		RecBeggarAccount account = new RecBeggarAccount();
		account.setBeggarId(beggarId);
		account.setAccountStatus(AccountStatus.ACTIVE);
		recBeggarAccountDao.addAccount(account);
		return beggarId;
	}

	@Override
	public RecBeggar getByBeggarrId(Long beggarId) {
		return recBeggarDao.getById(beggarId);
	}

	@Override
	public void updateBeggar(RecBeggar beggar) throws StaleObjectStateException {
		recBeggarDao.updateBeggarInfo(beggar);
	}

	@Override
	public RecBeggar getByMobile(String mobile) {
		return recBeggarDao.getbyMobile(mobile);
	}

	@Override
	public RecBeggar getLoginBeggar(HttpServletRequest request) {
		String value = WebUtils.getCookieValue(RecConstants.BEGGAR_LOGIN_KEY, request);
		if (StringUtils.isBlank(value)){
			return null;
		}

		String userIds = MobilePurseSecurityUtils.decryption(value,
				RecConstants.USER_PASSWORD_KEY);

		if (StringUtils.isBlank(userIds)){
			return null;
		}

		RecBeggar beggar = (RecBeggar) mem.get(RecConstants.BEGGAR_DETAIL + userIds);
		return beggar;
	}

	@Override
	public void refreshBeggarInfoWithCookie(HttpServletResponse response,RecBeggar recBeggar) {
		if(null== recBeggar || null == recBeggar.getBeggarId()){
			return;
		}
		String cookieSecret = MobilePurseSecurityUtils.secrect(recBeggar.getBeggarId() + "",
				RecConstants.USER_PASSWORD_KEY);
		Cookie cookie = WebUtils.cookie(RecConstants.BEGGAR_LOGIN_KEY,
					cookieSecret, RecConstants.cache_time);
		response.addCookie(cookie);
		
		recBeggar = getByBeggarrId(recBeggar.getBeggarId());
		mem.set(RecConstants.BEGGAR_DETAIL + recBeggar.getBeggarId(), recBeggar,RecConstants.cache_time);
	}

	@Override
	public void beggarLogout(HttpServletRequest request) {
		String value = WebUtils.getCookieValue(
				RecConstants.BEGGAR_LOGIN_KEY, request);
		if (StringUtils.isNotBlank(value)) {
			String userIds = MobilePurseSecurityUtils.decryption(value,
					RecConstants.USER_PASSWORD_KEY);
			mem.remove(RecConstants.BEGGAR_DETAIL + userIds);
		}
		WebUtils.removeableCookie(RecConstants.BEGGAR_LOGIN_KEY);
	
	}

	@Override
	public List<RecBeggar> getBeggarInfoByIds(List<Long> beggarIdList,BeggarStatus status) {
		if(null == beggarIdList || beggarIdList.size() == 0){
			return Collections.emptyList();
		}
		String ids = StringUtil.arrayToString(beggarIdList.toArray(), ",");
		return  recBeggarDao.getByBeggarIds(ids,status);
	}


    /**
     * 根据openId获取乞丐信息
     * @param openId
     * @return
     */
    @Override
    public RecBeggar getBeggarInfoByOpenId(String openId) {
        return recBeggarDao.getBeggarInfoByOpenId(openId);
    }

}
