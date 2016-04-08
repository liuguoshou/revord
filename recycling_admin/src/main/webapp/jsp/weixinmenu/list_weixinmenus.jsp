<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/jsp/base/base.jsp" %>
<link href="<%=contextPath%>/css/menu.css"/>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="col-sm-6" style="margin-left: 100px">
            <div>
                <div>
                    <label>
                        <input type="radio" name="weixin_type"
                                <c:if test="${weixin_type eq 'weixin_subscript_test'}">
                                    checked
                                </c:if>
                               value="weixin_subscript_test"/>
                        <span class="lbl">微信测试订阅号菜单</span>
                    </label>
                    <label>
                        <input type="radio" name="weixin_type"
                                <c:if test="${weixin_type eq 'weixin_subscript'}">
                                    checked
                                </c:if>
                               value="weixin_subscript"/>
                        <span class="lbl">微信订阅号菜单</span>
                    </label>
                    <label>
                        <input type="radio" name="weixin_type"
                                <c:if test="${weixin_type eq 'weixin_serivce_test'}">
                                    checked
                                </c:if>
                               value="weixin_serivce_test"/>
                        <span class="lbl">微信测试服务号菜单</span>
                    </label>
                    <label>
                        <input type="radio" name="weixin_type"
                                <c:if test="${weixin_type eq 'weixin_service'}">
                                    checked
                                </c:if>
                               value="weixin_service"/>
                        <span class="lbl">微信服务号菜单</span>
                    </label>
                </div>
                <div align="right">
                    <span class="text-warning">一级菜单最多：3个&nbsp;&nbsp;&nbsp;&nbsp;二级菜单最多5个</span>
                    <button class="btn btn-primary" id="weixinmenu_add_btn">添加菜单</button>
                    <button class="btn" id="weixinmenu_create_btn">生成菜单</button>
                </div>
            </div>
            <div class="dd" id="nestable_weixin">
                <c:forEach items="${menuList}" var="weixin" varStatus="la">
                    <ol class="dd-list">
                        <li class="dd-item " data-id="5">
                            <div class="dd-handle">
                                <span>${weixin.name}</span>
                                <span>
                                    <c:choose>
                                        <c:when test="${weixin.type== 'click'}">
                                            <span style="color:red">${weixin.type}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color:#1e90ff">${weixin.type}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                                <span>
                                    <c:choose>
                                        <c:when test="${weixin.type== 'click'}">
                                            ${weixin.key}
                                        </c:when>
                                        <c:otherwise>
                                            ${weixin.url}
                                        </c:otherwise>
                                    </c:choose>
                                </span>

                                <div class="pull-right action-buttons">
                                    <a class="blue" href="#" onclick="editMenu(${weixin.id})">
                                        <i class="ace-icon fa fa-pencil bigger-130"></i>
                                        修改
                                    </a>

                                    <a class="red" href="#" onclick="deleteMenu(${weixin.id},null)">
                                        <i class="ace-icon fa fa-trash-o bigger-130"></i>
                                        删除
                                    </a>
                                </div>
                            </div>
                            <ol class="dd-list">
                                <c:forEach items="${weixin.sub_button}" var="weixin_submenu" varStatus="ll">
                                    <li class="dd-item" data-id="${ll.count}">
                                        <div class="dd-handle">
                                            <span>${weixin_submenu.name}</span>
                                            <span>
                                                <c:choose>
                                                    <c:when test="${weixin_submenu.type== 'click'}">
                                                        <span style="color:red">${weixin_submenu.type}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span style="color:#1e90ff">${weixin_submenu.type}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                            <span>
                                                <c:choose>
                                                    <c:when test="${weixin_submenu.type== 'click'}">
                                                        <span style="color:red">${weixin_submenu.key}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span style="color:#1e90ff">${weixin_submenu.url}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>

                                            <div class="pull-right action-buttons">
                                                <a class="blue" href="#" onclick="editMenu(${weixin_submenu.id})">
                                                    <i class="ace-icon fa fa-pencil bigger-130"></i>
                                                    修改
                                                </a>

                                                <a class="red" href="#" onclick="deleteMenu(${weixin_submenu.id}, ${weixin_submenu.pid})">
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
        <div id="weixinmenu_add_dialog" class="modal hide fade" tabindex="-1" weixinmenu="dialog"
             aria-labelledby="weixinmenu_add_dialog_title" aria-hidden="true">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="weixinmenu_add_dialog_title">新增-微信菜单</h3>
            </div>
            <div id="weixinmenu_add_dialog_content" class="modal-body">
                <p>弹出层…</p>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                <button id="weixinmenu_add_dialog_save" class="btn btn-primary">保存</button>
            </div>
        </div>
        <div id="weixinmenu_modify_dialog" class="modal hide fade" tabindex="-1" weixinmenu="dialog"
             aria-labelledby="weixinmenu_modify_dialog_title" aria-hidden="true">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
                <h3 id="weixinmenu_modify_dialog_title">修改-微信菜单</h3>
            </div>
            <div id="weixinmenu_modify_dialog_content" class="modal-body">
                <p>弹出层…</p>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                <button id="weixinmenu_modify_dialog_save" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
    <!-- PAGE CONTENT ENDS -->
</div>
<!-- /.col -->
</div><!-- /.row -->
<script type="text/javascript">
    $(function () {
        $('.dd').nestable();
        $('.dd-handle a').on('mousedown', function (e) {
            e.stopPropagation();
        });
        $('[data-rel="tooltip"]').tooltip();

        //转向添加页面
        $("#weixinmenu_add_btn").on('click', function () {
            var wx_type = $("input[name='weixin_type']:checked").val();
            $("#weixinmenu_add_dialog_content").load('<%=contextPath%>/admin/addWeixinMenu.do?weixin_type='+wx_type);
            $('#weixinmenu_add_dialog').modal("show");
        });

        //微信公众号类型切换，重新加载数据
        $("input[name='weixin_type']").on("click", function () {
            $("#page-content").mask("正在加载...");
            $("#page-content", parent.document).load("<%=contextPath%>/admin/queryMenuList.do?weixin_type=" + $(this).val() + "&_=" + Math.random(), function () {
                $("#page-content").unmask();
            });
        })

        //保存操作
        $("#weixinmenu_add_dialog_save").on("click", function () {
            $("#weixinmenu_add_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveWeixinMenu.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    var wx_type=$("input[name='menu_type']:checked").val();
                    if ($("#menu_name").val() == '') {
                        $("#menu_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }
                    if ($("#sort").val() == '') {
                        $("#menu_url").attr("placeholder", "排序不能为空！");
                        return false;
                    }else if($("#sort").val()!=""){
                        var re=/^[0-9]+$/;
                        if (!re.test($("#sort").val()))
                        {
                            alert("请输入数字！");
                            return false;
                        }
                    }
                    if(wx_type=='view'){
                        if($("#menu_pid").val()!=0){
                            if($("#menu_url").val()==""){
                                alert("菜单URL不能为空！");
                                $("#menu_url").focus();
                                return false;
                            }
                        }
                    }else{
                        if($("#menu_pid").val()!=0){
                            if($("#menu_key").val()==""){
                                alert("菜单关键字不能为空！");
                                $("#menu_key").focus();
                                return false;
                            }
                        }
                    }

                    //验证菜单是否可以添加，一级菜单：3个 二级菜单：5个
                    $.ajax({
                        url:'<%=contextPath%>/admin/validateWeixinMenu.do',
                        type:'post',
                        data:{weixin_type:$("#weixin_type").val(),menu_pid:$("#weixin_menu_pid").val()},
                        async : false, //默认为true 异步
                        dataType:'json',
                        success:function(data){
                            if(!data){
                                alert("该栏目已到达创建菜单最大数目！");
                                return false;
                            }
                        },
                        error:function(){
                            alert('系统错误!');
                        }
                    });
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#weixinmenu_add_dialog').modal("hide");
                        var wx_type = $("input[name='weixin_type']:checked").val();
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/queryMenuList.do?&weixin_type="+wx_type+"&_="  + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })

        //保存修改
        $("#weixinmenu_modify_dialog_save").on("click", function () {
            $("#weixinmenu_modify_dialog_form").ajaxSubmit({
                url: "<%=contextPath%>/admin/saveWeixinMenu.do",
                type: "post",
                dataType: 'json',
                beforeSubmit: function () {
                    var wx_type=$("input[name='menu_type']:checked").val();
                    if ($("#menu_name").val() == '') {
                        $("#menu_name").attr("placeholder", "菜单名称不能为空！");
                        return false;
                    }
                    if ($("#sort").val() == '') {
                        $("#menu_url").attr("placeholder", "排序不能为空！");
                        return false;
                    }else if($("#sort").val()!=""){
                        var re=/^[0-9]+$/;
                        if (!re.test($("#sort").val()))
                        {
                            alert("请输入数字！");
                            return false;
                        }
                    }
                    if(wx_type=='view'){
                        if($("#menu_pid").val()!=0){
                            if($("#menu_url").val()==""){
                                alert("菜单URL不能为空！");
                                $("#menu_url").focus();
                                return false;
                            }
                        }
                    }else{
                        if($("#menu_pid").val()!=0){
                            if($("#menu_key").val()==""){
                                alert("菜单关键字不能为空！");
                                $("#menu_key").focus();
                                return false;
                            }
                        }
                    }

                    //验证菜单是否可以添加，一级菜单：3个 二级菜单：5个
                    $.ajax({
                        url:'<%=contextPath%>/admin/validateWeixinMenu.do',
                        type:'post',
                        data:{weixin_type:$("#weixin_type").val(),menu_pid:$("#weixin_menu_pid").val()},
                        async : false, //默认为true 异步
                        dataType:'json',
                        success:function(data){
                            if(!data){
                                alert("该栏目已到达创建菜单最大数目！");
                                return false;
                            }
                        },
                        error:function(){
                            alert('系统错误!');
                        }
                    });
                },
                success: function (data, status) {
                    alert(data.msg);
                    if (data.type == "success") {
                        $('#weixinmenu_modify_dialog').modal("hide");
                        var wx_type = $("input[name='weixin_type']:checked").val();
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/queryMenuList.do?&weixin_type="+wx_type+"&_=" + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }
                }
            });
        })



        //生成微信菜单
        $("#weixinmenu_create_btn").on("click", function () {
            if( ${!empty menuList} ){
                var wx_type = $("input[name='weixin_type']:checked").val();
                var msg="";
                if(wx_type=='weixin_subscript_test'){
                    msg="当前菜单为订阅测试号！";
                }else if(wx_type=='weixin_subscript'){
                    msg="当前菜单为订阅号！";
                }else if(wx_type=='weixin_service_test'){
                    msg="当前菜单为服务测试号！";
                }else{
                    msg="当前菜单为服务号！";
                }
                msg+="确认创建此菜单嘛？";
                var confirm = window.confirm(msg);
                if(confirm){
                    $.ajax({
                        type:'get',
                        url : '<%=contextPath %>/admin/createWeixinMenu.do',
                        data: {weixin_type: wx_type},
                        dataType : 'json',
                        async:true,
                        beforeSend: function(){
                        },
                        success : function(data, status) {
                            alert(data.msg);
                        },
                        error : function(data, status, e) {
                            alert("系统繁忙，请稍后再试！");
                        }
                    });
                }
            }else{
                alert("当前菜单为空！请先创建菜单！");
            }
        })



    });

    function deleteMenu(menu_id,pid) {
        var c = confirm("您确认要删除么？")
        if (c) {
            $.get("<%=contextPath%>/admin/deleteWeixinMenu.do?id="+menu_id+"&pid="+pid,
                    function (result) {
                        alert(result.msg);
                        var wx_type = $("input[name='weixin_type']:checked").val();
                        $("#page-content").mask("正在加载...");
                        $("#page-content", parent.document).load("<%=contextPath%>/admin/queryMenuList.do?&weixin_type="+wx_type+"&_=" + Math.random(), function () {
                            $("#page-content").unmask();
                        });
                    }, "json");
        }
    }

    function editMenu(menuId) {
        var wx_type = $("input[name='weixin_type']:checked").val();
        $("#weixinmenu_modify_dialog_content").load('<%=contextPath %>/admin/toUpdateWeixinMenu.do?id=' + menuId +"&weixin_type=" + wx_type);
        $('#weixinmenu_modify_dialog').modal("show");
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