package com.recycling.common.weixin.entity.menu;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:MenuModel.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class MenuModel implements Serializable{

	private static final long serialVersionUID = 553788132230491654L;
	
	private String type;
	
	private String name;
	
	private String key;
	
	private String url;
	
	private List<MenuModel> sub_button;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuModel> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<MenuModel> sub_button) {
		this.sub_button = sub_button;
	}
	
	

}
