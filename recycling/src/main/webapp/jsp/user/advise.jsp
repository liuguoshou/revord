<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title>投诉建议</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script  type="text/javascript">
	$(function(){
		$("#submitButton").click(function(){
			var adviseContent = $.trim($("#adviseContent").val());
			if(null == adviseContent || adviseContent == ''){
				$("#messageDiv").html("请填写您的建议内容！");
				$.blockUI.open("messageTip");
				return false;
			}
			$("#adviseForm").submit();
		});
	})
</script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<div class="warp">
        <h2 class="jfh2"><em class="j3"></em>投诉建议</h2>
        <form action="${pageContext.request.contextPath}/user/saveUserAdvise.do" method="post" id="adviseForm">
            <div class="tsdiv"><textarea name="adviseContent" id="adviseContent" cols="" rows="8" class="form_tex" maxlength="800"></textarea></div>
            <div class="tex-center"><input id="submitButton" type="button" class="form_btn" value="提交"></div>
        </form>   
    </div> 
</article>
</div>
<!--内容end-->

<div class="popup" id="messageTip" style="display:none;">
	<div class="po1" id="messageDiv"></div>
    <div class="poul tex-center"><a href="javascript:;" onclick="$.blockUI.close();">知道了</a></div>
</div>

<!--footer start-->
<%@ include file="/jsp/common/footer.jsp"%>
<!--footer end-->


</body>
</html>