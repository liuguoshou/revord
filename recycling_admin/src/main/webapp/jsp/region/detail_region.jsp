<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<form id="region_add_dialog_form" action="<%=contextPath%>/admin/saveSystemUser.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>选择要开通的区域：</label>
        <div class="controls">
            <select class="form-control" id="region_parent_id" name="region_parent_id" style="margin-bottom: 0px;">
                <option value="0">---请选择---</option>
                <c:forEach items="${recRegionList}" var="region" varStatus="ll">
                    <option value="${region.regionId}" ${region.parentId == region.regionId ? "selected" : ""}>${region.regionCnName}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>开通小区名称：</label>
        <div class="controls">
            <input id="region_cn_name" class="inputxt" name="region_cn_name" type="text" value="${region.regionCnName}"/>
        </div>
    </div>

    <input type="hidden" name="method" value="update"/>
    <input type="hidden" name="create_date" value="${region.createDate}"/>
    <input type="hidden" name="region_id" value="${region.regionId}"/>
</form>