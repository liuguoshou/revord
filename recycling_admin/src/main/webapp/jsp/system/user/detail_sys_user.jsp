<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<form id="user_modify_dialog_form" action="<%=contextPath%>/admin/saveSystemUser.do" class="form-horizontal" method="post" enctype="multipart/form-data">
    <div class="control-group">
        <label class="control-label" for="user_name"><span style="color: red">*</span>用户名称:</label>
        <div class="controls">
            <input id="user_name" class="inputxt" name="user_name" type="text" value="${adminUser.userName}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="user_realname"><span style="color: red">*</span>真实名字:</label>
        <div class="controls">
            <input type="text" class="inputxt" name="user_realname" id="user_realname" value="${adminUser.realName}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="user_name"><span style="color: red">*</span>用户密码:</label>
        <div class="controls">
            <input id="user_pwd" class="inputxt" name="user_pwd" type="password" value="${adminUser.password}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="user_pwd1"><span style="color: red">*</span>确认密码:</label>
        <div class="controls">
            <input type="text" class="inputxt" name="user_pwd1" id="user_pwd1" value="${adminUser.password}"/>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="user_name"><span style="color: red">*</span>是否有效:</label>
        <div class="controls">
            <div>
                <label>
                    <input class="ace" type="radio" name="user_isvalid" value="1" ${adminUser.isValid == 1 ? "checked" : ""}>
                    <span class="lbl">有效</span>
                </label>
                <label>
                    <input class="ace" type="radio" name="user_isvalid" value="0" ${adminUser.isValid == 0 ? "checked" : ""}>
                    <span class="lbl">无效</span>
                </label>
            </div>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="user_realname"><span style="color: red">*</span>用户角色:</label>
        <div class="controls">
            <select id="role" name="role">
                <option value="0">-----请选择-----</option>
                <c:forEach items="${adminRoleList}" var="role">
                    <option value="${role.adminRoleId}" ${roleId == role.adminRoleId ? "selected" : ""}>${role.roleName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <input type="hidden" name="method" value="update"/>
    <input type="hidden" name="user_id" value="${adminUser.adminUserId}"/>
</form>