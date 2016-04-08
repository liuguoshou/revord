<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <input type="hidden" id="detail_region_id" value="${region_id}"/>
    <input type="hidden" id="detail_categroy_id" value="${categroy_id}"/>
    <div class="row-fluid">
        <div align="right"><h4>回收总花费金额：<span id="totalStatisticsMoney" style="color: red">${totalStatisticsMoney}</span>元</h4></div>
        <table id="table_admin_order_detail"
               class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>序号</th>
                <th>回收人员</th>
                <th>小区名称</th>
                <th>物品</th>
                <th>单价/单位</th>
                <th>个数</th>
                <th>回收花费(元)</th>
                <th>回收时间</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <!--PAGE CONTENT ENDS HERE-->
    </div>
</div>
<script language="javascript">
    var detail_count_table = null;
    var flag="";
    $(function() {
        detail_count_table = $('#table_admin_order_detail').dataTable( {
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
                    "mData" : "real_name",
                    "sWidth" : "28px",
                    "bSortable" : false,
                    "aTargets" : [ 0 ]
                },
                {
                    "mData" : "real_name",
                    "sWidth" : "60px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "region_cn_name",
                    "sWidth" : "60px",
                    "aTargets" : [ 2 ],
                    "bSortable" : false
                },{
                    "mData" : "category_name",
                    "aTargets" : [ 3 ],
                    "sWidth" : "30px",
                    "bSortable" : false
                },{
                    "mData" : "price",
                    "aTargets" : [ 4 ],
                    "sWidth" : "30px",
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        return data+"元/"+full.unit;
                    }
                },{
                    "mData" : "count",
                    "aTargets" : [ 5 ],
                    "sWidth" : "30px",
                    "bSortable" : false
                },{
                    "mData" : "price",
                    "aTargets" : [ 6 ],
                    "sWidth" : "30px",
                    "bSortable" : false,
                    "mRender" : function(data, type,full) {
                        var result=data*full.count;
                        return result.toFixed(2);
                    }
                },
                {
                    "mData" : "update_date",
                    "sWidth" : "80px",
                    "aTargets" : [ 7 ],
                    "bSortable" : false,
                    "mRender":function(data, type, full){
                        return TimeObjectUtil.formatterDateTime(new Date(data));
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryTrxOrderHistoryDetailRecyclingAdmin.do",
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
                    "name" : "region_id",
                    "value" : $("#detail_region_id").val()
                },{
                    "name" : "categroy_id",
                    "value" : $("#detail_categroy_id").val()
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
            detail_count_table.fnDraw();
        };

        function getQueryFilter() {
            var searchStr = '';
            return searchStr;
        };

        $('[data-rel=tooltip]').tooltip();
    })
</script>


