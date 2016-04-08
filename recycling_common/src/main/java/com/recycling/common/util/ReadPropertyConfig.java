package com.recycling.common.util;
/**
 * @Title:加载所有属性文件工具类
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class ReadPropertyConfig {

	
	private static PropertyUtil redisConfig=PropertyUtil.getInstance("config/redis-config");
	
	public static String getRedisConfig(String key){
		
		return redisConfig.getProperty(key);
	}
}
