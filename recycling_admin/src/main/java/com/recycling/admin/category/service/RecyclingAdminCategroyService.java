package com.recycling.admin.category.service;

import com.recycling.admin.category.querycase.RecyclingAdminCategroyQueryCase;
import com.recycling.common.entity.RecCategory;

import java.util.List;

/**
 * @Title:RecyclingAdminCategroyService
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminCategroyService {

    /**
     * 查询分类列表
     * @param queryCase
     * @return
     */
    public List<RecCategory> queryRecyclingCategroy(RecyclingAdminCategroyQueryCase queryCase);

    /**
     * 查询所有父分类信息
     * @return
     */
    public List<RecCategory> queryRecyclingParentCategroy();

    /**
     * 查询分类详情
     * @param categroy_id
     * @return
     */
    public RecCategory getRecyclingCategroyById(Long categroy_id);

    /**
     * 保存分类
     * @param category
     * @return
     */
    public Boolean addRecyclingCategroy(RecCategory category);

    /**
     * 更新分类
     * @param category
     * @return
     */
    public Boolean updateRecyclingCategroy(RecCategory category);

    /**
     * 根据分类ID子分类
     * @param categroy_id
     * @return
     */
    public List<RecCategory> queryRecyclingCategroySubList(Long categroy_id);
}
