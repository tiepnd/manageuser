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

import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Controller xử lý cho màn hình ADM005
 * 
 * @author tiepnd
 */
public class ViewUserDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Xử lý hiển thị ra màn ADM005, khi click link id ở ADM002
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//lấy userId từ request được gửi từ ADM002.jsp bẳng queryString
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.ZERO) ;
			//tạo đối tượng userLogic để dùng phương thức getUserInforByUserID, checkExistUserById
			TblUserLogic userLogic = new TblUserLogicImpl();
			//Nếu không tồn tại user trong DB có user_id = userId
			if (!userLogic.checkExistUserById(userId)) {
				//sendredirect đến url systemError.do set parameter errorCode vào query string
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER013);
			} else {
				//Lấy user infor trong DB bằng userid
				UserInforEntities userInfor = userLogic.getUserInforById(userId);
				//set userInfor lên request
				request.setAttribute(Constant.USER_INFOR, userInfor);
				//set thông báo có muốn xóa người dùng không lên request
				request.setAttribute(Constant.MESSAGE, MessageProperties.getValueByKey(Constant.MSG004));
				//tạo đối tượng requestDispatcher sử dùng phương thức forward
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM005_PATH);
				//chuyển tiếp tiếp trang
			    requestDispatcher.forward(request, response);
			}
		//bắt lỗi
		} catch (Exception e) {
			//ghi log
			System.out.println("ViewUserDetailController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
}
