<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:message key="tag.welcome.welcome" bundle="${bundle}" />
<strong>
<c:choose>
	<c:when test="${not empty userBean.email}">
		<a href="<c:url value='/account' />" >${userBean.email}</a>
	</c:when>
	<c:otherwise>User</c:otherwise>
</c:choose>
</strong>

