<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function userDelete(cusId) {
		if(confirm("您确定要删除这个客户吗？")) {
			window.location="cusList?action=delete&cusId="+cusId;
		}
	}
	
	$(document).ready(function(){
		$("ul li:eq(1)").addClass("active");
	});
</script>
<div class="data_list">
		<div class="data_list_title">
		推介客户管理
		</div>
		<form name="myForm" class="form-search" method="post" action="cusList?action=search">
				<!-- 添加 暂时用不到
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='userList?action=preSave'">添加</button> -->
				<span class="data_search">
					<select id="searchType" name="searchType" style="width: 120px;">
					<!-- <option value="name">姓名</option> -->
					<option value="cusName" ${searchType eq "cusName"?'selected':'' }>客户名</option>
					<option value="cusPhone" ${searchType eq "cusPhone"?'selected':'' }>客户手机号</option>
					<option value="cusGuwen" ${searchType eq "cusGuwen"?'selected':'' }>客户指定置业顾问</option>
					</select>
					&nbsp;<input id=s_customerManagerText name="s_customerManagerText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_customerManagerText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tr>
					<th>编号</th>
					<th>id</th>
					<th>姓名</th>
					<th>性别</th>
					<th>电话</th>
					<th>预计到访时间</th>
					<th>面积</th>
					<th>推介人姓名</th>
					
					
					<th>推介人机构</th>
					<th>指定置业顾问</th>
					<th>中介app推荐人</th>
					
					 
					<th>客户状态</th>
					<th>推介时间</th>
					<th>操作</th>
				</tr>
				<c:forEach  varStatus="i" var="customer" items="${cusList }">
					<tr>
						<td>${i.count+(page-1)*pageSize }</td>
						<td>${customer.cusId }</td>
						<td>${customer.cusName }</td>
						<td>${customer.cusSex }</td>
						<td>${customer.cusPhone }</td>
						<td>${customer.cusDate }</td>
						<td>${customer.cusArea }</td>
						<td>${customer.cusUser }</td>
						
						<td>${customer.cusUserJigou }</td>
						<td>${customer.cusGuwen }</td> 
						<td>${customer.cusUserTjname }</td>
					
						<td>${customer.cusStat }</td>
						<td>${customer.cusTjTime }</td>
						<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='cusList?action=preSave&cusId=${customer.cusId }'">修改</button>&nbsp;
							<button class="btn btn-mini btn-danger" type="button" onclick="userDelete(${customer.cusId})">删除</button></td>
					</tr>
				</c:forEach>
				
				<!--  
				<c:forEach  varStatus="i" var="customer" items="${userList }">
					<tr>
						<td>${user.userJigou }</td>
					</tr>
				</c:forEach>
				-->
				
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
		<div class="pagination pagination-centered">
			<ul>
				${pageCode }
			</ul>
		</div>
</div>