package com.recycling.admin.category.querycase;

import com.recycling.common.entity.RecCategory;
import com.recycling.common.util.Pager;

import java.util.List;

/**
 * @Title:RecyclingAdminCategroyQueryCase.java
 * @Description:回收分类查询条件
 * @author:xu.he
 * @version:v1.0
 */
public class RecyclingAdminCategroyQueryCase {

    private String parent_category_id;

    private String sub_categroy_id;

    private Pager pager;

    public String getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(String parent_category_id) {
        this.parent_category_id = parent_category_id;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getSub_categroy_id() {
        return sub_categroy_id;
    }

    public void setSub_categroy_id(String sub_categroy_id) {
        this.sub_categroy_id = sub_categroy_id;
    }
}
