<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="row">
    <form id="menu_add_dialog_form" action="<%=contextPath%>/admin/saveSystemMenu.do" class="form-horizontal" method="post" enctype="multipart/form-data">
        <div class="control-group">
            <label class="control-label" for="parentMenu"><span style="color: red">*</span>菜单名称:</label>
            <div class="controls">
                <input id="menu_name" class="inputxt" name="menu_name" type="text" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="menu_url"><span style="color: red">*</span>菜单地址:</label>
            <div class="controls">
                <input type="text" class="inputxt" name="menu_url" id="menu_url"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="parentMenu"><span style="color: red">*</span>父级菜单:</label>
            <div class="controls">
                <select class="form-control" name="parentMenu" id="parentMenu">
                    <option value="0">一级菜单</option>
                    <c:forEach items="${adminMenusList}" var="menu">
                        <option value="${menu.menuId}">${menu.menuName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" ><span style="color: red">*</span>是否有效:</label>
            <div class="controls">
                <div>
                    <label>
                        <input class="ace" type="radio" name="menu_isvalid" value="1" checked>
                        <span class="lbl">有效</span>
                    </label>
                    <label>
                        <input class="ace" type="radio" name="menu_isvalid" value="0" >
                        <span class="lbl">无效</span>
                    </label>
                </div>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" ><span style="color: red">*</span>是否显示到菜单:</label>
            <div class="controls">
                <div>
                    <label>
                        <input class="ace" type="radio" name="menu_isdisplay" value="1" checked>
                        <span class="lbl">显示</span>
                    </label>
                    <label>
                        <input class="ace" type="radio" name="menu_isdisplay" value="0" >
                        <span class="lbl">不显示</span>
                    </label>
                </div>
            </div>
        </div>
        <input type="hidden" name="method" value="save"/>
    </form>

</div>
