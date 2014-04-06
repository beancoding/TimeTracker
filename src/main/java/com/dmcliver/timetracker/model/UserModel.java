package com.dmcliver.timetracker.model;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;;

public class UserModel {

	private String name;
	private String password;
	private String confirmPassword;
	
	@Length(min = 7, max = 11, message = "*The user name must be between 7-11 letters")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Pattern(regexp = "^(?=.*[^0-9a-zA-Z])(?=.*\\d)(?=.*[A-Z])\\S{8,}$", message = "*The password must contain 1 capital, 1 number and 1 symbol")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
