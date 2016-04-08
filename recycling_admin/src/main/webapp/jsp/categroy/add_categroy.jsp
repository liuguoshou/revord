<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<form id="categroy_add_dialog_form" action="<%=contextPath%>/admin/saveRecyclngAdminCategroy.do" class="form-horizontal" method="post" enctype="multipart/form-data">

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>分类名称：</label>
        <div class="controls">
            <input id="category_name" class="inputxt" name="category_name" type="text" />
        </div>
    </div>


    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>所属分类：</label>
        <div class="controls">
            <select class="form-control" id="categroy_pid" name="categroy_parent_id" style="margin-bottom: 0px;">
                <option value="0">一级分类</option>
                <c:forEach items="${recCategoryList}" var="categroy" varStatus="ll">
                    <option value="${categroy.categoryId}">${categroy.categoryName}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>二级分类：</label>
        <div class="controls">
            <select class="form-control" id="categroy_subid" name="categroy_sub_id" style="margin-bottom: 0px;">
                <option>---请选择---</option>
            </select>
        </div>
    </div>


    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>分类价格：</label>
        <div class="controls">
            <input id="price" class="inputxt" name="price" type="text" />
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>分类单位：</label>
        <div class="controls">
            <input id="unit" class="inputxt" name="unit" type="text" />
        </div>
    </div>


    <div class="control-group">
        <label class="control-label" ><span style="color: red">*</span>是否有效:</label>
        <div class="controls">
            <div>
                <label>
                    <input class="ace" type="radio" name="is_online" value="1" checked>
                    <span class="lbl">上线</span>
                </label>
                <label>
                    <input class="ace" type="radio" name="is_online" value="0" >
                    <span class="lbl">未上线</span>
                </label>
            </div>
        </div>
    </div>
    <input type="hidden" name="method" value="save"/>
</form>
<script type="text/javascript">
    $(function(){
        $("#categroy_pid").on("change",function(){
            $("#categroy_subid").html('<option value="">---请选择---</option>');
            $.get("<%=contextPath%>/admin/queryRecyclingAdminSubCategroy.do?categroy_id="+$(this).val(), function(result) {
                $.each(result, function(i, val) {
                    $("#categroy_subid").append(
                            "<option value='"+this.categoryId+"'>" + this.categoryName
                            + "</option>");
                })
            }, "json");
        })
    })
</script>