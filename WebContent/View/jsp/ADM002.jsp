<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="View/css/style.css" rel="stylesheet" type="text/css" />
<title>ユーザ管理</title>
</head>
<body onload="document.getElementById('fullname').focus();">
	<!-- Begin vung header -->	
	<jsp:include page="header.jsp" flush="true" />
	<!-- End vung header -->	
<!-- Begin vung dieu kien tim kiem -->	
<form action="listUser.do" method="get" name="mainform">
	<table  class="tbl_input" border="0" width="90%"  cellpadding="0" cellspacing="0" >		
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				会員名称で会員を検索します。検索条件無しの場合は全て表示されます。 
			</td>
		</tr>
		<tr>
			<td width="100%">
				<table class="tbl_input" cellpadding="4" cellspacing="0" >
					<tr>
						<td class="lbl_left">氏名:</td>
						<td align="left">
						<input class="txBox" type="text" id="fullname" name="fullname" value = "${fn:escapeXml(fullname)}"
							size="20" onfocus="this.style.borderColor='#0066ff';"
							onblur="this.style.borderColor='#aaaaaa';" />
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="lbl_left">グループ:</td>
						<td align="left" width="80px">
							<select name="group_id">
								<option value="0" selected="selected">全て</option>
								<c:set var = "groupIdRequest" value = "${group_id}"/>
								<c:forEach items="${listAllMstGroup}" var="mstGroup">
									<c:set var = "groupId" value = "${mstGroup.groupId}"/>
									<c:if test = "${groupId == groupIdRequest}">
										<option value="${mstGroup.groupId}" selected="selected"><c:out value="${mstGroup.groupName}" /></option>
									</c:if>
									<c:if test = "${groupId != groupIdRequest}">
										<option value="${mstGroup.groupId}"><c:out value="${mstGroup.groupName}" /></option>
									</c:if>
								</c:forEach>
							</select>							
						</td>
						<td align="left">
							<input style="display: none;" name="action" value="search" />
							<input class="btn" type="submit" value="検索" />
							<input class="btn" type="button" value="新規追加" onclick="location.href = 'addUserInput.do';"/>							
						</td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	<!-- End vung dieu kien tim kiem -->
</form>
	<!-- Begin vung hien thi danh sach user -->
	<table class="tbl_list" border="1" cellpadding="4" cellspacing="0" width="100%" style="overflow-wrap: anywhere; table-layout: fixed;">
		<c:set var = "list" value = "${listUsers}"/>
		<c:if test = "${list.size() == 0}">
		<tr>
			<c:out value = "${MSG005}"></c:out>
		</tr>
		</c:if>
		<c:if test = "${list.size() != 0}">
			<c:url var="listUserLink" value="listUser.do">
				<c:param name="action" value="sort"></c:param>
				<c:param name="fullname" value="${fullname}"></c:param>
				<c:param name="group_id" value="${group_id}"></c:param>
				<c:param name="currentPage" value="${currentPage}"></c:param>
			</c:url>
			<tr class="tr2">
			<th align="center" width="20px">
				ID
			</th>
			<th align="left">
				氏名 <a href = "${listUserLink}&sortType=full_name&valueSort=${valueSortByFullName == 'ASC' ? 'DESC' : 'ASC'}">${valueSortByFullName == 'ASC' ? '▲▽' : '△▼'}</a>
			</th>
			<th align="left">
				生年月日
			</th>
			<th align="left">
				グループ
			</th>
			<th align="left">
				メールアドレス
			</th>
			<th align="left" width="70px">
				電話番号
			</th>
			<th align="left">
				日本語能力  <a href = "${listUserLink}&sortType=code_level&valueSort=${valueSortByCodeLevel == 'ASC' ? 'DESC' : 'ASC'}">${valueSortByCodeLevel == 'ASC' ? '▲▽' : '△▼'}</a>
			</th>
			<th align="left">
				失効日 <a href = "${listUserLink}&sortType=end_date&valueSort=${valueSortByEndDate == 'ASC' ? 'DESC' : 'ASC'}">${valueSortByEndDate == 'ASC' ? '▲▽' : '△▼'}</a>
			</th>
			<th align="left">
				点数
			</th>
		</tr>
		</c:if>
		<c:forEach items = "${listUsers}" var="user">
			<tr>
			<td align="right">
				<a href="viewUserDetail.do?userId=${user.userId}"><c:out value=" ${user.userId}"></c:out></a>
			</td>
			<td>
				 <c:out value="${user.fullName}"></c:out>
			</td>
			<td align="center">
					<fmt:formatDate pattern = "yyyy/MM/dd" value="${user.birthday}" />
			</td>
			<td>
				<c:out value="${user.groupName}"></c:out>
			</td>
			<td>
				<c:out value="${user.email}"></c:out>
			</td>
			<td>
				<c:out value="${user.tel}"></c:out>
			</td>
			<td>
				<c:out value="${user.nameLevel}"></c:out>
			</td>
			<td align="center">
				<c:out value="${user.endDate}"></c:out>
			</td>
			<td align="right">
				<c:out value="${user.totalString}"></c:out>
			</td>
		</tr>
		</c:forEach>
		
	
		
	</table>
	<!-- End vung hien thi danh sach user -->

	<!-- Begin vung paging -->
	<table>
		<tr>
			<td class = "lbl_paging">
				<c:url var="pagingLink" value="listUser.do">
					<c:param name="action" value="paging"></c:param>
					<c:param name="fullname" value="${fullname}"></c:param>
					<c:param name="group_id" value="${group_id}"></c:param>
					<c:param name="sortType" value="${sortType}"></c:param>
					<c:param name="valueSort" value="${valueSort}"></c:param>
				</c:url>
				<c:if test="${currentPage > 3}">
					<a href = "${pagingLink}&currentPage=${previousPage}"><c:out value="<<"></c:out></a>
				</c:if>	
				<c:forEach items="${listPaging}" var="page">
					<c:if test="${currentPage == page}">
						<a href = "${pagingLink}&currentPage=${page}" style="font-weight: bold;"><c:out value="${page}"></c:out></a> &nbsp;
					</c:if>
					<c:if test="${currentPage != page}">
						<a href = "${pagingLink}&currentPage=${page}"><c:out value="${page}"></c:out></a> &nbsp;
					</c:if>
				</c:forEach>
				<c:set var="totalPage" value="${totalPage}"></c:set>
				<c:if test="${listPaging.size() > 0}">
					<c:if test="${listPaging.get(listPaging.size() - 1) < totalPage}">
						<a href = "${pagingLink}&currentPage=${nextPage}">>></a>
					</c:if>
				</c:if>
			</td>
		</tr>
	</table>
	<!-- End vung paging -->
	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" flush="true" />
	<!-- End vung footer -->
</body>
</html>