<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	</head>
	<body>
		<a href="/student/add">添加</a>
		<table border="1">
			 <tr>
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
                    <tr>
                      <td>${student.studentname}</td>
	             	  <td>${student.studentage}</td>
	             	  <td>${student.studentsex}</td>
	             	  <td>${student.classesname}</td>
	             	  <td>
	             	      <a href="/student/delete/${student.studentid }">删除</a>
	             	      <a href="/student/get/${student.studentid }">修改</a>
	             	  </td>
                    </tr>
            </c:forEach>
		</table>
	</body>
</html>