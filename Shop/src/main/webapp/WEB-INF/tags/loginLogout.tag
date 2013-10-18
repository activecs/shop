<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:choose>
	<c:when test="${empty userBean}">
		<a href="<c:url value='/login'/>"> 
			<span class="btn btn-large btn-success"><fmt:message key="tag.loginlogout.login" bundle="${bundle}" /></span>
		</a>
	</c:when>
	<c:otherwise>
		<a href="<c:url value='/logout'/>"> 
			<span class="btn btn-large btn-danger"><fmt:message key="tag.loginlogout.logout" bundle="${bundle}" /></span>
		</a>
	</c:otherwise>
</c:choose>
