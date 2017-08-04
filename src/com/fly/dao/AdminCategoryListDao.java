package com.fly.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.fly.domain.Category;
import com.fly.utils.DataSourceUtils;

public class AdminCategoryListDao {
	
	public List<Category> findAllCategory() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
	}
	
	public int addCategory(String cname, String uuid) throws SQLException{
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values(?, ?)";
		return queryRunner.update(sql, uuid,cname);
	}
	
	public int updateCategory(Category category) throws SQLException{
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		return queryRunner.update(sql, category.getCname(), category.getCid());
	}

	public Category findCategory(Category category) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		return (Category) queryRunner.query(sql, new BeanHandler<Category>(Category.class), category.getCid());
	}

	public int delCategory(Category category) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from category where cid = ?";
		return queryRunner.update(sql, category.getCid());
	}
	
}
