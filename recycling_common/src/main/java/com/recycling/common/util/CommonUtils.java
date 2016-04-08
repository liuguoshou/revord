package com.recycling.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @Title:CommonUtils.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class CommonUtils {

	/**
	 * reverse map's key & values
	 * 
	 * @param map
	 * @return
	 */
	public static Map<?, ?> reverseMapKeyValues(Map<?, ?> map) {
		if (map == null)
			return null;
		Set<String> keys = (Set<String>) map.keySet();
		Map<Object, Object> reverseMap = new HashMap<Object, Object>();
		for (Object object : keys) {
			Object oldValue = map.get(object);
			reverseMap.put(oldValue, object);
		}

		return reverseMap;

	}

	/**
	 * obj array to string array
	 * 
	 * @param obj
	 * @return
	 */
	public static String[] objectArrayToStringArray(Object obj[]) {
		String keys[] = null;
		if (obj != null && obj.length > 0) {
			keys = new String[obj.length];
			for (int i = 0; i < obj.length; i++) {
				keys[i] = (String) obj[i];
			}
		}
		return keys;
	}

	/**
	 * stringTolist
	 * 
	 * @param ids
	 * @return
	 */
	public static List<Long> stringToList(String ids) {
		if (StringUtils.isBlank(ids))
			return null;
		String str[] = ids.split(",");
		List<Long> listIds = new ArrayList<Long>();
		for (String s : str) {
			listIds.add(Long.parseLong(s));
		}
		return listIds;
	}

	/**
	 * setToString
	 * 
	 * @param set
	 * @return
	 */
	public static String setToString(Set<?> set) {
		if (set == null || set.size() == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Object object : set) {
			sb.append(object);
			sb.append(",");
		}
		String ids = sb.substring(0, sb.lastIndexOf(","));

		return ids;
	}
	
	
	/**
	 * listToString 多用于用户数据库列表查询
	 * 
	 * @param list
	 * @return
	 */
	public static String listToStringWithString(List<?> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append("'");
			sb.append(list.get(i));
			sb.append("'");
			sb.append(",");
		}
		String ids = sb.substring(0, sb.lastIndexOf(","));

		return ids;
	}

	/**
	 * listToString 多用于用户数据库列表查询
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<?> list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
			sb.append(",");
		}
		String ids = sb.substring(0, sb.lastIndexOf(","));

		return ids;
	}

	public static String[] getDetailRedisKeys(List<Long> listIds,
			String keyPrefix) {
		String[] keys = new String[listIds.size()];
		for (int i = 0; i < listIds.size(); i++) {
			keys[i] = keyPrefix + listIds.get(i);
		}
		return keys;
	}
	
	public static List<String> getListStr(List<Map<String, Object>> listMap,
			String columName) {
		List<String> listIds = new ArrayList<String>();
		for (Map<String, Object> map : listMap) {
			String id = (String) map.get(columName);
			listIds.add(id);
		}
		return listIds;
	}

	public static List<Long> getListIds(List<Map<String, Object>> listMap,
			String columName) {
		List<Long> listIds = new ArrayList<Long>();
		for (Map<String, Object> map : listMap) {
			Long id = (Long) map.get(columName);
			listIds.add(id);
		}
		return listIds;
	}

	public static String formatPicName() {
		StringBuilder pic = new StringBuilder();
		pic.append(DateUtil.formatDate(new Date(), "yyyyMMddHHmmssSSS"))
				.append("_").append(IdGen.genId());
		return pic.toString();
	}
}
