package com.recycling.common.ds;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.util.PatternMatchUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 设置数据源KEY的拦截器
 * 
 */
public class DataSourceInterceptor implements MethodInterceptor {

	private Logger log = Logger.getLogger(DataSourceInterceptor.class);
	// 方法和使用数据源key的对应关系
	private Map<String, String> attributeSource = new HashMap<String, String>();
	// 数据源key的存储控制器
	private DataSourceKey dataSourceKey;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		final String methodName = invocation.getMethod().getName();
		String bestNameMatch = null;
		for (Iterator<String> it = this.attributeSource.keySet().iterator(); it
				.hasNext();) {
			String mappedName = it.next();
			if (isMatch(methodName, mappedName)
					&& (bestNameMatch == null || bestNameMatch.length() <= mappedName
							.length())) {
				bestNameMatch = mappedName;
			}
		}
		String key = attributeSource.get(bestNameMatch);
		if ("READ".equalsIgnoreCase(key)) {
			log.info("++++++datasource choose the read key!!!");
			dataSourceKey.setReadKey();
		} else if ("WRITE".equalsIgnoreCase(key)) {
			log.info("++++++datasource choose the write key!!!");
			dataSourceKey.setWriteKey();
		} else {
			log.info("++++++datasource choose the other key!!! "+key);
			dataSourceKey.setKey(key);
		}
		return invocation.proceed();
	}

	public void setAttributes(Map<String, String> attributeSource) {
		this.attributeSource = attributeSource;
	}

	private boolean isMatch(String methodName, String mappedName) {
		return PatternMatchUtils.simpleMatch(mappedName, methodName);
	}

	public DataSourceKey getDataSourceKey() {
		return dataSourceKey;
	}

	public void setDataSourceKey(DataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

}
