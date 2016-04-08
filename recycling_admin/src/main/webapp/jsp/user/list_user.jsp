<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <div class="row-fluid">
        <div class="table-header">
            <div class="col-sm-6" align="right" >
            	<input type="hidden" id="rec_user_parent_id" name="rec_user_parent_id" value=""/>
               <%--  <select class="form-control" id="rec_user_parent_id" style="margin-bottom: 0px;">
                    <option value="">---请选择---</option>
                    <c:forEach items="${recRegionList}" var="region" varStatus="ll">
                        <option value="${region.regionId}">${region.regionCnName}</option>
                    </c:forEach>
                </select>
                <select class="form-control" id="region_id" style="margin-bottom: 0px;">
                    <option value="">---请选择---</option>
                </select> --%>
			<select   class="form-control" id="rec_user_privenc_id" name="rec_user_privenc_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
            </select>
             <select   class="form-control" id="rec_user_city_id" name="rec_user_city_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
            </select>
            <select  class="form-control" id="rec_user_district_id" name="rec_user_district_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
            </select>
            
            <select  class="form-control" id="rec_user_street_id" name="rec_user_street_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
            </select>
                <input type="text" id="mobile" placeholder="手机号"  style="margin-bottom: 0px;">
                <input type="text" id="real_name" placeholder="姓名" style="margin-bottom: 0px;">
                <button type="button" id="search" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                    查询
                </button>
            </div>
        </div>
        <table id="table_admin_user"
               class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>姓名</th>
                    <th>手机号</th>
                    <th>小区</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
    <!--/row-->
    <div id="user_modify_dialog_1" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="user_modify_dialog_title_1" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="user_modify_dialog_title_1">查看-用户信息</h3>
        </div>
        <div id="user_modify_dialog_content_1" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        </div>
    </div>
</div>
<script language="javascript">
    var oTable = null;
    var flag="";
    $(function() {
        oTable = $('#table_admin_user').dataTable( {
            "oLanguage": {
                "sUrl": "<%=contextPath%>/js/table_zh_CN.txt"
            },
            "bServerSide": true,
            "bStateSave": true,
            "bFilter": false,
            "iDisplayLength": 10,
            "sPaginationType":"full_numbers",
            "bAutoWidth":false,
            "aoColumnDefs" : [
                {
                    "mData" : "userId",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                {
                    "mData" : "realName",
                    "sWidth" : "200px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "mobile",
                    "sWidth" : "200px",
                    "aTargets" : [ 2 ],
                    "bSortable" : false
                },{
                    "mData" : "region.regionCnName",
                    "aTargets" : [ 3 ],
                    "sWidth" : "200px",
                    "bSortable" : false
                },
                {
                    "mData" : "userId",
                    "sClass" : "center",
                    "sWidth" : "80px",
                    "aTargets" : [ 4 ],
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var str='';
                        str += '<a href="javascript:void(0)" onclick="editUser(\''
                        + full.userId
                        + '\');">详情</a>';
                        return str;
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryRecyclingUser.do",
            "fnServerData" : function(sSource, aoData,fnCallback) {
                $.ajax({
                    "dataType" : 'json',
                    "type" : "get",
                    "url" : sSource,
                    "data" : aoData,
                    "success" : fnCallback
                });
            },
            "fnServerParams" : function(aoData) {//重要设置，可通过此设置添加额外查询参数
                aoData.push({
                    "name" : "rec_user_parent_id",
                    "value" : $("#rec_user_parent_id").val()
                },{
                    "name" : "region_id",
                    "value" : $("#region_id").val()
                },{
                    "name" : "mobile",
                    "value" : $("#mobile").val()
                },{
                    "name" : "real_name",
                    "value" : $("#real_name").val()
                });
            },
            //生成序号
            "fnDrawCallback" : function(oSettings) {
                var that = this;
                this.$('td:first-child', {
                    "filter" : "applied"
                }).each(
                        function(i) {
                            that.fnUpdate(i + 1,
                                    this.parentNode, 0,
                                    false, false);
                        });

            }
        });

        function query() {
            oTable.fnDraw();
        };

        function getQueryFilter() {
            var searchStr = '';
            return searchStr;
        };

        $('[data-rel=tooltip]').tooltip();

        $("#rec_user_parent_id").on("change",function(){
            $("#region_id").html('<option value="">---请选择---</option>');
            $.get("<%=contextPath%>/admin/getRecyclingAdminRegionByParentId.do?rec_user_parent_id="+$(this).val(), function(result) {
                $.each(result, function(i, val) {
                    $("#region_id").append(
                            "<option value='"+this.regionId+"'>" + this.regionCnName
                            + "</option>");
                })
            }, "json");
        })

        $("#search").on("click",function(){
            query();
        })

    })

    function editUser(user_id) {
        $("#user_modify_dialog_content_1").load('<%=contextPath%>/admin/toGetRecyclingUserById.do?user_id='+user_id);
        $('#user_modify_dialog_1').modal("show");
    }

    
    //查询条件JS ---------------------
    $('#rec_user_privenc_id').change(function(){ //省与市之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_user_privenc_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){
				var selOpt = $("#rec_user_city_id option");
					selOpt.remove();
				var selOpt1 = $("#rec_user_district_id option");
					selOpt1.remove();
				var selOpt2 = $("#rec_user_street_id option");
					selOpt2.remove();
				var sel = $("#rec_user_city_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
                getParentId();
             },
             error: function (){
            	 alert("ERROR");
             }
		});
	});
	
	$('#rec_user_city_id').change(function(){ //市与区之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_user_city_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_user_district_id option");
					selOpt.remove();
				var selOpt1 = $("#rec_user_street_id option");
					selOpt1.remove();
				var sel = $("#rec_user_district_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
                getParentId();
             },
             error: function (){
            	 alert("ERROR");
             }
		});
		 
	});
	
	
	$('#rec_user_district_id').change(function(){ //区与街道之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_user_district_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_user_street_id option");
					selOpt.remove();
				var sel = $("#rec_user_street_id");
				sel.append("<option value=''>"+"---请选择---"+"</option>");
                $.each(data, function(commentIndex, comment){
                	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                });
                
                getParentId();
             },
             error: function (){
            	 alert("ERROR");
             }
		});	
	});
	
	
	$('#rec_user_street_id').change(function(){ //区与街道之间的级联
		getParentId();
	});
	
	function getParentId(){
		var rec_user_privenc_id 	= $("#rec_user_privenc_id").val();
    	var rec_user_city_id 		= $("#rec_user_city_id").val();
    	var rec_user_district_id 	= $("#rec_user_district_id").val();
    	var rec_user_street_id 		= $("#rec_user_street_id").val();
    	if(rec_user_privenc_id ==""){
    		$("#rec_user_parent_id").val("");
    	}else if (rec_user_privenc_id != "" && rec_user_city_id ==""){
    		$("#rec_user_parent_id").val(rec_user_privenc_id);
    	}else if (rec_user_privenc_id != "" && rec_user_city_id !="" && rec_user_district_id==""){
    		$("#rec_user_parent_id").val(rec_user_city_id);
    	}else if (rec_user_privenc_id != "" && rec_user_city_id !="" && rec_user_district_id!="" && rec_user_street_id==""){
    		$("#rec_user_parent_id").val(rec_user_district_id);
      	}else if (rec_user_privenc_id != "" && rec_user_city_id !="" && rec_user_district_id!="" && rec_user_street_id!=""){
    		$("#rec_user_parent_id").val(rec_user_street_id);
    	}
	}
	
	
</script>


