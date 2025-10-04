package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.ProductDetailsInfo;
import com.example.demo.entity.Product;

public interface ProductDao extends JpaRepository<Product,Integer>{
	@Query("""
select new com.example.demo.dto.ProductDetailsInfo(
p.id,
p.name,
p.price,
p.description,
c.name
) from Product p join p.category c
			""")
	List<ProductDetailsInfo> findAllProductsDetailsInfo();
	
	
	
	@Query("""
select new com.example.demo.dto.ProductDetailsInfo(
p.id,
p.name,
p.price,
p.description,
c.name
) from Product p join p.category c where c.name = ?1
			""")
	List<ProductDetailsInfo> 
	findAllProductsDetailsInfoByCategoryName(String categoryName);
	
	
}
