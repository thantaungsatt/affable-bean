package com.example.demo.dto;

public record DepositRequest(String accountNumber, double amount, String username, String code) {}