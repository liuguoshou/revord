<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<form id="user_modify_dialog_form" action="<%=contextPath%>/admin/saveSystemUser.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>用户姓名:</label>
        <div class="controls">
            ${recUser.realName}
        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>手机号:</label>
        <div class="controls">
            ${recUser.mobile}
        </div>
    </div>

   <%--  <div class="control-group">
        <label class="control-label" ><span style="color: red">*</span>区域:</label>
        <div class="controls">
            ${recUser.region.parentRegion.regionCnName}
        </div>
    </div> --%>

    <div class="control-group">
        <label class="control-label"><span style="color: red">*</span>小区名称:</label>
        <div class="controls">
            ${recUser.region.regionCnName}
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" ><span style="color: red">*</span>地址:</label>
        <div class="controls">
            ${recUser.address}
        </div>
    </div>
    <input type="hidden" name="user_id" value="${adminUser.adminUserId}"/>
</form>