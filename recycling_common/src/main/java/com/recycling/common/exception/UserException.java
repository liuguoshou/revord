package com.recycling.common.exception;


/**
 * <p>
 * Title:用户相关Exception
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
public class UserException extends SimpleException {

	private static final long serialVersionUID = 4039314423110012567L;

	public static final String BALANCE_NOT_ENOUGH = "USER0001"; //账户余额不足
	public static final String INTEGRAL_NOT_ENOUGH = "USER0002"; //账户积分不足
	public static final String USER_NOT_FOUND="USER0003";//用户未发现
	public static final String USER_NOT_LOGIN="USER0004"; //用户未登录
	public static final String USER_STATUS_INVALID="USER0005"; //用户状态异常
	public static final String ACCOUNT_NOT_FOUND="USER0006"; //账户信息未发现
	public static final String PASSWORD_INVALID="USER0007"; //密码不正确
	public static final String MOBILE_HAS_EXISTS="USER0008";//手机号已存在
	
	static{
		errMap.put(BALANCE_NOT_ENOUGH, "账户余额不足！");
		errMap.put(INTEGRAL_NOT_ENOUGH, "账户积分不足！");
		errMap.put(USER_NOT_FOUND, "用户未发现！");
		errMap.put(USER_NOT_LOGIN, "用户未登录！");
		errMap.put(USER_STATUS_INVALID, "用户状态异常！");
		errMap.put(ACCOUNT_NOT_FOUND, "账户信息未发现！");
		errMap.put(PASSWORD_INVALID, "密码不正确！");
		errMap.put(MOBILE_HAS_EXISTS, "手机号已存在！");
	}
	
	public UserException() {

		super();
	}

	public UserException(String errorMsg) {

		super(errorMsg);
	}

}
