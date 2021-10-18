/**
 * Copyright(C) 2020  Luvina SoftWare
 * MstGroupEntities.java, Jul 8, 2020 tiepnd
 */
package manageuser.entities;

import java.io.Serializable;

/**
 * class java bean tương ứng với dữ liệu bảng mst_group trong DB
 * @author tiepnd
 */
public class MstGroupEntities implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7715597041549955376L;

	private int groupId;
	
	private String groupName;
	/**
	 * lấy groupId 
	 * @return groupId
	 */
	public int getGroupId() {
		return groupId;
	}
	/**
	 * set groupId
	 * @param groupId groupId
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	/**
	 * lấy groupName 
	 * @return groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * set groupName
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	
}
