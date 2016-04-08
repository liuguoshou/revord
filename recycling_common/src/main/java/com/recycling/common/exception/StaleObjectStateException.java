package com.recycling.common.exception;


/**
 * <p>
 * Title:乐观锁异常（DAO层）
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: Sinobo
 * </p>
 * 
 * @date 2015年1月20日23:13:29
 * @author renli.yu
 * @version 1.0
 */
public class StaleObjectStateException extends SimpleException {

	private static final long serialVersionUID = 4039314423110012567L;

	/**
	 * 乐观锁异常（分出此异常。便于加重试机制。）
	 */
	public static final String OPTIMISTIC_LOCK_ERROR = "LOCK0001"; // 乐观锁异常
	
	
	static
	{
		errMap.put(OPTIMISTIC_LOCK_ERROR, "乐观锁异常");
	}
	
	public StaleObjectStateException() {

		super();
	}

	public StaleObjectStateException(String errorMsg) {

		super(errorMsg);
	}

}
