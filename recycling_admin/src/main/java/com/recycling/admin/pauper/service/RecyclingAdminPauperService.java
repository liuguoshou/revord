package com.recycling.admin.pauper.service;

import com.recycling.admin.pauper.querycase.RecyclingAdminPauperQueryCase;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecUser;

import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminPauperService.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public interface RecyclingAdminPauperService {

    /**
     * 查询小区乞丐列表
     * @param queryCase
     * @return
     */
    public Map<String,Object> queryRecyclingPauper(RecyclingAdminPauperQueryCase queryCase);

    /**
     * 添加乞丐
     * @param recBeggar
     * @param region_ids
     * @return
     */
    public Boolean addRecyclingPauper(RecBeggar recBeggar,String region_ids, Long area_id);

    /**
     * 更新乞丐
     * @param recBeggar
     * @param region_ids
     * @return
     */
    public Boolean updateRecyclingPauper(RecBeggar recBeggar,String region_ids,Long area_id);

    /**
     * 更新乞丐状态
     * @param beggar_id
     * @param status
     * @return
     */
    public Boolean updateStatusRecyclingPauper(Long beggar_id, int status);

    /**
     * 查询乞丐详情
     * @param beggar_id
     * @return
     */
    public RecBeggar getRecyclingPauperById(Long beggar_id);
}
