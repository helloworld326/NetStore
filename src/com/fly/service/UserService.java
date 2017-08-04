package com.fly.service;

import java.sql.SQLException;

import com.fly.dao.UserDao;
import com.fly.domain.User;

public class UserService {

	public boolean register(User user) {
		UserDao dao = new UserDao();
		int count = 0;
		try {
			count = dao.userRegister(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count > 0 ? true : false;
	}

	public int active(String activeCode) {
		UserDao dao = new UserDao();
		int count = 0;
		try {
			count = dao.activeCode(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public User checkUsername(String username){
		UserDao dao = new UserDao();
		User user = null;
		try {
			user = dao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
