package com.java.spring.ppmtool.payload;

public class JWTLoginResponse {

	boolean sucess;
	String token;
	public JWTLoginResponse(boolean sucess, String token) {
		super();
		this.sucess = sucess;
		this.token = token;
	}
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "JWTLoginResponse [sucess=" + sucess + ", token=" + token + "]";
	}
	
}
