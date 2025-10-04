package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDetailsInfo;
import com.example.demo.service.AffableBeanServivce;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/affable-bean")
public class AffableBeanController {

	private final AffableBeanServivce affableBeanServivce;

	@GetMapping("/list-products")
	public List<ProductDetailsInfo> findAllProducts() {
		return affableBeanServivce.findAllProducts();
	}

	@GetMapping("/list-products/{name}")
	public List<ProductDetailsInfo> findAllProductsByCategoryName(@PathVariable("name") String name) {
		return affableBeanServivce.findAllProductsByCategoryName(name);
	}

}
