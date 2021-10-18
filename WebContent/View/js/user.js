/**
 * Xử lý ẩn hiển vùng trình độ tiếng nhật khi click link trình trình độ tiếng nhật
 * 
 * @param idTable là id của table chứa vùng trình độ tiếng nhật
 */
function showHigh(idTable){
	//lấy ra table theo id
	var table = document.getElementById(idTable);
	//nếu vùng trình độ tiếng nhật đang ẩn
	if (table.style.display == "none") {
		//hiện trình độ tiếng nhật
		table.style.display = "";
	} else {//nếu vùng trình độ tiếng nhật đang ẩn
		//ẩn trình độ tiếng nhật 
		table.style.display = "none";
	}
}
/**
 * Xử lý khi click button delete
 * 
 * @param message là thông báo hiển thị trên hộ thoại confirm  
 * @param userId là id của user để truyền vào query string 
 */
function onClickDelete(message, userId){
	//tạo tạo ra hộ thoại confirm và lưu giá trị vào biến isOk
	var isOk = confirm(message);
	//nếu người dùng click button OK của hộ thoại
	if (isOk == true) {
		//tạo 1 form
		var deleteUserForm = document.createElement("form");
		//set action cho form
		deleteUserForm.action = "deleteUser.do?userId=" + userId;
		//set method cho form
		deleteUserForm.method = "post";
		//add form vào file ADM005.jsp
		document.body.appendChild(deleteUserForm);
		//submit form
		deleteUserForm.submit();
		//xóa form
		deleteUserForm.remove();
	}
}

