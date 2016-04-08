package com.recycling.common.util;

public class MyStringUtils {

	public final static String orgStr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String getRandomStr(int length) {
		StringBuffer sb = new StringBuffer();
		int n;
		for (int i = 0; i < length; i++) {
			n = (int) (Math.random() * 62);
			sb.append(orgStr.substring(n, n + 1));
		}
		return sb.toString();

	}

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++)
			System.out.println(MyStringUtils.getRandomStr(60));

	}

}
