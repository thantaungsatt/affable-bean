package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Otp;

public interface OtpDao extends JpaRepository<Otp, Long>{
	Optional<Otp> findByUsername(String username);
	Optional<Otp> findByCodeAndUsername(String code, String username);
}
