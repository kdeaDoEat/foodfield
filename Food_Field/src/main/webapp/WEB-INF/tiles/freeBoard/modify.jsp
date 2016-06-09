<%@page import="org.kdea.vo.FreeBoardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String cp = request.getContextPath();
%>
<%--ContextPath 선언 --%>

<h2 id="bigTitle">글쓰기</h2>
<form id="bform" name="input" method="post" action="modifycontent">
	<input type="hidden" name="nickname" value="${modi.nickname }">
	<input type="hidden" name="num" value=" ${modi.num }">
	<p>
	<div id="divTiltle">글제목</div>
	<input type="text" name="title" id="title" value="${modi.title }">
	<p>
		<textarea name="contents" id="content">
${modi.contents}
</textarea>
	<p>
		<button type="submit" id="btn">저장</button>
</form>

