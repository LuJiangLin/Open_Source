<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改信息</title>
</head>
<base href="<%=basePath%>">
<body>
		<form action="<%=basePath%>student/update" method="post">
				<input type="hidden" name="student.studentid" value="${student.studentid }"/>
				姓名：
				<input type="text" name="student.studentname" value="${student.studentname }"/><br/>
				年龄：
				<input type="number" name="student.studentage" value="${student.studentage }"/><br/>
				性别：
				<input type="radio" name="student.studentsex" value="男" checked="checked"/>男&nbsp;&nbsp;
				<input type="radio" name="student.studentsex" value="女"/>女
				<br/>
				班级：
				<select  name="student.classesid" style="width: 175px;">
					<option value="1">一班</option>
					<option value="2">二班</option>
					<option value="3">三班</option>
				</select>
				<br/>
				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- <input type="text" name="student.classesid" /><br/> -->
				<input type="submit" value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="取消"/>
		</form>
</body>
</html>