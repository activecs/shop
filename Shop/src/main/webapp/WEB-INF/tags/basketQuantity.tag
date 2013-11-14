<%@ tag language="java" pageEncoding="UTF-8"%>

<span class="basketQuantity">${empty sessionScope.basket.products ? 0 : basketQuantity}</span>
