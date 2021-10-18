/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstJapanLogic.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics;

import java.sql.SQLException;
import java.util.List;
import manageuser.entities.MstJapanEntities;

/**
 * interface định nghĩa ra bộ hành vi thao tác vs bảng mst_japan
 * 
 * @author tiepnd
 */
public interface MstJapanLogic {
	/**
	 * Lấy tất cả các MstJapan có trong database
	 * 
	 * @return trả về danh sách các MstJapan
	 */
	List<MstJapanEntities> getAllMstJapan() throws SQLException, ClassNotFoundException;
	
	/**
	 * Lấy ra tên trình độ tiếng nhật
	 * 
	 * @param codeLevel truyền vào mã trình độ tiếng nhật
	 * @return trả về tên trình độ tiếng nhật
	 */
	String getNameLevel(String codeLevel) throws SQLException, ClassNotFoundException;
}
