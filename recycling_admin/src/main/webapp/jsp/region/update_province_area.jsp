<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<script type="text/javascript">

	
	$('#rec_are_id').change(function(){ //省与市之间的级联
		var isActive=$("#rec_are_id option:selected").attr("id");
		if(isActive=="1"){
			$("#isActiveY").attr("checked","checked");
		}else{
			$("#isActiveN").attr("checked","checked");
		}
	})
</script>

<form id="area_province_update_area_form" action="<%=contextPath%>/admin/updateArea.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>选择要开通的省：</label>
        <div class="controls">
            <select onchange="isActive()" class="form-control" id="rec_are_id" name="rec_are_id" style="margin-bottom: 0px;">
                <option value="0">---请选择---</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>是否开通：</label>
        <div class="controls">
         <!--    <input id="region_cn_name" class="inputxt" name="region_cn_name" type="radio" />开通
       <input id="region_cn_name" class="inputxt" name="region_cn_name" type="radio" />关闭 -->
       
       <input type="radio" id="isActiveY"  name="area_isActive_radio" value="1" >
		<span class="lbl">开通</span>
		<input type="radio" id="isActiveN"  name="area_isActive_radio" value="0" >
		<span class="lbl">关闭</span>
        </div>
    </div>

    <input type="hidden" name="method" value="save"/>
</form>