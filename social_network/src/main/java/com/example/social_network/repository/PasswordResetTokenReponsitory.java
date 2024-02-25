package com.example.social_network.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.social_network.entity.PasswordResetToken;

public interface PasswordResetTokenReponsitory extends CrudRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);

}
