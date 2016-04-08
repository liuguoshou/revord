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
<title>用户首页</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
<script type="text/javascript" >
var context = '${pageContext.request.contextPath}';

function iwantrecycle(){
	$.ajax({
		type : 'POST',
		url : context+'/user/iWantRecycle.do',
		data:{timeStamp:new Date().getTime()},
		async:false,
		dataType : "json",
		success : function(data) {
			if(data.rspCode == 'SUCCESS'){
				var userInfoHtml = "姓名："+data.realName+"<br>";
				userInfoHtml +="地址："+data.userAddress+"<br>";
				userInfoHtml +="电话："+data.mobile;
				$("#confirmDiv").html(userInfoHtml);
				$.blockUI.open("confirmTip");
			}else{
				if(data.rspCode == "USER0005"){
					$.blockUI.open("loginTip");
				}else{
					$("#messageDiv").html(data.rspMsg);
					$.blockUI.open("messageTip");
				}
			}
		},
		error : function() {
			alert("回收请求发送失败!");
		}
	});
}

function confirmRecycle(){
	$.ajax({
		type : 'POST',
		url : context+'/user/confirmRecycle.do',
		data:{
			timeStamp:new Date().getTime()
		},
		async:false,
		dataType : "json",
		success : function(data) {
			if(data.rspCode == 'SUCCESS'){
				$("#messageDiv").html("提交成功<br>小哥马上就到请在家中等待");
				window.location.reload();
			}else{
				$("#messageDiv").html(data.rspMsg);
			}
			$.blockUI.open("messageTip");
		},
		error : function() {
			alert("回收请求发送失败!");
		}
	});
}
</script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<div class="hs_banner">
    	<span class="logo"><img src="${pageContext.request.contextPath}/images/xiehui.jpg" /></span>
    	<img src="${pageContext.request.contextPath}/images/img_1.jpg" />
    </div>
    <div class="note acclist">
    	<a href="${pageContext.request.contextPath}/user/gotoModifyUserInfo.do">
	    	<c:if test="${loginUser.userStatus == 'ACTIVE'}">${userAddress}</c:if>
	    	<c:if test="${loginUser.userStatus != 'ACTIVE'}">提示：您的服务信息不完善，点次完善信息</c:if>
    	</a>
    </div>
    <ul class="recy">
        <li>
        	<a href="${pageContext.request.contextPath}/jsp/user/integralHistory.jsp" class="are">
       	 		<em class="r1"></em>积分：${account.integral }分<br>
        		<span style="top: 115px;">累计收入：${account.totalIncome }元</span>
        </a>
        
        </li>
        <li class="rey">
        	<c:if test="${empty recTrxorder}">
	        	<a href="javascript:;" onclick="return iwantrecycle();" class="are"><em class="r2"></em>我要回收</a>
        	</c:if>
        	<c:if test="${not empty recTrxorder}">
        		<a href="${pageContext.request.contextPath}/jsp/user/trxorderList.jsp" class="are"><em class="r2"></em>
        			<c:if test="${recTrxorder.trxStatus == 'INIT'}"><font style="color:blue;">等待接单</font></c:if>
                    <c:if test="${recTrxorder.trxStatus == 'PROCESSING'}">
	        			<c:if test="${not empty beggarInfo}">${beggarInfo.realName }:&nbsp;</c:if>
                    	<font style="color:#fe170D;">上门中</font>
                    </c:if>
                    <c:if test="${recTrxorder.trxStatus == 'CONFIRM'}"><font style="color:#308014;">待确认</font></c:if>
        		</a>
        		<c:if test="${not empty beggarInfo and recTrxorder.trxStatus == 'PROCESSING'}">
        			<span class="phone"><a href="tel:${beggarInfo.mobile}">电话:${beggarInfo.mobile}</a></span>
        		</c:if>
        	</c:if>
        </li>
    </ul>
</article>    
</div>
<!--内容end-->

<!--footer start-->
<%@ include file="/jsp/common/footer.jsp"%>
<!--footer end-->

<div class="popup" id="messageTip" style="display:none;">
	<div class="po1" id="messageDiv"></div>
    <div class="poul tex-center"><a href="javascript:;" onclick="$.blockUI.close();">知道了</a></div>
</div>

<div class="popup" id="confirmTip" style="display:none;">
	<dl class="podl">
    	<dt id="confirmDiv"><a href="javascript:;" onclick="return confirmRecycle();">确定</a></dt>
        <dd><a href="javascript:;" onclick="$.blockUI.close();">取消</a></dd>
    </dl>
</div>

<div class="popup" style="display:none;" id="loginTip">
	<div class="po1" id="loginDiv">请先完善信息，再回收！</div>
    <dl class="podl">
    	<dt><a href="${pageContext.request.contextPath}/user/gotoModifyUserInfo.do">完善信息</a></dt>
        <dd><a href="javascript:;" onclick="$.blockUI.close();">关闭</a></dd>
    </dl>
</div>

</body>
</html>