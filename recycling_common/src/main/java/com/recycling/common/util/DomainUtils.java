package com.recycling.common.util;

import com.recycling.common.config.MutilPropertyPlaceholderConfigurer;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title:DomainUtils.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Service
public class DomainUtils {

	@Autowired
	private MutilPropertyPlaceholderConfigurer mutilPropertyPlaceholderConfigurer;

	public static final String domain_online = "http://www.huishoudai.com/";
	// private static final String domain_online = "http://112.124.113.4";

	private static final String domain_dev = "http://localhost/";

	private static final String domain_test = "http://121.41.57.46:8090";

	public static final String short_domain = ".huishoudai.com";

//	public static final String cdn_domain = "http://csbk.oss-cn-hangzhou.aliyuncs.com";
	public static final String cdn_domain = "http://img.nowandthen.com.cn";

	public static String getShortDomain() {
		return short_domain;
	}

	public String getDomainUrl(String domain, String shortUrl) {
		if (mutilPropertyPlaceholderConfigurer.isOnline()) {
			if (StringUtils.isNotBlank(domain)) {
				if (StringUtils.isBlank(shortUrl)) {
					return "http://" + domain + short_domain;
				} else {
					return "http://" + domain + short_domain + shortUrl;
				}
			} else {

				return shortUrl == null ? domain_online : domain_online
						+ shortUrl;
			}
		} else {
			if (StringUtils.isNotBlank(domain)) {
				return shortUrl == null ? getDomain() + "?domain=" + domain
						: getDomain() + shortUrl + "?domain=" + domain;
			} else {
				return shortUrl == null ? getDomain() : getDomain() + shortUrl;
			}
		}
	}

	public String getDomain() {
		if (mutilPropertyPlaceholderConfigurer.isDevEnv())
			return domain_dev;
		else if (mutilPropertyPlaceholderConfigurer.isTestEnv())
			return domain_test;
		return domain_online;
	}

	public String getDomain(HttpServletRequest request) {
		if (mutilPropertyPlaceholderConfigurer.isDevEnv()) {
			String addr = IPUtils.getIp();
			int port = request.getServerPort();
			StringBuilder sb = new StringBuilder("http://");
			sb.append(addr);
			sb.append(":");
			sb.append(port);
			return sb.toString();
		} else if (mutilPropertyPlaceholderConfigurer.isTestEnv()) {
			return domain_test;
		}
		return domain_online;
	}
}
