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

import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Controller xử lý các logic thông báo thành công và lỗi hệ thống
 * @author tiepnd
 */
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * xử lý hiển thị màn hình ADM006
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//lấy type từ request
			String type = request.getParameter(Constant.TYPE);
			//nếu type là add success
			if (Common.compareString(Constant.ADD_SUCCESS, type)) {
				//đọc câu thông báo từ file properties và set lên request để di chuyến sang ADM006
				request.setAttribute(Constant.MESSAGE, MessageProperties.getValueByKey(Constant.MSG001));
				//tạo đối tượng dispatcher để foward sang ADM006
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.ADM006_PATH);
				//forward sang ADM006
				dispatcher.forward(request, response);
				//thoát khởi phương thức
				return;
			}
			//nếu type là edit success
			if (Common.compareString(Constant.EDIT_SUCCESS, type)) {
				request.setAttribute(Constant.MESSAGE, MessageProperties.getValueByKey(Constant.MSG002));
				//tạo đối tượng dispatcher để foward sang ADM006
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.ADM006_PATH);
				//forward sang ADM006
				dispatcher.forward(request, response);
				//thoát khởi phương thức
				return;
			}
			//nếu type là delete success
			if (Common.compareString(Constant.DELETE_SUCCESS, type)) {
				request.setAttribute(Constant.MESSAGE, MessageProperties.getValueByKey(Constant.MSG003));
				//tạo đối tượng dispatcher để foward sang ADM006
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.ADM006_PATH);
				//forward sang ADM006
				dispatcher.forward(request, response);
				//thoát khởi phương thức
				return;
			}
			//nếu type là error
			if (Common.compareString(Constant.ERROR, type)) {
				//sendredirect đến url systemError.do set parameter errorCode vào query string
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
			}
		} catch (Exception e) {
			System.out.println("SuccessController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}

}
