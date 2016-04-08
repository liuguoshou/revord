package com.recycling.common.util;

import java.util.List;

/**
 * @Title:分页返回Bean
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class PageBean {
	
	
	private List result;
	
	private int totalCount;

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
}	
