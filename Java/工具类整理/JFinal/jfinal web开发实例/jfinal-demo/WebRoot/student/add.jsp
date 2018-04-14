<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="/student/save" method="post">
				姓名：
				<input type="text" name="student.studentname" />${studentnameMsg}
				<br/>
				年龄：
				<input type="number" name="student.studentage" /><br/>
				性别：
				<input type="text" name="student.studentsex" /><br/>
				班级：
				<input type="text" name="student.classesid" /><br/>
				<input type="submit" value="保存"/>
		</form>
</body>
</html>