/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstJapanLogicImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import manageuser.dao.MstJapanDao;
import manageuser.dao.impl.MstJapanDaoImpl;
import manageuser.entities.MstJapanEntities;
import manageuser.logics.MstJapanLogic;

/**
 *  Xử lý các logic của đối tượng MstJapan
 *  
 * @author tiepnd
 */
public class MstJapanLogicImpl implements MstJapanLogic {
	/**
	 * Lấy tất cả các MstJapan có trong database
	 * 
	 * @return trả về danh sách các MstJapan
	 */
	@Override
	public List<MstJapanEntities> getAllMstJapan() throws ClassNotFoundException, SQLException {
		//Tạo list đối tượng MstJapanEntities
		List<MstJapanEntities> listMstJapanEntities = new ArrayList<MstJapanEntities>();
		//lấy list đối tượng MstJapanEntities
		try {
			//tạo đối tượng MstJapanDaoImpl
			MstJapanDao mstJapanDaoImpl = new MstJapanDaoImpl();
			listMstJapanEntities = mstJapanDaoImpl.getAllMstJapan();
			//trả về list MstJapanEntities
			return listMstJapanEntities;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstJapanLogicImpl:getAllMstJapan:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Lấy ra tên trình độ tiếng nhật
	 * 
	 * @param codeLevel truyền vào mã trình độ tiếng nhật
	 * @return trả về tên trình độ tiếng nhật
	 */
	@Override
	public String getNameLevel(String codeLevel) throws SQLException, ClassNotFoundException{
		//Khởi tạo đối tượng nameLevel
		String nameLevel = null;
		//tạo đối tượng MstJapanDaoImpl
		MstJapanDao mstJapanDao = new MstJapanDaoImpl();
		try {
			//Lấy nameLevel từ DB
			nameLevel = mstJapanDao.getNameLevel(codeLevel);
			//Trả về nameLevel
			return nameLevel;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupLogicImpl:getNameLevel:" + e.getMessage());
			throw e;
		}
	}
}
