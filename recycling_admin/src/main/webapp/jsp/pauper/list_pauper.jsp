<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <div class="row-fluid">
        <div class="table-header">
            <div class="col-sm-6" align="right">
            	<input type="hidden" id="region_parent_id" value=""/>
                <%--  <select class="form-control" id="region_parent_id" style="margin-bottom: 0px;">
                    <option value="">---请选择---</option>
                    <c:forEach items="${recRegionList}" var="region" varStatus="ll">
                        <option value="${region.regionId}">${region.regionCnName}</option>
                    </c:forEach>
                </select>  
                <select class="form-control" id="region_id" style="margin-bottom: 0px;">
                    <option value="">---请选择---</option>
                </select>
                --%>
                
                <select   class="form-control" id="rec_pauper_privenc_id" name="rec_pauper_privenc_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
	            </select>
	             <select   class="form-control" id="rec_pauper_city_id" name="rec_pauper_city_id" style="margin-bottom: 0px; width: 100px;">
	                <option value="">---请选择---</option>
	            </select>
	            <select  class="form-control" id="rec_pauper_district_id" name="rec_pauper_district_id" style="margin-bottom: 0px; width: 100px;">
	                <option value="">---请选择---</option>
	            </select>
	            
	            <select  class="form-control" id="rec_pauper_street_id" name="rec_pauper_street_id" style="margin-bottom: 0px; width: 100px;">
	                <option value="">---请选择---</option>
	            </select>
                
                <button type="button" id="search" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                    查询
                </button>
                <button type="button" id="pauper_add_btn" class="btn">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                    添加回收人员
                </button>
            </div>
        </div>
        <table id="table_admin_user"
               class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>序号</th>
<!--                     <th>区域</th> -->
                    <th>小区</th>
                    <th>回收人员</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
    <!--/row-->
    <div id="pauper_add_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="pauper_add_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="pauper_add_dialog_title">添加-回收人员信息</h3>
        </div>
        <div id="pauper_add_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="pauper_add_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <div id="pauper_modify_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="pauper_modify_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="pauper_modify_dialog_title">修改-回收人员信息</h3>
        </div>
        <div id="pauper_modify_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="pauper_modify_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <div id="pauper_detail_dialog" style="width: 70%;margin-left: -400px;" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="pauper_detail_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="pauper_detail_dialog_title">统计-回收人员回收信息</h3>
        </div>
        <div id="pauper_detail_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        </div>
    </div>
    <div id="pauper_history_dialog" style="width: 70%;margin-left: -400px;" class="modal hide fade" tabindex="-1" role="history"  aria-labelledby="pauper_history_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="pauper_history_dialog_title">查看-回收人员回收详细信息</h3>
        </div>
        <div id="pauper_history_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        </div>
    </div>
</div>
<script language="javascript">
    var pauperTable = null;
    var flag="";
    $(function() {
        pauperTable = $('#table_admin_user').dataTable( {
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
                    "mData" : "beggar_id",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                /* {
                    "mData" : "parent_regionCnName",
                    "sWidth" : "60px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                }, */
                {
                    "mData" : "reg_name",
                    "sWidth" : "250px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },{
                    "mData" : "real_name",
                    "aTargets" : [ 2 ],
                    "sWidth" : "60px",
                    "bSortable" : false
                },
                {
                    "mData" : "beggar_status",
                    "aTargets" : [ 3 ],
                    "sWidth" : "60px",
                    "bSortable" : false,
                    "mRender" : function(data, type,full){
                        if(data == 'ACTIVE'){
                            return "<span style='color: #0000ff'>正常</span>";
                        }else{
                            return "<span style='color: red'>待业</span>";
                        }
                    }
                },
                {
                    "mData" : "beggar_id",
                    "sClass" : "center",
                    "sWidth" : "180px",
                    "aTargets" : [ 4 ],
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var str='';

                        if(full.beggar_status == 'ACTIVE'){
                            str+='<a href="javascript:void(0)" onclick="updateStatus(\''
                            +full.beggar_id+ '\',0);"><span style="color: red">解雇</span></a>';
                        }else{
                            str+='<a href="javascript:void(0)" onclick="updateStatus(\''
                            +full.beggar_id+ '\',1);"><span style="color: #0000ff">上班</span></a>';
                        }
                        str += '\n'
                        str += '<a href="javascript:void(0)" onclick="detailHistoryUser(\''
                        + full.beggar_id
                        + '\');">回收详情</a>';
                        str += '\n';
                        str += '<a href="javascript:void(0)" onclick="editUser(\''
                        + full.beggar_id
                        + '\');">详情</a>';
                        str += '\n';
                        str += '<a href="javascript:void(0)" onclick="detail_pauper(\''
                        + full.beggar_id
                        + '\');">统计回收</a>';
                        return str;
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryRecyclingAdminPauper.do",
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
                    "name" : "region_parent_id",
                    "value" : $("#region_parent_id").val()
                },{
                    "name" : "region_id",
                    "value" : $("#region_id").val()
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
            pauperTable.fnDraw();
        };

        function getQueryFilter() {
            var searchStr = '';
            return searchStr;
        };

        $('[data-rel=tooltip]').tooltip();

        $("#region_parent_id").on("change",function(){
            $("#region_id").html('<option value="">---请选择---</option>');
            $.get("<%=contextPath%>/admin/getRecyclingAdminRegionByParentId.do?region_parent_id="+$(this).val(), function(result) {
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

        $("#pauper_add_btn").on("click",function(){
            $("#pauper_add_dialog_content").load('<%=contextPath%>/admin/toAddRecyclingAdminPauper.do');
            $('#pauper_add_dialog').modal("show");
        })


        //保存操作
        $("#pauper_add_dialog_save").on("click", function () {
            //小区列表
            var regionIds=[];
            $("[name^='region']:checked").each(function(){
                regionIds.push($(this).val());
            })
            $("#addRegionIds").val(regionIds);
            $("#pauper_add_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveRecyclingAdminPauper.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    if ($("#pauper_region_id").val() == '') {
                        alert("请选择小区");
                        return false;
                    }
                    if ($("#pauper_name").val() == '') {
                        $("#pauper_name").attr("placeholder", "回收人员姓名不能为空！");
                        return false;
                    }
                    if ($("#pauper_mobile").val() == '') {
                        $("#pauper_mobile").attr("placeholder", "回收人员电话不能为空！");
                        return false;
                    }
                    if ($("#pauper_password").val() == '') {
                        $("#pauper_password").attr("placeholder", "登录密码不能为空！");
                        return false;
                    }

                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#pauper_add_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminPauper.do?_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })

        //保存修改
        $("#pauper_modify_dialog_save").on("click", function () {
            //小区列表
            var regionIds=[];
            $("[name^='region']:checked").each(function(){
                regionIds.push($(this).val());
            })
            $("#updateRegionIds").val(regionIds);
            $("#pauper_modify_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveRecyclingAdminPauper.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    if ($("#pauper_region_id").val() == '') {
                        alert("请选择小区");
                        return false;
                    }
                    if ($("#pauper_name").val() == '') {
                        $("#pauper_name").attr("placeholder", "回收人员姓名不能为空！");
                        return false;
                    }
                    if ($("#pauper_mobile").val() == '') {
                        $("#pauper_mobile").attr("placeholder", "回收人员电话不能为空！");
                        return false;
                    }
                    if ($("#pauper_password").val() == '') {
                        $("#pauper_password").attr("placeholder", "登录密码不能为空！");
                        return false;
                    }
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#pauper_modify_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminPauper.do?_=" + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })

    })

    function editUser(user_id) {
        $("#pauper_modify_dialog_content").load('<%=contextPath%>/admin/toUpdateRecycingAdminPauper.do?pauper_id='+user_id);
        $('#pauper_modify_dialog').modal("show");
    }

    function detail_pauper(user_id){
        $("#pauper_detail_dialog_content").load('<%=contextPath%>/admin/toStatisticsRecyclingAdminPauper.do?collect_id='+user_id);
        $('#pauper_detail_dialog').modal("show");
    }

    function detailHistoryUser(user_id){
        $("#pauper_history_dialog_content").load('<%=contextPath%>/admin/toQueryPauperHistoryRecyclingAdmin.do?collect_id='+user_id);
        $('#pauper_history_dialog').modal("show");
    }

    function updateStatus(user_id,status){
        if(confirm("确定修改此状态吗？")){
            $.ajax({
                url: "<%=contextPath%>/admin/updateStatusRecyclingAdminPauper.do",
                type: "post",
                dataType: 'json',
                data:{user_id:user_id,status:status},
                beforeSubmit: function () {
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#pauper_modify_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminPauper.do?_=" + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        }
    }

    
  //查询条件JS ---------------------
    $('#rec_pauper_privenc_id').change(function(){ //省与市之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_pauper_privenc_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){
				var selOpt = $("#rec_pauper_city_id option");
					selOpt.remove();
				var selOpt1 = $("#rec_pauper_district_id option");
					selOpt1.remove();
				var selOpt2 = $("#rec_pauper_street_id option");
					selOpt2.remove();
				var sel = $("#rec_pauper_city_id");
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
	
	$('#rec_pauper_city_id').change(function(){ //市与区之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_pauper_city_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_pauper_district_id option");
					selOpt.remove();
				var selOpt1 = $("#rec_pauper_street_id option");
					selOpt1.remove();
				var sel = $("#rec_pauper_district_id");
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
	
	
	$('#rec_pauper_district_id').change(function(){ //区与街道之间的级联
		$.ajax({
			type: "post",
			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
			data: {parentId:$("#rec_pauper_district_id").val(),selectType:'active'},
			dataType: "json",
			success: function(data){				
				var selOpt = $("#rec_pauper_street_id option");
					selOpt.remove();
				var sel = $("#rec_pauper_street_id");
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
	
	$('#rec_pauper_street_id').change(function(){ //区与街道之间的级联
		getParentId();
	});
	
	function getParentId(){
		var rec_pauper_privenc_id 		= $("#rec_pauper_privenc_id").val();
    	var rec_pauper_city_id 		= $("#rec_pauper_city_id").val();
    	var rec_pauper_district_id 	= $("#rec_pauper_district_id").val();
    	var rec_pauper_street_id 		= $("#rec_pauper_street_id").val();
    	if(rec_pauper_privenc_id ==""){
    		$("#region_parent_id").val("");
    	}else if (rec_pauper_privenc_id != "" && rec_pauper_city_id ==""){
    		$("#region_parent_id").val(rec_pauper_privenc_id);
    	}else if (rec_pauper_privenc_id != "" && rec_pauper_city_id !="" && rec_pauper_district_id==""){
    		$("#region_parent_id").val(rec_pauper_city_id);
    	}else if (rec_pauper_privenc_id != "" && rec_pauper_city_id !="" && rec_pauper_district_id!="" && rec_pauper_street_id==""){
    		$("#region_parent_id").val(rec_pauper_district_id);
      	}else if (rec_pauper_privenc_id != "" && rec_pauper_city_id !="" && rec_pauper_district_id!="" && rec_pauper_street_id!=""){
    		$("#region_parent_id").val(rec_pauper_street_id);
    	}
	}
	
	
</script>


