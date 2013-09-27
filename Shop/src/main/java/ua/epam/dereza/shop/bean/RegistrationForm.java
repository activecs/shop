package ua.epam.dereza.shop.bean;

/**
 * Bean for registration's form
 * 
 * @author Eduard_Dereza
 *
 */
public class RegistrationForm {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String birthDate;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private String postcode;
	private String additionalInfo;
	private String phone;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "RegistrationBean [firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", birthDt=" + birthDate
				+ ", company=" + company + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", postCode="
				+ postcode + ", additionalInfo=" + additionalInfo + ", phone="
				+ phone + "]";
	}
}
