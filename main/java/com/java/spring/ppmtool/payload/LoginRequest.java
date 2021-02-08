package com.java.spring.ppmtool.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message="username cant be null")
	String username;
	@NotBlank(message="password cant be null")
	String password;
	public LoginRequest( String username,String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
