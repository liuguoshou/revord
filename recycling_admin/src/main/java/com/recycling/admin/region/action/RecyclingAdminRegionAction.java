package com.recycling.admin.region.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.area.service.RecyclingAdminAreaService;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.entity.AdminResult;
import com.recycling.admin.entity.AdminUser;
import com.recycling.admin.region.querycase.RecyclingAdminRegionQueryCase;
import com.recycling.admin.region.service.RecyclingAdminRegionService;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.exception.SimpleException;
import com.recycling.common.util.DateUtil;
import com.recycling.common.util.Pager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminRegionAction.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class RecyclingAdminRegionAction extends BaseAdminAction{


    @Autowired
    private RecyclingAdminRegionService recyclingAdminRegionService;

	@Autowired
	RecyclingAdminAreaService recyclingAdminAreaService;
    
    /**
     * 根据区域ID查询小区
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/getRecyclingAdminRegionByParentId.do")
    public String getRecyclingAdminRegionByParentId(HttpServletRequest request,HttpServletResponse response){
        String region_parent_id=request.getParameter("region_parent_id");
        if(StringUtils.isBlank(region_parent_id)){
            return "500";
        }
       // List<RecRegion> recRegionList=recyclingAdminRegionService.getByParentId(Long.valueOf(region_parent_id));
        List<RecRegion> recRegionList=recyclingAdminRegionService.getByStreetId(Long.valueOf(region_parent_id));
        renderJson(response, JSON.toJSONString(recRegionList));
        return null;
    }

    
    /**
     * 根据区域街道ID查询小区
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/getRecyclingAdminRegionByStreetId.do")
    public String getRecyclingAdminRegionByStreetId(HttpServletRequest request,HttpServletResponse response){
        String region_parent_id=request.getParameter("region_parent_id");
        if(StringUtils.isBlank(region_parent_id)){
            return "500";
        }
        List<RecRegion> recRegionList=recyclingAdminRegionService.getByStreetId(Long.valueOf(region_parent_id));
        renderJson(response, JSON.toJSONString(recRegionList));
        return null;
    }

    /**
     * 小区维护
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toRecyclingAdminRegion.do")
    public String toRecyclingAdminRegion(HttpServletRequest request,HttpServletResponse response){
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        request.setAttribute("recRegionList",recRegionList);
        
        List <RecArea>recAreaList = recyclingAdminAreaService.getActiveByParentId((new Long("0")));
        request.setAttribute("recAreaList",recAreaList);
      
        return "region/list_region";
    }

    /**
     * 查询小区列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryRecyclingAdminRegion.do")
    public String queryRecyclingAdminRegion(HttpServletRequest request,HttpServletResponse response){

        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        String region_parent_id=request.getParameter("region_parent_id");
        AdminUser au = (AdminUser) request.getAttribute("loginUser");
        RecyclingAdminRegionQueryCase queryCase=new RecyclingAdminRegionQueryCase();
        queryCase.setUserId(au.getAdminUserId());
        Pager pager = new Pager();
        pager.setStartRow(Integer.parseInt(iDisplayStart));
        pager.setPageSize(Integer.parseInt(iDisplayLength));
//        pager.setCurrentPage(Integer.parseInt(sEcho));
        queryCase.setPager(pager);
        List<RecRegion> recRegionList=null;

        try {
            queryCase.setRegion_parent_id(region_parent_id);
            recRegionList=recyclingAdminRegionService.queryRecyclingRegion(queryCase);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SimpleException();
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", recRegionList== null ? "":recRegionList );
        resMap.put("iTotalRecords", pager.getTotalRows());
        resMap.put("iTotalDisplayRecords", pager.getTotalRows());
        resMap.put("iDisplayLength", iDisplayLength);
        resMap.put("iDisplayStart", iDisplayStart);
        String json= JSON.toJSONString(resMap);
        renderJson(response,json);
        return null;
    }

    /**
     * 修改小区信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toDetailRecyclingAdminRegion.do")
    public String toDetailRecyclingAdminRegion(HttpServletRequest request,HttpServletResponse response){
        String region_id=request.getParameter("region_id");
        if(StringUtils.isBlank(region_id)){
            return "500";
        }
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        RecRegion recRegion=recyclingAdminRegionService.getRecyclingRegionById(Long.valueOf(region_id));
        request.setAttribute("region",recRegion);
        request.setAttribute("recRegionList",recRegionList);
        return "region/detail_region";
    }



    
    /**
     * 开通小区信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toAddRecyclingAdminRegion.do")
    public String toAddRecyclingAdminRegion(HttpServletRequest request,HttpServletResponse response){
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        
        List list = recyclingAdminRegionService.getAllProvence();
        List <RecArea>recAreaList = recyclingAdminAreaService.getActiveByParentId((new Long("0")));
        request.setAttribute("recAreaList",recAreaList);
        System.out.println(list);
        request.setAttribute("recRegionList",recRegionList);
        return "region/add_region";
    }

    /**
     * 保存小区数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/saveRecyclingAdminRegion.do")
    public String saveRecyclingAdminRegion(HttpServletRequest request,HttpServletResponse response){
      //  String region_parent_id =request.getParameter("region_parent_id");
    	
    	String region_parent_id=request.getParameter("rec_are_region_street_id");
    	
    	String rec_are_region_region_id = request.getParameter("rec_are_region_region_id");
    	String region_cn_name=request.getParameter("region_cn_name");
        String method=request.getParameter("method");
        String area_region_isActive_radio = request.getParameter("area_region_isActive_radio");
        
        if(StringUtils.isBlank(region_parent_id)||StringUtils.isBlank(region_cn_name)||StringUtils.isBlank(method)){
            return "500";
        }
        RecRegion recRegion=new RecRegion();
        //默认北京
        //recRegion.setAreaId(101L);
        recRegion.setAreaId(Long.parseLong(region_parent_id));
        //激活状态
        //recRegion.setIsActive(0);
        recRegion.setIsActive(1);
        //上线
        recRegion.setIsOnline(1);
        recRegion.setRegionCnName(region_cn_name);
        recRegion.setRegionEnName("");
        recRegion.setParentId(Long.valueOf(region_parent_id));
        recRegion.setVersion(0L);
        Boolean flag=true;
        if(method.equals("save")){
        	
        	if(StringUtils.isBlank(rec_are_region_region_id) && !StringUtils.isBlank(region_cn_name) ){ //添加
        		 recRegion.setCreateDate(new Date());
                 recRegion.setUpdateDate(new Date());
                 flag=recyclingAdminRegionService.addRecyclingRegion(recRegion);
        	}else if(!StringUtils.isBlank(rec_are_region_region_id)){ //修改
        		if(StringUtils.isBlank(rec_are_region_region_id)){
                    return "500";
                }
        		recRegion = recyclingAdminRegionService.getRecyclingRegionByIdAllState(Long.parseLong(rec_are_region_region_id));
        		recRegion.setIsActive(Integer.parseInt(area_region_isActive_radio));
        		recRegion.setUpdateDate(new Date());
                flag=recyclingAdminRegionService.updateRecyclingRegion(recRegion);
        	}
           
            
            
            
        }else if(method.equals("update")){
            recRegion.setUpdateDate(new Date());
            String create_date=request.getParameter("create_date");
            try {
                recRegion.setCreateDate(DateUtil.parseToDate(create_date,"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String region_id=request.getParameter("region_id");
            if(StringUtils.isBlank(region_id)){
                return "500";
            }
            recRegion.setRegionId(Long.valueOf(region_id));
            flag=recyclingAdminRegionService.updateRecyclingRegion(recRegion);
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
    
    
    @RequestMapping("/admin/queryRecyclingAdminRegionJs.do")
    public String queryRecyclingAdminRegionJs(HttpServletRequest request,HttpServletResponse response){

        
        String parentId=request.getParameter("parentId");
        String selectType=request.getParameter("selectType");
        List<RecRegion> list =  new ArrayList<RecRegion>();
        if(!StringUtils.isBlank(parentId)){
  	      if("all".equals( selectType)){
  	    	  list = recyclingAdminRegionService.getAllByParentId(Long.parseLong(parentId));
  	      }else if("active".equals( selectType)){
  	    	  
  	      } else if("noActive".equals( selectType)){
  	    	  
  	      }
        }
      
        
        String json= JSON.toJSONString(list);
        renderJson(response,json);
        return null;
    }
}
