package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description : 用户投诉建议表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:45:45 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecUserAdvise implements Serializable {

	private static final long serialVersionUID = 4730821175268025939L;
	
	private Long adviseId;

	/**用户Id*/
	private Long userId;

	/**建议内容*/
	private String adviseContent="";
	private Date createDate = new Date();
	public Long getAdviseId() {
		return adviseId;
	}
	public void setAdviseId(Long adviseId) {
		this.adviseId = adviseId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAdviseContent() {
		return adviseContent;
	}
	public void setAdviseContent(String adviseContent) {
		this.adviseContent = adviseContent;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
