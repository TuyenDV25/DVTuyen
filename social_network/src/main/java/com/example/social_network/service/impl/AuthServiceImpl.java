package com.example.social_network.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.example.social_network.entity.PasswordResetToken;
import com.example.social_network.entity.UserInfo;
import com.example.social_network.exception.ResourceNotFoundException;
import com.example.social_network.jwt.JwtUtils;
import com.example.social_network.jwt.SecurityConstant;
import com.example.social_network.repository.PasswordResetTokenReponsitory;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.service.OTPService;
import com.example.social_network.service.AuthService;
import com.example.social_network.utils.CommonConstant;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordResetTokenReponsitory passwordResetTokenReponsitory;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private OTPService otpService;

	@Override
	public ResponseEntity<RegistUserResDto> insertUser(RegistUserRepDto reqDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		reqDto.setPassword(encoder.encode(reqDto.getPassword()));
		UserInfo userInfor = modelMapper.map(reqDto, UserInfo.class);
		repository.save(userInfor);
		return new ResponseEntity<RegistUserResDto>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<PasswordResetRequestResDto> requestPasswordReset(PasswordResetRequestReqDto reqDto) {
		UserInfo userInfoEntity = repository.findByUsername(reqDto.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstant.USER_NOT_FOUND));

		if (userInfoEntity == null) {
			throw new ResourceNotFoundException("Không tồn tại user có Email nhập");
		}

		String token = jwtUtils.generateToken(userInfoEntity.getUsername(),
				SecurityConstant.PASSWORD_RESET_EXPIRATION_TIME);

		PasswordResetToken passwordResetTokenEntity = new PasswordResetToken();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserInfo(userInfoEntity);

		passwordResetTokenReponsitory.save(passwordResetTokenEntity);

		PasswordResetRequestResDto resDto = new PasswordResetRequestResDto();
		resDto.setLinkResetPassword(SecurityConstant.URL_AUTH + token);

		return new ResponseEntity<PasswordResetRequestResDto>(resDto, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<PasswordResetResDto> resetNewPassword(PasswordResetReqDto reqDto) {
		if (jwtUtils.isTokenExpired(reqDto.getToken())) {
			return new ResponseEntity<PasswordResetResDto>(HttpStatus.BAD_REQUEST);
		}
		PasswordResetToken passwordResetTokenEntity = passwordResetTokenReponsitory.findByToken(reqDto.getToken());

		if (passwordResetTokenEntity == null) {
			return new ResponseEntity<PasswordResetResDto>(HttpStatus.FORBIDDEN);
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserInfo userInfo = passwordResetTokenEntity.getUserInfo();
		String password = encoder.encode(reqDto.getPassword());
		userInfo.setPassword(password);
		UserInfo savedUserInfo = repository.save(userInfo);

		if (savedUserInfo == null || !savedUserInfo.getPassword().equalsIgnoreCase(password)) {
			return new ResponseEntity<PasswordResetResDto>(HttpStatus.FORBIDDEN);
		}

		passwordResetTokenReponsitory.delete(passwordResetTokenEntity);

		return new ResponseEntity<PasswordResetResDto>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<OtpResDto> generateOTP(OtpReqDto reqDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(reqDto.getUserName(), reqDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		OtpResDto restDto = new OtpResDto();
		if (authentication.isAuthenticated()) {
			int otp = otpService.generateOTP(reqDto.getUserName());
			restDto.setOtp(otp);
			return new ResponseEntity<OtpResDto>(restDto, HttpStatus.OK);
		}
		return new ResponseEntity<OtpResDto>(HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<OtpGetResDto> validateOTP(OtpGetReqDto reqDto) {
		OtpGetResDto resDto = new OtpGetResDto();
		if (reqDto.getOtp() >= 0) {
			int serverOtp = otpService.getOtp(reqDto.getUserName());
			if (serverOtp > 0) {
				if (reqDto.getOtp() == serverOtp) {
					otpService.clearOTP(reqDto.getUserName());
					resDto.setToken(jwtUtils.generateToken(reqDto.getUserName(), SecurityConstant.EXPIRATION_TIME));
					return new ResponseEntity<OtpGetResDto>(resDto, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity<OtpGetResDto>(HttpStatus.BAD_REQUEST);
	}
}
