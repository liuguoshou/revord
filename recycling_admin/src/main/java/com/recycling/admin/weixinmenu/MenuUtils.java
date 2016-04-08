package com.recycling.admin.weixinmenu;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recycling.service.weixin.CommonUtils;
import com.recycling.service.weixin.WeiXinUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Title:MenuUtils.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class MenuUtils {
	
	// 菜单创建（POST） 限100（次/天）  
    public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
  
    public static String createMenu(String jsonMenu) {
        String resultStr = "";  
        // 调用接口获取token  
        String tokenJson = CommonUtils.getToken();
        JSONObject object=JSON.parseObject(tokenJson);
        String token=object.getString("access_token");
        if (token != null) {  
            // 调用接口创建菜单  
            int result = createMenu(jsonMenu, token);  
            // 判断菜单创建结果  
            if (0 == result) {  
                resultStr = "菜单创建成功";  
            } else {  
                resultStr = "菜单创建失败，错误码：" + result;  
            }  
        }  
  
        return resultStr;  
    }  
  
  
    /** 
     * 创建菜单 
     *  
     * @param jsonMenu 
     *            菜单的json格式 
     * @param accessToken 
     *            有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */  
    public static int createMenu(String jsonMenu, String accessToken) {  
  
        int result = 0;  
        // 拼装创建菜单的url  
        String url = MENU_CREATE.replace("ACCESS_TOKEN", accessToken);  
        // 调用接口创建菜单  
        String json = WeiXinUtil.httpRequest(url, "POST", jsonMenu);
        JSONObject  jsonObject=JSON.parseObject(json);
        if (null != jsonObject) { 
            if (0 != jsonObject.getIntValue("errcode")) {  
                result = jsonObject.getIntValue("errcode");  
            }  
        }  
  
        return result;  
    }  
    
    public static void main(String[] args) {
    	
    	ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/cishibike-datasource.xml");
		System.out.println(MenuUtils.createMenu(null));
	}
  
}
