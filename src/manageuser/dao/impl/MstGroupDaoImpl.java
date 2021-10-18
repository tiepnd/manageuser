/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstGroupDaoImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.entities.MstGroupEntities;

/**
 * Class thao tác với bảng mst_group trong DB
 * 
 * @author tiepnd
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {
	/**
	 * Lấy tất cả các MstGroup có trong database
	 * 
	 * @return trả về danh sách các MstGroup
	 */
	@Override
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException{
		List<MstGroupEntities> listAllMstGroup = new ArrayList<MstGroupEntities>();
		try {
			//Mở 1 kết nối
			openConnection();
			if (connection != null) {
				//tạo câu truy vấn
				String selectQuery = "select * from mst_group order by group_id desc;";
				//Tạo đối tượng thực thi
				Statement statement = connection.createStatement();
				//Thực thi truy vấn
				ResultSet resultSet = statement.executeQuery(selectQuery);
				//add tập resultset vào 1 List<User>
				while (resultSet.next()) {
					//Tạo đối MstGroupEntities 
					MstGroupEntities mstGroup = new MstGroupEntities();
					//set thuộc tính groupId
					mstGroup.setGroupId(resultSet.getInt(1));
					//set thuộc tính groupName
					mstGroup.setGroupName(resultSet.getString(2));
					//thêm mstGroupEntities vào listMstGroupEntities
					listAllMstGroup.add(mstGroup);
				}	
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupDaoImpl:getAllMstGroup:" + e.getMessage());
			throw e;
		} finally {
			//Đóng kết nối
			closeConnection();
		}
		//trả về listMstGroupEntities
		return listAllMstGroup;
	}
	
	/**
	 * Lấy ra tên nhóm theo mã nhóm 
	 * 
	 * @param groupId truyền vào mã nhó
	 * @return trả về groupName 
	 */
	@Override
	public String getGroupName(int groupId) throws SQLException, ClassNotFoundException{
		//Khởi tạo groupName
		String groupName = null;
		try {
			//Mở kết nối
			openConnection();
		    //Nếu connection != null
			if (connection != null) {
				//Tạo câu truy vấn
				String queryString = "SELECT group_name FROM mst_group where group_id = ?;";
				//Tạo đối tượng thực thi
				PreparedStatement preparedStatement = connection.prepareStatement(queryString);
				//set parameter cho preparedStatement
				preparedStatement.setInt(1, groupId);
				//Thực thi truy vấn lấy về tập kết quả
				ResultSet resultSet = preparedStatement.executeQuery();
				//Nếu tập kết quả khác rỗng
				if (resultSet.next()) {
					groupName = resultSet.getString(1);
				}	
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupDaoImpl:getGroupName:" + e.getMessage());
			throw e;
		} finally {
			//Đóng kết nối
			closeConnection();
		}
		//Trả về groupName
		return groupName;
	}
}
