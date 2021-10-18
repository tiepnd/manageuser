<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="View/css/style.css" rel="stylesheet" type="text/css" />
<title>ユーザ管理</title>
</head>
<body>
	<!-- Begin vung header -->	
	<jsp:include page="header.jsp" flush="true" />
	<!-- End vung header -->	

<!-- End vung header -->	

<!-- Begin vung input-->	
	<form action="ADM002.html" method="post" name="inputform">
	<table  class="tbl_input"   border="0" width="80%"  cellpadding="0" cellspacing="0" >	
		<tr>
			<td align="center" colspan="2">
				<div style="height:50px"></div>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<font color = "red">${messageError}</font>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<div style="height:70px"></div>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input class="btn" type="button" value="OK" onclick="window.location.href = 'listUser.do';"/>
			</td>
		</tr>
	</table>
	</form>
<!-- End vung input -->

	<!-- Begin vung footer -->
	<jsp:include page="footer.jsp" flush="true" />
	<!-- End vung footer -->
</body>

</html>