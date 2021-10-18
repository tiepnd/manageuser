/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstGroupDao.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroupEntities;

/**
 * Interface định nghĩa ra bộ hành vi thao tác với bảng mst_group trong DB
 * 
 * @author tiepnd
 */
public interface MstGroupDao {
	/**
	 * Lấy tất cả các MstGroup có trong database
	 * 
	 * @return trả về danh sách các MstGroup
	 */
	List<MstGroupEntities> getAllMstGroup() throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy ra groupName 
	 * 
	 * @param groupId truyền vào groupId
	 * @return trả về groupName 
	 */
	String getGroupName(int groupId) throws SQLException, ClassNotFoundException;
}
