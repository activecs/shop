<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
</head>
<body>
	<!-- Modal window -->
	<%@ include file="/WEB-INF/jspf/basketModal.jspf" %>
	<!-- Modal window end-->
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
				<!-- Sidebar end ============================================== -->
				<div class="span9">
					<ul class="breadcrumb">
						<li><a href="<c:url value='/' />">Home</a><span	class="divider">/</span></li>
						<li class="active">Products</li>
					</ul>
					
					<div class="row">	  
			<div id="gallery" class="span3">
            <a href="#" title="zoom">
				<img src="" style="width:100%" alt=""/>
            </a>
			<div id="differentview" class="moreOptopm carousel slide">
                <div class="carousel-inner">
                  <div class="item active">
                   <a href="<c:url value='/product/${product.photo}' />"> <img style="width:29%" src="<c:url value='/product/${product.photo}' />" alt=""/></a>
                  </div>
                </div>
                
			  <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lt;</a>
              <a class="right carousel-control" href="#myCarousel" data-slide="next">&gt;</a> 
			  
              </div>
			  
			</div>
			<div class="span6">
				<h3> ${product.manufacturer} ${product.name} </h3>
				<small>- ${product.category}</small>
				<hr class="soft"/>
				<form class="form-horizontal qtyFrm">
				  <div class="control-group">
					<label class="control-label"><span>$${product.price}</span></label>
					<div class="controls">
						<a href="#activity" role="button" data-toggle="modal">
							<button onclick="basket.addToBasket(${product.id})" class="btn btn-large btn-primary pull-right"> Add to cart <i class=" icon-shopping-cart"></i></button>
						</a>
					</div>
				  </div>
				</form>
				
				<hr class="soft"/>
				<form class="form-horizontal qtyFrm pull-right">
				  <div class="control-group">
					<label class="control-label"><span>Color</span></label>
					<div class="controls">
					  <select class="span2">
						  <option>Black</option>
						  <option>Red</option>
						</select>
					</div>
				  </div>
				</form>
				<hr class="soft clr"/>
				<p>
					${product.description}
				</p>
				<br class="clr"/>
			<hr class="soft"/>
			</div>
			
			<div class="span9">
            <ul id="productDetail" class="nav nav-tabs">
              <li class="active"><a href="#home" data-toggle="tab">Product Details</a></li>
              <li><a data-toggle="tab"></a></li>
            </ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade active in" id="home">
							<h4>Product Information</h4>
							<table class="table table-bordered">
								<tbody>
									<tr class="techSpecRow">
										<th colspan="2">Product Details</th>
									</tr>
									<tr class="techSpecRow">
										<td class="techSpecTD1">Brand:</td>
										<td class="techSpecTD2">${product.manufacturer}</td>
									</tr>
									<tr class="techSpecRow">
										<td class="techSpecTD1">Model:</td>
										<td class="techSpecTD2">${product.name}</td>
									</tr>
								</tbody>
							</table>
						</div>
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