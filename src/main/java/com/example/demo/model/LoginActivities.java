package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LoginActivities")
public class LoginActivities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private Long userId;
	
	@Column
	private String status;
	
	@Column
	private String token;
	
	public LoginActivities(){
		
	}

	public LoginActivities(Long id, Long userId, String status, String token) {
		super();
		this.id = id;
		this.userId = userId;
		this.status = status;
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginActivities [id=" + id + ", userId=" + userId + ", status=" + status + ", token=" + token + "]";
	}

	
}
