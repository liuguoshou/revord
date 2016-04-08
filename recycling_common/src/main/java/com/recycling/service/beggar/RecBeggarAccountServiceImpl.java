package com.recycling.service.beggar;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.recycling.common.entity.RecBeggarAccount;
import com.recycling.common.entity.RecBeggarHistory;
import com.recycling.common.enums.BeggarHistoryType;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.exception.UserException;
import com.recycling.common.util.Amount;
import com.recycling.dao.beggar.RecBeggarAccountDao;
import com.recycling.dao.beggar.RecBeggarHistoryDao;

@Repository("recBeggarAccountService")
public class RecBeggarAccountServiceImpl implements RecBeggarAccountService{
	private final Logger logger = Logger.getLogger(RecBeggarAccountServiceImpl.class);

	@Autowired
	private RecBeggarAccountDao recBeggarAccountDao;
	
	@Autowired
	private RecBeggarHistoryDao recBeggarHistoryDao;
	
	@Override
	public RecBeggarAccount getById(Long accountId) {
		return recBeggarAccountDao.getById(accountId);
	}

	@Override
	public RecBeggarAccount getByBeggarId(Long beggarId) {
		return recBeggarAccountDao.getByBeggarId(beggarId);
	}

	@Override
	public Long addAccount(RecBeggarAccount recAccount) {
		return recBeggarAccountDao.addAccount(recAccount);
	}

	@Override
	public boolean updateAccount(RecBeggarAccount recAccount)throws StaleObjectStateException {
		return recBeggarAccountDao.updateAccount(recAccount);
	}

	@Override
	public void credit(RecBeggarAccount recAccount, Double trxAmount,Long trxorderId, 
			BeggarHistoryType beggarHistoryType, String desc)throws StaleObjectStateException {
        Long accountId = recAccount.getAccountId();
        Double curBalance = recAccount.getBalance();
        logger.info("#### Credit: Balance=" + curBalance + ", TrxAmout=" + trxAmount + ", Operation=" + Amount.sub(curBalance, trxAmount));
        recAccount.credit(trxAmount);
        recAccount.setUpdateDate(new Date());
        recBeggarAccountDao.updateAccount(recAccount);

        RecBeggarHistory actHistory = new RecBeggarHistory();
        actHistory.setTrxorderId(trxorderId);
        actHistory.setAccountId(accountId);
        actHistory.setBalance(recAccount.getBalance());
        actHistory.setTrxAmount(trxAmount);
        actHistory.setBeggarHistoryType(beggarHistoryType);
        actHistory.setDescription(desc);

        recBeggarHistoryDao.addBeggarHistory(actHistory);
    }

	@Override
	public void debit(RecBeggarAccount account, Double trxAmount,
			Long trxorderId, BeggarHistoryType beggarHistoryType, String desc)
			throws StaleObjectStateException {
        
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
        recBeggarAccountDao.updateAccount(account);

        Double actBlance = Amount.sub(curBalance, trxAmount);
        RecBeggarHistory actHistory = new RecBeggarHistory();
        actHistory.setTrxorderId(trxorderId);
        actHistory.setAccountId(accountId);
        actHistory.setBalance(actBlance);
        actHistory.setTrxAmount(trxAmount);
        actHistory.setBeggarHistoryType(beggarHistoryType);
        actHistory.setDescription(desc);
        
        recBeggarHistoryDao.addBeggarHistory(actHistory);
    }

}
