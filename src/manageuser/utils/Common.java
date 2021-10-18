/**
 * Copyright(C) 2020  Luvina SoftWare
 * Common.java, Jul 8, 2020 tiepnd
 */
package manageuser.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import manageuser.entities.MstGroupEntities;
import manageuser.entities.MstJapanEntities;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.MstGroupLogic;
import manageuser.logics.MstJapanLogic;
import manageuser.logics.TblUserLogic;
import manageuser.logics.impl.MstGroupLogicImpl;
import manageuser.logics.impl.MstJapanLogicImpl;
import manageuser.logics.impl.TblUserLogicImpl;

/**
 * Chứa các hàm common của dự án
 * 
 * @author tiepnd
 */
public class Common {
	    /**
	     * Mã hóa password
	     * 
	     * @param password chuỗi cần mã hóa
	     * @param salt để mã hóa
	     * @return trả về password đã được mã hóa
	     */
	    public static String encrypt(String password, String salt) throws NoSuchAlgorithmException {
	        //tạo biến kiểu string để lưu kết quả mã hóa
	    	StringBuffer stringmessageDigest = new StringBuffer();
	        try {
	        	//khai báo đối tượng của lớp hỗ trợ các thuật toán mã hóa
		        MessageDigest md;
		        //lấy thuật toán mã hóa là SHA1
		        md = MessageDigest.getInstance(Constant.SHA_1);
		        //mã hóa 1 mảng kiểu byte trả về 1 mạng kiểu byte đã mã hóa
		        byte[] messageDigest = md.digest((password + salt).getBytes());
		        //chuyển mảng byte mã hóa về string và thêm vào stringmessageDigest
		        for (int i = 0; i < messageDigest.length; i++) {
		       	  stringmessageDigest.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
		        }
		        //Trả về chuỗi đã mã hóa
		        return stringmessageDigest.toString();
	        //bắt lỗi
	        } catch (NoSuchAlgorithmException e) {
		        System.out.println("Common:encrypt:" + e.getMessage());
		        //ném lỗi
		        throw e;
	        }
	    }
	    
	    /**
	     * so sánh hai chuối
	     * @param string chuỗi so sánh thứ nhất
	     * @param string2  chuỗi so sánh thứ hai
	     * @return trả về true nếu hai chuỗi giống nhau
	     */
	    public static Boolean compareString(String string, String string2) {
	    	//trả về true nếu 2 chuỗi giống nhau, false nếu 2 chỗ khác nhau
	    	return string.equals(string2);
	    }
		/**
		 * Kiểm tra đã đang nhập trước khi vào trang
		 * @param session phiên làm việc của người dùng
		 * @return trả về true nếu session còn tồn tạo attribute loginName, false nếu koong còn tồn tạo attribute loginName
		 * @throws Exception 
		 */
		public static boolean checkLogin(HttpSession session) throws ClassNotFoundException, SQLException {
			//bắt đầu try
			try {
				//lấy đối tượng loginName trên session
				Object loginName =  session.getAttribute(Constant.LOGIN_NAME);
				//nếu loginName lấy từ session về bằng null
				if (loginName == null) {
					//trả về false
					return false;
				}
				//Khởi tạo đối tượng TblUserLogic
				TblUserLogic tblUserLogic = new TblUserLogicImpl();
				//Check admin
				boolean isAdmin = tblUserLogic.checkAdmin((String)loginName);
				//Trả về true nếu login name khác null và user là admin, false nếu ngược lại
				return isAdmin;
			//bắt lỗi
			} catch (SQLException | ClassNotFoundException e) {
				//ghi log
				System.out.println("Common:checkLogin:" + e.getMessage());
				//ném lỗi
				throw e;
			}
		}
		
		/**
		 * convert từ kiểu string sang kiểu int nếu chuyển đổi gặp exception return giá trị default
		 * 
		 * @param string chuỗi cần convert 
		 * @param intDefault giá trị default trả về nếu huyển đổi gặp exception
		 * @return trả về số đã được chuyển đồi
		 */
		public static int convertStringToInt(String stringConvert, int intDefault) {
			//bắt đầu try
			try {
				//trả về kiểu int nếu parseInt thành công
				return Integer.parseInt(stringConvert);
			//bắt lỗis NumberFormatException
			} catch (NumberFormatException e) {
				//trả về giá trị default
				return intDefault;
			}
		}
		
		
		/**
		 * Xử lý toán tử wildcard
		 * @param stringWildcard chuỗi chứa toán tử wildcard
		 * @return trả về string đã xử lý toán tử wildcard
		 */
		public static String replaceWildcard(String stringWildcard) {
			//khởi tạo chuỗi để lưu string đã replaceWildcard
			StringBuffer replaceWildcard = new StringBuffer();
			//chuyển chuỗi input thành mảng các kí tự để replace
			char []arrayChar = stringWildcard.toCharArray();
			for (char c : arrayChar) {
				switch (c) {
				case '\\':
					//thay thế tất cả kí tự \ thành \\ để sql hiểu đây không phải là các toán tử wildcard
					replaceWildcard.append("\\\\");
					break;
				case '%':
					//thay thế tất cả kí tự _ thành \_ để sql hiểu đây không phải là các toán tử wildcard
					replaceWildcard.append("\\%");
					break;
				case '_':
					//thay thế tất cả kí tự % thành \% để sql hiểu đây không phải là các toán tử wildcard
					replaceWildcard.append("\\_");
					break;
				default:
					break;
				}
			}
			//trả về chuỗi đã xử lý toán tử wildcard
			return stringWildcard.toString();
		}
		
		/**
		 * Lấy vị trí data cần lấy
		 *
		 * @param currentPage trang hiện tại
		 * @param limit số lượng cần hiển thị trên 1 trang
		 * @return int vị trí cần lấy
		 */
		public static int getOffset(int currentPage, int limit){
			//trả về vị trí cần lấy
			return currentPage * limit - limit;
		}
		/**
		 * Tính tổng số trang
		 * @param totalUser tổng số User
 		 * @paramint limit số lượng cần hiển thị trên 1 trang
		 * @return tổng số trang
		 */
		public static int getTotalPage(int totalUser, int limit){
			//lấy tổng số page 
			int totalPage = (int)Math.ceil(totalUser * 1.0 / limit);
			//nếu tống số page bằng 0
			return totalPage == 0 ? 1 : totalPage;
		}
		
		/**
		 * Tạo chuỗi paging
		 * @param totalUser tổng sô user
		 * @param limit số lượng cần hiển thị trên 1 trang
		 * @param currentPage trang hiện tại
		 * @return Danh sách các trang cần hiển thị ở chuỗi paging theo trang hiện tại
		 */
		public static List<Integer> getListPaging(int totalUser, int limit, int currentPage ){
			//Khởi tạo list paging
			List<Integer> listPaging = new ArrayList<Integer>();
			//Lấy tổng số page
			int totalPage = getTotalPage(totalUser, limit);
			//Khai báo page đầu
			int limitPage = convertStringToInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE), 3);
			//Lấy page đầu tiên của chuỗi paging
			int startPage = getStartPage(currentPage, limitPage);
			//Tạo chuỗi paging
			for(int i = startPage; i < startPage + limitPage; i++){
				if (i <= totalPage) {
					listPaging.add(i);
				}
			}
			//Trả về chuỗi paging
			return listPaging;
		}
		
		/**
		 * Lấy page đầu tiên của chuỗi paging 
		 * @param currentPage Page hiện tại 
		 * @param limitPage số page tối đa của 1 chuỗi
		 * @return trả về page đầu của chuỗi
		 */
		public static int getStartPage(int currentPage, int limitPage){
			//tạo biến startPage
			int startPage = Constant.ONE;
			//nếu currentPage chia hết cho limitPage
			if (currentPage % limitPage == 0) {
				startPage = currentPage - limitPage + Constant.ONE;
			} else {//nếu currentPage chia dư cho limitPage
				startPage = (currentPage / limitPage) * limitPage + Constant.ONE;
			}
			return startPage;
		}
		/**
		 * Lấy current page của link next
		 * @param currentPage page hiện tại
		 * @return Trả về current page của link next
		 */
		public static int getNextPage(int currentPage){
			//lấy limitPages
			int limitPage = convertStringToInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE), 3);
			//tính startPage
			int startPage = getStartPage(currentPage, limitPage);
			//trả về nextPage
			return startPage + limitPage;
		}
		
		/**
		 * Lấy current page của link previous
		 * @param currentPage page hiện tại
		 * @return Trả về current page của link previous
		 */
		public static int getPreviousPage(int currentPage){
			//lấy limitPages
			int limitPage = convertStringToInt(ConfigProperties.getValueByKey(Constant.LIMIT_PAGE), 3);
			//tính startPage
			int startPage = getStartPage(currentPage, limitPage);
			//trả về previousPage
			return startPage - limitPage;
		}
		
	    /**
	     * Lấy list năm
	     * @param startYear năm bắt đầu
	     * @param endYear năm kết thúc
	     * @return List<Integer> trả về list năm
	     */
	    public static List<Integer> getListYear(int startYear, int endYear){
	    	//khởi tạo listYear
	    	List<Integer> listYear = new ArrayList<>();
	    	//add vào listYear
	    	for(int i = startYear; i <= endYear; i++){
	    		listYear.add(i);
	    	}
	    	//trả về listYear
	    	return listYear;
	    }
	    
	    /**
	     * Lấy list tháng
	     * @return List<Integer> trả về list tháng
	     */
	    public static List<Integer> getListMonth(){
	    	//khởi tạo listMonth
	    	List<Integer> listMonth = new ArrayList<>();
	    	//add vào listMonth
	    	for(int i = 1; i <= Constant.MAX_MONTH; i++){
	    		listMonth.add(i);
	    	}
	    	//trả về listMonth
	    	return listMonth;
	    }
	    
	    /**
	     * Lấy danh sách ngày
	     * @return trả về danh sách các ngày
	     */
	    public static List<Integer> getListDay(){
	    	//khởi tạo listDay
	    	List<Integer> listDay = new ArrayList<>();
	    	//add vào listDay
	    	for(int i = 1; i <= Constant.MAX_DAY; i++){
	    		listDay.add(i);
	    	}
	    	//trả về listDay
	    	return listDay;
	    }
	    
	    /**
	     * Lấy ra Date hiện tại
	     * @return trả về Date hiện tại
	     */
	    public static Date getCurrentDate(){
	    	//lấy ngày hiện tại
	    	Date date = new Date();
	    	//trả về ngày hiện tại
	    	return date;
	    }
	    
	    /**
	     * Convert các số năm tháng ngày thành 1 đối tượng kiểu Date có format yyyy/MM/dd
	     * @param year giá trị năm truyền vào
	     * @param month giá trị tháng truyền vào
	     * @param day giá trị ngày truyền vào
	     * @return trả về 1 đối tượng kiểu Date
	     */
	    public static Date toDate(int year, int month, int day) throws java.text.ParseException {
	    	//khởi tạo chuỗi date
	    	String stringDate = year + "/" + month + "/" + day;
	    	//khởi tạo đối tượng SimpleDateFormat convert từ stringDate thành Date có format là năm tháng ngày
	    	SimpleDateFormat formatter = new SimpleDateFormat(Constant.FOMAT_DATE);
	    	try {
	    		//chuyển chuỗi date thành đối tượng Date
	            Date date = formatter.parse(stringDate);
	            //trả về đối tượng date
	            return date;      
	            //Bắt lỗi ParseException
	        } catch (java.text.ParseException e) {
	        	//ghi log
	        	System.out.println("Common:toDate:" + e.getMessage());
	        	//ném lỗi
	        	throw e;
	        }  
	    }
	    /**
	     * Kiểm trả chuỗi có phải chuỗi kí tự katakana 
	     * @param string chuỗi kiểm tra
	     * @return trả về true nếu tất cả các kí tự là kí tự katanana, false  nếu tất cả các kí tự không phải là kí tự katanana
	     */
	    public static boolean checkKatakana(String string) {
		    for(int i = 0; i < string.length(); i++){
		    	//nếu không phải kí tự katakana
		    	if ((Character.UnicodeBlock.of(string.charAt(i)) != Character.UnicodeBlock.KATAKANA)) {
		    		//trả về false
		    		return false;
		    	}
		    }
		    //trả về true
	        return true;
	    }  
	    /**
	     * Kiểm trả ngày có hợp lệ không
	     * @param ngày kiểm tra
	     * @return trả về true nếu ngày hợp lệ, false nếu không hợp lệ
	     */
	    public static boolean checkCorrectDate(ArrayList<Integer> arrIntDate) {
	    	int year = arrIntDate.get(Constant.INDEX_0);
	    	int month = arrIntDate.get(Constant.INDEX_1);
	    	int day = arrIntDate.get(Constant.INDEX_2);
	    	int maxDay = Constant.MAX_DAY;
	    	int maxMonth = Constant.MAX_MONTH;
	    	//validate năm
	    	if (!(year >= Constant.START_YEAR && year <= Constant.END_YEAR + Constant.ONE)) {
	    		return false;
	    	}
	    	//validate tháng
	    	if (!(month >= 1 && month <= maxMonth)) {
	    		return false;
	    	}
	    	//Tính maxday
	    	if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {//nếu là năm nhuận
	    		if (month == 2) {
	    			maxDay = 29;
	    		}
	    	} else {//nếu không phải là năm nhuận
	    		if (month == 2) {
	    			maxDay = 28;
	    		}
	    	}
	    	if (month == 4 || month == 6 || month == 9 || month == 11) {
	    		maxDay = 30;
	    	}
	    	//validate ngày
		    if (!(day >= 1 && day <= maxDay)) {
		    	return false;
		    }
	         return true;
	    }  
	    /**
	     * Chuyển ngày thành mảng int tương ứng năm tháng ngày
	     * @param date ngày tháng cần chuyển về mảng
	     * @return mảng int tương ứng năm tháng ngày
	     */
	    public static ArrayList<Integer> toArrayInteger(Date date){
	    	//Khởi tạo đối tượng calenda để lấy ngày tháng năm của đối tượng Date
	    	Calendar calendar = Calendar.getInstance();
	    	//khởi tạo mảng Integer để lưu các giá trị ngày tháng năm
	    	ArrayList<Integer> arrayDate = new ArrayList<>();
	    	//set thời gian cho đối tượng calendar bởi đối tượng date truyền vào
			calendar.setTime(date);
			//add các giá trị năm tháng ngày vào mảng
	    	arrayDate.add(calendar.get(Calendar.YEAR));
	    	arrayDate.add(calendar.get(Calendar.MONTH) + 1);
	    	arrayDate.add(calendar.get(Calendar.DAY_OF_MONTH));
	    	//trả về mảng năm tháng ngày
	    	return arrayDate;
	    }
	    /**
	     * Lấy năm hiện tại
	     * @return currentYear trả về năm hiện tại
	     */
	    public static int getYearNow() {
	    	//lấy năm hiện tại
	    	int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	    	//trả về năm hiện tại
	    	return currentYear;
	    }
	    
	    /**
	     * Kiểm tra chuỗi chứa tất cả các kí tự 1 byte
	     * 
	     * @param text chuỗi cần kiểm tra
	     * @return trả về true nếu chuỗi chứa tất cả các kí tự 1 byte, flase nếu không chuỗi chứa tất cả các kí tự 1 byte
	     */
	    public static boolean isOneByteCharater(String text) {
	    	//mảng kí tự từ chuỗi truyền vào
	    	char []arrChar = text.toCharArray();
	    	//duyệt từng kí tự của chuỗi
	    	for (char c : arrChar) {
	    		//nếu không phải là kí tự 1 byte
				if (c > 256) {
					//trả về false
					return false;
				}
			}
	    	//trả về true
	    	return true;
	    }
	    
	    /**
	     * Kiểm tra chuỗi chứa tất cả các kí tự là Halfsize
	     * @param text chuỗi cẫn kiểm tra
	     * @return trả về true nếu chuỗi chứa tất cả các kí tự là Halfsize, flase nếu ngược lại
	     */
	    public static boolean isHalfsize(String text) {
	    	//trả về true nếu chuỗi so khớp với regex
	    	return text.matches("[0-9]+");
	    }
	    
	    /**
	     * lấy đối tượng TblUserEntities từ userinfor
	     * @param userInfor chứa thông tin của user
	     * @return trả về đối tượng TblUserEntities
	     */
	    public static TblUserEntities getUserFromUserInfor(UserInforEntities userInfor) throws NoSuchAlgorithmException {
	    	//khởi tạo đối tượng TblUserEntities
	    	TblUserEntities user = new TblUserEntities();
	    	try {
		    	//set giá trị cho user lấy từ đối tượng userInfor
		    	user.setBirthday(userInfor.getBirthday());
		    	user.setEmail(userInfor.getEmail());
		    	user.setFullName(userInfor.getFullName());
		    	user.setFullNameKana(userInfor.getFullNameKana());
		    	user.setGroupId(userInfor.getGroupId());
		    	user.setTel(userInfor.getTel());
		    	user.setUserId(userInfor.getUserId());
		    	//nếu gọi đến getUserFromUserInfor trong trường hợp add mới user
		    	if (userInfor.getUserId() == Constant.ZERO) {
		    		user.setLoginName(userInfor.getLoginName());
			    	user.setRule(Constant.USER_RULE);
			    	String salt = createSalt();
			    	user.setSalt(salt);		
			    	String password = encrypt(userInfor.getPass(), salt);
			    	user.setPassword(password);
		    	}
		    	//trả về user
		    	return user;
		    //bắt lỗi
			} catch (NoSuchAlgorithmException e) {
				//ghi log
				System.out.println("Common:getUserFromUserInfor:" + e.getMessage());
				//ném lỗi
				throw e;
			}
		}
	    
	    /**
	     * lấy trình độ tiếng nhật từ đối tượng userInfor để insert vào bảng tbl_detail_user_japan
	     * @param userInfor chứa thông tin của user
	     * @return trả về về đối tượng TblDetailUserJapanEntities
	     */
	    public static TblDetailUserJapanEntities getDetailUserJapanFromUserInfor(UserInforEntities userInfor) {
	    	//khởi tạo đối tượng detailUserJapan
	    	TblDetailUserJapanEntities detailUserJapan = new TblDetailUserJapanEntities();
	    	//set giá trị đối tượng detailUserJapan
	    	detailUserJapan.setCodeLevel(userInfor.getCodeLevel());
	    	detailUserJapan.setStartDate(userInfor.getStartDate());
	    	detailUserJapan.setEndDate(userInfor.getEndDate());
	    	detailUserJapan.setTotal(userInfor.getTotal());
	    	detailUserJapan.setUserId(userInfor.getUserId());
	    	//trả về đối tượng detailUserJapan
	    	return detailUserJapan;
	    }
		/**
		 * tạo chuỗi là giá trị milisecond tính từ January 1, 1970, 00:00:00 GMT đến lúc getTime()
		 * 
		 * @return trả về chuỗi là giá trị milisecond tính từ January 1, 1970, 00:00:00 GMT đến lúc getTime()
		 */
	    public static String createSalt() {
	    	//trả về chuỗi là giá trị milisecond tính từ January 1, 1970, 00:00:00 GMT đến lúc getTime()
	    	String salt = String.valueOf(new Date().getTime());
	    	//trả về chuỗi salt
			return salt;
		}
	    
	    /**
	     * Kiểm tra người dùng có nhập cùng tiếng nhật không
	     * @param codeLevel mã trình độ tiếng nhật
	     * @return trả về true nếu đã nhập, false nếu không nhập
	     */
	    public static boolean checkEnterJapan(String codeLevel) {
	    	//trả về true nếu người dùng nhập
			return !compareString(Constant.STRING_BLANK, codeLevel);
		}
		/**
		 * Thực hiện set các list giá trị cho các hạng mục selectbox ở màn hình ADM003
		 * 
		 * @param request để thực hiện set các list giá trị lên request.
		 * @throws ClassNotFoundException, SQLException 
		 */
	    public static void setDataLogic(HttpServletRequest request) throws ClassNotFoundException, SQLException, ServletException, IOException {
			//Tạo đối tượng MstGroupLogic để lấy danh sách các nhóm từ bảng mst_group
			MstGroupLogic mstGroupLogic = new MstGroupLogicImpl();
			//Tạo đối tượng MstJapanLogic để lấy danh sách trình độ tiếng nhật từ bảng mst_japan
			MstJapanLogic mstJapanLogic = new MstJapanLogicImpl();
			//Tạo list chứa danh sách các nhóm được lấy từ bảng mst_group
			List<MstGroupEntities> listMstGroup = new ArrayList<MstGroupEntities>();
			//Tạo list chứa danh sách trình độ tiếng nhật được lấy từ bảng mst_japan
			List<MstJapanEntities> listMstJapan = new ArrayList<MstJapanEntities>();
			//Tạo list chứa danh sách các ngày của pulldown ngày trong các hạng mục birthday, startDate và endDate
			List<Integer> listDay = new ArrayList<Integer>();	
			//Tạo list chứa danh sách các tháng của pulldown tháng trong các hạng mục birthday, startDate và endDate
			List<Integer> listMonth = new ArrayList<Integer>();
			//Tạo list chứa danh sách các năm của pulldown năm trong các hạng mục birthday và startDate 
			List<Integer> listYear = new ArrayList<Integer>();
			//Tạo list chứa danh sách các năm của pulldown năm trong hạng mục endDate
			List<Integer> listEndYearJapan = new ArrayList<Integer>();		
			try {
				//lấy danh sách các nhóm từ bảng mst_group
				listMstGroup = mstGroupLogic.getAllMstGroup();
				//lấy danh sách trình độ tiếng nhật từ bảng mst_japan
				listMstJapan = mstJapanLogic.getAllMstJapan();
				//lấy danh sách các ngày
				listDay = getListDay();
				//lấy danh sách các tháng
				listMonth = getListMonth();
				//lấy danh sách các năm cho pulldown năm trong các hạng mục birthday và startDate 
				listYear = getListYear(Constant.START_YEAR, getYearNow());
				//lấy danh sách các năm cho pulldown năm trong hạng mục endDate
				listEndYearJapan = getListYear(Constant.START_YEAR, getYearNow() + 1);
				//set dữ liệu lên request
				request.setAttribute(Constant.LIST_ALL_MST_GROUP, listMstGroup);
				request.setAttribute(Constant.LIST_ALL_MST_JAPAN, listMstJapan);
				request.setAttribute(Constant.LIST_YEAR, listYear);
				request.setAttribute(Constant.LIST_YEAR_JAPAN, listEndYearJapan);
				request.setAttribute(Constant.LIST_MONTH, listMonth);
				request.setAttribute(Constant.LIST_DAY, listDay);
			//bắt lỗi
			} catch (ClassNotFoundException | SQLException e) {
				//ghi log
				System.out.println("Comon:setDataLogic:" + e.getMessage());
				//ném lỗi				
				throw e;			
			}
		}
}
