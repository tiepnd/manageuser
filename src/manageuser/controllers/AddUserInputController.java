/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblUserDaoImpl.java, Jul 29, 2020 tiepnd
 */
package manageuser.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * Controller xử lý các logic của màn hình ADM003 mode add
 * 
 * @author tiepnd
 */
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Xử lý hiển thị ra ADM003, khi click vào button add của ADM002, back của ADM004
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//set list giá trị cho các hạng mục pulldown ở màn hình ADM003 lên request
			Common.setDataLogic(request);
			//lấy thông tin user để hiển thị lên ADM003
			UserInforEntities userInfor = getDefaultValue(request);
			//set thông tin user lên request để hiển thị lên MH ADM003
			request.setAttribute(Constant.USER_INFOR, userInfor);
			//tạo đối tượng requestDispatcher sử dùng phương thức forward
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM003_PATH);
			//chuyển tiếp tiếp trang
		    	requestDispatcher.forward(request, response);
		} catch (Exception e) {
			//ghi log
			System.out.println("AddUserInputController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
	
	/**
	 * Xử lý khi click vào button Xác nhận của ADM003 mode add, tương ứng với với action confirm
	 * 
	 * thực hiện lấy thông tin user từ người dùng nhập nhập và validate các thông tin đó
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Khởi tạo và lấy session hiện tại để set user userInfor và ket03 lên session để di chuyển sang ADM004
		HttpSession session = request.getSession();
		//Khởi tạo listError chứa danh sách lỗi khi validate
		List<String> listError = new ArrayList<>();
		try {
			//Lấy thông tin user từ người dùng nhập vào để validate
			UserInforEntities userInfor = getDefaultValue(request);
			//Validate các thông tin nhập vào, lấy về list error
			listError = ValidateUser.validateUserInfor(userInfor);
			//nếu có lỗi
			if (!listError.isEmpty()) {
				//set list giá trị cho các hạng mục pulldown ở màn hình ADM003 lên request
				Common.setDataLogic(request);
				//set userInfor lên request để giữ giá trị khi nhập lỗi
				request.setAttribute(Constant.USER_INFOR, userInfor);
				//set list lỗi lên request để hiển thị danh sách lỗi lên màn hình ADM003
				request.setAttribute(Constant.LIST_ERROR, listError);
				//Tạo đối tượng RequestDispatcher để sử dụng phương thức foward
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM003_PATH);
				//Chuyển tiếp đến ADM003
				requestDispatcher.forward(request, response);
			} else {//nếu không có lỗi
				//Tạo keyUser để nhận biết userInfor khi lấy từ session về trong trường hợp add nhiều user cùng lúc
				String keyUser = Common.createSalt();
				//set userInfor lên session theo keyUser, userInfor sẽ được lấy theo keyUser tại EditUserConfirmController rồi hiển thị lên màn ADM004
				session.setAttribute(keyUser, userInfor);
				//set key03 lên session để đánh dấu đã qua ADM003, sử dụng trong trường hợp để hiển thị được ADM004 phải qua ADM003
				session.setAttribute(Constant.KEY03, Constant.KEY03);
				//sendredirect đến url addUserConfirmController.do set parameter keyUser vào query string để sang AddUserConfirmController trong doget lấy ra 
				response.sendRedirect(request.getContextPath() + Constant.ADD_USER_CONFIRM_URL + keyUser);
			}
		} catch (Exception e) {
			System.out.println("AddUserInputController:doPost:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
	
	/**
	 * lấy thông tin user để hiển thị lên màn hình ADM003 mode add
	 * Có 3 trường hợp gọi đến hàm getDefaultValue là click add ở màn ADM002, click ok màn ADM003, click back ở màn ADM004 mode add
	 * 
	 * @param request để lấy dữ liệu từ request về
	 * @return userInfor đối tượng chứa thông tin user để hiển lên màn ADM003 mode add
	 */
	private UserInforEntities getDefaultValue(HttpServletRequest request) throws ParseException, SQLException, ClassNotFoundException {
		try {
			//Khởi tạo session để lấy thông tin user trong trường hợp back
			HttpSession session = request.getSession();
			//Lấy action từ request để phân biệt các trường hợp
			String action = request.getParameter(Constant.ACTION);
			//Khởi tạo đối tương UserInforEntities lưu thông tin user
			UserInforEntities userInfor = new UserInforEntities();
			
			//trường hợp click button back ở màn hình ADM004, lấy thông tin user từ session về set cho cho đối tượng userInfor
			if (Common.compareString(Constant.ACTION_BACK, action)) {
				//Lấy keyUser từ request được gửi từ ADM004.jsp bẳng queryString (keyUser được gửi sang ADM004.jsp khi validate thành công ở ADM003)
				String keyUser = request.getParameter(Constant.KEY_USER);
				//Lấy userInfor từ session theo keyUser (keyUser để phần biệt giữa các userInfor session, mục đích là xử lý trường hợp add nhiều user cùng lúc)
				userInfor =  (UserInforEntities)session.getAttribute(keyUser);
				// xóa userInfor trên session
				session.removeAttribute(keyUser);
				//Trả về userInfor
				return userInfor;
			}
			
			//trường hợp click button add ở màn hình ADM002, set giá trị mặc định cho cho đối tượng userInfor
			Date currentDate = Common.getCurrentDate();
			String loginName = Constant.STRING_BLANK;
			int groupId = Constant.ZERO;
			String groupName = Constant.STRING_BLANK;
			String fullName = Constant.STRING_BLANK;
			String fullNameKana = Constant.STRING_BLANK;
			//birthday dùng để ghi vào DB
			Date birthday = currentDate;
			//arrIntBirthday dùng để hiển thị lên ADM003 và validate
			ArrayList<Integer> arrIntBirthday = Common.toArrayInteger(currentDate);
			String email = Constant.STRING_BLANK;
			String tel = Constant.STRING_BLANK;
			String password = Constant.STRING_BLANK;
			String passwordConfirm = Constant.STRING_BLANK;
			String codeLevel = Constant.STRING_BLANK;
			String nameLevel = Constant.STRING_BLANK;
			//startDate dùng để ghi vào DB
			Date startDate = currentDate;
			//arrIntStartDate dùng để hiển thị lên ADM003 và validate
			ArrayList<Integer> arrIntStartDate = Common.toArrayInteger(currentDate);
			//endDate dùng để ghi vào DB
			Date endDate = currentDate;
			//arrIntEndDate dùng để hiển thị lên ADM003 và validate
			ArrayList<Integer> arrIntEndDate = Common.toArrayInteger(endDate);
			arrIntEndDate.set(Constant.INDEX_0, Constant.END_YEAR + 1);
			//dùng để validate và hiển thị lên màn hình ADM003
			String totalString = Constant.STRING_BLANK;
			int total = Constant.ZERO;
			
			//trường hợp click button ok ở màn hình ADM003, set giá trị nhập vào từ người dùng cho đối tượng userInfor
			if (Common.compareString(Constant.ACTION_CONFIRM, action)) {
				//Khởi tạo đối tượng mstGroupLogic để lấy groupName từ DB theo groupId nhập vào từ người dùng 
				MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
				//Khởi tạo đối tượng mstJapanLogic để lấy nameLevel từ DB theo codelevel nhập vào từ người dùng 
				MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
				//Lấy dữ liệu của form được submit
				loginName = request.getParameter(Constant.LOGIN_NAME);
				groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.GROUP_ID_DEFAULT);
				groupName = mstGroupLogic.getGroupName(groupId);
				fullName = request.getParameter(Constant.FULLNAME);
				fullNameKana = request.getParameter(Constant.FULLNAME_KANA);
				//lấy thông tin birthday của người dùng nhập vào
				String birthYear = request.getParameter(Constant.BIRTH_YEAR);
				String birthMonth = request.getParameter(Constant.BIRTH_MONTH);
				String birthDay = request.getParameter(Constant.BIRTH_DAY);
				int birthYearInt = Common.convertStringToInt(birthYear, Constant.START_YEAR);
				int birthMonthInt = Common.convertStringToInt(birthMonth, Constant.ONE);
				int birthDayInt = Common.convertStringToInt(birthDay, Constant.ONE);
				//arrIntBirthday dùng để hiển thị lên ADM003 và validate
				arrIntBirthday.clear();
				arrIntBirthday.add(birthYearInt);
				arrIntBirthday.add(birthMonthInt);
				arrIntBirthday.add(birthDayInt);
				//lấy birday từ các số năm tháng ngày truyền vào
				birthday = Common.toDate(birthYearInt, birthMonthInt, birthDayInt);
				email = request.getParameter(Constant.EMAIL);
				tel = request.getParameter(Constant.TEL);
				password = request.getParameter(Constant.PASSWORD_PARA);
				passwordConfirm = request.getParameter(Constant.PASSWORD_CONFIRM);
				codeLevel = request.getParameter(Constant.CODE_LEVEL);
				nameLevel = mstJapanLogic.getNameLevel(codeLevel);
				//lấy thông tin startDate của người dùng nhập vào
				String startYear = request.getParameter(Constant.START_YEAR_PARA);
				String startMonth = request.getParameter(Constant.START_MONTH);
				String startDay = request.getParameter(Constant.START_DAY);
				int startYearInt = Common.convertStringToInt(startYear, Constant.START_YEAR);
				int startMonthInt = Common.convertStringToInt(startMonth, Constant.ONE);
				int startDayInt = Common.convertStringToInt(startDay, Constant.ONE);
				//arrIntStartDate dùng để hiển thị lên ADM003 và validate
				arrIntStartDate.clear();
				arrIntStartDate.add(startYearInt);
				arrIntStartDate.add(startMonthInt);
				arrIntStartDate.add(startDayInt);
				//lấy startDate từ các số năm tháng ngày truyền vào
				startDate = Common.toDate(startYearInt, startMonthInt, startDayInt);
				//lấy thông tin endDate của người dùng nhập vào
				String endYear = request.getParameter(Constant.END_YEAR_PARA);
				String endMonth = request.getParameter(Constant.END_MONTH);
				String endDay = request.getParameter(Constant.END_DAY);
				int endYearInt = Common.convertStringToInt(endYear, Constant.START_YEAR);
				int endMonthInt = Common.convertStringToInt(endMonth, Constant.ONE);
				int endDayInt = Common.convertStringToInt(endDay, Constant.ONE);
				//arrIntEndDate dùng để hiển thị lên ADM003 và validate
				arrIntEndDate.clear();
				arrIntEndDate.add(endYearInt);
				arrIntEndDate.add(endMonthInt);
				arrIntEndDate.add(endDayInt);
				//lấy endDate từ các số năm tháng ngày truyền vào
				endDate = Common.toDate(endYearInt, endMonthInt, endDayInt);
				//totalString dùng để hiển thị lên ADM003 và validate
				totalString = request.getParameter(Constant.TOTAL);
				total = Common.convertStringToInt(totalString, 0);
			}
			//set thông tin user cho đối tượng userInfor
			userInfor.setLoginName(loginName);
			userInfor.setGroupId(groupId);
			userInfor.setGroupName(groupName);
			userInfor.setFullName(fullName);
			userInfor.setFullNameKana(fullNameKana);				
			userInfor.setBirthday(birthday);
		    userInfor.setArrIntBirthday(arrIntBirthday);
		  	userInfor.setEmail(email);
		    userInfor.setTel(tel);
		    userInfor.setPass(password);
		    userInfor.setPassConfirm(passwordConfirm);
		    userInfor.setCodeLevel(codeLevel);
			userInfor.setNameLevel(nameLevel);
			userInfor.setStartDate(startDate);
			userInfor.setArrIntStartDate(arrIntStartDate);
			userInfor.setEndDate(endDate);
			userInfor.setArrIntEndDate(arrIntEndDate);
			userInfor.setTotalString(totalString);
			userInfor.setTotal(total);
			//Trả về đối tượng userInfor
			return userInfor;
		//bắt lỗi
		} catch (SQLException | ClassNotFoundException | ParseException e) {
			//ghi log
			System.out.println("AddUserInputController:getDefaultValue:" + e.getMessage());
			//ném lỗi
			throw e;
		}
	}
}
