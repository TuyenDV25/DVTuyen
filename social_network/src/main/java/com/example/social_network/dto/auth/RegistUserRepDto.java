package com.example.social_network.dto.auth;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RegistUserRepDto {

	private int id;

	@NotNull(message = "Tài khoản nhập null!")
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$", message = "Tài khoản không đúng định dạng!")
	private String username;

	@NotNull(message = "Họ nhập null!")
	private String lastName;

	@NotNull(message = "Tên nhập null!")
	private String firstName;

	@NotNull(message = "Mật khẩu null!")
	private String password;

	@Min(value = 0, message = "Tuổi không được bé hơn 0!")
	@Max(value = 200, message = "Tuổi không được lớn hơn 200!")
	private Long age;

	private String roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
