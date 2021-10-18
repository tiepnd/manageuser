/**
 * Copyright(C) 2020  Luvina SoftWare
 * BaseDaoImpl.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import manageuser.dao.BaseDao;
import manageuser.utils.Constant;
import manageuser.utils.DatabaseProperties;

/**
 * thao tác với connection
 * 
 * @author tiepnd
 */
public class BaseDaoImpl implements BaseDao{
	protected Connection connection;
	
	/**
	 * Mở kết nối
	 */
	@Override
	public void openConnection() throws ClassNotFoundException, SQLException{
		try {
			//đọc thông tin để kết nối với CSDL từ file properties
			String CLASS = DatabaseProperties.getValueByKey(Constant.CLASS);
			String urlDB = DatabaseProperties.getValueByKey(Constant.URLDB);
			String userName = DatabaseProperties.getValueByKey(Constant.USER_NAME);
			String password = DatabaseProperties.getValueByKey(Constant.PASSWORD);	
			Class.forName(CLASS);
			//mở 1 kết nối đến database, và gán cho thuộc tính connection
			connection = DriverManager.getConnection(urlDB, userName, password);
		//bắt lỗi
		} catch (ClassNotFoundException|SQLException e) {
			//ghi log
			System.out.println("BaseDaoImpl:openConnection:" + e.getMessage());
			//ném lỗi
			throw e;
		}
	}

	/**
	 * Đóng kết nối
	 */
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Lấy kết nối
	 * 
	 * @return Connection kết nối 
	 */
	@Override
	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl:closeConnection:" + e.getMessage());
		}
	}
	
	/**
	 * Set kết nối
	 * 
	 * @param connection kết nối truyền vào
	 */
	@Override
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * 
	 * Tắt chế độ tự động commit, vì nếu auto commit dữ liệu sẽ update vào DB không thể rollback data được
	 */
	@Override
	public void disableAutoCommit() throws SQLException {
		try {		
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl:disableAutoCommit:" + e.getMessage());
			throw e;
		}	
	}
	
	/**
	 * Update dữ liệu vào DB
	 */
	@Override
	public void commit() throws SQLException {
		try {
			connection.commit();
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl:commit:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * rollback data
	 */
	@Override
	public void rollback() {
		try {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println("BaseDaoImpl:rollback:" + e.getMessage());
		}
	}
}
