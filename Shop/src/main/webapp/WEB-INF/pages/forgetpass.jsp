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
						<li><a href="<c:url value='/' />">Home</a> <span class="divider">/</span></li>
						<li class="active">Forget password?</li>
					</ul>
					<h3>FORGET YOUR PASSWORD?</h3>
					<hr class="soft" />

					<div class="row">
						<div class="span9" style="min-height: 900px">
							<div class="well">
								<h5>Reset your password</h5>
								<br /> Please enter the email address for your account. A
								verification code will be sent to you. Once you have received
								the verification code, you will be able to choose a new password
								for your account.<br />
								<br />
								<br />
								<form action="#" method="post">
									<div class="control-group">
										<label class="control-label" for="inputEmail">E-mail address</label>
										<div class="controls">
											<input class="span3" type="text" name="inputEmail" id="inputEmail" placeholder="Email">
										</div>
									</div>
									<div class="controls">
										<button type="submit" class="btn block">Submit</button>
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