<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="row">
            <p align="right"><button class="btn btn-primary" id="menu_add_btn">添加菜单</button></p>
            <div class="col-sm-6" style="margin-left: 100px">
                <div class="dd" id="nestable">
                    <c:forEach items="${sysMenuList}" var="menu" varStatus="la">
                        <ol class="dd-list">
                            <li class="dd-item " data-id="5">
                            <div class="dd-handle">
                            <span>${menu.menuName}</span>
                            <span class="orange">${menu.menuUrl}</span>
                            <c:if test="${menu.menuDisplay==0}">
                                <span style="color:red">否</span>
                            </c:if>
                            <c:if test="${menu.menuDisplay==1}">
                                <span style="color:#1e90ff">是</span>
                            </c:if>
                            <c:if test="${menu.isValid==0}">
                                <span style="color:red">无效</span>
                            </c:if>
                            <c:if test="${menu.isValid==1}">
                                <span style="color:#1e90ff">有效</span>
                            </c:if>
                            <div class="pull-right action-buttons">
                                <a class="blue" href="#" onclick="editMenu(${menu.menuId})">
                                    <i class="ace-icon fa fa-pencil bigger-130"></i>
                                    修改
                                </a>

                                <a class="red" href="#" onclick="deleteMenu(${menu.menuId})">
                                    <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                    删除
                                </a>
                            </div>
                        </div>
                            <ol class="dd-list">
                                <c:forEach items="${menu.listSubMenu}" var="submenu" varStatus="ll">
                                    <li class="dd-item" data-id="${ll.count}">
                                        <div class="dd-handle">
                                            ${submenu.menuName}
                                            <span class="orange">${submenu.menuUrl}</span>
                                            <c:if test="${submenu.menuDisplay==0}">
                                                <span style="color:red">否</span>
                                            </c:if>
                                            <c:if test="${submenu.menuDisplay==1}">
                                                <span style="color:#1e90ff">是</span>
                                            </c:if>
                                            <c:if test="${submenu.isValid==0}">
                                                <span style="color:red">无效</span>
                                            </c:if>
                                            <c:if test="${submenu.isValid==1}">
                                                <span style="color:#1e90ff">有效</span>
                                            </c:if>
                                                <div class="pull-right action-buttons">
                                                    <a class="blue" href="#" onclick="editMenu(${submenu.menuId})">
                                                        <i class="ace-icon fa fa-pencil bigger-130"></i>
                                                        修改
                                                    </a>

                                                    <a class="red" href="#" onclick="deleteMenu(${submenu.menuId})">
                                                        <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                                        删除
                                                    </a>
                                                </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ol>
                            </li>
                        </ol>
                    </c:forEach>
                </div>
            </div>
            <div class="vspace-16-sm"></div>
            <div id="menu_add_dialog" class="modal hide fade" tabindex="-1" menu="dialog" aria-labelledby="menu_add_dialog_title" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="menu_add_dialog_title">新增-系统菜单</h3>
                </div>
                <div id="menu_add_dialog_content" class="modal-body">
                    <p>弹出层…</p>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                    <button id="menu_add_dialog_save" class="btn btn-primary">保存</button>
                </div>
            </div>
            <div id="menu_modify_dialog" class="modal hide fade" tabindex="-1" menu="dialog"  aria-labelledby="menu_modify_dialog_title" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                    <h3 id="menu_modify_dialog_title">修改-系统菜单</h3>
                </div>
                <div id="menu_modify_dialog_content" class="modal-body">
                    <p>弹出层…</p>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                    <button id="menu_modify_dialog_save" class="btn btn-primary">保存</button>
                </div>
            </div>
        </div><!-- PAGE CONTENT ENDS -->
    </div><!-- /.col -->
</div><!-- /.row -->
<script type="text/javascript">
    function refrash(){
        $("#page-content").mask("正在加载...");
        $("#page-content", parent.document).load("<%=contextPath%>/admin/querySystemMenu.do?_=" + Math.random(),function(){
            $("#page-content").unmask();
        });
    }

    $(function(){
        $('.dd').nestable();
        $('.dd-handle a').on('mousedown', function(e){
            e.stopPropagation();
        });
        $('[data-rel="tooltip"]').tooltip();


        $("#menu_add_btn").on('click', function() {
            $("#menu_add_dialog_content").load('<%=contextPath%>/admin/toAddSystemMenu.do');
            $('#menu_add_dialog').modal("show");
        });


        $("#menu_add_dialog_save").on("click",function(){
            $("#menu_add_dialog_form").ajaxSubmit({
                url : "<%=contextPath%>/admin/saveSystemMenu.do",
                type : "post",
                dataType : 'json',
                beforeSubmit:function(){
                    if($("#menu_name").val()==''){
                        $("#menu_name").attr("placeholder","菜单名称不能为空！");
                        return false;
                    }
                    if($("#menu_url").val()==''){
                        $("#menu_url").attr("placeholder","菜单地址不能为空！");
                        return false;
                    }
                },
                success : function(data, status) {
                    alert(data.msg);
                    if (data.type=="success") {
                        $('#menu_add_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/querySystemMenu.do?_=" + Math.random(),function(){
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })


        $("#menu_modify_dialog_save").on("click",function(){
            $("#menu_modify_dialog_form").ajaxSubmit({
                url : "<%=contextPath%>/admin/saveSystemMenu.do",
                type : "post",
                dataType : 'json',
                beforeSubmit:function(){
                    if($("#menu_name").val()==''){
                        $("#menu_name").attr("placeholder","菜单名称不能为空！");
                        return false;
                    }
                    if($("#menu_url").val()==''){
                        $("#menu_url").attr("placeholder","菜单地址不能为空！");
                        return false;
                    }
                },
                success : function(data, status) {
                    alert(data.msg);
                    if (data.type=="success") {
                        $('#menu_modify_dialog').modal("hide");
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/querySystemMenu.do?_=" + Math.random(),function(){
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })

    });

    function deleteMenu(menu_id) {
        var c = confirm("您确认要删除么？")
        if (c) {
            $.get("<%=contextPath%>/admin/deleteSystemMenu.do?menu_id=" + menu_id,
                    function(result) {
                        alert(result.msg);
                        refrash();
                    }, "json");
        }
    }

    function editMenu(menuId) {
        $("#menu_modify_dialog_content").load('<%=contextPath %>/admin/toUpdateSystemMenu.do?menu_id='+menuId);
        $('#menu_modify_dialog').modal("show");
    }
</script>
<style>
    .fa {
        display: inline-block;
        font: normal normal normal 14px/1 FontAwesome;
        font-size: inherit;
        text-rendering: auto;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }

    .dd {
        position: relative;
        display: block;
        margin: 0;
        padding: 0;
        /*max-width: 600px;*/
        list-style: none;
        line-height: 20px;
    }
    .dd-list {
        display: block;
        position: relative;
        margin: 0;
        padding: 0;
        list-style: none;
    }
    .dd-list .dd-list {
        padding-left: 30px;
    }
    .dd-collapsed .dd-list {
        display: none;
    }
    .dd-item,
    .dd-empty,
    .dd-placeholder {
        display: block;
        position: relative;
        margin: 0;
        padding: 0;
        min-height: 20px;
        line-height: 20px;
    }
    .dd-handle,
    .dd2-content {
        display: block;
        min-height: 38px;
        margin: 5px 0;
        padding: 8px 12px;
        background: #F8FAFF;
        border: 1px solid #DAE2EA;
        color: #7C9EB2;
        text-decoration: none;
        font-weight: bold;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
    .dd-handle:hover,
    .dd2-content:hover {
        color: #438EB9;
        background: #F4F6F7;
        border-color: #DCE2E8;
    }
    .dd-handle[class*="btn-"],
    .dd2-content[class*="btn-"] {
        color: #FFF;
        border: none;
        padding: 9px 12px;
    }
    .dd-handle[class*="btn-"]:hover,
    .dd2-content[class*="btn-"]:hover {
        opacity: 0.85;
        color: #FFF;
    }
    .dd2-handle + .dd2-content,
    .dd2-handle + .dd2-content[class*="btn-"] {
        padding-left: 44px;
    }
    .dd-handle[class*="btn-"]:hover,
    .dd2-content[class*="btn-"] .dd2-handle[class*="btn-"]:hover + .dd2-content[class*="btn-"] {
        color: #FFF;
    }
    .dd-item > button:hover ~ .dd-handle,
    .dd-item > button:hover ~ .dd2-content {
        color: #438EB9;
        background: #F4F6F7;
        border-color: #DCE2E8;
    }
    .dd-item > button:hover ~ .dd-handle[class*="btn-"],
    .dd-item > button:hover ~ .dd2-content[class*="btn-"] {
        opacity: 0.85;
        color: #FFF;
    }
    .dd2-handle:hover ~ .dd2-content {
        color: #438EB9;
        background: #F4F6F7;
        border-color: #DCE2E8;
    }
    .dd2-handle:hover ~ .dd2-content[class*="btn-"] {
        opacity: 0.85;
        color: #FFF;
    }
    .dd2-item.dd-item > button {
        margin-left: 34px;
    }
    .dd-item > button {
        display: block;
        position: relative;
        z-index: 1;
        cursor: pointer;
        float: left;
        width: 25px;
        height: 20px;
        margin: 5px 1px 5px 5px;
        padding: 0;
        text-indent: 100%;
        white-space: nowrap;
        overflow: hidden;
        border: 0;
        background: transparent;
        font-size: 12px;
        line-height: 1;
        text-align: center;
        font-weight: bold;
        top: 4px;
        left: 1px;
        color: #707070;
    }
    .dd-item > button:before {
        font-family: FontAwesome;
        content: '\f067';
        display: block;
        position: absolute;
        width: 100%;
        text-align: center;
        text-indent: 0;
        font-weight: normal;
        font-size: 14px;
    }
    .dd-item > button[data-action="collapse"]:before {
        content: '\f068';
    }
    .dd-item > button:hover {
        color: #707070;
    }
    .dd-item.dd-colored > button,
    .dd-item.dd-colored > button:hover {
        color: #EEE;
    }
    .dd-placeholder,
    .dd-empty {
        margin: 5px 0;
        padding: 0;
        min-height: 30px;
        background: #F0F9FF;
        border: 2px dashed #BED2DB;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }
    .dd-empty {
        border-color: #AAA;
        border-style: solid;
        background-color: #e5e5e5;
    }
    .dd-dragel {
        position: absolute;
        pointer-events: none;
        z-index: 999;
        opacity: 0.8;
    }
    .dd-dragel > li > .dd-handle {
        color: #4B92BE;
        background: #F1F5FA;
        border-color: #D6E1EA;
        border-left: 2px solid #777;
        position: relative;
    }
    .dd-dragel > li > .dd-handle[class*="btn-"] {
        color: #FFF;
    }
    .dd-dragel > .dd-item > .dd-handle {
        margin-top: 0;
    }
    .dd-list > li[class*="item-"] {
        border-width: 0;
        padding: 0;
    }
    .dd-list > li[class*="item-"] > .dd-handle {
        border-left: 2px solid;
        border-left-color: inherit;
    }
    .dd-list > li > .dd-handle .sticker {
        position: absolute;
        right: 0;
        top: 0;
    }
    .dd2-handle,
    .dd-dragel > li > .dd2-handle {
        left: 0;
        top: 0;
        width: 36px;
        margin: 0;
        border-width: 1px 1px 0 0;
        text-align: center;
        padding: 0 !important;
        line-height: 38px;
        height: 38px;
        background: #EBEDF2;
        border: 1px solid #DEE4EA;
        cursor: pointer;
        overflow: hidden;
        position: absolute;
        z-index: 1;
    }
    .dd2-handle:hover,
    .dd-dragel > li > .dd2-handle {
        background: #E3E8ED;
    }
    .dd2-content[class*="btn-"] {
        text-shadow: none !important;
    }
    .dd2-handle[class*="btn-"] {
        text-shadow: none !important;
        background: rgba(0, 0, 0, 0.1) !important;
        border-right: 1px solid #EEE;
    }
    .dd2-handle[class*="btn-"]:hover {
        background: rgba(0, 0, 0, 0.08) !important;
    }
    .dd-dragel .dd2-handle[class*="btn-"] {
        border-color: transparent;
        border-right-color: #EEE;
    }
    .dd2-handle.btn-yellow {
        text-shadow: none !important;
        background: rgba(0, 0, 0, 0.05) !important;
        border-right: 1px solid #FFF;
    }
    .dd2-handle.btn-yellow:hover {
        background: rgba(0, 0, 0, 0.08) !important;
    }
    .dd-dragel .dd2-handle.btn-yellow {
        border-color: transparent;
        border-right-color: #FFF;
    }
    .dd-item > .dd2-handle .drag-icon {
        display: none;
    }
    .dd-dragel > .dd-item > .dd2-handle .drag-icon {
        display: inline;
    }
    .dd-dragel > .dd-item > .dd2-handle .normal-icon {
        display: none;
    }
</style>