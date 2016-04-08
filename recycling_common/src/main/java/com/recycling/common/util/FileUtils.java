package com.recycling.common.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;

public class FileUtils {

	public static void download(HttpServletRequest request, String path,
			HttpServletResponse response) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String fileName = file.getName();
			// 取得文件的后缀名。
			// String ext = filename.substring(filename.lastIndexOf(".") +
			// 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setContentType("application/x-download");
			String agent = request.getHeader("USER-AGENT");// 用户代理
			// 防止中文文件名乱码
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				String codedfilename = org.apache.commons.lang.StringUtils
						.replace(URLEncoder.encode(fileName, "UTF-8"), "+",
								"%20");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + codedfilename);
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				String codedfilename = javax.mail.internet.MimeUtility
						.encodeText(fileName, "UTF-8", "B");
				response.setHeader("Content-Disposition",
						"attachment;filename=" + codedfilename);
			} else {
				response.setHeader("Content-Disposition",
						"attachment;filename=" + fileName);
			}
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static String getFileContent(String path) {
		File myFile = new File(path);
		if (!myFile.exists()) {
			System.err.println("Can't Find " + path);
		}
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					myFile), "UTF-8");
			in = new BufferedReader(read);
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}

		} catch (IOException e) {
			e.getStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static File writeFileContent(String content, String path) {
		File file = new File(path);
		FileOutputStream fileout = null;
		try {
			file.mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
			fileout = new FileOutputStream(file);
			fileout.write(content.getBytes("utf-8"));
			fileout.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileout != null) {
				try {
					fileout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 文件内容写入 目录不存在，先创建目录；文件不存在，先创建文件
	 * 
	 * @param content
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static File writeFileContent(String content, String path,
			String fileName) {
		File filePath = new File(path);
		File file = new File(path
				+ System.getProperties().getProperty("file.separator")
				+ fileName);
		FileOutputStream fileout = null;
		try {
			// 创建目录
			filePath.mkdirs();
			// 写入文件
			if (!file.exists()) {
				file.createNewFile();
			}
			fileout = new FileOutputStream(file);
			fileout.write(content.getBytes("utf-8"));
			fileout.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileout != null) {
				try {
					fileout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 获取文件路径
	 * 
	 * @param dir
	 * @param fileName
	 *            add by wenhua.cheng
	 * @return
	 */
	public static String getFilePath(String dir, String fileName) {

		String fileSeparator = System.getProperty("file.separator");
		if (!dir.endsWith(fileSeparator)) {
			dir += fileSeparator;
		}
		return dir + fileName;

	}

	public static File enhanceCompress(InputStream is, String extension,
			String path, Specification specification) {
		ImageInputStream iis = null;
		FileInputStream fis = null;
		try {
			Iterator<ImageReader> it = ImageIO
					.getImageReadersByFormatName(extension);
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();

			int height = reader.getHeight(0);
			int width = reader.getWidth(0);

			int x = 0;
			int y = 0;
			Rectangle rect = null;
			if (height > width) {
				x = 0;
				y = height / 2 - width / 2;
				rect = new Rectangle(x, y, width, width);
			} else if (width > height) {
				x = width / 2 - height / 2;
				y = 0;
				rect = new Rectangle(x, y, height, height);
			} else {
				x = 0;
				y = 0;
				rect = new Rectangle(x, y, width, height);
			}

			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			File f = new File(path);
			File parent = new File(f.getParent());
			if (!parent.exists()) {
				parent.mkdir();
			}
			if (!f.exists()) {
				f.createNewFile();
			}
			ImageIO.write(bi, extension, f);

			fis = new FileInputStream(f);

			Specification sf = null;
			if (specification == null) {
				sf = new Specification(width, height);
			}

			return compress(fis, sf, path, null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (iis != null)
					iis.close();
				if (fis != null)
					fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 图片压缩
	 * 
	 * @param originalFile
	 *            :原图片
	 * @param extension
	 *            :原图片扩展名
	 * @param specifications
	 *            :规格实体
	 * @param quality
	 *            :质量 默认0.9f
	 * @param targetImage
	 *            :目标图片地址
	 * @return
	 */
	public static File compress(InputStream inputStream,
			Specification specifications, String targetImage,
			Integer... quality) {

		FileOutputStream out = null;
		File f = null;
		try {

			Image image = ImageIO.read(inputStream);
			if (specifications == null) {
				specifications = new Specification(image.getWidth(null),
						image.getHeight(null));
			}
			int imageWidth = specifications.getWIDTH();
			int imageHeight = specifications.getHEIGHT();

			// float scale = getRatio(imageWidth, imageHeight,
			// specifications.getWIDTH(), specifications.getHEIGHT());
			// imageWidth = (int) (scale * imageWidth);
			// imageHeight = (int) (scale * imageHeight);

			image = image.getScaledInstance(imageWidth, imageHeight,
					Image.SCALE_AREA_AVERAGING);
			// Make a BufferedImage from the Image.
			BufferedImage mBufferedImage = new BufferedImage(imageWidth,
					imageHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = mBufferedImage.createGraphics();

			g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white,
					null);

			float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
					-0.125f, -0.125f, -0.125f, -0.125f };
			Kernel kernel = new Kernel(3, 3, kernelData2);
			ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
			mBufferedImage = cOp.filter(mBufferedImage, null);

			/** 压缩之后临时存放位置 */
			f = new File(targetImage);
			File fparent = new File(f.getParent());
			if (!fparent.exists()) {
				fparent.mkdir();
			}
			if (!f.exists()) {
				f.createNewFile();
			}
			out = new FileOutputStream(f);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder
					.getDefaultJPEGEncodeParam(mBufferedImage);
			param.setQuality(quality == null ? 0.9f : quality[0], true);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(mBufferedImage);

			return f;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 图片压缩
	 * 
	 * @param originalFile
	 *            :原图片
	 * @param extension
	 *            :原图片扩展名
	 * @param specifications
	 *            :规格实体
	 * @param quality
	 *            :质量 默认0.9f
	 * @param targetImage
	 *            :目标图片地址
	 * @return
	 */
	public static File compressWithRatio(InputStream inputStream,
			Specification specifications, String targetImage,
			Integer... quality) {

		FileOutputStream out = null;
		File f = null;
		try {
			Image image = ImageIO.read(inputStream);
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);

			if(specifications==null){
				specifications=new Specification(imageWidth, imageHeight);
			}
			
			imageWidth = specifications.getWIDTH();
			imageHeight = specifications.getHEIGHT();

			float scale = getRatio(imageWidth, imageHeight, specifications.getWIDTH(),
					specifications.getHEIGHT());
			imageWidth = (int) (scale * imageWidth);
			imageHeight = (int) (scale * imageHeight);

			image = image.getScaledInstance(imageWidth, imageHeight,
					Image.SCALE_AREA_AVERAGING);
			// Make a BufferedImage from the Image.
			BufferedImage mBufferedImage = new BufferedImage(imageWidth,
					imageHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = mBufferedImage.createGraphics();

			g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white,
					null);

			float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
					-0.125f, -0.125f, -0.125f, -0.125f };
			Kernel kernel = new Kernel(3, 3, kernelData2);
			ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
			mBufferedImage = cOp.filter(mBufferedImage, null);

			/** 压缩之后临时存放位置 */
			f = new File(targetImage);
			File fparent = new File(f.getParent());
			if (!fparent.exists()) {
				fparent.mkdir();
			}
			if (!f.exists()) {
				f.createNewFile();
			}
			out = new FileOutputStream(f);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder
					.getDefaultJPEGEncodeParam(mBufferedImage);
//			param.setQuality(quality == null ? 0.9f : quality[0], true);
			param.setQuality(0.8f, true);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(mBufferedImage);

			return f;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private static float getRatio(int width, int height, int maxWidth,
			int maxHeight) {
		float Ratio = 1.0f;
		float widthRatio;
		float heightRatio;
		widthRatio = (float) maxWidth / width;
		heightRatio = (float) maxHeight / height;
		if (widthRatio < 1.0 || heightRatio < 1.0) {
			Ratio = widthRatio <= heightRatio ? widthRatio : heightRatio;
		}
		return Ratio;
	}

	/**
	 * 压缩规格
	 */
	public static class Specification {
		private Specification() {

		}

		public Specification(Integer WIDTH, Integer HEIGHT) {
			this.WIDTH = WIDTH;
			this.HEIGHT = HEIGHT;
		}

		private Integer WIDTH = 0;
		private Integer HEIGHT = 0;

		public Integer getWIDTH() {
			return WIDTH;
		}

		public Integer getHEIGHT() {
			return HEIGHT;
		}
	}
}
