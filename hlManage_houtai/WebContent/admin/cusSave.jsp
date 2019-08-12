<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="jQuery/jquery-3.2.1.min.js"></script>

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
				客户状态修改
			</c:when>
			<c:otherwise>
				客户状态修改
			</c:otherwise>
		</c:choose>
		</div>
		<form action="cusList?action=save" method="post">
			<div class="data_form" >
				<input type="hidden" id="cusId" name="cusId" value="${customer.cusId  }"/>
					<table align="center">
							
						
							<td><font color="red">*</font>将客户状态修改为以下其一状态：
								
							<div align="center">
								<label class="radio">
  									<input type="radio" name="cusStat" id="cusStat" value="成功推介" checked>
  									成功推介
								</label>
								
								<label class="radio">
  									<input type="radio" name="cusStat" id="cusStat" value="成功到访">
  									成功到访
								</label>
								
								<label class="radio">
  									<input type="radio" name="cusStat" id="cusStat" value="成功排号">
  									成功排号
								</label>
								
								<label class="radio">
  									<input type="radio" name="cusStat" id="cusStat" value="成功订房">
  									成功订房
								</label>
							</div>
								
							</td>
							
							<%-- <td><input type="text" id="cusStat"  name="cusStat" value="${customer.cusStat }"  style="margin-top:5px;height:30px;" /></td> --%>
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