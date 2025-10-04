package com.example.demo.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.dao.PaymentAccountDao;
import com.example.demo.dto.PaymentCreateRequest;
import com.example.demo.entity.PaymentAccount;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.InsufficientException;
import com.example.demo.exception.SecurityCodeInvalidException;
import com.example.demo.interfaces.PaymentAccountInterface;

import jakarta.transaction.Transactional;

@Service
public class PaymentAccountInterfaceImpl implements PaymentAccountInterface {

	@Autowired
	private PaymentAccountDao paymentAccountDao;

	private final RestClient restClient;

	public PaymentAccountInterfaceImpl() {
		restClient = RestClient.builder().baseUrl("http://localhost:8080/api/auth").build();
	}

	@Override
	public boolean createBankAccount(String username, String password, String email) {
		PaymentAccount paymentAccount = new PaymentAccount(null, username, generateAccountNumber(username),
				BigDecimal.valueOf(0));
		PaymentAccount payment = paymentAccountDao.save(paymentAccount);
		var register = new PaymentCreateRequest(username, password, email);

		String response = restClient.post().uri("/register").contentType(MediaType.APPLICATION_JSON).body(register)
				.retrieve().body(String.class);

		return Objects.nonNull(response) ? true : false;
	}

	private String generateAccountNumber(String name) {
		StringBuilder names = new StringBuilder();
		for (char c : name.toCharArray()) {
			if (!Character.isSpaceChar(c)) {
				names.append(Character.toUpperCase(c));
			}
		}
		SecureRandom random = new SecureRandom();
		long num = (random.nextInt(100000) + 100000);
		return new StringBuilder().append("Z").append(names.toString()).append("163" + num).toString();
	}

	record AccessRequest(String username, String code) {
	}

	@Override
	public double deposit(String accountNumber, double amount, String username, String code) {
		var accessRequest = new AccessRequest(username, code);
		ResponseEntity<Void> response = grandedSecurityCode(code, accessRequest);

		if (response.getStatusCode().is2xxSuccessful()) {
			if (isAccountExit(accountNumber, username)) {
				PaymentAccount account = getAccount(accountNumber);
				account.setAmount(account.getAmount().add(BigDecimal.valueOf(amount)));
				PaymentAccount updateAccount = paymentAccountDao.saveAndFlush(account);
				return updateAccount.getAmount().doubleValue();
			}
			throw new AccountNotFoundException(accountNumber);
		} else {
			throw new SecurityCodeInvalidException(code);
		}

	}

	private ResponseEntity<Void> grandedSecurityCode(String code, AccessRequest accessRequest) {
		ResponseEntity<Void> response = null;
		try {
			response = restClient.post().uri("/access").contentType(MediaType.APPLICATION_JSON).body(accessRequest)
					.retrieve().toBodilessEntity();
		} catch (Exception e) {
			throw new SecurityCodeInvalidException(code);
		}
		return response;
	}

	private PaymentAccount getAccount(String accountNumber) {
		return paymentAccountDao.findByAccountNumber(accountNumber).get();
	}

	private boolean isAccountExit(String accountNumber, String name) {
		return paymentAccountDao.findByAccountNumberAndName(accountNumber, name).isPresent();
	}

	@Override
	public double withdraw(String accountNumber, double amount, String username, String code) {
		var accessRequest = new AccessRequest(username, code);
		ResponseEntity<Void> response = grandedSecurityCode(code, accessRequest);
		if (response.getStatusCode().is2xxSuccessful()) {
			if (isAccountExit(accountNumber, username)) {
				PaymentAccount account = getAccount(accountNumber);
				if (amount > account.getAmount().doubleValue()) {
					throw new InsufficientException();
				}
				account.setAmount(account.getAmount().subtract(BigDecimal.valueOf(amount)));
				PaymentAccount updateAccount = paymentAccountDao.saveAndFlush(account);
				return updateAccount.getAmount().doubleValue();
			}
			throw new AccountNotFoundException(accountNumber);
		}
		return amount;
	}

	@Override
	@Transactional
	public double transferAmount(String fromAccountNumber, String toAccountNumber, double amount, String username,
			String code) {
		withdraw(fromAccountNumber, amount, username, code);

		PaymentAccount payment = getAccount(toAccountNumber);
		payment.setAmount(payment.getAmount().add(BigDecimal.valueOf(amount)));
		paymentAccountDao.saveAndFlush(payment);
//		deposit(toAccountNumber, amount, username, code);

		return amount;
	}

}
