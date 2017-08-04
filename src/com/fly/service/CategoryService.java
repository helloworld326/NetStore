package com.fly.service;

import java.sql.SQLException;
import java.util.List;

import com.fly.dao.CategoryDao;
import com.fly.domain.Category;
import com.fly.domain.Product;

public class CategoryService {
	
	public List<Category> findAllCategory() {
		CategoryDao categoryDao = new CategoryDao();
		try {
			return categoryDao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Category findCategoryByCid(Product product){
		CategoryDao categoryDao = new CategoryDao();
		try {
			return categoryDao.findCategoryByCid(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
