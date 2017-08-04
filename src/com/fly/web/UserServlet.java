package com.fly.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.fly.domain.User;
import com.fly.service.UserLogin;
import com.fly.service.UserService;
import com.fly.utils.CommonsUtils;
import com.fly.utils.MailUtils;

@SuppressWarnings("all")
public class UserServlet extends BaseServlet {
	// 验证码校验
	public void checkImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String checkImg = request.getParameter("checkImg"); // 表单传递过来的验证码
		HttpSession session = request.getSession();
		session.removeAttribute("LoginInfo");
		boolean flag = false;
		if (checkImg != null) {
			String word = (String) session.getAttribute("word"); // 后台生成的验证码
			flag = word.equalsIgnoreCase(checkImg) ? true : false;
			// true 为验证码正确，false为验证码错误
			session.setAttribute("flag", flag);
			response.getWriter().write("{\"flag\":" + flag + "}");
		}else{
			session.setAttribute("LoginInfo", "验证码错误,请重新输入");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return ;
		}
	}
	
	// 注册时用户名检验
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		if(username != null){
			UserService service = new UserService();
			User user = service.checkUsername(username);
			boolean isExist = true; // true 为不存在，可以注册
			if(user != null) // false 为存在，不能注册
				isExist = false;
			response.getWriter().write("{\"isExist\" : " + isExist + "}");
		}
	}
	
	// 用户退出功能
	public void exitLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		String referer = request.getHeader("referer");
		
		Cookie cookie_username = new Cookie("cookie_username", "");
		Cookie cookie_password = new Cookie("cookie_password", "");
		cookie_username.setMaxAge(0);
		cookie_password.setMaxAge(0);
		cookie_username.setPath(request.getContextPath());
		cookie_password.setPath(request.getContextPath());
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		// 返回该点击退出功能的页面
		response.sendRedirect(referer);
	}
	
	// 用户注册
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String word = (String) session.getAttribute("word");
		String checkImg =  request.getParameter("checkCode");
		if(!word.equalsIgnoreCase(checkImg) && word != null){
			// 验证码错误
			session.setAttribute("RegisterInfo", "验证码错误,请重新输入");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return ;
		}
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		try {
			ConvertUtils.register(new Converter() {
				
				@Override
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = format.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return date;
				}
			}, Date.class);
			
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		user.setUid(CommonsUtils.getUUID());
		user.setTelephone(null);
		user.setState(0);
		String code = CommonsUtils.getUUID();
		user.setCode(code);
		
		UserService service = new UserService();
		boolean isRegister = service.register(user);
		
		if(isRegister){
			// 注册成功,激活校验
			String mes  = "账户注册成功，点击下面链接进行激活<a href='http://localhost/NetStore/active?activeCode='>"
					+ "" + CommonsUtils.getUUID() + "点击激活</a>";
			try {
				MailUtils.sendMail("tom@flyindance.com", "tom", user.getEmail(), mes);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
		}else{
			response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
		}
	}
	
	// 用户自动登陆
	public void userAuto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession httpSession = request.getSession();
		
		// ajax异步校验验证码
		boolean flag = (boolean) httpSession.getAttribute("flag");
		if(!flag){
			httpSession.setAttribute("LoginInfo", "验证码错误,请重新输入");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return ;
		}
		
		httpSession.removeAttribute("flag");
		
		// 如果验证码正确则清空session中的验证码信息;
		httpSession.removeAttribute("LoginInfo");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserLogin login = new UserLogin();
		User user = login.userLogin(username, password);
		if(user == null){
			httpSession.setAttribute("LoginInfo", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return ;
		}else{
			String autoLogin = request.getParameter("autoLogin");
			if(autoLogin != null){
				username = URLEncoder.encode(username, "UTF-8");
				// 创建cookie信息
				Cookie cookie_username = new Cookie("cookie_username", username);				
				Cookie cookie_password = new Cookie("cookie_password", password);
				cookie_password.setMaxAge(60 * 30);
				cookie_username.setMaxAge(60 * 30);
				cookie_username.setPath(request.getContextPath());
				cookie_password.setPath(request.getContextPath());
				
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
			}
			String rememberUsername = request.getParameter("rememberUsername");
			if(rememberUsername != null){
				// 选中记住账号密码
				httpSession.setAttribute("username", username);
				httpSession.setAttribute("password", password);
			}
			httpSession.setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}
	}

	// 需求: 生成验证码
	public void userLoginCheckImg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1 生成画布(类似 纸)
		int width = 120;
		int height = 40;
		BufferedImage bufi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 3 获取画笔
		Graphics g = bufi.getGraphics();
		// 4 填充背景色
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		// 5 绘制边框
		g.setColor(Color.red);
		g.drawRect(0, 0, width - 1, height - 1);
		// 6 绘制验证码
		// 6.1 准备数据
		String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		// 6.2 准备随机对象
		Random r = new Random();
		// 7.1 声明一个变量 保存验证码
		String code = "";
		// 6.3 生成4个随机的验证码
		for (int i = 0; i < 4; i++) {
			// 6.3.2 设置随机颜色
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			// 6.3.3 设置字体
			g.setFont(new Font("黑体", Font.BOLD, 25));
			// 6.3.1 绘制到画布中
			String str = data.charAt(r.nextInt(data.length())) + "";
			g.drawString(str, 10 + i * 28, 30);
			// 7.2 每个一个 就在验证码变量中追加
			code += str;
		}
		// 8 绘制干扰线
		for (int j = 0; j < 6; j++) {
			// 8.2 设置随机颜色
			g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			// 8.1 绘制干扰线
			g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
		}
		HttpSession session = request.getSession();
		session.setAttribute("word", code);
		// 2 将画布输出到浏览器上
		ImageIO.write(bufi, "jpg", response.getOutputStream());
	}

}
