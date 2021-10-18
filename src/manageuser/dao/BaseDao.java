/**
 * Copyright(C) 2020  Luvina SoftWare
 * BaseDao.java, Jul 8, 2020 tiepnd
 */
package manageuser.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * định nghĩa ra bộ hành vi thao tác vs connection
 * 
 * @author tiepnd
 */
public interface BaseDao {
	/**
	 * Mở kết nối
	 */
	void openConnection() throws ClassNotFoundException, SQLException;
	/**
	 * Đóng kết nối
	 */
	void closeConnection();
	
	/**
	 * Lấy kết nối
	 * 
	 * @return Connection kết nối 
	 */
	Connection getConnection();
	
	/**
	 * Set kết nối
	 * 
	 * @param connection kết nối truyền vào
	 */
	void setConnection(Connection connection);
	
	/**
	 * 
	 * Tắt chế độ tự động commit, vì nếu auto commit dữ liệu sẽ update vào DB không thể rollback data được
	 */
	void disableAutoCommit() throws SQLException;
	
	/**
	 * Update dữ liệu vào DB
	 */
	void commit() throws SQLException;
	
	/**
	 * rollback data
	 */
	void rollback();
}
