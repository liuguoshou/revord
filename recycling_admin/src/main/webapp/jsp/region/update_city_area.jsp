<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<script type="text/javascript">
	$('#rec_are_city_city_id').change(function(){ //省与市之间的级联
		var isActive=$("#rec_are_city_city_id option:selected").attr("id");
		if(isActive=="1"){
			$("#area_city_isActiveY").attr("checked","checked");
		}else{
			$("#area_city_isActiveN").attr("checked","checked");
		}
	});
	
	$('#rec_are_city_privenc_id').change(function(){ //省与市之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_are_city_privenc_id").val(),selectType:'all'},
			dataType: "json",
			success: function(data){
				var selOpt = $("#rec_are_city_city_id option");
					selOpt.remove();
				var sel = $("#rec_are_city_city_id");
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
	})
	
	
</script>

<form id="area_city_update_area_form" action="<%=contextPath%>/admin/updateArea.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>选择省：</label>
        <div class="controls">
            <select  onchange="isActive()" class="form-control" id="rec_are_city_privenc_id" name="rec_are_city_privenc_id" style="margin-bottom: 0px;">
                <option value="">---请选择---</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    
    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>选择要开通的市：</label>
        <div class="controls">
            <select onchange="isActive()" class="form-control" id="rec_are_city_city_id" name="rec_are_city_city_id" style="margin-bottom: 0px;">
                <option value="">---请选择---</option>
            </select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>是否开通：</label>
        <div class="controls">
        
       	<input type="radio" id="area_city_isActiveY"  name="area_city_isActive_radio" value="1" >
		<span class="lbl">开通</span>
		<input type="radio" id="area_city_isActiveN"  name="area_city_isActive_radio" value="0" >
		<span class="lbl">关闭</span>
        </div>
    </div>

    <input type="hidden" name="method" value="save"/>
</form>