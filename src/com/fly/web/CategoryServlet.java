package com.fly.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fly.domain.Category;
import com.fly.domain.Product;
import com.fly.service.CategoryService;
import com.fly.service.ProductService;
import com.google.gson.Gson;

@SuppressWarnings("all")
public class CategoryServlet extends BaseServlet {
	// ???
	public void headerInit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 初始化导航栏信息
		//
		CategoryService categoryService = new CategoryService();
		List<Category> categoryList = categoryService.findAllCategory();
		if (categoryList != null) {
			Gson gson = new Gson();
			String json = gson.toJson(categoryList);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
		}
	}
	
	public void navList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Product product = new Product();
		try {
			BeanUtils.populate(product, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		ProductService productService = new ProductService();
		List<Product> productList = productService.findProductByCid(product);
		
		response.setContentType("text/html;charset=UTF-8");
		session.setAttribute("productList", productList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}
}
