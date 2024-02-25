package com.example.social_network.service;

import org.springframework.http.ResponseEntity;

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

public interface AuthService {
	public ResponseEntity<RegistUserResDto> insertUser(RegistUserRepDto reqDto);

	public ResponseEntity<PasswordResetRequestResDto> requestPasswordReset(PasswordResetRequestReqDto reqDto);

	public ResponseEntity<PasswordResetResDto> resetNewPassword(PasswordResetReqDto reqDto);

	public ResponseEntity<OtpResDto> generateOTP(OtpReqDto reqDto);

	public ResponseEntity<OtpGetResDto> validateOTP(OtpGetReqDto reqDto);
}
