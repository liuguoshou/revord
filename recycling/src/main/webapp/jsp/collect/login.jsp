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
<title>回收人员登录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script type="text/javascript" >
$(function(){
	var errMsg = "${errMsg}";
	if(null != errMsg && errMsg != ''){
		$("#messageDiv").html(errMsg);
		$.blockUI.open("messageTip");
	}
	$("#submitButton").click(function(){
		var loginName = $.trim($("#loginName").val());
		var password = $.trim($("#password").val());
		if(null == loginName || loginName == ''){
			$("#messageDiv").html("请输入您的手机号！");
			$.blockUI.open("messageTip");
			return false;
		}
		var reg = /^0?(1[0-9][0-9])[0-9]{8}$/;
		if (!reg.test(loginName)) {
			$("#messageDiv").html("请输入正确的手机号码！");
			$.blockUI.open("messageTip");
			return false;
		}		
		if(null == password || password == ''){
			$("#messageDiv").html("请输入密码！");
			$.blockUI.open("messageTip");
			return false;
		}
		$("#loginForm").submit();
	});
})
</script>
</head>

<body>

<!--内容start-->
<article>
<form action="${pageContext.request.contextPath}/collect/login.do" method="post" id="loginForm">
	<input type="hidden" name="openId" id="openId" value="oNmQit9hlAZeI594JEfexEVFqz2E" />
	<div class="yhlogo"><img src="${pageContext.request.contextPath}/images/logo.png" /></div>
    <ul class="yhlogin">
    	<li><input name="loginName" id="loginName" type="text" class="form_put" placeholder="请输入您的手机号" maxlength="11"></li>
    	<li><input name="password" id="password" type="password" class="form_put" placeholder="请输入您的密码"></li>
        <li><input name="submitButton" type="button" class="form_btn" id="submitButton" value="登录"></li>
    </ul>
</form>
</article>
<!--内容end-->

<div class="popup" id="messageTip" style="display:none;">
	<div class="po1" id="messageDiv"></div>
    <div class="poul tex-center"><a href="javascript:;" onclick="$.blockUI.close();">知道了</a></div>
</div>

</body>
</html>