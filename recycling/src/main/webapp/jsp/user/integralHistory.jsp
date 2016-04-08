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
<title>积分明细</title>
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
		url : context+'/user/showIntegralHistory.do',
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
				if(data.historyList && data.totalCount  > 0){
					for(var i=0;i<data.historyList.length;i++){
						var historyInfo = data.historyList[i];
						orderListHTML +="<li><span>"+historyInfo.createDateStr+"</span>";
						if(historyInfo.integralHistoryType == 'RECYCLE_GARBAGE'){
							orderListHTML +="回收废品";
						}
						if(historyInfo.integralHistoryType == 'RECYCLE_GARBAGE'){
							orderListHTML +="+";
						}else{
							orderListHTML +="-";
						}
						orderListHTML +=historyInfo.trxAmount+"积分</li>";
					}
				}else{
					orderListHTML +="<div style='padding-top:10px;text-align:center;'>暂无数据!</div>";
				}
				$(".jful").append(orderListHTML);
			}else{
				$("#messageDiv").html(data.rspMsg);
				$.blockUI.open("messageTip");
			}
		},
		error: function(XmlHttpRequest, textStatus, errorThrown){
			$("#messageDiv").html("积分信息查询失败!");
			$.blockUI.open("messageTip");
	    }
	});
}

</script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<h2 class="jfh2"><a href="${pageContext.request.contextPath}/jsp/user/integralHistory.jsp" class="asx"></a><em class="j1"></em>积分列表</h2>
	<input type="hidden" name="currentPage" value="1" id="currentPage" />
    <ul class="jful">
    	
    </ul>
    
     <div id="loading" align="center" style="display: none;">
		<img src="${pageContext.request.contextPath}/images/loading.gif" width="30" height="30"/>
	</div>
	<div class="tex-center" style="margin-top:10px;" id="viewMoreDiv" >
		<input name="viewMore" id="viewMore"  type="button" class="page_btn" value="查看更多" />
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