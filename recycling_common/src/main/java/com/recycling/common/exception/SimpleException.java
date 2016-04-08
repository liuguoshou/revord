package com.recycling.common.exception;

import com.recycling.common.util.ObjectToJson;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: SimpleException.java
 * @Description: TODO
 * @author ye.tian&bengjiu
 * @date 2013-11-19 下午05:21:19
 * @version V1.0
 */
public class SimpleException extends RuntimeException {

	private static final long serialVersionUID = 4520460899078964807L;
	
	public static Map<String, String> errMap = new HashMap<String, String>();
	
	public static final String SUCCESS = "SUCCESS";//成功
	public static final String SYS_ERROR="SYS0001"; //系统错误
	public static final String ILLEGAL_REQUEST = "SYS0002"; // 非法请求
	public static final String CATEGORY_NOT_EXISTS = "SYS0003"; //分类信息不存在
	
	protected String errCode;

	protected String jsonError;
	
	static{
		errMap.put(SUCCESS, "成功!");
		errMap.put(SYS_ERROR, "系统错误!");
		errMap.put(ILLEGAL_REQUEST, "非法请求!");
		errMap.put(CATEGORY_NOT_EXISTS, "分类信息不存在!");
	}
	
	public SimpleException(){
		
	}
	
	public SimpleException(String code) {
		if (StringUtils.isNotBlank(code)) {
			errCode = code;
			errMsg = errMap.get(code);
			if (StringUtils.isNotBlank(errMsg)) {
				Map<String, String> errMsg_ = new HashMap<String, String>();
				errMsg_.put("errCode", errCode);
				errMsg_.put("errMsg", errMsg);
				String jsonError_ = ObjectToJson.map2json(errMsg_);
				jsonError = jsonError_;
			}
		}
	}


	public String getJsonError() {
		return jsonError;
	}

	public void setJsonError(String jsonError) {
		this.jsonError = jsonError;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	protected String errMsg;
	
}
