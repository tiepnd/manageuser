/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstJapanEntities.java, Jul 8, 2020 tiepnd
 */
package manageuser.entities;

import java.io.Serializable;

/**
 * class java bean tương ứng với dữ liệu bảng mst_japan trong DB
 * @author tiepnd
 */
public class MstJapanEntities implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2492373259146818451L;
	private String codeLevel;
	private String nameLevel;
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
	 * lấy nameLevel 
	 * @return nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}
	/**
	 * set nameLevel
	 * @param nameLevel
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}
	
	
}
