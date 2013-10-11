<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${empty userBean}">
		<a href="<c:url value='/login'/>"> 
			<span class="btn btn-large btn-success">Login</span>
		</a>
	</c:when>
	<c:otherwise>
		<a href="<c:url value='/logout'/>"> 
			<span class="btn btn-large btn-danger">Logout</span>
		</a>
	</c:otherwise>
</c:choose>
