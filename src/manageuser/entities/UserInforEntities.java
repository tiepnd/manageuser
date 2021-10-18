/**
 *Copyright(C) 2020  Luvina SoftWare
 *TblUserInforEntities.java, Jul 17, 2020 tiepnd
 */
package manageuser.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * class java bean lấy thông tin của user từ DB
 * 
 * @author tiepnd
 */
public class UserInforEntities implements Serializable{
	private static final long serialVersionUID = -8079157927841358214L;
	private int userId;
	private String fullName;
	private String email;
	private String tel;
	private Date birthday;
	private String groupName;
	private String nameLevel;
	private Date endDate;
	private int total;
	private String totalString;
	private String loginName;
	private String fullNameKana;
	private String pass;
	private String passConfirm;
	private String codeLevel;
	private Date startDate;
	private int groupId;
	private ArrayList<Integer> arrIntBirthday;
	private ArrayList<Integer> arrIntStartDate;
	private ArrayList<Integer> arrIntEndDate;
	private boolean existedUserId;
	/**
	 * @return the totalString
	 */
	public String getTotalString() {
		return totalString;
	}
	/**
	 * @param totalString the totalString to set
	 */
	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}
	/**
	 * @return the arrIntBirthday
	 */
	public ArrayList<Integer> getArrIntBirthday() {
		return arrIntBirthday;
	}
	/**
	 * @param arrIntBirthday the arrIntBirthday to set
	 */
	public void setArrIntBirthday(ArrayList<Integer> arrIntBirthday) {
		this.arrIntBirthday = arrIntBirthday;
	}
	/**
	 * @return the arrIntStartDate
	 */
	public ArrayList<Integer> getArrIntStartDate() {
		return arrIntStartDate;
	}
	/**
	 * @param arrIntStartDate the arrIntStartDate to set
	 */
	public void setArrIntStartDate(ArrayList<Integer> arrIntStartDate) {
		this.arrIntStartDate = arrIntStartDate;
	}
	/**
	 * @return the arrIntEndDate
	 */
	public ArrayList<Integer> getArrIntEndDate() {
		return arrIntEndDate;
	}
	/**
	 * @param arrIntEndDate the arrIntEndDate to set
	 */
	public void setArrIntEndDate(ArrayList<Integer> arrIntEndDate) {
		this.arrIntEndDate = arrIntEndDate;
	}
	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the fullNameKana
	 */
	public String getFullNameKana() {
		return fullNameKana;
	}
	/**
	 * @param fullNameKana the fullNameKana to set
	 */
	public void setFullNameKana(String fullNameKana) {
		this.fullNameKana = fullNameKana;
	}
	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return the passConfirm
	 */
	public String getPassConfirm() {
		return passConfirm;
	}
	/**
	 * @param passConfirm the passConfirm to set
	 */
	public void setPassConfirm(String passConfirm) {
		this.passConfirm = passConfirm;
	}
	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}
	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the groupId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}
	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}
	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}
	/**
	 * @param nameLevel the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return the existedUserId
	 */
	public boolean isExistedUserId() {
		return existedUserId;
	}
	/**
	 * @param existedUserId the existedUserId to set
	 */
	public void setExistedUserId(boolean existedUserId) {
		this.existedUserId = existedUserId;
	}
	
}
