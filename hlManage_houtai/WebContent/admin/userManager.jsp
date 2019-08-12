<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function userDelete(userId) {
		if(confirm("您确定要删除这个中介吗？")) {
			window.location="userList?action=delete&userId="+userId;
		}
	}
	
	$(document).ready(function(){
		$("ul li:eq(1)").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		中介管理
		</div>
		<form name="myForm" class="form-search" method="post" action="userList?action=search">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='userList?action=preSave'">添加</button>
				<span class="data_search">
					<select id="searchType" name="searchType" style="width: 80px;">
					<!-- <option value="name">姓名</option> -->
					<option value="userName" ${searchType eq "userName"?'selected':'' }>中介名</option>
					<option value="userPhone" ${searchType eq "userPhone"?'selected':'' }>中介手机号</option>
					</select>
					&nbsp;<input id="s_userManagerText" name="s_userManagerText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_userManagerText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tr>
					<th>编号</th>
					<th>id</th>
					<th>姓名</th>
					<th>电话</th>
					<th>密码</th>
					<th>机构</th>
					<th>app推荐人</th>
					<th>操作</th>
				</tr>
				<c:forEach  varStatus="i" var="user" items="${usersList }">
					<tr>
						<td>${i.count+(page-1)*pageSize }</td>
						<td>${user.userId }</td>
						<td>${user.userName }</td>
						<td>${user.userPhone }</td>
						<td>${user.userPassword }</td>
						<td>${user.userJigou }</td>
						<td>${user.userTjname }</td>
						<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='userList?action=preSave&userId=${user.userId }'">修改</button>&nbsp;
							<button class="btn btn-mini btn-danger" type="button" onclick="userDelete(${user.userId})">删除</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
		<div class="pagination pagination-centered">
			<ul>
				${pageCode }
			</ul>
		</div>
</div>