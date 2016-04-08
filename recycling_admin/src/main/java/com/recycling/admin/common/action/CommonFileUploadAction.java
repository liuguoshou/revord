package com.recycling.admin.common.action;

import com.recycling.common.util.ObjectToJson;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:文件ajax上传
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Controller
public class CommonFileUploadAction extends BaseAdminAction {

	@RequestMapping("/common/fileUpload.do")
	public String singleFileUpload(HttpServletRequest request,
			@RequestParam() CommonsMultipartFile file,
			HttpServletResponse response)throws Exception {
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");

		String json = fileUploadUtils.uploadSingleFile(request, "type", file);

		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, HttpServletRequest request,HttpServletResponse response) {
		Map<String, String> model = new HashMap<String, String>();
		response.setContentType("text/json; charset=UTF-8");
		response.setHeader("progma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		if (ex instanceof MaxUploadSizeExceededException) {
			model.put("errMsg", "文件过大!");
			try {
				response.getWriter().print(ObjectToJson.map2json(model));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		return null;
	}

}
