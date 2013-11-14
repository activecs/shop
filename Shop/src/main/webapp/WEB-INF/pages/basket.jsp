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
					<h3>Your basket</h3>
					<hr class="soft">	
					
					<!-- Products list -->
					<table class="table table-bordered">
		              <thead>
		                <tr>
		                  <th>Product</th>
		                  <th>Description</th>
		                  <th>Quantity/Update</th>
						  <th>Price</th>
		                  <th>Discount</th>
		                  <th>Tax</th>
		                  <th>Total</th>
						</tr>
		              </thead>
		              <tbody>
		              	<c:forEach items="${sessionScope.basket.products}" var="basketItem">
			              	<c:set value="${basketItem.key}" var="product"  />
			              	<c:set value="${basketItem.value}" var="quantity" />
			              	<tr>
			                  <td>
			                  	${product.manufacturer} ${product.name} 
			                  	<img width="60" src="<c:url value='/product/${product.photo}'/>" />
			                  </td>
			                  <td>${product.description}
							  <td>
								<div class="input-append">
									<input readonly style="max-width:34px" id="count${product.id}" placeholder="${quantity}" size="16" type="text">
									<button onclick="basket.reduceInBasket(${product.id})" class="btn" type="button"><i class="icon-minus"></i></button>
									<button onclick="basket.addToBasket(${product.id})" class="btn" type="button"><i class="icon-plus"></i></button>
									<button onclick="basket.removeFromBasket(${product.id})" class="btn btn-danger" type="button"><i class="icon-remove icon-white"></i></button>
								</div>
							  </td>
			                  <td>$${product.price}</td>
			                  <td>$0.00</td>
			                  <td>$0.00</td>
			                  <td id="total${product.id}">$${product.price*quantity}</td>
			                </tr>
		              	</c:forEach>
						 <tr>
		                  <td colspan="6" style="text-align:right"><strong>TOTAL =</strong></td>
		                  <td class="label label-important" style="display:block"><strong><shop:basketCost /></strong></td>
		                </tr>
						</tbody>
		            </table>
					<!-- Products list END -->
			
					<a href="<c:url value='/products' />" class="btn btn-large"><i class="icon-arrow-left"></i>Continue Shopping</a>
					<c:if test="${not empty sessionScope.basket.products}">
						<a href="<c:url value='/requisites' />" class="btn btn-large pull-right" >Next<i class="icon-arrow-right"></i></a>
					</c:if>
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