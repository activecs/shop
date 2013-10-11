<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/jspf/head.jspf"%>
	<%@ page isErrorPage="true" %>
</head>
<body>
	<!-- Header =============================== -->
	<header>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
	</header>
	<!-- Header end =============================== -->
	<div id="mainBody">
		<div class="container">
			<div class="row">
				<!-- Sidebar ================================================== -->
				<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>
				<!-- Sidebar end=============================================== -->
				<div class="span9">
					<ul class="breadcrumb">
						<li><a href="<c:url value='/' />">Home</a><span class="divider">/</span></li>
						<li class="active">Login</li>
					</ul>
					<h3>Error </h3>
					<hr class="soft" />
					<c:set var="code" value="${requestScope['javax.servlet.error.status_code']}" scope="page" />
					<c:if test="${code == '404'}">
						<c:set var="errorMessage" value="${code} Page not found" scope="page" />
					</c:if>
					<c:if test="${code == '500'}">
						<c:set var="errorMessage" value="${code} Internal server error occured" scope="page" />
					</c:if>
					<h4>
						<c:out value="${errorMessage}"/>
					</h4>
				</div>
			</div>
		</div>
	</div>
	<!-- MainBody End ============================= -->
	<!-- Footer =============================== -->
	<footer>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</footer>
	<!-- Footer end =============================== -->
</body>
</html>