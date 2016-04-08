package com.recycling.service.account;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.entity.RecAccount;
import com.recycling.common.entity.RecActHistory;
import com.recycling.common.entity.RecIntegralHistory;
import com.recycling.common.enums.ActHistoryType;
import com.recycling.common.enums.IntegralHistoryType;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.exception.UserException;
import com.recycling.common.util.Amount;
import com.recycling.dao.account.RecAccountDao;
import com.recycling.service.history.RecActHistoryService;
import com.recycling.service.history.RecIntegralHistoryService;

/**
 * Description : 账户信息Service实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-18 下午12:42:34 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Service("recAccountService")
public class RecAccountServiceImpl implements RecAccountService {

	private final Logger logger = Logger.getLogger(RecAccountServiceImpl.class);
	
	@Autowired
	private RecAccountDao recAccountDao;
	
	@Autowired
	private RecActHistoryService recActHistoryService;
	
	@Autowired
	private RecIntegralHistoryService recIntegralHistoryService;
	
	@Override
	public RecAccount getById(Long accountId) {
		return recAccountDao.getById(accountId);
	}

	@Override
	public RecAccount getByUserId(Long userId) {
		return recAccountDao.getByUserId(userId);
	}

	@Override
	public Long addAccount(RecAccount recAccount) {
		return recAccountDao.addAccount(recAccount);
	}

	@Override
	public boolean updateAccount(RecAccount recAccount)	throws StaleObjectStateException {
		return recAccountDao.updateAccount(recAccount);
	}

	@Override
	public void credit(RecAccount recAccount, Double trxAmount,Long trxorderId, 
			ActHistoryType actHistoryType, String desc)throws StaleObjectStateException {
        Long accountId = recAccount.getAccountId();
        Double curBalance = recAccount.getBalance();
        logger.info("#### Credit: Balance=" + curBalance + ", TrxAmout=" + trxAmount + ", Operation=" + Amount.sub(curBalance, trxAmount));
        recAccount.credit(trxAmount);
        recAccount.setUpdateDate(new Date());
        recAccount.setTotalIncome(Amount.add(recAccount.getTotalIncome(), trxAmount));
        recAccountDao.updateAccount(recAccount);

        RecActHistory actHistory = new RecActHistory();
        actHistory.setTrxorderId(trxorderId);
        actHistory.setAccountId(accountId);
        actHistory.setBalance(recAccount.getBalance());
        actHistory.setTrxAmount(trxAmount);
        actHistory.setActHistoryType(actHistoryType);
        actHistory.setDescription(desc);

        recActHistoryService.addActHistory(actHistory);
    }

	@Override
	public void debit(RecAccount account, Double trxAmount, Long trxorderId,
		   ActHistoryType actHistoryType, String desc) throws StaleObjectStateException {
        
    	Long accountId = account.getAccountId();
        Double curBalance = account.getBalance();// 账户可用余额

        // 账户历史余额
        Double actUsableBlance = Amount.sub(curBalance, trxAmount);
        
        logger.info("#### Debit: curBalance=" + curBalance + ", TrxAmout=" + trxAmount + ", Balance=" + actUsableBlance);
        if (actUsableBlance < 0) {
            throw new UserException(UserException.BALANCE_NOT_ENOUGH);
        }
        account.debit(trxAmount);
        account.setUpdateDate(new Date());
        recAccountDao.updateAccount(account);

        Double actBlance = Amount.sub(curBalance, trxAmount);
        RecActHistory actHistory = new RecActHistory();
        actHistory.setTrxorderId(trxorderId);
        actHistory.setAccountId(accountId);
        actHistory.setBalance(actBlance);
        actHistory.setTrxAmount(trxAmount);
        actHistory.setActHistoryType(actHistoryType);
        actHistory.setDescription(desc);
        
        recActHistoryService.addActHistory(actHistory);
    }

	@Override
	public void creditIntegral(RecAccount recAccount, Long trxIntegral,Long trxorderId, 
			IntegralHistoryType historyType, String desc)throws StaleObjectStateException { 
        Long accountId = recAccount.getAccountId();
        Long curIntegral = recAccount.getIntegral();
        logger.info("#### creditIntegral: curIntegral=" + curIntegral + ", trxIntegral=" + trxIntegral + ", Operation=" + Amount.sub(curIntegral, trxIntegral));

        recAccount.creditIntegral(trxIntegral);
        recAccount.setUpdateDate(new Date());
        recAccountDao.updateAccount(recAccount);

        RecIntegralHistory recIntegralHistory = new RecIntegralHistory();
        recIntegralHistory.setTrxorderId(trxorderId);
        recIntegralHistory.setAccountId(accountId);
        recIntegralHistory.setIntegral(recAccount.getIntegral());
        recIntegralHistory.setTrxAmount(trxIntegral);
        recIntegralHistory.setIntegralHistoryType(historyType);
        recIntegralHistory.setDescription(desc);

        recIntegralHistoryService.addIntegralHistory(recIntegralHistory);
    }

	@Override
	public void debitIntegral(RecAccount account, Long trxIntegral,Long trxorderId, 
			IntegralHistoryType historyType, String desc)throws StaleObjectStateException {
        
    	Long accountId = account.getAccountId();
        Long curIntegral = account.getIntegral();// 账户可用积分

        // 账户历史积分
        Double actUsableIntegral = Amount.sub(curIntegral, trxIntegral);
        logger.info("#### creditIntegral: curIntegral=" + curIntegral + ", trxIntegral=" + trxIntegral + ", Balance=" + actUsableIntegral);
        if (actUsableIntegral < 0) {
            throw new UserException(UserException.INTEGRAL_NOT_ENOUGH);
        }
        account.debitIntegral(trxIntegral);
        account.setUpdateDate(new Date());
        recAccountDao.updateAccount(account);

        Long actIntegral = curIntegral - trxIntegral;
        RecIntegralHistory integralHistory = new RecIntegralHistory();
        integralHistory.setTrxorderId(trxorderId);
        integralHistory.setAccountId(accountId);
        integralHistory.setIntegral(actIntegral);
        integralHistory.setTrxAmount(trxIntegral);
        integralHistory.setIntegralHistoryType(historyType);
        integralHistory.setDescription(desc);
        
        recIntegralHistoryService.addIntegralHistory(integralHistory);
    }

}
