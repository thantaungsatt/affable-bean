package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DepositRequest;
import com.example.demo.dto.PaymentCreateRequest;
import com.example.demo.dto.WithdrawRequest;
import com.example.demo.interfaces.PaymentAccountInterface;

@RestController
@RequestMapping("/payment")
public class PaymentAccountController {
	@Autowired
	private PaymentAccountInterface paymentInterface;

	record TransferRequest(String fromAccountNumber, String toAccountNumber, double amount, String username,
			String code) {
	}

	@PostMapping("/transfer")
	public ResponseEntity<Double> transfer(@RequestBody TransferRequest request) {
		return ResponseEntity.ok().body(paymentInterface.transferAmount(request.fromAccountNumber,
				request.toAccountNumber, request.amount, request.username, request.code));
	}

	@PostMapping("/withdraw")
	public ResponseEntity<Double> withdraw(@RequestBody WithdrawRequest request) {
		return ResponseEntity.ok().body(paymentInterface.withdraw(request.accountNumber(), request.amount(),
				request.username(), request.code()));
	}

	@PostMapping("/deposit")
	public ResponseEntity<Double> deposit(@RequestBody DepositRequest request) {
		return ResponseEntity.ok().body(paymentInterface.deposit(request.accountNumber(), request.amount(),
				request.username(), request.code()));
	}

	@PostMapping("/create-account")
	public ResponseEntity<Boolean> createPaymentAccount(@RequestBody PaymentCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(paymentInterface.createBankAccount(request.username(), request.password(), request.email()));
	}
}
