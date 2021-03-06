﻿<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>价格详情</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<c:if test="${not empty categoryMap}">
		<c:set var="categoryList" value="${categoryMap[0]}"></c:set>
		<c:forEach var="firstCategory" items="${categoryList}">
			 <div class="catelog">
		    	<h2><a class="shou"></a>${firstCategory.categoryName}</h2>
		    	<c:if test="${not empty categoryMap[firstCategory.categoryId]}">
		    		<c:forEach var="secondCategory" items="${categoryMap[firstCategory.categoryId]}">
		    			<div class="catexx">
			    		  <dl>
				        	<dt>${secondCategory.categoryName }</dt>
				        	<dd>
					        	<c:if test="${not empty categoryMap[secondCategory.categoryId]}">
					        		<c:forEach var="thirdCategory" items="${categoryMap[secondCategory.categoryId]}" varStatus="varStat">
					        				${thirdCategory.categoryName} &nbsp; ${thirdCategory.price }元/${thirdCategory.unit }
					        				<c:if test="${fn:length(categoryMap[firstCategory.categoryId]) != varStat.index-1}"><br></c:if>
					        		</c:forEach>
					        	</c:if>
					        	<c:if test="${empty categoryMap[secondCategory.categoryId]}">
					        		${secondCategory.price }元/${secondCategory.unit }
					        	</c:if>
				        	</dd>
				           </dl>
				        </div>
		    		</c:forEach>
		    	</c:if>
		       </div>
		</c:forEach>
	</c:if>

</article>
</div>
<!--内容end-->

<!--footer start-->
<%@ include file="/jsp/common/footer.jsp"%>
<!--footer end-->


</body>
</html>