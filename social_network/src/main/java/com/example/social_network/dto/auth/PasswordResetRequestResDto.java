package com.example.social_network.dto.auth;

/**
 * respond reset password request
 */
public class PasswordResetRequestResDto {

	private String linkResetPassword;

	public String getLinkResetPassword() {
		return linkResetPassword;
	}

	public void setLinkResetPassword(String linkResetPassword) {
		this.linkResetPassword = linkResetPassword;
	}

}
