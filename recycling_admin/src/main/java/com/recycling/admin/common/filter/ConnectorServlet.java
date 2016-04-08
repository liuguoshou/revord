package com.recycling.admin.common.filter;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author lvjx
 * 
 */
public class ConnectorServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6875349911168984332L;
	private static String baseDir;
	private static boolean debug = false;

	@Override
	public void init() throws ServletException {
		baseDir = getInitParameter("baseDir");
		debug = new Boolean(getInitParameter("debug")).booleanValue();
		if (baseDir == null)
			baseDir = "/UserFiles/";
		String realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists())
			baseFile.mkdir();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (debug)
			System.out.println("--- BEGIN DOGET ---");

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String commandStr = request.getParameter("Command");
		String typeStr = request.getParameter("Type");
		String currentFolderStr = request.getParameter("CurrentFolder");

		String currentPath = baseDir + typeStr + currentFolderStr;
		String currentDirPath = getServletContext().getRealPath(currentPath);

		/*
		 * ResourceBundle rb = ResourceBundle.getBundle("project"); String
		 * currentDirPath = rb.getString("FCK_IMAGE_PATH");
		 */
		File currentDir = new File(currentDirPath);
		if (!currentDir.exists()) {
			currentDir.mkdir();
		}

		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}

		Node root = CreateCommonXml(document, commandStr, typeStr, currentFolderStr, request.getContextPath() + currentPath);

		// Node
		// root=CreateCommonXml(document,commandStr,typeStr,currentFolderStr,"你需要存放文件的tomcat应用的相对路径"+currentPath);

		if (debug)
			System.out.println("Command = " + commandStr);

		if (commandStr.equals("GetFolders")) {
			getFolders(currentDir, root, document);
		} else if (commandStr.equals("GetFoldersAndFiles")) {
			getFolders(currentDir, root, document);
			getFiles(currentDir, root, document);
		} else if (commandStr.equals("CreateFolder")) {
			String newFolderStr = request.getParameter("NewFolderName");
			File newFolder = new File(currentDir, newFolderStr);
			String retValue = "110";

			if (newFolder.exists())
				retValue = "101";
			else {
				try {
					boolean dirCreated = newFolder.mkdir();
					if (dirCreated)
						retValue = "0";
					else
						retValue = "102";
				}
				catch (SecurityException sex) {
					retValue = "103";
				}
			}

			setCreateFolderResponse(retValue, root, document);
		}

		document.getDocumentElement().normalize();
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);

			if (debug) {
				StreamResult dbgResult = new StreamResult(System.out);
				transformer.transform(source, dbgResult);
				System.out.println("");
				System.out.println("--- END DOGET ---");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		out.flush();
		out.close();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (debug)
			System.out.println("--- BEGIN DOPOST ---");

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String commandStr = request.getParameter("Command");
		String typeStr = request.getParameter("Type");
		String currentFolderStr = request.getParameter("CurrentFolder");

		String currentPath = baseDir + typeStr + currentFolderStr;
		String currentDirPath = getServletContext().getRealPath(currentPath);

		//增加按时间创建文件夹
		currentPath += new SimpleDateFormat("yyyyMMdd").format(new Date());
		
		currentDirPath += File.separator + new SimpleDateFormat("yyyyMMdd").format(new Date()) + File.separator;
		File folder = new File(currentDirPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		/*
		 * ResourceBundle rb = ResourceBundle.getBundle("project"); String
		 * currentDirPath = rb.getString("FCK_IMAGE_PATH");
		 */
		if (debug)
			System.out.println(currentDirPath);

		String retVal = "0";
		String newName = "";

		if (!commandStr.equals("FileUpload")) {
			retVal = "203";
		} else {
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

				if (uplFile.getSize() > 204800) {
					retVal = "204";
				} else {
					String nameWithoutExt = getNameWithoutExtension(fileName);
					String ext = getExtension(fileName);
					File pathToSave = new File(currentDirPath, fileName);
					int counter = 1;
					while (pathToSave.exists()) {
						newName = nameWithoutExt + "(" + counter + ")" + "." + ext;
						retVal = "201";
						pathToSave = new File(currentDirPath, newName);
						counter++;
					}
					uplFile.write(pathToSave);
				}

			}
			catch (Exception ex) {
				ex.printStackTrace();
				retVal = "203";
			}

		}

		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.frames['frmUpload'].OnUploadCompleted(" + retVal + ",'" + newName + "');");
		out.println("</script>");
		out.flush();
		out.close();

		if (debug)
			System.out.println("--- END DOPOST ---");
	}

	private void setCreateFolderResponse(String retValue, Node root, Document doc) {
		Element myEl = doc.createElement("Error");
		myEl.setAttribute("number", retValue);
		root.appendChild(myEl);
	}

	private void getFolders(File dir, Node root, Document doc) {
		Element folders = doc.createElement("Folders");
		root.appendChild(folders);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; i++)
			if (fileList[i].isDirectory()) {
				Element myEl = doc.createElement("Folder");
				myEl.setAttribute("name", fileList[i].getName());
				folders.appendChild(myEl);
			}
	}

	private void getFiles(File dir, Node root, Document doc) {
		Element files = doc.createElement("Files");
		root.appendChild(files);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; i++)
			if (fileList[i].isFile()) {
				Element myEl = doc.createElement("File");
				myEl.setAttribute("name", fileList[i].getName());
				myEl.setAttribute("size", String.valueOf(fileList[i].length() / 1024L));
				files.appendChild(myEl);
			}
	}

	private Node CreateCommonXml(Document doc, String commandStr, String typeStr, String currentPath, String currentUrl) {
		Element root = doc.createElement("Connector");
		doc.appendChild(root);
		root.setAttribute("command", commandStr);
		root.setAttribute("resourceType", typeStr);

		Element myEl = doc.createElement("CurrentFolder");
		myEl.setAttribute("path", currentPath);
		myEl.setAttribute("url", currentUrl);
		root.appendChild(myEl);

		return root;
	}

	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
