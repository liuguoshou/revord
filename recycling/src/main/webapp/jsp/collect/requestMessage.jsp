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
<title>回收系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script type="text/javascript" >
var context = '${pageContext.request.contextPath}';
$(function(){
	ajaxSubmitForm();
	$("#viewMore").click(function(){
		var cur_page = parseInt($("#currentPage").val());
		$("#currentPage").val(cur_page+1);
		ajaxSubmitForm();
	});
})

function ajaxSubmitForm(){
	$("#loading").show();
	$.ajax({
		type : 'POST',
		url : context+'/collect/requestMessage.do',
		data:{
			currentPage:$("#currentPage").val(),
			timeStam:new Date().getTime()
		},
		async:false,
		dataType : "json",
		success : function(data) {
			$("#loading").hide();
			if(data.rspCode == 'SUCCESS'){
				var currentPage = $("#currentPage").val();
				if(data.currentPage && data.totalPage > currentPage){
					$("#viewMore").show();
				}else{
					$("#viewMore").hide();
				}
				var orderListHTML = "";
				if(data.messageList && data.totalCount > 0){
					for(var i=0;i<data.messageList.length;i++){
						var message = data.messageList[i];
						orderListHTML +="<dl><dt>回收垃圾<br>"+message.createDateStr;
				    	orderListHTML +="</dt><dd>";
				    	if(null == message.trxStatus || message.trxStatus == 'INIT'){
				    		orderListHTML += "呼叫中";
				    	}else if(message.trxStatus == 'PROCESSING'){
				    		orderListHTML += "回收中";
				    	}else if(message.trxStatus == 'CONFIRM'){
				    		orderListHTML += "确认中";
				    	}else if(message.trxStatus == 'SUCCESS'){
				    		orderListHTML += "回收成功";
				    	}else if(message.trxStatus == 'CANCEL'){
				    		orderListHTML += "已取消";
				    	}
				    	if(null == message.trxStatus || message.trxStatus == 'INIT'){
				    		orderListHTML +="<span class='zorange'>&nbsp;&nbsp;";
				    		orderListHTML +="	<a href='${pageContext.request.contextPath}/collect/comfirmReceptOrder.do?messageId="+message.messageId+"'>确认接单</a>";
				    		orderListHTML +="</span>";
				    	}else if(message.trxStatus == 'PROCESSING'){
				    		orderListHTML +="<span class='zorange'>&nbsp;&nbsp;";
				    		orderListHTML +="	<a href='${pageContext.request.contextPath}/collect/editTrxorder.do?trxorderId="+message.trxorderId+"'>编辑</a>";
				    		orderListHTML +="</span>";
				    	}
				    	orderListHTML +="<span class='zorange'>&nbsp;&nbsp;";  		
				    	orderListHTML +="<a href='javascript:;' onclick=\"return messageDetail('"+message.messageId+"');\">详情</a>"; 
				    	orderListHTML +="	</span>";
				    	if(null != message.trxAmountStr && "" != message.trxAmountStr){
				    		orderListHTML +="<br>共计："+message.trxAmountStr+"元";
				    	}
				    	orderListHTML += "</dd></dl>"; 
					}
				}else{
					orderListHTML +="<div style='padding-top:10px;text-align:center;'>暂无数据!</div>";
				}
				$(".hsdl").append(orderListHTML);
			}else{
				$("#messageDiv").html(data.rspMsg);
				$.blockUI.open("messageTip");
			}
		},
		error: function(XmlHttpRequest, textStatus, errorThrown){
			$("#messageDiv").html("信息查询失败!");
			$.blockUI.open("messageTip");
	    }
	});
}
function messageDetail(messageId){
	$.ajax({
		type : 'POST',
		url : context+'/collect/messageDetail.do',
		data:{
			messageId:messageId,
			timeStam:new Date().getTime()
		},
		async:false,
		dataType : "json",
		success : function(data) {
			if(data.rspCode == 'SUCCESS'){
				var userInfoHtml = "姓名："+data.realName+"<br>";
				userInfoHtml +="地址："+data.userAddress+"<br>";
				userInfoHtml +="电话：<a href='tel:"+data.mobile+"'>"+data.mobile+"</a>";
				$("#confirmDiv").html(userInfoHtml);
				$.blockUI.open("confirmTip");
			}else{
				alert(data.rspMsg);
			}
		},
		error : function() {
			alert("消息查询失败!");
		}
	});
}

</script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<h2 class="jfh2"><a href="${pageContext.request.contextPath}/collect/initRequestMessage.do" class="asx"></a> <em class="j6"></em>回收列表</h2>
    <input type="hidden" name="currentPage" value="1" id="currentPage" />
    <div class="hsdl">
    	
    </div>
    
    <div id="loading" align="center" style="display: none;">
		<img src="${pageContext.request.contextPath}/images/loading.gif" width="30" height="30"/>
	</div>
	<div class="tex-center" style="margin-top:10px;" id="viewMoreDiv" >
	<input name="viewMore" id="viewMore"  type="button" class="page_btn" value="查看更多" />
	</div>
	
</article>
</div>
<!--内容end-->

<div class="popup" id="confirmTip" style="display:none;">
	<div class="po1" id="confirmDiv"></div>
    <div class="poul tex-center"><a href="javascript:;" onclick="$.blockUI.close();">知道了</a></div>
</div>

<div class="popup" id="messageTip" style="display:none;">
	<div class="po1" id="messageDiv"></div>
    <div class="poul tex-center"><a href="javascript:;" onclick="$.blockUI.close();">知道了</a></div>
</div>

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