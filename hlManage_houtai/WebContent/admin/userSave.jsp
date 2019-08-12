<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function checkForm(){
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("userPassword").value;
		var jigou=document.getElementById("userJigou").value;
		var userPhone=document.getElementById("userPhone").value;
		if(userName==""||userPassword==""||jigou==""||userPhone==""){
			document.getElementById("error").innerHTML="信息填写不完整！";
			return false;
		} 
		return true;
	}
	
	$(document).ready(function(){
		$("ul li:eq(1)").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${user.userId!=null }">
				修改中介信息
			</c:when>
			<c:otherwise>
				添加中介信息
			</c:otherwise>
		</c:choose>
		</div>
		<form action="userList?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="userId" name="userId" value="${user.userId }"/>
					<table align="center">
					
						<tr>
							<td><font color="red">*</font>中介机构名称：</td>
							<td><input type="text" id="userJigou"  name="userJigou" value="${user.userJigou }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>中介姓名：</td>
							<td><input type="text" id="userName"  name="userName" value="${user.userName }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>中介手机号：</td>
							<td><input type="text" id="userPhone"  name="userPhone" value="${user.userPhone }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>app推荐人：</td>
							<td><input type="text" id="userTjname"  name="userTjname" value="${user.userTjname }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>密码：</td>
							<td><input type="password" id="userPassword"  name="userPassword" value="${user.userPassword }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						
						
					</table>
					<div align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
						&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
					</div>
					<div align="center">
						<font id="error" color="red">${error }</font>
					</div>
			</div>
		</form>
</div>