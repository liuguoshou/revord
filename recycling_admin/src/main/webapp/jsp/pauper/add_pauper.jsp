<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<form id="pauper_add_dialog_form" action="<%=contextPath%>/admin/saveRecyclingAdminPauper.do" class="form-horizontal" method="post" enctype="multipart/form-data">

    <div class="control-group">
        <label class="control-label">选择区域:</label>
        <div class="controls">
           <%--  <select class="form-control" id="add_pauper_region_parent_id" name="pauper_region_parent_id" style="margin-bottom: 0px;">
                <option value="">---请选择---</option>
                <c:forEach items="${recRegionList}"  var="region" varStatus="ll">
                    <option value="${region.regionId}">${region.regionCnName}</option>
                </c:forEach>
            </select> --%>
            <select style="width: 70px;"   class="form-control" id="rec_pauper_region_privenc_id" name="rec_pauper_region_privenc_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
            </select>
             <select style="width: 70px;"   class="form-control" id="rec_pauper_region_city_id" name="rec_pauper_region_city_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            <select style="width: 70px;"  class="form-control" id="rec_pauper_region_district_id" name="rec_pauper_region_district_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            
            <select style="width: 70px;"  class="form-control" id="rec_pauper_region_street_id" name="rec_pauper_region_street_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            
            
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>选择小区:</label>
        <div class="controls">
            <div id="accordionPauperAdd" class="accordion-style1 panel-group">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title" id="add_region_checkbox">
                        </h4>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>回收人员姓名:</label>
        <div class="controls">
            <input type="text" name="pauper_name" id="pauper_name"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>回收人员电话:</label>
        <div class="controls">
            <input type="text" name="pauper_mobile" id="pauper_mobile"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" ><span style="color: red">*</span>登录密码:</label>
        <div class="controls">
            <input type="text" name="pauper_password" id="pauper_password"/>
        </div>
    </div>
    <input type="hidden" name="method" value="save"/>
    <input type="hidden" name="regionIds" id="addRegionIds"/>
</form>
<script>
    $(function(){
        $("#add_pauper_region_parent_id").on("change",function(){
            $.get("<%=contextPath%>/admin/getRecyclingAdminRegionByParentId.do?region_parent_id="+$(this).val(), function(result) {
                $("#add_region_checkbox").html("");
                $.each(result, function(i, val) {
                    var html='';
                    if(i%3==0){
                        html+="<br>";
                    }
                    html+='<input name="region'+this.regionId+'" value="'+this.regionId+'" type="checkbox" >&nbsp;&nbsp;&nbsp;&nbsp;'+ this.regionCnName +"&nbsp;";
                    $("#add_region_checkbox").append(html);
                });
            }, "json");
        });
    });
    
    
    

	$('#rec_pauper_region_privenc_id').change(function(){ //省与市之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_pauper_region_privenc_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){
				var selOpt = $("#rec_pauper_region_city_id option");
					selOpt.remove();
				var sel = $("#rec_pauper_region_city_id");
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
	
	$('#rec_pauper_region_city_id').change(function(){ //市与区之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_pauper_region_city_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_pauper_region_district_id option");
					selOpt.remove();
				var sel = $("#rec_pauper_region_district_id");
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
	
	
	$('#rec_pauper_region_district_id').change(function(){ //区与街道之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_pauper_region_district_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_pauper_region_street_id option");
					selOpt.remove();
				var sel = $("#rec_pauper_region_street_id");
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
	
	
	
	
	$('#rec_pauper_region_street_id').change(function(){ //街道与小区之间的级联
		 $.get("<%=contextPath%>/admin/getRecyclingAdminRegionByParentId.do?region_parent_id="+$(this).val(), function(result) {
             $("#add_region_checkbox").html("");
             $.each(result, function(i, val) {
                 var html='';
                 if(i%3==0){
                     html+="<br>";
                 }
                 html+='<input name="region'+this.regionId+'" value="'+this.regionId+'" type="checkbox" >&nbsp;&nbsp;&nbsp;&nbsp;'+ this.regionCnName +"&nbsp;";
                 $("#add_region_checkbox").append(html);
             });
         }, "json");
    
		
		/*
		$.ajax({
			type: "post",*/
			//url: "<%=contextPath%>/admin/queryRecyclingAdminRegionJs.do",
			/*data: {parentId:$("#rec_pauper_region_street_id").val(),selectType:'all'},
			dataType: "json",
			success: function(data){	
				var selOpt = $("#rec_pauper_region_region_id option");
					selOpt.remove();
				var sel = $("#rec_pauper_region_region_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['regionId']+"' id='"+comment['isActive']+"' >"+comment['regionCnName']+"</option>");
                });
                getActiveCity();
             },
             error: function (){
            	 alert("ERROR");
             }
		});*/
	});
	
	$('#rec_pauper_region_region_id').change(function(){ //省与市之间的级联
		var isActive=$("#rec_pauper_region_region_id option:selected").attr("id");
		
		if(isActive=="1"){
			$("#area_region_isActiveY").attr("checked","checked");
		}else if(isActive=="0"){
			$("#area_region_isActiveN").attr("checked","checked");
		}
		
	});
</script>