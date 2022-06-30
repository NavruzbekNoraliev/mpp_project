package dataaccess;

import java.io.Serializable;

final public class User implements Serializable {
	
	private static final long serialVersionUID = 5147265048973262104L;

	private String username;
	
	private String password;
	private Auth authorization;
	User(String username, String pass, Auth  auth) {
		this.username = username;
		this.password = pass;
		this.authorization = auth;
	}
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public Auth getAuthorization() {
		return authorization;
	}
	@Override
	public String toString() {
		return "[" + username + ":" + password + ", " + authorization.toString() + "]";
	}
	
}
