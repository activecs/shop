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
				<!-- Sidebar end ============================================== -->
				<div class="span9">
					<ul class="breadcrumb">
						<li><a href="<c:url value='/' />">Home</a><span	class="divider">/</span></li>
						<li class="active">Products</li>
					</ul>

					<!-- Filter form ============================================== -->
					<form class="form-horizontal" style="margin:0">
						<fieldset>
							<!-- Form Name -->
							<legend>Refinement</legend>
							<div class="row">							
								
								<div class="span4">
									<!-- Select Sort by -->
									<div class="control-group">
										<label class="control-label" for="sortBy">Sort by</label>
										<div class="controls">
											<select id="sortBy" name="sortBy" class="input-large">
												<option />
												<option value="name" <c:if test="${formBean.sortBy == 'name'}" ><c:out value='selected'/></c:if>>Name</option>
												<option value="priceLowest" <c:if test="${formBean.sortBy == 'priceLowest'}" >selected</c:if>>Price : lowest fist</option>
												<option value="priceHigest" <c:if test="${formBean.sortBy == 'priceHigest'}" >selected</c:if>>Price : highest fist</option>
											</select>
										</div>
									</div>

									<!-- Checkboxes Manufactures -->
									<div class="control-group">
										<label class="control-label" for="manufactures">Manufactures</label>
										<div class="controls">
											<c:forEach items="${manufacturers}" var="manufacturer">
												<shop:manufacturer manufacturer="${manufacturer}" /> 
											</c:forEach>
										</div>
									</div>

									<!-- Keyword-->
									<div class="control-group">
										<label class="control-label" for="keyword">Keyword</label>
										<div class="controls">
											<input value="${formBean.keyword}" name="keyword" type="text" placeholder="type in keyword here" class="input-large">
										</div>
									</div>

									<!-- Apply -->
									<div class="control-group">
										<label class="control-label" for="apply"></label>
										<div class="controls">
											<input type="submit" value="Apply" class="btn btn-info">
										</div>
									</div>
								</div>
								
								<div class="span4 offset1">
									<!-- Select Category -->
									<div class="control-group">
										<label class="control-label" for="category">Category</label>
										<div class="controls">
											<select id="category" name="category" class="input-large">
												<option />
												<c:forEach items="${categories}" var="category">
													<option <c:if test="${formBean.category == category.name}">selected</c:if> >${category.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<!-- Price from to-->
									<div class="control-group">
										<label class="control-label" for="textinput">Price</label>
										<div class="controls">
											<input value="${formBean.priceFrom}" name="priceFrom" type="text" placeholder="from" class="input-mini"> 
											<input value="${formBean.priceTo}" name="priceTo" type="text" placeholder="to" class="input-mini">
										</div>
									</div>

									<!-- Select Item per page -->
									<div class="control-group">
										<label class="control-label" for="itemPerPage">Item	per page</label>
										<div class="controls">
											<select id="itemPerPage" name="itemPerPage" class="input-mini">
												<option />
												<option <c:if test="${formBean.itemPerPage == '6'}" >selected</c:if>>6</option>
												<option <c:if test="${formBean.itemPerPage == '12'}" >selected</c:if>>12</option>
												<option <c:if test="${formBean.itemPerPage == '18'}" >selected</c:if>>18</option>
											</select>
										</div>
									</div>
									
									<!-- View type -->
									<div id="myTab" class="pull-right">
										<a href="#listView" data-toggle="tab"><span class="btn"><i class="icon-list"></i></span></a> 
										<a href="#blockView" data-toggle="tab"><span class="btn btn-primary"><i class="icon-th-large"></i></span></a>
									</div>
								</div>
							</div>
						</fieldset>
					</form>
					<hr class="soft" />
					<!-- Filter form end ========================================== -->				
					<!-- Content ================================================== -->
					
					<c:if test="${empty products}">
						<div class='alert alert-info fade in'>
							<strong>0 results found</strong>
						</div>
					</c:if>
					
					<div class="tab-content">
						<!--  List view =========================================== -->
						<div class="tab-pane" id="listView">
						<c:forEach items="${requestScope.products}" var="product">		
    						<div class="row">								
								<div class="span2">
									<a href="<c:url value='/productDetail?id=${product.id}' />"><img src="<c:url value='/product/${product.photo}' />" /></a> 
								</div>
								<div class="span4">
									<h3>New | Available</h3>
									<hr class="soft" />
									<h5>${product.manufacturer} ${product.name}</h5>
									<p>
										${product.description}
									</p>
									<a class="btn btn-small pull-right" href="<c:url value='/productDetail?id=${product.id}' />">View Details</a>
									<br class="clr" />
								</div>
								<div class="span3 alignR">
									<form class="form-horizontal qtyFrm">
									<h3>$${product.price}</h3>
									<br /> 
										
										<a href="#" class="btn btn-large btn-primary">Add to <i class=" icon-shopping-cart"></i></a> 
									
									</form>
								</div>
							</div>
							<hr class="soft" />
						</c:forEach>
						</div>
						<!-- List view end======================================== -->
						
						<!-- Block view ========================================== -->
						<div class="tab-pane  active" id="blockView">
							<ul class="thumbnails">
								<c:forEach items="${requestScope.products}" var="product">
								<li class="span3">
									<div class="thumbnail">
										<a href="<c:url value='/productDetail?id=${product.id}' />"><img src="<c:url value='/product/${product.photo}'/>" style="height:250px;"  /></a>
										<div class="caption">
											<h5>${product.manufacturer} ${product.name}</h5>
											<h4 style="text-align: center">
												<a class="btn" href="<c:url value='/productDetail?id=${product.id}' />" ><i class="icon-zoom-in"></i></a> 
												<a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a>
												<a class="btn btn-primary">$${product.price}</a>
											</h4>
										</div>
									</div>
								</li>
								</c:forEach>
							</ul>
							<hr class="soft" />
						</div>
						<!-- Block view end======================================== -->
					</div>
					<!-- Content end=============================================== -->
					<!-- Pagination -->
					<shop:pagination />
					<!-- Pagination End -->
					<br class="clr" />
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