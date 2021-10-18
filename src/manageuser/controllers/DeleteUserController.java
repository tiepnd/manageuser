/**
 * Copyright(C) 2020  Luvina SoftWare
 * MessageProperties.java, Jul 8, 2020 tiepnd
 */
package manageuser.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manageuser.entities.TblUserEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Controller xử lý xóa user
 * 
 * @author tiepnd
 */
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Xử lý khi click button xóa ở màn hình ADM005
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//tạo đối tượng userLogic để sử dụng phương thức getUserById và deleteUser
		TblUserLogic userLogic = new TblUserLogicImpl();
		try {
			//lấy userId từ query string của request và convert thành int
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.ZERO);
			//lấy user từ DB bằng userId để kiểm tra user tồn tại và rule của user 
			TblUserEntities user = userLogic.getUserById(userId);
			//nếu user tồn tại
			if (user != null) {
				if (user.getRule() == Constant.USER_RULE) {//nếu rule là rule user
					//thực hiện xóa thông tin user trong 2 bảng tbl_user và tbl_detai_user_japan
					boolean deleteSuccess = userLogic.deleteUser(userId);
					//nếu xóa thành công
					if (deleteSuccess) {
						//sendredirect đến url success.do set parameter type vào query string
						response.sendRedirect(request.getContextPath() + Constant.SUCCESS_DO + Constant.DELETE_SUCCESS);
					} else {//nếu xóa không thành công
						//sendredirect đến url success.do set parameter type vào query string
						response.sendRedirect(request.getContextPath() + Constant.SUCCESS_DO + Constant.ERROR);
					}
				} else {//nếu là admin
					//sendredirect đến url systemError.do set parameter errorCode vào query string
					response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER020);
				}
			} else {//nếu user không tồn tại
				//sendredirect đến url systemError.do set parameter errorCode vào query string
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER014);
			}
		} catch (Exception e) {
			System.out.println("DeleteUserController:doPost:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
}
