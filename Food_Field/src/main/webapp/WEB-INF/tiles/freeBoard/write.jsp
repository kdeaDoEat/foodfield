<%@page import="org.kdea.vo.FreeboardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String cp = request.getContextPath();
	String parentnum=request.getParameter("num");
	System.out.println("ref: "+parentnum);
	if(parentnum==null){
		parentnum="0";
		System.out.println("상귀 글번호가 없을때 ref: "+parentnum);
	}
	session.setAttribute("ref", parentnum);
%> <%--ContextPath 선언 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

<title>자유게시판 글쓰기</title>
</head>
<body>
<h2 id="bigTitle">글쓰기</h2>
<form id="bform" name="input" method="post" action="winput">
<input type="hidden" name="id" value="jungheeLee">
<input type="hidden" name="ref" value="${ref}"> 
<p>
<div id="divTiltle" >글제목</div>
<input type="text" name="title" id="title" >

<p><p>
<textarea name="content"  id="content">
내용
</textarea>
<p>
<button type="submit" id="btn">저장</button>
</form>
<!--글쓰기 내용 -->

</body>
</html>