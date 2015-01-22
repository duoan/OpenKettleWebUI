<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="<%=path%>" />
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <base href="<%=basePath%>">
    <title>kettle数据转换web调度控制平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="kettle,web,java,数据转换">
	<meta http-equiv="description" content="kettle数据转换web控制平台">
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		function executeTrans(transname,dir){
			$.ajax({
				type    : "get",
				url     : "${ctx}/servlet/kettle?transname="+transname+"&dir="+dir,
				timeout : 3000,
				dataType: "json",
				success : function(){
					
				},
				error   : function(){
					alert("请求出错");
				}
			});
		}
	</script>
	
  </head>
  <body>
	<div class="container" style="margin-top: 10px;">
		<c:if test="${flag == 'f' }">
		<div class="alert alert-warning alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<strong>Warning!</strong> 执行转换失败
		</div>
		</c:if>
		<c:if test="${flag == 's' }">
		<div class="alert alert-success alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<strong>Success!</strong> 执行转换成功
		</div>
		</c:if>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">kettle数据转换web调度控制平台</h3>
			</div>
			<div class="panel-body">
				<table class="table table-bordered table-hover table-condensed">
					<thead>
						<tr>
							<th>transname</th>
							<th>path</th>
							<th>operate</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${transFiles}" var="trans">
						<tr>
							<td>${trans.name}</td>
							<td>${trans.absolutePath}</td>
							<td><a class="btn btn-xs btn-info" href="<%=path%>/servlet/kettle?transname=${trans.name}&dir=${dir}">执行</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
