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
<title>我的账户</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<div class="account"><em class="my1"></em>手机号：${loginUser.mobile }<br>
		状&nbsp;&nbsp;&nbsp;&nbsp;态：
			<c:if test="${loginUser.userStatus !='ACTIVE' }">未开启服务，请完善信息</c:if>
			<c:if test="${loginUser.userStatus == 'ACTIVE' }">正常服务</c:if>
	</div>
    <ul class="acclist">
    	<li><a href="${pageContext.request.contextPath}/jsp/user/integralHistory.jsp"> <em class="my2"></em> 账户积分：${account.integral}分</a></li>
    	<li><a href="${pageContext.request.contextPath}/user/gotoModifyUserInfo.do"><em class="my3"></em> 服务地址：请选择</a> </li>
    	<li><a href="${pageContext.request.contextPath}/jsp/user/trxorderList.jsp"><em class="my4"></em> 回收记录</a> </li>
    	<li><a href="${pageContext.request.contextPath}/jsp/user/advise.jsp"> <em class="my5"></em> 投诉建议</a></li>
    </ul>
</article>
</div>
<!--内容end-->

<!--footer start-->
<%@ include file="/jsp/common/footer.jsp"%>
<!--footer end-->


</body>
</html>