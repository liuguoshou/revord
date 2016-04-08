package com.recycling.service.weixin;

import java.io.Serializable;

/**
 * @Title:WeixinToken.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class WeixinToken implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 35106027627199966L;

	private String token;

	private long expires_in;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

}
