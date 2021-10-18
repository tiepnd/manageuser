<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="View/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="View/js/user.js"></script>
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->	
		<jsp:include page="header.jsp" flush="true" />
	<!-- End vung header -->
<!-- Begin vung input-->
	<c:if test="${userInfor.userId == 0}">
		<c:set var="userOk" value="addUserOk.do?keyUser=${keyUser}"></c:set>
	</c:if>
	<c:if test="${userInfor.userId != 0}">
		<c:set var="userOk" value="editUserOk.do?keyUser=${keyUser}"></c:set>
	</c:if>
	<form action="${userOk}" method="post" name="inputform">	
	<table  class="tbl_input" border="0" width="75%"  cellpadding="0" cellspacing="0" >			
		<tr>
			<th align="left">
				<div style="padding-left:100px;">
					情報確認 <br/>
					入力された情報をＯＫボタンクリックでＤＢへ保存してください
				</div>
				<div style="padding-left:100px;">&nbsp;</div>
			</th>			
		</tr>				
		<tr>
			<td align="left" >
				<div style="padding-left:100px; overflow-wrap: anywhere;">
					<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0" >					
					<tr>
						<td class="lbl_left">アカウント名:</td>
						<td align="left"><c:out value="${userInfor.loginName}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left"><c:out value="${userInfor.groupName}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left"><c:out value="${userInfor.fullName}"></c:out></td>
					</tr>	
					<tr>
						<td class="lbl_left">カタカナ氏名:</td>
						<td align="left"><c:out value="${userInfor.fullNameKana}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">生年月日:</td>
						<td align="left"><fmt:formatDate pattern = "yyyy/MM/dd" value="${userInfor.birthday}"/></td>
					</tr>				
					<tr>
						<td class="lbl_left">メールアドレス:</td>
						<td align="left"><c:out value="${userInfor.email}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">電話番号:</td>
						<td align="left"><c:out value="${userInfor.tel}"></c:out></td>
					</tr>	
					<tr>
						<th colspan = "2"><a href = "#" onclick="showHigh('id_tbl_japan')">日本語能力</a></th>
					</tr>
					</table>
					<table border="1" width="70%" class="tbl_input" cellpadding="4" cellspacing="0" id="id_tbl_japan" style="display: none;">
					<tr>
						<td class="lbl_left">資格:</td>
						<td align="left"><c:out value="${userInfor.nameLevel}"></c:out></td>
					</tr>
					<tr>
						<td class="lbl_left">資格交付日:</td>
						<td align="left">
							<c:if test="${userInfor.codeLevel != ''}">						
								<fmt:formatDate pattern = "yyyy/MM/dd" value="${userInfor.startDate}"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td class="lbl_left">失効日:</td>
						<td align="left">
							<c:if test="${userInfor.codeLevel != ''}">						
								<fmt:formatDate pattern = "yyyy/MM/dd" value="${userInfor.endDate}"/>
							</c:if>
						</td>
					</tr>	
					<tr>
						<td class="lbl_left">点数:</td>
						<td align="left">
							<c:if test="${userInfor.codeLevel != ''}">						
								<c:out value="${userInfor.totalString}"></c:out>
							</c:if>
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
					<input class="btn" type="submit" value="OK" />			
				</td>
				<td>
					<c:if test="${userInfor.userId == 0}">
						<c:set var="addUserValidate" value="location.href ='addUserValidate.do?action=back&keyUser=${keyUser}';"></c:set>
						<input class="btn" type="button" value="戻る" onclick="${addUserValidate}"/>
					</c:if>
					<c:if test="${userInfor.userId != 0}">
						<c:set var="editUserValidate" value="location.href ='editUserValidate.do?action=back&keyUser=${keyUser}&userId=${userInfor.userId}';"></c:set>
						<input class="btn" type="button" value="戻る" onclick="${editUserValidate}"/>
					</c:if>  						
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