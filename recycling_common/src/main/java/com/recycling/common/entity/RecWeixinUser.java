package com.recycling.common.entity;

import java.io.Serializable;


/**
 * @Title:RecWeixinUser.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class RecWeixinUser implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8857040036626085662L;

	private Long weixinUserId;
	
	private String openId;
	
	private String weixinNickName;
	
	private String weixinHeadImgUrl;
	
	private Long userId;
	
	private String city;
	
	private Integer sex;
	
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getWeixinUserId() {
		return weixinUserId;
	}

	public void setWeixinUserId(Long weixinUserId) {
		this.weixinUserId = weixinUserId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getWeixinNickName() {
		return weixinNickName;
	}

	public void setWeixinNickName(String weixinNickName) {
		this.weixinNickName = weixinNickName;
	}

	public String getWeixinHeadImgUrl() {
		return weixinHeadImgUrl;
	}

	public void setWeixinHeadImgUrl(String weixinHeadImgUrl) {
		this.weixinHeadImgUrl = weixinHeadImgUrl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
