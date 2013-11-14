package ua.epam.dereza.shop.bean;

public class RequisitesBean {

	private String paymentMethod;
	private String city;
	private String address;
	private String phone;

	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "RequisitesBean [paymentMethod=" + paymentMethod + ", city="
				+ city + ", address=" + address + ", phone=" + phone + "]";
	}
}