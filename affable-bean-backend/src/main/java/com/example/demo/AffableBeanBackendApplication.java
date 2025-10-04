package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.dao.ProductDao;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class AffableBeanBackendApplication {
	private final ProductDao productDao;
	@Bean
	public ApplicationRunner runner() {
		return r ->{
			productDao
			.findAllProductsDetailsInfoByCategoryName("bakery")
			.forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(AffableBeanBackendApplication.class, args);
	}

}
