package ua.epam.dereza.shop.bean;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basket bean
 * 
 * @author Eduard_Dereza
 * 
 */
public class Basket {

	private Map<Product, Integer> products;

	public Basket() {
		products = new HashMap<>();
	}

	public synchronized void  addProduct(Product product, Integer quantity) {
		Integer qnt = products.get(product);
		qnt = qnt == null ? 0 : qnt;
		products.put(product, (qnt + quantity));
	}

	public synchronized void removeProduct(Product product) {
		products.remove(product);
	}

	public synchronized void reduceProduct(Product product) {
		Integer qnt = products.get(product);
		qnt = qnt == null ? 0 : qnt;
		qnt -= 1;
		if(qnt <= 0)
			products.remove(product);
		else
			products.put(product, qnt);
	}

	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(products);
	}

	public Integer getProductQuantity(Product product) {
		Integer quantity = products.get(product);
		return quantity == null ? 0 : quantity;
	}

	public Integer getTotalQuantity() {
		Integer quantity = new Integer(0);
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			quantity += entry.getValue();
		}

		return quantity;
	}

	public synchronized void clear() {
		products.clear();
	}

	public BigDecimal getTotalCost() {
		BigDecimal cost = new BigDecimal(0, new MathContext(2,
				RoundingMode.HALF_DOWN));
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			cost = cost.add(new BigDecimal(entry.getValue()).multiply(entry.getKey().getPrice()));
		}

		return cost;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<Product, Integer> entry : products.entrySet())
			str.append("|product ->" + entry.getKey() + " quantity ->" + entry.getValue());
		return str.toString();
	}

	public synchronized Order createOrder(){
		Order order = null;
		List<OrderItem> items = new ArrayList<>();
		OrderItem item = null;
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			Integer productId = entry.getKey().getId();
			Integer quantity = entry.getValue();
			BigDecimal price = entry.getKey().getPrice();
			item = new OrderItem(productId, quantity, price);
			items.add(item);
		}
		order = new Order(items);
		clear();
		return order;
	}
}