<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>查看数据信息</title>
	</head>
	<base href="<%=basePath%>">
	<body>
		<a href="<%=basePath%>student/add">添加</a>
		<table border="1">
			 <tr align="center" style="background-color: darkgray;">
			    <td>
                    编号
                </td>
                <td>
                    姓名
                </td>
                <td>
                    年龄
                </td>
                <td>
                    性别
                </td>
                <td>
                    班级
                </td>
                <td>
                    操作
                </td>
            </tr>
            <c:forEach var="student" items="${studentList }">
                    <tr align="center">
                      <td>${student.studentid}</td>
                      <td>${student.studentname}</td>
	             	  <td>${student.studentage}</td>
	             	  <td>${student.studentsex}</td>
	             	  <td>${student.classesname}</td>
	             	  <td>
	             	      <a href="<%=basePath%>student/delete/${student.studentid }">删除</a>
	             	      <a href="<%=basePath%>student/get/${student.studentid }">修改</a>
	             	  </td>
                    </tr>
            </c:forEach>
		</table>
	</body>
</html>