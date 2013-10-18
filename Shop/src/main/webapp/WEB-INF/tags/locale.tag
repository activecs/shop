<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.shop.library.com" prefix="shopLibrary" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set scope="request" value="${initParam.availableLocales}" var="avaliableLocales" />

<c:forEach items="${fn:split(avaliableLocales, ',')}" var="lang">
	<a href='<shopLibrary:localeUrl lang="${fn:trim(lang)}" />' >
		<span>${fn:trim(lang)}</span>
	</a>
</c:forEach>

<span class='btn btn-mini'>${pageContext.request.locale.language}</span>