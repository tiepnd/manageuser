/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblDetailUserJapanLogic.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics;

import java.sql.SQLException;

/**
 * interface định nghĩa ra bộ hành vi thao tác vs bảng tbl_detai_user_japan
 * 
 * @author tiepnd
 */
public interface TblDetailUserJapanLogic {
	/**
	 * Kiểm tra có tồn tại userid trong bảng tbl_detail_user_japan bằng userid
	 * 
	 * @param userId id của user cần check 
	 * @return trả về true nếu tồn tại userid trong bảng tbl_detail_user_japan, flase nếu không tồn tại userid trong bảng tbl_detail_user_japan
	 */
	boolean checkExistDetailUserById(int userId) throws SQLException , ClassNotFoundException;
}
