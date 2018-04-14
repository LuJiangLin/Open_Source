<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加信息</title>
</head>
<base href="<%=basePath%>">
<body>
		<form action="<%=basePath%>student/save" method="post">
				姓名：
				<input type="text" name="student.studentname" />${studentnameMsg}
				<br/>
				年龄：
				<input type="number" name="student.studentage" /><br/>
				性别：
				<input type="radio" name="student.studentsex" value="男" checked="checked"/>男&nbsp;&nbsp;
				<input type="radio" name="student.studentsex" value="女"/>女
				<br/>
				班级：
				
				<select  name="student.classesid" style="width: 175px;">
					<c:forEach var="classes" items="${stuList }">
						<option value="${classes.classesid}">${classes.classesname}</option>
					</c:forEach>
				</select>
				<br/>
				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- <input type="text" name="student.classesid" /><br/> -->
				<input type="submit" value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="取消"/>
		</form>
</body>
</html>