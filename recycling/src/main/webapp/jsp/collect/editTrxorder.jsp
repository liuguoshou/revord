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
<title>编辑回收订单</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script type="text/javascript" >
var context = '${pageContext.request.contextPath}';
$(function(){
	
	$(".order_list .reduce").click(function(){
		var _count = $(this).next().val();
		_count--;
		if(_count<=0){
			_count = 0;
		}
		$(this).next().val(_count);
	});
	
	$(".order_list .add").click(function(){
		var _count = $(this).prev().val();
		_count++;
		$(this).prev().val(_count);
	});
	
	$("#submitButton").click(function(){
		$("#detailForm").submit();
	});
})
</script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
	<form action="${pageContext.request.contextPath}/collect/saveTrxorderDetail.do" method="post" id="detailForm">
	   <input type="hidden" name="trxorderId" value ="${trxorder.trxorderId}" />
	   <c:if test="${not empty categoryMap}">
		<c:set var="categoryList" value="${categoryMap[0]}"></c:set>
		<c:forEach var="firstCategory" items="${categoryList}">
	      <div class="catelog">
	    	<h2><a class="shou"></a> ${firstCategory.categoryName}</h2>
    		<c:if test="${not empty categoryMap[firstCategory.categoryId]}">
	    		<c:forEach var="secondCategory" items="${categoryMap[firstCategory.categoryId]}">
	    	    <c:set var="thirdCategoryList" value="${categoryMap[secondCategory.categoryId]}"></c:set>
		        <div class="catexx c_th order_list">
		            <dl>
		                <dt>${secondCategory.categoryName}</dt>
		                <c:if test="${not empty thirdCategoryList}"><dd><a class="ab shou" href="javascript:;"></a></dd></c:if>
		                <c:if test="${empty thirdCategoryList}">
		                	<dd>
		                		<input type="hidden" name="categoryId" value="${secondCategory.categoryId}" />
		                		<a class="reduce" href="javascript:;" >-</a>
		                		<input type="text" name="buyCount" class="numput" value="${secondCategory.buyCount}"   onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"  />
		                		<a href="javascript:;" class="add">+</a> 个
		                	</dd>
		                </c:if>
		            </dl>
		            <c:if test="${not empty thirdCategoryList}">
			            <ul class="cateul">
			            	<c:forEach var="thirdCategory" items="${thirdCategoryList }">
				            	<li><span>${thirdCategory.categoryName }</span>
				            		<input type="hidden" name="categoryId" value="${thirdCategory.categoryId}" />
				            		<a class="reduce" href="javascript:;" >-</a>
				            		<input name="buyCount" type="text" class="numput" value="${thirdCategory.buyCount }"   onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" />
				            		<a  href="javascript:;" class="add">+</a> 个
				            	</li>
			            	</c:forEach>
			            </ul>
		            </c:if>
		          </div>
		        </c:forEach>
		       </c:if>
	    </div>
	    </c:forEach>
    </c:if>
     <div class="tex-center"><input id="submitButton" type="button" class="form_btn" value="确定"></div>
    </form>
</article>
</div>
<!--内容end-->

<!--footer start-->
<footer class="mybag">
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