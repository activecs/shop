package ua.epam.dereza.shop.security;
import java.util.HashSet;
import java.util.Set;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.bean.User.Role;

public class SecurityRule {

	private String urlPattern;
	private Set<User.Role> roles;

	public SecurityRule() {
		roles = new HashSet<>();
	}
	public SecurityRule(String urlPattern, Set<Role> roles) {
		this.urlPattern = urlPattern;
		this.roles = roles;
	}
	public String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(String path) {
		urlPattern = path;
	}
	public Set<User.Role> getRoles() {
		return roles;
	}
	public void addRole(User.Role role){
		roles.add(role);
	}
	@Override
	public String toString() {
		return "Rule [urlPattern=" + urlPattern + ", roles=" + roles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlPattern == null) ? 0 : urlPattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SecurityRule))
			return false;
		SecurityRule other = (SecurityRule) obj;
		if (urlPattern == null) {
			if (other.urlPattern != null)
				return false;
		} else if (!urlPattern.equals(other.urlPattern))
			return false;
		return true;
	}
}
