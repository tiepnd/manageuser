/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblUserLogicImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.TblUserDao;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.dao.impl.TblUserDaoImpl;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.logics.TblUserLogic;
import manageuser.utils.Common;

/**
 * Xử lý các logic của đối tượng TblUser
 * 
 * @author tiepnd
 */
public class TblUserLogicImpl implements TblUserLogic {
	/**
	 * Kiểm tra tồn tại đăng nhập

	 * @param loginName tên đăng nhập
	 * @param password mật khẩu
	 * @return trả về true nếu tồn tại đăng nhâp, false nếu không tồn tại
	 */
	@Override
	public Boolean checkExistLogin(String loginName, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		try {
			//Khởi tạo đối tượng TblUserDao
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//Lấy ra user với loginName
			TblUserEntities user = tblUserDao.getUserByLoginName(loginName);
			//Nếu user không tồn tại trả về false
			if (user == null) {
				return false;
			}
			//Nếu user tồn tại, mã hóa mật khẩu người dùng và salt của user vì trong DB đã lưu password đã được mã hóa 1 chiều require yêu cầu mã hóa theo thuật toán SHA-1
			String strEnscrypt = Common.encrypt(password, user.getSalt());
			//So sánh mật khẩu đã mã hóa của người dùng nhập với mật khẩu của user trong DB và trả về kết quả
			return Common.compareString(strEnscrypt, user.getPassword());
		} catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
			System.out.println("TblUserLogicImpl:checkExistLogin:" + e.getMessage());
			throw e;
		}
	}
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
	@Override
	public List<UserInforEntities> getListUsers(int offset, int limit, int groupId, String fullName, String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws ClassNotFoundException, SQLException{
		//tạo list chứa danh sách user
		List<UserInforEntities> listUserInforEntities = new ArrayList<UserInforEntities>();
		//tạo đối tượng TblUserDao để sử dụng phương thức getListUsers
		TblUserDao tblUserDao = new TblUserDaoImpl();
		try {
			//lấy danh sách user từ DB để trả về 
			listUserInforEntities = tblUserDao.getListUsers(offset, limit, groupId, Common.replaceWildcard(fullName), sortType, sortByFullName, sortByCodeLevel, sortByEndDate);
			//trả về danh sách user
			return listUserInforEntities;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl:getListUsers:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Kiểm tra tồn tại 1 user có rule bằng 0 và login name bằng loginName
	 * 
	 * @param loginName truyền vào login cần kiểm tra
	 * @return Trả về true nếu TblUserEntities tồn tại
	 */
	@Override
	public boolean checkAdmin(String loginName) throws SQLException , ClassNotFoundException{
		try {
			//Tạo đối tượng TblUserDao để sử dụng phương thức getUserByLoginName
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//Lấy về user với loginName 
			TblUserEntities user = tblUserDao.getUserByLoginName(loginName);
			//Trả về true nếu user tồn tại, flase nếu user không tồn tại
			return !(user == null);
		} catch (SQLException|ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl:checkAdmin" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Lấy tổng số user thỏa mãn điều kiện tìm kiếm theo fullname và groupId
	 * 
	 * @param  groupId mã nhóm tìm kiếm
     * @param fullName tên tìm kiếm
	 * @return tổng số user
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException{
		try {
			//tạo đối tượng TblUserDao để sử dụng phương thức getTotalUsers
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//replace các toàn tử wildcard để mysql hiểu câu truy vấn và lấy về tổng số user
			return tblUserDao.getTotalUsers(groupId, Common.replaceWildcard(fullName));
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserLogicImpl:getTotalUsers:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Kiểm tra email đã tồn tại trong bảng tbl_user chưa?
	 * 
	 * @param userId id của user cần check
	 * @param email email của user cần check
	 * @return trả về true email đã tồn tại, flase email không tồn tại
	 */
	@Override
	public boolean checkExistedEmail(int userId, String email) throws SQLException , ClassNotFoundException{
		//Tạo đối tượng TblUserDao để sử dụng phương thức checkExistInDatabase
		TblUserDao tblUserDao = new TblUserDaoImpl();
		try {
			//lấy user bởi email và userId
			TblUserEntities user = tblUserDao.getUserByEmail(userId, email);
			//Trả về true nếu user tồn tại, flase nếu user không tồn tại
			return user != null;
		} catch (SQLException|ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl:checkExistedEmail" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Kiểm tra loginName đã tồn tại trong bảng tbl_user chưa?
	 * 
	 * @param loginName tên đăng nhập của user cần kiểm tra
	 * @return trả về true loginName đã tồn tại, flase loginName không tồn tại
	 */
	public boolean checkExistedLoginName(String loginName ) throws SQLException , ClassNotFoundException{
		try {
			//Tạo đối tượng TblUserDao để sử dụng phương thức checkExistInDatabase
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//Lấy user bởi loginName
			TblUserEntities user = tblUserDao.getUserByLoginNameCheckExisted(loginName);
			//Trả về true nếu user tồn tại, flase nếu user không tồn tại
			return user != null;
		} catch (SQLException|ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl:checkExistedLoginName" + e.getMessage());
			throw e;
		}
	}
	
	
	/**
	 * Kiểm tra có tồn tại user trong bảng tbl_user bằng userid
	 * 
	 * @param userId id của user cần check
	 * @return trả về true nếu tồn tại user trong bảng tbl_user, flase nếu không tồn tại user trong bảng tbl_user
	 */
	@Override
	public boolean checkExistUserById(int userId) throws SQLException, ClassNotFoundException {
		try {
			//Tạo đối tượng TblUserDao để sử dụng phương thức getUserById
			TblUserDao tblUserDao = new TblUserDaoImpl();
			//Lấy user bởi userid
			TblUserEntities user = tblUserDao.getUserById(userId);
			//Trả về true nếu user tồn tại, flase nếu user không tồn tại
			return user != null;
		} catch (SQLException|ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl:checkExistUserById" + e.getMessage());
			throw e;
		}
	}
	
	
	/**
	 * Insert data  user vào bảng tbl_user và tbl_detail_user_japan
	 * 
	 * @param userInfor chứa thông tin user cần check
	 * @return true nếu insert thành công, false nếu insert không thành công
	 */
	@Override
	public boolean createUser(UserInforEntities userInfor){
		//khởi tạo đối tượng TblUserDao để thao tác vào bảng tbl_user
		TblUserDao userDao = new TblUserDaoImpl();
		//khởi tạo đối tượng TblDetailUserJapanDao để thao tác và bảng tbl_detail_user_japan
		TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();
		try {
			//Mở kết nối chung bằng đối tượng userDao
			userDao.openConnection();
			//Lấy ra kết nối chung đó để set cho đối tượng detailUserJapanDao
			Connection connection = userDao.getConnection();
			//Set chế độ auto commit của connection là false để quản lý connection nếu lỗi thì rollback, không lỗi thì commit
			userDao.disableAutoCommit();
			//lấy user từ đối tượng userInfor để insert vào bảng tbl_user
			TblUserEntities user = Common.getUserFromUserInfor(userInfor);
			//insert user vào bảng tbl_user nếu thành công trả về userid
			int userId = userDao.insertUser(user);
			//set userId cho đối tượng userInfor
			userInfor.setUserId(userId);
			//nếu insert được bản ghi vào bảng tbl_user và người dùng có vùng nhập trình độ tiếng nhật
			if (userId > 0 && Common.checkEnterJapan(userInfor.getCodeLevel())) {
				//lấy trình độ tiếng nhật từ đối tượng userInfor để insert vào bảng tbl_detail_user_japan
				TblDetailUserJapanEntities detailUserJapan = Common.getDetailUserJapanFromUserInfor(userInfor);
				//set connection chung cho đối tượng detailUserJapanDao
				detailUserJapanDao.setConnection(connection);
				//insert trình độ tiếng nhật vào bảng tbl_detail_user_japan
				detailUserJapanDao.insertTblDetailUserJapanDao(detailUserJapan);
			}
			//commit dữ liệu để update vào DB
			userDao.commit();
			//trả về update thành công
			return true;
		//bắt lỗi
		} catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
			System.out.println("TblUserLogicImpl:createUser:" + e.getMessage());
			//rollback dữ liệu khi bắt được lỗi
			userDao.rollback();
			//trả về update thất bại
			return false;
		}
		finally {
			//đóng kết nối
			userDao.closeConnection();			
		}
	}
	
	/**
	 * Lấy đối tượng userInfor từ userId 
	 * 
	 * @param userId id của user cần lấy
	 * @return trả về userInfor
	 */
	@Override
	public UserInforEntities getUserInforById(int userId) throws SQLException, ClassNotFoundException {
		//tạo đối userInfor để trả về
		UserInforEntities userInfor = null;
		try {
			//tạo đối tượng userDao để lấy userInfor từ DB
			TblUserDao userDao = new TblUserDaoImpl();
			//lấy đối tượng userInfor từ DB
			userInfor = userDao.getUserInforById(userId);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl:getUserInforById" + e.getMessage());
			throw e;
		}
		return userInfor;
	}
	
	/**
	 * Lấy đối tượng userInfor từ userId 
	 * 
	 * @param userId id của user cần lấy
	 * @return trả về userInfor
	 */
	@Override
	public TblUserEntities getUserById(int userId) throws SQLException, ClassNotFoundException {
		//khởi tạo user để trả về
		TblUserEntities user = null;
		
		try {
			//khởi tạo đối tượng userDao để lấy user từ DB
			TblUserDao userDao = new TblUserDaoImpl();
			//lấy user từ DB và gán cho đối tượng user
			user = userDao.getUserById(userId);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserLogicImpl:getUserById" + e.getMessage());
			throw e;
		}
		return user;
	}
	
	/**
	 * update thông tin user vào bảng tbl_user và tbl_detail_user_japan
	 * 
	 * @param userInfor chứa thông tin của user
	 * @return true nếu update thành công, false nếu update không thành công
	 */
	@Override
	public boolean updateUser(UserInforEntities userInfor) {
		//khởi tạo đối tượng TblUserDao để thao tác vào bảng tbl_user
		TblUserDao userDao = new TblUserDaoImpl();
		//khởi tạo đối tượng TblDetailUserJapanDao để thao tác và bảng tbl_detail_user_japan
		TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();
		try {
			//Mở kết nối chung bằng đối tượng userDao
			userDao.openConnection();
			//Lấy ra kết nối chung đó để set cho đối tượng detailUserJapanDao
			Connection connection = userDao.getConnection();
			//Set chế độ auto commit của connection là false để quản lý connection nếu lỗi thì rollback, không lỗi thì commit
			userDao.disableAutoCommit();
			//lấy user từ đối tượng userInfor để update vào bảng tbl_user
			TblUserEntities user = Common.getUserFromUserInfor(userInfor);
			//upadate user vào bảng tbluser nếu thành công trả về true, nếu không thành công trả về false
			boolean updateSuccess = userDao.updateTblUSer(user);
			//nếu upadate user vào bảng tbluser thành công
			if (updateSuccess) {
				//kiểm tra xem người dùng có nhập trình độ tiếng nhật không
				boolean isEnterJapan = Common.checkEnterJapan(userInfor.getCodeLevel());
				//nếu người dùng có nhập trình độ tiếng nhật
				if (isEnterJapan) {
					//lấy trình độ tiếng nhật từ đối tượng userInfor để update vào bảng tbl_detail_user_japan
					TblDetailUserJapanEntities detailUserJapan = Common.getDetailUserJapanFromUserInfor(userInfor);
					//set connection cho detailUserJapanDao để cùng connection với userDao
					detailUserJapanDao.setConnection(connection);
					//nếu trong DB người dùng đã có trình độ tiếng nhật thì thực hiện updateTblDetailUserJapanDao
					if (userInfor.isExistedUserId()) {
						//update TblDetailUserJapan
						detailUserJapanDao.updateTblDetailUserJapanDao(detailUserJapan);
					} else { //nếu trong DB người dùng chưa có trình độ tiếng nhật thì thực hiện insertTblDetailUserJapanDao
						//insert TblDetailUserJapan
						detailUserJapanDao.insertTblDetailUserJapanDao(detailUserJapan);
					}	
				} else {//nếu người dùng không nhập trình độ tiếng nhật
					//set connection cho detailUserJapanDao để cùng connection với userDao
					detailUserJapanDao.setConnection(connection);
					//nếu trong DB người dùng đã có trình độ tiếng nhật thì thực hiện deleteTblDetailUserJapanDao
					if (userInfor.isExistedUserId()) {
						//delete TblDetailUserJapan
						detailUserJapanDao.deleteTblDetailUserJapanDao(userInfor.getUserId());
					}
				}
			}
			//commit dữ liệu để ghi vào DB
			userDao.commit();
			//trả về update thành công
			return true;
		//bắt lỗi
		} catch (SQLException | ClassNotFoundException | NoSuchAlgorithmException e) {
			//ghi log
			System.out.println("TblUserLogicImpl:updateUser:" + e.getMessage());
			//rollback dữ liệu khi bắt được lỗi
			userDao.rollback();
			//trả về update thất bại
			return false;
		}
		finally {
			//đóng conection
			userDao.closeConnection();			
		}
	}
	
	/**
	 * thực hiện xóa thông tin user trong 2 bảng tbl_user và tbl_detai_user_japan
	 * 
	 * @param userId id của user cần xóa
	 * @return true nếu delete thành công, false nếu delete không thành công
	 */
	@Override
	public boolean deleteUser(int userId) {
		//khởi tạo đối tượng TblUserDao để thao tác vào bảng tbl_user
		TblUserDao userDao = new TblUserDaoImpl();
		//khởi tạo đối tượng TblDetailUserJapanDao để thao tác và bảng tbl_detail_user_japan
		TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();
		try {
			//Mở kết nối chung bằng đối tượng userDao
			userDao.openConnection();
			//Lấy ra kết nối chung đó để set cho đối tượng detailUserJapanDao
			Connection connection = userDao.getConnection();
			//Set chế độ auto commit của connection là false để quản lý connection nếu lỗi thì rollback, không lỗi thì commit
			userDao.disableAutoCommit();
			//set connection cho detailUserJapanDao để cùng connection với userDao
			detailUserJapanDao.setConnection(connection);
			//delete thông tin user ở bảng tbl_detail_user_japan
			detailUserJapanDao.deleteTblDetailUserJapanDao(userId);
			//delete thông tin user ở bảng tbl_user
			userDao.deleteTblUser(userId);
			//commit dữ liệu để update vào DB
			userDao.commit();
			//trả về update thành công
			return true;
		//bắt lỗi
		} catch (SQLException | ClassNotFoundException e) {
			//ghi log
			System.out.println("TblUserLogicImpl:deleteUser:" + e.getMessage());
			//rollback dữ liệu khi bắt được lỗi
			userDao.rollback();
			//trả về update thất bại
			return false;
		}
		finally {
			//đóng conection
			userDao.closeConnection();			
		}
	}
}
