package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ProductDao;
import com.example.demo.dto.ProductDetailsInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AffableBeanServivce {
	
	private final ProductDao productDao;
	
	public List<ProductDetailsInfo> findAllProducts(){
		return productDao.findAllProductsDetailsInfo();
	}
	
	public List<ProductDetailsInfo> 
		findAllProductsByCategoryName(String categoryName){
		return productDao
				.findAllProductsDetailsInfoByCategoryName(categoryName);
	}

}
