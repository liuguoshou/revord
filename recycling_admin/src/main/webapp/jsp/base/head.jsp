<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>

<script type="text/javascript">
var errMsg="${errMsg}";
	if(errMsg){
		alert(errMsg);
	}
</script>
<div id="wx-header" style="height: auto;">
	<div class="r" id="wx-shortcut">
		欢迎您！
		<font color="#333">
			${loginUser.realName}
		</font>

		<i class="split">|</i>&nbsp;
		<a href="<%=contextPath%>/adminuser/userLogout.do">退出</a>
	</div>
	<div id="wx-nav">
		<ul>
			<c:forEach items="${ADMIN_CONTEXT_MENUS}" var="superMenu"
				varStatus="status">
				<li class="<c:if test="${status.first}">first</c:if> <c:if test="${superMenu.menuId == currentParentId}">current</c:if>">
					<a href="<%=contextPath%>${superMenu.menuUrl}">${superMenu.menuName}</a>
				</li>
			</c:forEach>
		</ul>
		<div class="tail"></div>
	</div>
</div>