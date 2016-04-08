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
<title>回收系统</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.form.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/blockUI.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/other.js" type="text/javascript"></script>
<script type="text/javascript">
var context = '${pageContext.request.contextPath}';
$(function(){
	//区域联动
	$('#parentRegionId').change(function(){
		var selParentId = $('#parentRegionId option:selected').val();
		$.ajax({
			type : 'POST',
			url : context+'/user/queryAreaJs.do',
			data:{	
				regionId:selParentId,
				timeStam:new Date().getTime() 
			},
			async:false,
			dataType : "json",
			success : function(date) {
				var regionJson = date.regionMap;
				var regionList = '';
				for(var i=0;i<regionJson.length;i++){
					regionList += '<option value="'+regionJson[i].regionId+'">'+regionJson[i].regionCnName+'</option>';
				}
				$("#regionId").html(regionList);
			},
			error : function() {
				alert("区域信息查询失败!");
			}
		});
	});
	
	
	
	    
	    //查询条件JS ---------------------
	      $('#provinceId').change(function(){ //省与市之间的级联
	    	 var parentId = $("#provinceId").val();
	    	 var selectType= 'active';
	      
	  		$.ajax({
	  			type : 'POST',
	  			url : context+'/user/queryAreaJs.do',
	  			data:{	
	  				parentId:parentId,
	  				selectType:selectType
	  			},
	  			async:false,
	  			dataType : "json",
	  			success : function(data) {
	  				var selOpt = $("#cityId option");
	  					selOpt.remove();
	  				var selOpt1 = $("#districtId option");
	  					selOpt1.remove();
	  				var selOpt2 = $("#streetId option");
	  					selOpt2.remove();
	  				var selOpt3 = $("#regionId option");
	  					selOpt3.remove();
	  				var sel = $("#cityId");
	  				sel.append("<option value=''>"+"--选择市--"+"</option>");
	                  $.each(data, function(commentIndex, comment){
	                  	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
	                  });
	  			},
	  			error : function() {
	  				alert("区域信息查询失败!");
	  			}
	  		});
	  	});
	  	
	  	$('#cityId').change(function(){ //市与区之间的级联
	  		$.ajax({
	  			type: "post",
	  			url : context+'/user/queryAreaJs.do',
	  			data: {parentId:$("#cityId").val(),selectType:'active'},
	  			dataType: "json",
	  			success: function(data){				
	  				var selOpt = $("#districtId option");
	  					selOpt.remove();
	  				var selOpt1 = $("#streetId option");
	  					selOpt1.remove();
	  				var selOpt2 = $("#regionId option");
	  					selOpt2.remove();
	  				var sel = $("#districtId");
	  				sel.append("<option value=''>"+"--选择区--"+"</option>");
	                  $.each(data, function(commentIndex, comment){
	                  	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
	                  });
	               },
	               error: function (){
	              	 alert("ERROR");
	               }
	  		});
	  		 
	  	});
	  	
	  	
	  	$('#districtId').change(function(){ //区与街道之间的级联
	  		$.ajax({
	  			type: "post",
	  			url : context+'/user/queryAreaJs.do',
	  			data: {parentId:$("#districtId").val(),selectType:'active'},
	  			dataType: "json",
	  			success: function(data){				
	  				var selOpt = $("#streetId option");
	  					selOpt.remove();
	  				var selOpt1 = $("#regionId option");
	  					selOpt1.remove();
	  				var sel = $("#streetId");
	  				sel.append("<option value=''>"+"--选择街--"+"</option>");
	                  $.each(data, function(commentIndex, comment){
	                  	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
	                  });
	                  
	               },
	               error: function (){
	              	 alert("ERROR");
	               }
	  		});	
	  	});
	  		  	
	  	$('#streetId').change(function(){ //街道与小区之间的级联
	  		var selParentId = $('#streetId option:selected').val();
			$.ajax({
				type : 'POST',
				url : context+'/user/queryRegionMap.do',
				data:{	
					regionId:selParentId,
					timeStam:new Date().getTime() 
				},
				async:false,
				dataType : "json",
				success : function(date) {
					var selOpt = $("#regionId option");
  					selOpt.remove();
					var regionJson = date.regionMap;
					var regionList = '';
					for(var i=0;i<regionJson.length;i++){
						regionList += '<option value="'+regionJson[i].regionId+'">'+regionJson[i].regionCnName+'</option>';
					}
					$("#regionId").html(regionList);
				},
				error : function() {
					alert("区域信息查询失败!");
				}
			});
	  	});
	
	
	//-------------------------------
	$("#submitButton").click(function(){
		var address = $.trim($("#address").val());
		var realName = $.trim($("#realName").val());
		var mobile = $.trim($("#mobile").val());
		if(null == address || address == ''){
			$("#messageDiv").html("请输入您的门牌号！");
			$.blockUI.open("messageTip");
			return false;
		}
		if(null == realName || realName == ''){
			$("#messageDiv").html("请输入您的姓名！");
			$.blockUI.open("messageTip");
			return false;
		}
		if(null == mobile || mobile == ''){
			$("#messageDiv").html("请输入您的手机号！");
			$.blockUI.open("messageTip");
			return false;
		}
		var reg = /^0?(1[0-9][0-9])[0-9]{8}$/;
		if (!reg.test(mobile)) {
			$("#messageDiv").html("请输入正确的手机号码！");
			$.blockUI.open("messageTip");
			return false;
		}
		$("#userForm").ajaxSubmit({
			type:'post',
			dataType :"json",
			async:false,
			url: context+'/user/modifyUserInfo.do',
			success: function(data){
				if(data.rspCode == 'SUCCESS'){
					window.location.href=context+"/user/userIndex.do";
				}else{
					$("#messageDiv").html(data.rspMsg);
					$.blockUI.open("messageTip");
				}
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){
				$("#messageDiv").html("保存失败，请稍后重试!");
				$.blockUI.open("messageTip");
			    }
			});
		
		//$("#userForm").submit();
	});
})
</script>
</head>

<body>

<!--内容start-->
<div class="warpxx"  id="co1">
<article>
<form action="${pageContext.request.contextPath}/user/modifyUserInfo.do" method="post" id="userForm">
	<input type="hidden" name="areaId" id="areaId" value="${loginUser.areaId}"/>
	<input type="hidden" id="regionMap" value="${regionMap}" />
    
    
    
    <div class="dzlb clearfix">
    	<div class="dzl">省</div>
        <div class="dzy">
        	<select name="provinceId" id="provinceId">    
        		<option value=""  >--选择省--</option>    	
        		<c:forEach var="province" items="${provinceList}">
	        		<option value="${province.areaId}" <c:if test="${provinceId==province.areaId}">selected="selected"</c:if> >${province.areaCnName }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>
    <div class="dzlb clearfix">
    	<div class="dzl">市</div>
        <div class="dzy">
        	<select name="cityId" id="cityId">    
        		<c:forEach var="city" items="${cityList}">
	        		<option value="${city.areaId}" <c:if test="${cityId==city.areaId}">selected="selected"</c:if> >${city.areaCnName }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>
    <div class="dzlb clearfix">
    	<div class="dzl">区</div>
        <div class="dzy">
        	<select name="districtId" id="districtId">    
        		<c:forEach var="district" items="${districtList}">
	        		<option value="${district.areaId}" <c:if test="${districtId==district.areaId}">selected="selected"</c:if> >${district.areaCnName }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>
    
    		
    <div class="dzlb clearfix">
    	<div class="dzl">街道</div>
        <div class="dzy">
        	<select name="streetId" id="streetId">    
        		<c:forEach var="street" items="${streetList}">
	        		<option value="${street.areaId}" <c:if test="${streetId==street.areaId}">selected="selected"</c:if> >${street.areaCnName }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>
    
    <div class="dzlb clearfix">
    	<div class="dzl">小区</div>
        <div class="dzy">
            <select name="regionId" id="regionId">
            	<c:forEach var="region" items="${regionList}">
            		<option value="${region.regionId}" <c:if test="${regionId==region.regionId}">selected="selected"</c:if> >${region.regionCnName }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>
    
 
    
<%--      <div class="dzlb clearfix">
    	<div class="dzl">区域</div>
        <div class="dzy">
        	<select name="parentRegionId" id="parentRegionId">
        	<option value="0">北</option>
        	<option value="110000">北京</option>
        		<c:forEach var="parentRegion" items="${regionList}">
	        		<option value="${parentRegion.regionId}" <c:if test="${parentRegionId==parentRegion.regionId}">selected="selected"</c:if> >${parentRegion.regionCnName }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>
    
    <div class="dzlb clearfix">
    	<div class="dzl">小区</div>
        <div class="dzy">
            <select name="regionId" id="regionId">
            	<c:forEach var="region" items="${regionExtList}">
            		<option value="${region.regionId}" <c:if test="${regionId==region.regionId}">selected="selected"</c:if> >${region.regionCnName }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>  --%>
    
    <div class="dzlb clearfix">
    	<div class="dzl">门牌号</div>
        <div class="dzr"><input name="address" id="address" type="text" class="form_put" value="${loginUser.address}" maxlength="200"></div>
    </div>
    
    <div class="dzlb clearfix">
    	<div class="dzl">姓名</div>
        <div class="dzr"><input name="realName" id="realName" type="text" class="form_put" value="${loginUser.realName }" maxlength="20"></div>
    </div>
    
    <div class="dzlb clearfix">
    	<div class="dzl">手机</div>
        <div class="dzr"><input name="mobile" id="mobile" type="text" class="form_put" value="${loginUser.mobile }"   onkeyup="this.value=this.value.replace(/[^\d]/g,'')" maxlength="11"></div>
    </div>
    <div class="tex-center"><input name="" id="submitButton" type="button" class="form_btn" value="保存"></div>
</form>
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