package com.example.demo.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDetailsInfo;
import com.example.demo.service.AffableBeanServivce;

@RestController
public class AffableBeanGraphqlController {
	@Autowired
	private AffableBeanServivce affableBeanServivce;
	
	@QueryMapping
	public List<ProductDetailsInfo> listAllProducts(){
		return affableBeanServivce.findAllProducts();
	}
	@QueryMapping
	public List<ProductDetailsInfo> 
		listPorductsByCaregoryName(@Argument("name")String name){
		if(affableBeanServivce
				.findAllProductsByCategoryName(name).isEmpty()) {
			return List.of();
		}
		return affableBeanServivce
				.findAllProductsByCategoryName(name);
	}

}
