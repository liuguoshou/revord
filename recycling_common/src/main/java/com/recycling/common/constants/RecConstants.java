package com.recycling.common.constants;

import com.recycling.common.config.BeanContext;
import com.recycling.common.config.MutilPropertyPlaceholderConfigurer;

/**
 * @Title:FansenterUserConstants.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class RecConstants {

	public static final String USER_PASSWORD_KEY = "X+vWCW1zuKE=X+vWCW1zuKE=";

	public static final String USER_PK_SOURCE_FROM = "from";

	public static final String USER_PK_SOURCE_TO = "to";

	public static final String USER_PK_TYPE_UP = "up";

	public static final String USER_PK_TYPE_DOWN = "down";

	public static final String USER_LOGIN_KEY = "rec_user_uuid_";
	
	public static final String BEGGAR_LOGIN_KEY = "rec_beggar_uuid_";

	public static final String DOMAIN_COOKIE = "rec_domain";

	// 所有系列缓存
	public static final String SERIES_ALL = "SERIES_ALL";

	// 系列详情
	public static final String SERIES_DETAIL = "SERIES_DETAIL_";

	// 所有分类缓存
	public static final String CATLOG_ALL = "CATLOG_ALL";

	// 按照分类 缓存属性
	public static final String PROPERTY_CATLOG = "PROPERTY_CATLOG_";

	// 城市信息
	public static final String CITY_CATLOG = "CITY_CATLOG__";
	// 地域信息
	public static final String REGION_CATLOG = "REGION_CATLOG__";
	
	public static final String REC_AREA_INFO="REC_AREA_INFO_";
	
	public static final String REC_AREA_LIST="REC_AREA_LIST_";
	
	public static final String REC_CATEGORY_INFO="REC_CATEGORY_INFO_";
	
	public static final String REC_CATEGORY_LIST="REC_CATEGORY_LIST_";
	
	public static final String REC_ALL_CATEGORY_MAP="REC_ALL_CATEGORY_MAP";
	
	public static final String REC_CATEGORY_RELATION_MAP="REC_CATEGORY_RELATION_MAP";
	
	public static final String REC_REGION_INFO="REC_REGION_INFO_";
	
	public static final String REC_REGION_LIST="REC_REGIOIN_LIST_";
	
	public static final String REC_REGION_MAP="REC_REGIOIN_MAP";

	// 商品详情
	public static final String GOODS_DETAIL = "GOODS_DETAIL_";
	// 系列商品列表
	public static final String SERIES_GOODS_LIST = "SERIES_GOODS_LIST_";

	// 商品详情属性
	public static final String GOODS_PROPERTY_VALUE = "GOODS_PROPERTY_VALUE_";
	// 用户详情
	public static final String USER_DETAIL = "REC_USER_DETAIL_";
	
	// 乞丐详情
	public static final String BEGGAR_DETAIL = "REC_BEGGAR_DETAIL_";

	public static final int PAGE_SIZE_SMALL = 5;
	
	public static final int PAGE_SIZE = 20;

	public static final String USER_MOBILE_RANDOM = "USER_MOBILE_RANDOM_COUNT_";

	public static final String USER_MOBILE_RANDOM_CODE = "USER_MOBILE_RANDOM_CODE";

	public static final int cache_time = 60 * 60;// 缓存1小时

	/**
	 * 情人节活动随即验证码
	 */
	public static final String USER_MOBILE_RANDOM_CODE_ACTIVITY_LOVER = "USER_MOBILE_RANDOM_CODE_ACTIVITY_LOVER_";

//	public static final String WEIXIN_APP_KEY = "wx785858b0c552e9c4";
	public static String WEIXIN_APP_KEY = "wx85adbfd95fba6f9d";

//	public static final String WEIXIN_APP_SECRET = "8495f129873b9ce087cfaec9292cbb5e";
	public static String WEIXIN_APP_SECRET = "b236911dd10115d7c949b9c212af5cd4";

	public static final String ACCESS_TOKEN = "dddce110854e7dc20e8f98c9bb79455e";

	public static final String WEIXIN_USER_DETAIL = "WEIXIN_USER_DETAIL_";

	public static MutilPropertyPlaceholderConfigurer propertyConfigurer = (MutilPropertyPlaceholderConfigurer) BeanContext
			.getBean("propertyConfigurer");

	public static final String APPKEY_NAME = "weixin_appkey";
	public static final String APPSECRET_NAME = "weixin_appsecret";
	public static final String JS_TICKET_CACHE = "JS_TICKET_CACHE_";

    public static final String APPKEY_NAME_DINGYUE = "weixin_appkey_dingyue";
    public static final String APPSECRET_NAME_DINGYUE  = "weixin_appsecret_dingyue";

	public static String getWeixinAppkey() {
		return propertyConfigurer.getProperty(APPKEY_NAME);
	}

	public static String getWeixinSecret() {

		return propertyConfigurer.getProperty(APPSECRET_NAME);
	}

    //根据标识获取订阅号或者服务号appkey
    public static String getWeixinAppkey(String tag) {
        String app_key="";
        if(tag.contains("weixin_subscript")){
            app_key=propertyConfigurer.getProperty(APPKEY_NAME_DINGYUE);
        }else {
            app_key=propertyConfigurer.getProperty(APPKEY_NAME);
        }
        return app_key;
    }

    //根据标识获取订阅号或者服务号appsecret
    public static String getWeixinSecret(String tag) {
        String app_secret="";
        if(tag.contains("weixin_subscript")){
            app_secret=propertyConfigurer.getProperty(APPSECRET_NAME_DINGYUE);
        }else {
            app_secret=propertyConfigurer.getProperty(APPSECRET_NAME);
        }
        return app_secret;
    }





}
