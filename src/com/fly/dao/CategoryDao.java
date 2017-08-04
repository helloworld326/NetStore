package com.fly.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.fly.domain.Category;
import com.fly.domain.Product;
import com.fly.utils.DataSourceUtils;

public class CategoryDao {
	
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
	}
	
	public Category findCategoryByCid(Product product) throws SQLException{
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		return queryRunner.query(sql, new BeanHandler<Category>(Category.class), product.getCid());
	}
}
