package com.dmcliver.timetracker.domain;

import javax.persistence.*;

@Entity
@Table(name = "SysUser")
public class SysUser {

	private String userName;
	private String password;
	
	public SysUser(String userName, String password) {
		
		this.userName = userName;
		this.password = password;
	}
	
	public SysUser() {}
	
	@Id
	@Column(name = "UserName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "Password", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
