<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="row">
    <form id="weixinmenu_add_dialog_form" action="<%=contextPath%>/admin/saveWeixinMenu.do" class="form-horizontal" method="post" enctype="multipart/form-data">
        <div class="control-group">
            <label class="control-label" for="menu_name"><span style="color: red">*</span>菜单名称:</label>
            <div class="controls">
                <input id="menu_name" class="inputxt" name="menu_name" type="text" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="weixin_menu_pid"><span style="color: red">*</span>所属菜单:</label>
            <div class="controls">
                <select id="weixin_menu_pid" name="menu_pid">
                    <option value=0 selected>一级菜单</option>
                    <c:forEach items="${parent_ment}" var="menu">
                        <option  value="${menu.id}">${menu.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label"><span style="color: red">*</span>菜单事件类型:</label>
            <div class="controls">
                <div>
                    <label>
                        <input class="ace" type="radio" name="menu_type" value="click" checked>
                        <span class="lbl">click</span>
                    </label>
                    <label>
                        <input class="ace" type="radio" name="menu_type" value="view" >
                        <span class="lbl">view</span>
                    </label>
                </div>
            </div>
        </div>

        <div class="control-group" id="url">
            <label class="control-label" ><span style="color: red">*</span>菜单URL:</label>
            <div class="controls">
                <input id="menu_url" class="inputxt" name="menu_url" type="text" />
            </div>
        </div>

        <div class="control-group" id="key">
            <label class="control-label" ><span style="color: red">*</span>菜单关键字:</label>
            <div class="controls">
                <input id="menu_key" class="inputxt" name="menu_key" type="text" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" ><span style="color: red">*</span>排序:</label>
            <div class="controls">
                <input id="sort" class="inputxt" name="sort" type="text" />
            </div>
        </div>
        <input type="hidden" value="save" name="method" id="method" />
        <input type="hidden" value="${weixin_type}" name="weixin_type" id="weixin_type" />
    </form>
</div>
<script>
    //click事件与view事件切换，隐藏和现实文本框
    $("#url").hide();
    $("input[name='menu_type']").on("click",function(){
        if($(this).val()=='click'){
            $("#key").show();
            $("#url").hide();
        }else{
            $("#url").show();
            $("#key").hide();
        }
    })
</script>
