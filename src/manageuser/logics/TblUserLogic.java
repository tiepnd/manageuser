/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblUserLogic.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;

/**
 * interface định nghĩa ra bộ hành vi thao tác vs bảng tbl_user
 * 
 * @author tiepnd
 */
public interface TblUserLogic {
	/**
	 * Kiểm tra tồn tại đăng nhập
	 * 
	 * @param loginName tên đăng nhập
	 * @param password mật khẩu
	 * @return trả về true nếu tồn tại đăng nhâp, false nếu không tồn tại
	 */
	Boolean checkExistLogin(String loginName, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException;
	
	/**
	 * Lấy danh sách user thỏa mãn các điều kiện truyền vào
	 * 
	 * @param offset vị trí data cần lấy nào
	 * @param limit số lượng lấy
	 * @param groupId mã nhóm tìm kiếm
	 * @param fullName tên tìm kiếm
	 * @param sortType nhận biết xem cột nào được ưu tiên sắp xếp (full_name or end_date or code_level)
	 * @param sortByFullName giá trị sắp xếp của cột full_name (ASC or DESC)
	 * @param sortByCodeLevel giá trị sắp xếp của cột end_date  (ASC or DESC)
	 * @param sortByEndDate nhận giá trị sắp xếp của cột code_level (ASC or DESC)
	 * @return danh sách user sẽ hiển thị trên màn ADM002
	 */
	List<UserInforEntities> getListUsers(int offset, int limit, int groupId, String fullName, String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws ClassNotFoundException, SQLException;

	
	/**
	 * Kiểm tra tồn tại 1 user có rule bằng 0 và login name bằng loginName
	 * 
	 * @param loginName truyền vào login cần kiểm tra
	 * @return Trả về true nếu TblUserEntities tồn tại
	 */
	boolean checkAdmin(String loginName) throws SQLException , ClassNotFoundException;
	
	/**
	 * Lấy tổng số user thỏa mãn điều kiện tìm kiếm theo fullname và groupId
	 * 
	 * @param  groupId mã nhóm tìm kiếm
     * @param fullName tên tìm kiếm
	 * @return tổng số user
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException;
	
	/**
	 * Kiểm tra email đã tồn tại trong bảng tbl_user chưa?
	 * 
	 * @param userId id của user cần check
	 * @param email email của user cần check
	 * @return trả về true email đã tồn tại, flase email không tồn tại
	 */
	boolean checkExistedEmail(int userId, String email) throws SQLException , ClassNotFoundException;
	
	/**
	 * Kiểm tra loginName đã tồn tại trong bảng tbl_user chưa?
	 * 
	 * @param loginName tên đăng nhập của user cần kiểm tra
	 * @return trả về true loginName đã tồn tại, flase loginName không tồn tại
	 */
	boolean checkExistedLoginName(String loginName) throws SQLException , ClassNotFoundException;
	
	/**
	 * Kiểm tra có tồn tại user trong bảng tbl_user bằng userid
	 * 
	 * @param userId id của user cần check
	 * @return trả về true nếu tồn tại user trong bảng tbl_user, flase nếu không tồn tại user trong bảng tbl_user
	 */
	boolean checkExistUserById(int userId) throws SQLException , ClassNotFoundException;
	
	/**
	 * Insert data  user vào bảng tbl_user và tbl_detail_user_japan
	 * 
	 * @param userInfor chứa thông tin user cần check
	 * @return true nếu insert thành công, false nếu insert không thành công
	 */
	boolean createUser(UserInforEntities userInfor);
	/**
	 * Lấy đối tượng userInfor từ userId 
	 * 
	 * @param userId id của user cần lấy
	 * @return trả về userInfor
	 */
	UserInforEntities getUserInforById(int userId) throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy đối tượng userInfor từ userId 
	 * 
	 * @param userId id của user cần lấy
	 * @return trả về userInfor
	 */
	TblUserEntities getUserById(int userId) throws SQLException, ClassNotFoundException;
	
	/**
	 * update thông tin user vào bảng tbl_user và tbl_detail_user_japan
	 * 
	 * @param userInfor chứa thông tin của user
	 * @return true nếu update thành công, false nếu update không thành công
	 */
	boolean updateUser(UserInforEntities userInfor);
	/**
	 * thực hiện xóa thông tin user trong 2 bảng tbl_user và tbl_detai_user_japan
	 * 
	 * @param userId id của user cần xóa
	 * @return true nếu delete thành công, false nếu delete không thành công
	 */
	boolean deleteUser(int userId );
	
}
