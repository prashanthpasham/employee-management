package com.example.demo.dao;

public class LoginDAO {
	
	private Long id;
	private String username;
	private String role;
	private String emailId;
	private String message;
	private String token;
	
	public LoginDAO() {
		
	}

	public LoginDAO(Long id, String username, String role, String emailId, String message, String token) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
		this.emailId = emailId;
		this.message = message;
		this.token = token;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginDAO [id=" + id + ", username=" + username + ", role=" + role + ", emailId=" + emailId
				+ ", message=" + message + ", token=" + token + "]";
	}

	
	
	

}
