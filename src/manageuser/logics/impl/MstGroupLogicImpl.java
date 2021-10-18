/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstGroupLogicImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import manageuser.dao.MstGroupDao;
import manageuser.dao.impl.MstGroupDaoImpl;
import manageuser.entities.MstGroupEntities;
import manageuser.logics.MstGroupLogic;

/**
 * Xử lý các logic của đối tượng MstGroup
 * 
 * @author tiepnd
 */
public class MstGroupLogicImpl implements MstGroupLogic{
	/**
	 * Lấy tất cả các MstGroup có trong database
	 * 
	 * @return trả về danh sách các MstGroup
	 */
	@Override
	public List<MstGroupEntities> getAllMstGroup() throws ClassNotFoundException, SQLException {
		//Tạo list đối tượng MstGroupEntities
		List<MstGroupEntities> listMstGroupEntities = new ArrayList<MstGroupEntities>();
		//lấy list đối tượng MstGroupEntities
		try {
			//tạo đối tượng MstGroupDaoImpl
			MstGroupDao mstGroupDao = new MstGroupDaoImpl();
			listMstGroupEntities = mstGroupDao.getAllMstGroup();
			//trả về list mstGroupEntities
			return listMstGroupEntities;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupLogicImpl:getAllMstGroup:" + e.getMessage());
			throw e;
		}
	}
	/**
	 * Lấy ra tên nhóm từ mã nhóm
	 * 
	 * @param groupId truyền vào mã nhóm
	 * @return trả về tên nhóm 
	 */
	@Override
	public String getGroupName(int groupId) throws SQLException, ClassNotFoundException{
		//Khởi tạo đối tượng groupName
		String groupName = null;
		//tạo đối tượng MstGroupDaoImpl
		MstGroupDao mstGroupDao = new MstGroupDaoImpl();
		try {
			//Lấy groupName từ DB
			groupName = mstGroupDao.getGroupName(groupId);
			//Trả về groupName
			return groupName;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("MstGroupLogicImpl:getGroupName:" + e.getMessage());
			throw e;
		}
	}
}
