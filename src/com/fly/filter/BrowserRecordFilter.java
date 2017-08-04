package com.fly.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fly.domain.Product;
import com.fly.service.ProductService;

public class BrowserRecordFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// 商品记录初始化,将所有的浏览记录存储在一个叫做historyCookie中;
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 用来存储查找成功后的商品集合;
		HttpSession session = request.getSession();
		
		Cookie[] cookies = request.getCookies();
		Cookie historyCookie = null;
		// 浏览记录商品集合
		List<Product> productRecordList = new ArrayList<Product>();
		for (Cookie cookie : cookies) {
			if("historyCookie".equals(cookie.getName())){
				historyCookie = cookie;
				if(historyCookie != null){
					// 存在该cookie,则进行信息查询
					String stringPids = historyCookie.getValue();
					String[] pids = stringPids.split("#");
					for (String pid : pids) {
						if(!"".equals(pid)){
							// 按照pid进行商品查询
							ProductService productService = new ProductService();
							Product product = new Product();
							product.setPid(pid);
							product = productService.findProductByPid(product);
							productRecordList.add(product);
						}
					}
				}
			}
		}
		session.setAttribute("productRecordList", productRecordList);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
