<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="View/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="View/js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body onload="document.getElementById('focus').focus();">
	<!-- Begin vung header -->	
	<jsp:include page="header.jsp" flush="true" />
	<!-- End vung header -->

<!-- End vung header -->	

<!-- Begin vung input-->
	<c:set var="addUserValidate" value="addUserValidate.do?action=confirm"></c:set>
	<c:set var="editUserValidate" value="editUserValidate.do?action=confirm&userId=${userInfor.userId}"></c:set>
	<form action="${userInfor.userId != 0 ? editUserValidate : addUserValidate}" method="post" name="inputform">	
	<table  class="tbl_input"   border="0" width="75%"  cellpadding="0" cellspacing="0" >		
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					会員情報編集
				</div>
			</th>			
		</tr>
		<c:forEach var="error" items="${list_Errors}">
		<tr>
			<td class="errMsg">
				<div style="padding-left:100px">
					<c:out value="${error}"></c:out>
				</div>
			</td>
		</tr>
		</c:forEach>	
		<tr>
			<td class="errMsg">
				<div style="padding-left:100px">
					&nbsp;
				</div>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<div style="padding-left:100px;">
					<table border="0" width="100%" class="tbl_input" cellpadding="4" cellspacing="0" >					
					<tr>
						<td class="lbl_left"><font color = "red">*</font> アカウント名:</td>
						<td align="left">
							<c:if test="${userInfor.userId != 0}">
								<input readonly="readonly" class="txBox" type="text" name="login_name" value="${fn:escapeXml(userInfor.loginName)}"
								size="15" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" />
							</c:if>
							<c:if test="${userInfor.userId == 0}">
								<input class="txBox" id="focus" type="text" name="login_name" value="${fn:escapeXml(userInfor.loginName)}"
								size="15" onfocus="this.style.borderColor='#0066ff';"
								onblur="this.style.borderColor='#aaaaaa';" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> グループ:</td>
						<td align="left">						
							<select name="group_id">
								<option value="0">選択してください</option>
								<c:set var = "groupIdRequest" value = "${userInfor.groupId}"/>
								<c:forEach items="${listAllMstGroup}" var="mstGroup">
									<c:set var = "groupId" value = "${mstGroup.groupId}"/>
									<c:if test = "${groupId == groupIdRequest}">
										<option value="${mstGroup.groupId}" selected="selected"> <c:out value="${mstGroup.groupName}" /></option>
									</c:if>
									<c:if test = "${groupId != groupIdRequest}">
										<option value="${mstGroup.groupId}"><c:out value="${mstGroup.groupName}" /></option>
									</c:if>
								</c:forEach>
							</select>							
							<span>&nbsp;&nbsp;&nbsp;</span>
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 氏名:</td>
						<td align="left">
						<c:if test="${userInfor.userId != 0}">
							<input class="txBox" type="text" name="fullname" value="${fn:escapeXml(userInfor.fullName)}"
							id="focus" size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
						</c:if>
						<c:if test="${userInfor.userId == 0}">
							<input class="txBox" type="text" name="fullname" value="${fn:escapeXml(userInfor.fullName)}"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
						</c:if>
													
						</td>
					</tr>
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left">
						<input class="txBox" type="text" name="fullNameKana" value="${fn:escapeXml(userInfor.fullNameKana)}"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>	
					<tr>
						<td class="lbl_left"><font color = "red">*</font> 生年月日:</td>
						<td align="left">
								<select name="birthYear">
									<c:set var="listYear" value="${listYear}" ></c:set>
									<c:set var="birthYear" value="${userInfor.arrIntBirthday.get(0)}" ></c:set>
									<c:forEach var="year" items="${listYear}">
										<c:if test="${birthYear == year}">
											<option value="${year}" selected="selected"><c:out value="${(year)}"></c:out></option>
										</c:if>
										<c:if test="${birthYear != year}">
											<option value="${year}"><c:out value="${(year)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>年
								<select name="birthMonth">
									<c:set var="listMonth" value="${listMonth}" ></c:set>
									<c:set var="birthMonth" value="${userInfor.arrIntBirthday.get(1)}" ></c:set>
									<c:forEach var="month" items="${listMonth}">
										<c:if test="${birthMonth == month}">
											<option value="${month}" selected="selected"><c:out value="${(month)}"></c:out></option>
										</c:if>
										<c:if test="${birthMonth != month}">
											<option value="${month}"><c:out value="${(month)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>月
								<select name="birthDay">
									<c:set var="listDay" value="${listDay}" ></c:set>
									<c:set var="birthDay" value="${userInfor.arrIntBirthday.get(2)}" ></c:set>
									<c:forEach var="day" items="${listDay}">
										<c:if test="${birthDay == day}">
											<option value="${day}" selected="selected"><c:out value="${(day)}"></c:out></option>
										</c:if>
										<c:if test="${birthDay != day}">
											<option value="${day}"><c:out value="${(day)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>日							
						</td>
					</tr>				
					<tr>
						<td class="lbl_left"><font color = "red">*</font> メールアドレス:</td>
						<td align="left">
							<input class="txBox" type="text" name="email" value="${fn:escapeXml(userInfor.getEmail())}"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />							
						</td>
					</tr>
					<tr>
						<td class="lbl_left"><font color = "red">*</font>電話番号:</td>
						<td align="left">
						<input class="txBox" type="text" name="tel" value="${fn:escapeXml(userInfor.tel)}"
							size="30" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />						
						</td>
					</tr>
					<c:if test="${userInfor.userId == 0}">
						<tr>
							<td class="lbl_left"><font color = "red">*</font> パスワード:</td>
							<td align="left">
									<input class="txBox" type="password" name="password" value="${fn:escapeXml(userInfor.pass)}"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" />				
							</td>
						</tr>
					</c:if>
					<c:if test="${userInfor.userId == 0}">
						<tr>
							<td class="lbl_left">パスワード（確認）:</td>
							<td align="left">
									<input class="txBox" type="password" name="passwordConfirm" value="${fn:escapeXml(userInfor.passConfirm)}"
									size="30" onfocus="this.style.borderColor='#0066ff';"
									onblur="this.style.borderColor='#aaaaaa';" />						
							</td>
						</tr>
					</c:if>
					<tr>
						<th align="left" colspan = "2" >							
								<a href = "#" onclick="showHigh('id_tbl_japan')">日本語能力</a>
						</th>			
					</tr>
				</table>
				<table border="0" width="100%" class="tbl_input" cellpadding="4" cellspacing="0" id="id_tbl_japan" style="display: none;">
					<tr>
						<td class="lbl_left">資格:</td>
						<td align="left">
							<select name="codeLevel">
								<option value="">選択してください</option>
								<c:set var = "codeLevelRequest" value = "${userInfor.codeLevel}"/>
								<c:forEach items="${listAllMstJapan}" var="mstJapan">
									<c:set var = "codeLevel" value = "${mstJapan.codeLevel}"/>
									<c:if test = "${codeLevel == codeLevelRequest}">
										<option value="${mstJapan.codeLevel}" selected="selected"><c:out value="${mstJapan.codeLevel}" /></option>
									</c:if>
									<c:if test = "${codeLevel != codeLevelRequest}">
										<option value="${mstJapan.codeLevel}"><c:out value="${mstJapan.codeLevel}" /></option>
									</c:if>
								</c:forEach>						
							</select>							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">資格交付日: </td>
						<td align="left">
							<select name="startYear">
									<c:set var="listYear" value="${listYear}" ></c:set>
									<c:set var="startYear" value="${userInfor.arrIntStartDate.get(0)}" ></c:set>
									<c:forEach var="year" items="${listYear}">
										<c:if test="${startYear == year}">
											<option value="${year}" selected="selected"><c:out value="${(year)}"></c:out></option>
										</c:if>
										<c:if test="${startYear != year}">
											<option value="${year}"><c:out value="${(year)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>年
								<select name="startMonth">
									<c:set var="listday" value="${listMonth}" ></c:set>
									<c:set var="startMonth" value="${userInfor.arrIntStartDate.get(1)}" ></c:set>
									<c:forEach var="month" items="${listMonth}">
										<c:if test="${startMonth == month}">
											<option value="${month}" selected="selected"><c:out value="${(month)}"></c:out></option>
										</c:if>
										<c:if test="${startMonth != month}">
											<option value="${month}"><c:out value="${(month)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>月
								<select name="startDay">
									<c:set var="listDay" value="${listDay}" ></c:set>
									<c:set var="startDay" value="${userInfor.arrIntStartDate.get(2)}" ></c:set>
									<c:forEach var="day" items="${listDay}">
										<c:if test="${startDay == day}">
											<option value="${day}" selected="selected"><c:out value="${(day)}"></c:out></option>
										</c:if>
										<c:if test="${startDay != day}">
											<option value="${day}"><c:out value="${(day)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>日							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">失効日: </td>
						<td align="left">
							<select name="endYear">
									<c:set var="listYear" value="${listYearJapan}" ></c:set>
									<c:set var="endYear" value="${userInfor.arrIntEndDate.get(0)}" ></c:set>
									<c:forEach var="year" items="${listYear}">
										<c:if test="${endYear == year}">
											<option value="${year}" selected="selected"><c:out value="${(year)}"></c:out></option>
										</c:if>
										<c:if test="${endYear != year}">
											<option value="${year}"><c:out value="${(year)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>年
								<select name="endMonth">
									<c:set var="listday" value="${listMonth}" ></c:set>
									<c:set var="endMonth" value="${userInfor.arrIntEndDate.get(1)}" ></c:set>
									<c:forEach var="month" items="${listMonth}">
										<c:if test="${endMonth == month}">
											<option value="${month}" selected="selected"><c:out value="${(month)}"></c:out></option>
										</c:if>
										<c:if test="${endMonth != month}">
											<option value="${month}"><c:out value="${(month)}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>月
								<select name="endDay">
									<c:set var="listDay" value="${listDay}" ></c:set>
									<c:set var="endDay" value="${userInfor.arrIntEndDate.get(2)}" ></c:set>
									<c:forEach var="day" items="${listDay}">
										<c:if test="${endDay == day}">
											<option value="${day}" selected="selected"><c:out value="${day}"></c:out></option>
										</c:if>
										<c:if test="${endDay != day}">
											<option value="${day}"><c:out value="${day}"></c:out></option>
										</c:if>
									</c:forEach>
								</select>日							
						</td>
					</tr>
					<tr>
						<td class="lbl_left">点数: </td>
						<td align="left">
							<input class="txBox" type="text" name="total" value="${userInfor.totalString}"
							size="5" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />			
						</td>
					</tr>									
				</table>
				</div>				
			</td>		
		</tr>
	</table>
	<div style="padding-left:100px;">&nbsp;</div>
		<!-- Begin vung button -->
	<div style="padding-left:45px;"> 
	<table border="0" cellpadding="4" cellspacing="0" width="300px">	
		<tr>
			<th width="200px" align="center">&nbsp;</th>
				<td>
					<input class="btn" type="submit" value="確認" />					
				</td>	
				<td>
					<c:set var="listUser" value="location.href = 'listUser.do?action=back';"></c:set>
					<c:set var="viewUserDetail" value="location.href = 'viewUserDetail.do?userId=${userInfor.userId}';"></c:set>
					<input class="btn" type="button" value="戻る" onclick="${userInfor.userId != 0 ? viewUserDetail : listUser}" />				
				</td>
		</tr>		
	</table>
	</div>
	<!-- End vung button -->	
</form>
<!-- End vung input -->

	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" flush="true" />
	<!-- End vung footer -->
</body>

</html>