<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ include file="/jsp/base/base.jsp"%>
<div id="menu">
<div class="box">
  <ul>
    <c:forEach items="${subMenuList}" var="childMenu">
      <c:if test="${childMenu.menuDisplay==1}">	
      	<li><a href="<%=contextPath %>${childMenu.menuUrl}" <c:if test="${childMenu.menuUrl == requestPath}">class="current"</c:if>>${childMenu.menuName}</a></li>
      </c:if>
    </c:forEach>
  </ul>
</div>
</div>