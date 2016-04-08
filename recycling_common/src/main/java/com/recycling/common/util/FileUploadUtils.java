package com.recycling.common.util;

import com.recycling.common.config.MutilPropertyPlaceholderConfigurer;
import com.recycling.common.util.FileUtils.Specification;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Title:图片上传
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
@Service
public class FileUploadUtils {

	private static final String UPLOAD_BASE_PATH = "upload/";

	private static List<String> picExtension = new ArrayList<String>();

	private static List<String> voiceExension = new ArrayList<String>();

	private static Set<String> fileType = new HashSet<String>();

	private static final String FILE_REQUEST_URL = "IMAGEPATH";

	@Autowired
	private DomainUtils domainUtils;

	@Autowired
	private MutilPropertyPlaceholderConfigurer mutilPropertyPlaceholderConfigurer;

	private static String paths = null;

	static {
		picExtension.add("jpg");
		picExtension.add("jpeg");
		picExtension.add("gif");
		picExtension.add("bmp");
		picExtension.add("png");

		picExtension.add("JPG");
		picExtension.add("JPEG");
		picExtension.add("GIF");
		picExtension.add("BMP");
		picExtension.add("PNG");

		voiceExension.add("mp3");

		fileType.add("goodsinfo");
		fileType.add("groupinfo");
		fileType.add("pkpic");
		fileType.add("fansay");
		fileType.add("clubinfo");
		fileType.add("activityinfo");

	}

	public String uploadSingleFile(HttpServletRequest request, String fileType,
			CommonsMultipartFile file) {
		String filePath = null;
		String type = request.getParameter(fileType);
		Map<String, String> jsonMap = new HashMap<String, String>();
		if (!file.isEmpty()) {
			String contextPath = request.getContextPath();
			String isCompress = request.getParameter("isCompress");
			FileInputStream fis = null;
			try {
				String extension = FilenameUtils.getExtension(file
						.getOriginalFilename());
				// 控制上传格式。。。
				String fileTypeJson = fileType(type, extension);
				if (StringUtils.isNotBlank(fileTypeJson)) {
					return fileTypeJson;
				}
				// 控制大小
				long size = file.getSize();
				String sizeTypeJson = fileSize(size, extension);
				if (StringUtils.isNotBlank(sizeTypeJson)) {
					return sizeTypeJson;
				}
				String fileName = CommonUtils.formatPicName();
				String width = request.getParameter("width");
				String height = request.getParameter("height");
				Specification specifications = null;
				if (StringUtils.isNotBlank(width)
						&& StringUtils.isNotBlank(height)) {
					specifications = new Specification(Integer.parseInt(width),
							Integer.parseInt(height));
				} else {
					specifications = new Specification(200, 200);
				}
				// 组装生成图片路径
				filePath = createFilePath(request, type, extension, fileName,
						specifications);
				// 组装 http 路径
				String httpUrl = createHttpPicUrl(request, contextPath, type,
						fileName, extension, specifications);
				// File filex = new File(filePath);
				// file.getFileItem().write(new File(filePath));
				// 图片压缩 默认是压缩成200*200
				File f = null;
				if ("false".equals(isCompress)) {
					// 需要压缩 文件大小
					f = new File(filePath);
					File parent = new File(f.getParent());
					if (!parent.exists()) {
						parent.mkdir();
					}
					// 压缩图片大小
					if("png".equals(extension)){
						file.getFileItem().write(f);
					}else{
						f = FileUtils.compressWithRatio(file.getInputStream(),
								null, filePath);
					}
				} else {
					if (picExtension.contains(extension)) {
						f = FileUtils.enhanceCompress(file.getInputStream(),
								extension, filePath, specifications);
					} else if (voiceExension.contains(extension)) {
						f = new File(filePath);
						File parent = new File(f.getParent());
						if (!parent.exists()) {
							parent.mkdir();
						}
						file.getFileItem().write(f);

					}
				}

				// 调用阿里云服务上传图片 然后删除图片
				String finalHttpPath = OSSObjectSample.uploadAliyunFile(
						httpUrl, filePath);
				if (f.exists()) {
					f.delete();
				}

				if (StringUtils.isBlank(finalHttpPath)) {
					Map<String, String> mapJson = new HashMap<String, String>();
					mapJson.put("errMsg", "系统繁忙!");
					return ObjectToJson.map2json(mapJson);
				}

				// 发送到其他服务器
				// if ("/admin".equals(contextPath)
				// || mutilPropertyPlaceholderConfigurer.isOnline()) {
				// String remoteFilePath = UPLOAD_BASE_PATH + type
				// + System.getProperty("file.separator");
				// if (specifications != null && specifications.getWIDTH() > 0
				// && specifications.getHEIGHT() > 0) {
				// remoteFilePath += fileName + "_"
				// + specifications.getWIDTH() + "_"
				// + specifications.getHEIGHT() + "." + extension;
				// } else {
				// remoteFilePath += fileName + "." + extension;
				// }
				// // 文件分发
				// if (f != null) {
				// paths = mutilPropertyPlaceholderConfigurer
				// .getProperty(FILE_REQUEST_URL);
				// if (StringUtils.isNotBlank(paths)) {
				// String ps[] = paths.split(",");
				// for (String string : ps) {
				// fis = new FileInputStream(f);
				// SendFileUtils.sendFile(fis, remoteFilePath,
				// string);
				// if (fis != null) {
				// fis.close();
				// }
				// }
				// }
				// }
				// }
				// file.getFileItem().write(f);
				jsonMap.put("errMsg", "success");
				jsonMap.put("imgurl", finalHttpPath);
			} catch (Exception e) {
				e.printStackTrace();
				jsonMap.put("errMsg", "系统繁忙，稍后再试!");
			}
		} else {
			jsonMap.put("errMsg", "请选择文件!");
		}

		return ObjectToJson.map2json(jsonMap);
	}

	public String createFilePath(HttpServletRequest request, String type,
			String extension, String fileName, Specification specifications) {
		String finalPath = null;
		String path = request
				.getSession()
				.getServletContext()
				.getRealPath(
						System.getProperty("file.separator") + UPLOAD_BASE_PATH
								+ type + System.getProperty("file.separator"));
		path += System.getProperty("file.separator");
		if (specifications != null && specifications.getWIDTH() > 0
				&& specifications.getHEIGHT() > 0) {
			finalPath = path + fileName + "_" + specifications.getWIDTH() + "_"
					+ specifications.getHEIGHT() + "." + extension;
		} else {
			finalPath = path + fileName + "." + extension;
		}
		return finalPath;

	}

	public String createHttpPicUrl(HttpServletRequest request,
			String contextPath, String type, String fileName, String extension,
			Specification specifications) {
		StringBuilder sb = new StringBuilder();
		if (mutilPropertyPlaceholderConfigurer.isDevEnv()) {
//			sb.append(contextPath);
		}
		sb.append(UPLOAD_BASE_PATH);
		sb.append(type);
		sb.append("/");
		sb.append(fileName);
		if (specifications != null && specifications.getWIDTH() > 0
				&& specifications.getHEIGHT() > 0) {
			sb.append("_");
			sb.append(specifications.getWIDTH());
			sb.append("_");
			sb.append(specifications.getHEIGHT());
		}
		sb.append(".");
		sb.append(extension);

		return sb.toString();
	}

	public String fileType(String fileType, String extension) {
		String json = null;
		Map<String, String> mapJson = new HashMap<String, String>();
		if (fileType.contains(fileType)) {
			// 如果不是希望的格式图片
			if (!picExtension.contains(extension)) {
				mapJson.put("errMsg", "图片格式有误!");
				json = ObjectToJson.map2json(mapJson);
			}
		} else if ("voice".equals(fileType)) {
			if (!voiceExension.contains(extension)) {
				mapJson.put("errMsg", "声音格式有误!");
				json = ObjectToJson.map2json(mapJson);
			}
		} else {
			mapJson.put("errMsg", "文件格式有误!");
			json = ObjectToJson.map2json(mapJson);
		}

		return json;
	}

	public String fileSize(long size, String extension) {
		String json = "";
		double mb = getFileMB(size);
		Map<String, String> mapJson = new HashMap<String, String>();
		if (mb == 0) {
			mapJson.put("errMsg", "请上传文件!");
			json = ObjectToJson.map2json(mapJson);
		}
		if (picExtension.contains(extension)) {
			if (mb > 5) {
				mapJson.put("errMsg", "文件过大!");
				json = ObjectToJson.map2json(mapJson);
			}
		} else if (voiceExension.contains(extension)) {
			if (mb > 5) {
				mapJson.put("errMsg", "文件过大!");
				json = ObjectToJson.map2json(mapJson);
			}
		}

		return json;
	}

	private double getFileMB(long size) {
		if (size == 0)
			return 0;
		long mb = 1024 * 1024;
		return BigDecimal.valueOf(size)
				.divide(new BigDecimal(mb), 2, BigDecimal.ROUND_HALF_DOWN)
				.doubleValue();
	}
}
