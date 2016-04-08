<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="container-fluid" id="main-container">
    <input type="hidden" id="history_collect_id" value="${collect_id}"/>
    <div class="row-fluid">
        <div class="table-header">
        </div>
        <table id="table_admin_order_detail_history"
               class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>序号</th>
                <th>回收人员</th>
                <th>小区名称</th>
                <th>物品</th>
                <th>单价/单位</th>
                <th>个数</th>
                <th>交易价格(元)</th>
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
    var history_table = null;
    var flag="";
    $(function() {
        history_table = $('#table_admin_order_detail_history').dataTable( {
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
                    "sWidth" : "40px",
                    "aTargets" : [ 1 ],
                    "bSortable" : false
                },
                {
                    "mData" : "region_cn_name",
                    "sWidth" : "40px",
                    "aTargets" : [ 2 ],
                    "bSortable" : false
                },{
                    "mData" : "category_name",
                    "aTargets" : [ 3 ],
                    "sWidth" : "40px",
                    "bSortable" : false
                },{
                    "mData" : "price",
                    "aTargets" : [ 4 ],
                    "sWidth" : "40px",
                    "bSortable" : false,
                    "mRender" : function(data, type, full){
                        return data+"元/"+full.unit;
                    }
                },{
                    "mData" : "count",
                    "aTargets" : [ 5 ],
                    "sWidth" : "40px",
                    "bSortable" : false
                },{
                    "mData" : "price",
                    "aTargets" : [ 6 ],
                    "sWidth" : "40px",
                    "bSortable" : false,
                    "mRender" : function(data, type, full){
                        var result=data*full.count;
                        return result.toFixed(2);
                    }
                },{
                    "mData" : "update_date",
                    "aTargets" : [ 7 ],
                    "sWidth" : "80px",
                    "bSortable" : false,
                    "mRender" : function(data, type, full){
                        return TimeObjectUtil.formatterDateTime(new Date(data));
                    }
                } ],
            "sAjaxSource" : "<%=contextPath %>/admin/queryPauperDetailRecyclingAdmin.do",
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
                    "name" : "collect_id",
                    "value" : $("#history_collect_id").val()
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
            history_table.fnDraw();
        };

        function getQueryFilter() {
            var searchStr = '';
            return searchStr;
        };

        $('[data-rel=tooltip]').tooltip();
    })
</script>


