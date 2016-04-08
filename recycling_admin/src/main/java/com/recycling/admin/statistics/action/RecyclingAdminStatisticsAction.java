package com.recycling.admin.statistics.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.category.service.RecyclingAdminCategroyService;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.region.service.RecyclingAdminRegionService;
import com.recycling.admin.statistics.querycase.RecyclingAdminStatisticsQueryCase;
import com.recycling.admin.statistics.service.RecyclingAdminStatisticsService;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecCategory;
import com.recycling.common.entity.RecRegion;
import com.recycling.service.area.RecAreaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminStatisticsAction.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class RecyclingAdminStatisticsAction extends BaseAdminAction {


    @Autowired
    private RecyclingAdminCategroyService recyclingAdminCategroyService;

    @Autowired
    private RecyclingAdminRegionService recyclingAdminRegionService;

    @Autowired
    private RecyclingAdminStatisticsService recyclingAdminStatisticsService;

    @Autowired
    private RecAreaService recAreaService;



    /**
     * 统计报表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toRecyclingAdminStatistics.do")
    public String toRecyclingAdminStatistics(HttpServletRequest request,HttpServletResponse response){
        List<RecCategory> recCategoryList=recyclingAdminCategroyService.queryRecyclingParentCategroy();
        List<RecArea> recRegionList = recAreaService.getActiveByParentId(new Long(0));
        //List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        request.setAttribute("recRegionList",recRegionList);
        request.setAttribute("recCategoryList",recCategoryList);
        return "statistics/list_statistics";
    }

    /**
     * 查询统计列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryRecyclingAdminStatustics.do")
    public String queryRecyclingAdminStatustics(HttpServletRequest request,HttpServletResponse response){
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        String categroy_parent_id=request.getParameter("categroy_parent_id");
        String region_id=request.getParameter("region_id");
        String region_parent_id=request.getParameter("region_parent_id");
        String categroy_id=request.getParameter("categroy_id");
        String from_date=request.getParameter("from_date");
        String to_date=request.getParameter("to_date");
        String collect_id =request.getParameter("collect_id");
        if(StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
            return "500";
        }



        RecyclingAdminStatisticsQueryCase queryCase=new RecyclingAdminStatisticsQueryCase();
        Map<String,Object> objectMap=new HashMap<String, Object>();
        try {
            queryCase.setStart(Integer.parseInt(iDisplayStart));
            queryCase.setLength(Integer.parseInt(iDisplayLength));
            queryCase.setCategroy_parent_id(categroy_parent_id);
            queryCase.setCategroy_id(categroy_id);
            queryCase.setRegion_id(region_id);
            queryCase.setRegion_parent_id(region_parent_id);
            queryCase.setFromDate(from_date);
            queryCase.setToDate(to_date);
            queryCase.setCollect_id(collect_id);
            objectMap=recyclingAdminStatisticsService.queryRecyclingTrxorderDetail(queryCase);


        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }
        int totalCount=(Integer)objectMap.get("totalCount");
        List<Map<String,Object>> resultMap= (List<Map<String, Object>>) objectMap.get("result");
        Double totalMoney = (Double) objectMap.get("totalMoney");
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", resultMap== null ? "":resultMap );
        resMap.put("iTotalRecords", totalCount);
        resMap.put("iTotalDisplayRecords", totalCount);
        resMap.put("iDisplayLength", iDisplayLength);
        resMap.put("iDisplayStart", iDisplayStart);
        resMap.put("totalMoney",totalMoney);
        String json= JSON.toJSONString(resMap);
        renderJson(response,json);
        return null;
    }


    /**
     * 查询统计详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toDetailRecyclingAdminStatustics.do")
    public String toDetailRecyclingAdminStatustics(HttpServletRequest request,HttpServletResponse response){
        String region_id=request.getParameter("region_id");
        String categroy_id=request.getParameter("categroy_id");
        String totalStatisticsMoney =request.getParameter("totalStatisticsMoney");
        request.setAttribute("totalStatisticsMoney",totalStatisticsMoney);
        request.setAttribute("region_id",region_id);
        request.setAttribute("categroy_id",categroy_id);
        return "statistics/detail_statistics";
    }

    /**
     * 统计详情列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryTrxOrderHistoryDetailRecyclingAdmin.do")
    public String queryTrxOrderHistoryDetailRecyclingAdmin(HttpServletRequest request,HttpServletResponse response){
        String region_id=request.getParameter("region_id");
        String categroy_id=request.getParameter("categroy_id");
        String from_date =request.getParameter("from_date");
        String to_date=request.getParameter("to_date");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        if(StringUtils.isBlank(region_id)||StringUtils.isBlank(categroy_id)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
            return "500";
        }
        Map<String,Object> objectMap=new HashMap<String, Object>();
        try {
            objectMap=recyclingAdminStatisticsService.queryRecyclingTrxOrderHistoryDetailList(Long.valueOf(region_id),Long.valueOf(categroy_id),Integer.parseInt(iDisplayStart),Integer.parseInt(iDisplayLength),from_date,to_date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "500";
        }

        int totalCount=(Integer)objectMap.get("totalCount");
        List<Map<String,Object>> resultMap= (List<Map<String, Object>>) objectMap.get("result");
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", resultMap== null ? "":resultMap );
        resMap.put("iTotalRecords", totalCount);
        resMap.put("iTotalDisplayRecords", totalCount);
        resMap.put("iDisplayLength", iDisplayLength);
        resMap.put("iDisplayStart", iDisplayStart);
        String json= JSON.toJSONString(resMap);
        renderJson(response,json);
        return null;
    }

}
