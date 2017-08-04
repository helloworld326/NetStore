package com.fly.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fly.service.UserService;

public class ActiveCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String activeCode = request.getParameter("activeCode");
		UserService service = new UserService();
		int count = service.active(activeCode);
		if (count > 0) {
			// 激活成功
			response.sendRedirect(request.getContextPath() + "/activeSuccess.jsp");
		} else {
			// 激活失败
			response.sendRedirect(request.getContextPath() + "/activeFail.jsp");
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
