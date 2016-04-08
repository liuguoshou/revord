<%@ page language="java" pageEncoding="UTF-8"%>
<footer>
    <div class="mybag">
        <p><a href="${pageContext.request.contextPath}/user/accountInfo.do">我的账户</a></p>
        <p><a href="${pageContext.request.contextPath}/user/showServiceArea.do">服务范围</a></p>
        <!-- p><a href="${pageContext.request.contextPath}/user/initServiceRoute.do">联系客服</a></p> -->
        <p><a href="${pageContext.request.contextPath}/user/initUserAdvise.do">意见反馈</a></p>
        <!-- p class="none"><a href="${pageContext.request.contextPath}/user/initAboutus.do">关于我们</a></p> -->
        <span class="myico"></span>
    </div>
    <ul>
        <li><a href="${pageContext.request.contextPath}/user/userIndex.do"><em class="f1"></em>回收废品</a></li>
        <li><a href="${pageContext.request.contextPath}/user/showPriceDetail.do"><em class="f2"></em>价格明细</a></li>
        <li><a href="javascript:;" id="flip"><em class="f3"></em>我的袋子</a></li>
    </ul>
</footer>
<script>
	total = document.documentElement.clientHeight;
	colHeight = total-61-document.getElementById("co1").offsetTop;
	document.getElementById("co1").style.minHeight=colHeight+"px";
</script>
