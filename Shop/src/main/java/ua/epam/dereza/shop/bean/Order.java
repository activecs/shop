package ua.epam.dereza.shop.bean;

import java.util.Date;
import java.util.List;

public class Order {

	private Integer id;
	private Integer userId;
	private State state;
	private String details;
	private PaymentMethod method;
	private String city;
	private String address;
	private String phone;
	private Date date;
	private List<OrderItem> items;

	public static enum State {
		GOT, IN_PROGRESS, SENT, DONE, CANCELED
	}

	public static enum PaymentMethod {
		CASH("cash"), BANK_TRANSFER("bank transfer");
		private PaymentMethod(String value) {
			this.value = value;
		}

		private String value;

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	public Order() {
		super();
	}

	public Order(List<OrderItem> items) {
		this.items = items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public PaymentMethod getMethod() {
		return method;
	}

	public void setMethod(PaymentMethod method) {
		this.method = method;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", state=" + state
				+ ", details=" + details + ", method=" + method + ", city="
				+ city + ", address=" + address + ", phone=" + phone
				+ ", date=" + date + ", items=" + items + "]";
	}
}