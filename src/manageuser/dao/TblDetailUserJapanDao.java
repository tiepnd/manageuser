/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblDetailUserJapanDao.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao;

import java.sql.SQLException;
import manageuser.entities.TblDetailUserJapanEntities;

/**
 * interface định nghĩa ra bộ hành vi thao tác với bảng tbl_detail_user_japan
 * 
 * @author tiepnd
 */
public interface TblDetailUserJapanDao extends BaseDao{
	/**
	 * Thực hiện thêm mới 1 đối tượng TblDetailUserJapanEntities vào bảng tbl_detail_user_japan
	 * 
	 * @param detailUserJapan Đối tượng chứa thông tin của TblDetailUserJapan
	 */
	void insertTblDetailUserJapanDao(TblDetailUserJapanEntities detailUserJapan) throws SQLException;
	
	/**
	 * Lấy thông tin user trong bảng tbl_detail_user_japan bằng userid
	 * 
	 * @param userId id của user cần lấy
	 * @return trả về đối tượng TblDetailUserJapanEntities chứa thông tin user trong bảng tbl_detail_user_japan
	 */
	TblDetailUserJapanEntities getDetailUserById (int userId) throws SQLException , ClassNotFoundException;
	/**
	 * Thực hiện update thông tin user vào bảng tbl_detail_user_japan
	 * 
	 * @param detailUserJapan đối tượng TblDetailUserJapanEntities chứa thông tin user
	 */
	void updateTblDetailUserJapanDao(TblDetailUserJapanEntities detailUserJapan) throws SQLException;
	/**
	 * Thực hiện delete thông tin user trong bảng tbl_detail_user_japan
	 * 
	 * @param userId id của user cần xóa
	 */
	void deleteTblDetailUserJapanDao(int userId) throws SQLException;
	
}
