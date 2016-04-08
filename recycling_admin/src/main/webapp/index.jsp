<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>回收运营系统</title>
    <%--<link href="<%=contextPath%>/css/index.css" type="text/css" rel="stylesheet" />--%>
    <meta name="description" content="User login page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="<%=contextPath%>/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="<%=contextPath%>/assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/font-awesome.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/font-awesome-ie7.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/ace.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="<%=contextPath%>/assets/css/ace-ie.min.css" />
    <script src="<%=contextPath%>/assets/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        $().ready(function(){
            $("input[name='username']").focus();
        });
        function show_box(id) {
            $('.widget-box.visible').removeClass('visible');
            $('#'+id).addClass('visible');
        }
        var msg="${msg}";
        if(msg){
            alert(msg);
        }
        document.onkeydown = function(event) {
            e = event ? event : (window.event ? window.event : null);
            var userName = document.getElementById("userName").value;
            var passwd = document.getElementById("passwd").value;
            if (e.keyCode == 13 && userName != '' && passwd != '') {
                sub();
            }
        }
        function sub() {
            var userName = document.getElementById("userName");
            if (userName.value == '') {
                alert("请填写登录名");
                userName.focus();
                return;
            }
            var passwd = document.getElementById("passwd");
            if (passwd.value == '') {
                alert("请填写密码");
                passwd.focus();
                return;
            }
            document.forms[0].submit();
        }
        function keydown() {
            if (event.keyCode == 13) {
                sub();
            }
        }
    </script>
</head>
<%--	<body>
		<dl class="welcome welcome_yy">
			<dt></dt>
			<dd class="form_02">
				<form id="loginForm" action="<%=contextPath%>/adminuser/userLogin.do"
					method="post">
					<c:if test="${errMsg!=''}">
						<h4>${errMsg }</h4>
					</c:if>
					<p>
						<label>
							登录名
						</label>
						<input type="text" class="textfield" name="userName" value=""
							id="userName" />
					</p>
					<p>
						<label>
							密码
						</label>
						<input type="password" class="textfield mr10" id="passwd"
							name="passwd" />
					</p>
					<p class="login_btn">
						<input type="button" value="登&nbsp;&nbsp;录" class="yellow_btn"
							onclick="sub();" />
					</p>
				</form>
			</dd>
		</dl>
	</body>--%>
<body class="login-layout">
<div class="container-fluid" id="main-container">
    <div id="main-content">
        <div class="row-fluid">
            <div class="span12">
                <div class="login-container">
                    <div class="row-fluid">
                        <div class="center">
                            <!--
                            <h1>
                                <i class="icon-leaf green"></i>
                                <span class="red">Ace</span>
                                <span class="white">Application</span>
                            </h1>
                            <h4 class="blue">&copy; Company Name</h4>
                            -->
                            <div>&nbsp;</div>
                            <div>&nbsp;</div>
                            <div>&nbsp;</div>
                            <div>&nbsp;</div>
                            <div>&nbsp;</div>
                        </div>
                    </div>

                    <div class="space-6"></div>
                    <div class="row-fluid">
                        <div class="position-relative">
                            <div id="login-box" class="visible widget-box no-border">
                                <div class="widget-body">
                                    <div class="widget-main">
                                        <h4 class="header blue lighter bigger">
                                            <i class="icon-coffee green"></i>
                                            请输入您的账户和密码
                                        </h4>
                                        <div class="space-6"></div>
                                        <form action="<%=contextPath%>/adminuser/userLogin.do" method="post">
                                            <fieldset>
                                                <label>
                                                                <span class="block input-icon input-icon-right">
                                                                    <input type="text" name="userName" id="userName" class="span12" placeholder="账户" focus="true"/>
                                                                    <i class="icon-user"></i>
                                                                </span>
                                                </label>

                                                <label>
                                                                <span class="block input-icon input-icon-right">
                                                                    <input type="password" name="passwd" id="passwd" class="span12" placeholder="密码" autocomplete="off"/>
                                                                    <i class="icon-lock"></i>
                                                                </span>
                                                </label>

                                                <div class="space"></div>

                                                <div class="row-fluid">
                                                    <label class="span8">
                                                        <c:if test="${errMsg!=''}">
                                                            <h4 class="text-warning bigger-110 red">${errMsg}</h4>
                                                        </c:if>
                                                    </label>

                                                    <button type="button" class="span4 btn btn-small btn-primary" onclick="sub();">
                                                        <i class="icon-key"></i>
                                                        登录
                                                    </button>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div><!--/widget-main-->
                                    <div class="toolbar clearfix">
                                    </div>
                                </div><!--/widget-body-->
                            </div><!--/login-box-->
                        </div>
                    </div>
                </div><!--/span-->
            </div><!--/row--><!-- <script type="text/javascript">
                window.jQuery || document.write("<script src='assets/js/jquery-1.9.1.min.js'>"+"<"+"/script>");
            </script> -->
        </div>
    </div><!--/.fluid-container-->
</div>
</body>
</html>