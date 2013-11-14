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
						<li class="active">Basket</li>
					</ul>
					<h3>Your requisites</h3>
					<hr class="soft">
					
					<!-- Products list -->
					<table class="table table-bordered">
		              <thead>
		                <tr>
		                  <th>Product</th>
		                  <th>Description</th>
						  <th>Price</th>
		                  <th>Discount</th>
		                  <th>Tax</th>
		                  <th>Total</th>
						</tr>
		              </thead>
		              <tbody>
		              	<c:forEach items="${basket.products}" var="basketItem">
			              	<c:set value="${basketItem.key}" var="product"  />
			              	<c:set value="${basketItem.value}" var="quantity" />
			              	<tr>
			                  <td>
			                  	${product.manufacturer} ${product.name} 
			                  	<img width="60" src="<c:url value='/product/${product.photo}'/>" />
			                  </td>
			                  <td>${product.description}
			                  <td>$${product.price}</td>
			                  <td>$0.00</td>
			                  <td>$0.00</td>
			                  <td id="total${product.id}">$${product.price*quantity}</td>
			                </tr>
		              	</c:forEach>
						 <tr>
		                  <td colspan="5" style="text-align:right"><strong>TOTAL =</strong></td>
		                  <td class="label label-important" style="display:block"><strong><shop:basketCost /></strong></td>
		                </tr>
						</tbody>
		            </table>
					<!-- Products list END -->
					
					<div id="errors">
						<c:forEach var="errorMessage" items="${requestScope.errors}">
							<div class='alert alert-info fade in'>
								<button type='button' class='close' data-dismiss='alert'>×</button>
								<strong>${errorMessage}</strong>
							</div>
						</c:forEach>
					</div>
					
					<!-- Requisites -->
					<table class="table table-bordered">
					 <tr><th>ESTIMATE YOUR SHIPPING AND PAYMENT METHOD</th></tr>
					 <tr> 
					 <td>
						<form class="form-horizontal" id="requisites-form" method="post" action="<c:url value='/requisites'/>" >
						  <div class="control-group">
							<label class="control-label" for="paymentMethod">Payment method</label>
							<div class="controls">
							  <select id="paymentMethod" name="paymentMethod">
							  	<c:forEach var="paymentMethod" items="${requestScope.paymentMethods}">					  	
							  		<option value="${paymentMethod}" <c:if test="${formBean.paymentMethod eq paymentMethod}">selected</c:if>>${paymentMethod.value}</option>
							  	</c:forEach>
							  </select>
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label" for="city">City</label>
							<div class="controls">
							  <input type="text" id="city" name="city" placeholder="city" value="${formBean.city}">
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label" for="address">Address</label>
							<div class="controls">
							  <input type="text" id="address" name="address" placeholder="street, apartment" value="${formBean.address}">
							</div>
						  </div>
						  <div class="control-group">
							<label class="control-label" for="phone">Phone</label>
							<div class="controls">
							  <input type="text" id="phone" name="phone" placeholder="phone number" value="${formBean.phone}">
							</div>
						  </div>
						  <div class="control-group">
							<div class="controls">
							  <button type="submit" class="btn">ESTIMATE</button>
							</div>
						  </div>
						</form>				  
					  </td>
					  </tr>
		            </table>	
					<!-- Requisites End -->
					<a href="<c:url value='/basket' />" class="btn btn-large"><i class="icon-arrow-left"></i>Go to Basket</a>
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