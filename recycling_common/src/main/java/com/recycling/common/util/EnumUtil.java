package com.recycling.common.util;


/**
 * @Title: EnumUtil.java
 * @Package com.beike.util
 * @Description: Enum工具类
 * @author wh.cheng@sinobogroup.com
 * @date May 5, 2011 5:05:33 PM
 * @version V1.0
 */
public class EnumUtil {

	public static <T extends Enum<T>> T transStringToEnum(Class<T> c,
			String enumString) {
		//判断条件注意null 和"" ,如果库里默认写入是NULL或者""，
		//则需要全部判断，防止Enum.valueOf时NULL或"",找不到emum常量  add by wenhua.cheng
		if (c != null && !"".equals(enumString)) {
			try {
				return Enum.valueOf(c, enumString.trim().toUpperCase());
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public static String transEnumToString(Enum<?> enumValue) {

		if (enumValue == null || "".equals(enumValue.name())) {

			// throw new IllegalArgumentException("enumValue not null");
			return "";
		}
		return enumValue.toString();

	}
	
	public static <T> T getEnumByOrdinal(Class<T> clazz,int index){  
	    T[] c=clazz.getEnumConstants();  
	    return c[index];  
	}

	public static void main(String[] args) {
		 //System.out.print(EnumUtil.transStringToEnum(ProCheckStatus.class,"MATCHED"));
		// System.out.print(AccountType.CASH.toString());
	}

}
