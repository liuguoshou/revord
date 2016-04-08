
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<%--<!DOCTYPE html >
<html>
<head>
    <title>修改微信菜单</title>
    <link href="<%=contextPath%>/css/main.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="<%=contextPath%>/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/js/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/js/FCKeditor/fckeditor.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/js/fileUpload.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/js/common.js"></script>
</head>
<body>
<jsp:include page="/jsp/base/head.jsp" />
<div id="main">
    <jsp:include page="/jsp/base/leftmenu.jsp" />
    <div id="content">
        <form class="registerform" id="pform" action="<%=contextPath%>/admin/saveWeixinMenu.do"
              method="post">
            <table class="noborder">
                <tr>
                    <input type="hidden" value="${weixin_type}" name="weixin_type" id="weixin_type" />
                    <input type="hidden" value="${weixin_menu.id}" name="weixin_id" id="weixin_id" />
                    <input type="hidden" value="update" name="method" id="method" />
                    <td>菜单名称:</td>
                    <td class="edit"><input class="inputxt" value="${weixin_menu.name}" id="menu_name" name="menu_name" type="text"/></td>
                </tr>
                <tr id="catlog">
                    <td>所属菜单:</td>
                    <td class="edit">
                        <select id="menu_pid" name="menu_pid">
                            <option value=0>一级菜单</option>
                            <c:forEach items="${parent_menu}" var="menu">
                                <option value="${menu.id}" ${weixin_menu.pid == menu.id ? "selected" : "" }>${menu.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>菜单事件类型:</td>
                    <td class="edit">
                        <input type="radio" name="menu_type" value="click" ${weixin_menu.type == 'click' ? "checked" : ""}>click
                        <input type="radio" name="menu_type" value="view"  ${weixin_menu.type == 'view' ? "checked" : ""}>view
                    </td>
                </tr>
                <tr id="url">
                    <td>菜单URL:</td>
                    <td class="edit"><input class="inputxt" value="${weixin_menu.url}" id="menu_url" name="menu_url" type="text"/></td>
                </tr>
                <tr id="key">
                    <td>菜单关键字:</td>
                    <td class="edit"><input class="inputxt" value="${weixin_menu.key}" id="menu_key" name="menu_key" type="text"/></td>
                </tr>
                <tr>
                    <td>排序:</td>
                    <td class="edit"><input class="inputxt" value="${weixin_menu.sort}" id="sort" name="sort" type="text"/></td>
                </tr>
                <tr>
                    <td><input type="button" id="zy" value="保存" onclick="addMenu();" />
                    </td>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/js/Validform_v5.3.2.js"></script>
<script type="text/javascript">
    $(function(){
        var demo=$(".registerform").Validform({
            tiptype:3,
            label:".label",
            showAllError:true,
            datatype:{
                "zh1-6":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,6}$/,
                "n":/^\d+$/
            },
            ajaxPost:false
        });

        demo.tipmsg.w["zh1-6"]="请输入1到6个中文字符！";
        demo.tipmsg.w["n"]="请输入数字！";
        demo.addRule([
            {
                ele:".inputxt:eq(0)",
                datatype:"*1-100"
            },
            {
                ele:".inputxt:eq(3)",
                datatype:"n"
            }
        ]);
        //click事件与view事件切换，隐藏和现实文本框
        $("#key").hide();
        $("input[name='menu_type']").on("click",function(){
            if($(this).val()=='click'){
                $("#key").show();
                $("#url").hide();
            }else{
                $("#url").show();
                $("#key").hide();
            }
        })
    })

    function addMenu(){
        var wx_type=$("input[name='menu_type']:checked").val();
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
        if($("#menu_pid").val()!=0){
            $.ajax({
                url:'<%=contextPath%>/admin/validateWeixinMenu.do',
                type:'post',
                data:{weixin_type:$("#weixin_type").val(),menu_pid:$("#menu_pid").val()},
                async : false, //默认为true 异步
                dataType:'json',
                success:function(data){
                    if(!data){
                        alert("该栏目已到达创建菜单最大数目！");
                        return false;
                    }else{
                        $("#pform").submit();
                    }
                },
                error:function(){
                    alert('系统错误!');
                }
            });
        }else{
            $("#pform").submit();
        }
    }


</script>


</body>
</html>

--%>
<div class="row">
    <form id="weixinmenu_modify_dialog_form" action="<%=contextPath%>/admin/saveWeixinMenu.do" class="form-horizontal" method="post" enctype="multipart/form-data">
        <div class="control-group">
            <label class="control-label" for="menu_name"><span style="color: red">*</span>菜单名称:</label>
            <div class="controls">
                <input id="menu_name" class="inputxt" name="menu_name" value="${weixin_menu.name}"  type="text" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="weixin_menu_pid"><span style="color: red">*</span>所属菜单:</label>
            <div class="controls">
                <select id="weixin_menu_pid" name="menu_pid">
                    <option value=0>一级菜单</option>
                    <c:forEach items="${parent_menu}" var="menu">
                        <option value="${menu.id}" ${weixin_menu.pid == menu.id ? "selected" : "" }>${menu.name}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label"><span style="color: red">*</span>菜单事件类型:</label>
            <div class="controls">
                <div>
                    <label>
                        <input class="ace" type="radio" name="menu_type" value="click" ${weixin_menu.type == 'click' ? "checked" : ""}>
                        <span class="lbl">click</span>
                    </label>
                    <label>
                        <input class="ace" type="radio" name="menu_type" value="view" ${weixin_menu.type == 'view' ? "checked" : ""}>
                        <span class="lbl">view</span>
                    </label>
                </div>
            </div>
        </div>

        <div class="control-group" id="url">
            <label class="control-label" ><span style="color: red">*</span>菜单URL:</label>
            <div class="controls">
                <input id="menu_url" class="inputxt" name="menu_url" type="text" value="${weixin_menu.url}" />
            </div>
        </div>

        <div class="control-group" id="key">
            <label class="control-label" ><span style="color: red">*</span>菜单关键字:</label>
            <div class="controls">
                <input id="menu_key" class="inputxt" name="menu_key" type="text" value="${weixin_menu.key}" />
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" ><span style="color: red">*</span>排序:</label>
            <div class="controls">
                <input id="sort" class="inputxt" name="sort" type="text"  value="${weixin_menu.sort}"/>
            </div>
        </div>
        <input type="hidden" value="${weixin_type}" name="weixin_type" id="weixin_type" />
        <input type="hidden" value="${weixin_menu.id}" name="weixin_id" id="weixin_id" />
        <input type="hidden" value="update" name="method" id="method" />
    </form>
</div>
<script>
    //click事件与view事件切换，隐藏和现实文本框
    $(function(){
        var value=$("input[name='menu_type']:checked").val();
        if(value=="click"){
            ("#key").show();
            $("#url").hide();
        }else{
            $("#url").show();
            $("#key").hide();
        }
    })
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
