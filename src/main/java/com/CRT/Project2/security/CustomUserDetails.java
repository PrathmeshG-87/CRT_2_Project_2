package com.CRT.Project2.security;

public class CustomUserDetails {
	private Long userId;
	private String email;
	
	public CustomUserDetails(Long userId, String email) {
		this.userId = userId;
		this.email = email;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	

}
