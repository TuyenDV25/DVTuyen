package com.example.social_network.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.social_network.dto.auth.OtpGetReqDto;
import com.example.social_network.dto.auth.OtpGetResDto;
import com.example.social_network.dto.auth.OtpReqDto;
import com.example.social_network.dto.auth.OtpResDto;
import com.example.social_network.dto.auth.PasswordResetReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestReqDto;
import com.example.social_network.dto.auth.PasswordResetRequestResDto;
import com.example.social_network.dto.auth.PasswordResetResDto;
import com.example.social_network.dto.auth.RegistUserRepDto;
import com.example.social_network.dto.auth.RegistUserResDto;
import com.example.social_network.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService service;

	@PostMapping("/register")
	public ResponseEntity<RegistUserResDto> register(@RequestBody @Valid RegistUserRepDto userInfo) {
		return service.insertUser(userInfo);
	}
//abccui
	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "Welcome to User Profile";
	}
//cbsf
	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "Welcome to Admin Profile";
	}

	@PostMapping("/generateOtp")
	public ResponseEntity<OtpResDto> generateOtp(@RequestBody OtpReqDto reqDto) {
		return service.generateOTP(reqDto);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<OtpGetResDto> authenticate(@RequestBody OtpGetReqDto reqDto) {
		return service.validateOTP(reqDto);
	}

	@PostMapping("/password-reset-request")
	public ResponseEntity<PasswordResetRequestResDto> resetPassword(@RequestBody PasswordResetRequestReqDto reqDto) {
		return service.requestPasswordReset(reqDto);
	}

	@PostMapping("/password-reset")
	public ResponseEntity<PasswordResetResDto> resetNewPassword(@RequestBody PasswordResetReqDto reqDto) {
		return service.resetNewPassword(reqDto);
	}

}
