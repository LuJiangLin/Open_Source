<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="/student/update" method="post">
				<input type="text" name="student.studentid" value="${student.studentid }"/>
				姓名：
				<input type="text" name="student.studentname" value="${student.studentname }"/><br/>
				年龄：
				<input type="number" name="student.studentage" value="${student.studentage }"/><br/>
				性别：
				<input type="text" name="student.studentsex" value="${student.studentsex }"/><br/>
				班级：
				<input type="text" name="student.classesid" value="${student.classesid }"/><br/>
				<input type="submit" value="保存"/>
		</form>
</body>
</html>