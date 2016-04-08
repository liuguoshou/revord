package com.recycling.admin.user.querycase;

import com.recycling.common.util.Pager;

/**
 * @Title:RecyclingAdminUserQueryCase.java
 * @Description:用户查询条件
 * @author:xu.he
 * @version:v1.0
 */
public class RecyclingAdminUserQueryCase {

    /**
     * 省份ID
     */
    private String area_parent_id;

    /**
     * 城市ID
     */
    private String area_id;

    /**
     * 商圈（热区）ID
     */
    private String region_parent_id;

    /**
     * 小区ID
     */
    private String region_id;

    private String phone_number;

    private String real_name;

    private Pager pager;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getArea_parent_id() {
        return area_parent_id;
    }

    public void setArea_parent_id(String area_parent_id) {
        this.area_parent_id = area_parent_id;
    }

    public String getRegion_parent_id() {
        return region_parent_id;
    }

    public void setRegion_parent_id(String region_parent_id) {
        this.region_parent_id = region_parent_id;
    }
}
