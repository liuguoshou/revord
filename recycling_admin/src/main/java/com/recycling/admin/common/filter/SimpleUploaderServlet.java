package com.recycling.admin.common.filter;

import com.recycling.common.util.CommonUtils;
import com.recycling.common.util.OSSObjectSample;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleUploaderServlet extends HttpServlet {
	private static String baseDir;
	private static boolean debug = false;

	private static boolean enabled = false;
	private static Hashtable allowedExtensions;
	private static Hashtable deniedExtensions;
	private static Hashtable allowedSize;

	private static final String FILE_REQUEST_URL = "IMAGEPATH";
	private static String paths = null;

	public void init() throws ServletException {
		debug = new Boolean(getInitParameter("debug")).booleanValue();

		if (debug) {
			System.out
					.println("\r\n---- SimpleUploaderServlet initialization started ----");
		}
		baseDir = getInitParameter("baseDir");
		enabled = new Boolean(getInitParameter("enabled")).booleanValue();
		if (baseDir == null)
			baseDir = "/UserFiles/";
		String realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists()) {
			baseFile.mkdir();
		}

		allowedExtensions = new Hashtable(3);
		deniedExtensions = new Hashtable(3);
		allowedSize = new Hashtable(3);

		allowedExtensions.put("File",
				stringToArrayList(getInitParameter("AllowedExtensionsFile")));
		deniedExtensions.put("File",
				stringToArrayList(getInitParameter("DeniedExtensionsFile")));

		allowedExtensions.put("Image",
				stringToArrayList(getInitParameter("AllowedExtensionsImage")));
		deniedExtensions.put("Image",
				stringToArrayList(getInitParameter("DeniedExtensionsImage")));

		allowedExtensions.put("Flash",
				stringToArrayList(getInitParameter("AllowedExtensionsFlash")));
		deniedExtensions.put("Flash",
				stringToArrayList(getInitParameter("DeniedExtensionsFlash")));

		if ((getInitParameter("AllowedSizeFile") == null)
				|| ("".equals(getInitParameter("AllowedSizeFile"))))
			allowedSize.put("File", new Long(0L));
		else {
			allowedSize.put("File", new Long(
					getInitParameter("AllowedSizeFile")));
		}
		if ((getInitParameter("AllowedSizeImage") == null)
				|| ("".equals(getInitParameter("AllowedSizeImage"))))
			allowedSize.put("Image", new Long(0L));
		else {
			allowedSize.put("Image", new Long(
					getInitParameter("AllowedSizeImage")));
		}
		if ((getInitParameter("AllowedSizeFlash") == null)
				|| ("".equals(getInitParameter("AllowedSizeFlash"))))
			allowedSize.put("Flash", new Long(0L));
		else {
			allowedSize.put("Flash", new Long(
					getInitParameter("AllowedSizeFlash")));
		}

		if (debug)
			System.out
					.println("---- SimpleUploaderServlet initialization completed ----\r\n");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (debug) {
			System.out.println("--- BEGIN DOPOST ---");
		}
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String typeStr = request.getParameter("Type");

		String currentPath = baseDir + typeStr;
		String currentDirPath = getServletContext().getRealPath(currentPath);
		currentPath = request.getContextPath() + currentPath;

		// 增加按时间创建文件夹
		currentPath += "/"
				+ new SimpleDateFormat("yyyyMMdd").format(new Date());

		currentDirPath += File.separator
				+ new SimpleDateFormat("yyyyMMdd").format(new Date())
				+ File.separator;
		File folder = new File(currentDirPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		if (debug) {
			System.out.println(currentDirPath);
		}
		String retVal = "0";
		String newName = "";
		String fileUrl = "";
		String errorMessage = "";

		String remoteHttpPath = "";

		label720: if (enabled) {
			DiskFileUpload upload = new DiskFileUpload();
			try {
				List items = upload.parseRequest(request);

				Map fields = new HashMap();

				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField())
						fields.put(item.getFieldName(), item.getString());
					else
						fields.put(item.getFieldName(), item);
				}
				FileItem uplFile = (FileItem) fields.get("NewFile");
				
				String fileNameLong = uplFile.getName();
				fileNameLong = fileNameLong.replace('\\', '/');
				String[] pathParts = fileNameLong.split("/");

				String fileName = pathParts[(pathParts.length - 1)];
				String nameWithoutExt = getNameWithoutExtension(fileName);
				String ext = getExtension(fileName);

				long fileSize = uplFile.getSize();
				if (sizeIsAllowed(typeStr, fileSize)) {
					long currentTime = System.currentTimeMillis();
					String random = String.valueOf(Math.abs(new Random(
							currentTime).nextInt()) % 10000);
					nameWithoutExt = String.valueOf(currentTime + "_" + random);
					String namebydate = nameWithoutExt + "." + ext;

					File pathToSave = new File(currentDirPath, namebydate);
					fileUrl = currentPath + "/" + namebydate;
					Map<String, Object> temp = null;
					if (extIsAllowed(typeStr, ext)) {
						int counter = 1;
						while (pathToSave.exists()) {
							newName = nameWithoutExt + "(" + counter + ")"
									+ "." + ext;
							fileUrl = currentPath + "/" + newName;
							retVal = "201";

							pathToSave = new File(currentDirPath, newName);
							
							counter++;
						}
						FileCopyUtils.copy(uplFile.get(), pathToSave);
						// try {
						// Map<String, Object> map = ImgUtil.sendImg(
						// Constant.FCK_IMAGES_URL, pathToSave);
						// fileUrl = map.get(ImgUtil.FILE_URL).toString();
						// } catch (Exception e) {
						// e.printStackTrace();
						// }

						// 除了本地环境都要发送到前台服务器

//						MutilPropertyPlaceholderConfigurer mpc = (MutilPropertyPlaceholderConfigurer) BeanContext
//								.getBean("propertyConfigurer");
//						DomainUtils domainUtils = (DomainUtils) BeanContext
//								.getBean("domainUtils");
						String remoteFilePath = "upload/activityinfo"
								+ System.getProperty("file.separator")
								+ CommonUtils.formatPicName()+"."+FilenameUtils.getExtension(uplFile.getName());
						
						
//						if (!mpc.isDevEnv()) {
//							StringBuilder sb = new StringBuilder(
//									domainUtils.getDomain(request));
//							sb.append(remoteFilePath);
//							remoteHttpPath = sb.toString();
//							FileInputStream fis = null;
//							paths = mpc.getProperty(FILE_REQUEST_URL);
//							if (StringUtils.isNotBlank(paths)) {
//								String ps[] = paths.split(",");
//								for (String string : ps) {
//									fis = new FileInputStream(pathToSave);
//									SendFileUtils.sendFile(fis, remoteFilePath,
//											string);
//									if (fis != null) {
//										fis.close();
//									}
//								}
//							}
//						}
						uplFile.write(pathToSave);
						remoteHttpPath=OSSObjectSample.uploadAliyunFile(remoteFilePath,pathToSave.getAbsolutePath());
						pathToSave.delete();
						break label720;
					}
					retVal = "202";
					errorMessage = "";
					if (!debug)
						break label720;
					System.out.println("Invalid file type: " + ext);
					break label720;
				}

				retVal = "204";
				errorMessage = "";
				if (!debug)
					break label720;
				System.out.println("Invalid file size: " + fileSize);
			} catch (Exception ex) {
				if (debug)
					ex.printStackTrace();
				retVal = "203";
			}
		} else {
			retVal = "1";
			errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
		}

		label720: out.println("<script type=\"text/javascript\">");
		if (StringUtils.isNotBlank(remoteHttpPath)) {
			out.println("window.parent.OnUploadCompleted(" + retVal + ",'"
					+ remoteHttpPath + "','" + newName + "','" + errorMessage
					+ "');");
		} else {
			out.println("window.parent.OnUploadCompleted(" + retVal + ",'"
					+ fileUrl + "','" + newName + "','" + errorMessage + "');");
		}
		out.println("</script>");
		out.flush();
		out.close();

		if (debug)
			System.out.println("--- END DOPOST ---");
	}

	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	private ArrayList stringToArrayList(String str) {
		if (debug)
			System.out.println(str);
		String[] strArr = str.split("\\|");

		ArrayList tmp = new ArrayList();
		if (str.length() > 0) {
			for (int i = 0; i < strArr.length; i++) {
				if (debug)
					System.out.println(i + " - " + strArr[i]);
				tmp.add(strArr[i].toLowerCase());
			}
		}
		return tmp;
	}

	private boolean extIsAllowed(String fileType, String ext) {
		ext = ext.toLowerCase();

		ArrayList allowList = (ArrayList) allowedExtensions.get(fileType);
		ArrayList denyList = (ArrayList) deniedExtensions.get(fileType);

		if (allowList.size() == 0) {
			return !denyList.contains(ext);
		}

		if (denyList.size() == 0) {
			return allowList.contains(ext);
		}

		return false;
	}

	private boolean sizeIsAllowed(String fileType, long size) {
		Long allowSize = (Long) allowedSize.get(fileType);
		boolean ret = true;

		if ((size <= 0L) || (allowSize.longValue() <= 0L))
			ret = true;
		else if (allowSize.longValue() >= size)
			ret = true;
		else {
			ret = false;
		}

		return ret;
	}
}
