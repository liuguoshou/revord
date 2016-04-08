package com.recycling.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description : 商品分类表实体类 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午4:41:15 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
public class RecCategory implements Serializable {

	private static final long serialVersionUID = -9183669398930952994L;

	private Long categoryId;

	/**分类名称*/
	private String categoryName="";

	/**父级分类Id*/
	private Long parentId;

	/**当前分类价格*/
	private double price;

	/**单位*/
	private String unit;

	/**当前分类是否上线 默认上线*/
	private int isOnline=1;
	
	private Date createDate = new Date();
	
	private List<RecCategory> childCategoryList = null;
	
	private double buyCount = 0;
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public List<RecCategory> getChildCategoryList() {
		return childCategoryList;
	}
	public void setChildCategoryList(List<RecCategory> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}
	public double getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(double buyCount) {
		this.buyCount = buyCount;
	}
	
}
