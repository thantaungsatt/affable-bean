package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PaymentAccount;

public interface PaymentAccountDao extends JpaRepository<PaymentAccount, Long>{

	Optional<PaymentAccount> findByAccountNumber(String accountNumber);
	
	Optional<PaymentAccount> findByAccountNumberAndName(String accountNumber, String name);
}
