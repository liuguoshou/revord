package com.recycling.common.util;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public final class StringUtil {

	/**
	 * ************************************************
	 * 获得UUID，用于上传文件的名称等需要唯一值的操作
	 * ************************************************
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}

	/**
	 * base64加密
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Encoding(String str) {
		Base64 b64 = new Base64();
		return new String(b64.encode(str.getBytes()));
	}

	/**
	 * base64解密
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Decoding(String str) {
		Base64 b64 = new Base64();
		return new String(b64.decode(str.getBytes()));
	}

	/**
	 * MD5加密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		return DigestUtils.md5Hex(str.getBytes());
	}

	/**
	 * 判断是否符合电子邮件格式
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean isEmail(String mail) {
		if (isBlank(mail)) {
			return false;
		} else {
			String emailAddressPattern = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
			Pattern p = Pattern.compile(emailAddressPattern);
			Matcher m = p.matcher(mail.toLowerCase());
			return m.matches();
		}
	}

	/**
	 * 生成随机字符
	 * 
	 * @param len
	 * @param exclude
	 * @return
	 */
	public static String getRandomString(int len, String exclude) {
		byte[] bs = new byte[len];
		Random rand = new SecureRandom();
		for (int i = 0; i < len; i++) {
			byte b = (byte) (65 + rand.nextInt(26));
			bs[i] = b;
		}
		String randomString = new String(bs);
		if (exclude != null && exclude.indexOf(randomString) >= 0) {
			return getRandomString(len, exclude);
		} else {
			return randomString;
		}
	}
	
	
	/**
	 * 在原数据后面填充0
	 * 
	 * @param strSrc
	 * @param length
	 * @return
	 */
	public static String charForAppend(String strSrc, int length) {
		int strSrcLen = strSrc.length();
		if (strSrcLen < length) {
			int gap = length - strSrcLen;
			StringBuffer sb = new StringBuffer();
			sb.append(strSrc);
			for (int i = 0; i < gap; i++) {
				sb.append("0");
			}
			return sb.toString();
		} else {

			return strSrc;
		}

	}


	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true(空); false(非空)
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 判断字符串是否为空或者为空格
	 * 
	 * @param str
	 * @return true(空); false(非空)
	 */
	public static boolean isBlank(String str) {
		
		if(str == null){
			return true;
		}
		return "".equals(str.trim());
	}
	
	public static String validateNull(Object obj){
		if(obj == null){
			return "";
		}else{
			return String.valueOf(obj);
		}
	}

	/**
	 * 判断一个对象是否为空；
	 */
	public final static boolean isEmpty(Object o) {
		return (o == null);
	}
	
	/**
	 * 检测变量的值是否为一个整型数据；
	 */
	public final static boolean isInt(String value) {
		if (isEmpty(value))
			return false;

		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}
	
	/**
	 * 检测两个字符串是否相同；
	 */
	public final static boolean isSame(String value1, String value2) {
		if (isEmpty(value1) && isEmpty(value2))
			return true;
		else if (!isEmpty(value1) && !isEmpty(value2))
			return (value1.trim().equalsIgnoreCase(value2.trim()));
		else
			return false;
	}
	/**
	 * 若Null转"", 反之返回
	 * 
	 * @param str
	 * @return
	 */
	public static String nullTransEmpty(String str) {
		if (isBlank(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 若Null转"", 反之返回
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (isBlank(str)) {
			return "";
		} else {
			return str.trim();
		}
	}

	/**
	 * 获取随机颜色
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public static Color getRandColor(int i, int j) {
		return null;
	}

	private static final String mobileRegex = "^1\\d{10}$";

	/**
	 * 
	 * janwen
	 * 
	 * @param mobile
	 * @return 手机号码校验
	 * 
	 */
	public static boolean isMobileNo(String mobile) {
		return Pattern.matches(mobileRegex, mobile);
	}

	/**
	 * 去掉“[]”中括号
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteStr(String str) {
		if (str != null && str.contains("[")) {
			str = str.replace("[", "");
		}
		if (str != null && str.contains("]")) {
			str = str.replace("]", "");
		}
		return str;
	}

	/**
	 * 去掉IP字符串前后所有的空格
	 * 
	 * @param IP
	 * @return
	 */
	public static String trimSpaces(String IP) {
		while (IP.startsWith(" ")) {
			IP = IP.substring(1, IP.length()).trim();
		}
		while (IP.endsWith(" ")) {
			IP = IP.substring(0, IP.length() - 1).trim();
		}
		return IP;
	}

	/**
	 * 判断是否是一个IP
	 * 
	 * @param IP
	 * @return
	 */
	public static boolean isIp(String IP) {
		boolean b = false;
		IP = trimSpaces(IP);
		if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String s[] = IP.split("\\.");
			if (Integer.parseInt(s[0]) < 256) {
				if (Integer.parseInt(s[1]) < 256) {
					if (Integer.parseInt(s[2]) < 256) {
						if (Integer.parseInt(s[3]) < 256) {
							b = true;
						}
					}
				}
			}
		}
		return b;
	}

	/**
	 * 包括整数和小数，小数点后最多两位
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		String exp = "^[+]?(([1-9]\\d*[.]?)|(0.))(\\d{0,2})?$";
		if (str.matches(exp)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 把数组转换成String
	 * @param array
	 * @return
	 */
	public static String arrayToString(Object[] array, String split) {
		if (array == null) {
			return "";
		}
		StringBuffer str = new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			if (i != array.length - 1) {
				str.append(array[i].toString()).append(split);
			} else {
				str.append(array[i].toString());
			}
		}
		return str.toString();
	}
	public static boolean isBlank(Object obj) {
		return null ==obj ? true : isBlank(obj.toString());
	}
	
	/**
	 * 随机8位数字和字母，去掉 0,o,i,l
	 *  add by jianjun.huo
	 */
	public static String getRandomCode(int count)
	{
		String[] sourceStr = { "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		StringBuffer returnStr = new StringBuffer("");

		for (int i = 0; i < count; i++)
		{
			Random random = new Random();
			int num = random.nextInt(sourceStr.length);
			returnStr.append(sourceStr[num]);
		}
		return returnStr.toString();
	}
	
	/**
	 * 正则校验
	 * @param regex 正则表达式
	 * @param val   校验值
	 * @return true:通过，false:未通过
	 * @author yanshaodong@hkrt.cn
	 */
	public static boolean checkMatcher(String regex, String val){
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(val);
		return m.matches();
	}
	
	
	/**
	 * 删除字符空格
	 * @author wenjie.mai
	 *
	 * @param strtrim
	 * @return
	 */
	public static String toTrim(String strtrim) {
		if (null != strtrim && !strtrim.equals("")) {
			return strtrim.trim();
		}
		return "";
	}
	
	public static String createUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 将请求的参数map转换成字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String transMapToString(Map<String, String> map) {
		String AMP = "&";
		if (null == map || map.keySet().size() == 0) {
			return "";
		}

		StringBuffer url = new StringBuffer();
		Set<Entry<String, String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();

		Entry<String, String> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			url.append(entry.getKey()).append("=").append(entry.getValue()).append(AMP);
		}

		String strURL = "";
		strURL = url.toString();
		if (AMP.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return strURL;
	}
	
	public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return "".equals(obj.toString().trim());
    }
	
	
	public static String cardNoHidden(String cardNo){
		int len = cardNo.length();
		int hidLen = len/4;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(cardNo.substring(0,hidLen));
		
		for(int i=0;i<len-(hidLen*2);i++){
			sb.append("*");
		}
		
		sb.append(cardNo.substring(len-hidLen));
		return sb.toString();
		
	}
	
	
	public static String nameHidden(String name){
		int len = name.length();
		
		StringBuilder sb = new StringBuilder();
		if(len==1){
			sb.append("*");
		}else if(len==2){
			sb.append(name.substring(0,1)).append("*");
		}else{
			int hidLen = len/3;
			sb.append(name.substring(0,hidLen));
			
			for(int i=0;i<len-(hidLen*2);i++){
				sb.append("*");
			}
			
			sb.append(name.substring(len-hidLen));
		}
		
		
		return sb.toString();
		
	}
	
	
	/**
	 *如果为null返回""
	 * @param str
	 * @return
	 */
	public static  String trimStr(String str){
		
		if(isNull(str)){
			
			return "";
		}else {
			
			return str.trim();
		}
	}
	/**
	 * 变态转换jsonToMap
	 * @param str
	 * @return
	 */
	public static  Map<String,Object>  transJsonToMap(String str){
		String test="";
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(str!=null&str.length()>0){
			test=str.replace("[", "").replace("{", "").replace("}", "").replace("]", "").replace("\"", "");
			String[] strAry=test.split(",");
			
			for(String item:strAry){
				String[] itemAry=item.split(":");
				resultMap.put(itemAry[0], itemAry.length==2?item.split(":")[1]:"");
				
			}
		}
		return resultMap;
	}
	
	/**
	 * 在原数据后面填充字符
	 * @param sourceSrc
	 * @param length
	 * @param fillSrc
	 * @return
	 */
	public static String fillOnRight(String sourceSrc, int length,String fillSrc) {
		int strSrcLen = sourceSrc.length();
		if (strSrcLen < length) {
			int gap = length - strSrcLen;
			StringBuffer sb = new StringBuffer();
			sb.append(sourceSrc);
			for (int i = 0; i < gap; i++) {
				sb.append(fillSrc);
			}
			return sb.toString();
		} else {
			return sourceSrc;
		}

	}
	
	
	/**
	 * 在原数据前面填充字符
	 * @param sourceSrc
	 * @param length
	 * @param fillSrc
	 * @return
	 */
	public static String fillOnLeft(String sourceSrc, int length,String fillSrc) {
		int strSrcLen = sourceSrc.length();
		if (strSrcLen < length) {
			int gap = length - strSrcLen;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < gap; i++) {
				sb.append(fillSrc);
			}
			sb.append(sourceSrc);
			return sb.toString();
		} else {
			return sourceSrc;
		}

	}
	
	
public static String mobileHidden(String mobile){
		
		int len = mobile.length();
		int hidLen = len-6;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(mobile.substring(0,3));
		
		for(int i=0;i<hidLen;i++){
			sb.append("*");
		}
		
		sb.append(mobile.substring(hidLen+3));
		return sb.toString();
		
	}
	
	
	
	public static void main(String args[]){
		System.out.println(mobileHidden("18618197909"));
	}
	
	
	
	

}