/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblDetailUserJapanDaoImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import manageuser.dao.TblDetailUserJapanDao;
import manageuser.entities.TblDetailUserJapanEntities;

/**
 * class thao tác với bảng tbl_detail_user_japan
 * @author tiepnd
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {
	
	/**
	 * Thực hiện thêm mới 1 đối tượng TblDetailUserJapanEntities vào bảng tbl_detail_user_japan
	 * 
	 * @param detailUserJapan Đối tượng chứa thông tin của TblDetailUserJapan
	 */
	@Override
	public void insertTblDetailUserJapanDao(TblDetailUserJapanEntities detailUserJapan) throws SQLException {
		try {
			if (connection != null) {
				//Tạo câu truy vấn
				String sqlString = "INSERT INTO tbl_detail_user_japan (user_id, code_level, start_date, end_date, total) VALUES (?, ?, ?, ?, ?);";
				//Tạo đối tượng thực thi câu truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
				//tạo biến đếm
				int i = 1;
				preparedStatement.setInt(i++, detailUserJapan.getUserId());
				preparedStatement.setString(i++, detailUserJapan.getCodeLevel());
				Date startDate  = new Date(detailUserJapan.getStartDate().getTime());
				preparedStatement.setObject(i++, startDate);
				Date endDate  = new Date(detailUserJapan.getEndDate().getTime());
				preparedStatement.setObject(i++, endDate);
				preparedStatement.setInt(i++, detailUserJapan.getTotal());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("TblDetailUserJapanDaoImpl:inserTblDetailUserJapanDao:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Lấy thông tin user trong bảng tbl_detail_user_japan bằng userid
	 * 
	 * @param userId id của user cần lấy
	 * @return trả về đối tượng TblDetailUserJapanEntities chứa thông tin user trong bảng tbl_detail_user_japan
	 */
	@Override
	public TblDetailUserJapanEntities getDetailUserById(int userId) throws SQLException, ClassNotFoundException {
		TblDetailUserJapanEntities detailUser = null;
		//Tạo biến đếm, tăng index của preparestatement và result set
	    int c = 1;
		try {
			//Mở kết nối
			openConnection();
			if (connection != null) {
				//Tạo câu truy vấn
				String queryString = "select detail_user_japan_id from tbl_detail_user_japan where user_id = ? ;";
				//Tạo đối tượng PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(queryString);
				//Set parameter cho đối tượng PreparedStatement
				preparedStatement.setInt(c, userId);
				//Lấy kết quả lưu vào tập resultSet
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					detailUser = new TblDetailUserJapanEntities();
					//set dữ liệu tương ứng từ result set cho đối tượng tblUserInforEntities
					detailUser.setDetailUserJapanId(resultSet.getInt(c));
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("TblDetailUserJapanDaoImpl:getDetailUserById:" + e.getMessage());
			throw e;
		} finally {
			//đóng kết nối
			closeConnection();
		}
		//trả về user
		return detailUser;
	}
	
	/**
	 * Thực hiện update thông tin user vào bảng tbl_detail_user_japan
	 * 
	 * @param detailUserJapan đối tượng TblDetailUserJapanEntities chứa thông tin user
	 */
	@Override
	public void updateTblDetailUserJapanDao(TblDetailUserJapanEntities detailUserJapan) throws SQLException {
		try {
			if (connection != null) {
				//Tạo câu truy vấn
				String sqlString = "UPDATE tbl_detail_user_japan SET code_level = ?, start_date = ?, end_date = ?, total = ? WHERE (user_id = ?);";
				//Tạo đối tượng thực thi câu truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
				//tạo biến đếm
				int i = 1;
				//Set parameter cho đối tượng PreparedStatement
				preparedStatement.setString(i++, detailUserJapan.getCodeLevel());
				preparedStatement.setObject(i++, detailUserJapan.getStartDate().toInstant().atZone(ZoneId.of("Asia/Bangkok")).toLocalDate());
				preparedStatement.setObject(i++, detailUserJapan.getEndDate().toInstant().atZone(ZoneId.of("Asia/Bangkok")).toLocalDate());
				preparedStatement.setInt(i++, detailUserJapan.getTotal());
				preparedStatement.setInt(i++, detailUserJapan.getUserId());
				//thực thi update
				preparedStatement.executeUpdate();
			}
		//bắt lỗi
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblDetailUserJapanDaoImpl:updateTblDetailUserJapanDao:" + e.getMessage());
			//ném lỗi
			throw e;
		}
	}
	
	/**
	 * Thực hiện delete thông tin user trong bảng tbl_detail_user_japan
	 * 
	 * @param userId id của user cần xóa
	 */
	@Override
	public void deleteTblDetailUserJapanDao(int userId) throws SQLException {
		try {
			if (connection != null) {
				//Tạo câu truy vấn
				String sqlString = "delete from tbl_detail_user_japan where user_id = ?;";
				//Tạo đối tượng thực thi câu truy vấn
				PreparedStatement preparedStatement = connection.prepareStatement(sqlString);
				//tạo biến đếm
				int i = 1;
				//Set parameter cho đối tượng PreparedStatement
				preparedStatement.setInt(i++, userId);
				//thực thi update
				preparedStatement.executeUpdate();
			}
		//bắt lỗi
		} catch (SQLException e) {
			//ghi log
			System.out.println("TblDetailUserJapanDaoImpl:deleteTblDetailUserJapanDao:" + e.getMessage());
			//ném lỗi
			throw e;
		}
	}
}
