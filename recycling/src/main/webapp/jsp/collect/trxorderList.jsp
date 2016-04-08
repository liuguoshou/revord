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
<title>订单统计</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.form.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"  type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
<script type="text/javascript" >
	var context = '${pageContext.request.contextPath}';
	$(function(){
		ajaxSubmitForm();
		$("#submitButton").click(function(){
			$("#currentPage").val(1);
			$(".hs_ul").html("");
			ajaxSubmitForm();
		});
		
		$("#viewMore").click(function(){
			var cur_page = parseInt($("#currentPage").val());
			$("#currentPage").val(cur_page+1);
			ajaxSubmitForm();
		});
	})
	
	function ajaxSubmitForm(){
		$("#loading").show();
		$("#queryForm").ajaxSubmit({
			type:'post',
			dataType :"json",
			async:false,
			url: context+'/collect/trxorderList.do',
			success: function(data){
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
							orderListHTML +="<li>";
								orderListHTML +="<a href='"+context+"/collect/viewTrxorderDetail.do?trxorderId="+trxorder.trxorderId+"'>";
								orderListHTML +="<span>回收垃圾</span>"+trxorder.createDateStr+"<br>";
								orderListHTML +="订单金额："+trxorder.trxAmount+"元<br>";
								orderListHTML +="地址："+trxorder.userAddress+"<br>";
								orderListHTML +="姓名：";
								if(trxorder.recUser && trxorder.recUser.realName){
									orderListHTML +=trxorder.recUser.realName+"<br>";
								}else{
									orderListHTML +="无<br>";
								}
								orderListHTML +="</a>";
								if(trxorder.recUser && trxorder.recUser.mobile){
									orderListHTML +="电话：<a href='tel:"+trxorder.recUser.mobile+"' class='hs_ul_a'>"+trxorder.recUser.mobile+"</a>";
								}else{
									orderListHTML +="电话：无";
								}
							orderListHTML +="</li>";
						}
					}else{
						orderListHTML +="<div style='padding-top:10px;text-align:center;'>暂无数据!</div>";
					}
					$(".hs_ul").append(orderListHTML);
				}else{
					$("#messageDiv").html(data.rspMsg);
					$.blockUI.open("messageTip");
				}
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){
				$("#messageDiv").html("统计信息查询失败!");
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
	<h2 class="jfh2"><a href="${pageContext.request.contextPath}/collect/initTrxorderList.do" class="asx"></a> <em class="j7"></em>订单统计</h2>
	<form action="${pageContext.request.contextPath}/collect/initTrxorderList.do" method="post" id="queryForm">
		<input type="hidden" name="currentPage" value="1" id="currentPage" />
	    <ul class="hs_bd">
	    	<li class="hs_b1"><input id="startDate" name="startDate" value="${param.startDate}" class="input1 w120 Wdate inputxx" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'2020-10-01\'}'})"/></li>
	    	<li class="hs_b2"><input id="endDate" name="endDate" value="${param.endDate }" class="input1 w120 Wdate inputxx" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2020-10-01'})"/></li>
	    </ul>	
	    <div class="seldiv">
	    	<p>选择小区</p>
	    	<div class="dzy">
		            <select name="regionId" id="regionId">
		            	<option value="">请选择</option>
		            	<c:forEach var="region" items="${regionList}">
		            		<option value="${region.regionId}" <c:if test="${param.regionId==region.regionId}">selected="selected"</c:if> >${region.regionCnName }</option>
		        		</c:forEach>
		        	</select>
		        </div>
	    </div>
	    <div class="tex-center" style="margin-top:10px;"><input name="submitButton" id="submitButton"  type="button" class="form_btn" value="查询" /></div>
	</form>
	
	<ul class="hs_ul">
		
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