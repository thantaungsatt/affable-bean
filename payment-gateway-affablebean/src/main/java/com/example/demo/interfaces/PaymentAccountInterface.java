package com.example.demo.interfaces;

public interface PaymentAccountInterface {

	boolean createBankAccount(String useername, String password, String email);

	double deposit(String accountNumber, double amount, String username, String code);

	double withdraw(String accountNumber, double amount, String username, String code);

	double transferAmount(String fromAccountNumber, String toAccountNumber, double amount, String username,
			String code);
}
