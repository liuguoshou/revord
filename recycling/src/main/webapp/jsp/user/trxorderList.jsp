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
<title>回收记录</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
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
		url : context+'/user/showTrxorderList.do',
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
				if(data.trxorderList && data.totalCount > 0){
					for(var i=0;i<data.trxorderList.length;i++){
						var trxorder = data.trxorderList[i];
						orderListHTML +="<dl><dt>回收废品</dt>"+trxorder.createDateStr;
						orderListHTML +="<dd>";
						if(trxorder.trxStatus == 'INIT'){
							 orderListHTML +="<span style='color:blue;'>等待接单</span>&nbsp;";
				        	 orderListHTML +="<a href='javascript:;' onclick=\"cancelorder('"+trxorder.trxorderId+"')\">取消</a>&nbsp;";
						}else if(trxorder.trxStatus == 'PROCESSING'){
				        	 orderListHTML +="<span style='color:#fe170D;'>上门中</span>&nbsp;";
				        	 orderListHTML +="<a href='javascript:;' onclick=\"cancelorder('"+trxorder.trxorderId+"')\">取消</a>&nbsp;";
				         }else if(trxorder.trxStatus == 'CONFIRM'){
				        	 orderListHTML +="<a href='javascript:;' onclick=\"confirmorder('"+trxorder.trxorderId+"',true)\">确认</a>&nbsp;";
				        	 orderListHTML +="<a href='javascript:;' onclick=\"confirmorder('"+trxorder.trxorderId+"',false)\">详情</a>&nbsp;";
				         }else if(trxorder.trxStatus == 'SUCCESS'){
				        	 orderListHTML +="<span style='color:#7FFF00;'>已完成</span>&nbsp;";
				        	 orderListHTML +="<a href='javascript:;' onclick=\"confirmorder('"+trxorder.trxorderId+"',false)\">详情</a>&nbsp;";
				         }else if(trxorder.trxStatus == 'CANCEL'){
				        	 orderListHTML +="已取消&nbsp;";
				         }
				         orderListHTML +="</dd></dl>";
					}
				}else{
					orderListHTML +="<div style='padding-top:10px;text-align:center;'>暂无数据!</div>";
				}
				$(".jfdl").append(orderListHTML);
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
function cancelorder(trxorderId){
	$("#cancelMessage").attr("href","${pageContext.request.contextPath}/user/cancelTrxorder.do?trxorderId="+trxorderId);
	$.blockUI.open("cancelTip");
}
function confirmorder(trxorderId,isconfirmorder){
	$.ajax({
		type : 'POST',
		url : context+'/user/getTrxorderDetail.do',
		data:{
			trxorderId:trxorderId,
			timeStam:new Date().getTime()
		},
		async:false,
		dataType : "json",
		success : function(data) {
			if(data.rspCode == 'SUCCESS'){
				var detailHtml = "";
				if(data.detailList && data.trxorder){
					for(var i=0;i<data.detailList.length;i++){
						trxorderDetail = data.detailList[i];
						detailHtml += trxorderDetail.categoryName+":"+trxorderDetail.count+trxorderDetail.unit+"<br>";
					}
					detailHtml += "共计："+data.trxorder.trxAmount+"元<br>"+data.createDate;
				}
				if(isconfirmorder && data.trxorder.trxStatus == 'CONFIRM'){
					$("#orderConfirmDetail").html(detailHtml);
					$("#confirmOrder").attr("href","${pageContext.request.contextPath}/user/confirmTrxorder.do?userOption=SUCCESS&trxorderId="+trxorderId);
					$("#cancelOrder").attr("href","${pageContext.request.contextPath}/user/confirmTrxorder.do?userOption=REBACK&trxorderId="+trxorderId);
					$.blockUI.open("orderConfirmTip");
				}else{
					$("#orderDetail").html(detailHtml);
					$.blockUI.open("orderDetailTip");
				}
			}else{
				alert(data.rspMsg);
			}
		},
		error : function() {
			alert("订单查询失败!");
		}
	});
}

</script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<h2 class="jfh2"><a href="${pageContext.request.contextPath}/jsp/user/trxorderList.jsp" class="asx"></a><em class="j2"></em>回收记录</h2>
	<input type="hidden" name="currentPage" value="1" id="currentPage" />
    <div class="jfdl">
    </div>
    
     <div id="loading" align="center" style="display: none;">
		<img src="${pageContext.request.contextPath}/images/loading.gif" width="30" height="30"/>
	</div>
	<div class="tex-center" style="margin-top:10px;" id="viewMoreDiv" >
		<input name="viewMore" id="viewMore"  type="button" class="page_btn" value="查看更多" />
	</div>
    
</article>
<!--内容end-->

<div class="popup" style="display: none;" id="orderConfirmTip">
	<div class="po1" id="orderConfirmDetail"></div>
    <dl class="podl">
    	<dt id="confirmDiv"><a href="javascript:;" id="confirmOrder">确定</a></dt>
        <dd><a href="javascript:;" id="cancelOrder">退回</a></dd>
    </dl>
</div>

<div class="popup" style="display: none;" id="cancelTip">
	<div class="po1">亲，确定要取消此次回收请求吗?</div>
    <dl class="podl">
    	<dt ><a href="javascript:;" id="cancelMessage">确定</a></dt>
        <dd><a href="javascript:;" onclick="$.blockUI.close();">取消</a></dd>
    </dl>
</div>

<div class="popup" style="display: none;" id="orderDetailTip">
	<div class="po1" id="orderDetail"></div>
	<div class="poul tex-center"><a href="javascript:;" onclick="$.blockUI.close();">取消</a></div>
</div>

<div class="popup" id="messageTip" style="display:none;">
	<div class="po1" id="messageDiv"></div>
    <div class="poul tex-center"><a href="javascript:;" onclick="$.blockUI.close();">知道了</a></div>
</div>
</div>
<!--footer start-->
<%@ include file="/jsp/common/footer.jsp"%>
<!--footer end-->

</body>
</html>