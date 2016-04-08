package com.recycling.action.weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.recycling.common.entity.RecBeggar;
import com.recycling.common.weixin.entity.res.Article;
import com.recycling.common.weixin.entity.res.NewsMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recycling.action.common.BaseRecAction;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecUser;
import com.recycling.common.entity.RecWeixinUser;
import com.recycling.common.util.WebUtils;
import com.recycling.common.weixin.entity.res.TextMessage;
import com.recycling.common.weixin.utils.HttpUtils;
import com.recycling.common.weixin.utils.MessageUtil;
import com.recycling.common.weixin.utils.SignUtil;
import com.recycling.service.weixin.CommonUtils;
import com.recycling.service.weixin.Constant;
import com.recycling.service.weixin.WeixinToken;

/**
 * @Title:CishibikeWeixinAction.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Controller
public class RecWeixinAction extends BaseRecAction {

    private Logger logger= Logger.getLogger(RecWeixinAction.class);

	private static String WEIXIN_AUTH_URL = "/weixin/redirectFansMobile.do";

	private static Map<String, String> url = new HashMap<String, String>();
	
	static {
		url.put("user_index", "/user/userIndex.do");
        url.put("account_info","/user/accountInfo.do");
        url.put("init_advise","/user/initUserAdvise.do");
        url.put("price_detail","/user/showPriceDetail.do");
        url.put("service_area","/user/showServiceArea.do");
        url.put("beggar_index","/jsp/collect/login.jsp");
        url.put("init_login","/collect/initLogin.do");
	}

	@RequestMapping("/weixin/su.do")
	public String mobileWeixinUser(HttpServletRequest request,
			HttpServletResponse response) {
		String goType = request.getParameter("goType");
		String parameter = request.getParameter("parameter");
        logger.info("************************mobileWeixinUser|TEST***************************");
        if("init_login".equals(goType)){
            logger.info("==================getInMethod===================");
            RecBeggar recBeggar = getRecBeggar(request);
            logger.info("==================getWeixin===================");
            if (recBeggar != null) {
                String shortUrl = url.get(goType);
                if (StringUtils.isBlank(shortUrl)) {
                    return "redirect:" + domainUtils.getDomainUrl(null, null);
                }
                shortUrl+="?openId="+recBeggar.getOpenId();
                return "redirect:" + domainUtils.getDomainUrl(null, shortUrl);
            }
        }else{
            logger.info("==================getInMethod===================");
            RecUser user = getLoginUser(request);
            logger.info("==================getUser===================");
            if (user != null) {
                String shortUrl = url.get(goType);
                if (StringUtils.isBlank(shortUrl)) {
                    return "redirect:" + domainUtils.getDomainUrl(null, null);
                }
                return "redirect:" + domainUtils.getDomainUrl(null, shortUrl);
            }
        }
		StringBuilder sb = new StringBuilder(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
		sb.append(RecConstants.getWeixinAppkey());
		sb.append("&redirect_uri=");
        sb.append(URLEncoder.encode(domainUtils.getDomain()
                + WEIXIN_AUTH_URL + "?goType=" + goType));
		sb.append("&response_type=code&scope=snsapi_userinfo");
		sb.append("#wechat_redirect");
        logger.info("===================mobileWeixinUser======================:"+sb.toString());
		return "redirect:" + sb.toString();
	}

	@RequestMapping("/weixin/redirectFansMobile.do")
	public String redirectFansMobile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String goType = request.getParameter("goType");
		String code = request.getParameter("code");
		String parameter = request.getParameter("parameter");
		if (StringUtils.isNotBlank(code)) {
			StringBuilder accessTokenUrl = new StringBuilder(
					"https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
			accessTokenUrl.append(RecConstants.getWeixinAppkey());
			accessTokenUrl.append("&secret=");
			accessTokenUrl.append(RecConstants.getWeixinSecret());
			accessTokenUrl.append("&code=");
			accessTokenUrl.append(code);
			accessTokenUrl.append("&grant_type=authorization_code");
			String responseJson = HttpUtils
					.httpGet(accessTokenUrl.toString(), new HashMap());
			JSONObject jb = JSON.parseObject(responseJson);
			String errcode = (String) jb.get("errcode");

			if (StringUtils.isNotBlank(errcode)) {
				return "redirect:" + domainUtils.getDomainUrl(null, null);
			}
			String accessToken = (String) jb.get("access_token");
			String openid = (String) jb.get("openid");
	        if(!"init_login".equals(goType)){
                try {
                    RecWeixinUser weixinUser = MessageUtil.getWeixinUserInfo(accessToken, openid);
                    RecUser recUser=new RecUser();
                    recUser.setOpenId(weixinUser.getOpenId());
                    recUserService.addRecUser(recUser,weixinUser, response,request);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "redirect:" + domainUtils.getDomainUrl(null, null);
                }
            }
			String shortUrl = url.get(goType);
			if (StringUtils.isBlank(shortUrl)) {
				return "redirect:" + domainUtils.getDomainUrl(null, null);
			}

            if (goType.equals("init_login")) {
                shortUrl = shortUrl + "?openId=" + openid;
            }

            logger.info(" ============redirectFansMobile============= "+domainUtils.getDomainUrl(null, shortUrl));
			return "redirect:" + domainUtils.getDomainUrl(null, shortUrl);

		}

		return "redirect:" + domainUtils.getDomainUrl(null, null);
	}

	@RequestMapping(value = "/weixin/weixin.do", method = RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce,
                    RecConstants.ACCESS_TOKEN)) {

				out.print(echostr);
			}
		} catch (Exception e) {
			return null;
		}
		out.close();
		out = null;
		return null;
	}

	@RequestMapping(value = "/weixin/weixin.do", method = RequestMethod.POST)
	public String doPost(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		String responseText = null;
		try {
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(Constant.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			if (msgType.equals(Constant.REQ_MESSAGE_TYPE_EVENT)) {

				String eventKey = requestMap.get("Event");
				String ek = requestMap.get("EventKey");
				if ("subscribe".equals(eventKey)) {
					String message = "欢迎使用回收系统！";
					try {
                        //获取token
						WeixinToken wt = CommonUtils.getTokenByAppid(RecConstants.getWeixinAppkey(), RecConstants.getWeixinSecret());
						RecWeixinUser weixinUser = MessageUtil.getWeixinUserInfo(wt.getToken(), fromUserName);
                        RecUser recUser=new RecUser();
                        recUser.setOpenId(weixinUser.getOpenId());
                        //初始化用户信息
                        recUserService.addRecUser(recUser,weixinUser,response,request);
					} catch (Exception e) {
						e.printStackTrace();
					}
					textMessage.setContent(message);
					responseText = MessageUtil.textMessageToXml(textMessage);

				} else if ("unsubscribe".equals(eventKey)) {
					Cookie cookie = WebUtils.removeableCookie(RecConstants.USER_LOGIN_KEY);
					response.addCookie(cookie);
				}

			}else if (msgType.equals(Constant.REQ_MESSAGE_TYPE_TEXT)) {

                String content = requestMap.get("Content");
                if("回收大厅".equals(content)){
                    NewsMessage newsMessage = new NewsMessage();
                    newsMessage.setToUserName(fromUserName);
                    newsMessage.setFromUserName(toUserName);
                    newsMessage.setCreateTime(new Date().getTime());
                    newsMessage.setMsgType(Constant.RESP_MESSAGE_TYPE_NEWS);
                    newsMessage.setFuncFlag(0);
                    List<Article> articleList = new ArrayList<Article>();
                    Article article = new Article();
                    article.setTitle("乞丐入口");
                    article.setDescription("乞丐回收后台");
                    article.setUrl(domainUtils.getDomainUrl(null,"/jsp/collect/login.jsp"));
                    articleList.add(article);
                    // 设置图文消息个数
                    newsMessage.setArticleCount(articleList.size());
                    // 设置图文消息包含的图文集合
                    newsMessage.setArticles(articleList);
                    responseText = MessageUtil.newsMessageToXml(newsMessage);
                }else{

                    String rcontent = "亲！点击菜单即可完成回收物品操作！";
                    textMessage.setContent(rcontent);
                    responseText = MessageUtil.textMessageToXml(textMessage);
                }

            }






        } catch (Exception e) {
			e.printStackTrace();
		}

		// 响应消息
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(responseText);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
		return null;
	}


	@RequestMapping("/winxin/initLogin.do")
	public String initLogin(HttpServletRequest request,
									 HttpServletResponse response) throws IOException {

		String oppenId = request.getParameter("openid");
		RecUser user = recUserService.getRecUserByOpenId(oppenId);
		recUserService.refreshUserInfoWithCookie(response, user);

		return "redirect:" + "/user/userIndex.do";
	}
}
