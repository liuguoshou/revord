package com.recycling.admin.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:后台常用缓存key
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class AdminConstant {

	/**
	 * 用户菜单缓存key
	 */
	public static final String USER_SUBMENU_KEY = "SUBMENUS_";

	/**
	 * 所有子级别菜单
	 */
	public static final String All_SUBMENU_KEY = "ALL_SUBMENUS_";

	/**
	 * 后台用户登录缓存
	 */
	public static final String USER_ADMIN_LOGIN_KEY = "ADMIN_USER_";

	/**
	 * 后台用户登录cookie
	 */
	public static final String USER_ADMIN_LOGIN_COOKIE_KEY = "CSBK_ADMIN_COOKIE_USER_";

	/**
	 * 后台菜单展示使用
	 */
	public static final String CONTEXT_MENUS = "ADMIN_CONTEXT_MENUS";

	/**
	 * 分页个数
	 */
	public static final int PAGE_SIZE = 20;
	
	/**
	 * 后台分页属性
	 */
	public static final String PAGER_TAG_KEY = "admin_pager";

    /**
     * 微信测试号标示
     */
    public static final String WEIXIN_SUBSCRIPT_TEST="weixin_subscript_test";

    /**
     * 菜单icon
     */
    public static Map<Long, String> iconMap = new HashMap<Long, String>();

    static {
        iconMap.put(6L,"icon-dashboard");
        iconMap.put(49L,"icon-bullhorn");
        iconMap.put(46L,"icon-group");
        iconMap.put(58L,"icon-cloud");
        iconMap.put(64L,"icon-heart");
        iconMap.put(70L,"icon-bar-chart");
        iconMap.put(82L,"icon-globe");
    }

}
