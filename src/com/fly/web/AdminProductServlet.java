package com.fly.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fly.domain.Category;
import com.fly.domain.Product;
import com.fly.service.AdminCategoryListService;
import com.fly.service.AdminProductListService;
import com.fly.utils.MyBeanUtils;
import com.fly.vo.ValueProduct;
@SuppressWarnings("all")
public class AdminProductServlet extends BaseServlet {
	// 添加后台商品
	public void adminAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Product product = new Product();
		
		Map<String, String[]> map = request.getParameterMap();
		MyBeanUtils.populate(product, map);
		
		// 将为封装的数据封装到表单中
		// pid
		String uuid = UUID.randomUUID().toString();
		product.setPid(uuid);
		// pimage (未学习上传，先固定地址)
		product.setPimage("products/1/c_0011.jpg");
		// pflag
		product.setPflag(0);
		
		// 设置日期
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String format = dateFormat.format(date);
		product.setPdate(format);
		
		AdminProductListService adminProductListService = new AdminProductListService();
		int countUpdate = adminProductListService.addProduct(product);
		if(countUpdate > 0){
			// 添加成功
			request.getRequestDispatcher("/adminProduct?method=adminProductList").forward(request, response);
			return ;
		}
	}
	
	// 后台商品删除
	public void adminDelProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		AdminProductListService adminProductListService = new AdminProductListService();
		int updateCount = adminProductListService.delProductByPid(pid);
		if(updateCount > 0){
			// 删除成功
			// 转发到商品展示页面 		然而不能成功  ??
//			request.getRequestDispatcher("/adminProduct?method=adminProductList").forward(request, response);
			// 重定向要该请求页面
			response.sendRedirect(request.getContextPath() + "/adminProduct?method=adminProductList");
			return ;
		}
	}
	
	// 后台商品查找
	public void adminFindProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ValueProduct valueProduct = new ValueProduct();
		try {
			BeanUtils.populate(valueProduct, request.getParameterMap());
			AdminProductListService adminProductListService = new AdminProductListService();
			List<Product> productList = adminProductListService.findProductByCondition(valueProduct);
			response.setContentType("text/html;charset=UTF-8");
			request.setAttribute("productList", productList);
			request.setAttribute("valueProduct", valueProduct);
			request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
			return ;
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	// 后台商品列表
	public void adminProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		AdminProductListService adminProductListService = new AdminProductListService();
		List<Product> productList = adminProductListService.findAllProduct(); // 返回所有商品的集合
		AdminCategoryListService adminCategoryListService = new AdminCategoryListService();
		List<Category> categoryList = adminCategoryListService.findAllCategory(); // 返回商品品种的集合
		if(productList != null){
			response.setContentType("text/html;charset=utf-8");
			session.setAttribute("productList", productList);
			session.setAttribute("categoryList", categoryList);
			request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
			return ;
		}
	}
	
	// 后台商品修改信息回显
	public void adminShowProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String pid = request.getParameter("pid");
		AdminProductListService adminProductListService = new AdminProductListService();
		Product findProduct = adminProductListService.findProduct(pid);
		if(findProduct != null){
			// 数据返回
			response.setContentType("text/html;charset=UTF-8");
			session.setAttribute("findProduct", findProduct);
			response.sendRedirect(request.getContextPath() + "/admin/product/edit.jsp");
			return ;
		}
	}
	
	// 后台商品修改
	public void adminUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Product product = new Product();
		try {
			BeanUtils.populate(product, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		AdminProductListService adminProductListService = new AdminProductListService();
		if(product != null){ // 判空
			int isUpdate = adminProductListService.updateProduct(product);
			if(isUpdate > 0){
				// 修改成功   重定向的话要写全路径名称
				response.setContentType("text/html;charset=UTF-8");
				request.getRequestDispatcher("/adminProduct?method=adminProductList").forward(request, response);
				return ;
			}
		}
	}
	
	// 批量删除数据
	public void adminBatchDelProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] pids = request.getParameterValues("pid");
		AdminProductListService adminProductListService = new AdminProductListService();
		adminProductListService.batchDelProduct(pids);
		// 删除成功后重定向到商品展示页面
		response.sendRedirect(request.getContextPath() + "/adminProduct?method=adminProductList");
//		request.getRequestDispatcher("").forward(request, response);
	}
	
}
