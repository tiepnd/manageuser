/**
 * Copyright(C) 2020  Luvina SoftWare
 * MessageProperties.java, Jul 8, 2020 tiepnd
 */
package manageuser.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Filter xử lý check Login 
 * 
 * @author tiepnd
 */
public class LoginFilter implements Filter {
	/**
	 * Hàm xử lý check Login của các màn hình từ ADM002 đến ADM006
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//ép kiểu sang HttpServletRequest để sử dụng phương thức getServletPath
		HttpServletRequest req = (HttpServletRequest) request;
		//ép kiểu sang HttpServletResponse để sử dụng phương thức sendRedirect
		HttpServletResponse rep = (HttpServletResponse) response;
		//lấy đường dẫn đến servlet
	   	String servletPath = req.getServletPath();
	   		//Check login
			//Khởi tạo và lấy session hiện tại để check login
			HttpSession session = req.getSession();
			try {
				// kiểm tra login từ MH ADM002 đến ADM006
				if (Common.compareString( Constant.LOGIN_DO, servletPath) || Common.compareString(Constant.SYSTEM_ERROR_DO, servletPath) || Common.checkLogin(session)) {
					//cho qua filter
					chain.doFilter(request, response);
				} else {//nếu chưa login chuyển đến MH login
					//sendRedirect đến url mapping với LoginController
					rep.sendRedirect(req.getContextPath() + Constant.LOGIN_DO);
				}
			} catch (Exception e) {
				System.out.println("LoginFilter:doFilter:" + e.getMessage());
				///sendredirect đến url systemError.do set parameter errorCode vào query string
				rep.sendRedirect(req.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);		
			}	
	}
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	
	@Override
	public void destroy(){
		
	}
}
