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
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * Controller xử lý cho màn hình ADM004 mode add
 * 
 * @author tiepnd
 */
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Xử lý hiển thị ra ADM004, khi click vào button xác nhận và validate thông công của ADM003 
	 * 
	 * @param request yêu cầu từ browser đến server  
	 * @param response phản hồi từ server về browser  
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Khởi tạo và lấy session hiện tại để lấy key03 và userInfor từ session
			HttpSession session = request.getSession();
			//Lấy key03 từ session, để kiểm tra xem đã qua màn hình ADM003 chưa
			Object key03 = session.getAttribute(Constant.KEY03);
			//remove ket03 trên session, nếu không remove người qua ADM003 1 lần, lần sau nhập link vào ADM004 vẫn vào bt
			session.removeAttribute(Constant.KEY03);
			//Nếu chưa validate thành thành công ở màn ADM003 thì trở về màn hình trang chủ ADM002
			if (key03 == null) {
				//sendredirect đến url mapping với ListUserController
				response.sendRedirect(request.getContextPath() + Constant.LISTUSER_DO);
			} else {
				//Lấy keyUser từ request được truyền vào query string khi validate thành công
				String keyUser = request.getParameter(Constant.KEY_USER);
				//Lấy userInfor từ session  theo keyUser
				UserInforEntities userInfor = (UserInforEntities) session.getAttribute(keyUser);
				//Set keyUser lên request sang ADM004.jsp set lên query string cho 2 trường hợp click back vào click ok
				request.setAttribute(Constant.KEY_USER, keyUser);
				//Set userInfor lên request hiển thị thông tin user lên màn ADM004
				request.setAttribute(Constant.USER_INFOR, userInfor);
				//Tạo đối tượng RequestDispatcher để sử dụng phương thức forward
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM004_PATH);
				//Chuyển tiếp đến trang ADM004
				requestDispatcher.forward(request, response);
			}
			
		//bắt lỗi
		} catch (Exception e) {
			//ghi log
			System.out.println("AddUserConfirmController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
	/**
	 * Xử lý khi click vào button OK của ADM004 mode add
	 * 
	 * @param request yêu cầu từ browser đến server  
	 * @param response phản hồi từ server về browser 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Khởi tạo và lấy session hiện tại để lấy userInfor từ session
		HttpSession session = request.getSession();
		//khởi tạo đối tượng TblUserLogic để sử các phương thức check existed
		TblUserLogic userLogic = new TblUserLogicImpl();
		try {
			//Lấy keyUser từ query string của request được gửi từ ADM004.jsp
			String keyUser = request.getParameter(Constant.KEY_USER);
			//lấy userInfor từ session theo keyUser
			UserInforEntities userInfor = (UserInforEntities)session.getAttribute(keyUser);
			//remove userInfor trên session
			session.removeAttribute(keyUser);
			//Lấy loginName để check đã tồn tại 
			String loginName = userInfor.getLoginName();
			//Lấy email để check đã tồn tại 
			String email = userInfor.getEmail();
			//nếu đã tồn tại loginName hoặc email trong DB
			if (userLogic.checkExistedLoginName(loginName) || userLogic.checkExistedEmail(Constant.ZERO, email)) {
				//sendredirect đến url systemError.do set parameter errorCode vào query string
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
				//thoát khỏi phương thức
				return;
			}
			//thực hiện insert user vào DB
			Boolean insertSuccess = userLogic.createUser(userInfor);
			//Nếu insert user vào DB thành công
			if (insertSuccess) {
				//sendRedirect đến url success.do mapping với SuccessController
				response.sendRedirect(request.getContextPath() + Constant.SUCCESS_DO + Constant.ADD_SUCCESS);
			} else {
				//sendRedirect đến url success.do  mapping với SuccessController
				response.sendRedirect(request.getContextPath() + Constant.SUCCESS_DO + Constant.ERROR);
			}
		} catch (Exception e) {
			System.out.println("AddUserConfirmController:doPost:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
}
