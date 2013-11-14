<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
	<!-- Header =============================== -->
	<header>
		<%@ include file="/WEB-INF/jspf/header.jspf" %>
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
						<li class="active">Registration</li>
					</ul>
					<h3>Registration</h3>
					<div class="well">
						<div id="errors">
							<c:forEach var="errorMessage" items="${requestScope.errors}">
								<div class='alert alert-info fade in'>
									<button type='button' class='close' data-dismiss='alert'>×</button>
									<strong>${errorMessage}</strong>
								</div>
							</c:forEach>
						</div>
						<form action="<c:url value='/registration' />" class="form-horizontal" id="registration-form" method="post" enctype="multipart/form-data" >
							<h4>Your personal information</h4>

							<div class="control-group">
								<label class="control-label" for="firstName">First name <sup>*</sup></label>
								<div class="controls">
									<input type="text" name="firstName" id="firstName" placeholder="First Name" value="${formBean.firstName}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="lastName">Last name <sup>*</sup></label>
								<div class="controls">
									<input type="text" name="lastName" id="lastName" placeholder="Last Name" value="${formBean.lastName}" />
								</div>
							</div>
							<div id="emailControlGroup" class="control-group">
								<label class="control-label" for="inputEmail">Email <sup>*</sup></label>
								<div class="controls">
									<input type="text" name="inputEmail" id="inputEmail" placeholder="Email" value="${formBean.email}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="password">Password <sup>*</sup></label>
								<div class="controls">
									<input type="password" name="password" id="password" placeholder="Password">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="birthDate">Date of birth <sup>*</sup></label>
								<div class="controls">
									<div class="input-append date">
										<input type="text" name="birthDate" id="birthDate" readonly="readonly"
											<c:choose> 
												<c:when test="${empty formBean.birthDate}">value='01-01-2010'</c:when>
												<c:otherwise>value="${formBean.birthDate}"</c:otherwise>
											</c:choose> />
											<span class="add-on"><i class="icon-th"></i></span>
									</div>
								</div>
							</div>

							<h4>Your address</h4>
							<div class="control-group">
								<label class="control-label" for="company">Company</label>
								<div class="controls">
									<input type="text" name="company" id="company" placeholder="company" value="${formBean.company}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="address1">Address<sup>*</sup></label>
								<div class="controls">
									<input type="text" name="address1" id="address1" placeholder="Adress" value="${formBean.address1}" /> 
									<span>Street address, P.O. box, company name, c/o</span>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="address2">Address	(Line 2)<sup>*</sup></label>
								<div class="controls">
									<input type="text" name="address2" id="address2" placeholder="Adress line 2" value="${formBean.address2}" /> 
									<span>Apartment, suite, unit, building, floor, etc.</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="city">City<sup>*</sup></label>
								<div class="controls">
									<input type="text" name="city" id="city" placeholder="city"	value="${formBean.city}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="postcode">Zip / Postal Code<sup>*</sup></label>
								<div class="controls">
									<input type="text" name="postcode" id="postcode" placeholder="Zip / Postal Code" value="${formBean.postcode}" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="aditionalInfo">Additional information</label>
								<div class="controls">
									<textarea name="aditionalInfo" id="aditionalInfo" cols="26" rows="3" placeholder="Additional information">${formBean.additionalInfo}</textarea>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="phone">Phone number <sup>*</sup></label>
								<div class="controls">
									<input type="text" name="phone" id="phone" placeholder="Phone number" value="${formBean.phone}" />
								</div>
							</div>
							<%-- custom tag for captcha --%>
							<shop:captcha />
							<div class="control-group">
								<label class="control-label" for="phone">Avatar</label>
								<div class="controls">
									<input type="file" name="avatar" id="avatar" placeholder="Path to image" accept="image/x-png, image/gif, image/jpeg" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<span class="label"><sup>*</sup>Required field</span>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">  
									<input class="btn btn-large btn-success" type="submit" value="Register" />
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Footer =============================== -->
	<footer>
		<%@ include file="/WEB-INF/jspf/footer.jspf"  %>
	</footer>
	<!-- Footer end =============================== -->
</body>
</html>