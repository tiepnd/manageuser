/**
 * Copyright(C) 2020  Luvina SoftWare
 * LogoutController.java, Jul 8, 2020 tiepnd
 */
package manageuser.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;

/**
 * Xử lý đăng xuất
 * @author tiepnd
 */
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Hàm xử lý khi click button đăng xuất
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Khởi tạo và lấy session hiện tại
			HttpSession session = request.getSession();
			//xóa sesion
		    session.invalidate();
			//sendRedirect đến url mapping với LoginController
			response.sendRedirect(request.getContextPath() + Constant.LOGIN_DO);
		} catch (Exception e) {
			System.out.println("LogoutController:doGet " + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}

	}
}
