<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>

<script type="text/javascript">
	
	$('#rec_are_region_privenc_id').change(function(){ //省与市之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_are_region_privenc_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){
				var selOpt = $("#rec_are_region_city_id option");
					selOpt.remove();
				var sel = $("#rec_are_region_city_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
                getActiveCity();
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	$('#rec_are_region_city_id').change(function(){ //市与区之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_are_region_city_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_are_region_district_id option");
					selOpt.remove();
				var sel = $("#rec_are_region_district_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
                getActiveCity();
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	
	$('#rec_are_region_district_id').change(function(){ //区与街道之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_are_region_district_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_are_region_street_id option");
					selOpt.remove();
				var sel = $("#rec_are_region_street_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
                getActiveCity();
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	
	
	
	$('#rec_are_region_street_id').change(function(){ //街道与小区之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminRegionJs.do",
			data: {parentId:$("#rec_are_region_street_id").val(),selectType:'all'},
			dataType: "json",
			success: function(data){	
				var selOpt = $("#rec_are_region_region_id option");
					selOpt.remove();
				var sel = $("#rec_are_region_region_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['regionId']+"' id='"+comment['isActive']+"' >"+comment['regionCnName']+"</option>");
                });
                getActiveCity();
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	$('#rec_are_region_region_id').change(function(){ //省与市之间的级联
		var isActive=$("#rec_are_region_region_id option:selected").attr("id");
		
		if(isActive=="1"){
			$("#area_region_isActiveY").attr("checked","checked");
		}else if(isActive=="0"){
			$("#area_region_isActiveN").attr("checked","checked");
		}
		
	});
</script>
<form id="region_add_dialog_form" action="<%=contextPath%>/admin/saveSystemUser.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>选择要开通的区域：</label>
        <div class="controls">
            <select style="width: 70px;"  onchange="isActive()" class="form-control" id="rec_are_region_privenc_id" name="rec_are_region_privenc_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
            </select>
             <select style="width: 70px;"   class="form-control" id="rec_are_region_city_id" name="rec_are_region_city_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            <select style="width: 70px;"  class="form-control" id="rec_are_region_district_id" name="rec_are_region_district_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            
            <select style="width: 70px;"  class="form-control" id="rec_are_region_street_id" name="rec_are_region_street_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            
            
           <%--  <select class="form-control" id="region_parent_id" name="region_parent_id" style="margin-bottom: 0px;">
                <option value="0">---请选择---</option>
                <c:forEach items="${recRegionList}" var="region" varStatus="ll">
                    <option value="${region.regionId}">${region.regionCnName}</option>
                </c:forEach>
            </select> --%>           
            
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>开通小区名称：</label>
        <div class="controls">
        	<select style="width: 140px;"  class="form-control" id="rec_are_region_region_id" name="rec_are_region_region_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            <input style="width: 140px;"  id="region_cn_name" class="inputxt" name="region_cn_name" type="text" />
        </div>
    </div>
    
   <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>是否开通：</label>
        <div class="controls">
        
       	<input type="radio" id="area_region_isActiveY"  name="area_region_isActive_radio" value="1" >
		<span class="lbl">开通</span>
		<input type="radio" id="area_region_isActiveN"  name="area_region_isActive_radio" value="0" >
		<span class="lbl">关闭</span>
        </div>
    </div>

    <input type="hidden" name="method" value="save"/>
</form>