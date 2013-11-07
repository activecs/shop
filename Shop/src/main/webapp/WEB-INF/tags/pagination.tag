<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.shop.library.com" prefix="shopLibrary" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${pageAmount > 1}">
	
	<c:set var="currentPage" value="${formBean.page}" />
	<c:set var="startPage" value="${(currentPage-2)>=1 ? (currentPage-2) : 1}" />
	<c:set var="finishPage" value="${(currentPage+2)<=pageAmount ? (currentPage+2) : pageAmount}" />

	<div class="pagination">
		<ul>
			<!-- first page -->
			<li><a href='<shopLibrary:paginationUrl page="${1}"/>'>&lsaquo;&lsaquo;</a></li>
			
			<!-- previous page -->
			<c:if test="${currentPage != 1}">
				<li><a href='<shopLibrary:paginationUrl page="${currentPage-1}"/>'>&lsaquo;</a></li>
			</c:if>
			
			<!-- nearest pages -->			
			<c:forEach begin="${startPage}"  end="${finishPage}" var="page">
				<li <c:if test="${page == formBean.page}">class='disabled'</c:if> >
					<a href='<shopLibrary:paginationUrl page="${page}"/>' >${page}</a>
				</li>	
			</c:forEach>
			
			<!-- next page -->
			<c:if test="${currentPage != pageAmount}">
				<li><a href='<shopLibrary:paginationUrl page="${currentPage+1}"/>'>&rsaquo;</a></li>
			</c:if>
			
			<!-- last page -->
			<li><a href='<shopLibrary:paginationUrl page="${pageAmount}"/>'>&rsaquo;&rsaquo;</a></li>
		</ul>
	</div>
</c:if>