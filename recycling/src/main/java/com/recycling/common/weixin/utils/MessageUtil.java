package com.recycling.common.weixin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecUser;
import com.recycling.common.entity.RecWeixinUser;
import com.recycling.common.util.HttpClientUtil;
import com.recycling.common.weixin.entity.res.TextMessage;
import com.recycling.common.weixin.entity.res.Article;
import com.recycling.common.weixin.entity.res.MusicMessage;
import com.recycling.common.weixin.entity.res.NewsMessage;
import com.recycling.common.weixin.entity.res.VoiceMessage;
import com.recycling.service.weixin.CommonUtils;
import com.recycling.service.weixin.WeixinToken;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息工具类
 * 
 */
public class MessageUtil {

	public static void main(String[] args) {
		// String
		// accessToken="aMfSQzMpAzC5jpasDYCtblV_lPuv94eEWK-zbzQnAKbz3aAcThq73Jj8m7ZRS59LolH8oi6X8m5KAodagShXaw";
		// String openid="oFDdYuF0r3ZkF9_Jnm14HQDV8Oqo";
		// FansenterWeixinUser fwu=null;
		// try {
		// fwu=MessageUtil.getWeixinUserInfo(accessToken, openid);
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (DocumentException e) {
		// e.printStackTrace();
		// }
		// System.out.println(fwu);
		// String url =
		// "https://api.weixin.qq.com/sns/userinfo?access_token=OezXcEiiBSKSxW0eoylIeBDlQKnrqosGpQrXAXyXZRC2M1Eu9ceVw1kqVbCzsKYVet-eWP2N6RcpJjCPkjxQLeWedGGWF0Dm13i1ksuDynzp8vDI4aGV584oYAJY1yloi1ZySYQBa74bcWNZx8JbcQ&openid=oz-qxt8KNaKccnme4mW909UiGK1g&lang=zh_CN";
		//
		// String response = HttpClientUtil.getResponseByGet(url, null,
		// "utf-8");
		// System.out.println(response);

		// String token = CommonUtils.getToken();
		// JSONObject object = JSON.parseObject(token);
		// token = object.getString("access_token");
		// String openid = "oFDdYuD_6LRAYeH7Ko_cEUykJzfE";
		// String content = "{\"touser\":\""
		// + openid
		// +
		// "\",\"msgtype\":\"text\",\"text\":{\"content\":\"<a href='http://www.baidu.com'>你好</a>\"}}";
		// sendMainMessage(token, openid, content);

	}

	public static RecWeixinUser getAuthWeixinUserInfo(String accessToken,
			String openid) throws Exception {

		StringBuilder urlBuilder = new StringBuilder(
				"https://api.weixin.qq.com/sns/userinfo?access_token=");
		urlBuilder.append(accessToken);
		urlBuilder.append("&openid=");
		urlBuilder.append(openid);
		urlBuilder.append("&lang=zh_CN");
		String responseText = HttpClientUtil.getResponseByGet(
                urlBuilder.toString(), null, "utf-8");

		JSONObject jb = JSON.parseObject(responseText);

		String city = (String) jb.get("city");
		String oid = (String) jb.get("openid");
		String nickName = (String) jb.get("nickname");
		String imgUrl = (String) jb.get("headimgurl");

        RecWeixinUser recWeixinUser=new RecWeixinUser();
        recWeixinUser.setCity(city);
        recWeixinUser.setOpenId(oid);
        recWeixinUser.setWeixinHeadImgUrl(imgUrl);
		if (StringUtils.isNotBlank(nickName)) {
            recWeixinUser.setWeixinNickName(nickName.replaceAll(
					"[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", ""));
		}
        recWeixinUser.setSex((Integer) jb.get("sex"));
		return recWeixinUser;

	}

	public static RecWeixinUser getWeixinUserInfo(String accessToken,
			String openid) throws MalformedURLException, DocumentException {

		
		WeixinToken wt = CommonUtils
				.getTokenByAppid(RecConstants.getWeixinAppkey(),
                        RecConstants.getWeixinSecret());
		
		StringBuilder urlBuilder = new StringBuilder(
				"https://api.weixin.qq.com/cgi-bin/user/info?access_token=");
		urlBuilder.append(wt.getToken());
		urlBuilder.append("&openid=");
		urlBuilder.append(openid);
		urlBuilder.append("&lang=zh_CN");
		String responseText = HttpClientUtil.getResponseByGet(
				urlBuilder.toString(), null, "utf-8");
		JSONObject jb = JSON.parseObject(responseText);
		Integer errcode = (Integer) jb.get("errcode");
		if (errcode != null && errcode > 0) {
			wt = CommonUtils.getTokenByAppid(RecConstants.getWeixinAppkey(), RecConstants.getWeixinSecret());
			return getWeixinUserInfo(wt.getToken(), openid);
		}

		int subscribe = (Integer) jb.get("subscribe");
		if (subscribe == 0) {
			try {
				
				return getAuthWeixinUserInfo(accessToken, openid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String city = (String) jb.get("city");
		String oid = (String) jb.get("openid");
		String nickName = (String) jb.get("nickname");
		String imgUrl = (String) jb.get("headimgurl");

        RecWeixinUser recWeixinUser=new RecWeixinUser();
        recWeixinUser.setCity(city);
        recWeixinUser.setOpenId(oid);
        recWeixinUser.setWeixinHeadImgUrl(imgUrl);
        if (StringUtils.isNotBlank(nickName)) {
            recWeixinUser.setWeixinNickName(nickName.replaceAll(
                    "[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", ""));
        }
        recWeixinUser.setSex((Integer) jb.get("sex"));
        return recWeixinUser;

	}

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点

		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 处理音频
	 */
	public static String voiceMessageToXml(VoiceMessage voiceMessage) {
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

}
