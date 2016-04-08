package com.recycling.admin.region.querycase;

import com.recycling.common.util.Pager;

/**
 * @Title:RecyclingAdminRegionQueryCase.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public class RecyclingAdminRegionQueryCase {

    private String region_parent_id;

    private Pager pager;
    
    private Long userId;

    public String getRegion_parent_id() {
        return region_parent_id;
    }

    public void setRegion_parent_id(String region_parent_id) {
        this.region_parent_id = region_parent_id;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
