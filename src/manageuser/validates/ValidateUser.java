/**
 * Copyright(C) 2020  Luvina SoftWare
 * ValidateUser.java, Jul 8, 2020 tiepnd
 */
package manageuser.validates;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.TblUserLogicImpl;
import manageuser.utils.Common;
import manageuser.utils.Constant;
import manageuser.utils.MessageErrorProperties;

/**
 * Thực hiện check dữ liệu nhập vào 
 * 
 * @author tiepnd
 */
public class ValidateUser {
	/**
	 * Kiểm tra thông tin người dùng nhập trên màn hình ADM001
	 * 
	 * @param userName tên đăng nhập của người dùng nhập vào
	 * @param password mật khẩu của người dùng nhập vào
	 * @return Trả về 1 danh sách lỗi
	 */
	public static List<String> validateLogin(String userName, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException{
		//khởi tạo danh sách chứa list lỗi
		List<String> listError = new ArrayList<String>();
		//Khỏi tạo đối tượng TblUserLogic để sử dụng phương thức checkExistLogin
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		try {		
			//Nếu tên đăng nhập chưa được nhập 
			if (Common.compareString(Constant.STRING_BLANK, userName)) {
				//add lỗi có mã lỗi ER001_LOGINNAME vào danh sách lỗi
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_LOGINNAME));
			}
			//Nếu mất khẩu chưa được nhập
			if (Common.compareString(Constant.STRING_BLANK, password)) {
				//add lỗi có mã lỗi ER001_PASSWORD vào danh sách lỗi
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD));
			}
			//Nếu người dùng đã nhập dữ liệu và không tồn tại username password trong DB
			if (listError.isEmpty() && !tblUserLogic.checkExistLogin(userName, password)) {
				//add lỗi có mã lỗi ER016 là lỗi user hoặc password không đúng vào danh sách lỗi
				listError.add(MessageErrorProperties.getValueByKey(Constant.ER016));
			}
			//trả về list lỗi
			return listError;
		} catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
			System.out.println("ValidateUser:validateLogin:" + e.getMessage());
			throw e;
		}	
	}
	
	/**
	 * Thực hiện validate thông tin user nhập từ màn hình ADM003 
	 * 
	 * @param userInfo Đối tượng user cần validate
	 * @return trả về list lỗi nếu có lỗi, trả về 1 list rỗng nếu không có lỗi
	 * @throws SQLException, ClassNotFoundException 
	 */
	public static List<String> validateUserInfor(UserInforEntities userInfor) throws SQLException, ClassNotFoundException {

		//Khởi tạo list lỗi chứa các lỗi khi validate dữ liệu
		List<String> lstError = new ArrayList<>();
		try {
			//validate hạng mục loginname, trong trường hợp add user
			if (userInfor.getUserId() == 0) {
				validateLoginName(lstError, userInfor.getLoginName());
			}
			//validate hạng mục group
			validateGroup(lstError, userInfor.getGroupId(), userInfor.getGroupName());
			//validate hạng mục fullname
			validateFullName(lstError, userInfor.getFullName());
			//validate hạng mục fullnamekana
			validateFullNameKana(lstError, userInfor.getFullNameKana());
			//validate hạng mục birthday
			validateBirthday(lstError, userInfor.getArrIntBirthday());
			//validate hạng mục email
			validateEmail(lstError, userInfor.getUserId(), userInfor.getEmail());
			//validate hạng mục tel
			validateTel(lstError, userInfor.getTel());
			//validate hạng mục password
			String password = userInfor.getPass();
			if (userInfor.getUserId() == 0) {
				validatePassword(lstError, password);
			}
			//validate hạng mục passwordConfirm
			if(userInfor.getUserId() == 0) {
				validatePasswordConfirm(lstError, password, userInfor.getPassConfirm());
			}
			//Nhập trình độ tiếng nhật thì validate các hạng mục
			if (!Common.compareString(Constant.STRING_BLANK, userInfor.getCodeLevel())) {
				//validate hạng mục trình độ tiếng nhật
				validateNameLevel(lstError, userInfor.getNameLevel());
				//validate hạng mục startdate
				validateStartDate(lstError, userInfor.getArrIntStartDate());
				//validate hạng mục enddate
				validateEndDate(lstError, userInfor.getArrIntEndDate(), userInfor.getStartDate(), userInfor.getEndDate());
				//validate hạng mục total
				validateTotal(lstError, userInfor.getTotalString());
			}
			
			//trả về list lỗi
			return lstError;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ValidateUser:validateUserInfor:" + e.getMessage());
			throw e;
		}
	}

	/**
	 * validate hạng mục login name
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param loginName tên đăng nhập
	 */
	public static void validateLoginName(List<String> lstError, String loginName) throws SQLException, ClassNotFoundException {
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		try {
			if (Common.compareString(Constant.STRING_BLANK, loginName)) {//check không nhập
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER001_LOGINNAME));
				return;
			}
			if (!loginName.matches(Constant.LOGIN_NAME_FOMAT)) {//check nhập đúng fomat
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER019_LOGINNAME));
				return;
			}
			if (Constant.MIN_LENGTH_LOGIN_NAME > loginName.length() || loginName.length() > Constant.MAX_LENGTH_LOGIN_NAME) {//check điều kiện biên
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER007_LOGINNAME));
				return;
			}
			
			if (tblUserLogic.checkExistedLoginName(loginName)) {//check tồn tại
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER003_LOGINNAME));
				return;
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ValidateUser:validateLoginName:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * validate hạng mục group
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param groupId mã nhóm
	 * @param groupName tên nhóm
	 */
	public static void validateGroup(List<String> lstError, int groupId, String groupName) {
		if (groupId == Constant.GROUP_ID_DEFAULT) {//check không chọn
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER002_GROUPNAME));
			return;
		}
		//nếu groupName lấy từ DB groupId là null, tức là groupName chọn từ người không tồn tại trong DB
		if (groupName == null) {//check không tồn tại
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER004_GROUPNAME));
			return;
		}
	}
	
	/**
	 * validate hạng mục fullname
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param fullName tên đầy đủ
	 */
	private static void validateFullName(List<String> lstError, String fullName) {
		if (Common.compareString(Constant.STRING_BLANK, fullName)) {//check không nhập
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER001_FULLNAME));
			return;
		}
		if (fullName.length() > Constant.MAX_LENGTH) {//check max length
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER006_FULLNAME));
			return;
		}
	}
	
	/**
	 * validate hạng mục fullnameKana
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param fullNameKana tên katakana
	 */
	private static void validateFullNameKana(List<String> lstError, String fullNameKana) {
		//nếu nhập hạng mục fullNameKana
		if (!Common.compareString(Constant.STRING_BLANK, fullNameKana)){
			if (!Common.checkKatakana(fullNameKana)) {//check kí tự katakana
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER009_FULLNAMEKANA));
				return;
			}
			if (fullNameKana.length() > Constant.MAX_LENGTH) {//check max length
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER006_FULLNAME));
				return;
			}
		}
	}
	
	/**
	 * validate hạng mục Birthday
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param arrIntBirthday mảng int của ngày sinh
	 */
	private static void validateBirthday(List<String> lstError, ArrayList<Integer> arrIntBirthday) {
		if (!Common.checkCorrectDate(arrIntBirthday)) {//check fomat ngày
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER011_BIRTHDAY));
		}
	}
	
	/**
	 * validate hạng mục email
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param userId id của user
	 * @param email địa chỉ email
	 */
	public static void validateEmail(List<String> lstError, int userId, String email) throws SQLException, ClassNotFoundException {
		TblUserLogic tblUserLogic = new TblUserLogicImpl();
		try {
			if (Common.compareString(Constant.STRING_BLANK, email)) {//check không nhập
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER001_EMAIL));
				return;
			}
			if (email.length() > Constant.MAX_LENGTH) {//check max length
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER006_EMAIL));
				return;
			}
			if (!email.matches(Constant.EMAIL_FOMAT)) {//check fomat email
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER005_EMAIL));
				return;
			}
			if (tblUserLogic.checkExistedEmail(userId, email)) {//check tồn tại
				lstError.add(MessageErrorProperties.getValueByKey(Constant.ER003_EMAIL));
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("ValidateUser:validateEmail:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * validate hạng mục tel
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param tel số điện thoại
	 */
	private static void validateTel(List<String> lstError, String tel) {
		if (Common.compareString(Constant.STRING_BLANK, tel)) {//check không nhập
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER001_TEL));
			return;
		}
		if (tel.length() > Constant.MAX_LENGTH_TEL) {//check max length
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER006_TEL));
			return;
		}
		if (!tel.matches(Constant.TEL_FOMAT)) {//check fomat tel
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER005_TEL));
			return;
		}
	}
	/**
	 * validate hạng mục password
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param passwordConfirm mật khẩu
	 */
	private static void validatePassword(List<String> lstError, String password) {
		if (Common.compareString(Constant.STRING_BLANK, password)) {//check không nhập
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER001_PASSWORD));
			return;
		}
		if (!Common.isOneByteCharater(password)) {
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER008_PASSWORD));
			return;
		}
		if (Constant.MIN_LENGTH_PASSWORD > password.length() || password.length() > Constant.MAX_LENGTH_PASSWORD){
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER007_PASSWORD));
			return;
		}
	}
	
	/**
	 * validate hạng mục passwordConfirm
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param passwordConfirm mật khẩu xác nhận
	 */
	private static void validatePasswordConfirm(List<String> lstError, String password, String passwordConfirm) {
		if (password != null && !Common.compareString(password, passwordConfirm)) {
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER017));
		}
	}
	
	/**
	 * validate hạng mục nameLevel
	 * 
	 * @param  lstError list lỗi add vào nếu check có lỗi
	 * @param nameLevel tên trình độ tiếng nhật
	 */
	private static void validateNameLevel(List<String> lstError, String nameLevel) {
		//nếu nameLevel lấy từ DB codeLevel là null, tức là nameLevel chọn từ người không tồn tại trong DB
		if (nameLevel == null) {//check tồn tại
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER004_CODELEVEL));
		}
	}
	
	/**
	 * validate hạng mục startdate
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param arrIntStartDate mảng int là ngày bắt đầu của trình độ tiếng nhật
	 */
	private static void validateStartDate(List<String> lstError, ArrayList<Integer> arrIntStartDate) {
		if (!Common.checkCorrectDate(arrIntStartDate)) {//check ngày hợp lệ
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER011_STARTDATE));
		}
	}
	
	/**
	 * validate hạng mục enddate
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param startDate ngày bắt đầu của trình độ tiếng nhật
	 * @param endDate ngày kết thúc của trình độ tiếng nhật
	 */
	private static void validateEndDate(List<String> lstError, ArrayList<Integer> arrIntEndDate, Date startDate, Date endDate) {
		if (!Common.checkCorrectDate(arrIntEndDate)) {//check ngày hợp lệ
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER011_ENDDATE));
			return;
		}
		if (!startDate.before(endDate)) {//check ngày kết thúc sau ngày bắt đầu
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER012));
			return;
		}
	}
	
	/**
	 * validate hạng mục total
	 * 
	 * @param lstError list lỗi add vào nếu check có lỗi
	 * @param totalString chuỗi total
	 */
	private static void validateTotal(List<String> lstError, String totalString) {
		if (Common.compareString(Constant.STRING_BLANK, totalString)) {//check không nhập
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER001_TOTAL));
			return;
		}
		if (!Common.isHalfsize(String.valueOf(totalString))) {//check haftsize
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER018_TOTAL));
			return;
		}
		if (totalString.length() > 9) {//check max length
			lstError.add(MessageErrorProperties.getValueByKey(Constant.ER006_TOTAL));
			return;
		}
	}
}
