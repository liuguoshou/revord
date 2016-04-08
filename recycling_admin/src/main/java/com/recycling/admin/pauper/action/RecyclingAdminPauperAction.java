package com.recycling.admin.pauper.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.area.service.RecyclingAdminAreaService;
import com.recycling.admin.auth.service.AdminAuthService;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.constants.AdminConstant;
import com.recycling.admin.entity.AdminMenus;
import com.recycling.admin.entity.AdminResult;
import com.recycling.admin.entity.AdminUser;
import com.recycling.admin.pauper.querycase.RecyclingAdminPauperQueryCase;
import com.recycling.admin.pauper.service.RecyclingAdminPauperService;
import com.recycling.admin.region.service.RecyclingAdminRegionService;
import com.recycling.admin.statistics.service.RecyclingAdminStatisticsService;
import com.recycling.admin.user.service.RecyclingAdminUserService;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.entity.RecUser;
import com.recycling.common.enums.BeggarStatus;
import com.recycling.common.enums.UserStatus;
import com.recycling.common.enums.UserType;
import com.recycling.common.util.DateUtil;
import com.recycling.common.util.EnumUtil;
import com.recycling.common.util.MobilePurseSecurityUtils;
import com.recycling.common.util.Pager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminPauperAction.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class RecyclingAdminPauperAction extends BaseAdminAction{

    @Autowired
    private RecyclingAdminRegionService recyclingAdminRegionService;

    @Autowired
    private RecyclingAdminPauperService recyclingAdminPauperService;

    @Autowired
    private RecyclingAdminStatisticsService recyclingAdminStatisticsService;

	@Autowired
	RecyclingAdminAreaService recyclingAdminAreaService;
    /**
     * 乞丐维护
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toRecyclingAdminPauper.do")
    public String toRecyclingAdminPauper(HttpServletRequest request,HttpServletResponse response){
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        request.setAttribute("recRegionList",recRegionList);
        
        List <RecArea>recAreaList = recyclingAdminAreaService.getActiveByParentId((new Long("0")));
        request.setAttribute("recAreaList",recAreaList);
        
        return "pauper/list_pauper";
    }

    /**
     * 乞丐查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryRecyclingAdminPauper.do")
    public String queryRecyclingAdminPauper(HttpServletRequest request,HttpServletResponse response){

    	AdminUser au = (AdminUser) request.getAttribute("loginUser");
    	
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        String region_parent_id=request.getParameter("region_parent_id");
        String region_id=request.getParameter("region_ids");
        RecyclingAdminPauperQueryCase queryCase=new RecyclingAdminPauperQueryCase();
        Pager pager = new Pager();
        pager.setStartRow(Integer.parseInt(iDisplayStart));
        pager.setPageSize(Integer.parseInt(iDisplayLength));
        queryCase.setPager(pager);
        queryCase.setUserId(au.getAdminUserId());
        Map<String,Object> objectMap=null;

        try { 
            queryCase.setRegion_id(region_id);
            queryCase.setRegion_parent_id(region_parent_id);
            objectMap=recyclingAdminPauperService.queryRecyclingPauper(queryCase);
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }
        int total=(Integer)objectMap.get("totalCount");
        List<Map<String,Object>> recBeggarList= (List<Map<String, Object>>) objectMap.get("result");
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", recBeggarList== null ? "":recBeggarList );
        resMap.put("iTotalRecords", total);
        resMap.put("iTotalDisplayRecords",total);
        resMap.put("iDisplayLength", iDisplayLength);
        resMap.put("iDisplayStart", iDisplayStart);
        String json= JSON.toJSONString(resMap);
        renderJson(response,json);
        return null;
    }

    /**
     * 添加乞丐
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toAddRecyclingAdminPauper.do")
    public String toAddRecyclingAdminPauper(HttpServletRequest request,HttpServletResponse response){
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        
        List <RecArea>recAreaList = recyclingAdminAreaService.getActiveByParentId((new Long("0")));
        request.setAttribute("recAreaList",recAreaList);
        request.setAttribute("recRegionList",recRegionList);
        return  "pauper/add_pauper";
    }

    /**
     * 更新乞丐
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toUpdateRecycingAdminPauper.do")
    public String toUpdateRecycingAdminPauper(HttpServletRequest request,HttpServletResponse response){
        String pauper_id =request.getParameter("pauper_id");
        if(StringUtils.isBlank(pauper_id)){
            return "500";
        }
        RecBeggar beggar=recyclingAdminPauperService.getRecyclingPauperById(Long.valueOf(pauper_id));
        beggar.setPassword(MobilePurseSecurityUtils.decryption(beggar.getPassword(),RecConstants.USER_PASSWORD_KEY));
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        
        List <RecArea>recAreaList = recyclingAdminAreaService.getActiveByParentId((new Long("0")));
        request.setAttribute("recAreaList",recAreaList);
        
        request.setAttribute("recRegionList",recRegionList);
        request.setAttribute("beggar",beggar);
        return "pauper/detail_pauper";
    }


    /**
     * 保存乞丐
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/saveRecyclingAdminPauper.do")
    public String saveRecyclingAdminPauper(HttpServletRequest request,HttpServletResponse response){

        //省市
      //  String area_id=request.getParameter("area_id");
        //小区ID
      //  String region_parent_id=request.getParameter("pauper_region_parent_id");
        String rec_pauper_region_street_id = request.getParameter("rec_pauper_region_street_id");
        String rec_update_pauper_region_street_id = request.getParameter("rec_update_pauper_region_street_id");
        
        String region_ids=request.getParameter("regionIds");
        String pauper_name=request.getParameter("pauper_name");
        String pauper_mobile=request.getParameter("pauper_mobile");
        String pauper_password =request.getParameter("pauper_password");
        String method =request.getParameter("method");
        if(StringUtils.isBlank("method")||StringUtils.isBlank(pauper_name)||StringUtils.isBlank(pauper_mobile)||StringUtils.isBlank(pauper_password)){
            return "500";
        }
        RecBeggar beggar = new RecBeggar();
        beggar.setVersion(0L);
        beggar.setRealName(pauper_name);
        beggar.setMobile(pauper_mobile);
        beggar.setPassword(MobilePurseSecurityUtils.secrect(pauper_password, RecConstants.USER_PASSWORD_KEY));
        beggar.setBeggarStatus(BeggarStatus.ACTIVE);
        Boolean flag=true;
        if(method.equals("save")){
        	if(StringUtils.isBlank(rec_pauper_region_street_id)){
                return "500";
            }
            beggar.setCreateDate(new Date());
            beggar.setUpdateDate(new Date());
            flag=recyclingAdminPauperService.addRecyclingPauper(beggar,region_ids,Long.parseLong(rec_pauper_region_street_id));
        }else if(method.equals("update")){
            String pauper_id=request.getParameter("pauper_id");
            String create_date=request.getParameter("create_date");
            if(StringUtils.isBlank(pauper_id)||StringUtils.isBlank(create_date) || StringUtils.isBlank(rec_update_pauper_region_street_id)) {
                return "500";
            }
            try {
                beggar.setCreateDate(DateUtil.parseToDate(create_date,"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
                return "500";
            }

            beggar.setUpdateDate(new Date());
            beggar.setBeggarId(Long.valueOf(pauper_id));
            flag=recyclingAdminPauperService.updateRecyclingPauper(beggar,region_ids,Long.parseLong(rec_update_pauper_region_street_id));
        }
        AdminResult result=new AdminResult();
        if(flag){
            result.setMsg("操作成功！");
            result.setType(AdminResult.SUCCESS);
        }else{
            result.setMsg("操作失败！");
            result.setType(AdminResult.ERROR);
        }
        renderJson(response,JSON.toJSONString(result));
        return null;
    }

    /**
     * 统计乞丐回收
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toStatisticsRecyclingAdminPauper.do")
    public String toStatisticsRecyclingAdminPauper(HttpServletRequest request,HttpServletResponse response){
        String collect_id =request.getParameter("collect_id");
        if(StringUtils.isBlank(collect_id)){
            return "500";
        }
        request.setAttribute("collect_id",collect_id);
        return "pauper/statistics_pauper";
    }

    /**
     * 修改乞丐状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/updateStatusRecyclingAdminPauper.do")
    public String updateStatusRecyclingAdminPauper(HttpServletRequest request,HttpServletResponse response){
        String user_id=request.getParameter("user_id");
        String status=request.getParameter("status");

        if(StringUtils.isBlank(user_id)||StringUtils.isBlank(status)){
            return "500";
        }
        Boolean flag=recyclingAdminPauperService.updateStatusRecyclingPauper(Long.valueOf(user_id), Integer.parseInt(status));
        AdminResult result=new AdminResult();
        if(flag){
            result.setMsg("操作成功！");
            result.setType(AdminResult.SUCCESS);
        }else{
            result.setMsg("操作失败！");
            result.setType(AdminResult.ERROR);
        }
        renderJson(response,JSON.toJSONString(result));
        return null;
    }


    /**
     * 回收详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryPauperDetailRecyclingAdmin.do")
    public String queryPauperDetailRecyclingAdmin(HttpServletRequest request,HttpServletResponse response){
        String user_id=request.getParameter("collect_id");
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        if(StringUtils.isBlank(user_id)||StringUtils.isBlank(iDisplayStart)||StringUtils.isBlank(iDisplayLength)){
            return "500";
        }
        Map<String,Object> objectMap=recyclingAdminStatisticsService.queryRecyclingPauperDetail(Long.valueOf(user_id),Integer.parseInt(iDisplayStart),Integer.parseInt(iDisplayLength));
        Map<String, Object> resMap = new HashMap<String, Object>();
        int totalCount=(Integer)objectMap.get("totalCount");
        List<Map<String,Object>> resultMap= (List<Map<String, Object>>) objectMap.get("result");
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

    /**
     * 查看回收详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toQueryPauperHistoryRecyclingAdmin.do")
    public String toQueryPauperHistoryRecyclingAdmin(HttpServletRequest request,HttpServletResponse response){
        String collect_id=request.getParameter("collect_id");
        if(StringUtils.isBlank(collect_id)){
            return "500";
        }
        request.setAttribute("collect_id",collect_id);
        return "pauper/detail_history_pauper";
    }

}
