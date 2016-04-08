<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <div class="row-fluid">
        <div class="table-header">
            <div class="col-sm-6" >
                选择区域：
                <select class="form-control" id="region_parent_id" name="region_parent_id" style="margin-bottom: 0px;width: 160px;" >
                    <option value="">---请选择---</option>
                    <c:forEach items="${recRegionList}" var="region" varStatus="ll">
                        <option value="${region.areaId}">${region.areaCnName}</option>
                    </c:forEach>
                </select>
                选择小区：
                <select class="form-control" id="region_id" name="region_id" style="margin-bottom: 0px;width: 160px;">
                    <option value="">---请选择---</option>
                </select>
                选择分类：
                <select class="form-control" id="categroy_parent_id" name="categroy_parent_id" style="margin-bottom: 0px;width: 160px;">
                    <option value="">---请选择---</option>
                    <c:forEach items="${recCategoryList}" var="categroy" varStatus="ll">
                        <option value="${categroy.categoryId}">${categroy.categoryName}</option>
                    </c:forEach>
                </select>
                选择物品：
                <select class="form-control" id="categroy_id" name="categroy_id" style="margin-bottom: 0px;width: 160px;">
                    <option value="">---请选择---</option>
                </select>
            </div>
            <div class="col-sm-6">
                开始时间：
                <input type="text" style="width: 145px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="from_date" name="from_date"  value="${from_date}" />
                结束时间：
                <input type="text" style="width: 145px;" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  id="to_date" name="to_date"  value="${to_date}" />
                <button type="button" id="search" class="btn btn-purple btn-sm">
                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                    查询
                </button>
            </div>
        </div>
        <table id="table_admin_order_count"
               class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>区域</th>
                    <th>小区名称</th>
                    <th>分类名称</th>
                    <th>物品</th>
                    <th>个数</th>
                    <th>单价/单位</th>
                    <th>回收花费(元)</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
    <!--/row-->
    <div id="order_detail_modify_dialog" style="width: 70%;margin-left: -400px;" class="modal hide fade" tabindex="-1" role="dialog"  aria-labelledby="order_detail_modify_dialog_title" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
            <h3 id="order_detail_modify_dialog_title">查看-回收信息</h3>
        </div>
        <div id="order_detail_modify_dialog_content" class="modal-body">
            <p>弹出层…</p>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        </div>
    </div>
</div>
<script language="javascript">
    var count_table = null;
    var flag="";
    $(function() {
        count_table = $('#table_admin_order_count').dataTable( {
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
                    "mData" : "rpid",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                {
                    "mData" : "regionName",
                    "sWidth" : "200px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "region_cn_name",
                    "sWidth" : "200px",
                    "aTargets" : [ 2 ],
                    "bSortable" : false
                },{
                    "mData" : "categroyName",
                    "aTargets" : [ 3 ],
                    "sWidth" : "200px",
                    "bSortable" : false
                },{
                    "mData" : "category_name",
                    "aTargets" : [ 4 ],
                    "sWidth" : "200px",
                    "bSortable" : false
                },{
                    "mData" : "total",
                    "aTargets" : [ 5 ],
                    "sWidth" : "200px",
                    "bSortable" : false
                },{
                    "mData" : "unit",
                    "aTargets" : [ 6 ],
                    "sWidth" : "200px",
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        return full.price+"元/"+data;
                    }
                },
                {
                    "mData" : "price",
                    "aTargets" : [ 7 ],
                    "sWidth" : "200px",
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var result=data*full.total;
                        return result.toFixed(2);
                    }
                },
                {
                    "mData" : "cpid",
                    "sClass" : "center",
                    "sWidth" : "80px",
                    "aTargets" : [ 8 ],
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var totalStatisticsMoney=(full.price*full.total).toFixed(2);
                        var str='';
                        str += '<a href="javascript:void(0)" onclick="detail_order(\''
                        + full.region_id+"','"+full.category_id+"','"
                        + totalStatisticsMoney
                        + '\');">详情</a>';
                        return str;
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryRecyclingAdminStatustics.do",
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
                },{
                    "name" : "categroy_id",
                    "value" : $("#categroy_id").val()
                },{
                    "name" : "categroy_parent_id",
                    "value" : $("#categroy_parent_id").val()
                },{
                    "name" : "from_date",
                    "value" : $("#from_date").val()
                },{
                    "name" : "to_date",
                    "value" : $("#to_date").val()
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
            count_table.fnDraw();
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

        $("#categroy_parent_id").on("change",function(){
            $("#categroy_id").html('<option value="">---请选择---</option>');
            $.get("<%=contextPath%>/admin/queryRecyclingAdminSubCategroy.do?categroy_id="+$(this).val(), function(result) {
                $.each(result, function(i, val) {
                    $("#categroy_id").append(
                            "<option value='"+this.categoryId+"'>" + this.categoryName
                            + "</option>");
                })
            }, "json");
        })


        $("#search").on("click",function(){
            query();
        })
    })

    function detail_order(region_id,categroy_id,totalStatisticsMoney) {
        $("#order_detail_modify_dialog_content").load('<%=contextPath%>/admin/toDetailRecyclingAdminStatustics.do?region_id='+region_id+'&categroy_id='+categroy_id+'&totalStatisticsMoney='+totalStatisticsMoney);
        $('#order_detail_modify_dialog').modal("show");
    }

</script>


