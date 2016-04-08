package com.recycling.admin.category.action;

import com.alibaba.fastjson.JSON;
import com.recycling.admin.category.querycase.RecyclingAdminCategroyQueryCase;
import com.recycling.admin.category.service.RecyclingAdminCategroyService;
import com.recycling.admin.common.action.BaseAdminAction;
import com.recycling.admin.entity.AdminResult;
import com.recycling.common.entity.RecCategory;
import com.recycling.common.util.DateUtil;
import com.recycling.common.util.Pager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;

/**
 * @Title:RecyclingAdminCategroryAction.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Controller
public class RecyclingAdminCategroryAction extends BaseAdminAction {


    @Autowired
    private RecyclingAdminCategroyService recyclingAdminCategroyService;

    /**
     * 回收品类维护
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toRecyclingAdminCategroy.do")
    public String toRecyclingAdminCategroy(HttpServletRequest request,HttpServletResponse response){
        List<RecCategory>  recCategoryList=recyclingAdminCategroyService.queryRecyclingParentCategroy();
        request.setAttribute("recCategoryList",recCategoryList);
        return "categroy/list_categroy";
    }


    /**
     * 查询分类列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryRecyclingAdminCategroy.do")
    public String queryRecyclingAdminCategroy(HttpServletRequest request,HttpServletResponse response){
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength =request.getParameter("iDisplayLength");
        String sEcho =request.getParameter("sEcho");
        String categroy_parent_id=request.getParameter("categroy_parent_id");
        RecyclingAdminCategroyQueryCase queryCase=new RecyclingAdminCategroyQueryCase();
        Pager pager = new Pager();
        pager.setStartRow(Integer.parseInt(iDisplayStart));
        pager.setPageSize(Integer.parseInt(iDisplayLength));
        queryCase.setPager(pager);
        List<RecCategory> categoryList=null;
        try {
            queryCase.setParent_category_id(categroy_parent_id);
            categoryList=recyclingAdminCategroyService.queryRecyclingCategroy(queryCase);
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("sEcho", sEcho);
        resMap.put("aaData", categoryList== null ? "":categoryList );
        resMap.put("iTotalRecords", pager.getTotalRows());
        resMap.put("iTotalDisplayRecords", pager.getTotalRows());
        resMap.put("iDisplayLength", iDisplayLength);
        resMap.put("iDisplayStart", iDisplayStart);
        String json= JSON.toJSONString(resMap);
        renderJson(response,json);
        return null;
    }

    /**
     * 添加分类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toAddRecyclingAdminCategroy.do")
    public String toAddRecyclingAdminCategroy(HttpServletRequest request,HttpServletResponse response){
        List<RecCategory>  recCategoryList=recyclingAdminCategroyService.queryRecyclingParentCategroy();
        request.setAttribute("recCategoryList",recCategoryList);
        return "categroy/add_categroy";
    }

    /**
     * 更新分类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/toUpdateRecyclingAdminCategroy.do")
    public String toUpdateRecyclingAdminCategroy(HttpServletRequest request,HttpServletResponse response){
        String categroy_id=request.getParameter("categroy_id");
        if(StringUtils.isBlank(categroy_id)){
            return "500";
        }
        List<RecCategory>  recCategoryList=recyclingAdminCategroyService.queryRecyclingParentCategroy();
        RecCategory category=recyclingAdminCategroyService.getRecyclingCategroyById(Long.valueOf(categroy_id));
        RecCategory parent_category=recyclingAdminCategroyService.getRecyclingCategroyById(Long.valueOf(category.getParentId()));
        Boolean flag=false;
        if(parent_category.getParentId() != 0){
            parent_category=recyclingAdminCategroyService.getRecyclingCategroyById(Long.valueOf(parent_category.getParentId()));
            flag=true;
        }
        List<RecCategory> categorySubList=recyclingAdminCategroyService.queryRecyclingCategroySubList(Long.valueOf(parent_category.getCategoryId()));
        request.setAttribute("recCategoryList",recCategoryList);
        request.setAttribute("recCategory",category);
        request.setAttribute("categorySubList",categorySubList);
        request.setAttribute("parent_category",parent_category);
        request.setAttribute("flag",flag);
        return "categroy/detail_categroy";
    }

    /**
     * 保存分类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/saveRecyclngAdminCategroy.do")
    public String saveRecyclngAdminCategroy(HttpServletRequest request,HttpServletResponse response){
        String category_name=request.getParameter("category_name");
        String categroy_parent_id=request.getParameter("categroy_parent_id");
        String categroy_sub_id=request.getParameter("categroy_sub_id");
        String price=request.getParameter("price");
        String unit=request.getParameter("unit");
        String is_online=request.getParameter("is_online");
        String method=request.getParameter("method");

        if(StringUtils.isBlank(category_name)||StringUtils.isBlank(categroy_parent_id)||StringUtils.isBlank(price)||StringUtils.isBlank(unit)||StringUtils.isBlank(method)){
            return "500";
        }

        RecCategory category=new RecCategory();
        category.setCategoryName(category_name);
        category.setIsOnline(Integer.parseInt(is_online));
        if(StringUtils.isNotBlank(categroy_sub_id)){
            category.setParentId(Long.valueOf(categroy_sub_id));
        }else{
            category.setParentId(Long.valueOf(categroy_parent_id));
        }
        category.setPrice(Double.parseDouble(price));
        category.setUnit(unit);
        Boolean flag=true;
        if(method.equals("save")){
            category.setCreateDate(new Date());
            flag=recyclingAdminCategroyService.addRecyclingCategroy(category);
        }else if(method.equals("update")){
            String categroy_id=request.getParameter("categroy_id");
            String create_date=request.getParameter("create_date");
            if(StringUtils.isBlank(categroy_id)||StringUtils.isBlank(create_date)){
                return "500";
            }
            try {
                category.setCreateDate(DateUtil.parseToDate(create_date,"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
                return "500";
            }
            category.setCategoryId(Long.valueOf(categroy_id));
            flag=recyclingAdminCategroyService.updateRecyclingCategroy(category);
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
     * 查询分类子分类
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/queryRecyclingAdminSubCategroy.do")
    public String queryRecyclingAdminSubCategroy(HttpServletRequest request,HttpServletResponse response){
        String categroy_id=request.getParameter("categroy_id");
        if(StringUtils.isBlank(categroy_id)){
            return "500";
        }
        List<RecCategory> recCategoryList=recyclingAdminCategroyService.queryRecyclingCategroySubList(Long.valueOf(categroy_id));
        renderJson(response,JSON.toJSONString(recCategoryList));
        return null;
    }
}
