<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<script>
    $(function(){
        $("#add_user_area_parent_id").on("change",function(){
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
        
        $("#user_area_all").on("click",function(){
        	if($("#user_area_all").prop('checked')){
        		$("#rec_user_area_privenc_id").attr("disabled","disabled");
        		$("#rec_user_area_city_id").attr("disabled","disabled");
        		$("#rec_user_area_district_id").attr("disabled","disabled");
        		$("#rec_user_area_street_id").attr("disabled","disabled");
        		
        		$("#rec_user_area_privenc_id").get(0).selectedIndex=0;
        		$("#rec_user_area_city_id").get(0).selectedIndex=0;
        		$("#rec_user_area_district_id").get(0).selectedIndex=0;
        		$("#rec_user_area_street_id").get(0).selectedIndex=0;  
        		
        		$("#add_region_checkbox").empty();
        		
        		
        		
        	}else{
        		$("#rec_user_area_privenc_id").removeAttr("disabled");
        		$("#rec_user_area_city_id").removeAttr("disabled");
        		$("#rec_user_area_district_id").removeAttr("disabled");
        		$("#rec_user_area_street_id").removeAttr("disabled");
        	}
        });
        
        
    });
    
    
    

	$('#rec_user_area_privenc_id').change(function(){ //省与市之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_user_area_privenc_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){
				var selOpt = $("#rec_user_area_city_id option");
					selOpt.remove();
				var sel = $("#rec_user_area_city_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
                
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	$('#rec_user_area_city_id').change(function(){ //市与区之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_user_area_city_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_user_area_district_id option");
					selOpt.remove();
				var sel = $("#rec_user_area_district_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	
	$('#rec_user_area_district_id').change(function(){ //区与街道之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_user_area_district_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_user_area_street_id option");
					selOpt.remove();
				var sel = $("#rec_user_area_street_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	
	
	
	$('#rec_user_area_street_id').change(function(){ //街道与小区之间的级联
		 $.get("<%=contextPath%>/admin/getRecyclingAdminRegionByParentId.do?region_parent_id="+$(this).val(), function(result) {
             $("#add_region_checkbox").html("");
             $.each(result, function(i, val) {
                 var html='';
                 if(i%3==0){
                     html+="<br>";
                 }
                 html+='<input name="u_region'+this.regionId+'" value="'+this.regionId+'" type="checkbox" >&nbsp;&nbsp;&nbsp;&nbsp;'+ this.regionCnName +"&nbsp;";
                 $("#add_region_checkbox").append(html);
             });
         }, "json");
    
		
		/*
		$.ajax({
			type: "post",*/
			//url: "<%=contextPath%>/admin/queryRecyclingAdminRegionJs.do",
			/*data: {parentId:$("#rec_user_area_street_id").val(),selectType:'all'},
			dataType: "json",
			success: function(data){	
				var selOpt = $("#rec_user_area_region_id option");
					selOpt.remove();
				var sel = $("#rec_user_area_region_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['regionId']+"' id='"+comment['isActive']+"' >"+comment['regionCnName']+"</option>");
                });
             },
             error: function (){
            	 alert("ERROR");
             }
		});*/
	});
	
	$('#rec_user_area_region_id').change(function(){ //省与市之间的级联
		var isActive=$("#rec_user_area_region_id option:selected").attr("id");
		
		if(isActive=="1"){
			$("#area_region_isActiveY").attr("checked","checked");
		}else if(isActive=="0"){
			$("#area_region_isActiveN").attr("checked","checked");
		}
		
	});
	
	
	
</script>

<form id="user_area_add_dialog_form" action="<%=contextPath%>/admin/saveSystemUserArea.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label" for="user_name"><span style="color: red">*</span>用户名称:</label>
        <div class="controls">
        	<input id="user_id" class="inputxt" readonly="readonly" name="user_id" type="hidden" value="${adminUser.adminUserId}"/>
            <input id="user_name" class="inputxt" readonly="readonly" name="user_name" type="text" value="${adminUser.userName}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="user_realname"><span style="color: red">*</span>真实名字:</label>
        <div class="controls">
            <input type="text" class="inputxt" readonly="readonly" name="user_realname" id="user_realname" value="${adminUser.realName}"/>
        </div>
    </div>
    
   <div class="control-group">
        <label class="control-label" for="user_realname">全部:</label>
        <div class="controls">
        	<input type="checkbox" name="user_area_all" id="user_area_all" >
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label">选择区域:</label>
        <div class="controls">
           
            <select style="width: 70px;"   class="form-control" id="rec_user_area_privenc_id" name="rec_user_area_privenc_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
            </select>
             <select style="width: 70px;"   class="form-control" id="rec_user_area_city_id" name="rec_user_area_city_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            <select style="width: 70px;"  class="form-control" id="rec_user_area_district_id" name="rec_user_area_district_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            
            <select style="width: 70px;"  class="form-control" id="rec_user_area_street_id" name="rec_user_area_street_id" style="margin-bottom: 0px;">
                <option value="">--选择--</option>
            </select>
            
            
        </div>
    </div>

    <div class="control-group">
    	<input type="hidden" name="userAreaIds" id="userAreaIds" value="">
        <label class="control-label">选择小区:</label>
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

    

    
    <input type="hidden" name="method" value="save"/>
</form>