/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblUserDaoImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.TblUserDao;
import manageuser.entities.TblUserEntities;
import manageuser.entities.UserInforEntities;
import manageuser.utils.Common;
import manageuser.utils.Constant;

/**
 * Class thao tác với bảng tbl_user trong DB
 * 
 * @author tiepnd
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {
	/**
	 * Lấy ra đối tượng TblUser trong DB thỏa mãn loginName
	 * 
	 * @param loginName login name
	 * @return trả về 1 đối tượng TblUSer
	 */
	@Override
	public TblUserEntities getUserByLoginName(String loginName) throws ClassNotFoundException, SQLException {
		TblUserEntities tblUserEntities = null;
		try {
			//khởi tạo kết nối đến database
			openConnection();
			if (connection != null) {
				//tạo câu truy vấn để lấy dữ liệu
				String selectQuery = "select pass, salt from tbl_user where rule = ? and login_name = ?;";
				//Tạo đối tượng thực thi, nạp câu truy vẫn vào mysql
				PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
				//reset biến tăng
				//tạo biến tự tăng cho index của result set và preparedStatement
				int i = 1;
				//set giá trị ? là rule
				preparedStatement.setInt(i++, Constant.ADMIN_RULE);
				//set giá trị ? là loginName
				preparedStatement.setString(i++, loginName);
				//Thực thi truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();			
				while (resultSet.next()) {
					//reset biến tăng
					i = 1;
					tblUserEntities = new TblUserEntities();
					tblUserEntities.setPassword(resultSet.getString(i++));
					tblUserEntities.setSalt(resultSet.getString(i++));
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUSerDaoImpl:getUserByLoginName:" + e.getMessage());
			throw e;
		} finally {
			//Đóng kết nối
			closeConnection();
		}
		//Trả về user 
		return tblUserEntities; 
	}
	
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
	@Override
	public List<UserInforEntities> getListUsers(int offset, int limit, int groupId, String fullName, String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws ClassNotFoundException, SQLException{
		//tại list chứa danh sách user
		List<UserInforEntities> listUserInfor = new ArrayList<UserInforEntities>();
		//Tạo biến đếm để tăng index của preparestatement và result set
		int i = 1;
		try {
			//mở kết nối
			openConnection();
			if (connection != null) {
				//tạo câu truy vấn để lấy dữ liệu từ DB
				StringBuilder selectQuery = new StringBuilder();
				selectQuery.append("SELECT u.user_id, u.full_name, u.email,u.tel,u.birthday,g.group_name,j.name_level,d.end_date, d.total ");
				selectQuery.append("FROM tbl_user u INNER JOIN mst_group g on u.group_id = g.group_id ");
				selectQuery.append("LEFT JOIN tbl_detail_user_japan d on u.user_id = d.user_id ");
				selectQuery.append("LEFT JOIN mst_japan j on d.code_level = j.code_level ");
				selectQuery.append("WHERE u.rule = ? and u.full_name LIKE ? ");
				selectQuery.append(groupId == 0 ? "" : "AND u.group_id = ? ");
				//Tạo whitelist để kiểm trả trường truyền vào có tồn tại không trước khi order by
				List<String> whiteList = getColumnSort();
				if (whiteList.contains(sortType)) {
					switch (sortType) {
					case Constant.FULL_NAME_TYPE:
						selectQuery.append("ORDER BY u.full_name " + sortByFullName + ", j.code_level " + sortByCodeLevel + ", d.end_date " + sortByEndDate);
						break;
					case Constant.CODE_LEVEL_TYPE:
						selectQuery.append("ORDER BY j.code_level " + sortByCodeLevel + ", u.full_name " + sortByFullName + ", d.end_date " + sortByEndDate);
						break;				
					case Constant.END_DATE_TYPE:
						selectQuery.append("ORDER BY d.end_date " + sortByEndDate + ", u.full_name " + sortByFullName + ", j.code_level " + sortByCodeLevel);
						break;
					}
				} else {
					selectQuery.append("ORDER BY u.full_name , j.code_level , d.end_date " + Constant.VALUE_SORT_BY_END_DATE_DEFAULT);
				}
				selectQuery.append(" LIMIT ? OFFSET ?;");	
				//Tạo đối tượng thực thi, nạp câu truy vẫn vào mysql
				PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString());
				//reset index = 1;
				i = 1;
				//set tham số thực thi truy vấn cho prepareStatement
				preparedStatement.setInt(i++, Constant.USER_RULE);
				preparedStatement.setString(i++, "%" + fullName + "%");
				if (groupId != 0) {
					preparedStatement.setInt(i++, groupId);
				}
				preparedStatement.setInt(i++, limit);
				preparedStatement.setInt(i++, offset);
				//Thực thi truy vấn
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					UserInforEntities userInfor = new UserInforEntities();
					//reset index = 1
					i = 1;
					//set dữ liệu tương ứng từ result set cho đối tượng tblUserInforEntities
					userInfor.setUserId(resultSet.getInt(i++));
					userInfor.setFullName(resultSet.getString(i++));
					userInfor.setEmail(resultSet.getString(i++));
					userInfor.setTel(resultSet.getString(i++));
					userInfor.setBirthday(resultSet.getDate(i++));
					userInfor.setGroupName(resultSet.getString(i++));
					userInfor.setNameLevel(resultSet.getString(i++));
					userInfor.setEndDate(resultSet.getDate(i++));
					int total = resultSet.getInt(i++);
					if (total > 0) {
						userInfor.setTotalString(String.valueOf(total));
					}
					//thêm đối tượng userInforEntities vào list
					listUserInfor.add(userInfor);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl:getListUsers:" + e.getMessage());
			throw e;
		} finally {
			//Đóng kết nối
			closeConnection();
		}
		//trả về listUserInfor
		return listUserInfor;
	}
	
	/**
	 * Lấy tổng số user thỏa mãn điều kiện tìm kiếm theo fullName và groupId
	 * 
	 * @param groupId mã nhóm tìm kiếm
     * @param fullName tên tìm kiếm
	 * @return tổng số user
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@Override
	public int getTotalUsers(int groupId, String fullName) throws ClassNotFoundException, SQLException{
		//tạo biến chứa tổng số user thỏa mãn
		int totalUser = 0;
		try {
			//mở kết nối
			openConnection();
			if (connection != null) {
				//Tạo câu truy vấn
				StringBuffer queryString = new StringBuffer();
				queryString.append("select count(user_id) from tbl_user u");
				queryString.append(" where u.rule = ?");
				queryString.append(groupId == 0 ? "" : " and u.group_id = ?");
				queryString.append(Common.compareString(Constant.STRING_BLANK, fullName) ? "" : " and u.full_name like ?;");
				//Tạo đối tượng PrepareStament
				PreparedStatement preparedStatement = connection.prepareStatement(queryString.toString());
				int i = 1;
				//set tham số thực thi truy vấn
				preparedStatement.setInt(i++, Constant.USER_RULE);
				if (groupId > 0) {
					preparedStatement.setInt(i++, groupId);
				}
				if (!Common.compareString(Constant.STRING_BLANK, fullName)) {
					preparedStatement.setString(i++, "%" + fullName + "%");
				}
				//thực thi truy vấn, trả kết quả cho result set
				ResultSet resultSet = preparedStatement.executeQuery();
				//nếu tập resultSet có kết quả
				if (resultSet.next()) {
					//lấy về tổng số user thỏa mãn
					totalUser = resultSet.getInt(1);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserDaoImpl:getTotalUsers:" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			closeConnection();
		}
		//trả về tổng số user
		return totalUser;
	}
	
	/**
	 * Lấy các trường được sắp xếp trong DB
	 * 
	 * @return trả vể danh sách các trường có thể được sắp xêp
	 */
	@Override
	public List<String> getColumnSort() throws ClassNotFoundException, SQLException{
		//tạo list chứa các trường có thể được sắp xếp
		List<String> listColumnSort = new ArrayList<>();
		try {
			if (connection != null) {
				//tạo câu truy vấn
				StringBuilder selectQuery = new StringBuilder();
				selectQuery.append("SELECT u.full_name, d.code_level, d.end_date  ");
				selectQuery.append("FROM tbl_user u INNER JOIN mst_group g on u.group_id = g.group_id ");
				selectQuery.append("LEFT JOIN tbl_detail_user_japan d on u.user_id = d.user_id ");
				selectQuery.append("LEFT JOIN mst_japan j on d.code_level = j.code_level ");
				selectQuery.append(" LIMIT 1 OFFSET 0;");
				//Tạo đối tượng PrepareStament để thực thi câu truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString());
				//thực thi truy vấn, trả kết quả cho result set
				ResultSet resultSet = preparedStatement.executeQuery();
				//lấy các columns của đối tượng resultSet
				ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
				//tạo biến đếm tăng index của resultSetMetadata
				int i = 1;
				//add vào listColumnSort
				listColumnSort.add(resultSetMetadata.getColumnName(i++));
				listColumnSort.add(resultSetMetadata.getColumnName(i++));
				listColumnSort.add(resultSetMetadata.getColumnName(i++));
			}
		//bắt lỗi
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl:getColumbSort:" + e.getMessage());
			//ném lỗi
			throw e;
		}
		//trả về listColumnSort
		return listColumnSort;
	}

	/**
	 * Lấy thông tin của user theo Email
	 * 
	 * @param userId trường hợp update thì có giá trị truyền vào. Nếu là Add thì truyền vào là null
	 * @param email Địa chỉ mail cần kiểm tra
	 * @return đối tượng TblUserEntities
	 */
	@Override
	public TblUserEntities getUserByEmail(final int userId , final String email) throws SQLException , ClassNotFoundException{
		TblUserEntities user = null;
		//Tạo biến đếm, tăng index của preparestatement và result set
	    int i = 1;
		try {
			//Mở kết nối
			openConnection();
			if (connection != null) {
				//Tạo câu truy vấn
				StringBuilder queryString = new StringBuilder("select user_id from tbl_user where email = ? ");
				if (userId > 0) {
					queryString.append("and user_id != ?;");
				}
				//Tạo đối tượng PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(queryString.toString());
				//Set parameter cho đối tượng PreparedStatement
				preparedStatement.setString(i++, email);
				if (userId > 0) {
					preparedStatement.setInt(i++, userId);
				}
				//Lấy kết quả lưu vào tập resultSet
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					//Khởi tạo user
					user = new TblUserEntities();
					//reset index = 1
					i = 1;
					//set dữ liệu tương ứng từ result set cho đối tượng tblUserInforEntities
					user.setUserId(resultSet.getInt(i++));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserDaoImpl:getUserByEmail:" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			closeConnection();
		}
		//trả về user
		return user;
	}
	
	/**
	 * lấy đối tượng TblUserEntities bằng loginName
	 * 
	 * @param loginName Tên đăng nhập cần kiểm tra
	 * @return trả về đối tượng TblUserEntities
	 */
	@Override
	public TblUserEntities getUserByLoginNameCheckExisted(String loginName) throws SQLException , ClassNotFoundException{
		TblUserEntities user = null;
		//Tạo biến đếm, tăng index của preparestatement và result set
	    int i = 1;
		try {
			//Mở kết nối 
			openConnection();
			if (connection != null) {
				//Tạo câu truy vấn
				StringBuilder queryString = new StringBuilder("select user_id from tbl_user where login_name = ?;");
				//Tạo đối tượng PreparedStatement để thực thi câu truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(queryString.toString());
				//Set parameter cho đối tượng PreparedStatement
				preparedStatement.setString(i++, loginName);
				//thực thi truy vấn và lưu vào tập resultSet
				ResultSet resultSet = preparedStatement.executeQuery();
				//nếu tập resultSet có giá trị
				if (resultSet.next()) {
					//khởi tạo đối tượng TblUserEntities 
					user = new TblUserEntities();
					//reset index = 1
					i = 1;
					//set dữ liệu tương ứng từ result set cho đối tượng tblUserInforEntities
					user.setUserId(resultSet.getInt(i++));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserDaoImpl:getColumbSort:" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			closeConnection();
		}
		//trả về user
		return user;
	}
	
	/**
	 * Lấy user trong bảng tbl_user bằng userid
	 * 
	 * @param userId id user cần lấy
	 * @return trả về đối tượng TblUserEntities
	 */
	@Override
	public TblUserEntities getUserById(int userId) throws SQLException, ClassNotFoundException {
		TblUserEntities user = null;
		//Tạo biến đếm, tăng index của preparestatement và result set
	    int i = 1;
		try {
			//Mở kết nối
			openConnection();
			if (connection != null) {
				//Tạo câu truy vấn
				String queryString = "select rule from tbl_user where user_id = ? ;";
				//Tạo đối tượng PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(queryString.toString());
				//Set parameter cho đối tượng PreparedStatement
				preparedStatement.setInt(i, userId);
				//Lấy kết quả lưu vào tập resultSet
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					user = new TblUserEntities();
					//set dữ liệu tương ứng từ result set cho đối tượng tblUserInforEntities
					user.setRule(resultSet.getInt(i++));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblUserDaoImpl:getColumbSort:" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			closeConnection();
		}
		//trả về user
		return user;
	}
	
	/**
	 * Thực hiện thêm mới 1 user vào bảng tbl_user
	 * 
	 * @param user đối tượng user chứa thông tin của user để ghi vào bảng tbl_user
	 * @return trả về id của user vừa thêm mới
	 */
	@Override
	public int insertUser(TblUserEntities user) throws SQLException {
		//tạo biến chứa userId thêm mới
		int userId = 0;
		try {
			if (connection != null) {
				//tạo câu lệnh insert vào DB
				String sqlString = "INSERT INTO tbl_user (group_id, login_name, pass, full_name, full_name_kana, email, tel, birthday, rule, salt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
				//tạo đối tượng thực thi câu lệnh insert vào DB
				PreparedStatement preparedStatement = connection.prepareStatement(sqlString, PreparedStatement.RETURN_GENERATED_KEYS);
				//tạo biến tự tăng cho index của preparedStatement
				int i = 1;
				//set tham số cho đối tượng prepareStatemen
				preparedStatement.setInt(i++, user.getGroupId());
				preparedStatement.setString(i++, user.getLoginName());
				preparedStatement.setString(i++, user.getPassword());
				preparedStatement.setString(i++, user.getFullName());
				preparedStatement.setString(i++, user.getFullNameKana());
				preparedStatement.setString(i++, user.getEmail());
				preparedStatement.setString(i++, user.getTel());
				preparedStatement.setObject(i++, user.getBirthday().toInstant().atZone(ZoneId.of("Asia/Bangkok")).toLocalDate());
				preparedStatement.setInt(i++, user.getRule());
				preparedStatement.setString(i++, user.getSalt());
				//thực thi câu lệnh insert vào DB
				preparedStatement.executeUpdate();
				//lấy userId được tự động sinh ra khi insert 1 bản ghi vào bảng tbl_user
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				//reset biến tự tăng cho index
				i = 1;
				//nếu tập resultSet có giá trị
				if (resultSet.next()) {
					//lấy về userId
					userId = resultSet.getInt(i++);
				}
			}
		//bắt catch
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl:insertUser:" + e.getMessage());
			//ném lỗi
			throw e;
		}
		//trả về userId
		return userId;
	}
	
	/**
	 * Lấy đối tượng userInfor từ userId 
	 * 
	 * @param userId id user cần lấy
	 * @return trả về đối tượng UserInforEntities
	 */
	@Override
	public UserInforEntities getUserInforById(int userId) throws SQLException, ClassNotFoundException {
		UserInforEntities userInfor = null;
		//tạo biến tự tăng cho index của result set và preparedStatement
		int i = 1;
		try {
			//khởi tạo kết nối đến database
			openConnection();
			if (connection != null) {
				//tạo câu truy vấn
				StringBuilder selectQuery = new StringBuilder();
				selectQuery.append("SELECT u.login_name,g.group_id ,g.group_name, u.full_name,u.full_name_kana,u.birthday, u.email,u.tel,j.name_level,j.code_level,d.start_date, d.end_date, d.total ");
				selectQuery.append("FROM tbl_user u INNER JOIN mst_group g on u.group_id = g.group_id ");
				selectQuery.append("LEFT JOIN tbl_detail_user_japan d on u.user_id = d.user_id ");
				selectQuery.append("LEFT JOIN mst_japan j on d.code_level = j.code_level ");
				selectQuery.append("WHERE u.user_id = ?;");
				//Tạo đối tượng thực thi, nạp câu truy vẫn vào mysql
				PreparedStatement preparedStatement = connection.prepareStatement(selectQuery.toString());
				//set tham số thực thi truy vấn cho prepareStatement
				preparedStatement.setInt(i, userId);
				//Thực thi truy vấn lấy về tập resultSet
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					//khởi tạo userInfor
					userInfor = new UserInforEntities();
					//set dữ liệu tương ứng từ result set cho đối tượng tblUserInforEntities
					userInfor.setLoginName(resultSet.getString(i++));
					userInfor.setGroupId(resultSet.getInt(i++));
					userInfor.setGroupName(resultSet.getString(i++));
					userInfor.setFullName(resultSet.getString(i++));
					userInfor.setFullNameKana(resultSet.getString(i++));
					userInfor.setBirthday(resultSet.getDate(i++));
					userInfor.setEmail(resultSet.getString(i++));
					userInfor.setTel(resultSet.getString(i++));
					userInfor.setNameLevel(resultSet.getString(i++));
					userInfor.setCodeLevel(resultSet.getString(i++));
					userInfor.setStartDate(resultSet.getDate(i++));
					userInfor.setEndDate(resultSet.getDate(i++));
					userInfor.setTotal(resultSet.getInt(i++));
					if (userInfor.getTotal() != 0) {
						userInfor.setTotalString(String.valueOf(userInfor.getTotal()));
					}
					userInfor.setUserId(userId);
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("TblUserDaoImpl:getListUsers:" + e.getMessage());
			throw e;
		} finally {
			//Đóng kết nối
			closeConnection();
		}
		return userInfor;
	}
	
	/**
	 * Thực hiện update 1 user vào bảng tbl_user
	 * 
	 * @param user đối tượng TblUserEntities chứa thông tin của user
	 * @return trả về true nếu insert thành công
	 */
	@Override
	public boolean updateTblUSer(TblUserEntities user) throws SQLException {
		//tạo biến lưu trạng update thông công hay không
		boolean updateSuccess = true;
		try {
			if (connection != null) {
				//tạo câu truy vấn
				String sqlString = "update tbl_user set group_id = ?, full_name = ?, full_name_kana = ?, email = ?, tel = ?, birthday = ? WHERE (user_id = ?);";
				//tạo đối tượng thực thi câu truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
				//tạo biến tự tăng cho index của preparedStatement
				int i = 1;
				////set tham số thực thi truy vấn cho prepareStatemen
				preparedStatement.setInt(i++, user.getGroupId());
				preparedStatement.setString(i++, user.getFullName());
				preparedStatement.setString(i++, user.getFullNameKana());
				preparedStatement.setString(i++, user.getEmail());
				preparedStatement.setString(i++, user.getTel());
				Date birthday  = new Date(user.getBirthday().getTime());
				preparedStatement.setObject(i++,  birthday);
				preparedStatement.setInt(i++, user.getUserId());
				//thực thi cập nhật trả về số lần update
				int updateCount = preparedStatement.executeUpdate();
				//update thông công nếu số lần update lớn hơn 0
				updateSuccess = updateCount > Constant.ZERO;
			}
		//bắt catch
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl:updateTblUSer:" + e.getMessage());
			//ném lỗi
			throw e;
		}
		//trả về updateSuccess
		return updateSuccess;
	}
	
	/**
	 * xóa thông tin user trong bảng tbl_user dựa vào userId
	 * 
	 * @param userId id của user cần xóa
	 */
	@Override
	public void deleteTblUser(int userId) throws SQLException {
		try {
			if (connection != null) {
				//Tạo câu truy vấn
				String sqlString = "DELETE FROM tbl_user WHERE (user_id = ?);";
				//Tạo đối tượng thực thi câu truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
				//tạo biến đếm
				int i = 1;
				//Set parameter cho đối tượng PreparedStatement
				preparedStatement.setInt(i++, userId);
				//thực thi update
				preparedStatement.execute();
			}
		//bắt catch
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblUserDaoImpl:deleteTblUser:" + e.getMessage());
			//ném lỗi
			throw e;
		}
	}
}
