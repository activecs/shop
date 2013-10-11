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
	<!-- Big carousel -->
	<div id="carouselBlk">
		<div id="myCarousel" class="carousel slide">
			<div class="carousel-inner">
				<div class="active item">
					<a href='#'> <img src="<c:url value='/resources/images/carousel/1.png' />" alt="special offers" />
					</a>
					<div class="carousel-caption">
						<h4>label</h4>
						<p>Description</p>
					</div>
				</div>
				<div class="item">
					<a href='#'> <img src="<c:url value='/resources/images/carousel/2.png' />" alt="special offers" />
					</a>
					<div class="carousel-caption">
						<h4>label</h4>
						<p>Description</p>
					</div>
				</div>
				<div class="item">
					<a href='#'> <img src="<c:url value='/resources/images/carousel/3.png' />" alt="special offers" />
					</a>
					<div class="carousel-caption">
						<h4>label</h4>
						<p>Description</p>
					</div>
				</div>
				<div class="item">
					<a href='#'> <img src="<c:url value='/resources/images/carousel/4.png' />" alt="special offers" />
					</a>
					<div class="carousel-caption">
						<h4>label</h4>
						<p>Description</p>
					</div>
				</div>
				<div class="item">
					<a href='#'> <img src="<c:url value='/resources/images/carousel/5.png' />" alt="special offers" />
					</a>
					<div class="carousel-caption">
						<h4>label</h4>
						<p>Description</p>
					</div>
				</div>
				<div class="item">
					<a href='#'> <img src="<c:url value='/resources/images/carousel/6.png' />" alt="special offers" />
					</a>
					<div class="carousel-caption">
						<h4>label</h4>
						<p>Description</p>
					</div>
				</div>
			</div>
			<a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</div>
	</div>
	<!-- Big carousel End-->
	<div id="mainBody">
		<div class="container">
			<div class="row">
				<!-- Sidebar ================================================== -->
				<%@ include file="/WEB-INF/jspf/sidebar.jspf"%>
				<!-- Sidebar end=============================================== -->
				<div class="span9">
					<div class="well well-small">
						<h4>Featured Products <small class="pull-right">200+ featured products</small></h4>
						<div class="row-fluid">
							<div id="featured" class="carousel slide">
								<div class="carousel-inner">
									<div class="item active">
										<ul class="thumbnails"><li class="span3">
												<div class="thumbnail"><i class="tag"></i> 
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/b1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4><a class="btn" href="product_details.html">VIEW</a> 
														<span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<i class="tag"></i> <a href="product_details.html"><img src="<c:url value='/resources/images/products/b1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4><a class="btn" href="product_details.html">VIEW</a> 
														<span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<i class="tag"></i> <a href="product_details.html"><img src="<c:url value='/resources/images/products/b1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<i class="tag"></i> <a href="product_details.html"><img src="<c:url value='/resources/images/products/b1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
										</ul>
									</div>
									<div class="item">
										<ul class="thumbnails">
											<li class="span3">
												<div class="thumbnail">
													<i class="tag"></i> <a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<i class="tag"></i> <a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
										</ul>
									</div>
									<div class="item">
										<ul class="thumbnails">
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
										</ul>
									</div>
									<div class="item">
										<ul class="thumbnails">
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
											<li class="span3">
												<div class="thumbnail">
													<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt=""></a>
													<div class="caption">
														<h5>Product name</h5>
														<h4>
															<a class="btn" href="product_details.html">VIEW</a> <span class="pull-right">$222.00</span>
														</h4>
													</div>
												</div>
											</li>
										</ul>
									</div>
								</div>
								<a class="left carousel-control" href="#featured" data-slide="prev">&lsaquo;</a> 
								<a class="right carousel-control" href="#featured" data-slide="next">&rsaquo;</a>
							</div>
						</div>
					</div>
					<h4>Latest Products</h4>
					<ul class="thumbnails">
						<li class="span3">
							<div class="thumbnail">
								<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt="" /></a>
								<div class="caption">
									<h5>Product name</h5>
									<p>Lorem Ipsum is simply dummy text.</p>

									<h4 style="text-align: center">
										<a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> 
										<a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a>
										<a class="btn btn-primary" href="#">$222.00</a>
									</h4>
								</div>
							</div>
						</li>
						<li class="span3">
							<div class="thumbnail">
								<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt="" /></a>
								<div class="caption">
									<h5>Product name</h5>
									<p>Lorem Ipsum is simply dummy text.</p>
									<h4 style="text-align: center">
										<a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> 
										<a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a> 
										<a class="btn btn-primary" href="#">$222.00</a>
									</h4>
								</div>
							</div>
						</li>
						<li class="span3">
							<div class="thumbnail">
								<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt="" /></a>
								<div class="caption">
									<h5>Product name</h5>
									<p>Lorem Ipsum is simply dummy text.</p>
									<h4 style="text-align: center">
										<a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> 
										<a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a> 
										<a class="btn btn-primary" href="#">$222.00</a>
									</h4>
								</div>
							</div>
						</li>
						<li class="span3">
							<div class="thumbnail">
								<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt="" /></a>
								<div class="caption">
									<h5>Product name</h5>
									<p>Lorem Ipsum is simply dummy text.</p>
									<h4 style="text-align: center">
										<a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> 
										<a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a> 
										<a class="btn btn-primary" href="#">$222.00</a>
									</h4>
								</div>
							</div>
						</li>
						<li class="span3">
							<div class="thumbnail">
								<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt="" /></a>
								<div class="caption">
									<h5>Product name</h5>
									<p>Lorem Ipsum is simply dummy text.</p>
									<h4 style="text-align: center">
										<a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> 
										<a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a> 
										<a class="btn btn-primary" href="#">$222.00</a>
									</h4>
								</div>
							</div>
						</li>
						<li class="span3">
							<div class="thumbnail">
								<a href="product_details.html"><img src="<c:url value='/resources/images/products/1.jpg' />" alt="" /></a>
								<div class="caption">
									<h5>Product name</h5>
									<p>Lorem Ipsum is simply dummy text.</p>
									<h4 style="text-align: center">
										<a class="btn" href="product_details.html"> <i class="icon-zoom-in"></i></a> 
										<a class="btn" href="#">Add to <i class="icon-shopping-cart"></i></a> 
										<a class="btn btn-primary" href="#">$222.00</a>
									</h4>
								</div>
							</div>
						</li>
					</ul>

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