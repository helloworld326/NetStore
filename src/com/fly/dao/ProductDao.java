package com.fly.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.fly.domain.Product;
import com.fly.utils.DataSourceUtils;
import com.fly.vo.PageBean;

public class ProductDao {
	public List<Product> findHotProduct() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? limit ?, ?";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
	}

	public List<Product> findNewProduct() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit  ?, ?";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
	}

	public List<Product> findProductByKey(String key) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pname like ? limit 0, 8";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), "%" + key + "%");
	}

	public List<Product> findProductByCid(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ?";
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), product.getCid());
	}

	public PageBean<Product> pageProductListByCid(PageBean<Product> pageBean, int index) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sqlCount = "select count(*) from product where cid = ?";
		Long l = (Long) queryRunner.query(sqlCount, new ScalarHandler(), pageBean.getCid());
		pageBean.setTotalCount(l.intValue());
		String sqlProduct = "select * from product where cid = ? limit ?, 12";
		List<Product> productList = queryRunner.query(sqlProduct, new BeanListHandler<Product>(Product.class),
				pageBean.getCid(), index);
		pageBean.setCurrentCount(productList.size());
		pageBean.setProductList(productList);
		return pageBean;
	}

	public Product findProductByPid(Product product) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		if(product.getPid() == null && product.getPname() != null){
			String sql0 = "select * from product where pname = ?"; // 专用于搜索框搜索
			product = queryRunner.query(sql0, new BeanHandler<Product>(Product.class), product.getPname());
		}
		String sql = "select * from product where pid = ?";
		return queryRunner.query(sql, new BeanHandler<Product>(Product.class), product.getPid());
	}
	
/*	// 表中不存在该商品记录，直接插入
	public int insertProductRecord(ProductRecord productRecord) throws SQLException{
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into productrecord values(?, ?)";
		int updateCount = queryRunner.update(sql, productRecord.getPid(),  productRecord.getVisitTime());
		return updateCount;
	}
	
	// 表中是否存在该商品，若存在返回该商品的记录信息，否则返回null
	public boolean isExistProductRecord(ProductRecord productRecord) throws SQLException{
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from productrecord where pid = ?";
		ProductRecord record = queryRunner.query(sql, new BeanHandler<ProductRecord>(ProductRecord.class), productRecord.getPid());
		if(record != null){
			return true;
		}
		return false;
	}

	public int updateProductRecord(ProductRecord productRecord) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update productrecord set visitTime = ? where pid = ?";
		int updateCount = queryRunner.update(sql, productRecord.getVisitTime(), productRecord.getPid());
		return updateCount;
	}
	
	public List<ProductRecord> findProductRecord(ProductRecord productRecord) throws SQLException{
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from productrecord order by visitTime desc";
		return queryRunner.query(sql, new BeanListHandler<ProductRecord>(ProductRecord.class));
	}*/
	
}
