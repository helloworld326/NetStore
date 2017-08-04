package com.fly.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {

	/**
	 * tomcat会先根据请求头通过对应的xml配置(所以xml并不需要更改)访问对应的servlet模块，然后去寻找service方法，从而找到baseServlet,因为他重写了httpServlet
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		try {
			// 获取method名称
			String methodName = request.getParameter("method");
			// 获取被访问对象的字节码对象
			Class clazz = this.getClass();
			// 获取指定字节码对象的指定方法对象
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			try {
				// 调用方法
				method.invoke(this, request, response);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

}
