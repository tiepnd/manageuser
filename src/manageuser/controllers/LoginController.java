/**
 * Copyright(C) 2020  Luvina SoftWare
 * LoginController.java, Jul 8, 2020 tiepnd
 */
package manageuser.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * là controller xử lý cho màn hình ADM001
 * 
 * @author tiepnd
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Hàm xử lý khi click button đăng nhập ở màn hình ADM001
	 * thực hiện validate loginName và password người dùng nhập vào
	 * Di chuyển màn hình
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Tạo listErrors chứa các lỗi khi validate loginName và password
			List<String> listErrors = new  ArrayList<String>();
			//Lấy login name từ request
			String loginName = request.getParameter(Constant.LOGIN_NAME);
			//Lấy password từ request
			String password = request.getParameter(Constant.PASSWORD_PARA);
			//thực hiện validate loginName và password trả về list lỗi
			listErrors = ValidateUser.validateLogin(loginName, password);
			//nếu validate không có lỗi
			if (listErrors.isEmpty()) {
				//khởi tạo session để set loginName 
				HttpSession session = request.getSession();
				//set loginName lên session để đánh dấu người dùng đã đăng nhập
				session.setAttribute(Constant.LOGIN_NAME, loginName);
				//chuyễn tiếp đến url listUser.do mapping đến servlet class ListUserController
				response.sendRedirect(request.getContextPath() + Constant.LISTUSER_DO);
			} else {//nếu validate có lỗi
				//set listErrors lên request để chuyển tiếp sang ADM001
				request.setAttribute(Constant.LIST_ERROR, listErrors);
				//set login_Name lên request để chuyển tiếp sang ADM001 
				request.setAttribute(Constant.LOGIN_NAME, loginName);
				//tạo đối tượng requestDispatcher sử dùng phương thức forward
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM001_PATH);
				//chuyển tiếp tiếp sang trang ADM001
			    	requestDispatcher.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println("LoginController:doPost:" + e.getMessage());
			//sendredirect đến url systemError.do set mã lỗi vào query string, 
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
	/**
	 * Xử lý hiển thị màn hình Login
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//tạo đối tượng RequestDispatcher để sử dụng phương thức forward
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM001_PATH);
			//chuyển tiếp tiếp sang trang ADM001 bằng phương thức foward
		    requestDispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("LoginController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);		
			}
		}
	}
