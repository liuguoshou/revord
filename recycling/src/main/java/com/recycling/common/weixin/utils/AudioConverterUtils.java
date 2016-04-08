package com.recycling.common.weixin.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Title:AudioConverterUtils.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class AudioConverterUtils {

	private static Logger logger = Logger.getLogger(AudioConverterUtils.class);

	public static int amrConvertMp3(String sourceFilePath, String targetFilePath) {

		if (targetFilePath == null || "".equals(targetFilePath)) {
			targetFilePath = "./";
		}
		int second = 1;
		try {

			Runtime rt = Runtime.getRuntime();
			String command = "ffmpeg -i " + sourceFilePath + " "
					+ targetFilePath;

			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null)
				sb.append(line);

			int exitVal = proc.waitFor();
			second = getMusicSecond(sb.toString());
			logger.info(sb.toString() + "---" + exitVal);
			// 删除amr文件
			File file = new File(sourceFilePath);
			if (file.exists()) {
				file.delete();
			}

		} catch (Exception e) {
		}

		return second;
	}

	public static int getMusicSecond(String resultCode) {
		int second = 1;
		if (StringUtils.isNotBlank(resultCode)) {
			if (resultCode.indexOf("Duration:") != -1
					&& resultCode.indexOf(", bitrate:") != -1) {
				resultCode = resultCode.substring(
						resultCode.indexOf("Duration:") + 9,
						resultCode.indexOf(", bitrate:")).trim();
			}
			String times[] = resultCode.split(":");
			if (times != null && times.length == 3) {
				second = (int) (Integer.parseInt(times[0]) * 3600
						+ Integer.parseInt(times[1]) * 60 + Math.round(Double
						.parseDouble(times[2])));
			}
		}
		return second;
	}
}
