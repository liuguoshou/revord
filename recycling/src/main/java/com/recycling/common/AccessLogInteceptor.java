package com.recycling.common;

import com.recycling.common.config.MutilPropertyPlaceholderConfigurer;
import com.recycling.common.util.DateUtil;
import com.recycling.common.util.WebUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Title:访问日志、action执行时间记录
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class AccessLogInteceptor implements HandlerInterceptor {

	private Logger log = Logger.getLogger(AccessLogInteceptor.class);

	private MutilPropertyPlaceholderConfigurer propertyConfigurer;


	private String date=DateUtil.formatDate(new Date(), "yyyyMMddHHmm");

	public MutilPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			MutilPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse arg1, Object handler, ModelAndView arg3)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			long startTime = (Long) request.getAttribute("m_startTime");
			long endTime = System.currentTimeMillis();
			long executeTime = endTime - startTime;
			log.info("[" + handlerMethod.getBean().getClass().getSimpleName()
					+ " " + handlerMethod.getMethod().getName()
					+ "] executeTime : " + executeTime + "ms");
		}
		boolean isWeixin = (Boolean) request.getAttribute("isWeixin");
		log.info("weixin agent:" + isWeixin);
		request.setAttribute("isWeixin", isWeixin);
		boolean isOnline = propertyConfigurer.isOnline();
		request.setAttribute("isOnline", isOnline);
		//设置时间戳给静态资源使用
		request.setAttribute("datestr", date);
		try {

//			CishibikeUser user = cishibikeUserService.getLoginUser(request);
//			request.setAttribute("userinfo", user);

			String ru = request.getRequestURI();
			String contextPath = request.getContextPath();
			String loginSource = ru.substring(ru.indexOf(contextPath)
					+ contextPath.length());
			request.setAttribute("loginSource", loginSource);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		boolean isWeixin = false;
		String ua = ((HttpServletRequest) request).getHeader("user-agent")
				.toLowerCase();
		log.info("~~~~user agent:" + ua);
		if (ua.indexOf("micromessenger") > 0) {// 是微信浏览器
			isWeixin = true;
		}

		request.setAttribute("isWeixin", isWeixin);

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			String className = handlerMethod.getBean().getClass()
					.getSimpleName();
			Method method = handlerMethod.getMethod();
			String methodName = method.getName();
			String logString = WebUtils.getRequestPath(request);
			log.info("[" + className + ":" + methodName + "] access log: "
					+ logString);

			long startTime = System.currentTimeMillis();
			request.setAttribute("m_startTime", startTime);
		}

		return true;
	}

}
