<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <div class="row-fluid">
        <div class="table-header">
			<div class="col-sm-6" align="right">
                <button type="button" id="area_province_update_btn" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 开通省
                </button>
                <button type="button" id="area_city_update_btn" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 开通市
                </button>  
                
                <button type="button" id="area_district_update_btn" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 开通区
                </button>  
                
                <button type="button" id="area_street_update_btn" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 开通街道
                </button> 
                      
                <button type="button" id="region_add_btn" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>开通小区
                </button>
            </div>
            <div class="col-sm-6" align="right">
               	 选择小区：
               <%--  <select class="form-control" id="region_parent_id" style="margin-bottom: 0px;">
                    <option value="">---请选择---</option>
                    <c:forEach items="${recRegionList}" var="region" varStatus="ll">
                        <option value="${region.regionId}">${region.regionCnName}</option>
                    </c:forEach>
                </select> --%>
             <input type="hidden" id="region_parent_id" value="">   
           	<select   class="form-control" id="rec_privenc_id" name="rec_privenc_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
                <c:forEach items="${recAreaList}" var="area" varStatus="ll">
                    <option value="${area.areaId}" id="${area.isActive}">${area.areaCnName}</option>
                </c:forEach>
            </select>
             <select   class="form-control" id="rec_city_id" name="rec_city_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
            </select>
            <select  class="form-control" id="rec_district_id" name="rec_district_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
            </select>
            
            <select  class="form-control" id="rec_street_id" name="rec_street_id" style="margin-bottom: 0px; width: 100px;">
                <option value="">---请选择---</option>
            </select>
            </div>
        </div>
        <div id="msgTip"></div>
        <table id="table_region"
               class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>小区名称</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
    <!--/row-->
    
    <!--     省start -->
    <div id="area_province_update_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="area_update_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="area_update_dialog_title">开通-省</h3>
        </div>
        <div id="area_province_update_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="area_province_update_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <!--    省 end -->
    <!--     市 start -->
    <div id="area_city_update_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="area_city_update_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="area_city_update_dialog_title">开通-市</h3>
        </div>
        <div id="area_city_update_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="area_city_update_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <!--    市 end -->
    <!--    区 start -->
    <div id="area_district_update_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="area_district_update_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="area_district_update_dialog_title">开通-区</h3>
        </div>
        <div id="area_district_update_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="area_district_update_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <!--    市 end -->
    
    <!--    街道 start -->
    <div id="area_street_update_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="area_street_update_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="area_street_update_dialog_title">开通-街道</h3>
        </div>
        <div id="area_street_update_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="area_street_update_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <!--   街道 end -->
    
<!--     小区 start -->
    <div id="region_add_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="region_add_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="region_add_dialog_title">开通-小区信息</h3>
        </div>
        <div id="region_add_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="region_add_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <div id="region_modify_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="region_modify_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="region_modify_dialog_title">修改-小区信息</h3>
        </div>
        <div id="region_modify_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="region_modify_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
</div>
<script language="javascript">
    var regionTable = null;
    var flag="";
    $(function() {
        regionTable = $('#table_region').dataTable( {
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
                    "mData" : "regionId",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                {
                    "mData" : "regionCnName",
                    "sWidth" : "200px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "regionId",
                    "sClass" : "center",
                    "sWidth" : "80px",
                    "aTargets" : [ 2 ],
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var str='';
                        str += '<a href="javascript:void(0)" onclick="editRegion(\''
                        + full.regionId
                        + '\');">修改</a>';
                        return str;
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryRecyclingAdminRegion.do",
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
        	var rec_privenc_id 		= $("#rec_privenc_id").val();
        	var rec_city_id 		= $("#rec_city_id").val();
        	var rec_district_id 	= $("#rec_district_id").val();
        	var rec_street_id 		= $("#rec_street_id").val();
        	if(rec_privenc_id ==""){
        		$("#region_parent_id").val("");
        	}else if (rec_privenc_id != "" && rec_city_id ==""){
        		$("#region_parent_id").val(rec_privenc_id);
        	}else if (rec_privenc_id != "" && rec_city_id !="" && rec_district_id==""){
        		$("#region_parent_id").val(rec_city_id);
        	}else if (rec_privenc_id != "" && rec_city_id !="" && rec_district_id!="" && rec_street_id==""){
        		$("#region_parent_id").val(rec_district_id);
          	}else if (rec_privenc_id != "" && rec_city_id !="" && rec_district_id!="" && rec_street_id!=""){
        		$("#region_parent_id").val(rec_street_id);
        	}
            regionTable.fnDraw();
        };

        function getQueryFilter() {
            var searchStr = '';
            return searchStr;
        };

        $('[data-rel=tooltip]').tooltip();

        $("#region_parent_id").on("change",function(){
            query();
        });

       
        
        
        
        
       //转向添加页面 开通省
        $("#area_province_update_btn").on('click', function () {
            $("#area_province_update_dialog_content").load('<%=contextPath%>/admin/toAddRecyclingAdminProvince.do?type=0');
            $('#area_province_update_dialog').modal("show");
        });
        //保存省操作
        $("#area_province_update_dialog_save").on("click", function () {
            $("#area_province_update_area_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/updateArea.do",
                type: "post",
                data: {type:0},
                dataType: 'json',
                beforeSubmit: function () {
                    /*if ($("#region_cn_name").val() == '') {
                        $("#region_cn_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }*/
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#area_province_update_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminRegion.do?_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        });
        
        //打开开通市
        $("#area_city_update_btn").on('click', function () {
            $("#area_city_update_dialog_content").load('<%=contextPath%>/admin/toAddRecyclingAdminProvince.do?type=1');
            $('#area_city_update_dialog').modal("show");
        });
      //保存市操作
        $("#area_city_update_dialog_save").on("click", function () {
            $("#area_city_update_area_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/updateArea.do",
                data: {type:1},
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    /*if ($("#region_cn_name").val() == '') {
                        $("#region_cn_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }*/
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#area_city_update_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminRegion.do?_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        });
      
        //打开开通区
        $("#area_district_update_btn").on('click', function () {
            $("#area_district_update_dialog_content").load('<%=contextPath%>/admin/toAddRecyclingAdminProvince.do?type=2');
            $('#area_district_update_dialog').modal("show");
        });
      //保存区操作
        $("#area_district_update_dialog_save").on("click", function () {
            $("#area_district_update_area_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/updateArea.do",
                data: {type:2},
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    /*if ($("#region_cn_name").val() == '') {
                        $("#region_cn_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }*/
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#area_district_update_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminRegion.do?_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        });
        
      
        //打开 开通街道
        $("#area_street_update_btn").on('click', function () {
            $("#area_street_update_dialog_content").load('<%=contextPath%>/admin/toAddRecyclingAdminProvince.do?type=3');
            $('#area_street_update_dialog').modal("show");
        });
      //保存街道操作
        $("#area_street_update_dialog_save").on("click", function () {
            $("#area_street_update_area_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/updateArea.do",
                data: {type:3},
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    /*if ($("#region_cn_name").val() == '') {
                        $("#region_cn_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }*/
                },
                success: function (data, status) {
                  
                    if (data.type == "success") {
                        $('#area_street_update_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminRegion.do?_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                },
                error:function(){
                	alert("error");
                }
            });
        });
        
        
       //-----------------------------------------------------------------------------------// 
        
        
        
        //转向添加页面
        $("#region_add_btn").on('click', function () {
            $("#region_add_dialog_content").load('<%=contextPath%>/admin/toAddRecyclingAdminRegion.do');
            $('#region_add_dialog').modal("show");
        });


        //保存操作
        $("#region_add_dialog_save").on("click", function () {
            $("#region_add_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveRecyclingAdminRegion.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    if ($("#region_cn_name").val() == '') {
                        $("#region_cn_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#region_add_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminRegion.do?_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        });

        //保存修改
        $("#region_modify_dialog_save").on("click", function () {
            $("#region_add_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveRecyclingAdminRegion.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    if ($("#region_cn_name").val() == '') {
                        $("#region_cn_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#region_modify_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminRegion.do?_=" + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        });
        
        
        
        
        
      //查询条件JS ---------------------
        $('#rec_privenc_id').change(function(){ //省与市之间的级联
    		$.ajax({
    			type: "post",
    			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
    			data: {parentId:$("#rec_privenc_id").val(),selectType:'active'},
    			dataType: "json",
    			success: function(data){
    				var selOpt = $("#rec_city_id option");
    					selOpt.remove();
    				var selOpt1 = $("#rec_district_id option");
    					selOpt1.remove();
    				var selOpt2 = $("#rec_street_id option");
    					selOpt2.remove();
    				var sel = $("#rec_city_id");
    				sel.append("<option value=''>"+"---请选择---"+"</option>");
                    $.each(data, function(commentIndex, comment){
                    	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                    });
                    query();
                 },
                 error: function (){
                	 alert("ERROR");
                 }
    		});
    	});
    	
    	$('#rec_city_id').change(function(){ //市与区之间的级联
    		$.ajax({
    			type: "post",
    			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
    			data: {parentId:$("#rec_city_id").val(),selectType:'active'},
    			dataType: "json",
    			success: function(data){				
    				var selOpt = $("#rec_district_id option");
    					selOpt.remove();
    				var selOpt1 = $("#rec_street_id option");
    					selOpt1.remove();
    				var sel = $("#rec_district_id");
    				sel.append("<option value=''>"+"---请选择---"+"</option>");
                    $.each(data, function(commentIndex, comment){
                    	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                    });
                    query();
                 },
                 error: function (){
                	 alert("ERROR");
                 }
    		});
    		 
    	});
    	
    	
    	$('#rec_district_id').change(function(){ //区与街道之间的级联
    		$.ajax({
    			type: "post",
    			url: "<%=contextPath%>/admin/queryRecyclingAdminAreaJs.do",
    			data: {parentId:$("#rec_district_id").val(),selectType:'active'},
    			dataType: "json",
    			success: function(data){				
    				var selOpt = $("#rec_street_id option");
    					selOpt.remove();
    				var sel = $("#rec_street_id");
    				sel.append("<option value=''>"+"---请选择---"+"</option>");
                    $.each(data, function(commentIndex, comment){
                    	  sel.append("<option value='"+comment['areaId']+"' id='"+comment['isActive']+"' >"+comment['areaCnName']+"</option>");
                    });
                    
                    query();
                 },
                 error: function (){
                	 alert("ERROR");
                 }
    		});	
    	});
    	
    	
    	
    	
    	$('#rec_street_id').change(function(){ //街道与小区之间的级联
    		 query();
    	});
        
        
//         查询条件结束
    });

    function editRegion(region_id) {
        $("#region_modify_dialog_content").load('<%=contextPath%>/admin/toDetailRecyclingAdminRegion.do?region_id='+region_id);
        $('#region_modify_dialog').modal("show");
    }

    
    
    
</script>


