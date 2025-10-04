package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.dto.ProductDetailsInfo;

@Service
public class AffableBeanService {
	private RestClient restclient;

	@Value("${owner.account}")
	private String ownerAccount;

	@Value("10")
	private int deliveryCharge;

	@Autowired
	private CartService cartService;

	public AffableBeanService() {

	}

	public List<ProductDetailsInfo> getProductByCategoryName(String name) {
		restclient = RestClient.builder().baseUrl("http://localhost:8080/api/affable-bean").build();
		return restclient.get().uri("/list-products/{name}", name).retrieve()
				.body(new ParameterizedTypeReference<List<ProductDetailsInfo>>() {
				});
	}

	record PurchaseDto(String fromAccountNumber, String toAccountNumber, double amount, String username, String code) {
	}

	public void purchaseItems(String fromAccount, String username, String code) {
		restclient = RestClient.builder().baseUrl("http://localhost:8080/payment").build();
		var purchase = new PurchaseDto(fromAccount, ownerAccount, cartService.getTotalPrice() + deliveryCharge,
				username, code);
		restclient.post().uri("/transfer").contentType(MediaType.APPLICATION_JSON).body(purchase).retrieve()
				.body(Double.class);

	}
}
