package com.fly.service;

import java.sql.SQLException;
import java.util.List;

import com.fly.dao.AdminCategoryListDao;
import com.fly.domain.Category;

public class AdminCategoryListService {
	public List<Category> findAllCategory() {
		AdminCategoryListDao adminCategoryListDao = new AdminCategoryListDao();
		try {
			return adminCategoryListDao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int addCategory(String cname, String uuid){
		AdminCategoryListDao adminCategoryListDao = new AdminCategoryListDao();
		try {
			return adminCategoryListDao.addCategory(cname, uuid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateCategory(Category category){
		AdminCategoryListDao adminCategoryListDao = new AdminCategoryListDao();
		try {
			return adminCategoryListDao.updateCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 查找指定的品种
	public Category findCategory(Category category) {
		AdminCategoryListDao adminCategoryListDao = new AdminCategoryListDao();
		try {
			return adminCategoryListDao.findCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 删除指定的品种
	public int delCategory(Category category){
		AdminCategoryListDao adminCategoryListDao = new AdminCategoryListDao();
		try {
			return adminCategoryListDao.delCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
