package com.fly.vo;

import java.util.ArrayList;
import java.util.List;

import com.fly.domain.Product;

@SuppressWarnings("all")
public class Cart {
	
	private List<CartItem> cartList = new ArrayList<>();

	public List<CartItem> getCartList() {
		return cartList;
	}

	public void setCartList(List<CartItem> cartList) {
		this.cartList = cartList;
	}

}
