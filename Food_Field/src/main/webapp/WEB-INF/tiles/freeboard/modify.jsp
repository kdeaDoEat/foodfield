<%@page import="org.kdea.vo.FreeboardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String cp = request.getContextPath(); %> <%--ContextPath 선언 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    
<title>글수정</title>
</head>
<body>

<h2 id="bigTitle">글쓰기</h2>
<form id="bform" name="input" method="post" action="modifycontent">
<input type="hidden" name="id" value="${modi.id }">
<input type="hidden" name="num" value=" ${modi.num }">
<p>
<div id="divTiltle" >글제목</div>
<input type="text" name="title" id="title" value="${modi.title }">
<p>
<textarea name="content"  id="content">
${modi.content}
</textarea>
<p>
<button type="submit" id="btn">저장</button>
</form>

</body>
</html>
