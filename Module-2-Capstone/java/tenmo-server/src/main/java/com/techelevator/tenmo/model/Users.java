package com.techelevator.tenmo.model;

public class Users {
	
	private Long userId;
	private String username;
	private String passwordHash;
	
	private Users() {
	}
	
	private Users(Long userId, String username, String passwordHash) {
		this.userId = userId;
		this.username = username;
		this.passwordHash = passwordHash;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", username=" + username + ", passwordHash=" + passwordHash + "]";
	}
}
