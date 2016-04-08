package com.recycling.admin.region.service;

import com.recycling.admin.region.querycase.RecyclingAdminRegionQueryCase;
import com.recycling.common.entity.RecRegion;

import javax.swing.plaf.synth.Region;

import java.util.List;

/**
 * @Title:RecyclingAdminRegionService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminRegionService {

    /**
     * 查询所有热区
     * @return
     */
    public List<RecRegion> queryAllRecyclingRegion();

    /**
     * 根据pid查询所有小区
     * @param parentId
     * @return
     */
    public List<RecRegion> getByParentId(Long parentId);

    /**
     * 查询小区列表
     * @param queryCase
     * @return
     */
    public List<RecRegion> queryRecyclingRegion(RecyclingAdminRegionQueryCase queryCase);

    /**
     * 添加小区
     * @param recRegion
     * @return
     */
    public Boolean addRecyclingRegion(RecRegion recRegion);

    /**
     * 更新小区
     * @param recRegion
     * @return
     */
    public Boolean updateRecyclingRegion(RecRegion recRegion);

    /**
     * 查询小区信息
     * @param region_id
     * @return
     */
    public RecRegion getRecyclingRegionById(Long region_id);
    
    public List <RecRegion>getAllProvence();
    /**
     * 根据parentId查询出据有的小区信息
     * @param parentId
     * @return
     */
	public List<RecRegion> getAllByParentId(Long parentId);

	public RecRegion getRecyclingRegionByIdAllState(Long region_id);

	public List<RecRegion> getByStreetId(Long parentId);
}
