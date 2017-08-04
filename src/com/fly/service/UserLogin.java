package com.fly.service;

import java.sql.SQLException;

import com.fly.dao.UserDao;
import com.fly.domain.User;

public class UserLogin {
	public User userLogin(String username, String password) {
		UserDao dao = new UserDao();
		User user = null;
		try {
			user = dao.userLogin(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
