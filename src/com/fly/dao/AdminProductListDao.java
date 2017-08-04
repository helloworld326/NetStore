package com.fly.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.fly.domain.Category;
import com.fly.domain.Product;
import com.fly.utils.DataSourceUtils;
import com.fly.vo.ValueProduct;

public class AdminProductListDao {

	public List<Product> findAllProduct() throws SQLException, IllegalAccessException, InvocationTargetException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		List<Product> productList = new ArrayList<>();
		String sql = "select * from product p,category c where p.cid = c.cid";
		List<Map<String,Object>> list = queryRunner.query(sql, new MapListHandler());
		if(list != null) {
			for (Map<String, Object> map : list) {
				Product product = new Product();
				BeanUtils.populate(product, map);
				Category category = new Category();
				BeanUtils.populate(category, map);
				product.setCategory(category);
				productList.add(product);
			}
		}
		return productList;

	}
	
	public int updateProduct(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update product set pname = ?, market_price = ?, pdesc = ?, is_hot = ?, shop_price = ?,cid = ? where pid = ?";
		return queryRunner.update(sql, product.getPname(), product.getMarket_price(), product.getPdesc(),
				product.getIs_hot(), product.getShop_price(), product.getCid(), product.getPid());
	}

	public int addProduct(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = { product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(),
				product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(),
				product.getCid() };
		return queryRunner.update(sql, params);
	}

	public int delProduct(String pid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid = ?";
		return queryRunner.update(sql, pid);
	}

	// 查找具体某个产品
	public Product findProduct(String pid) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ?";
		return (Product) queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	public List<Product> findProductByCondition(ValueProduct valueProduct) throws SQLException, IllegalAccessException, InvocationTargetException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product p, category c where p.cid = c.cid ";
		List<String> list = new ArrayList<String>();
		if (valueProduct.getPname() != null && valueProduct.getPname().trim().length() > 0) {
			sql += "and pname like ? ";
			list.add("%" + valueProduct.getPname().trim() + "%");
		}
		if (valueProduct.getCid() != null && valueProduct.getCid().trim().length() > 0) {
			sql += "and cid = ? ";
			list.add(valueProduct.getCid().trim());
		}
		if (valueProduct.getIs_hot() != null && valueProduct.getIs_hot().trim().length() > 0) {
			sql += "and is_hot = ? ";
			list.add(valueProduct.getIs_hot().trim());
		}
		
		List<Product> productList = new ArrayList<>();
		List<Map<String,Object>> query = queryRunner.query(sql, new MapListHandler(), list.toArray());
		for (Map<String, Object> map : query) {
			Product product = new Product();
			BeanUtils.populate(product, map);
			Category category = new Category();
			BeanUtils.populate(category, map);
			product.setCategory(category);
			productList.add(product);
		}
		return productList;
	}
	

	public void batchDelProduct(String[] pids) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from product where pid = ?";
		Object[][] params = new Object[pids.length][1];
		for (int i = 0; i < pids.length; i++) {
			params[i][0] = pids[i];
		}
		queryRunner.batch(sql, params);
	}
	
	
}
