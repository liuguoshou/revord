package com.recycling.admin.area.querycase;

import com.recycling.common.util.Pager;

/**
 * @Title:RecyclingAdminAreaQueryCase.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public class RecyclingAdminAreaQueryCase {

    /**
     * 查询某一类地区
     */
    private String area_parent_id;

    /**
     * 地区ID
     */
    private String area_id;

    private Pager pager;

	public String getArea_parent_id() {
		return area_parent_id;
	}

	public void setArea_parent_id(String area_parent_id) {
		this.area_parent_id = area_parent_id;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}
    
    


}
