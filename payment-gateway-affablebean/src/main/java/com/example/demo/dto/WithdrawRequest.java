package com.example.demo.dto;

public record WithdrawRequest(String accountNumber, double amount, String username,String code) {

}
