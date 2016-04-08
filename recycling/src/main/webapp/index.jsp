<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	RequestDispatcher dispatcher = request.getRequestDispatcher("/index.do");
	dispatcher.forward(request, response);
 %>