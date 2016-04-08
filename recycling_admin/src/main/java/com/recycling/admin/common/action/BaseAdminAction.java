package com.recycling.admin.common.action;

import com.recycling.admin.weixinmenu.serivce.RecyclingAdminWeixinMenuService;
import com.recycling.common.util.FileUploadUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title:BaseAction.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Controller
public class BaseAdminAction {
	
	@Autowired
	protected FileUploadUtils fileUploadUtils;

    @Autowired
    protected RecyclingAdminWeixinMenuService recyclingAdminWeixinMenuService;

    protected void renderJson(HttpServletResponse response,Object object){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.getWriter().print(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
