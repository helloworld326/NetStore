package com.fly.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fly.domain.Category;
import com.fly.service.AdminCategoryListService;

@SuppressWarnings("all")
public class AdminCategoryServlet extends BaseServlet {
	// 后台商品种类添加
	public void adminAddCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String cname = request.getParameter("cname");
		System.out.println(cname);
		if(cname != null){
			String uuid = UUID.randomUUID().toString();
			AdminCategoryListService adminCategoryListService = new AdminCategoryListService();
			int updateCount = adminCategoryListService.addCategory(cname, uuid);
			if(updateCount > 0){ // 添加成功
				request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
			}
		}
	}
	
	// 后台商品种类展示
	public void adminCategoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminCategoryListService adminCategoryListService = new AdminCategoryListService();
		List<Category> categoryList = adminCategoryListService.findAllCategory();
		if(categoryList != null){
			response.setContentType("text/html;charset=utf-8");
			request.setAttribute("categoryList", categoryList);
			request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
		}
	}

	// 后台商品种类删除
	public void adminDelCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Category category = new Category();
		try {
			BeanUtils.populate(category, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		/**
		 *	category表中有主键是product表中外键，不能直接删除;
		 */
		if(category != null){ // 判断空指针
			AdminCategoryListService adminCategoryListService = new AdminCategoryListService();
			int isDel = adminCategoryListService.delCategory(category);
			if(isDel > 0){
				// 删除成功
				response.setContentType("text/html;charset=UTF-8");
				request.getRequestDispatcher("/adminCategoryList").forward(request, response);
			}
		}
	}
	
	// 后台商品种类展示
	public void adminShowCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Category category = new Category();
		try {
			BeanUtils.populate(category, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		AdminCategoryListService adminCategoryListService = new AdminCategoryListService();
		category = adminCategoryListService.findCategory(category);
		if(category != null){
			// 查找成功
			session.setAttribute("category", category);
			response.sendRedirect(request.getContextPath() + "/admin/category/edit.jsp");
		}
		
	}
	
	// 后台商品种类修改
	public void adminUpdateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Category category = new Category();
		try {
			BeanUtils.populate(category, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		AdminCategoryListService adminCategoryListService = new AdminCategoryListService();
		int isUpdate = adminCategoryListService.updateCategory(category);
		if(isUpdate > 0){
			// 修改成功
			response.setContentType("text/html;charset=UTF-8");
			request.getRequestDispatcher("/adminCategoryList").forward(request, response);
		}
	}
	
}
