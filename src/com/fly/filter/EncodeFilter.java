package com.fly.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class EncodeFilter implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		
		HttpServletRequest myRequest = new MyRequest(httpServletRequest);

		
		chain.doFilter(myRequest, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
	
	class MyRequest extends HttpServletRequestWrapper{
		private HttpServletRequest httpServletRequest;
		
		// 判断是否被修改,以防止第二个参数开始出现第二次编码解码，从而导致乱码;
		boolean isUpdate = false;
		
		public MyRequest(HttpServletRequest request) {
			super(request); // 不能省
			this.httpServletRequest = request;
		}
		
		// 重写getParameterMap()方法
		@Override
		public Map<String, String[]> getParameterMap() {
			// 1.获得请求方式
			String method = httpServletRequest.getMethod();
			// 2.判断处理乱码
			if("post".equalsIgnoreCase(method)){
				try {
					httpServletRequest.setCharacterEncoding("UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else if("get".equalsIgnoreCase(method)){ // 可能还存在其他提交方式
				Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
				if(!isUpdate){
					// 第一次进行乱码处理
					for(Entry<String, String[]> entry : parameterMap.entrySet()){
						String[] values = entry.getValue();
						if(values != null){ // 判断非空
							for (int i = 0; i < values.length; i++) {
								try {
									values[i] = new String(values[i].getBytes("iso8859-1"),"UTF-8");
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							}
						}
					}
					isUpdate = true;
				}
				return parameterMap;
			}
			return super.getParameterMap();
		}
		
		// 获取单个参数
		@Override
		public String getParameter(String name) {
			Map<String, String[]> parameterMap = this.getParameterMap();
			String[] values = parameterMap.get(name);
			if(values != null){
				return values[0];
			}
			return null;
		}
		
		// 获取多个参数
		@Override
		public String[] getParameterValues(String name) {
			Map<String, String[]> map = this.getParameterMap();
			return map.get(name);
		}
	}
	
}

