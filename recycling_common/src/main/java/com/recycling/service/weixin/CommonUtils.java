package com.recycling.service.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title:CommonUtils.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class CommonUtils {

	private static Logger log = Logger.getLogger(CommonUtils.class);

	public static Map<String, String> chatMap = new HashMap<String, String>();

	static {
	}

	private static MemCacheService mem = MemCacheServiceImpl.getInstance();

	

	public static boolean isQqFace(String content) {
		boolean result = false;

		// 判断QQ表情的正则表达式
		// String qqfaceRegex =
		// "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";

		String qqfaceRegex = "[/:dig|/:bye|/::\\)|/::~|/::B|/:8\\-\\)|/::<|/::$|/::X|/::Z|/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:\\-\\-b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|\\-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B\\-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P\\-\\(|/::'\\||/:X\\-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#\\-0|/:hiphot|/:kiss|/:<&|/:&>]+?";

		Pattern p = Pattern.compile(qqfaceRegex);
		Matcher m = p.matcher(content);
		if (m.matches()) {
			result = true;
		}
		return result;
	}

	private static String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";

	public CommonUtils() {

	}

	public static String getToken(String appId, String secret) {
		WeixinToken at = null;
		String tokenJson = (String) mem.get(RecConstants.ACCESS_TOKEN);
		if (StringUtils.isNotBlank(tokenJson)) {
			at = JSON.parseObject(tokenJson, WeixinToken.class);
			long expires = at.getExpires_in();
			long millis = (System.currentTimeMillis() - expires) / 1000;
			if (millis > 7000) {
				at = getTokenByAppid(appId, secret);
			}
		} else {
			at = getTokenByAppid(appId, secret);
		}

		String t = at.getToken();

		return t;
	}

	public static WeixinToken getTokenByAppid(String appId, String secret) {
		WeixinToken at = null;
		try {
			StringBuilder urlBuilder = new StringBuilder(url);
			urlBuilder.append(appId + "&secret=" + secret);
			String token = WeiXinUtil.httpRequest(urlBuilder.toString(), "GET",
					null);
			JSONObject tokenObj = JSON.parseObject(token);
			at = new WeixinToken();
			at.setToken((String) tokenObj.get("access_token"));
			at.setExpires_in(System.currentTimeMillis());
			mem.set(RecConstants.ACCESS_TOKEN + appId,
					JSON.toJSONString(at));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return at;
	}

	/**
	 * 创建菜单专用，其他地方不可用
	 * 
	 * @return
	 */
	public static String getToken() {
		StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append(RecConstants.WEIXIN_APP_KEY + "&secret="
				+ RecConstants.WEIXIN_APP_SECRET);
		return WeiXinUtil.httpRequest(urlBuilder.toString(), "GET", null);
	}

	public static String getJsTicket(String token) {

		String ticket = (String) mem.get(RecConstants.JS_TICKET_CACHE
				+ RecConstants.getWeixinAppkey());
		if (StringUtils.isBlank(ticket)) {
			StringBuilder sb = new StringBuilder(
					"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=");
			sb.append(token);
			sb.append("&type=jsapi");
			String url = sb.toString();

			String json = WeiXinUtil.httpRequest(url, "GET", null);
			JSONObject obj = JSON.parseObject(json);
			ticket = (String) obj.get("ticket");
			Number errcode = (Number) obj.get("errcode");
			String errmsg = obj.getString("errmsg");

			log.info("++++++++++getJsTicket+++++++++ errcode:" + errcode
					+ "   errmsg:" + errmsg);
			mem.set(RecConstants.JS_TICKET_CACHE
					+ RecConstants.getWeixinAppkey(), ticket, 3600);
		}

		return ticket;
	}

	public static Map<String, String> getJsApiSign(String url) {

		String token = getToken(RecConstants.getWeixinAppkey(),
				RecConstants.getWeixinSecret());

		String jsapi_ticket = getJsTicket(token);
		Map<String, String> ret = Sign.sign(jsapi_ticket, url);

		return ret;
	}

	public static List<String> getWeixinFollowOpenId() {
		StringBuilder sb = new StringBuilder(
				"https://api.weixin.qq.com/cgi-bin/user/get?access_token=");
		String tokenJson = getToken();
		
		JSONObject t=JSON.parseObject(tokenJson);
		String accessToken=(String)t.get("access_token");
		sb.append(accessToken);
		String json = WeiXinUtil.httpRequest(sb.toString(), "GET", null);
		JSONObject obj = JSON.parseObject(json);
		JSONObject o=(JSONObject) obj.get("data");
		List<String> listIds=(List<String>) o.get("openid");
		return listIds;
	}
	public static void main(String[] args) {
//		String str = "/:dig/:dig/:dig/:dig";
//		System.out.println(isQqFace(str));
		
		
		
		
	}

}
