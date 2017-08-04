package com.fly.service;

import java.sql.SQLException;
import java.util.List;

import com.fly.dao.ProductDao;
import com.fly.domain.Product;
import com.fly.vo.PageBean;

public class ProductService {

	public List<Product> findHotProduct() {
		ProductDao dao = new ProductDao();
		List<Product> hotProduct = null;
		try {
			hotProduct = dao.findHotProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProduct;
	}

	public List<Product> findNewProduct() {
		ProductDao dao = new ProductDao();
		List<Product> newProduct = null;
		try {
			newProduct = dao.findNewProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProduct;
	}

	public List<Product> findProductByKey(String key) {
		ProductDao dao = new ProductDao();
		List<Product> list = null;
		try {
			list = dao.findProductByKey(key);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据商品种类进行查找
	public List<Product> findProductByCid(Product product) {
		ProductDao dao = new ProductDao();
		try {
			return dao.findProductByCid(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 需要返回总条数告知页面页面分配多少页数;
	 */

	// 根据商品种类进行分页显示数据
	public PageBean<Product> pageProductListByCid(PageBean<Product> pageBean) {
		// 获取索引
		int index = (pageBean.getCurrentPage() - 1) * 12;
		// 封装数据
		ProductDao dao = new ProductDao();
		try {
			pageBean = dao.pageProductListByCid(pageBean, index);
			int countPage = (int) Math.ceil(1.0 * pageBean.getTotalCount() / 12);
			pageBean.setTotalPage(countPage);
			return pageBean;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Product findProductByPid(Product product) {
		ProductDao dao = new ProductDao();
		try {
			return dao.findProductByPid(product);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

/*	// 获取表中商品浏览的记录数据
	public List<Product> getProductRecord(ProductRecord productRecord) {
		ProductDao dao = new ProductDao();
		List<Product> productList = null;
		try {
			boolean isExist = dao.isExistProductRecord(productRecord);
			if (isExist) {
				// 如果表中存在浏览的商品，则直接修该访问时间
				dao.updateProductRecord(productRecord);
			} else {
				// 如果表中不存在，则直接插入
				dao.insertProductRecord(productRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*//**
		 * 开始获取商品浏览记录
		 *//*
		try {
			productList = findProductByRecord(productRecord);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productList;
	}

	// 通过浏览记录获取信息
	private List<Product> findProductByRecord(ProductRecord productRecord) throws SQLException {
		ProductDao dao = new ProductDao();
		Product product = new Product();
		List<Product> productList = new ArrayList<>();
		List<ProductRecord> productRecordList = dao.findProductRecord(productRecord);
		for (ProductRecord productRecord2 : productRecordList) { // 通过pid去查找具体的商品信息;
			product.setPid(productRecord2.getPid());
			product = dao.findProductByPid(product);
			productList.add(product);
		}
		return productList;
	}*/

}
