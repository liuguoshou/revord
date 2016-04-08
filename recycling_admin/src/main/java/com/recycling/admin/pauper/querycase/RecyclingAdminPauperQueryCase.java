package com.recycling.admin.pauper.querycase;

import com.recycling.common.util.Pager;

/**
 * @Title:RecyclingAdminPauperQueryCase.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public class RecyclingAdminPauperQueryCase {

    /**
     * 商圈（热区）ID
     */
    private String region_parent_id;

    /**
     * 小区ID
     */
    private String region_id;

    private Pager pager;
    
    private Long userId;

    public String getRegion_parent_id() {
        return region_parent_id;
    }

    public void setRegion_parent_id(String region_parent_id) {
        this.region_parent_id = region_parent_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
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
