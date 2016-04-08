package com.recycling.admin.user.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.area.service.RecyclingAdminAreaService;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.region.service.RecyclingAdminRegionService;
import com.recycling.admin.user.querycase.RecyclingAdminUserQueryCase;
import com.recycling.admin.user.service.RecyclingAdminUserService;
import com.recycling.common.entity.RecArea;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.entity.RecUser;
import com.recycling.common.exception.SimpleException;
import com.recycling.common.util.Pager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:RecyclingAdminUserAction.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class RecyclingAdminUserAction extends BaseAdminAction{

    @Autowired
    private RecyclingAdminUserService recyclingAdminUserService;

    @Autowired
    private RecyclingAdminRegionService recyclingAdminRegionService;
    
    @Autowired
    private RecyclingAdminAreaService recyclingAdminAreaService;

    @RequestMapping("/admin/toRecyclingUser.do")
    public String toRecyclingUser(HttpServletRequest request,HttpServletResponse response){
        List<RecRegion> recRegionList=recyclingAdminRegionService.queryAllRecyclingRegion();
        request.setAttribute("recRegionList",recRegionList);
        List <RecArea>recAreaList = recyclingAdminAreaService.getActiveByParentId((new Long("0")));
        request.setAttribute("recAreaList",recAreaList);
        return "user/list_user";
    }

    /**
     * 查询用户列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryRecyclingUser.do")
    public String queryRecyclingUser(HttpServletRequest request,HttpServletResponse response){
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        String area_parent_id=request.getParameter("rec_user_parent_id");
        String area_id =request.getParameter("area_id");
        String region_parent_id=request.getParameter("region_parent_id");
        String region_id=request.getParameter("region_id");
        String moblie=request.getParameter("moblie");
        String real_name=request.getParameter("real_name");
        RecyclingAdminUserQueryCase queryCase=new RecyclingAdminUserQueryCase();
        Pager pager = new Pager();
        pager.setStartRow(Integer.parseInt(iDisplayStart));
        pager.setPageSize(Integer.parseInt(iDisplayLength));
        queryCase.setPager(pager);
        List<RecUser> recUserList=null;
        try {
            queryCase.setArea_parent_id(area_parent_id);
            queryCase.setArea_id(area_id);
            queryCase.setRegion_parent_id(region_parent_id);
            queryCase.setRegion_id(region_id);
            queryCase.setPhone_number(moblie);
            queryCase.setReal_name(real_name);

            recUserList=recyclingAdminUserService.queryRecyclingUser(queryCase);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SimpleException();
        }
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", recUserList== null ? "":recUserList );
        resMap.put("iTotalRecords", pager.getTotalRows());
        resMap.put("iTotalDisplayRecords", pager.getTotalRows());
        resMap.put("iDisplayLength", iDisplayLength);
        resMap.put("iDisplayStart", iDisplayStart);
        String json= JSON.toJSONString(resMap);
        renderJson(response,json);
        return null;
    }

    /**
     * 查看用户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toGetRecyclingUserById.do")
    public String toGetRecyclingUserById(HttpServletRequest request,HttpServletResponse response){
        String user_id=request.getParameter("user_id");
        RecUser recUser=recyclingAdminUserService.getRecyclingUserById(Long.valueOf(user_id));
        request.setAttribute("recUser",recUser);
        return "user/detail_user";
    }
}
