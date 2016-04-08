<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8"/>
    <title>回收管理后台</title>
    <meta charset="utf-8">
    <meta name="description" content="回收管理后台"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!--basic styles-->
    <link href="<%=contextPath%>/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/assets/css/bootstrap-responsive.min.css"
          rel="stylesheet"/>
    <link href="<%=contextPath%>/assets/css/jquery.loadmask.css"
          rel="stylesheet"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/font-awesome.min.css"/>
    <link rel="shortcut icon" type="image/png"
          href="<%=contextPath%>/images/favicon.png">
    <!--[if IE 7]>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!--page specific plugin styles-->

    <!--ace styles-->
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/ace-responsive.min.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/ace-skins.min.css"/>


    <!--[if lte IE 8]>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/ace-ie.min.css"/>
    <![endif]-->

    <!--inline styles if any-->

    <!--page specific plugin styles-->
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/jquery-ui-1.10.3.custom.min.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/chosen.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/datepicker.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/bootstrap-timepicker.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/daterangepicker.css"/>
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/colorpicker.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/assets/uploadify/uploadify.css">
    <style>
        .btvdclass {
            border-color: #FF0000;
            color: #FF0000;
            font-weight: normal;
        }
    </style>
</head>
<body>
<div class="navbar navbar-inverse">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a href="#" class="brand">
                <small><i class="icon-list"></i>
                    回收管理后台
                </small>
            </a>
            <!--/.brand-->

            <ul class="nav ace-nav pull-right">

                <li class="light-blue user-profile"><a data-toggle="dropdown"
                                                       href="#" class="user-menu dropdown-toggle"> <img
                        class="nav-user-photo" src="<%=contextPath%>/assets/avatars/avatar2.png"
                        alt="Jason's Photo"/> <span id="user_info"> <small>欢迎你,</small>
                ${loginUser.realName}
						</span> <i class="icon-caret-down"></i>
                </a>

                    <ul class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer"
                            id="user_menu">
                        <li><a href="<%=contextPath%>/adminuser/userLogout.do"> <i class="icon-off"></i> 退出
                        </a></li>
                    </ul>
                </li>
            </ul>
            <!--/.ace-nav-->
        </div>
        <!--/.container-fluid-->
    </div>
    <!--/.navbar-inner-->
</div>
<div id="user_modifyPwd_dialog" class="modal hide fade" tabindex="-1" role="dialog"
     aria-labelledby="user_modifyPwd_dialog_title" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
        <h3 id="user_modifyPwd_dialog_title">修改-用户密码</h3>
    </div>
    <div id="user_modifyPwd_dialog_content" class="modal-body">
        <p>弹出层…</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button id="user_modifyPwd_dialog_save" class="btn btn-primary">保存</button>
    </div>
</div>
<div class="container-fluid" id="main-container">
    <a id="menu-toggler" href="#"> <span></span>
    </a>
</div>
<div id="sidebar">
    <div id="sidebar-shortcuts">
        <div id="sidebar-shortcuts-large">
            <button class="btn btn-small btn-success">
                <a href="<%=contextPath%>/admin/toRecyclingUser.do" target="center">
                    <i class="icon-group" style="color: #ffffff;"></i>
                </a>
            </button>

            <button class="btn btn-small btn-info">
                <a href="<%=contextPath%>/admin/toRecyclingAdminRegion.do" target="center">
                    <i class="icon-bullhorn" style="color: #ffffff;"></i>
                </a>
            </button>

            <button class="btn btn-small btn-warning">
                <a href="<%=contextPath%>/admin/toRecyclingAdminPauper.do" target="center">
                    <i class="icon-heart" style="color: #ffffff;"></i>
                </a>
            </button>

            <button class="btn btn-small btn-danger">
                <a href="<%=contextPath%>/admin/toRecyclingAdminStatistics.do" target="center">
                    <i class="icon-bar-chart" style="color: #ffffff;"></i>
                </a>
            </button>

        </div>

        <div id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span> <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
        </div>
    </div>
    <!--#sidebar-shortcuts -->

    <ul class="nav nav-list">
        <c:forEach items="${ADMIN_CONTEXT_MENUS}" var="superMenu" varStatus="status">
            <li>
                <c:if test="${empty superMenu.listSubMenu}">
                    <a href="<%=contextPath%>${superMenu.menuUrl}" target="center">
                        <i class="${superMenu.menuIcon}"></i>
                        <span>${superMenu.menuName}</span>
                        <b class="arrow"></b>
                    </a>
                </c:if>
                <c:if test="${not empty superMenu.listSubMenu}">
                    <a href="<%=contextPath%>${superMenu.menuUrl}" class="dropdown-toggle">
                        <i class="${superMenu.menuIcon} "></i>
                        <span>${superMenu.menuName}</span>
                        <b class="arrow icon-angle-down"></b>
                    </a>
                    <ul class="submenu">
                        <c:forEach items="${superMenu.listSubMenu}" var="subMenu">
                            <li>
                                <a href="<%=contextPath%>${subMenu.menuUrl}" target="center">
                                    <i class="icon-double-angle-right"></i> ${subMenu.menuName}
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </li>
        </c:forEach>
    </ul>
    <!--/.nav-list-->

    <div id="sidebar-collapse">
        <i class="icon-double-angle-left"></i>
    </div>
</div>
<div id="main-content" class="clearfix">
    <div id="breadcrumbs">
        <ul class="breadcrumb">
            <li><i class="icon-home"></i> <a href="#">首页</a> <span
                    class="divider"> <i class="icon-angle-right"></i>
					</span></li>
            <li class="active" id="currentMenu"></li>
        </ul>
    </div>

    <div id="page-content" class="clearfix">
    </div>
    <!--/#main-content-->
</div>
<!--/.fluid-container#main-container-->

<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
    <i class="icon-double-angle-up icon-only bigger-110"></i>
</a>

<!--basic scripts-->

<script src="<%=contextPath%>/assets/js/jquery-1.9.1.min.js"></script>
<script src="<%=contextPath%>/assets/js/bootstrap.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.nestable.js"></script>

<!--page specific plugin scripts-->

<script src="<%=contextPath%>/assets/js/jquery.dataTables.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.dataTables.bootstrap.js"></script>

<!--page specific plugin scripts-->
<script src="<%=contextPath%>/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.ui.touch-punch.min.js"></script>
<script src="<%=contextPath%>/assets/js/chosen.jquery.min.js"></script>
<script src="<%=contextPath%>/assets/js/fuelux/fuelux.spinner.min.js"></script>
<script src="<%=contextPath%>/assets/js/date-time/moment.min.js"></script>
<script src="<%=contextPath%>/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="<%=contextPath%>/assets/js/date-time/bootstrap-timepicker.min.js"></script>
<script src="<%=contextPath%>/assets/js/date-time/daterangepicker.min.js"></script>
<script src="<%=contextPath%>/assets/js/bootstrap-colorpicker.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.knob.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.autosize-min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.inputlimiter.1.3.1.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.maskedinput.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.loadmask.js"></script>
<script src="<%=contextPath%>/assets/js/date-time/locales/bootstrap-datepicker.zh-CN.js"></script>
<!--ace scripts-->
<script src="<%=contextPath%>/assets/uploadify/jquery.uploadify.js" type="text/javascript"></script>


<script src="<%=contextPath%>/assets/js/ace-elements.min.js"></script>
<script src="<%=contextPath%>/assets/js/ace/ace.js"></script>
<script src="<%=contextPath%>/assets/js/ace/elements.wysiwyg.js"></script>
<script src="<%=contextPath%>/assets/js/ace/ace.sidebar.js"></script>
<script src="<%=contextPath%>/assets/js/ace.min.js"></script>
<script src="<%=contextPath%>/js/formatCommon.js"></script>
<script src="<%=contextPath%>/js/jquery.form.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.validate.min.js"></script>
<!--inline scripts related to this page-->
<script src="<%=contextPath%>/assets/js/markdown/markdown.min.js"></script>
<script src="<%=contextPath%>/assets/js/markdown/bootstrap-markdown.min.js"></script>
<script src="<%=contextPath%>/assets/js/jquery.hotkeys.min.js"></script>
<script src="<%=contextPath%>/assets/js/bootstrap-wysiwyg.min.js"></script>
<script src="<%=contextPath%>/assets/js/bootbox.min.js"></script>

<script type="text/javascript" src="<%=contextPath%>/js/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/FCKeditor/fckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/fileUpload.js"></script>
<script type="text/javascript">
    $(function () {
        $.ajaxSetup({
            cache: false //关闭AJAX相应的缓存
        });


        $('[data-rel=tooltip]').tooltip();

        $(".dropdown-toggle").on('click', function () {

            if (!$(this).parent().hasClass("active")) {
                $(this).parent().addClass("active");
                $(".active").removeClass("active");
            }
        });

        $("a[target='center']").on('click', function () {
            var html="";
            $("#page-content").mask("正在加载...");
            $("#page-content").html(html);
            $.ajax({
                url : $(this).attr('href') + "?_=" + Math.random(),
                type : "post",
                dataType : 'html',
                async:false,
                success : function(data, status) {
                    html=data;
                },
                statusCode: {
                    404: function() {
                        html=$("#404_content").html();
                    },
                    500: function(){
                        html=$("#500_content").html();
                    }
                }
            });

            $("#page-content").append(html);
            $("#page-content").unmask();
            return false;
        });
    })
    function editPwd(userId) {
        $("#user_modifyPwd_dialog_content").load('@{UserController.editPwd}?userId=' + userId + '');
        $('#user_modifyPwd_dialog').modal("show");
    }
</script>
</body>
<style type="text/css">
    a {
        cursor: pointer;
    }
</style>
</html>

<div class="row" id="500" style="display: none">
    <div class="col-xs-12" id="500_content">
        <div class="error-container">
            <div class="well">
                <h1 class="grey lighter smaller">
                    <span class="blue bigger-125">
                        <i class="ace-icon fa fa-random"></i>
                        500
                    </span>
                    抱歉！系统错误！
                </h1>

                <hr>
                <h3 class="lighter smaller">
                    请联系系统管理员，为您解决此问题！
                </h3>

                <div class="space"></div>

                <div>
                    <h4 class="lighter smaller">系统内部错误！您请求的功能发生系统异常，请联系系统管理员！</h4>
                </div>
            </div>
        </div>
    </div><!-- /.col -->
</div>
<div class="row" id="404" style="display: none;">
    <div class="error-container" id="404_content">
        <div class="well">
            <h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="ace-icon fa fa-sitemap"></i>
												404
											</span>
                抱歉，此链接不存在！！！
            </h1>

            <hr>
            <h3 class="lighter smaller">我们在系统中没有找到您访问的链接！</h3>

            <div>
                <div class="space"></div>
                <h4 class="smaller">请尝试联系管理员解决此问题！</h4>
            </div>
        </div>
    </div>
</div>