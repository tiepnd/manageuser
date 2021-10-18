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
import manageuser.logics.TblDetailUserJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblDetailUserJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Constant;

/**
 * Controller xử lý cho màn hình ADM004 mode edit
 * 
 * @author tiepnd
 */
public class EditUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	/**
	 * Xử lý hiển thị ra ADM004, khi click vào button Xác  nhận  của ADM003
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Khởi tạo và lấy session hiện tại
			HttpSession session = request.getSession();
			//Lấy key03 từ session, khi validate thành công ở ADM003 set 1 key03 lên session
			Object key03 = session.getAttribute(Constant.KEY03);
			//Nếu chưa qua MH ADM003 thì trở về màn hình ADM002
			//require yêu cầu nếu muốn vào ADM004 phải qua được validate màn hình ADM003, nếu không có key03 thì di chuyển về màn ADM002
			if (key03 == null) {
				//sendredirect đến url mapping với ListUserController
				response.sendRedirect(request.getContextPath() + Constant.LISTUSER_DO);
			} else {
				//remove ket03 trên session, nếu không remove người qua ADM003 1 lần, lần sau nhập link vào ADM004 vẫn vào bt
				session.removeAttribute(Constant.KEY03);
				//Lấy keyUser từ request khi validate thành công sẽ sendredirect đến url editUserConfirmController.do?keyUser=" + keyUser
				String keyUser = request.getParameter(Constant.KEY_USER);
				//Lấy userInfor từ session theo keyUser
				UserInforEntities userInfor = (UserInforEntities)session.getAttribute(keyUser);
				//Set keyUser lên request để sang ADM004 set lên query string trong 2 trường hợp click back về màn ADM003, và ok sang màn ADM005 còn lấy được userInfor trên session
				request.setAttribute(Constant.KEY_USER, keyUser);
				//Set userInfor lên request để hiển thị thông tin edit user lên màn ADM004
				request.setAttribute(Constant.USER_INFOR, userInfor);
				//Tạo đối tượng RequestDispatcher để chuyển tiếp sang màn ADM004
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM004_PATH);
				//Chuyển tiếp đến trang ADM004
				requestDispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			System.out.println("EditUserConfirmController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}

	/**
	 * Xử lý khi click OK ở ADM004
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	
			//Khởi tạo và lấy session hiện tại
			HttpSession session = request.getSession();
			//Lấy keyUser từ request khi validate thành công sẽ sendredirect đến url addUserConfirmController.do?idUserInfor=" + idUserInfor
			String keyUser = request.getParameter(Constant.KEY_USER);
			//lấy userInfor theo keyUser từ session
			UserInforEntities userInfor =  (UserInforEntities)session.getAttribute(keyUser);
			//remove userInfor trên session
			session.removeAttribute(keyUser);
			//tạo đối tượng userLogic
			TblUserLogic userLogic = new TblUserLogicImpl();
			//lấy userId từ userInfor
			int userId = userInfor.getUserId();
			//Lấy email để check Existed
			String email = userInfor.getEmail();
			//Nếu không tồn tại user trong DB có user_id = userId, để tái hiện vào đến ADM004 sau đó xóa user cần edit trong DB
			//Nếu đã tồn tại email trong DB
			if (!userLogic.checkExistUserById(userId) || userLogic.checkExistedEmail(userId, email)) {
				//sendredirect đến url systemError.do set parameter errorCode vào query string
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER013);
				//thoát khởi phương thức
				return;
			}
			//tạo đối tượng userLogic
			TblDetailUserJapanLogic detailUserJapanLogic = new TblDetailUserJapanLogicImpl();
			//check existed userId trong bảng tbl_detail_user_japan
			boolean existedUserId = detailUserJapanLogic.checkExistDetailUserById(userId);
			//set existedUserId cho userInfor để vào để kiểm tra khi thao tác vào bảng tbl_detail_user_japan trường hợp edit
			userInfor.setExistedUserId(existedUserId);
			//update thông tin user vào DB
			boolean updateSuccess = userLogic.updateUser(userInfor);
			//nếu update thông tin user vào DB thành công
			if (updateSuccess) {
				//sendredirect đến url success.do set parameter type vào query string
				response.sendRedirect(request.getContextPath() + Constant.SUCCESS_DO + Constant.EDIT_SUCCESS);
			} else {//nếu update thông tin user vào DB không thành công
				//sendredirect đến url success.do set parameter type vào query string
				response.sendRedirect(request.getContextPath() + Constant.SUCCESS_DO + Constant.ERROR);
			}
		} catch (Exception e) {
			System.out.println("EditUserConfirmController:doPost:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
}
