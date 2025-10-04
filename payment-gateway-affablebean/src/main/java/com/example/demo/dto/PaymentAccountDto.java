package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentAccountDto {
	private Long id;
	private String name;
	private String accountNumber;
	private BigDecimal amount;
	public PaymentAccountDto() {
		
	}
	public PaymentAccountDto(Long id, String name, String accountNumber, BigDecimal amount) {
		super();
		this.id = id;
		this.name = name;
		this.accountNumber = accountNumber;
		this.amount = amount;
	}
	
	
	
}
