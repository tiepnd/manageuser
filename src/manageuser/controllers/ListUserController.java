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

import manageuser.entities.MstGroupEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.ConfigProperties;
import manageuser.utils.Constant;
import manageuser.utils.MessageProperties;

/**
 * Controller xử lý cho màn hình ADM002
 * 
 * Có các chức năng sau: search, sort, paging, add (thêm mới user) và chức năng xem thông tin chi tiết của 1 user
 * @author tiepnd
 */
public class ListUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * xử lý hiển thị màn ADM002
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//lấy session hiện tại để set dữ liệu lên session, dùng cho trường hợp back về từ ADM003 hoặc ADM005
			HttpSession session = request.getSession();			
			//Khởi tạo các đối tượng MstGroupLogic để sử dụng phương thức getAllMstGroup
			MstGroupLogic mstGroupLogicImpl = new MstGroupLogicImpl();
			//Khởi tạo các đối tượng TblUserLogic để sử dụng các phương thức getTotalUsers, getListUsers
			TblUserLogic tblUserLogicImpl = new TblUserLogicImpl();
			//Set các giá trị default
			String action = Constant.ACTION_DEFAULT;
			int offset = Constant.OFFSET_DEDAULT;
			int limit =	Constant.LIMIT_DEFAULT;
			int groupId = Constant.GROUP_ID_DEFAULT;
			String fullName = Constant.FULLNAME_DEFAULT;
			String sortType = Constant.SORT_TYPE_DEFAULT;
			String valueSort = Constant.VALUE_SORT_DEFAULT;
			String valueSortByFullName = Constant.VALUE_SORT_BY_CODE_LEVEL_DEFAULT;
			String valueSortByCodeLevel = Constant.VALUE_SORT_BY_CODE_LEVEL_DEFAULT;
			String valueSortByEndDate = Constant.VALUE_SORT_BY_END_DATE_DEFAULT;
			int currentPage = Constant.CURRENT_PAGE_DEDAULT;
			int totalUser = Constant.ZERO;
			int totalPage = Constant.ZERO;
			List<Integer> listPaging = new ArrayList<Integer>();
			List<MstGroupEntities> listAllMstGroup =  new ArrayList<>();
			List<UserInforEntities> listUsers = new ArrayList<>();
			
			//Lấy action từ request để nhận biết người dùng hiển thị ADM002 với trường hợp nào
			action = request.getParameter(Constant.ACTION);
			//nếu action == null tức là hiển thị màn ADM002 với trường hợp mặc định, từ ADM001 sang ADM002
			//nếu action != null
			if (action != null) {
				//kiểm tra hành động của user
				switch (action) {
					//nếu action là search, lấy điều kiện search theo groupId và fullName từ request
					case Constant.ACTION_SEARCH:
						 groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.GROUP_ID_NOT_EXIST);
						 fullName = request.getParameter(Constant.FULLNAME);
						 break;
					//nếu action là sort, lấy điều kiện search, sort, và currentPage từ request
					case Constant.ACTION_SORT:
						 groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.GROUP_ID_NOT_EXIST);
						 fullName = request.getParameter(Constant.FULLNAME);
						 currentPage = Common.convertStringToInt(request.getParameter(Constant.CURRENT_PAGE), Constant.CURRENT_PAGE_DEDAULT);
						 sortType = request.getParameter(Constant.SORT_TYPE);
						 valueSort = request.getParameter(Constant.VALUE_SORT);
						 switch (sortType) {
							 case Constant.FULL_NAME_TYPE :
							 	 valueSortByFullName = valueSort;				
								 break;
							 case Constant.CODE_LEVEL_TYPE :
								 valueSortByCodeLevel = valueSort;
								 break;
						 	 case Constant.END_DATE_TYPE :
								 valueSortByEndDate = valueSort;
								 break;
						 }
						 break;
					//nếu là action paging lấy điều kiện search, sort, currentPage từ request
					case Constant.ACTION_PAGING:
						 groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.GROUP_ID_NOT_EXIST);
						 fullName = request.getParameter(Constant.FULLNAME);
						 currentPage = Common.convertStringToInt(request.getParameter(Constant.CURRENT_PAGE), Constant.CURRENT_PAGE_DEDAULT);
						 sortType = request.getParameter(Constant.SORT_TYPE);
						 valueSort = request.getParameter(Constant.VALUE_SORT);
						 switch (sortType) {
							 case Constant.FULL_NAME_TYPE :
							 	 valueSortByFullName = valueSort;				
								 break;
							 case Constant.CODE_LEVEL_TYPE :
								 valueSortByCodeLevel = valueSort;
								 break;
						 	 case Constant.END_DATE_TYPE :
								 valueSortByEndDate = valueSort;
								 break;
						 }
						 break;
					//nếu là action back, lấy điều kiện search, sort, currentPage từ session
					case Constant.ACTION_BACK:
						 groupId = Common.convertStringToInt(String.valueOf(session.getAttribute(Constant.GROUP_ID)), Constant.GROUP_ID_NOT_EXIST);
						 fullName = (String) session.getAttribute(Constant.FULLNAME);
						 currentPage = Common.convertStringToInt(String.valueOf(session.getAttribute(Constant.CURRENT_PAGE)), Constant.CURRENT_PAGE_DEDAULT);				
						 sortType = (String) session.getAttribute(Constant.SORT_TYPE);
						 valueSort = (String) session.getAttribute(Constant.VALUE_SORT);
						 switch (sortType) {
							 case Constant.FULL_NAME_TYPE :
							 	 valueSortByFullName = valueSort;				
								 break;
							 case Constant.CODE_LEVEL_TYPE :
								 valueSortByCodeLevel = valueSort;
								 break;
						 	 case Constant.END_DATE_TYPE :
								 valueSortByEndDate = valueSort;
								 break;
						 }
						 break;
				}
			}
			//Lấy tổng user thỏa mãn điều kiện tìm kiếm
			totalUser = tblUserLogicImpl.getTotalUsers(groupId, fullName);
			//lấy số lượng hiển thị bản ghi trên 1 trang 
			limit = Common.convertStringToInt(ConfigProperties.getValueByKey(Constant.LIMIT_USER), Constant.LIMIT_DEFAULT);
			//Nếu totalUser > số user trên 1 page thì xử lý logic paging
			if (totalUser > limit) {
				//Lấy tổng số page
				totalPage = Common.getTotalPage(totalUser, limit);
				//Nếu current page lớn hơn total page gán currentPage = totalPage;
				if (currentPage > totalPage) {
					currentPage = totalPage;
				}
				//Nếu current page nhỏ hơn 0 gán currentPage = CURRENT_PAGE_DEDAULT
				if (currentPage < Constant.ZERO) {
					currentPage = Constant.CURRENT_PAGE_DEDAULT;
				}
				//Lấy vị trí data cần lấy dữ liệu trong trong DB
				offset = Common.getOffset(currentPage, limit);
				//Lấy list paging
				listPaging = Common.getListPaging(totalUser, limit, currentPage);
			}
			//Lấy list group để hiển thị lên pulldown group
			listAllMstGroup = mstGroupLogicImpl.getAllMstGroup();

			//Set dữ liệu lên request
			request.setAttribute(Constant.LIST_ALL_MST_GROUP, listAllMstGroup);			
			request.setAttribute(Constant.FULLNAME, fullName);
			request.setAttribute(Constant.GROUP_ID, groupId);	
			//Set dữ liệu lên session dùng cho trường hợp action back
			session.setAttribute(Constant.FULLNAME, fullName);
			session.setAttribute(Constant.GROUP_ID, groupId);
			
			//nếu không có user nào thì set thông báo MSG005 lên request
			if (totalUser == 0) {
				//set câu thông báo lên request
				request.setAttribute(Constant.MSG005, MessageProperties.getValueByKey(Constant.MSG005));
			} else {//nếu có user thì tiếp tục lấy list user để hiển thị lên danh sách user và set set dữ lên request và session
				//Lấy list user để hiển thị lên danh sách user
				listUsers = tblUserLogicImpl.getListUsers(offset, limit, groupId, fullName, sortType, valueSortByFullName, valueSortByCodeLevel, valueSortByEndDate);
				request.setAttribute(Constant.LIST_USERS, listUsers);
				request.setAttribute(Constant.SORT_TYPE, sortType);
				request.setAttribute(Constant.VALUE_SORT, valueSort);
				request.setAttribute(Constant.VALUE_SORT_BY_FULLNAME, valueSortByFullName);
				request.setAttribute(Constant.VALUE_SORT_BY_CODE_LEVEL, valueSortByCodeLevel);
				request.setAttribute(Constant.VALUE_SORT_BY_END_DATE, valueSortByEndDate);
				request.setAttribute(Constant.CURRENT_PAGE, currentPage);
				request.setAttribute(Constant.LIST_PAGING, listPaging);
				request.setAttribute(Constant.TOTAL_PAGE, totalPage);
				request.setAttribute(Constant.NEXT_PAGE, Common.getNextPage(currentPage));
				request.setAttribute(Constant.PREVIOUS_PAGE, Common.getPreviousPage(currentPage));
				//Set data lên session dùng cho trường hợp action back
				session.setAttribute(Constant.SORT_TYPE, sortType);
				session.setAttribute(Constant.VALUE_SORT, valueSort);
				session.setAttribute(Constant.VALUE_SORT_BY_FULLNAME, valueSortByFullName);
				session.setAttribute(Constant.VALUE_SORT_BY_CODE_LEVEL, valueSortByCodeLevel);
				session.setAttribute(Constant.VALUE_SORT_BY_END_DATE, valueSortByEndDate);
				session.setAttribute(Constant.CURRENT_PAGE, currentPage);
			}
			//tạo đối tượng requestDispatcher sử dùng phương thức forward
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM002_PATH);
			//chuyển tiếp tiếp sang trang ADM002
			requestDispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("ListUserController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
}
