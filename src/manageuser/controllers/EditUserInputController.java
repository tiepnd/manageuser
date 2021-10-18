/**
 * Copyright(C) 2020  Luvina SoftWare
 * MessageProperties.java, Jul 8, 2020 tiepnd
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
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.validates.ValidateUser;

/**
 * Controller xử lý cho màn hình ADM003 mode edit
 * 
 * @author tiepnd
 */
public class EditUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * Xử lý hiển thị ra ADM003, khi click vào button edit của MH ADM005 tương ứng với action edit, back của ADM004 tương ứng với action back
	 * 
	 * @param request yêu cầu từ browser đến server
	 * @param response phản hồi từ server về browser
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//tạo đối tượng userLogic để dùng phương thức checkExistUserById, đảm bảo edit user tồn tại trong DB
			TblUserLogic userLogic = new TblUserLogicImpl();
			//lấy userId từ request để check tồn tại user
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.ZERO);
			//Nếu không tồn tại user trong DB
			if (!userLogic.checkExistUserById(userId)) {
				//sendredirect đến url systemError.do set parameter errorCode vào query string
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER013);
			}
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
			System.out.println("EditUserInputController:doGet:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
	
	/**
	 * Xử lý khi click vào button Xác nhận của ADM003 mode edit, tương ứng với với action confirm
	 * 
	 * HttpServletRequest request 
	 * HttpServletResponse response
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Khởi tạo và lấy session hiện tại, để set userInfor và key03 lên session
			HttpSession session = request.getSession();
			//Khởi tạo listError chứa danh sách lỗi khi validate
			List<String> listError = new ArrayList<>();
			//lấy userId từ query string của request để check exited, mục đích là validate 1 user còn tồn tại trong DB
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.ZERO) ;
			//tạo đối tượng userLogic để dùng phương thức checkExistUserById
			TblUserLogic userLogic = new TblUserLogicImpl();
			//kiểm tra tồn tại của user trong DB trong trước khi validate data, qua màn ADM004. Nếu user không tồn tại chuyển sang màn SE không validate data nữa
			if (!userLogic.checkExistUserById(userId)) {
				//sendredirect đến url systemError.do set parameter errorCode vào query string
				response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER013);
				//thoát khởi phương thức
				return;
			}
			//Lấy thông tin user từ người dùng nhập vào để validate
			UserInforEntities userInfor = getDefaultValue(request);
			//Validate các thông tin nhập vào, lấy về list error
			listError = ValidateUser.validateUserInfor(userInfor);
			////nếu có lỗi
			if (!listError.isEmpty()) {
				//set giá trị cho các hạng mục selectbox ở màn hình ADM003 lên request
				Common.setDataLogic(request);
				//set userInfor lên request để giữ giá trị khi nhập lỗi
				request.setAttribute(Constant.USER_INFOR, userInfor);
				//set list lỗi lên request để hiển thị list lỗi lên màn ADM003 
				request.setAttribute(Constant.LIST_ERROR, listError);
				//Tạo đối tượng RequestDispatcher để forward đến màn ADM003
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.ADM003_PATH);
				//Chuyển tiếp đến ADM003
				requestDispatcher.forward(request, response);
			} else {//nếu không có lỗi
				//Tạo keyUser để nhận biết userInfor khi lấy từ session về trong trường hợp edit user cùng lúc từ nhiều tab
				String keyUser = Common.createSalt();
				//set userInfor lên session theo keyUser, userInfor sẽ được lấy theo keyUser tại EditUserConfirmController rồi hiển thị lên màn ADM004
				session.setAttribute(keyUser, userInfor);
				//set key03 lên session để đánh dấu đã qua ADM003, sử dụng trong trường hợp để hiển thị được ADM004 phải qua ADM003
				session.setAttribute(Constant.KEY03, Constant.KEY03);
				//sendredirect đến url editUserConfirmController.do set parameter keyUser vào query string để sang EditUserConfirmController trong doget lấy ra 
				response.sendRedirect(request.getContextPath() + "/editUserConfirmController.do?keyUser=" + keyUser);
			}
		//bắt lỗi
		} catch (Exception e) {
			//ghi log
			System.out.println("EditUserInputController:doPost:" + e.getMessage());
			//sendredirect đến url systemError.do set parameter errorCode vào query string
			response.sendRedirect(request.getContextPath() + Constant.SYSTEM_ERROR_DO + Constant.ERROR_CODE_QUERY_STRING + Constant.ER015);
		}
	}
	
	/**
	 * lấy thông tin user để hiển thị lên màn hình ADM003 mode edit
	 * Có 3 trường hợp gọi đến hàm getDefaultValue là click edit ở màn ADM005, click ok màn ADM003, click back ở màn ADM004 mode edit
	 * 
	 * @param request để lấy dữ liệu từ request về
	 * @return userInfor đối tượng chứa thông tin user để hiển lên màn ADM003 mode edit
	 */
	private UserInforEntities getDefaultValue(HttpServletRequest request ) throws ParseException, SQLException, ClassNotFoundException{
		try {
			//khởi tạo đối tượng TblUserLogic lấy thông tin user từ DB trong trường hợp click edit ADM005
			TblUserLogic userLogic = new TblUserLogicImpl();
			//Lấy session để lấy userInfor từ session trong trường hợp click back ADM004
			HttpSession session = request.getSession();
			//Khởi tạo đối tương UserInfor lưu thông user trong từng trường hợp
			UserInforEntities userInfor = new UserInforEntities();
			//Lấy action từ request để phân biệt các trường hợp
			String action = request.getParameter(Constant.ACTION);
			//lấy userId từ request được gửi từ ADM005.jsp bẳng queryString để lấy thông tin user từ DB
			int userId = Common.convertStringToInt(request.getParameter(Constant.USER_ID), Constant.ZERO);
			//trường hợp click edit từ ADM005, lấy thông tin user từ DB bằng userId set cho đối tượng userInfor
			if (Common.compareString(Constant.ACTION_EDIT, action)) {
				//lấy thông tin user từ DB bằng userId để hiển thị lên màn ADM003
				userInfor = userLogic.getUserInforById(userId);
				//set userId cho đối tượng userInfor
				userInfor.setUserId(userId);
				//nếu không có trình độ tiếng nhật set ngày tháng năm hiện tại cho các select box ngày tháng năm
				if (userInfor.getCodeLevel() == null) {
					Date currentDate = Common.getCurrentDate();
					userInfor.setArrIntBirthday(Common.toArrayInteger(currentDate));
					userInfor.setArrIntStartDate(Common.toArrayInteger(currentDate));
					userInfor.setArrIntEndDate(Common.toArrayInteger(currentDate));
					userInfor.getArrIntEndDate().set(Constant.INDEX_0, Constant.END_YEAR + 1);
				} else {//nếu có trình độ tiếng nhật set ngày tháng năm trong DB cho các select box ngày tháng năm
					userInfor.setArrIntBirthday(Common.toArrayInteger(userInfor.getBirthday()));
					userInfor.setArrIntStartDate(Common.toArrayInteger(userInfor.getStartDate()));
					userInfor.setArrIntEndDate(Common.toArrayInteger(userInfor.getEndDate()));
				}
				//trả về đối tượng userInfor
				return userInfor;
			}
			//trường hợp click button back ở màn hình ADM004, lấy thông tin user từ session về set cho cho đối tượng userInfor
			if (Common.compareString(Constant.ACTION_BACK, action)) {
				//Lấy keyUser từ request được gửi từ ADM004.jsp bẳng queryString (keyUser được gửi sang ADM004.jsp khi validate thành công ở ADM003)
				String keyUser = request.getParameter(Constant.KEY_USER);
				//Lấy userInfor từ session theo keyUser (keyUser để phần biệt giữa các userInfor session, mục đích là xử lý trường hợp edit 1 user từ nhiều tab cùng lúc)
				userInfor =  (UserInforEntities)session.getAttribute(keyUser);
				// xóa userInfor trên session
				session.removeAttribute(keyUser);
				//Trả về userInfor
				return userInfor;
			}
			
			//trường hợp click button ok ở màn hình ADM003, set giá trị nhập vào từ người dùng cho đối tượng userInfor
			if (Common.compareString(Constant.ACTION_CONFIRM, action)) {
				//Khởi tạo đối tượng mstGroupLogic để lấy groupName từ DB
				MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
				//Khởi tạo đối tượng mstGroupLogic để lấy nameLevel từ DB
				MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
				//set userId cho đối tượng userInfor
				userInfor.setUserId(userId);
				//Lấy dữ liệu của form được submit từ request set thông tin cho userInfor
				String loginName = request.getParameter(Constant.LOGIN_NAME);			
				int groupId = Common.convertStringToInt(request.getParameter(Constant.GROUP_ID), Constant.GROUP_ID_DEFAULT);
				String groupName = mstGroupLogic.getGroupName(groupId);
				String fullName = request.getParameter(Constant.FULLNAME);
				String fullNameKana = request.getParameter(Constant.FULLNAME_KANA);
				//lấy thông tin birthday của người dùng nhập vào
				String birthYear = request.getParameter(Constant.BIRTH_YEAR);
				String birthMonth = request.getParameter(Constant.BIRTH_MONTH);
				String birthDay = request.getParameter(Constant.BIRTH_DAY);
				int birthYearInt = Common.convertStringToInt(birthYear, Constant.START_YEAR);
				int birthMonthInt = Common.convertStringToInt(birthMonth, Constant.ONE);
				int birthDayInt = Common.convertStringToInt(birthDay, Constant.ONE);
				//arrIntBirthday dùng để hiển thị lên ADM003 và validate
				ArrayList<Integer> arrIntBirthday = new ArrayList<Integer>();
				arrIntBirthday.add(birthYearInt);
				arrIntBirthday.add(birthMonthInt);
				arrIntBirthday.add(birthDayInt);
				//lấy birday từ các số năm tháng ngày truyền vào
				Date birthday = Common.toDate(birthYearInt, birthMonthInt, birthDayInt);
				String email = request.getParameter(Constant.EMAIL);
				String tel = request.getParameter(Constant.TEL);
				String codeLevel = request.getParameter(Constant.CODE_LEVEL);
				String nameLevel = mstJapanLogic.getNameLevel(codeLevel);
				//lấy thông tin startDate của người dùng nhập vào
				String startYear = request.getParameter(Constant.START_YEAR_PARA);
				String startMonth = request.getParameter(Constant.START_MONTH);
				String startDay = request.getParameter(Constant.START_DAY);
				int startYearInt = Common.convertStringToInt(startYear, Constant.START_YEAR);
				int startMonthInt = Common.convertStringToInt(startMonth, Constant.ONE);
				int startDayInt = Common.convertStringToInt(startDay, Constant.ONE);
				//arrIntStartDate dùng để hiển thị lên ADM003 và validate
				ArrayList<Integer> arrIntStartDate = new ArrayList<Integer>();
				arrIntStartDate.add(startYearInt);
				arrIntStartDate.add(startMonthInt);
				arrIntStartDate.add(startDayInt);
				//lấy birday từ các số năm tháng ngày truyền vào
				Date startDate = Common.toDate(startYearInt, startMonthInt, startDayInt);
				//lấy thông tin endDate của người dùng nhập vào
				String endYear = request.getParameter(Constant.END_YEAR_PARA);
				String endMonth = request.getParameter(Constant.END_MONTH);
				String endDay = request.getParameter(Constant.END_DAY);
				int endYearInt = Common.convertStringToInt(endYear, Constant.START_YEAR);
				int endMonthInt = Common.convertStringToInt(endMonth, Constant.ONE);
				int endDayInt = Common.convertStringToInt(endDay, Constant.ONE);
				//arrIntEndDate dùng để hiển thị lên ADM003 và validate
				ArrayList<Integer> arrIntEndDate = new ArrayList<Integer>();
				arrIntEndDate.add(endYearInt);
				arrIntEndDate.add(endMonthInt);
				arrIntEndDate.add(endDayInt);
				//lấy endDate từ các số năm tháng ngày truyền vào
				Date endDate = Common.toDate(endYearInt, endMonthInt, endDayInt);
				//totalString dùng để hiển thị lên ADM003 và validate
				String totalString = request.getParameter(Constant.TOTAL);
				int total = Common.convertStringToInt(totalString, Constant.ZERO);
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
			    userInfor.setCodeLevel(codeLevel);
				userInfor.setNameLevel(nameLevel);
				userInfor.setStartDate(startDate);
				userInfor.setArrIntStartDate(arrIntStartDate);
				userInfor.setEndDate(endDate);
				userInfor.setArrIntEndDate(arrIntEndDate);
				userInfor.setTotalString(totalString);
				userInfor.setTotal(total);
			}	
			//trả về đối tượng userInfor
			return userInfor;
		//bắt lỗi
		} catch (ParseException | SQLException | ClassNotFoundException e) {
			//ghi log
			System.out.println("EditUserInputController:getDefaultValue:" + e.getMessage());
			//ném lỗi
			throw e;
		}
	}
}
