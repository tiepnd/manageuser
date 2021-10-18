/**
 * Copyright(C) 2020  Luvina SoftWare
 * tblDetailUserJapanEntities.java, Jul 8, 2020 tiepnd
 */
package manageuser.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * class java bean tương ứng với dữ liệu bảng tbl_detail_user_japan trong DB
 * @author tiepnd
 */
public class TblDetailUserJapanEntities implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6405044261201140982L;
	private int detailUserJapanId;
	private int userId;
	private String codeLevel;
	private Date startDate;
	private Date endDate;
	private int total;
	
	/**
	 * lấy detailUserJapanId 
	 * @return detailUserJapanId
	 */
	public int getDetailUserJapanId() {
		return detailUserJapanId;
	}
	/**
	 * set detailUserJapanId
	 * @param detailUserJapanId
	 */
	public void setDetailUserJapanId(int detailUserJapanId) {
		this.detailUserJapanId = detailUserJapanId;
	}
	/**
	 * lấy userId 
	 * @return userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * set userId
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * lấy codeLevel 
	 * @return codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}
	/**
	 * set codeLevel
	 * @param codeLevel
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	/**
	 * lấy startDate 
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * set startDate
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * lấy endDate 
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * set endDate
	 * @param endDate 
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * lấy total 
	 * @return total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * set total
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
