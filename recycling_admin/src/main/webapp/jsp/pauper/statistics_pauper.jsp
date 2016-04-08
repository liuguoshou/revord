<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <input type="hidden" id="statistics_collect_id" value="${collect_id}"/>
    <div class="row-fluid">
        <div align="right"><h4>回收总花费金额：<span id="totalMoney" style="color: red"></span>元</h4></div>
        <table id="table_admin_order_detail" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>序号</th>
                <th>回收人员</th>
                <th>小区名称</th>
                <th>物品分类</th>
                <th>物品</th>
                <th>个数</th>
                <th>单价/单位</th>
                <th>价格(元)</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
</div>
<script language="javascript">
    var statistics_table = null;
    var flag="";
    $(function() {
        statistics_table = $('#table_admin_order_detail').dataTable( {
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
                    "mRender" : function(data, type, full){
                        return full.price+"元/"+data;
                    }
                },{
                    "mData" : "money",
                    "aTargets" : [ 7 ],
                    "sWidth" : "80px",
                    "bSortable" : false,
                    "mRender" : function(data, type, full){
                        return data.toFixed(2);
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryRecyclingAdminStatustics.do",
            "fnServerData" : function(sSource, aoData,fnCallback) {
                $.ajax({
                    "dataType" : 'json',
                    "type" : "get",
                    "url" : sSource,
                    "data" : aoData,
                    "success" : function(data){
                        $("#totalMoney").html(data['totalMoney'].toFixed(2));
                        fnCallback(data);
                    }
                });
            },
            "fnServerParams" : function(aoData) {//重要设置，可通过此设置添加额外查询参数
                aoData.push({
                    "name" : "collect_id",
                    "value" : $("#statistics_collect_id").val()
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
            statistics_table.fnDraw();
        };

        function getQueryFilter() {
            var searchStr = '';
            return searchStr;
        };

        $('[data-rel=tooltip]').tooltip();
    })
</script>


