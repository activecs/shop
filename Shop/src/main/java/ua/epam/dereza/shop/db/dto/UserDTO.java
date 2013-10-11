package ua.epam.dereza.shop.db.dto;

import java.util.Date;
import java.util.Locale;

/**
 * Instances of this class are used for interaction with the database
 * (as container for user's information).
 * 
 * @author Eduard_Dereza
 * 
 */
public class UserDTO {

	private String email;
	private String avatar;
	private Boolean enabled;
	private String firstName;
	private String lastName;
	private String password;
	private Date birthDate;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private int postCode;
	private String additionalInfo;
	private String phone;
	private Role role;
	private Locale locale;

	public static enum Role {
		USER, ADMIN;
	}

	public UserDTO() {
	}

	public UserDTO(String email, String avatar, Boolean enabled,
			String firstName, String lastName, String password, Date birthDate,
			String company, String address1, String address2, String city,
			int postCode, String additionalInfo, String phone, Role role,
			Locale locale) {
		super();
		this.email = email;
		this.avatar = avatar;
		this.enabled = enabled;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.birthDate = birthDate;
		this.company = company;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postCode = postCode;
		this.additionalInfo = additionalInfo;
		this.phone = phone;
		this.role = role;
		this.locale = locale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
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

	public int getPostCode() {
		return postCode;
	}

	public void setPostCode(int postCode) {
		this.postCode = postCode;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "UserDTO [email=" + email + ", avatar=" + avatar + ", enabled="
				+ enabled + ", firstName=" + firstName + ", lastName="
				+ lastName + ", password=" + password + ", birthDate="
				+ birthDate + ", company=" + company + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", postCode="
				+ postCode + ", additionalInfo=" + additionalInfo + ", phone="
				+ phone + ", role=" + role + ", locale=" + locale + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserDTO))
			return false;
		UserDTO other = (UserDTO) obj;
		return email.equals(other.email);
	}

}
