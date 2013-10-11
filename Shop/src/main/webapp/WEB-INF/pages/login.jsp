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
					<h3>Login</h3>
					<hr class="soft" />
					<div class="row">
						<div class="span3">
							<div class="well well-small">
								<h5>CREATE YOUR ACCOUNT</h5>
								<br />
								<div class="controls">
									<a href="<c:url value='/registration'/>" class="btn block">Create Your Account</a>
								</div>
							</div>
						</div>
						<div class="span4 offset2">
							<div class="well">
								<h5>ALREADY REGISTERED ?</h5>
								<div id="errors">
									<c:forEach var="errorMessage" items="${requestScope.errors}">
										<div class='alert alert-info fade in'>
											<button type='button' class='close' data-dismiss='alert'>×</button>
											<strong>${errorMessage}</strong>
										</div>
									</c:forEach>
								</div>
								<form action="<c:url value='/login'/>" method="post">
									<div class="control-group">
										<label class="control-label" for="inputEmail">Email</label>
										<div class="controls">
											<input class="span3" type="text" name="inputEmail" id="inputEmail" placeholder="Email" value="${formBean.email}">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="password">Password</label>
										<div class="controls">
											<input type="password" class="span3" name="password" id="password" placeholder="Password">
										</div>
									</div>
									<div class="control-group">
										<div class="controls">
											<button type="submit" class="btn">Sign in</button>
											<a href="<c:url value='/forgetpass' />">Forget password?</a>
										</div>
									</div>
								</form>
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