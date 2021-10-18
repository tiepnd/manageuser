/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblUserDao.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;

/**
 * interface định nghĩa ra bộ hành vi thao tác với bảng tbl_user trong DB
 * @author tiepnd
 */
public interface TblUserDao extends BaseDao{
	/**
	 * Lấy ra đối tượng TblUser trong DB thỏa mãn loginName
	 * 
	 * @param loginName login name
	 * @return trả về 1 đối tượng TblUSer
	 */
	TblUserEntities getUserByLoginName(String loginName) throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy danh sách user thỏa mãn điều kiện truyền vào
	 * 
	 * @param offset Vị trí data cần lấy nào
	 * @param limit Số lượng lấy
	 * @param groupId Mã nhóm tìm kiếm
	 * @param fullName Tên tìm kiếm
	 * @param sortType Nhận biết xem cột nào được ưu tiên sắp xếp(full_name or end_date or code_level)
	 * @param sortByFullName Giá trị sắp xếp của cột Tên(ASC or DESC)
	 * @param sortByCodeLevel Giá trị sắp xếp của cột Trình độ tiếng nhật(ASC or DESC)
	 * @param sortByEndDate Nhận Giá trị sắp xếp của cột Ngày kết hạn(ASC or DESC)
	 * @return danh sách user sẽ hiển thị trên màn ADM002
	 */
	List<UserInforEntities> getListUsers(int offset, int limit, int groupId, String fullName, String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy tổng số user thỏa mãn điều kiện tìm kiếm theo fullName và groupId
	 * 
	 * @param groupId mã nhóm tìm kiếm
     * @param fullName tên tìm kiếm
	 * @return tổng số user
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException;
	
	/**
	 * Lấy các trường được sắp xếp trong DB
	 * 
	 * @return trả vể danh sách các trường có thể được sắp xêp
	 */
	List<String> getColumnSort() throws ClassNotFoundException, SQLException;
	
	/**
	 * Lấy thông tin của user theo Email
	 * 
	 * @param userId trường hợp update thì có giá trị truyền vào. Nếu là Add thì truyền vào là null
	 * @param email Địa chỉ mail cần kiểm tra
	 * @return đối tượng TblUserEntities
	 */
	TblUserEntities getUserByEmail(int userId , String email) throws SQLException , ClassNotFoundException;
	
	/**
	 * lấy đối tượng TblUserEntities bằng loginName
	 * 
	 * @param loginName Tên đăng nhập cần kiểm tra
	 * @return trả về đối tượng TblUserEntities
	 */
	TblUserEntities getUserByLoginNameCheckExisted (String loginName) throws SQLException , ClassNotFoundException;
	/**
	 * Lấy user trong bảng tbl_user bằng userid
	 * 
	 * @param userId id user cần lấy
	 * @return trả về đối tượng TblUserEntities
	 */
	TblUserEntities getUserById (int userId) throws SQLException , ClassNotFoundException;
	
	/**
	 * Thực hiện thêm mới 1 user vào bảng tbl_user
	 * 
	 * @param user đối tượng user chứa thông tin của user để ghi vào bảng tbl_user
	 * @return trả về id của user vừa thêm mới
	 */
	int insertUser(TblUserEntities user) throws SQLException;
	
	/**
	 * Lấy đối tượng userInfor từ userId 
	 * 
	 * @param userId id user cần lấy
	 * @return trả về đối tượng UserInforEntities
	 */
	UserInforEntities getUserInforById(int userId) throws SQLException, ClassNotFoundException;
	/**
	 * Thực hiện update 1 user vào bảng tbl_user
	 * 
	 * @param user đối tượng TblUserEntities chứa thông tin của user
	 * @return trả về true nếu insert thành công
	 */
	boolean updateTblUSer(TblUserEntities user) throws SQLException;
	/**
	 * xóa thông tin user trong bảng tbl_user dựa vào userId
	 * 
	 * @param userId id của user cần xóa
	 */
	void deleteTblUser(int userId) throws SQLException;
} 
