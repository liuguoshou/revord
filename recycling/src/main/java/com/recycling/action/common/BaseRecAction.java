package com.recycling.action.common;

import com.recycling.common.config.MutilPropertyPlaceholderConfigurer;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecUser;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.common.util.DomainUtils;
import com.recycling.common.util.ObjectToJson;
import com.recycling.common.util.WebUtils;

import com.recycling.service.beggar.RecBeggarService;
import com.recycling.service.user.RecUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title:BaseCsbkAction.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Controller
public class BaseRecAction {

	protected static final String PAGE_NOT_FOUND = "404";

	protected static final String PAGE_ERROR_FOUND = "500";

	public static Map<String, String> noLoginUrl = new HashMap<String, String>();

	static {
		noLoginUrl.put("regist", "regist");
		noLoginUrl.put("login", "login");
		noLoginUrl.put("wxlogin", "login_wx");
		noLoginUrl.put("wxregist", "regist_wx");
	}

	protected static final int PAGE_SIZE = 10;


	@Autowired
	protected MutilPropertyPlaceholderConfigurer propertyConfigurer;

	@Autowired
	protected DomainUtils domainUtils;

    @Autowired
    protected RecUserService recUserService;

    @Autowired
    protected RecBeggarService recBeggarService;

	protected MemCacheService mem = MemCacheServiceImpl.getInstance();


    protected RecUser getLoginUser(HttpServletRequest request){
        return  recUserService.getLoginUser(request);
    }

    public RecBeggar getRecBeggar(HttpServletRequest request) {
        return recBeggarService.getLoginBeggar(request);
    }

    /**
	 * 打印请求参数log
	 * 
	 * @param request
	 * @return
	 */
	protected String getLogString(HttpServletRequest request) {
		return WebUtils.getRequestPath(request);
	}

	protected String getForwardUrl(String type, String source) {
		StringBuilder sb = new StringBuilder("redirect:/forwardTo.do?");
		sb.append("type=");
		sb.append(type);
		if (StringUtils.isNotBlank(source)) {
			sb.append("&source=");
			sb.append(source);
		}

		return sb.toString();
	}

	protected String getLoginUrl(String source) {
		String loginUrl = getForwardUrl("login", source);

		return loginUrl.replaceAll("redirect:", "");
	}

	public String getSystemErrorJson() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("errMsg", "系统繁忙!");
		return ObjectToJson.map2json(map);
	}
}
