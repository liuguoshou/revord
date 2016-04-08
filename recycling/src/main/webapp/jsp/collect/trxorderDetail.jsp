<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>回收详情</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<h2 class="jfh2"><em class="j2"></em>订单明细</h2>
    <ul class="jful kflist">
    	<c:forEach var="detail" items="${detailList}">
	    	<li>${detail.categoryName}：&nbsp;&nbsp;${detail.count }${detail.unit}</li>
    	</c:forEach>
    	<li>共计：${trxorder.trxAmount }元&nbsp;&nbsp;<fmt:formatDate value="${trxorder.createDate}" pattern="yyyy-MM-dd"/></li>
    </ul>
    <form action="${pageContext.request.contextPath}/collect/initTrxorderList.do" method="post">
    	<div class="tex-center" style="margin-top:10px;"><input name="submitButton" id="submitButton"  type="submit" class="form_btn" value="返回"></div>
    </form>
</article>
</div>
<!--内容end-->

<!--footer start-->
<footer>
    <ul class="ff2">
        <li><a href="${pageContext.request.contextPath}/collect/initRequestMessage.do"><em class="f1"></em>回收列表</a></li>
        <li><a href="${pageContext.request.contextPath}/collect/initTrxorderList.do"><em class="f2"></em>订单统计</a></li>
    </ul>
</footer>
<!--footer end-->
<script>
	total = document.documentElement.clientHeight;
	colHeight = total-61-document.getElementById("co1").offsetTop;
	document.getElementById("co1").style.minHeight=colHeight+"px";
</script>
</body>
</html>