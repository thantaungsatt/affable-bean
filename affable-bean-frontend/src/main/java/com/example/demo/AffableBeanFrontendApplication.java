package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.service.AffableBeanService;

@SpringBootApplication
public class AffableBeanFrontendApplication {

	@Autowired
	private AffableBeanService affableBeanService;
	@Bean
	public ApplicationRunner runner() {
		return r ->{
			affableBeanService.getProductByCategoryName("dairy")
			.forEach(System.out::println);;
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AffableBeanFrontendApplication.class, args);
	}

}
