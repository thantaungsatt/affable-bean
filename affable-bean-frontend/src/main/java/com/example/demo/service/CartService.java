package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDetailsInfo;
import com.example.demo.model.CartItem;

@Service
public class CartService {
	private Set<CartItem> cartItems = new HashSet<>();

	public CartItem toCatrItem(ProductDetailsInfo productDetailsInfo) {
		return new CartItem(productDetailsInfo.getName(), productDetailsInfo.getPrice(), 1);
	}

	public int cartSize() {
		return cartItems.size();
	}

	public void clearCart() {
		cartItems.clear();
	}

	public void removeFromCart(String name) {
		cartItems.stream().filter(i -> !i.getName().equals(name)).collect(Collectors.toSet());
	}

	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	public void addToCart(ProductDetailsInfo product) {
		cartItems.add(toCatrItem(product));
	}

	public void increaseQuantity(String name) {
		this.cartItems = this.cartItems.stream().map(item -> {
			if (item.getName().equals(name)) {
				item.setQuantity(item.getQuantity() + 1);
			}
			return item;
		}).collect(Collectors.toSet());
	}

	public void decreaseQuantity(String name) {
		this.cartItems = cartItems.stream().map(item -> {
			if (item.getName().equals(name) && item.getQuantity() > 1) {
				item.setQuantity(item.getQuantity() - 1);
			}
			return item;
		}).collect(Collectors.toSet());
	}

	public double getTotalPrice() {
		return this.cartItems.stream().map(item -> item.getPrice() * item.getQuantity())
				.mapToDouble(Double::doubleValue).sum();
	}

}
