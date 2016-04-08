package com.recycling.admin.area.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.area.querycase.RecyclingAdminAreaQueryCase;
import com.recycling.admin.area.service.RecyclingAdminAreaService;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.entity.AdminResult;
import com.recycling.common.entity.RecArea;
import com.recycling.common.exception.SimpleException;
import com.recycling.common.util.Pager;

/**
 * @Title:RecyclingAdminPauperAction.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class RecyclingAdminAreaAction extends BaseAdminAction{
	
	@Autowired
	RecyclingAdminAreaService recyclingAdminAreaService;
	
	
	 @RequestMapping("/admin/toListRecyclingAdminArea.do")
	    public String toListRecyclingAdminArea(HttpServletRequest request,HttpServletResponse response){
		String type = (String) request.getAttribute("type");
		if(type== null || "".equals(type)){
			type="0";
		}

		List <RecArea>list = recyclingAdminAreaService.getByParentId(new Long(type));

		//List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
		request.setAttribute("recAreaList",list);

		return "area/list_area";
	}

	@RequestMapping("/admin/queryRecyclingAdminArea.do")
	    public String queryRecyclingAdminArea(HttpServletRequest request,HttpServletResponse response){

	        String iDisplayStart = request.getParameter("iDisplayStart");
	        String iDisplayLength =request.getParameter("iDisplayLength");
	        String sEcho =request.getParameter("sEcho");
	        String area_parent_id=request.getParameter("area_parent_id");
	        String area_id=request.getParameter("area_id");

	        RecyclingAdminAreaQueryCase queryCase=new RecyclingAdminAreaQueryCase();
	        Pager pager = new Pager();
	        pager.setStartRow(Integer.parseInt(iDisplayStart));
	        pager.setPageSize(Integer.parseInt(iDisplayLength));
//	        pager.setCurrentPage(Integer.parseInt(sEcho));
	        queryCase.setPager(pager);
	        List<RecArea> recAreaList=null;

	        try {
	            queryCase.setArea_id(area_id);;
	            queryCase.setArea_parent_id(area_parent_id);
	            recAreaList=recyclingAdminAreaService.queryRecyclingArea(queryCase);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new SimpleException();
	        }

	        Map<String, Object> resMap = new HashMap<String, Object>();
	        resMap.put("sEcho", sEcho);
	        resMap.put("aaData", recAreaList== null ? "":recAreaList );
	        resMap.put("iTotalRecords", pager.getTotalRows());
	        resMap.put("iTotalDisplayRecords", pager.getTotalRows());
	        resMap.put("iDisplayLength", iDisplayLength);
	        resMap.put("iDisplayStart", iDisplayStart);
	        String json= JSON.toJSONString(resMap);
	        renderJson(response,json);
	        return null;
	    }
	 
    /**
     * 修改省信息
     * @param request
     * @param response
     * @return
     */
  @RequestMapping("/admin/toAddRecyclingAdminProvince.do")
    public String toAddRecyclingAdminProvince(HttpServletRequest request,HttpServletResponse response){
    	String type = (String) request.getAttribute("type");
    	String result = "";
    	List <RecArea>list = new ArrayList<RecArea>();
    	if("0".equals(type)){
    		list = recyclingAdminAreaService.getAllActiveByParentId(0L);
    		result="region/update_province_area";    		
    	} else if("1".equals(type)){
    		list = recyclingAdminAreaService.getActiveByParentId(0L);
    		result="region/update_city_area";
    	}else if("2".equals(type)){
    		list = recyclingAdminAreaService.getActiveByParentId(0L);
    		result="region/update_district_area";
    	}else if("3".equals(type)){
    		list = recyclingAdminAreaService.getActiveByParentId(0L);
    		result="region/add_street_area";
    	}
        request.setAttribute("recAreaList",list);
        
        return result;
    }
    
  
  @RequestMapping("/admin/queryRecyclingAdminAreaJs.do")
  public String queryRecyclingAdminAreaJs(HttpServletRequest request,HttpServletResponse response){

      
      String parentId=request.getParameter("parentId");
      String selectType=request.getParameter("selectType");
      List<RecArea> list =  new ArrayList<RecArea>();
      if(!StringUtils.isBlank(parentId)){
	      if("all".equals( selectType)){
	    	  list = recyclingAdminAreaService.getAllActiveByParentId(Long.parseLong(parentId));
	      }else if("active".equals( selectType)){
	    	  list = recyclingAdminAreaService.getActiveByParentId(Long.parseLong(parentId));
	      } else if("noActive".equals( selectType)){
	    	  list = recyclingAdminAreaService.getByParentId(Long.parseLong(parentId));
	      }
      }
    /*
      Map<String, Object> resMap = new HashMap<String, Object>();
      resMap.put("sEcho", sEcho);
      resMap.put("aaData", recAreaList== null ? "":recAreaList );
      resMap.put("iTotalRecords", pager.getTotalRows());
      resMap.put("iTotalDisplayRecords", pager.getTotalRows());
      resMap.put("iDisplayLength", iDisplayLength);
      resMap.put("iDisplayStart", iDisplayStart);*/
      
      String json= JSON.toJSONString(list);
      renderJson(response,json);
      return null;
  }

  
  @RequestMapping("/admin/toUpdateRecyclingAdminAreaIsActive.do")
  public String toUpdateRecyclingAdminAreaIsActive(HttpServletRequest request,HttpServletResponse response){
  	String type = (String) request.getAttribute("type");
//  		recyclingAdminAreaService.updateRecyclingRegion(recArea);
      //List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
//      request.setAttribute("recAreaList",list);
      
      return "region/update_area";
  }
  
  /**
   * 修改地区信息
   * @param request
   * @param response
   * @return
   */
  @RequestMapping("/admin/updateArea.do")
  public String toUpdateRecyclingAdminArea(HttpServletRequest request,HttpServletResponse response){
	  Boolean flag=true;
	  String type = request.getParameter("type");
	  
	  String rec_are_id =request.getParameter("rec_are_id");
      String area_isActive_radio=request.getParameter("area_isActive_radio");  //开通或关闭
      String rec_are_street_street_name ="";
      String rec_are_pid ="";
	  if("0".equals(type)){
		  rec_are_id =request.getParameter("rec_are_id");
	      area_isActive_radio=request.getParameter("area_isActive_radio");
	  }else  if("1".equals(type)){
		  rec_are_id =request.getParameter("rec_are_city_city_id");
	      area_isActive_radio=request.getParameter("area_city_isActive_radio");
	  }else  if("2".equals(type)){
		  rec_are_id =request.getParameter("rec_are_district_district_id");
	      area_isActive_radio=request.getParameter("area_district_isActive_radio");
	  }else  if("3".equals(type)){
		  rec_are_pid =request.getParameter("rec_are_street_district_id");
		  rec_are_id =request.getParameter("rec_are_street_street_id");
		  rec_are_street_street_name=request.getParameter("rec_are_street_street_name");
	      area_isActive_radio=request.getParameter("area_street_isActive_radio");  
	  }
      
      if("3".equals(type)){ //街道的处理
    	  if(StringUtils.isBlank(rec_are_pid)||StringUtils.isBlank(area_isActive_radio) ||
		  (StringUtils.isBlank(rec_are_id)&&StringUtils.isBlank(rec_are_street_street_name))){
              return "500";
          }
    	  
    	  if(!StringUtils.isBlank(rec_are_id)){ //修改
    		  RecArea recArea = recyclingAdminAreaService.getByAreaId(new Long(rec_are_id));
    		  recArea.setIsActive(Integer.parseInt(area_isActive_radio));
    		  flag = recyclingAdminAreaService.updateArea(recArea);
    	  }else{							//添加
    		  RecArea recArea = new RecArea();
    		  recArea.setParentId(Long.parseLong(rec_are_pid));
        	  recArea.setAreaCnName(rec_are_street_street_name);
        	  recArea.setAreaEnName("");
        	  recArea.setIsActive(Integer.parseInt(area_isActive_radio));
        	  recArea.setIsOnline(1);
        	  recArea.setAreaType(4);
        	  recyclingAdminAreaService.addRecArea(recArea);
    	  }
    	 
      }else{ 
    	  if(StringUtils.isBlank(rec_are_id)||StringUtils.isBlank(area_isActive_radio)){
              return "500";
          }
    	  
	    RecArea recArea = recyclingAdminAreaService.getByAreaId(new Long(rec_are_id));
	    recArea.setIsActive(Integer.parseInt(area_isActive_radio));
	    flag = recyclingAdminAreaService.updateArea(recArea);
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

}
