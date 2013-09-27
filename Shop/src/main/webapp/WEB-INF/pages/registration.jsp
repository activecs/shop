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
				<div id="sidebar" class="span3">
					<div class="well well-small">
						<a id="myCart" href="product_summary.html"> 
							<img src="<c:url value='/resources/images/ico-cart.png' />" alt="cart">3 Items in your cart <span class="badge badge-warning pull-right">$155.00</span>
						</a>
					</div>
					<ul id="sideManu" class="nav nav-tabs nav-stacked">
						<li class="subMenu open"><a> ELECTRONICS [230]</a>
							<ul>
								<li><a class="active" href="products.html"><i class="icon-chevron-right"></i>Cameras (100) </a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Computers, Tablets & laptop (30)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Mobile Phone (80)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Sound & Vision (15)</a></li>
							</ul></li>
						<li class="subMenu"><a> CLOTHES [840] </a>
							<ul style="display: none">
								<li><a href="products.html"><i class="icon-chevron-right"></i>Women's Clothing (45)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Women's Shoes (8)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Women's Hand Bags (5)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Men's Clothings (45)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Men's Shoes (6)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Kids Clothing (5)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Kids Shoes (3)</a></li>
							</ul></li>
						<li class="subMenu"><a>FOOD AND BEVERAGES [1000]</a>
							<ul style="display: none">
								<li><a href="products.html"><i class="icon-chevron-right"></i>Angoves (35)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Bouchard Aine & Fils (8)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>French Rabbit (5)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Louis Bernard (45)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>BIB Wine (Bag in Box) (8)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Other Liquors & Wine (5)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Garden (3)</a></li>
								<li><a href="products.html"><i class="icon-chevron-right"></i>Khao Shong (11)</a></li>
							</ul></li>
					</ul>
					<br />
				</div>
				<!-- Sidebar end=============================================== -->
				<div class="span9">
					<ul class="breadcrumb">
						<li><a href="index.html">Home</a> <span class="divider">/</span></li>
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
						<form action="/registration" class="form-horizontal" id="registration-form" method="post">
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
							<div class="control-group">
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
							<div class="control-group">
								<label class="control-label" for="captcha">Captcha <sup>*</sup></label>
								<div class="controls">
									<img alt="captcha" src="<c:url value='/captcha.png' />"><br>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="text" name="captcha" id="captcha" placeholder="Enter symbols from image" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<span class="label"><sup>*</sup>Required field</span>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="hidden" name="expectedCaptcha"	id="expectedCaptcha" />  
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