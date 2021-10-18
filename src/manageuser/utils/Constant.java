/**
+ * Copyright(C) 2020  Luvina SoftWare
 * Constant.java, Jul 8, 2020 tiepnd
 */
package manageuser.utils;
/**
 * Các hằng dùng trong chương trình
 * 
 * @author tiepnd
 */
public class Constant {
	// Database properties constant
	public static final String PROPERTIES_MESSAGE_ERROR_PATH = "message_error_ja.properties";
	public static final String PROPERTIES_DATABASE_PATH = "database.properties";
	public static final String PROPERTIES_CONFIG_PATH = "config.properties";
	public static final String PROPERTIES_MESSAGE_PATH = "message_ja.properties";

	// Message eror properties constant
	public static final String ER001_LOGINNAME = "ER001_loginName";
	public static final String ER001_FULLNAME = "ER001_fullName";
	public static final String ER001_BIRTHDAY = "ER001_birthday";
	public static final String ER001_EMAIL = "ER001_email";
	public static final String ER001_TEL = "ER001_tel";
	public static final String ER001_PASSWORD = "ER001_password";
	public static final String ER001_TOTAL = "ER001_total";
	public static final String ER002_GROUPNAME = "ER002_groupName";
	public static final String ER003_LOGINNAME = "ER003_loginName";
	public static final String ER003_EMAIL = "ER003_email";
	public static final String ER004_GROUPNAME = "ER004_groupName";
	public static final String ER004_CODELEVEL = "ER004_codeLevel";
	public static final String ER005_EMAIL = "ER005_email";
	public static final String ER005_TEL = "ER005_tel";
	public static final String ER006_FULLNAME = "ER006_fullName";
	public static final String ER006_FULLNAMEKANA = "ER006_fullNameKana";
	public static final String ER006_EMAIL = "ER006_email";
	public static final String ER006_TEL = "ER006_tel";
	public static final String ER006_TOTAL = "ER006_total";
	public static final String ER007_LOGINNAME = "ER007_loginName";
	public static final String ER007_PASSWORD = "ER007_password";
	public static final String ER008_PASSWORD = "ER008_password";
	public static final String ER009_FULLNAMEKANA = "ER009_fullNameKana";
	public static final String ER010 = "ER010";
	public static final String ER011_BIRTHDAY = "ER011_birthday";;
	public static final String ER011_STARTDATE = "ER011_startDate";
	public static final String ER011_ENDDATE = "ER011_endDate";
	public static final String ER012 = "ER012";
	public static final String ER013 = "ER013";
	public static final String ER014 = "ER014";
	public static final String ER015 = "ER015";
	public static final String ER016 = "ER016";
	public static final String ER017 = "ER017";
	public static final String ER018_TOTAL = "ER018_total";
	public static final String ER019_LOGINNAME = "ER019_loginName";
	public static final String ER020 = "ER020";

	// Message properties constant
	public static final String MSG001 = "MSG001";
	public static final String MSG002 = "MSG002";
	public static final String MSG003 = "MSG003";
	public static final String MSG004 = "MSG004";
	public static final String MSG005 = "MSG005";

	// các thuộc tính để kết nối đến DB
	public static final String CLASS = "CLASS";
	public static final String URLDB = "urlDB";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	
	// constant đường dẫn đến các màn hình
	public static final String ADM001_PATH = "/View/jsp/ADM001.jsp";
	public static final String ADM002_PATH = "/View/jsp/ADM002.jsp";
	public static final String ADM003_PATH = "/View/jsp/ADM003.jsp";
	public static final String ADM004_PATH = "/View/jsp/ADM004.jsp";
	public static final String ADM005_PATH = "/View/jsp/ADM005.jsp";
	public static final String ADM006_PATH = "/View/jsp/ADM006.jsp";
	public static final String SYSTEM_ERROR_PATH = "/View/jsp/System_Error.jsp";
	
	//các url để sendRedirect
	public static final String LISTUSER_DO = "/listUser.do";
	public static final String LOGIN_DO = "/login.do";
	public static final String LOGOUT_DO = "/logout.do";
	public static final String SYSTEM_ERROR_DO = "/systemError.do";
	public static final String ERROR_CODE_QUERY_STRING = "?errorCode=";
	public static final String SUCCESS_DO = "/success.do?type=";
	public static final String TYPE = "type";
	public static final String ADD_USER_CONFIRM_URL = "/addUserConfirmController.do?keyUser=";

	//các giá trị default cho màn hình ADM002
	public static final String LIMIT_USER = "limitUser";
	public static final String CURRENT_PAGE = "currentPage";
	public static final int OFFSET_DEDAULT = 0;
	public static final int LIMIT_DEFAULT = 5;
	public static final int CURRENT_PAGE_DEDAULT = 1;
	public static final int GROUP_ID_DEFAULT = 0;
	public static final int GROUP_ID_NOT_EXIST = -1;
	public static final String FULLNAME_DEFAULT = "";
	public static final String SORT_TYPE_DEFAULT = "sortByFullName";
	public static final String VALUE_SORT_DEFAULT = "ASC";
	public static final String VALUE_SORT_BY_FULLNAME = "valueSortByFullName";
	public static final String VALUE_SORT_BY_CODE_LEVEL = "valueSortByCodeLevel";
	public static final String VALUE_SORT_BY_END_DATE = "valueSortByEndDate";
	public static final String VALUE_SORT = "valueSort";
	public static final String FULL_NAME_TYPE = "full_name";
	public static final String CODE_LEVEL_TYPE  = "code_level";
	public static final String END_DATE_TYPE  = "end_date";
	public static final String VALUE_SORT_BY_FULLNAME_DEFAULT = "ASC";
	public static final String VALUE_SORT_BY_CODE_LEVEL_DEFAULT = "ASC";
	public static final String VALUE_SORT_BY_END_DATE_DEFAULT = "DESC";
	public static final String STRING_BLANK = "";
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final Integer INTERGER_NULL = null;
	
	// contant các action của người dùng
	public static final String ACTION = "action";
	public static final String ACTION_DEFAULT = "";
	public static final String ACTION_SEARCH = "search";
	public static final String ACTION_SORT = "sort";
	public static final String ACTION_PAGING = "paging";
	public static final String ACTION_BACK = "back";
	public static final String ACTION_CONFIRM = "confirm";
	public static final String ACTION_EDIT = "edit";
	
	// constant tên của các giá trị để set lên request
	public static final String LOGIN_NAME = "login_name";
	public static final String LIST_ERROR = "list_Errors";
	public static final String LIST_ALL_MST_GROUP = "listAllMstGroup";
	public static final String LIST_ALL_MST_JAPAN = "listAllMstJapan";
	public static final String FULLNAME= "fullname";
	public static final String GROUP_ID= "group_id";
	public static final String EMAIL = "email";
	public static final String FULLNAME_KANA = "fullNameKana";
	public static final String BIRTH_YEAR = "birthYear";
	public static final String BIRTH_MONTH = "birthMonth";
	public static final String BIRTH_DAY = "birthDay";
	public static final String TEL = "tel";
	public static final String PASSWORD_CONFIRM = "passwordConfirm";
	public static final String CODE_LEVEL = "codeLevel";
	public static final String START_YEAR_PARA = "startYear";
	public static final String START_MONTH = "startMonth";
	public static final String START_DAY = "startDay";
	public static final String END_YEAR_PARA = "endYear";
	public static final String END_MONTH = "endMonth";
	public static final String END_DAY = "endDay";
	public static final String TOTAL = "total";
	public static final String LIST_USERS = "listUsers";
	public static final String USER_INFOR = "userInfor";
	public static final String LIST_PAGING = "listPaging";
	public static final String TOTAL_PAGE = "totalPage";
	public static final String NEXT_PAGE = "nextPage";
	public static final String PREVIOUS_PAGE = "previousPage";
	public static final String PASSWORD_PARA = "password";
	public static final String LIST_YEAR = "listYear";
	public static final String LIST_YEAR_JAPAN = "listYearJapan";
	public static final String LIST_MONTH = "listMonth";
	public static final String LIST_DAY = "listDay";
	public static final String MESSAGE = "message";
	public static final String USER_ID = "userId";
	public static final String KEY03 = "key03";
	public static final String ERROR_CODE = "errorCode";
	public static final String MESSAGE_ERROR = "messageError";
	public static final String LIMIT_PAGE = "limitPage";
	public static final String SORT_TYPE = "sortType";
	public static final String KEY_USER = "keyUser";
	
	// constant rule
	public static final int ADMIN_RULE = 0;
	public static final int USER_RULE = 1;
	
	//regex format
	public static final String LOGIN_NAME_FOMAT = "^[a-zA-Z_]{1}[\\w_]*$";
	public static final String EMAIL_FOMAT = "^[^@.]+@[\\w_]+.[\\w_]+[\\w_.]*$";
	public static final String TEL_FOMAT = "^[\\d]{1,4}-[\\d]{1,4}-[\\d]{1,4}$";
	public static final String FOMAT_DATE = "yyyy/MM/dd";
	
	//contant maxlength minlength
	public static final int MAX_LENGTH = 255;
	public static final int MAX_LENGTH_TEL = 15;
	public static final int MAX_LENGTH_LOGIN_NAME = 15;
	public static final int MIN_LENGTH_LOGIN_NAME = 4;
	public static final int MAX_LENGTH_PASSWORD = 50;
	public static final int MIN_LENGTH_PASSWORD = 5;
	public static final int START_YEAR = 1900;	
	public static final int END_YEAR = Common.getYearNow();
	public static final int MAX_MONTH = 12;
	public static final int MAX_DAY = 31;
	
	// contant type sang Success controller lấy
	public static final String ERROR = "error";
	public static final String ADD_SUCCESS = "addSuccess";
	public static final String EDIT_SUCCESS = "editSuccess";
	public static final String DELETE_SUCCESS = "deleteSuccess";
	
	// các contant khác
	public static final String UTF_8 = "UTF-8";
	public static final String SHA_1 = "SHA-1";
	public static final int INDEX_0 = 0;
	public static final int INDEX_1 = 1;
	public static final int INDEX_2 = 2;
}
