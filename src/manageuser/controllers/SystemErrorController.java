/**
 * Copyright(C) 2020  Luvina SoftWare
 * MessageProperties.java, Jul 8, 2020 tiepnd
 */
package manageuser.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Controller xử lý cho màn hình system error
 * @author tiepnd
 */
public class SystemErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	/**
	 * xử lý hiển thị màn hình System Error
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Lấy errorCode từ request
			String errorCode = request.getParameter(Constant.ERROR_CODE);
			//Set errorCode lên request để chuyển tiếp sang MH system error
			request.setAttribute(Constant.MESSAGE_ERROR, MessageErrorProperties.getValueByKey(errorCode));
			//tạo đối tượng requestDispatcher sử dùng phương thức forward
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.SYSTEM_ERROR_PATH);
			//chuyển tiếp tiếp trang
		    requestDispatcher.forward(request, response);
		//bắt lỗi
		} catch (Exception e) {
			//ghi log
			System.out.println("SystemErrorController:doGet:" + e.getMessage());
		}
	}		
}
