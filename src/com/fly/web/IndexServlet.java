package com.fly.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fly.domain.Product;
import com.fly.service.ProductService;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService productService = new ProductService();
		// 准备热门商品 -- List<Product>
		List<Product> hotProduct = productService.findHotProduct();
		if (hotProduct != null) {
			request.setAttribute("hotProduct", hotProduct);
		}

		// 准备最新商品 -- List<Porduct>
		List<Product> newProduct = productService.findNewProduct();
		if (newProduct != null) {
			request.setAttribute("newProduct", newProduct);
		}
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
