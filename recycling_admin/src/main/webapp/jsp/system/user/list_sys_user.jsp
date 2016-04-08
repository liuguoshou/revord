<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <div class="row-fluid">
        <div class="table-header">
            <button id="user_add_btn" class="btn btn-mini btn-success" data-toggle="modal">
                <i class="icon-ok bigger-120">新增</i>
            </button>
        </div>
        <table id="table_user"
               class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>用户名称</th>
                    <th>真实名字</th>
                    <th>是否有效</th>
                    <th>添加时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
    <!--/row-->
    <div id="user_add_dialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="user_add_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="user_add_dialog_title">新增-系统用户</h3>
        </div>
        <div id="user_add_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="user_add_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <div id="user_modify_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="user_modify_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="user_modify_dialog_title">修改-系统用户</h3>
        </div>
        <div id="user_modify_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="user_modify_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    
    <div id="user_area_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="user_area_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="user_modify_dialog_title">系统用户地域管理</h3>
        </div>
        
        <div id="user_area_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="user_area_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    
    
    
</div>
<script language="javascript">
    var oTable = null;
    var flag="";
    $(function() {
        oTable = $('#table_user').dataTable( {
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
                    "mData" : "adminUserId",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                {
                    "mData" : "userName",
                    "sWidth" : "100px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "realName",
                    "sWidth" : "100px",
                    "aTargets" : [ 2 ],
                    "bSortable" : false
                },{
                    "mData" : "isValid",
                    "aTargets" : [ 3 ],
                    "sWidth" : "100px",
                    "bSortable" : false,
                    "mRender":function(data, type, full){
                        if(data == 0){
                            return '<span style="color:red">无效</span>';
                        }else{
                            return '<span style="color:#1e90ff">有效</span>';
                        }
                    }
                },
                {
                    "mData" : "addTime",
                    "sWidth" : "100px",
                    "aTargets" : [ 4 ],
                    "bSortable" : false,
                    "mRender":function(data, type, full){
                        return TimeObjectUtil.formatterDateTime(new Date(data));
                    }
                },
                {
                    "mData" : "adminUserId",
                    "sClass" : "center",
                    "sWidth" : "80px",
                    "aTargets" : [ 5 ],
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var str='';
                        str += '<a href="javascript:void(0)" onclick="editUser(\''+ full.adminUserId + '\');">修改</a>';
                        str += '|<a href="javascript:void(0)" onclick="editArea(\''+ full.adminUserId + '\');">地域</a>';
                        str += '|<a href="javascript:void(0)" onclick="deleteUser(\''+ full.adminUserId + '\');">删除</a>';
                        return str;
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/querySystemUser.do",
            "fnServerData" : function(sSource, aoData,fnCallback) {
                $.ajax({
                    "dataType" : 'json',
                    "type" : "get",
                    "url" : sSource,
                    "data" : aoData,
                    "success" : fnCallback
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
        }
        ;

        function getQueryFilter() {
            var searchStr = '';
            return searchStr;
        }
        ;

        $('[data-rel=tooltip]').tooltip();

        $("#user_add_btn").on('click', function() {
            $("#user_add_dialog_content").load('<%=contextPath%>/admin/toAddSystemUser.do');
            $('#user_add_dialog').modal("show");
        });

        $("#user_add_dialog_save").on('click', function() {
            $("#user_add_dialog_form").ajaxSubmit({
                url : "<%=contextPath%>/admin/saveSystemUser.do",
                type : "post",
                dataType : 'json',
                beforeSubmit:function(){
                    if($("#user_name").val()==''){
                        $("#user_name").attr("placeholder","用户名称不能为空！");
                        return false;
                    }
                    if($("#user_realname").val()==''){
                        $("#user_realname").attr("placeholder","真实名字不能为空！");
                        return false;
                    }
                    if($("#user_pwd").val()==''){
                        $("#user_pwd").attr("placeholder","用户密码不能为空！");
                        return false;
                    }
                    if($("#user_pwd1").val()==''){
                        $("#user_pwd1").attr("placeholder","确认密码不能为空！");
                        return false;
                    }
                    if($("#user_pwd1").val() != $("#user_pwd").val()){
                        $("#user_pwd1").attr("placeholder","两次输入的密码不一致！");
                        return false;
                    }
                },
                success : function(data, status) {
                    alert(data.msg);
                    if (data.type=="success") {
                        $('#user_add_dialog').modal("hide");
                        oTable.fnDraw(false);
                    }
                }
            });
        });

        $("#user_modify_dialog_save").on('click', function() {
            $("#user_modify_dialog_form").ajaxSubmit({
                url : "<%=contextPath%>/admin/saveSystemUser.do",
                type : "post",
                dataType : 'json',
                beforeSubmit:function(){
                    if($("#user_name").val()==''){
                        $("#user_name").attr("placeholder","用户名称不能为空！");
                        return false;
                    }
                    if($("#user_realname").val()==''){
                        $("#user_realname").attr("placeholder","真实名字不能为空！");
                        return false;
                    }
                    if($("#user_pwd").val()==''){
                        $("#user_pwd").attr("placeholder","用户密码不能为空！");
                        return false;
                    }
                    if($("#user_pwd1").val()==''){
                        $("#user_pwd1").attr("placeholder","确认密码不能为空！");
                        return false;
                    }
                    if($("#user_pwd1").val() != $("#user_pwd").val()){
                        $("#user_pwd1").attr("placeholder","两次输入的密码不一致！");
                        return false;
                    }
                },
                success : function(data, status) {
                    alert(data.msg);
                    if (data.type == 'success') {
                        $('#user_modify_dialog').modal("hide");
                        oTable.fnDraw(false);
                    }
                }
            });
        });
        
        
        //设置用户所能查看的权限
        $("#user_area_dialog_save").on('click', function() {
        	 var regionIds=[];
             $("[name^='u_region']:checked").each(function(){
                 regionIds.push($(this).val());
             })
             $("#userAreaIds").val(regionIds);
            $("#user_area_add_dialog_form").ajaxSubmit({
                url : "<%=contextPath%>/admin/saveSystemUserArea.do",
                type : "post",
                dataType : 'json',
                beforeSubmit:function(){
                    if($("#user_area_all").prop('checked')){ //可以查看所有地域的信息
                        return true;
                    }else{                   	
                    	  
                        if($("#user_realname").val()==''){
                            $("#user_realname").attr("placeholder","真实名字不能为空！");
                            return false;
                        }
                    }
                    
                  
                    
                },
                success : function(data, status) {
                    alert(data.msg);
                    if (data.type == 'success') {
                        $('#user_modify_dialog').modal("hide");
                        oTable.fnDraw(false);
                    }
                },
                error :function (data){
                	alert(data)
                }
            });
        });
        
        
        
    })
    function deleteUser(user_id) {
        var c = confirm("您确认要删除么？")
        if (c) {
            $.get("<%=contextPath%>/admin/deleteSystemUser.do?user_id=" + user_id,
                    function(result) {
                        alert(result.msg);
                        oTable.fnDraw(false);
                }, "json");
        }
    }


    function editUser(user_id) {
        $("#user_modify_dialog_content").load('<%=contextPath%>/admin/toUpdateSystemUser.do?user_id='+user_id);
        $('#user_modify_dialog').modal("show");
    }
    
    function editArea(user_id) {
        $("#user_area_dialog_content").load('<%=contextPath%>/admin/toUpdateSystemUserArea.do?user_id='+user_id);
        $('#user_area_dialog').modal("show");
    }

</script>


