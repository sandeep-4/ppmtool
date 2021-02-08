package com.java.spring.ppmtool.exception;

public class UsernameAlreadyExists {

	String username;

	public UsernameAlreadyExists(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
