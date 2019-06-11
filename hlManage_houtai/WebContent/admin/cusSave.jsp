<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript">
	function checkForm(){
		var cusId=document.getElementById("cusId").value;
		var cusName=document.getElementById("cusName").value;
		var cusSex=document.getElementById("cusSex").value;
		var cusPhone=document.getElementById("cusPhone").value;
		var cusArea=document.getElementById("cusArea").value;
		var cusUserId=document.getElementById("cusUserId").value;
		var cusStat=document.getElementById("cusStat").value;
		if(cusId==""||cusName==""||cusSex==""||cusPhone==""||cusArea==""||cusUserId==""||cusStat==""){
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
				修改业务员
			</c:when>
			<c:otherwise>
				修改业务员
			</c:otherwise>
		</c:choose>
		</div>
		<form action="cusList?action=save" method="post">
			<div class="data_form" >
				<input type="hidden" id="cusId" name="cusId" value="${customer.cusId  }"/>
					<table align="center">
						<%-- <tr>
							<td><font color="red">*</font>id：</td>
							<td><input type="text" id="cusId"  name="cusId" value="${customer.cusId }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>姓名：</td>
							<td><input type="text" id="cusName"  name="cusName" value="${customer.cusName }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						
						<tr>
							<td><font color="red">*</font>性别：</td>
							<td><input type="text" id="cusSex"  name="cusSex" value="${customer.cusSex  }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						
						<tr>
							<td><font color="red">*</font>联系电话：</td>
							<td><input type="text" id="cusPhone"  name="cusPhone" value="${customer.cusPhone }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>面积：</td>
							<td><input type="text" id="cusArea"  name="cusArea" value="${customer.cusArea }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td><font color="red">*</font>中介id：</td>
							<td><input type="text" id="cusUserId"  name="cusUserId" value="${customer.cusUserId }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr> --%>
							<td><font color="red">*</font>状态：</td>
							<td><input type="text" id="cusStat"  name="cusStat" value="${customer.cusStat }"  style="margin-top:5px;height:30px;" /></td>
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