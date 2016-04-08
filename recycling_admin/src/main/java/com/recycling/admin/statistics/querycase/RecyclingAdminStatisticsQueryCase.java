package com.recycling.admin.statistics.querycase;

import com.recycling.common.util.Pager;

/**
 * @Title:RecyclingAdminStatisticsQueryCase.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public class RecyclingAdminStatisticsQueryCase {

    private String region_parent_id;

    private String region_id;

    private String categroy_parent_id;

    private String categroy_id;

    private String fromDate;

    private String toDate;

    private Pager pager;

    private int start;

    private int length;

    private String categrou_sub_ids;

    /**
     * 乞丐ID
     */
    private String collect_id;

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

    public String getCategroy_parent_id() {
        return categroy_parent_id;
    }

    public void setCategroy_parent_id(String categroy_parent_id) {
        this.categroy_parent_id = categroy_parent_id;
    }

    public String getCategroy_id() {
        return categroy_id;
    }

    public void setCategroy_id(String categroy_id) {
        this.categroy_id = categroy_id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCategrou_sub_ids() {
        return categrou_sub_ids;
    }

    public void setCategrou_sub_ids(String categrou_sub_ids) {
        this.categrou_sub_ids = categrou_sub_ids;
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }
}
