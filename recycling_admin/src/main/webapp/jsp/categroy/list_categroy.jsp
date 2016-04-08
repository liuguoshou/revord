<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <div class="row-fluid">
        <div class="table-header">
            <div class="col-sm-6" align="right">
                选择分类：
                <select class="form-control" id="categroy_parent_id" style="margin-bottom: 0px;">
                    <option value="">---请选择---</option>
                    <c:forEach items="${recCategoryList}" var="categroy" varStatus="ll">
                        <option value="${categroy.categoryId}">${categroy.categoryName}</option>
                    </c:forEach>
                </select>
                <button type="button" id="categroy_add_btn" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                    新增
                </button>
            </div>
        </div>
        <div id="msgTip"></div>
        <table id="table_categroy"
               class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>物品名称</th>
                    <th>价格</th>
                    <th>单位</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
    <!--/row-->
    <div id="categroy_add_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="categroy_add_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="categroy_add_dialog_title">新增-分类信息</h3>
        </div>
        <div id="categroy_add_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="categroy_add_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
    <div id="categroy_modify_dialog" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="categroy_modify_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="categroy_modify_dialog_title">修改-分类信息</h3>
        </div>
        <div id="categroy_modify_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="categroy_modify_dialog_save" class="btn btn-primary">保存</button>
        </div>
    </div>
</div>
<script language="javascript">
    var oTable = null;
    var flag="";
    $(function() {
        oTable = $('#table_categroy').dataTable( {
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
                    "mData" : "categoryId",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                {
                    "mData" : "categoryName",
                    "sWidth" : "200px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "price",
                    "sWidth" : "200px",
                    "aTargets" : [ 2 ],
                    "bSortable" : false
                },
                {
                    "mData" : "unit",
                    "sWidth" : "200px",
                    "aTargets" : [ 3 ],
                    "bSortable" : false
                },
                {
                    "mData" : "categoryId",
                    "sClass" : "center",
                    "sWidth" : "80px",
                    "aTargets" : [ 4 ],
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var str='';
                        str += '<a href="javascript:void(0)" onclick="editCategroy(\''
                        + full.categoryId
                        + '\');">修改</a>';
                        return str;
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryRecyclingAdminCategroy.do",
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
                    "name" : "categroy_parent_id",
                    "value" : $("#categroy_parent_id").val()
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

        $("#categroy_parent_id").on("change",function(){
            query();
        })

        //转向添加页面
        $("#categroy_add_btn").on('click', function () {
            $("#categroy_add_dialog_content").load('<%=contextPath%>/admin/toAddRecyclingAdminCategroy.do');
            $('#categroy_add_dialog').modal("show");
        });


        //保存操作
        $("#categroy_add_dialog_save").on("click", function () {
            $("#categroy_add_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveRecyclngAdminCategroy.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    if ($("#category_name").val() == '') {
                        $("#category_name").attr("placeholder", "分类名称不能为空！");
                        return false;
                    }

                    if ($("#price").val() == '') {
                        $("#price").attr("placeholder", "价格不能为空！");
                        return false;
                    }

                    if ($("#unit").val() == '') {
                        $("#unit").attr("placeholder", "单位不能为空！");
                        return false;
                    }

                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#categroy_add_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminCategroy.do?_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })

        //保存修改
        $("#categroy_modify_dialog_save").on("click", function () {
            $("#categroy_modify_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveRecyclngAdminCategroy.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    if ($("#category_name").val() == '') {
                        $("#category_name").attr("placeholder", "分类名称不能为空！");
                        return false;
                    }

                    if ($("#price").val() == '') {
                        $("#price").attr("placeholder", "价格不能为空！");
                        return false;
                    }

                    if ($("#unit").val() == '') {
                        $("#unit").attr("placeholder", "单位不能为空！");
                        return false;
                    }
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#categroy_modify_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/toRecyclingAdminCategroy.do?_=" + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })
    })

    function editCategroy(categroy_id) {
        $("#categroy_modify_dialog_content").load('<%=contextPath%>/admin/toUpdateRecyclingAdminCategroy.do?categroy_id='+categroy_id);
        $('#categroy_modify_dialog').modal("show");
    }

</script>


