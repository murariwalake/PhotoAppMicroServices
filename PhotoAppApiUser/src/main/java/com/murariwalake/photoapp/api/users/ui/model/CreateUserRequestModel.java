package com.murariwalake.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {
	
	@NotNull(message = "First name is required")
	@Size(min = 2, message="First name should be of minimum 2 charecters ")
	private String firstName;
	
	@NotNull(message = "Last name is required")
	@Size(min = 2, message="Last name should be of minimum 2 charecters ")
	private String lastName;
	
	@NotNull(message = "Email is required")
	@Email(message = "invalid email")
	private String email;
	
	@NotNull(message = "Password is required")
	@Size(min = 4, message="Password should be of minimum 2 charecters ")
	private String password;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


}
