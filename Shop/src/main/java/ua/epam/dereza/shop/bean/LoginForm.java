package ua.epam.dereza.shop.bean;

/**
 * Bean for login form
 * 
 * @author Eduard_Dereza
 *
 */
public class LoginForm {

	private String email;
	private String password;

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
	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + "]";
	}
}
