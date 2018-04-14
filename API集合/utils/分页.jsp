<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<%-- 
给出分页相差的链接
 --%>
<center>
第${pb.pc }页/共${pb.tp }页

<a href="${pb.url }&pc=1">首页</a>
<c:if test="${pb.pc > 1 }">
<a href="${pb.url }&pc=${pb.pc-1}">上一页</a>
</c:if>

<%-- 计算begin、end --%>
<c:choose>
	<%-- 如果总页数不足10页，那么把所有的页数都显示出来！ --%>
	<c:when test="${pb.tp <= 10 }">
		<c:set var="begin" value="1" />
		<c:set var="end" value="${pb.tp }" />
	</c:when>
	<c:otherwise>
		<%-- 当总页数>10时，通过公式计算出begin和end --%>
		<c:set var="begin" value="${pb.pc-5 }" />
		<c:set var="end" value="${pb.pc+4 }" />	
		<%-- 头溢出 --%>
		<c:if test="${begin < 1 }">
			<c:set var="begin" value="1" />
			<c:set var="end" value="10" />
		</c:if>	
		<%-- 尾溢出 --%>
		<c:if test="${end > pb.tp }">
			<c:set var="begin" value="${pb.tp - 9 }" />
			<c:set var="end" value="${pb.tp }" />
		</c:if>	
	</c:otherwise>
</c:choose>
<%-- 循环遍历页码列表 --%>
<c:forEach var="i" begin="${begin }" end="${end }">
	<c:choose>
		<c:when test="${i eq pb.pc }">
			[${i }]
		</c:when>
		<c:otherwise>
			<a href="${pb.url }&pc=${i}">[${i }]</a>	
		</c:otherwise>
	</c:choose>
	
</c:forEach>


<c:if test="${pb.pc < pb.tp }">
<a href="${pb.url }&pc=${pb.pc+1}">下一页</a>
</c:if>
<a href="${pb.url }&pc=${pb.tp}">尾页</a>

