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

import manageuser.utils.Constant;

/**
 * Filter xử lý set utf8
 * 
 * @author tiepnd
 */
public class CharsetEncodingFilter implements Filter {
	/**
	 * Hàm xử lý set utf8
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//set utf-8 cho request
		request.setCharacterEncoding(Constant.UTF_8);
		//set utf-8 cho response
		response.setCharacterEncoding(Constant.UTF_8);
		//cho qua filter
		chain.doFilter(request, response);
	}
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	
	}
	@Override
	public void destroy() {
	}
}
