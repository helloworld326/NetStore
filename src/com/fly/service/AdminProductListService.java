package com.fly.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import com.fly.dao.AdminProductListDao;
import com.fly.domain.Product;
import com.fly.vo.ValueProduct;

public class AdminProductListService {

	public List<Product> findAllProduct() {
		AdminProductListDao adminProductListDao = new AdminProductListDao();
		try {
			return adminProductListDao.findAllProduct();
		} catch (IllegalAccessException | InvocationTargetException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int updateProduct(Product product) {
		AdminProductListDao adminProductListDao = new AdminProductListDao();
		try {
			return adminProductListDao.updateProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int addProduct(Product product) {
		AdminProductListDao adminProductListDao = new AdminProductListDao();
		try {
			return adminProductListDao.addProduct(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int delProductByPid(String pid) {
		AdminProductListDao adminProductListDao = new AdminProductListDao();
		try {
			return adminProductListDao.delProduct(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 查找单个产品
	public Product findProduct(String pid) {
		AdminProductListDao adminProductListDao = new AdminProductListDao();
		try {
			return adminProductListDao.findProduct(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 通过条件查询商品
	public List<Product> findProductByCondition(ValueProduct valueProduct) {
		AdminProductListDao adminProductListDao = new AdminProductListDao();
			try {
				return adminProductListDao.findProductByCondition(valueProduct);
			} catch (IllegalAccessException | InvocationTargetException | SQLException e) {
				e.printStackTrace();
			}
		return null;
	}

	public void batchDelProduct(String[] pids) {
		AdminProductListDao adminProductListDao = new AdminProductListDao();
		try {
			adminProductListDao.batchDelProduct(pids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
