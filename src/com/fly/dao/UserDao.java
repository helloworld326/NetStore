package com.fly.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.fly.domain.User;
import com.fly.utils.DataSourceUtils;

public class UserDao {

    public User userLogin(String username, String password) throws SQLException{
        QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username = ? and password = ?";
        User user = queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
        return user;
    }
    
    public int userRegister(User user) throws SQLException{
    	QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
    	String sql = "insert into user values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	int count = queryRunner.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex()
    			, user.getState(), user.getCode());
    	return count;
    }

	public int activeCode(String activeCode) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state=? where code=?";
		int count = queryRunner.update(sql, 1, activeCode);
		return count;
	}
	
	public User checkUsername(String username) throws SQLException{
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql  = "select * from user where username = ?";
		User user = queryRunner.query(sql, new BeanHandler<User>(User.class), username);
		return user;
	}

}
