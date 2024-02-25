package com.example.social_network.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.social_network.entity.UserInfo;
import com.example.social_network.repository.UserInfoRepository;
import com.example.social_network.utils.CommonConstant;

@Service
public class UserInfoServiceImpl implements UserDetailsService {

	@Autowired
	private UserInfoRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userDetail = repository.findByUsername(username);

		// Converting userDetail to UserDetails
		return userDetail.map(UserInfoDetailsImpl::new)
				.orElseThrow(() -> new UsernameNotFoundException(CommonConstant.USER_NOT_FOUND + username));
	}

}
