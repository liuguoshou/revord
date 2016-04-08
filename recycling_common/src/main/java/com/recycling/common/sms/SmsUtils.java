package com.recycling.common.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import com.recycling.common.util.DateUtil;
import com.recycling.common.util.Digest;
import com.recycling.common.util.HttpClientUtil;
import com.recycling.common.util.RandomNumberUtils;
import com.recycling.sdk.SingletonClient;

/**
 * @Title:短信工具
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class SmsUtils {

	private static Logger log = Logger.getLogger(SmsUtils.class);

	private static String userid = "410105";

	private static String pwd = "799985";
	
    public static String template2pauper="亲，您负责的区域有用户发出回收请求，请登录后台接单!该用户姓名为%s,手机号为%s。【此时彼刻】";

    public static String template2user="亲，小马哥已经确认接收您的订单！请耐心等待！接单小马哥姓名为%s,手机号为%s。【此时彼刻】";
    
    public static String templateCancel="亲，用户取消了您已接的订单，请重新安排上门时间!该用户姓名为%s,手机号为%s。【此时彼刻】";
    
    public static void main(String[] args) {
		sendSms("18600198020", String.format(template2pauper,"土豆","18600198020"));

//		int num = RandomNumberUtils.getRandomNumber(10);
//
//		System.out.println(num);
	}
	
	public static void sendSmsWithEm(String mobile,String content) throws Exception{
			int i = SingletonClient.getClient().sendSMS(
					new String[] { mobile }, content, "", 1);
			log.info("send em sms the result:" + i + "\n mobile=" + mobile
					+ ":" + content);
	}
	
	public static void sendSmsWithSm(String mobile,String content)throws Exception{
		StringBuilder url = new StringBuilder(
				"http://api.shumi365.com:8090/sms/send.do?");
		String date = DateUtil.formatDate(new Date(), "yyyymmddhhmmss");
		try {
			content = new BASE64Encoder().encode(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String pass = Digest.hmacSign(pwd + date);
		url.append("userid=");
		url.append(userid);
		url.append("&pwd=");
		url.append(pass.toUpperCase());
		url.append("&timespan=");
		url.append(date);
		url.append("&content=");
		url.append(URLEncoder.encode(content));
		url.append("&mobile=");
		url.append(mobile);
		url.append("&msgfmt=UTF8");
		String result = HttpClientUtil.getResponseByGet(url.toString(),
				null);
		log.info("send sm sms the result:" + result + "\n mobile=" + mobile
				+ ":" + content);
	}

	public static void sendSms(String mobile, String content) {

		int num = RandomNumberUtils.getRandomNumber(10);
		if (num % 2 == 0) {
			try {
				sendSmsWithSm(mobile, content);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					sendSmsWithEm(mobile, content);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		} else {
			try {
				sendSmsWithEm(mobile, content);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					sendSmsWithSm(mobile, content);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

    public static String replaceTemplate(String template,String name,String mobile){
        return String.format(template,name,mobile);
    }
}
