//package com.example.social_network.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.social_network.dto.auth.LoginResDto;
//import com.example.social_network.dto.auth.OtpReqDto;
//import com.example.social_network.dto.auth.OtpResDto;
//import com.example.social_network.service.OTPService;
//
//@RestController
//public class OTPController {
//
//	@Autowired
//	public OTPService otpService;
//
//	@GetMapping("/generateOtp")
//	public ResponseEntity<OtpResDto> generateOTP(OtpReqDto reqDto) {
//		return otpService.generateOTP(reqDto);
//	}
//
//	@RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
//	public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum) {
//
//		final String SUCCESS = "Entered Otp is valid";
//		final String FAIL = "Entered Otp is NOT valid. Please Retry!";
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String username = auth.getName();
//		// Validate the Otp
//		if (otpnum >= 0) {
//
//			int serverOtp = otpService.getOtp(username);
//			if (serverOtp > 0) {
//				if (otpnum == serverOtp) {
//					otpService.clearOTP(username);
//
//					return (SUCCESS);
//				} else {
//					return FAIL;
//				}
//			} else {
//				return FAIL;
//			}
//		} else {
//			return FAIL;
//		}
//	}
//}
