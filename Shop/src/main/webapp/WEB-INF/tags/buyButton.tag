<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="productId" required="true" %>

<a href="#activity" role="button" data-toggle="modal">
	<span class="btn" onclick="basket.addToBasket(${productId})" />Add to <i class="icon-shopping-cart"></i></span>
</a>
