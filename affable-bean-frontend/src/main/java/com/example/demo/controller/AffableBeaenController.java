package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.ProductDetailsInfo;
import com.example.demo.model.CartItem;
import com.example.demo.service.AffableBeanService;
import com.example.demo.service.CartService;

@Controller
@RequestMapping("/ui")
public class AffableBeaenController {

	@Autowired
	private AffableBeanService affableBeanService;

	@Autowired
	private CartService cartService;

	@GetMapping("/products/view-cart")
	public String viewCart(Model model) {
		model.addAttribute("items", cartService.getCartItems());
		return "cart-view";
	}

	@GetMapping("/products/checkout")
	public String checkOutPage() {
		return "checkout";

	}

	private String categoryName;

	public ProductDetailsInfo findByName(String name) {
		return affableBeanService.getProductByCategoryName(categoryName).stream().filter(i -> i.getName().equals(name))
				.findFirst().orElse(null);
	}

	@GetMapping("/products/category/{name}")
	public String productsByCategory(@PathVariable("name") String name, Model model) {
		model.addAttribute("products", affableBeanService.getProductByCategoryName(name));
		this.categoryName = name;
		return "product";
	}

	@GetMapping("/products/add-to-cart")
	public String addToCart(@RequestParam("name") String name) {
		cartService.addToCart(findByName(name));
		return "redirect:/ui/products/category/" + categoryName;
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@ModelAttribute("cartSize")
	public int cartSize() {
		return cartService.cartSize();
	}

	@ModelAttribute("cartQuantity")
	public int cartItemQuantity() {
		return cartService.getCartItems().stream().map(CartItem::getQuantity).mapToInt(Integer::intValue).sum();
	}

	@PostMapping("/products/purchase")
	public String processCheckout(@RequestParam("fromAccount") String fromAccount,
			@RequestParam("username") String username, @RequestParam("code") String code) {
		affableBeanService.purchaseItems(fromAccount, username, code);
		affableBeanService.purchaseItems(fromAccount, username, code);
		return "redirect:/ui/success";
	}

	@RequestMapping("/success")
	public String successPage() {
		cartService.clearCart();
		return "success";
	}

	@ModelAttribute("subTotal")
	public double totalPrice() {
		return cartService.getTotalPrice();
	}

	@GetMapping("/products/increase")
	public String increaseQuantity(@RequestParam("name") String name) {
		cartService.increaseQuantity(name);
		return "redirect:/ui/products/view-cart";
	}

	@GetMapping("/products/decrease")
	public String decreaseQuantity(@RequestParam("name") String name) {
		cartService.decreaseQuantity(name);
		return "redirect:/ui/products/view-cart";
	}

	@GetMapping("/products/clear-cart")
	public String clearCart() {
		cartService.clearCart();
		return "redirect:/ui/products/view-cart";
	}
}
