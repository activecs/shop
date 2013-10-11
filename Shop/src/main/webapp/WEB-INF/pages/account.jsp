<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/jspf/head.jspf"%>
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
					<h3>Personal information</h3>
					<hr class="soft" />
					<div class="row">
						<div class="span3">
							<div class="well well-small">
								<img src="<c:url value='/avatar.png' />" class="img-rounded">
							</div>
						</div>
						<div class="span6">
							<div class="well">
								<table class="table table-striped">
									<col width="110">
									<tr>
										<td>Firstname</td><td>${userBean.firstName}</td>
									</tr>
									<tr>
										<td>Lastname</td><td>${userBean.lastName}</td>
									</tr>
									<tr>
										<td>Email</td><td>${userBean.email}</td>
									</tr>
									<tr>
										<td>BirthDate</td>
										<td>
											<fmt:formatDate pattern="dd-MM-yyyy" value="${userBean.birthDate}" />
										</td>
									</tr>
									<tr>
										<td>Company</td><td>${userBean.company}</td>
									</tr>
									<tr>
										<td>Address</td><td>${userBean.address1}</td>
									</tr>
									<tr>
										<td>Address</td><td>${userBean.address2}</td>
									</tr>
									<tr>
										<td>Postcode</td><td>${userBean.postCode}</td>
									</tr>
									<tr>
										<td>Additional info</td><td>${userBean.additionalInfo}</td>
									</tr>
									<tr>
										<td>Phone</td><td>${userBean.phone}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
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