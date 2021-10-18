/**
 * Copyright(C) 2020  Luvina SoftWare
 * TblDetailUserJapanLogicImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import manageuser.dao.TblDetailUserJapanDao;
import manageuser.dao.impl.TblDetailUserJapanDaoImpl;
import manageuser.entities.TblDetailUserJapanEntities;
import manageuser.logics.TblDetailUserJapanLogic;

/**
 * Xử lý các logic của đối tượng TblDetailUserJapan
 * 
 * @author tiepnd
 */
public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {
	/**
	 * Kiểm tra có tồn tại userid trong bảng tbl_detail_user_japan bằng userid
	 * 
	 * @param userId id của user cần check 
	 * @return trả về true nếu tồn tại userid trong bảng tbl_detail_user_japan, flase nếu không tồn tại userid trong bảng tbl_detail_user_japan
	 */
	@Override
	public boolean checkExistDetailUserById(int userId) throws SQLException, ClassNotFoundException {
		try {
			//Tạo đối tượng TblDetailUserJapanDao để sử dụng phương thức getDetailUserById
			TblDetailUserJapanDao detailUserJapanDao = new TblDetailUserJapanDaoImpl();
			//Lấy detailUser bởi userid
			TblDetailUserJapanEntities detailUser = detailUserJapanDao.getDetailUserById(userId);
			//Trả về true nếu detailUser tồn tại, flase nếu detailUser không tồn tại
			return detailUser != null;
		//bắt lỗi
		} catch (SQLException|ClassNotFoundException e) {
			//ghi log
			System.out.println("TblDetailUserJapanLogicImpl:checkExistDetailUserById:" + e.getMessage());
			//ném lỗi
			throw e;
		}
	}
}
