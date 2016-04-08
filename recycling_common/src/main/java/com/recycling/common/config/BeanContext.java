package com.recycling.common.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Title:BeanContext.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class BeanContext implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext _applicationContext)
			throws BeansException {
		applicationContext = _applicationContext;
	}

	public static Object getBean(String beanName) {
		if(applicationContext==null)return null;
		return applicationContext.getBean(beanName);
	}

}
