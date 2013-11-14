package ua.epam.dereza.shop.bean;

import java.math.BigDecimal;

public class OrderItem {

	private Integer productId;
	private Integer quantity;
	private BigDecimal price;

	public OrderItem(Integer productId, Integer quantity, BigDecimal price) {
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	public Integer getProductId() {
		return new Integer(productId);
	}

	public Integer getQuantity() {
		return new Integer(quantity);
	}

	public BigDecimal getPrice() {
		return new BigDecimal(price.doubleValue());
	}

	@Override
	public String toString() {
		return "OrderItem [productId=" + productId + ", quantity=" + quantity
				+ ", price=" + price + "]";
	}
}
