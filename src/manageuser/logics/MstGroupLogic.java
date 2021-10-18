/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstGroupLogic.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;

import manageuser.entities.MstGroupEntities;

/**
 * interface định nghĩa ra bộ hành vi thao tác vs bảng mst_group
 * 
 * @author tiepnd
 */
public interface MstGroupLogic {
	/**
	 * Lấy tất cả các MstGroup có trong database
	 * 
	 * @return trả về danh sách các MstGroup
	 */
	List<MstGroupEntities> getAllMstGroup() throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy ra tên nhóm từ mã nhóm
	 * 
	 * @param groupId truyền vào mã nhóm
	 * @return trả về tên nhóm 
	 */
	String getGroupName(int groupId) throws SQLException, ClassNotFoundException;
}
