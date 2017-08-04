package com.fly.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fly.domain.Category;
import com.fly.domain.Product;
import com.fly.domain.User;
import com.fly.service.CategoryService;
import com.fly.service.ProductService;
import com.fly.vo.PageBean;
import com.google.gson.Gson;

@SuppressWarnings("all")
public class ProductServlet extends BaseServlet {
	// 添加商品至购物车
	public void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 用户没有登陆，需要先登陆才能将商品添加至购物车
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		// 用户已经登陆,获取参数信息
		String pid = request.getParameter("pid");
		String quantity = request.getParameter("quantity");

		Product product = new Product();
		ProductService productService = new ProductService();
		product = productService.findProductByPid(product);
	}

	// 搜索框，通过关键字搜索,ajax实现
	public void findByKey(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 搜索框按名称关键字搜索
		HttpSession session = request.getSession();
		String key = request.getParameter("key");
		ProductService productService = new ProductService();
		List<Product> listProduct2 = productService.findProductByKey(key);
		// 使用json工具将对象或者集合转换成字符串
		// JSONArray fromObject = JSONArray.fromObject(listProduct2);
		// String listProduct = fromObject.toString();
		Gson gson = new Gson();
		String listProduct = gson.toJson(listProduct2);

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(listProduct);
	}

	// 商品分页
	public void pageProductList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PageBean<Product> pageBean = new PageBean<>();
		HttpSession session = request.getSession();
		try {
			BeanUtils.populate(pageBean, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		ProductService productService = new ProductService();
		pageBean = productService.pageProductListByCid(pageBean);
		if (pageBean.getProductList() != null) {
			// 数据查询成功
			// 这里不能用request域,第二次请求之后，cid会丢失
			response.setContentType("text/html;charset=UTF-8");
			session.setAttribute("pageBean", pageBean);
			session.setAttribute("cid", pageBean.getCid()); // 实现导航栏点中时出现选中的效果
			request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		}
	}

	// 商品具体信息展示
	public void showProductInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取session
		HttpSession session = request.getSession();
		Product product = new Product();
		try {
			BeanUtils.populate(product, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		ProductService productService = new ProductService();

		Cookie[] cookies = request.getCookies();
		String historyStr = "";
		ok:
		for (Cookie cookie : cookies) {
			if("historyCookie".equals(cookie.getName())){
				String[] stringPid = null;
				if(cookie != null){
					stringPid = cookie.getValue().split("#");
					List<String> list = Arrays.asList(stringPid);
					LinkedList<String> linkedList = new LinkedList<>(list);
					if(linkedList.contains(product.getPid())){
						// cookie中包含该商品信息，先删除后添加
						linkedList.remove(product.getPid());
						linkedList.addFirst(product.getPid());
					} else {
						// cookie中不存在该商品信息;
						linkedList.addFirst(product.getPid());
					}
					for (int i = 0; i < linkedList.size(); i++) {
						historyStr += linkedList.get(i) + "#";
					}
					break ok;
				}
				// 用户第一次访问商品
				historyStr += product.getPid() + "#";
			}
		}
		try {
			Cookie cookie0 = new Cookie("historyCookie", historyStr);
			System.out.println(historyStr);
			cookie0.setMaxAge(1000 * 3600 * 24 * 30);
			cookie0.setPath("/");
			response.addCookie(cookie0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 查询商品信息
		product = productService.findProductByPid(product);
		CategoryService categoryService = new CategoryService();
		// 用于导航栏下面的索引信息
		Category category = categoryService.findCategoryByCid(product);
		if(category != null){
			response.setContentType("text/html;charset=UTF-8");
			request.setAttribute("category", category);
			request.setAttribute("product", product);
			
			request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		}
	}
}