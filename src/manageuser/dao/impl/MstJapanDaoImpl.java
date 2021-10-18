/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstJapanDaoImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstJapanDao;
import manageuser.entities.MstJapanEntities;

/**
 * Class thao tác với bảng mst_japan trong DB
 * 
 * @author tiepnd
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {
	/**
	 * Lấy tất cả các MstJapan có trong database
	 * 
	 * @return trả về danh sách các MstJapan
	 */
	@Override
	public List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException{
		List<MstJapanEntities> listAllMstJapan = new ArrayList<MstJapanEntities>();
		try {
			//Mở 1 kết nối
			openConnection();
			if (connection != null) {
				//tạo câu truy vấn
				String selectQuery = "select * from mst_japan;";
				//Tạo đối tượng thực thi
				Statement statement = connection.createStatement();
				//Thực thi truy vấn
				ResultSet resultSet = statement.executeQuery(selectQuery);
				//add tập resultset vào 1 List<User>
				while (resultSet.next()) {
					//Tạo đối MstJapanEntities 
					MstJapanEntities mstJapan = new MstJapanEntities();
					//set thuộc tính groupId
					mstJapan.setCodeLevel(resultSet.getString(1));
					//set thuộc tính groupName
					mstJapan.setNameLevel(resultSet.getString(2));
					//thêm MstJapanEntities vào listMstJapanEntities
					listAllMstJapan.add(mstJapan);
				}	
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstJapanDaoImpl:getAllMstJapan:" + e.getMessage());
			throw e;
		} finally {
			//Đóng kết nối
			closeConnection();
		}
		//trả về listMstJapanEntities
		return listAllMstJapan;
	}
	
	/**
	 * Lấy ra tên trình độ tiếng nhật 
	 * 
	 * @param codeLevel truyền vào mã trình độ tiếng nhật
	 * @return trả về nameLevel 
	 */
	@Override
	public String getNameLevel(String codeLevel) throws SQLException, ClassNotFoundException{
		//Khởi tạo groupName
		String nameLevel = null;
		try {
			//Mở kết nối
			openConnection();
		    //Nếu connection != null
			if (connection != null) {
				//Tạo câu truy vấn
				String queryString = "SELECT name_level FROM mst_japan where code_level = ?;";
				//Tạo đối tượng thực thi
				PreparedStatement preparedStatement = connection.prepareStatement(queryString);
				//set parameter cho preparedStatement
				preparedStatement.setString(1, codeLevel);
				//Thực thi truy vấn lấy về tập kết quả
				ResultSet resultSet = preparedStatement.executeQuery();
				//Nếu tập kết quả khác rỗng
				if (resultSet.next()) {
					nameLevel = resultSet.getString(1);
				}	
			}		
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupDaoImpl:getNameLevel:" + e.getMessage());
			throw e;
		} finally {
			//Đóng kết nối
			closeConnection();
		}
		//Trẻ về groupName
		return nameLevel;
	}
}
