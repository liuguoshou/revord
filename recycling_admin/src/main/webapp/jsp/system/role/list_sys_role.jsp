<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <div class="row-fluid">
        <div class="table-header">
            <button id="role_add_btn" class="btn btn-mini btn-success" data-toggle="modal">
                <i class="icon-ok bigger-120">新增</i>
            </button>
        </div>
        <table id="table_role"
               class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>序号</th>
                <th>角色名称</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
    <!--/row-->
    <div id="role_add_dialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="role_add_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="role_add_dialog_title">新增-系统角色</h3>
        </div>
        <div id="role_add_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="role_add_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <div id="role_modify_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="role_modify_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="role_modify_dialog_title">修改-系统角色</h3>
        </div>
        <div id="role_modify_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="role_modify_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
</div>
<script language="javascript">
    var oTable = null;
    var flag="";
    $(function() {
        oTable = $('#table_role').dataTable( {
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
                    "mData" : "adminRoleId",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                {
                    "mData" : "roleName",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "adminRoleId",
                    "sClass" : "center",
                    "aTargets" : [ 2 ],
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var str='';
                        str += '<a href="javascript:void(0)" onclick="editRole(\''
                        + full.adminRoleId
                        + '\');">修改</a>';
                        str += '|<a href="javascript:void(0)" onclick="deleteRole(\''
                        + full.adminRoleId
                        + '\');">删除</a>';
                        return str;
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/querySystemRole.do",
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

        $("#role_add_btn").on('click', function() {
            $("#role_add_dialog_content").load('<%=contextPath%>/admin/toAddSystemRole.do');
            $('#role_add_dialog').modal("show");
        });

        $("#role_add_dialog_save").on('click', function() {
            //父菜单
            var parent_menu=[];
            $("[name^='parentMenu']:checked").each(function(){
                parent_menu.push($(this).val());
            })
            //子菜单
            var sub_menu=[];
            $("[name^='subMenu']:checked").each(function(){
                sub_menu.push($(this).val());
            })
            $("#parent_menu").val(parent_menu);
            $("#sub_menu").val(sub_menu);
            $("#role_add_dialog_form").ajaxSubmit({
                url : "<%=contextPath%>/admin/saveSystemRole.do",
                type : "post",
                dataType : 'json',
                beforeSubmit:function(){
                },
                success : function(data, status) {
                    alert(data.msg);
                    if (data.type=="success") {
                        $('#role_add_dialog').modal("hide");
                        oTable.fnDraw(false);
                    }
                }
            });
        });

        $("#role_modify_dialog_save").on('click', function() {
            //父菜单
            var parent_menu=[];
            $("[name^='updateParentMenu']:checked").each(function(){
                parent_menu.push($(this).val());
            })
            //子菜单
            var sub_menu=[];
            $("[name^='updateSubMenu']:checked").each(function(){
                sub_menu.push($(this).val());
            })
            $("#update_parent_menu").val(parent_menu);
            $("#update_sub_menu").val(sub_menu);
            $("#role_modify_dialog_form").ajaxSubmit({
                url : "<%=contextPath%>/admin/saveSystemRole.do",
                type : "post",
                dataType : 'json',
                beforeSubmit:function(){
                },
                success : function(data, status) {
                    alert(data.msg);
                    if (data.type == 'success') {
                        $('#role_modify_dialog').modal("hide");
                        oTable.fnDraw(false);
                    }
                }
            });
        });
    })
    function deleteRole(roleId) {
        var c = confirm("您确认要删除么？")
        if (c) {
            $.get("<%=contextPath%>/admin/deleteSystemRole.do?roleId=" + roleId,
                    function(result) {
                        alert(result.msg);
                        oTable.fnDraw(false);
                    }, "json");
        }
    }


    function editRole(roleId) {
        $("#role_modify_dialog_content").load('<%=contextPath %>/admin/toUpdateSystemRole.do?roleId='+roleId);
        $('#role_modify_dialog').modal("show");
    }

</script>


